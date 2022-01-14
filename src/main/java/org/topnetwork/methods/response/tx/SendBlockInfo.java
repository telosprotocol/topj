package org.topnetwork.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class SendBlockInfo {

    @JSONField(name = "height")
    private BigInteger height = BigInteger.ZERO;

    @JSONField(name = "tx_fee")
    private BigInteger txFee = BigInteger.ZERO;

    @JSONField(name = "account")
    private String account = "";

    @JSONField(name = "used_gas")
    private BigInteger usedGas = BigInteger.ZERO;

    public BigInteger getHeight() {
        return height;
    }

    public void setHeight(BigInteger height) {
        this.height = height;
    }

    public BigInteger getTxFee() {
        return txFee;
    }

    public void setTxFee(BigInteger txFee) {
        this.txFee = txFee;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigInteger getUsedGas() {
        return usedGas;
    }

    public void setUsedGas(BigInteger usedGas) {
        this.usedGas = usedGas;
    }
}
