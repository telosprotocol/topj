package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class XTransaction extends XTransactionHeader {

    @JSONField(name = "source_action")
    private XAction sourceAction;

    @JSONField(name = "target_action")
    private XAction targetAction;

    @JSONField(name = "transaction_hash")
    private String transactionHash;

    @JSONField(name = "authorization")
    private String authorization;

    @JSONField(name = "public_key")
    private String publicKey;

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
        return hashResultBytes;
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
}
