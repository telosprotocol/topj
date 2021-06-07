package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class TableBlockResponse {
    @JSONField(name = "value")
    private Block value;

    public Block getValue() {
        return value;
    }

    public void setValue(Block value) {
        this.value = value;
    }
}
