package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class TableBlockResponse {
    @JSONField(name = "value")
    private Block<TableBlock> value;
}
