package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class LightUnitState{

   @JSONField(name = "balance_change")
   private BigInteger balanceChange;

   @JSONField(name = "burned_amount_change")
   private BigInteger burnedAmountChange;

    public BigInteger getBalanceChange() {
        return balanceChange;
    }

    public void setBalanceChange(BigInteger balanceChange) {
        this.balanceChange = balanceChange;
    }

    public BigInteger getBurnedAmountChange() {
        return burnedAmountChange;
    }

    public void setBurnedAmountChange(BigInteger burnedAmountChange) {
        this.burnedAmountChange = burnedAmountChange;
    }
}
