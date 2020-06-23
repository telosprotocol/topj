package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class XUnitInfo {

    @JSONField(name = "tx_exec_status")
    private Integer txExecStatus;

    @JSONField(name = "recv_tx_exec_status")
    private Integer recvTxExecStatus;

    @JSONField(name = "exec_status")
    private Integer execStatus;

    @JSONField(name = "height")
    private BigInteger height;

    @JSONField(name = "unit_hash")
    private String unitHash;

    @JSONField(name = "used_deposit")
    private BigInteger usedDeposit;

    @JSONField(name = "used_disk")
    private BigInteger usedDisk;

    @JSONField(name = "used_tgas")
    private BigInteger usedDTgas;

    public String getUnitHash() {
        return unitHash;
    }

    public void setUnitHash(String unitHash) {
        this.unitHash = unitHash;
    }

    public Integer getTxExecStatus() {
        return txExecStatus;
    }

    public void setTxExecStatus(Integer txExecStatus) {
        this.txExecStatus = txExecStatus;
    }

    public Integer getRecvTxExecStatus() {
        return recvTxExecStatus;
    }

    public void setRecvTxExecStatus(Integer recvTxExecStatus) {
        this.recvTxExecStatus = recvTxExecStatus;
    }

    public Integer getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(Integer execStatus) {
        this.execStatus = execStatus;
    }

    public BigInteger getHeight() {
        return height;
    }

    public void setHeight(BigInteger height) {
        this.height = height;
    }

    public BigInteger getUsedDeposit() {
        return usedDeposit;
    }

    public void setUsedDeposit(BigInteger usedDeposit) {
        this.usedDeposit = usedDeposit;
    }

    public BigInteger getUsedDisk() {
        return usedDisk;
    }

    public void setUsedDisk(BigInteger usedDisk) {
        this.usedDisk = usedDisk;
    }

    public BigInteger getUsedDTgas() {
        return usedDTgas;
    }

    public void setUsedDTgas(BigInteger usedDTgas) {
        this.usedDTgas = usedDTgas;
    }
}
