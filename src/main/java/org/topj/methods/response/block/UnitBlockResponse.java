package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class UnitBlockResponse {
    @JSONField(name = "value")
    private Block<UnitBlock> value;

    public Block<UnitBlock> getValue() {
        return value;
    }

    public void setValue(Block<UnitBlock> value) {
        this.value = value;
    }
}
