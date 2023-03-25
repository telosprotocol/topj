package org.topnetwork.methods.response;

import com.alibaba.fastjson.annotation.JSONField;
import org.topnetwork.utils.BufferUtils;
import org.topnetwork.utils.StringUtils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ReceiverAction {
    @JSONField(name = "action_hash")
    private BigInteger actionHash = BigInteger.ZERO;

    @JSONField(name = "action_type")
    private BigInteger actionType = BigInteger.ZERO;

    @JSONField(name = "action_size")
    private BigInteger actionSize = BigInteger.ZERO;

    @JSONField(name = "tx_receiver_account_addr")
    private String txReceiverAccountAddr = "";

    @JSONField(name = "action_name")
    private String actionName = "";

    @JSONField(name = "action_param")
    private String actionParam;

    @JSONField(name = "action_ext")
    private String actionExt = "";

    @JSONField(name = "action_authorization")
    private String actionAuthorization = "";

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.BigIntToBytes(actionHash, 32)
                .BigIntToBytes(actionType, 16)
                .BigIntToBytes(actionSize, 16)
                .stringToBytes(txReceiverAccountAddr)
                .stringToBytes(actionName);
        byte[] actionParamBytes = StringUtils.hexToByte(actionParam.replaceFirst("0x", ""));
        if (actionParamBytes.length == 0) {
            bufferUtils.stringToBytes("");
        } else {
            bufferUtils.BigIntToBytes(BigInteger.valueOf(actionParamBytes.length), 32).bytesArray(actionParamBytes);
        }

        bufferUtils.stringToBytes(actionExt);

//        if (actionAuthorization.isEmpty()){
            bufferUtils.stringToBytes(actionAuthorization);
//        } else {
//            byte[] actionAuthBytes = StringUtils.hexToByte(actionAuthorization.replaceFirst("0x", ""));
//            bufferUtils.int32ToBytes(actionAuthBytes.length).bytesArray(actionAuthBytes);
//        }
        return bufferUtils.pack();
    }

    public byte[] set_digest() throws NoSuchAlgorithmException {
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.BigIntToBytes(actionHash, 32)
                .BigIntToBytes(actionType, 16)
                .BigIntToBytes(actionSize, 16)
                .stringToBytes(txReceiverAccountAddr)
                .stringToBytes(actionName);
        byte[] actionParamBytes = StringUtils.hexToByte(actionParam.replaceFirst("0x", ""));
        bufferUtils.int32ToBytes(actionParamBytes.length).bytesArray(actionParamBytes);

        bufferUtils.stringToBytes(actionExt);

        byte[] dataBytes = bufferUtils.pack();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes);
        return md.digest();
    }

    public BigInteger getActionHash() {
        return actionHash;
    }

    public void setActionHash(BigInteger actionHash) {
        this.actionHash = actionHash;
    }

    public BigInteger getActionType() {
        return actionType;
    }

    public void setActionType(BigInteger actionType) {
        this.actionType = actionType;
    }

    public BigInteger getActionSize() {
        return actionSize;
    }

    public void setActionSize(BigInteger actionSize) {
        this.actionSize = actionSize;
    }

    public String getTxReceiverAccountAddr() {
        return txReceiverAccountAddr;
    }

    public void setTxReceiverAccountAddr(String txReceiverAccountAddr) {
        this.txReceiverAccountAddr = txReceiverAccountAddr;
    }

    public String getActionName() {
        return actionName;
    }

    public void setActionName(String actionName) {
        this.actionName = actionName;
    }

    public String getActionParam() {
        return actionParam;
    }

    public void setActionParam(String actionParam) {
        this.actionParam = actionParam;
    }

    public String getActionExt() {
        return actionExt;
    }

    public void setActionExt(String actionExt) {
        this.actionExt = actionExt;
    }

    public String getActionAuthorization() {
        return actionAuthorization;
    }

    public void setActionAuthorization(String actionAuthorization) {
        this.actionAuthorization = actionAuthorization;
    }
}
