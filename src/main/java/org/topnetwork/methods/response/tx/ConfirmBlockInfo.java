package org.topnetwork.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class ConfirmBlockInfo {

    @JSONField(name = "account")
    private String account = "";

    @JSONField(name = "exec_status")
    private String execStatus = "";

    @JSONField(name = "height")
    private BigInteger height = BigInteger.ZERO;

    @JSONField(name = "recv_tx_exec_status")
    private String recvTxExecStatus = "";

    @JSONField(name = "used_deposit")
    private BigInteger usedDeposit = BigInteger.ZERO;

    @JSONField(name = "used_gas")
    private BigInteger usedGas = BigInteger.ZERO;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

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

    public String getRecvTxExecStatus() {
        return recvTxExecStatus;
    }

    public void setRecvTxExecStatus(String recvTxExecStatus) {
        this.recvTxExecStatus = recvTxExecStatus;
    }

    public BigInteger getUsedDeposit() {
        return usedDeposit;
    }

    public void setUsedDeposit(BigInteger usedDeposit) {
        this.usedDeposit = usedDeposit;
    }

    public BigInteger getUsedGas() {
        return usedGas;
    }

    public void setUsedGas(BigInteger usedGas) {
        this.usedGas = usedGas;
    }
}
