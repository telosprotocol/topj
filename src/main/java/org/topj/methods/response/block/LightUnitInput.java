package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;
import java.util.Map;

public class LightUnitInput {
    @JSONField(name = "txs")
    private List<Map<String, UnitTx>> txs;

    public List<Map<String, UnitTx>> getTxs() {
        return txs;
    }

    public void setTxs(List<Map<String, UnitTx>> txs) {
        this.txs = txs;
    }
}
