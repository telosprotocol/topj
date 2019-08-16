package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class XTransactionHeader {

    @JSONField(name = "transaction_type")
    private Short transactionType = 0;

    @JSONField(name = "transaction_len")
    private Short transactionLen = 0;

    @JSONField(name = "version")
    private Integer version = 0;

    @JSONField(name = "to_network_id")
    private Short toNetworkId = 0;

    @JSONField(name = "from_network_id")
    private Short fromNetworkId = 0;

    @JSONField(name = "to_account_id")
    private Long toAccountId = Long.valueOf(0);

    @JSONField(name = "from_account_id")
    private Long fromAccountId = Long.valueOf(0);

    @JSONField(name = "gas_price")
    private int gasPrice = 0;

    @JSONField(name = "gas_limit")
    private int gasLimit = 0;

    @JSONField(name = "expire_duration")
    private Short expireDuration = 0;

    @JSONField(name = "fire_timestamp")
    private Long fireTimestamp = Long.valueOf(0);

    @JSONField(name = "trans_random_nounce")
    private Short transRandomNounce = 0;

    @JSONField(name = "hash_work_proof")
    private Short hashWorkProof = 0;

    @JSONField(name = "last_unit_hight")
    private Long lastUnitHight = Long.valueOf(0);

    @JSONField(name = "last_unit_hash")
    private Long lastUnitHash = Long.valueOf(0);

    @JSONField(name = "last_trans_nonce")
    private Long lastTransNonce = Long.valueOf(0);

    @JSONField(name = "last_trans_hash")
    private String lastTransHash = "";

    @JSONField(name = "parent_account")
    private String parentAccount = "";

    @JSONField(name = "authority_keys")
    private String authorityKeys = "";

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.shortToBytes(transactionType)
                .shortToBytes(transactionLen)
                .int32ToBytes(version)
                .longToBytes(toAccountId)
                .longToBytes(fromAccountId)
                .shortToBytes(toNetworkId)
                .shortToBytes(fromNetworkId)
                .int32ToBytes(gasPrice)
                .int32ToBytes(gasLimit)
                .shortToBytes(expireDuration)
                .longToBytes(fireTimestamp)
                .int32ToBytes(transRandomNounce)
                .int32ToBytes(hashWorkProof)
                .longToBytes(lastUnitHight)
                .longToBytes(lastUnitHash)
                .longToBytes(lastTransNonce)
                .hexToBytes(lastTransHash.replaceFirst("0x", ""))
                .stringToBytes(parentAccount)
                .stringToBytes(authorityKeys);
        return bufferUtils.pack();
    }

    public Short getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Short transactionType) {
        this.transactionType = transactionType;
    }

    public Short getTransactionLen() {
        return transactionLen;
    }

    public void setTransactionLen(Short transactionLen) {
        this.transactionLen = transactionLen;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Short getToNetworkId() {
        return toNetworkId;
    }

    public void setToNetworkId(Short toNetworkId) {
        this.toNetworkId = toNetworkId;
    }

    public Short getFromNetworkId() {
        return fromNetworkId;
    }

    public void setFromNetworkId(Short fromNetworkId) {
        this.fromNetworkId = fromNetworkId;
    }

    public Long getToAccountId() {
        return toAccountId;
    }

    public void setToAccountId(Long toAccountId) {
        this.toAccountId = toAccountId;
    }

    public Long getFromAccountId() {
        return fromAccountId;
    }

    public void setFromAccountId(Long fromAccountId) {
        this.fromAccountId = fromAccountId;
    }

    public int getGasPrice() {
        return gasPrice;
    }

    public void setGasPrice(int gasPrice) {
        this.gasPrice = gasPrice;
    }

    public int getGasLimit() {
        return gasLimit;
    }

    public void setGasLimit(int gasLimit) {
        this.gasLimit = gasLimit;
    }

    public Short getExpireDuration() {
        return expireDuration;
    }

    public void setExpireDuration(Short expireDuration) {
        this.expireDuration = expireDuration;
    }

    public Long getFireTimestamp() {
        return fireTimestamp;
    }

    public void setFireTimestamp(Long fireTimestamp) {
        this.fireTimestamp = fireTimestamp;
    }

    public Short getTransRandomNounce() {
        return transRandomNounce;
    }

    public void setTransRandomNounce(Short transRandomNounce) {
        this.transRandomNounce = transRandomNounce;
    }

    public Short getHashWorkProof() {
        return hashWorkProof;
    }

    public void setHashWorkProof(Short hashWorkProof) {
        this.hashWorkProof = hashWorkProof;
    }

    public Long getLastUnitHight() {
        return lastUnitHight;
    }

    public void setLastUnitHight(Long lastUnitHight) {
        this.lastUnitHight = lastUnitHight;
    }

    public Long getLastUnitHash() {
        return lastUnitHash;
    }

    public void setLastUnitHash(Long lastUnitHash) {
        this.lastUnitHash = lastUnitHash;
    }

    public Long getLastTransNonce() {
        return lastTransNonce;
    }

    public void setLastTransNonce(Long lastTransNonce) {
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

    public String getAuthorityKeys() {
        return authorityKeys;
    }

    public void setAuthorityKeys(String authorityKeys) {
        this.authorityKeys = authorityKeys;
    }
}
