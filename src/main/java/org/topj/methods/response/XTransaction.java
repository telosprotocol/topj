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

    @JSONField(name = "confirm_unit_info")
    private XUnitInfo confirmUnitInfo;

    @JSONField(name = "send_unit_info")
    private XUnitInfo sendUnitInfo;

    @JSONField(name = "recv_unit_info")
    private XUnitInfo recvUnitInfo;

    @JSONField(name = "edge_nodeid")
    private String edgeNodeId;

    @JSONField(name = "flag")
    private BigInteger flag;

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
     * 优先查看confirm_unit_info exec_status返回的结果，若为1表示成功。
     * 不为1时，即失败，若需要更详细的分析，则分两种情况：
     *     1. account为发送方，则查看tx_exec_status和recv_tx_exec_status判断是哪步出错。
     *     2. account为接收方则判断tx_exec_status出错。
     * @return (boolean) isSuccess
     */
    public Boolean isSuccess() {
        if (confirmUnitInfo == null || confirmUnitInfo.getExecStatus() == null) {
            return false;
        }
        return confirmUnitInfo.getExecStatus() == 1;
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

    public XUnitInfo getConfirmUnitInfo() {
        return confirmUnitInfo;
    }

    public void setConfirmUnitInfo(XUnitInfo confirmUnitInfo) {
        this.confirmUnitInfo = confirmUnitInfo;
    }

    public XUnitInfo getSendUnitInfo() {
        return sendUnitInfo;
    }

    public void setSendUnitInfo(XUnitInfo sendUnitInfo) {
        this.sendUnitInfo = sendUnitInfo;
    }

    public XUnitInfo getRecvUnitInfo() {
        return recvUnitInfo;
    }

    public void setRecvUnitInfo(XUnitInfo recvUnitInfo) {
        this.recvUnitInfo = recvUnitInfo;
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
}
