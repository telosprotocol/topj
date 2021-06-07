package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class LightUnitInput {

    @JSONField(name = "is_contract_create")
    private Integer isContractCreate;

    @JSONField(name = "sender_tx_locked_gas")
    private BigInteger senderTxLockedGas;

    @JSONField(name = "tx_consensus_phase")
    private String txConsensusPhase;

    public Integer getIsContractCreate() {
        return isContractCreate;
    }

    public void setIsContractCreate(Integer isContractCreate) {
        this.isContractCreate = isContractCreate;
    }

    public BigInteger getSenderTxLockedGas() {
        return senderTxLockedGas;
    }

    public void setSenderTxLockedGas(BigInteger senderTxLockedGas) {
        this.senderTxLockedGas = senderTxLockedGas;
    }

    public String getTxConsensusPhase() {
        return txConsensusPhase;
    }

    public void setTxConsensusPhase(String txConsensusPhase) {
        this.txConsensusPhase = txConsensusPhase;
    }
}
