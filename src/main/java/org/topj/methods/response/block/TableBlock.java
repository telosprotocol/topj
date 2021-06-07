package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Map;

public class TableBlock {

    @JSONField(name = "units")
    private Map<String, UnitObj> units;

    public Map<String, UnitObj> getUnits() {
        return units;
    }

    public void setUnits(Map<String, UnitObj> units) {
        this.units = units;
    }
}
