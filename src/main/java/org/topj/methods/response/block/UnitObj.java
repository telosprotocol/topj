package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.Map;

public class UnitObj {

    @JSONField(name = "lightunit_input")
    private Map<String, LightUnitInput> lightunitInput;

    @JSONField(name = "lightunit_state")
    private LightUnitState lightunitState;

    @JSONField(name = "unit_height")
    private BigInteger unitHeight;

    public Map<String, LightUnitInput> getLightunitInput() {
        return lightunitInput;
    }

    public void setLightunitInput(Map<String, LightUnitInput> lightunitInput) {
        this.lightunitInput = lightunitInput;
    }

    public LightUnitState getLightunitState() {
        return lightunitState;
    }

    public void setLightunitState(LightUnitState lightunitState) {
        this.lightunitState = lightunitState;
    }

    public BigInteger getUnitHeight() {
        return unitHeight;
    }

    public void setUnitHeight(BigInteger unitHeight) {
        this.unitHeight = unitHeight;
    }
}
