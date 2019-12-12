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
    private BigInteger actionHash = BigInteger.ZERO;

    @JSONField(name = "action_type")
    private BigInteger actionType = BigInteger.ZERO;

    @JSONField(name = "action_size")
    private BigInteger actionSize = BigInteger.ZERO;

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
        bufferUtils.BigIntToBytes(actionHash, 32)
                .BigIntToBytes(actionType, 16)
                .BigIntToBytes(actionSize, 16)
                .stringToBytes(accountAddr)
                .stringToBytes(actionName);
        byte[] actionParamBytes = StringUtils.hexToByte(actionParam.replaceFirst("0x", ""));
        if (actionParamBytes.length == 0) {
            bufferUtils.stringToBytes("");
        } else {
            bufferUtils.BigIntToBytes(BigInteger.valueOf(actionParamBytes.length), 32).bytesArray(actionParamBytes);
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
        bufferUtils.BigIntToBytes(actionHash, 32)
                .BigIntToBytes(actionType, 16)
                .BigIntToBytes(actionSize, 16)
                .stringToBytes(accountAddr)
                .stringToBytes(actionName);
        byte[] actionParamBytes = StringUtils.hexToByte(actionParam.replaceFirst("0x", ""));
        bufferUtils.int32ToBytes(actionParamBytes.length).bytesArray(actionParamBytes);
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
