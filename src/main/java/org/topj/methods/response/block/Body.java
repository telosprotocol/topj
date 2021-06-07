package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

    public class Body {
        @JSONField(name = "tableblock")
        private TableBlock tableblock;

    public TableBlock getTableblock() {
        return tableblock;
    }

    public void setTableblock(TableBlock tableblock) {
        this.tableblock = tableblock;
    }
}
