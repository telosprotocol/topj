package org.topnetwork.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.List;

public class FullUnit{
    @JSONField(name = "account_balance")
    private BigInteger accountBalance;

    @JSONField(name = "account_create_time")
    private Long accountCreateTime;

    @JSONField(name = "burned_amount_change")
    private BigInteger burnedAmountChange;

    @JSONField(name = "latest_full_unit_hash")
    private String latestFullUnitHash;

    @JSONField(name = "latest_full_unit_number")
    private BigInteger latestFullUnitNumber;

    @JSONField(name = "latest_recv_trans_number")
    private BigInteger latestRecvTransNumber;

    @JSONField(name = "latest_send_trans_hash")
    private String latestSendTransHash;

    @JSONField(name = "latest_send_trans_number")
    private BigInteger latestSendTransnumber;

    @JSONField(name = "txs")
    private List<TxObj> txs;

    public BigInteger getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigInteger accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Long getAccountCreateTime() {
        return accountCreateTime;
    }

    public void setAccountCreateTime(Long accountCreateTime) {
        this.accountCreateTime = accountCreateTime;
    }

    public BigInteger getBurnedAmountChange() {
        return burnedAmountChange;
    }

    public void setBurnedAmountChange(BigInteger burnedAmountChange) {
        this.burnedAmountChange = burnedAmountChange;
    }

    public String getLatestFullUnitHash() {
        return latestFullUnitHash;
    }

    public void setLatestFullUnitHash(String latestFullUnitHash) {
        this.latestFullUnitHash = latestFullUnitHash;
    }

    public BigInteger getLatestFullUnitNumber() {
        return latestFullUnitNumber;
    }

    public void setLatestFullUnitNumber(BigInteger latestFullUnitNumber) {
        this.latestFullUnitNumber = latestFullUnitNumber;
    }

    public BigInteger getLatestRecvTransNumber() {
        return latestRecvTransNumber;
    }

    public void setLatestRecvTransNumber(BigInteger latestRecvTransNumber) {
        this.latestRecvTransNumber = latestRecvTransNumber;
    }

    public String getLatestSendTransHash() {
        return latestSendTransHash;
    }

    public void setLatestSendTransHash(String latestSendTransHash) {
        this.latestSendTransHash = latestSendTransHash;
    }

    public BigInteger getLatestSendTransnumber() {
        return latestSendTransnumber;
    }

    public void setLatestSendTransnumber(BigInteger latestSendTransnumber) {
        this.latestSendTransnumber = latestSendTransnumber;
    }

    public List<TxObj> getTxs() {
        return txs;
    }

    public void setTxs(List<TxObj> txs) {
        this.txs = txs;
    }
}
