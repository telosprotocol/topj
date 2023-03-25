package org.topnetwork.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class RecvBlockInfo {

    @JSONField(name = "height")
    private BigInteger height = BigInteger.ZERO;

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
