package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class UnitTx {
    @JSONField(name = "send_tx_lock_gas")
    private BigInteger sendTxLockGas;
    @JSONField(name = "tx_consensus_phase")
    private String txConsensusPhase;
    @JSONField(name = "tx_exec_status")
    private String txExecStatus;
    @JSONField(name = "used_disk")
    private Integer usedDisk;
    @JSONField(name = "used_gas")
    private Integer usedGas;
    @JSONField(name = "used_tx_deposit")
    private Integer usedTxDeposit;

    public BigInteger getSendTxLockGas() {
        return sendTxLockGas;
    }

    public void setSendTxLockGas(BigInteger sendTxLockGas) {
        this.sendTxLockGas = sendTxLockGas;
    }

    public String getTxConsensusPhase() {
        return txConsensusPhase;
    }

    public void setTxConsensusPhase(String txConsensusPhase) {
        this.txConsensusPhase = txConsensusPhase;
    }

    public String getTxExecStatus() {
        return txExecStatus;
    }

    public void setTxExecStatus(String txExecStatus) {
        this.txExecStatus = txExecStatus;
    }

    public Integer getUsedDisk() {
        return usedDisk;
    }

    public void setUsedDisk(Integer usedDisk) {
        this.usedDisk = usedDisk;
    }

    public Integer getUsedGas() {
        return usedGas;
    }

    public void setUsedGas(Integer usedGas) {
        this.usedGas = usedGas;
    }

    public Integer getUsedTxDeposit() {
        return usedTxDeposit;
    }

    public void setUsedTxDeposit(Integer usedTxDeposit) {
        this.usedTxDeposit = usedTxDeposit;
    }
}
