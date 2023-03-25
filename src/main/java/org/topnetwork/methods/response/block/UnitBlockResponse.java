package org.topnetwork.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class UnitBlockResponse {
    @JSONField(name = "value")
    private Block value;

    @JSONField(name = "result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Block getValue() {
        return value;
    }

    public void setValue(Block value) {
        this.value = value;
    }
}
