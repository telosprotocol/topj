package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class UnitBlock {

    @JSONField(name = "fullunit")
    private FullUnit fullUnit;

    @JSONField(name = "lightunit")
    private LightUnit lightUnit;

    public FullUnit getFullUnit() {
        return fullUnit;
    }

    public void setFullUnit(FullUnit fullUnit) {
        this.fullUnit = fullUnit;
    }

    public LightUnit getLightUnit() {
        return lightUnit;
    }

    public void setLightUnit(LightUnit lightUnit) {
        this.lightUnit = lightUnit;
    }
}

