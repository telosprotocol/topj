package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class TableBlock {

    @JSONField(name = "txs")
    private List<TxObj> txs;

    @JSONField(name = "units")
    private List<UnitObj> units;

    public List<TxObj> getTxs() {
        return txs;
    }

    public void setTxs(List<TxObj> txs) {
        this.txs = txs;
    }

    public List<UnitObj> getUnits() {
        return units;
    }

    public void setUnits(List<UnitObj> units) {
        this.units = units;
    }
}
