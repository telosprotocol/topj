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
import org.topj.methods.property.XTransactionType;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XTransaction {

    @JSONField(name = "amount")
    private BigInteger amount = BigInteger.ZERO;

    @JSONField(name = "authorization")
    private String authorization = "";

    @JSONField(name = "edge_nodeid")
    private String edgeNodeId = "";

    @JSONField(name = "ext")
    private String ext = "";

    @JSONField(name = "last_tx_nonce")
    private BigInteger lastTxNonce = BigInteger.ZERO;

    @JSONField(name = "note")
    private String note = "";

    @JSONField(name = "premium_price")
    private BigInteger premiumPrice = BigInteger.ZERO;

    @JSONField(name = "receiver_account")
    private String receiverAccount = "";

    @JSONField(name = "receiver_action_name")
    private String receiverActionName = "";

    @JSONField(name = "receiver_action_param")
    private String receiverActionParam = "";

    @JSONField(name = "send_timestamp")
    private BigInteger sendTimestamp = BigInteger.ZERO;

    @JSONField(name = "sender_account")
    private String senderAccount = "";

    @JSONField(name = "sender_action_name")
    private String senderActionName = "";

    @JSONField(name = "sender_action_param")
    private String senderActionParam = "";

    @JSONField(name = "token_name")
    private String tokenName = "";

    @JSONField(name = "tx_deposit")
    private BigInteger txDeposit = BigInteger.ZERO;

    @JSONField(name = "tx_expire_duration")
    private BigInteger txExpireDuration = BigInteger.ZERO;

    @JSONField(name = "tx_hash")
    private String txHash = "";

    @JSONField(name = "tx_len")
    private BigInteger txLen = BigInteger.ZERO;

    @JSONField(name = "tx_structure_version")
    private BigInteger txStructureVersion = BigInteger.ZERO;

    @JSONField(name = "tx_type")
    private BigInteger txType = BigInteger.ZERO;

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.BigIntToBytes(txType, 16)
                .BigIntToBytes(txExpireDuration, 16)
                .BigIntToBytes(sendTimestamp, 64)
                .stringToBytes(senderAccount)
                .stringToBytes(receiverAccount)
                .stringToBytes(edgeNodeId)
                .BigIntToBytes(amount, 64)
                .stringToBytes(tokenName)
                .BigIntToBytes(lastTxNonce, 64)
                .BigIntToBytes(txDeposit, 32)
                .BigIntToBytes(premiumPrice, 32)
                .stringToBytes(note)
                .stringToBytes(ext);
        if (txType != XTransactionType.Transfer) {
            bufferUtils.stringToBytes(senderActionName);
            byte[] sendParamBytes = StringUtils.hexToByte(senderActionParam.replaceFirst("0x", ""));
            if (sendParamBytes.length == 0) {
                bufferUtils.stringToBytes("");
            } else {
                bufferUtils.BigIntToBytes(BigInteger.valueOf(sendParamBytes.length), 32).bytesArray(sendParamBytes);
            }
            bufferUtils.stringToBytes(receiverActionName);
            byte[] recParamBytes = StringUtils.hexToByte(receiverActionParam.replaceFirst("0x", ""));
            if (recParamBytes.length == 0) {
                bufferUtils.stringToBytes("");
            } else {
                bufferUtils.BigIntToBytes(BigInteger.valueOf(recParamBytes.length), 32).bytesArray(recParamBytes);
            }
        }
        return bufferUtils.pack();
    }

    public byte[] set_digest() throws NoSuchAlgorithmException {
        byte[] dataBytes = serialize_write();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes);
        byte[] hashResultBytes = md.digest();
        txHash = "0x" + StringUtils.bytesToHex(hashResultBytes);
        return hashResultBytes;
    }

    public BigInteger getAmount() {
        return amount;
    }

    public void setAmount(BigInteger amount) {
        this.amount = amount;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getEdgeNodeId() {
        return edgeNodeId;
    }

    public void setEdgeNodeId(String edgeNodeId) {
        this.edgeNodeId = edgeNodeId;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public BigInteger getLastTxNonce() {
        return lastTxNonce;
    }

    public void setLastTxNonce(BigInteger lastTxNonce) {
        this.lastTxNonce = lastTxNonce;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public BigInteger getPremiumPrice() {
        return premiumPrice;
    }

    public void setPremiumPrice(BigInteger premiumPrice) {
        this.premiumPrice = premiumPrice;
    }

    public String getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(String receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public String getReceiverActionName() {
        return receiverActionName;
    }

    public void setReceiverActionName(String receiverActionName) {
        this.receiverActionName = receiverActionName;
    }

    public String getReceiverActionParam() {
        return receiverActionParam;
    }

    public void setReceiverActionParam(String receiverActionParam) {
        this.receiverActionParam = receiverActionParam;
    }

    public BigInteger getSendTimestamp() {
        return sendTimestamp;
    }

    public void setSendTimestamp(BigInteger sendTimestamp) {
        this.sendTimestamp = sendTimestamp;
    }

    public String getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(String senderAccount) {
        this.senderAccount = senderAccount;
    }

    public String getSenderActionName() {
        return senderActionName;
    }

    public void setSenderActionName(String senderActionName) {
        this.senderActionName = senderActionName;
    }

    public String getSenderActionParam() {
        return senderActionParam;
    }

    public void setSenderActionParam(String senderActionParam) {
        this.senderActionParam = senderActionParam;
    }

    public String getTokenName() {
        return tokenName;
    }

    public void setTokenName(String tokenName) {
        this.tokenName = tokenName;
    }

    public BigInteger getTxDeposit() {
        return txDeposit;
    }

    public void setTxDeposit(BigInteger txDeposit) {
        this.txDeposit = txDeposit;
    }

    public BigInteger getTxExpireDuration() {
        return txExpireDuration;
    }

    public void setTxExpireDuration(BigInteger txExpireDuration) {
        this.txExpireDuration = txExpireDuration;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }

    public BigInteger getTxLen() {
        return txLen;
    }

    public void setTxLen(BigInteger txLen) {
        this.txLen = txLen;
    }

    public BigInteger getTxStructureVersion() {
        return txStructureVersion;
    }

    public void setTxStructureVersion(BigInteger txStructureVersion) {
        this.txStructureVersion = txStructureVersion;
    }

    public BigInteger getTxType() {
        return txType;
    }

    public void setTxType(BigInteger txType) {
        this.txType = txType;
    }
}
