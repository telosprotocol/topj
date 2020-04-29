/*
 * Copyright 2019 Sawyer Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    private BigInteger freeze;

    @JSONField(name = "last_hash")
    private String lastHash;

    @JSONField(name = "last_hash_xxhash64")
    private String lastHashXxhash64;

    @JSONField(name = "last_unit_height")
    private BigInteger lastUnitHeight;

    @JSONField(name = "nonce")
    private BigInteger nonce;

    @JSONField(name = "random_seed")
    private String randomSeed;

    @JSONField(name = "random_seed_xxhash64")
    private BigInteger randomSeedXxhash64;

    /**
     * tgas质押金
     */
    @JSONField(name = "tgas_balance")
    private BigInteger tgasBalance;

    @JSONField(name = "lock_tgas")
    private BigInteger lockTGas;

    @JSONField(name = "lock_deposit_balance")
    private BigInteger lockDepositBalance;

    @JSONField(name = "lock_balance")
    private BigInteger lockBalance;

    /**
     * 未投票数
     */
    @JSONField(name = "unvote_num")
    private BigInteger unvoteNum;

    /**
     * vote 质押金
     */
    @JSONField(name = "vote_balance")
    private BigInteger voteBalance;

    /**
     * disk质押金
     */
    @JSONField(name = "disk_balance")
    private BigInteger diskBalance;

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

    public BigInteger getFreeze() {
        return freeze;
    }

    public void setFreeze(BigInteger freeze) {
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

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public String getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(String randomSeed) {
        this.randomSeed = randomSeed;
    }

    public BigInteger getRandomSeedXxhash64() {
        return randomSeedXxhash64;
    }

    public void setRandomSeedXxhash64(BigInteger randomSeedXxhash64) {
        this.randomSeedXxhash64 = randomSeedXxhash64;
    }

    public BigInteger getTgasBalance() {
        return tgasBalance;
    }

    public void setTgasBalance(BigInteger tgasBalance) {
        this.tgasBalance = tgasBalance;
    }

    public BigInteger getUnvoteNum() {
        return unvoteNum;
    }

    public void setUnvoteNum(BigInteger unvoteNum) {
        this.unvoteNum = unvoteNum;
    }

    public BigInteger getVoteBalance() {
        return voteBalance;
    }

    public void setVoteBalance(BigInteger voteBalance) {
        this.voteBalance = voteBalance;
    }

    public BigInteger getDiskBalance() {
        return diskBalance;
    }

    public void setDiskBalance(BigInteger diskBalance) {
        this.diskBalance = diskBalance;
    }

    public BigInteger getLockTGas() {
        return lockTGas;
    }

    public void setLockTGas(BigInteger lockTGas) {
        this.lockTGas = lockTGas;
    }

    public BigInteger getLockDepositBalance() {
        return lockDepositBalance;
    }

    public void setLockDepositBalance(BigInteger lockDepositBalance) {
        this.lockDepositBalance = lockDepositBalance;
    }

    public BigInteger getLockBalance() {
        return lockBalance;
    }

    public void setLockBalance(BigInteger lockBalance) {
        this.lockBalance = lockBalance;
    }
}
