package org.topj.methods.Model;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class Proposal {

    @JSONField(name = "effective_timer_height")
    private BigInteger effectiveTimerHeight;

    @JSONField(name = "expire_time")
    private String expireTime;

    @JSONField(name = "priority")
    private BigInteger priority;

    @JSONField(name = "proposal_account_addr")
    private String proposalAccountAddr;

    @JSONField(name = "proposal_deposit")
    private BigInteger proposalDeposit;

    @JSONField(name = "proposal_id")
    private String proposalId;

    @JSONField(name = "proposal_type")
    private BigInteger proposalType;

    @JSONField(name = "target")
    private String target;

    @JSONField(name = "value")
    private String value;

    @JSONField(name = "voting_status")
    private BigInteger votingStatus;

    public BigInteger getEffectiveTimerHeight() {
        return effectiveTimerHeight;
    }

    public void setEffectiveTimerHeight(BigInteger effectiveTimerHeight) {
        this.effectiveTimerHeight = effectiveTimerHeight;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public BigInteger getPriority() {
        return priority;
    }

    public void setPriority(BigInteger priority) {
        this.priority = priority;
    }

    public String getProposalAccountAddr() {
        return proposalAccountAddr;
    }

    public void setProposalAccountAddr(String proposalAccountAddr) {
        this.proposalAccountAddr = proposalAccountAddr;
    }

    public BigInteger getProposalDeposit() {
        return proposalDeposit;
    }

    public void setProposalDeposit(BigInteger proposalDeposit) {
        this.proposalDeposit = proposalDeposit;
    }

    public String getProposalId() {
        return proposalId;
    }

    public void setProposalId(String proposalId) {
        this.proposalId = proposalId;
    }

    public BigInteger getProposalType() {
        return proposalType;
    }

    public void setProposalType(BigInteger proposalType) {
        this.proposalType = proposalType;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public BigInteger getVotingStatus() {
        return votingStatus;
    }

    public void setVotingStatus(BigInteger votingStatus) {
        this.votingStatus = votingStatus;
    }
}
