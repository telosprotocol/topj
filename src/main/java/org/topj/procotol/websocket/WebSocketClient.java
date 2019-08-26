package org.topj.procotol.websocket;

import org.java_websocket.handshake.ServerHandshake;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.Optional;

public class WebSocketClient extends org.java_websocket.client.WebSocketClient {

    private static final Logger log = LoggerFactory.getLogger(WebSocketClient.class);
    private Optional<WebSocketListener> listenerOpt = Optional.empty();

    public WebSocketClient(URI serverUri) {
        super(serverUri);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        log.debug("Opened WebSocket connection to {}", uri);
    }

    @Override
    public void onMessage(String message) {
        log.debug("Received message {} from server {}", message, uri);
        listenerOpt.ifPresent(
                listener -> {
                    try {
                        listener.onMessage(message);
                    } catch (Exception e) {
                        log.error("Failed to process message '{}' from server {}", message, uri, e);
                    }
                });
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        log.debug(
                "Closed WebSocket connection to {}, because of reason: '{}'."
                        + "Connection closed remotely: {}",
                uri,
                reason,
                remote);
        listenerOpt.ifPresent(WebSocketListener::onClose);
    }

    @Override
    public void onError(Exception ex) {
        log.error("WebSocket connection to {} failed with error", uri, ex);
    }

    public void setListener(WebSocketListener listener) {
        this.listenerOpt = Optional.ofNullable(listener);
    }
}
