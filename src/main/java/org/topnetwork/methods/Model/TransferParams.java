package org.topnetwork.methods.Model;

import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;

public class TransferParams {
    private String to;
    private String coinType = "";
    private BigInteger amount = BigInteger.ZERO;
    private String note = "";
    private BigInteger transDeposit = TopjConfig.getDeposit();

    public TransferParams(BigInteger amount) throws IOException {
        this.amount = amount;
    }

    public TransferParams(String to, BigInteger amount, String note) throws IOException {
        this.to = to;
        this.amount = amount;
        this.note = note;
    }

    public TransferParams(String to, String coinType, BigInteger amount, String note, BigInteger transDeposit) throws IOException {
        this.to = to;
        this.coinType = coinType;
        this.amount = amount;
        this.note = note;
        this.transDeposit = transDeposit;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getCoinType() {
        return coinType;
    }

    public void setCoinType(String coinType) {
        this.coinType = coinType;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigInteger getTransDeposit() {
        return transDeposit;
    }

    public void setTransDeposit(BigInteger transDeposit) throws IOException {
        if (transDeposit == null) {
            this.transDeposit = TopjConfig.getDeposit();
        }
        this.transDeposit = transDeposit;
    }
}
