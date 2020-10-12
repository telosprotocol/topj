package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class CandidateNodeDetail {

    @JSONField(name = "consensus_public_key")
    private String consensusPublicKey;

    @JSONField(name = "node_id")
    private String nodeId;

    @JSONField(name = "program_version")
    private String programVersion;

    @JSONField(name = "stake")
    private BigInteger stake;

    public String getConsensusPublicKey() {
        return consensusPublicKey;
    }

    public void setConsensusPublicKey(String consensusPublicKey) {
        this.consensusPublicKey = consensusPublicKey;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
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
