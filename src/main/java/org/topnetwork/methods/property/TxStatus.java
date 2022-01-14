package org.topnetwork.methods.property;

public enum TxStatus {

    SUCCESS("success"), FAILURE("failure"), PENDING("pending"), NULL("null");

    private String status;
    private TxStatus(String status) {
        this.status = status;
    }
    public String getStatus() {
        return this.status;
    }
}
