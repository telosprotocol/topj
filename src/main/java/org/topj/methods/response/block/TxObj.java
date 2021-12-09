package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class TxObj {

    @JSONField(name = "tx_consensus_phase")
    private String txConsensusPhase;

    @JSONField(name = "tx_hash")
    private String txHash;

    public String getTxConsensusPhase() {
        return txConsensusPhase;
    }

    public void setTxConsensusPhase(String txConsensusPhase) {
        this.txConsensusPhase = txConsensusPhase;
    }

    public String getTxHash() {
        return txHash;
    }

    public void setTxHash(String txHash) {
        this.txHash = txHash;
    }
}
