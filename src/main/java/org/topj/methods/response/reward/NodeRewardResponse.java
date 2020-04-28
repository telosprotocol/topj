package org.topj.methods.response.reward;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class NodeRewardResponse {
    @JSONField(name = "last_claim_time")
    private BigInteger lastClaimTime;

    @JSONField(name = "unclaimed")
    private BigInteger unclaimed;

    @JSONField(name = "accumulated")
    private BigInteger accumulated;

    @JSONField(name = "account")
    private String account;

    public BigInteger getLastClaimTime() {
        return lastClaimTime;
    }

    public void setLastClaimTime(BigInteger lastClaimTime) {
        this.lastClaimTime = lastClaimTime;
    }

    public BigInteger getUnclaimed() {
        return unclaimed;
    }

    public void setUnclaimed(BigInteger unclaimed) {
        this.unclaimed = unclaimed;
    }

    public BigInteger getAccumulated() {
        return accumulated;
    }

    public void setAccumulated(BigInteger accumulated) {
        this.accumulated = accumulated;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
