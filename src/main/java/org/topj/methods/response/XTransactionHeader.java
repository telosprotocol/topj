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
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class XTransactionHeader {

    @JSONField(name = "transaction_type")
    private BigInteger transactionType = BigInteger.ZERO;

    @JSONField(name = "transaction_len")
    private BigInteger transactionLen = BigInteger.ZERO;

    @JSONField(name = "version")
    private BigInteger version = BigInteger.ZERO;

    @JSONField(name = "to_network_id")
    private BigInteger toNetworkId = BigInteger.ZERO;

    @JSONField(name = "from_network_id")
    private BigInteger fromNetworkId = BigInteger.ZERO;

    @JSONField(name = "to_account_id")
    private BigInteger toAccountId = BigInteger.ZERO;

    @JSONField(name = "from_account_id")
    private BigInteger fromAccountId = BigInteger.ZERO;

    @JSONField(name = "deposit")
    private BigInteger deposit = BigInteger.ZERO;

    @JSONField(name = "expire_duration")
    private BigInteger expireDuration = BigInteger.ZERO;

    @JSONField(name = "fire_timestamp")
    private BigInteger fireTimestamp = BigInteger.ZERO;

    @JSONField(name = "trans_random_nounce")
    private BigInteger transRandomNounce = BigInteger.ZERO;

    @JSONField(name = "hash_work_proof")
    private BigInteger hashWorkProof = BigInteger.ZERO;

    @JSONField(name = "last_unit_hight")
    private BigInteger lastUnitHight = BigInteger.ZERO;

    @JSONField(name = "last_unit_hash")
    private BigInteger lastUnitHash = BigInteger.ZERO;

    @JSONField(name = "last_trans_nonce")
    private BigInteger lastTransNonce = BigInteger.ZERO;

    @JSONField(name = "last_trans_hash")
    private String lastTransHash = "";

    @JSONField(name = "parent_account")
    private String parentAccount = "";

    @JSONField(name = "confirm_action")
    private String confirmAction = "";

    @JSONField(name = "ext")
    private String ext = "";

    @JSONField(name = "authority_keys")
    private String authorityKeys = "";

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.BigIntToBytes(transactionType, 16)
                .BigIntToBytes(transactionLen, 16)
                .BigIntToBytes(version, 32)
                .BigIntToBytes(toAccountId, 64)
                .BigIntToBytes(fromAccountId, 64)
                .BigIntToBytes(toNetworkId, 16)
                .BigIntToBytes(fromNetworkId, 16)
                .BigIntToBytes(deposit, 32)
                .BigIntToBytes(expireDuration, 16)
                .BigIntToBytes(fireTimestamp, 64)
                .BigIntToBytes(transRandomNounce, 32)
                .BigIntToBytes(hashWorkProof, 32)
                .BigIntToBytes(lastUnitHight, 64)
                .BigIntToBytes(lastUnitHash, 64)
                .BigIntToBytes(lastTransNonce, 64)
                .hexToBytes(lastTransHash.replaceFirst("0x", ""))
                .stringToBytes(parentAccount)
                .stringToBytes(authorityKeys)
                .stringToBytes(confirmAction)
                .stringToBytes(ext);
        return bufferUtils.pack();
    }

    public BigInteger getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(BigInteger transactionType) {
        this.transactionType = transactionType;
    }

    public BigInteger getTransactionLen() {
        return transactionLen;
    }

    public void setTransactionLen(BigInteger transactionLen) {
        this.transactionLen = transactionLen;
    }

    public BigInteger getVersion() {
        return version;
    }

    public void setVersion(BigInteger version) {
        this.version = version;
    }

    public BigInteger getToNetworkId() {
        return toNetworkId;
    }

    public void setToNetworkId(BigInteger toNetworkId) {
        this.toNetworkId = toNetworkId;
    }

    public BigInteger getFromNetworkId() {
        return fromNetworkId;
    }

    public void setFromNetworkId(BigInteger fromNetworkId) {
        this.fromNetworkId = fromNetworkId;
    }

    public BigInteger getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(BigInteger toAccountId) {
        this.toAccountId = toAccountId;
    }

    public BigInteger getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(BigInteger fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public BigInteger getDeposit() {
        return deposit;
    }

    public void setDeposit(BigInteger deposit) {
        this.deposit = deposit;
    }

    public BigInteger getExpireDuration() {
        return expireDuration;
    }

    public void setExpireDuration(BigInteger expireDuration) {
        this.expireDuration = expireDuration;
    }

    public BigInteger getFireTimestamp() {
        return fireTimestamp;
    }

    public void setFireTimestamp(BigInteger fireTimestamp) {
        this.fireTimestamp = fireTimestamp;
    }

    public BigInteger getTransRandomNounce() {
        return transRandomNounce;
    }

    public void setTransRandomNounce(BigInteger transRandomNounce) {
        this.transRandomNounce = transRandomNounce;
    }

    public BigInteger getHashWorkProof() {
        return hashWorkProof;
    }

    public void setHashWorkProof(BigInteger hashWorkProof) {
        this.hashWorkProof = hashWorkProof;
    }

    public BigInteger getLastUnitHight() {
        return lastUnitHight;
    }

    public void setLastUnitHight(BigInteger lastUnitHight) {
        this.lastUnitHight = lastUnitHight;
    }

    public BigInteger getLastUnitHash() {
        return lastUnitHash;
    }

    public void setLastUnitHash(BigInteger lastUnitHash) {
        this.lastUnitHash = lastUnitHash;
    }

    public BigInteger getLastTransNonce() {
        return lastTransNonce;
    }

    public void setLastTransNonce(BigInteger lastTransNonce) {
        this.lastTransNonce = lastTransNonce;
    }

    public String getLastTransHash() {
        return lastTransHash;
    }

    public void setLastTransHash(String lastTransHash) {
        this.lastTransHash = lastTransHash;
    }

    public String getParentAccount() {
        return parentAccount;
    }

    public void setParentAccount(String parentAccount) {
        this.parentAccount = parentAccount;
    }

    public String getConfirmAction() {
        return confirmAction;
    }

    public void setConfirmAction(String confirmAction) {
        this.confirmAction = confirmAction;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public String getAuthorityKeys() {
        return authorityKeys;
    }

    public void setAuthorityKeys(String authorityKeys) {
        this.authorityKeys = authorityKeys;
    }
}
