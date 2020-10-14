package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class StandBysDetail {

    @JSONField(name = "node_sign_key")
    private String nodeSignKey;

    @JSONField(name = "node_account_address")
    private String nodeAccountAddress;

    @JSONField(name = "program_version")
    private String programVersion;

    @JSONField(name = "stake")
    private BigInteger stake;

    public String getNodeSignKey() {
        return nodeSignKey;
    }

    public void setNodeSignKey(String nodeSignKey) {
        this.nodeSignKey = nodeSignKey;
    }

    public String getNodeAccountAddress() {
        return nodeAccountAddress;
    }

    public void setNodeAccountAddress(String nodeAccountAddress) {
        this.nodeAccountAddress = nodeAccountAddress;
    }

    public String getProgramVersion() {
        return programVersion;
    }

    public void setProgramVersion(String programVersion) {
        this.programVersion = programVersion;
    }

    public BigInteger getStake() {
        return stake;
    }

    public void setStake(BigInteger stake) {
        this.stake = stake;
    }
}
