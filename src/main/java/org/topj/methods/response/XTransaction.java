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
import net.jpountz.xxhash.XXHash64;
import net.jpountz.xxhash.XXHashFactory;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XTransaction {

    @JSONField(name = "authorization")
    private String authorization = "";

    @JSONField(name = "challenge_proof")
    private String challengeProof = "";

    @JSONField(name = "ext")
    private String ext = "";

    @JSONField(name = "from_ledger_id")
    private BigInteger fromLedgerId = BigInteger.ZERO;

    @JSONField(name = "last_tx_hash")
    private String lastTxHash = "";

    @JSONField(name = "last_tx_nonce")
    private BigInteger lastTxNonce = BigInteger.ZERO;

    @JSONField(name = "note")
    private String note = "";

    @JSONField(name = "send_timestamp")
    private BigInteger sendTimestamp = BigInteger.ZERO;

    @JSONField(name = "to_ledger_id")
    private BigInteger toLedgerId = BigInteger.ZERO;

    @JSONField(name = "tx_deposit")
    private BigInteger txDeposit = BigInteger.ZERO;

    @JSONField(name = "tx_expire_duration")
    private BigInteger txExpireDuration = BigInteger.ZERO;

    @JSONField(name = "tx_hash")
    private String txHash = "";

    @JSONField(name = "tx_len")
    private BigInteger txLen = BigInteger.ZERO;

    @JSONField(name = "tx_random_nonce")
    private BigInteger txRandomNonce = BigInteger.ZERO;

    @JSONField(name = "tx_structure_version")
    private BigInteger txStructureVersion = BigInteger.ZERO;

    @JSONField(name = "tx_type")
    private BigInteger txType = BigInteger.ZERO;

    @JSONField(name = "tx_action")
    private XAction xAction;

    @JSONField(name = "receiver_action")
    private ReceiverAction receiverAction;
    @JSONField(name = "sender_action")
    private SenderAction senderAction;

    private String xx64Hash;

    public byte[] sw(){
        BufferUtils bufferUtils = new BufferUtils();
        return bufferUtils.pack();
    }

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.BigIntToBytes(txType, 16)
                .BigIntToBytes(txLen, 16)
                .BigIntToBytes(txStructureVersion, 32)
                .BigIntToBytes(toLedgerId, 16)
                .BigIntToBytes(fromLedgerId, 16)
                .BigIntToBytes(txDeposit, 32)
                .BigIntToBytes(txExpireDuration, 16)
                .BigIntToBytes(sendTimestamp, 64)
                .BigIntToBytes(txRandomNonce, 32)
                .BigIntToBytes(lastTxNonce, 64)
                .hexToBytes(lastTxHash.replaceFirst("0x", ""))
//                .BigIntToBytes(new BigInteger("17791961111430638837"), 64)
                .stringToBytes(challengeProof)
                .stringToBytes(ext)
                .stringToBytes(note);
        byte[] sourceActionBytes = xAction.getSenderAction().serialize_write();
        byte[] targetActionBytes = xAction.getReceiverAction().serialize_write();
        bufferUtils.bytesArray(sourceActionBytes).bytesArray(targetActionBytes);
        return bufferUtils.pack();
    }

    public byte[] set_digest() throws NoSuchAlgorithmException {
        byte[] dataBytes = serialize_write();
        System.out.println(">>" + StringUtils.bytesToHex(dataBytes));
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes);
        byte[] hashResultBytes = md.digest();
        txHash = "0x" + StringUtils.bytesToHex(hashResultBytes);

        XXHashFactory factory = XXHashFactory.fastestInstance();
        XXHash64 xxHash641 = factory.hash64();
        Long result = xxHash641.hash(hashResultBytes, 0, hashResultBytes.length, 0);
        xx64Hash = "0x" + Long.toHexString(result);
        return hashResultBytes;
    }

    /**
     * 判断该交易是否成功
     * 优先查看confirm_unit_info exec_status返回的结果，若为1表示成功。
     * 不为1时，即失败，若需要更详细的分析，则分两种情况：
     *     1. account为发送方，则查看tx_exec_status和recv_tx_exec_status判断是哪步出错。
     *     2. account为接收方则判断tx_exec_status出错。
     * @return (boolean) isSuccess
     */
    public Boolean isSuccess() {
//        if (confirmUnitInfo == null || confirmUnitInfo.getExecStatus() == null) {
//            return false;
//        }
//        return confirmUnitInfo.getExecStatus() == 1;
        return false;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getChallengeProof() {
        return challengeProof;
    }

    public void setChallengeProof(String challengeProof) {
        this.challengeProof = challengeProof;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public BigInteger getFromLedgerId() {
        return fromLedgerId;
    }

    public void setFromLedgerId(BigInteger fromLedgerId) {
        this.fromLedgerId = fromLedgerId;
    }

    public String getLastTxHash() {
        return lastTxHash;
    }

    public void setLastTxHash(String lastTxHash) {
        this.lastTxHash = lastTxHash;
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

    public BigInteger getSendTimestamp() {
        return sendTimestamp;
    }

    public void setSendTimestamp(BigInteger sendTimestamp) {
        this.sendTimestamp = sendTimestamp;
    }

    public BigInteger getToLedgerId() {
        return toLedgerId;
    }

    public void setToLedgerId(BigInteger toLedgerId) {
        this.toLedgerId = toLedgerId;
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

    public BigInteger getTxRandomNonce() {
        return txRandomNonce;
    }

    public void setTxRandomNonce(BigInteger txRandomNonce) {
        this.txRandomNonce = txRandomNonce;
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

    public XAction getxAction() {
        return xAction;
    }

    public void setxAction(XAction xAction) {
        this.xAction = xAction;
    }

    public ReceiverAction getReceiverAction() {
        return receiverAction;
    }

    public void setReceiverAction(ReceiverAction receiverAction) {
        this.receiverAction = receiverAction;
    }

    public SenderAction getSenderAction() {
        return senderAction;
    }

    public void setSenderAction(SenderAction senderAction) {
        this.senderAction = senderAction;
    }

    public String getXx64Hash() {
        return xx64Hash;
    }

    public void setXx64Hash(String xx64Hash) {
        this.xx64Hash = xx64Hash;
    }
}
