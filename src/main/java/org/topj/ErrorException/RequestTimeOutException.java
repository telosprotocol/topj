package org.topj.ErrorException;

public class RequestTimeOutException extends RuntimeException {
    private String tx_hash;
    public RequestTimeOutException(String message, String tx_hash) {
        super(message);
        this.tx_hash = tx_hash;
    }

    public String getTx_hash() {
        return tx_hash;
    }
}
