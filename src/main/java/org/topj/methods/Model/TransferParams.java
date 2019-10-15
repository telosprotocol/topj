package org.topj.methods.Model;

import org.topj.utils.TopjConfig;

import java.io.IOException;

public class TransferParams {
    private String to;
    private String coinType = "";
    private Long amount = Long.valueOf(0);
    private String note = "";
    private Integer transDeposit = 100000;

    public TransferParams(Long amount) {
        this.amount = amount;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getTransDeposit() {
        return transDeposit;
    }

    public void setTransDeposit(Integer transDeposit) {
        this.transDeposit = transDeposit;
    }
}
