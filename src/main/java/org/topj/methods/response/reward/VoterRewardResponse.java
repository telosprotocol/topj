package org.topj.methods.response.reward;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.List;

public class VoterRewardResponse {
    @JSONField(name = "last_claim_time")
    private BigInteger lastClaimTime;

    @JSONField(name = "unclaimed")
    private BigInteger unclaimed;

    @JSONField(name = "accumulated")
    private BigInteger accumulated;

    @JSONField(name = "node_rewards")
    private List<NodeRewardResponse> nodeRewards;

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

    public List<NodeRewardResponse> getNodeRewards() {
        return nodeRewards;
    }

    public void setNodeRewards(List<NodeRewardResponse> nodeRewards) {
        this.nodeRewards = nodeRewards;
    }
}
