package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class AccountInfoResponse {

    @JSONField(name = "account")
    private String accountAddress;

    @JSONField(name = "balance")
    private BigInteger balance;

    @JSONField(name="contract_address")
    private String contractAddress;

    @JSONField(name="freeze")
    private Integer freeze;

    @JSONField(name = "last_hash")
    private String lastHash;

    @JSONField(name = "last_hash_xxhash64")
    private String lastHashXxhash64;

    @JSONField(name = "last_unit_height")
    private BigInteger lastUnitHeight;

    @JSONField(name = "nonce")
    private Long nonce;

    @JSONField(name = "random_seed")
    private String randomSeed;

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public String getContractAddress() {
        return contractAddress;
    }

    public void setContractAddress(String contractAddress) {
        this.contractAddress = contractAddress;
    }

    public Integer getFreeze() {
        return freeze;
    }

    public void setFreeze(Integer freeze) {
        this.freeze = freeze;
    }

    public String getLastHash() {
        return lastHash;
    }

    public void setLastHash(String lastHash) {
        this.lastHash = lastHash;
    }

    public String getLastHashXxhash64() {
        return lastHashXxhash64;
    }

    public void setLastHashXxhash64(String lastHashXxhash64) {
        this.lastHashXxhash64 = lastHashXxhash64;
    }

    public BigInteger getLastUnitHeight() {
        return lastUnitHeight;
    }

    public void setLastUnitHeight(BigInteger lastUnitHeight) {
        this.lastUnitHeight = lastUnitHeight;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public String getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(String randomSeed) {
        this.randomSeed = randomSeed;
    }
}
