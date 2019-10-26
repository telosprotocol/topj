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
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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
            bufferUtils.stringToBytes("");
        } else {
            bufferUtils.int32ToBytes(actionParamBytes.length).bytesArray(actionParamBytes);
        }
        if (actionAuthorization.isEmpty()){
            bufferUtils.stringToBytes(actionAuthorization);
        } else {
            byte[] actionAuthBytes = StringUtils.hexToByte(actionAuthorization.replaceFirst("0x", ""));
            bufferUtils.int32ToBytes(actionAuthBytes.length).bytesArray(actionAuthBytes);
        }
        return bufferUtils.pack();
    }

    public byte[] set_digest() throws NoSuchAlgorithmException {
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.int32ToBytes(actionHash)
                .shortToBytes(actionType)
                .shortToBytes(actionSize)
                .stringToBytes(accountAddr)
                .stringToBytes(actionName);
        byte[] actionParamBytes = StringUtils.hexToByte(actionParam.replaceFirst("0x", ""));
        bufferUtils.int32ToBytes(actionParamBytes.length).bytesArray(actionParamBytes);
        byte[] dataBytes = bufferUtils.pack();
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(dataBytes);
        return md.digest();
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
