package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class TableBlockResponse {
    @JSONField(name = "value")
    private Block value;

    @JSONField(name = "result")
    private String result;

    public Block getValue() {
        return value;
    }

    public void setValue(Block value) {
        this.value = value;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
