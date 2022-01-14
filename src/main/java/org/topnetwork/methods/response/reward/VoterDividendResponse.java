package org.topnetwork.methods.response.reward;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.List;

public class VoterDividendResponse {
    @JSONField(name = "last_claim_time")
    private BigInteger lastClaimTime;

    @JSONField(name = "unclaimed")
    private BigInteger unclaimed;

    @JSONField(name = "accumulated")
    private BigInteger accumulated;

    @JSONField(name = "accumulated_decimals")
    private BigInteger accumulatedDecimals;

    @JSONField(name = "issue_time")
    private BigInteger issueTime;

    @JSONField(name = "unclaimed_decimals")
    private BigInteger unclaimedDecimals;

    @JSONField(name = "node_dividend")
    private List<NodeRewardResponse> nodeDividend;

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

    public BigInteger getAccumulatedDecimals() {
        return accumulatedDecimals;
    }

    public void setAccumulatedDecimals(BigInteger accumulatedDecimals) {
        this.accumulatedDecimals = accumulatedDecimals;
    }

    public BigInteger getIssueTime() {
        return issueTime;
    }

    public void setIssueTime(BigInteger issueTime) {
        this.issueTime = issueTime;
    }

    public BigInteger getUnclaimedDecimals() {
        return unclaimedDecimals;
    }

    public void setUnclaimedDecimals(BigInteger unclaimedDecimals) {
        this.unclaimedDecimals = unclaimedDecimals;
    }

    public List<NodeRewardResponse> getNodeDividend() {
        return nodeDividend;
    }

    public void setNodeDividend(List<NodeRewardResponse> nodeDividend) {
        this.nodeDividend = nodeDividend;
    }
}
