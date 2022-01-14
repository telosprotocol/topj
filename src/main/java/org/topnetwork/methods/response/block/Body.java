package org.topnetwork.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

    public class Body {
        // only table block get this
        @JSONField(name = "tableblock")
        private TableBlock tableBlock;

        // only full unit block get this
        @JSONField(name = "fullunit")
        private FullUnit fullUnit;

        // only light unit block get this
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

        public TableBlock getTableBlock() {
            return tableBlock;
        }

        public void setTableBlock(TableBlock tableBlock) {
            this.tableBlock = tableBlock;
        }
    }
