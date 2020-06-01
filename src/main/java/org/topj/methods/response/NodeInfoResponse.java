package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class NodeInfoResponse {

    @JSONField(name = "m_vote_amount")
    private String voteAmount;

    @JSONField(name = "m_credit_denominator")
    private String creditDenominator;

    @JSONField(name = "network_id")
    private String networkId;

    @JSONField(name = "nickname")
    private String nickname;

    @JSONField(name = "m_registered_role")
    private String registeredRole;

    @JSONField(name = "m_support_ratio_numerator")
    private String supportRatioNumerator;

    @JSONField(name = "m_support_ratio_denominator")
    private String supportRatioDenominator;

    @JSONField(name = "m_validate_vote_stake")
    private String validateVoteStake;

    @JSONField(name = "m_audit_vote_stake")
    private String auditVoteStake;

    /**
     * 节点保证金
     */
    @JSONField(name = "m_account_mortgage")
    private String accountMortgage;

    @JSONField(name = "m_account")
    private String account;

    @JSONField(name = "m_credit_numerator")
    private String creditNumerator;

    public String getVoteAmount() {
        return voteAmount;
    }

    public void setVoteAmount(String voteAmount) {
        this.voteAmount = voteAmount;
    }

    public String getCreditDenominator() {
        return creditDenominator;
    }

    public void setCreditDenominator(String creditDenominator) {
        this.creditDenominator = creditDenominator;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getRegisteredRole() {
        return registeredRole;
    }

    public void setRegisteredRole(String registeredRole) {
        this.registeredRole = registeredRole;
    }

    public String getSupportRatioNumerator() {
        return supportRatioNumerator;
    }

    public void setSupportRatioNumerator(String supportRatioNumerator) {
        this.supportRatioNumerator = supportRatioNumerator;
    }

    public String getSupportRatioDenominator() {
        return supportRatioDenominator;
    }

    public void setSupportRatioDenominator(String supportRatioDenominator) {
        this.supportRatioDenominator = supportRatioDenominator;
    }

    public String getValidateVoteStake() {
        return validateVoteStake;
    }

    public void setValidateVoteStake(String validateVoteStake) {
        this.validateVoteStake = validateVoteStake;
    }

    public String getAuditVoteStake() {
        return auditVoteStake;
    }

    public void setAuditVoteStake(String auditVoteStake) {
        this.auditVoteStake = auditVoteStake;
    }

    public String getAccountMortgage() {
        return accountMortgage;
    }

    public void setAccountMortgage(String accountMortgage) {
        this.accountMortgage = accountMortgage;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getCreditNumerator() {
        return creditNumerator;
    }

    public void setCreditNumerator(String creditNumerator) {
        this.creditNumerator = creditNumerator;
    }
}
