package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class LightUnit{

    @JSONField(name = "lightunit_input")
    private LightUnitInput lightUnitInput;

    @JSONField(name = "lightunit_state")
    private LightUnitState lightUnitState;

    public LightUnitInput getLightUnitInput() {
        return lightUnitInput;
    }

    public void setLightUnitInput(LightUnitInput lightUnitInput) {
        this.lightUnitInput = lightUnitInput;
    }

    public LightUnitState getLightUnitState() {
        return lightUnitState;
    }

    public void setLightUnitState(LightUnitState lightUnitState) {
        this.lightUnitState = lightUnitState;
    }
}

