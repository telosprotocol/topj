package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class LightUnit{

    @JSONField(name = "txs")
    private List<TxObj> txs;

    public List<TxObj> getTxs() {
        return txs;
    }

    public void setTxs(List<TxObj> txs) {
        this.txs = txs;
    }
}

