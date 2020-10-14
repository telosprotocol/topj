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

    @JSONField(name = "account_addr")
    private String accountAddr;

    @JSONField(name = "available_gas")
    private BigInteger availableGas;

    @JSONField(name = "balance")
    private BigInteger balance;

    @JSONField(name = "burned_token")
    private BigInteger burnedToken;

    @JSONField(name = "cluster_id")
    private BigInteger clusterId;

    @JSONField(name = "created_time")
    private BigInteger createdTime;

    @JSONField(name = "disk_staked_token")
    private BigInteger diskStakedToken;

    @JSONField(name = "gas_staked_token")
    private BigInteger gasStakedToken;

    @JSONField(name = "group_id")
    private BigInteger groupId;

    @JSONField(name = "latest_tx_hash")
    private String latestTxHash;

    @JSONField(name = "latest_tx_hash_xxhash64")
    private String latestTxHashXxhash64;

    @JSONField(name = "latest_unit_height")
    private BigInteger latestUnitHeight;

    @JSONField(name = "lock_balance")
    private BigInteger lockBalance;

    @JSONField(name = "nonce")
    private BigInteger nonce;

    @JSONField(name = "total_free_gas")
    private BigInteger totalFreeGas;

    @JSONField(name = "total_gas")
    private BigInteger totalGas;

    @JSONField(name = "total_stake_gas")
    private BigInteger totalStakeGas;

    @JSONField(name = "unlock_disk_staked")
    private BigInteger unlockDiskStaked;

    @JSONField(name = "unlock_gas_staked")
    private BigInteger unlockGasStaked;

    @JSONField(name = "unused_free_gas")
    private BigInteger unusedFreeGas;

    @JSONField(name = "unused_stake_gas")
    private BigInteger unusedStakeGas;

    @JSONField(name = "unused_vote_amount")
    private BigInteger unusedVoteAmount;

    @JSONField(name = "vote_staked_token")
    private BigInteger voteStakedToken;

    @JSONField(name = "zone_id")
    private BigInteger zoneId;

    public String getAccountAddr() {
        return accountAddr;
    }

    public void setAccountAddr(String accountAddr) {
        this.accountAddr = accountAddr;
    }

    public BigInteger getAvailableGas() {
        return availableGas;
    }

    public void setAvailableGas(BigInteger availableGas) {
        this.availableGas = availableGas;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }

    public BigInteger getBurnedToken() {
        return burnedToken;
    }

    public void setBurnedToken(BigInteger burnedToken) {
        this.burnedToken = burnedToken;
    }

    public BigInteger getClusterId() {
        return clusterId;
    }

    public void setClusterId(BigInteger clusterId) {
        this.clusterId = clusterId;
    }

    public BigInteger getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(BigInteger createdTime) {
        this.createdTime = createdTime;
    }

    public BigInteger getDiskStakedToken() {
        return diskStakedToken;
    }

    public void setDiskStakedToken(BigInteger diskStakedToken) {
        this.diskStakedToken = diskStakedToken;
    }

    public BigInteger getGasStakedToken() {
        return gasStakedToken;
    }

    public void setGasStakedToken(BigInteger gasStakedToken) {
        this.gasStakedToken = gasStakedToken;
    }

    public BigInteger getGroupId() {
        return groupId;
    }

    public void setGroupId(BigInteger groupId) {
        this.groupId = groupId;
    }

    public String getLatestTxHash() {
        return latestTxHash;
    }

    public void setLatestTxHash(String latestTxHash) {
        this.latestTxHash = latestTxHash;
    }

    public String getLatestTxHashXxhash64() {
        return latestTxHashXxhash64;
    }

    public void setLatestTxHashXxhash64(String latestTxHashXxhash64) {
        this.latestTxHashXxhash64 = latestTxHashXxhash64;
    }

    public BigInteger getLatestUnitHeight() {
        return latestUnitHeight;
    }

    public void setLatestUnitHeight(BigInteger latestUnitHeight) {
        this.latestUnitHeight = latestUnitHeight;
    }

    public BigInteger getLockBalance() {
        return lockBalance;
    }

    public void setLockBalance(BigInteger lockBalance) {
        this.lockBalance = lockBalance;
    }

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public BigInteger getTotalGas() {
        return totalGas;
    }

    public void setTotalGas(BigInteger totalGas) {
        this.totalGas = totalGas;
    }

    public BigInteger getUnlockDiskStaked() {
        return unlockDiskStaked;
    }

    public void setUnlockDiskStaked(BigInteger unlockDiskStaked) {
        this.unlockDiskStaked = unlockDiskStaked;
    }

    public BigInteger getUnlockGasStaked() {
        return unlockGasStaked;
    }

    public void setUnlockGasStaked(BigInteger unlockGasStaked) {
        this.unlockGasStaked = unlockGasStaked;
    }

    public BigInteger getUnusedFreeGas() {
        return unusedFreeGas;
    }

    public void setUnusedFreeGas(BigInteger unusedFreeGas) {
        this.unusedFreeGas = unusedFreeGas;
    }

    public BigInteger getUnusedStakeGas() {
        return unusedStakeGas;
    }

    public void setUnusedStakeGas(BigInteger unusedStakeGas) {
        this.unusedStakeGas = unusedStakeGas;
    }

    public BigInteger getUnusedVoteAmount() {
        return unusedVoteAmount;
    }

    public void setUnusedVoteAmount(BigInteger unusedVoteAmount) {
        this.unusedVoteAmount = unusedVoteAmount;
    }

    public BigInteger getVoteStakedToken() {
        return voteStakedToken;
    }

    public void setVoteStakedToken(BigInteger voteStakedToken) {
        this.voteStakedToken = voteStakedToken;
    }

    public BigInteger getZoneId() {
        return zoneId;
    }

    public void setZoneId(BigInteger zoneId) {
        this.zoneId = zoneId;
    }

    public BigInteger getTotalFreeGas() {
        return totalFreeGas;
    }

    public void setTotalFreeGas(BigInteger totalFreeGas) {
        this.totalFreeGas = totalFreeGas;
    }

    public BigInteger getTotalStakeGas() {
        return totalStakeGas;
    }

    public void setTotalStakeGas(BigInteger totalStakeGas) {
        this.totalStakeGas = totalStakeGas;
    }
}
