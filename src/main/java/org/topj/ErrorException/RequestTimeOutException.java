package org.topj.ErrorException;

public class RequestTimeOutException extends RuntimeException {
    public RequestTimeOutException(String message) {
        super(message);
    }
}
