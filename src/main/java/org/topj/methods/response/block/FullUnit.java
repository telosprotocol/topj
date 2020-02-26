package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class FullUnit{
    @JSONField(name = "m_account_type")
    private Integer mAccountType;
    @JSONField(name = "m_account_address")
    private String mAccountAddress;
    @JSONField(name = "m_account_balance")
    private Long mAccountBalance;
    @JSONField(name = "m_account_credit")
    private Long mAccountCredit;
    @JSONField(name = "m_account_nonce")
    private Long m_account_nonce;
    @JSONField(name = "m_account_create_time")
    private Long mAccountCreateTime;
    @JSONField(name = "m_account_status")
    private Integer mAccountStatus;
    @JSONField(name = "m_property_hash")
    private Map<String, String> mPropertyHash;
    @JSONField(name = "m_native_property")
    private Integer mNativeProperty;

    public Integer getmAccountType() {
        return mAccountType;
    }

    public void setmAccountType(Integer mAccountType) {
        this.mAccountType = mAccountType;
    }

    public String getmAccountAddress() {
        return mAccountAddress;
    }

    public void setmAccountAddress(String mAccountAddress) {
        this.mAccountAddress = mAccountAddress;
    }

    public Long getmAccountBalance() {
        return mAccountBalance;
    }

    public void setmAccountBalance(Long mAccountBalance) {
        this.mAccountBalance = mAccountBalance;
    }

    public Long getmAccountCredit() {
        return mAccountCredit;
    }

    public void setmAccountCredit(Long mAccountCredit) {
        this.mAccountCredit = mAccountCredit;
    }

    public Long getM_account_nonce() {
        return m_account_nonce;
    }

    public void setM_account_nonce(Long m_account_nonce) {
        this.m_account_nonce = m_account_nonce;
    }

    public Long getmAccountCreateTime() {
        return mAccountCreateTime;
    }

    public void setmAccountCreateTime(Long mAccountCreateTime) {
        this.mAccountCreateTime = mAccountCreateTime;
    }

    public Integer getmAccountStatus() {
        return mAccountStatus;
    }

    public void setmAccountStatus(Integer mAccountStatus) {
        this.mAccountStatus = mAccountStatus;
    }

    public Map<String, String> getmPropertyHash() {
        return mPropertyHash;
    }

    public void setmPropertyHash(Map<String, String> mPropertyHash) {
        this.mPropertyHash = mPropertyHash;
    }

    public Integer getmNativeProperty() {
        return mNativeProperty;
    }

    public void setmNativeProperty(Integer mNativeProperty) {
        this.mNativeProperty = mNativeProperty;
    }
}
