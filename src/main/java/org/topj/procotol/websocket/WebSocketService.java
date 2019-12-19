package org.topj.procotol.websocket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topj.methods.response.ResponseBase;
import org.topj.procotol.TopjService;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.*;

public class WebSocketService implements TopjService {
    private static final Logger log = LoggerFactory.getLogger(WebSocketService.class);

    private final long REQUEST_TIMEOUT = 60;
    private final ScheduledExecutorService executor;

    private final WebSocketClient webSocketClient;

    private Map<Long, WebSocketRequest<?>> requestForId = new ConcurrentHashMap<>();
    private Map<Long, WebSocketSubscription<?>> subscriptionRequestForId =
            new ConcurrentHashMap<>();
    private Map<String, WebSocketSubscription<?>> subscriptionForId = new ConcurrentHashMap<>();

    public WebSocketService(String serverUrl){
        URI serverURI = null;
        try {
            serverURI = new URI(serverUrl);
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Failed to parse URL: '%s'", serverUrl), e);
        }
        this.executor = Executors.newScheduledThreadPool(1);
        webSocketClient = new WebSocketClient(serverURI);
    }

    public void connect() throws ConnectException {
        try {
            boolean connected = webSocketClient.connectBlocking();
            if (!connected) {
                throw new ConnectException("Failed to connect to WebSocket");
            }
            webSocketClient.setListener(
                    new WebSocketListener() {
                        @Override
                        public void onMessage(String message) throws IOException {
                            onWebSocketMessage(message);
                        }

                        @Override
                        public void onError(Exception e) {
                            log.error("Received error from a WebSocket connection", e);
                        }

                        @Override
                        public void onClose() {
                            onWebSocketClose();
                        }
                    });
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            log.warn("Interrupted while connecting via WebSocket protocol");
        }
    }

    @Override
    public <T> ResponseBase<T> send(Map<String, String> args, Class<T> responseClass) throws IOException {
        long requestId = Long.valueOf(args.get("sequence_id"));
        try {

            StringBuilder stringBuilder = new StringBuilder();
            args.forEach((key, value) -> {
                stringBuilder.append("&").append(key).append("=").append(value);
            });
            String str = stringBuilder.toString();
            str = str.replaceFirst("&", "");

            CompletableFuture<ResponseBase<T>> result = new CompletableFuture<>();
            Class<ResponseBase> ct = ResponseBase.class;
            requestForId.put(requestId, new WebSocketRequest<T>(result, responseClass));
            log.debug("Sending request: {}", str);
            System.out.println("send request");
            System.out.println(str);
            webSocketClient.send(str);
            setRequestTimeout(requestId);
            return result.get();
        } catch (InterruptedException e) {
            Thread.interrupted();
            throw new IOException("Interrupted WebSocket request", e);
        } catch (ExecutionException e) {
            if (e.getCause() instanceof IOException) {
                throw (IOException) e.getCause();
            }
            throw new RuntimeException("Unexpected exception", e.getCause());
        }
    }

    @Override
    public void close() throws IOException {
        webSocketClient.close();
        executor.shutdown();
    }

    void onWebSocketMessage(String messageStr) throws IOException {
        ResponseBase responseBase = JSON.parseObject(messageStr, new TypeReference<ResponseBase>() {});
        WebSocketRequest request = requestForId.get(Long.valueOf(responseBase.getSequenceId()));
        String dataStr = JSON.toJSONString(responseBase.getData());
        responseBase.setData(JSON.parseObject(dataStr, request.getResponseType()));
        request.getOnReply().complete(responseBase);
        log.debug(messageStr);
    }

    void onWebSocketClose() {
        requestForId
                .values()
                .forEach(
                        request -> {
                            request.getOnReply()
                                    .completeExceptionally(
                                            new IOException("Connection was closed"));
                        });
        subscriptionForId
                .values()
                .forEach(
                        subscription -> {
                            subscription
                                    .getSubject()
                                    .onError(new IOException("Connection was closed"));
                        });
    }

    void closeRequest(long requestId, Exception e) {
        CompletableFuture result = requestForId.get(requestId).getOnReply();
        requestForId.remove(requestId);
        result.completeExceptionally(e);
    }

    private void setRequestTimeout(long requestId) {
        executor.schedule(
                () -> closeRequest(
                        requestId,
                        new IOException(
                                String.format("Request with id %d timed out", requestId))),
                REQUEST_TIMEOUT,
                TimeUnit.SECONDS);
    }
}
