package org.topj.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class ConfirmUnitInfo {

    @JSONField(name = "exec_status")
    private String execStatus = "";

    @JSONField(name = "height")
    private BigInteger height = BigInteger.ZERO;

    @JSONField(name = "tx_exec_status")
    private String txExecStatus = "";

    @JSONField(name = "unit_hash")
    private String unitHash = "";

    @JSONField(name = "used_deposit")
    private BigInteger usedDeposit = BigInteger.ZERO;

    @JSONField(name = "used_disk")
    private BigInteger usedDisk = BigInteger.ZERO;

    @JSONField(name = "used_gas")
    private BigInteger usedGas = BigInteger.ZERO;

    public String getExecStatus() {
        return execStatus;
    }

    public void setExecStatus(String execStatus) {
        this.execStatus = execStatus;
    }

    public BigInteger getHeight() {
        return height;
    }

    public void setHeight(BigInteger height) {
        this.height = height;
    }

    public String getTxExecStatus() {
        return txExecStatus;
    }

    public void setTxExecStatus(String txExecStatus) {
        this.txExecStatus = txExecStatus;
    }

    public String getUnitHash() {
        return unitHash;
    }

    public void setUnitHash(String unitHash) {
        this.unitHash = unitHash;
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

    public BigInteger getUsedGas() {
        return usedGas;
    }

    public void setUsedGas(BigInteger usedGas) {
        this.usedGas = usedGas;
    }
}
