package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class UnitObj {

    @JSONField(name = "account")
    private String account;

    @JSONField(name = "unit_height")
    private BigInteger unitHeight;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public BigInteger getUnitHeight() {
        return unitHeight;
    }

    public void setUnitHeight(BigInteger unitHeight) {
        this.unitHeight = unitHeight;
    }
}
