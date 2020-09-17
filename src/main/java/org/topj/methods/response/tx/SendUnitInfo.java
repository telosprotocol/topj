package org.topj.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class SendUnitInfo {

    @JSONField(name = "height")
    private BigInteger height = BigInteger.ZERO;

    @JSONField(name = "tx_fee")
    private BigInteger txFee = BigInteger.ZERO;

    @JSONField(name = "unit_hash")
    private String unitHash = "";

    @JSONField(name = "used_deposit")
    private BigInteger usedDeposit = BigInteger.ZERO;

    @JSONField(name = "used_disk")
    private BigInteger usedDisk = BigInteger.ZERO;

    @JSONField(name = "used_gas")
    private BigInteger usedGas = BigInteger.ZERO;

    public BigInteger getHeight() {
        return height;
    }

    public void setHeight(BigInteger height) {
        this.height = height;
    }

    public BigInteger getTxFee() {
        return txFee;
    }

    public void setTxFee(BigInteger txFee) {
        this.txFee = txFee;
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
