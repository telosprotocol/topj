package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class VoteStakedDetail {

    @JSONField(name = "duration")
    private BigInteger duration;

    @JSONField(name = "lock_time")
    private BigInteger lockTime;

    @JSONField(name = "lock_token")
    private BigInteger lockToken;

    @JSONField(name = "vote_num")
    private BigInteger voteNum;

    public BigInteger getLockToken() {
        return lockToken;
    }

    public void setLockToken(BigInteger lockToken) {
        this.lockToken = lockToken;
    }

    public BigInteger getDuration() {
        return duration;
    }

    public void setDuration(BigInteger duration) {
        this.duration = duration;
    }

    public BigInteger getLockTime() {
        return lockTime;
    }

    public void setLockTime(BigInteger lockTime) {
        this.lockTime = lockTime;
    }

    public BigInteger getVoteNum() {
        return voteNum;
    }

    public void setVoteNum(BigInteger voteNum) {
        this.voteNum = voteNum;
    }
}
