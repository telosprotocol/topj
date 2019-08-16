package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;
import java.nio.ByteBuffer;

public class XAction {

    @JSONField(name = "action_hash")
    private Integer actionHash = 0;

    @JSONField(name = "action_type")
    private Short actionType = 0;

    @JSONField(name = "action_size")
    private Short actionSize = 0;

    @JSONField(name = "account_addr")
    private String accountAddr = "";

    @JSONField(name = "action_name")
    private String actionName = "";

    @JSONField(name = "action_param")
    private String actionParam;

    @JSONField(name = "action_authorization")
    private String actionAuthorization = "";

    public byte[] serialize_write(){
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.int32ToBytes(actionHash)
                .shortToBytes(actionType)
                .shortToBytes(actionSize)
                .stringToBytes(accountAddr)
                .stringToBytes(actionName);
        byte[] actionParamBytes = StringUtils.hexToByte(actionParam.replaceFirst("0x", ""));
        if (actionParamBytes.length == 0) {
            bufferUtils.stringToBytes("").stringToBytes(actionAuthorization);
        } else {
            bufferUtils.int32ToBytes(actionParamBytes.length).bytesArray(actionParamBytes).stringToBytes(actionAuthorization);
        }
        return bufferUtils.pack();
    }

    public Integer getActionHash() {
        return actionHash;
    }

    public void setActionHash(Integer actionHash) {
        this.actionHash = actionHash;
    }

    public Short getActionType() {
        return actionType;
    }

    public void setActionType(Short actionType) {
        this.actionType = actionType;
    }

    public Short getActionSize() {
        return actionSize;
    }

    public void setActionSize(Short actionSize) {
        this.actionSize = actionSize;
    }

    public String getAccountAddr() {
        return accountAddr;
    }

    public void setAccountAddr(String accountAddr) {
        this.accountAddr = accountAddr;
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

    public String getActionAuthorization() {
        return actionAuthorization;
    }

    public void setActionAuthorization(String actionAuthorization) {
        this.actionAuthorization = actionAuthorization;
    }
}
