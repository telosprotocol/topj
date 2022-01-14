package org.topnetwork.procotol.websocket;

import org.topnetwork.methods.response.ResponseBase;

import java.util.concurrent.CompletableFuture;

public class WebSocketRequest<T> {
    private CompletableFuture<ResponseBase<T>> onReply;
    private Class<T> responseType;

    public WebSocketRequest(CompletableFuture<ResponseBase<T>> onReply, Class<T> responseType) {
        this.onReply = onReply;
        this.responseType = responseType;
    }

    public CompletableFuture<ResponseBase<T>> getOnReply() {
        return onReply;
    }

    public Class<T> getResponseType() {
        return responseType;
    }
}
