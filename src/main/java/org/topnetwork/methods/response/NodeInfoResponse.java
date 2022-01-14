package org.topnetwork.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class NodeInfoResponse {

    @JSONField(name = "account_addr")
    private String accountAddr;

    @JSONField(name = "auditor_credit")
    private String auditorCredit;

    @JSONField(name = "auditor_stake")
    private BigInteger auditorStake;

    @JSONField(name = "dividend_ratio")
    private BigInteger dividend_ratio;

    @JSONField(name = "network_id")
    private String networkId;

    @JSONField(name = "node_deposit")
    private BigInteger nodeDeposit;

    @JSONField(name = "node_sign_key")
    private String nodeSignKey;

    @JSONField(name = "nodename")
    private String nodename;

    @JSONField(name = "rec_stake")
    private BigInteger recStake;

    @JSONField(name = "registered_node_type")
    private String registeredNodeType;

    @JSONField(name = "validator_credit")
    private String validatorCredit;

    @JSONField(name = "validator_stake")
    private BigInteger validatorStake;

    @JSONField(name = "vote_amount")
    private BigInteger voteAmount;

    @JSONField(name = "zec_stake")
    private BigInteger zecStake;

    public String getAccountAddr() {
        return accountAddr;
    }

    public void setAccountAddr(String accountAddr) {
        this.accountAddr = accountAddr;
    }

    public String getAuditorCredit() {
        return auditorCredit;
    }

    public void setAuditorCredit(String auditorCredit) {
        this.auditorCredit = auditorCredit;
    }

    public BigInteger getAuditorStake() {
        return auditorStake;
    }

    public void setAuditorStake(BigInteger auditorStake) {
        this.auditorStake = auditorStake;
    }

    public BigInteger getDividend_ratio() {
        return dividend_ratio;
    }

    public void setDividend_ratio(BigInteger dividend_ratio) {
        this.dividend_ratio = dividend_ratio;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public BigInteger getNodeDeposit() {
        return nodeDeposit;
    }

    public void setNodeDeposit(BigInteger nodeDeposit) {
        this.nodeDeposit = nodeDeposit;
    }

    public String getNodeSignKey() {
        return nodeSignKey;
    }

    public void setNodeSignKey(String nodeSignKey) {
        this.nodeSignKey = nodeSignKey;
    }

    public String getNodename() {
        return nodename;
    }

    public void setNodename(String nodename) {
        this.nodename = nodename;
    }

    public BigInteger getRecStake() {
        return recStake;
    }

    public void setRecStake(BigInteger recStake) {
        this.recStake = recStake;
    }

    public String getRegisteredNodeType() {
        return registeredNodeType;
    }

    public void setRegisteredNodeType(String registeredNodeType) {
        this.registeredNodeType = registeredNodeType;
    }

    public String getValidatorCredit() {
        return validatorCredit;
    }

    public void setValidatorCredit(String validatorCredit) {
        this.validatorCredit = validatorCredit;
    }

    public BigInteger getValidatorStake() {
        return validatorStake;
    }

    public void setValidatorStake(BigInteger validatorStake) {
        this.validatorStake = validatorStake;
    }

    public BigInteger getVoteAmount() {
        return voteAmount;
    }

    public void setVoteAmount(BigInteger voteAmount) {
        this.voteAmount = voteAmount;
    }

    public BigInteger getZecStake() {
        return zecStake;
    }

    public void setZecStake(BigInteger zecStake) {
        this.zecStake = zecStake;
    }
}
