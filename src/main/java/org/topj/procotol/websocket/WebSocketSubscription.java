package org.topj.procotol.websocket;

import io.reactivex.subjects.BehaviorSubject;

public class WebSocketSubscription<T> {
    private BehaviorSubject<T> subject;
    private Class<T> responseType;

    /**
     * Creates WebSocketSubscription.
     *
     * @param subject used to send new data items to listeners
     * @param responseType type of a data item returned by a WebSocket subscription
     */
    public WebSocketSubscription(BehaviorSubject<T> subject, Class<T> responseType) {
        this.subject = subject;
        this.responseType = responseType;
    }

    public BehaviorSubject<T> getSubject() {
        return subject;
    }

    public Class<T> getResponseType() {
        return responseType;
    }
}
