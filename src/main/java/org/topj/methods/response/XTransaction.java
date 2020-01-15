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

public class XTransaction extends XTransactionHeader {

    @JSONField(name = "source_action")
    private XAction sourceAction;

    @JSONField(name = "target_action")
    private XAction targetAction;

    @JSONField(name = "transaction_hash")
    private String transactionHash;

    private String xx64Hash;

    @JSONField(name = "authorization")
    private String authorization;

    @JSONField(name = "public_key")
    private String publicKey;

    @JSONField(name = "confirm_unit_height")
    private BigInteger confirmUnitHeight;

    @JSONField(name = "edge_nodeid")
    private String edgeNodeId;

    @JSONField(name = "flag")
    private BigInteger flag;

    @JSONField(name = "recv_unit_height")
    private BigInteger recvUnitHeight;

    @JSONField(name = "send_unit_height")
    private BigInteger sendUnitHeight;

    @JSONField(name = "tx_exec_status")
    private Integer txExecStatus;

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        byte[] superBytes = super.serialize_write();
        byte[] sourceActionBytes = sourceAction.serialize_write();
        byte[] targetActionBytes = targetAction.serialize_write();
        bufferUtils.bytesArray(superBytes).bytesArray(sourceActionBytes).bytesArray(targetActionBytes);
        return bufferUtils.pack();
    }

    public byte[] set_digest() throws NoSuchAlgorithmException {
        byte[] dataBytes = serialize_write();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes);
        byte[] hashResultBytes = md.digest();
        transactionHash = "0x" + StringUtils.bytesToHex(hashResultBytes);

        XXHashFactory factory = XXHashFactory.fastestInstance();
        XXHash64 xxHash641 = factory.hash64();
        Long result = xxHash641.hash(hashResultBytes, 0, hashResultBytes.length, 0);
        xx64Hash = "0x" + Long.toHexString(result);
        return hashResultBytes;
    }

    /**
     * 判断该交易是否成功
     * @return (boolean) isSuccess
     */
    public Boolean isSuccess() {
        return txExecStatus != null && txExecStatus == 1;
    }

    public XAction getSourceAction() {
        return sourceAction;
    }

    public void setSourceAction(XAction sourceAction) {
        this.sourceAction = sourceAction;
    }

    public XAction getTargetAction() {
        return targetAction;
    }

    public void setTargetAction(XAction targetAction) {
        this.targetAction = targetAction;
    }

    public String getTransactionHash() {
        return transactionHash;
    }

    public void setTransactionHash(String transactionHash) {
        this.transactionHash = transactionHash;
    }

    public String getXx64Hash() {
        return xx64Hash;
    }

    public void setXx64Hash(String xx64Hash) {
        this.xx64Hash = xx64Hash;
    }

    public String getAuthorization() {
        return authorization;
    }

    public void setAuthorization(String authorization) {
        this.authorization = authorization;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public BigInteger getConfirmUnitHeight() {
        return confirmUnitHeight;
    }

    public void setConfirmUnitHeight(BigInteger confirmUnitHeight) {
        this.confirmUnitHeight = confirmUnitHeight;
    }

    public String getEdgeNodeId() {
        return edgeNodeId;
    }

    public void setEdgeNodeId(String edgeNodeId) {
        this.edgeNodeId = edgeNodeId;
    }

    public BigInteger getFlag() {
        return flag;
    }

    public void setFlag(BigInteger flag) {
        this.flag = flag;
    }

    public BigInteger getRecvUnitHeight() {
        return recvUnitHeight;
    }

    public void setRecvUnitHeight(BigInteger recvUnitHeight) {
        this.recvUnitHeight = recvUnitHeight;
    }

    public BigInteger getSendUnitHeight() {
        return sendUnitHeight;
    }

    public void setSendUnitHeight(BigInteger sendUnitHeight) {
        this.sendUnitHeight = sendUnitHeight;
    }

    public Integer getTxExecStatus() {
        return txExecStatus;
    }

    public void setTxExecStatus(Integer txExecStatus) {
        this.txExecStatus = txExecStatus;
    }
}
