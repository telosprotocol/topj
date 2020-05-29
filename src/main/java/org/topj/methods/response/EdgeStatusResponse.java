package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class EdgeStatusResponse {

    @JSONField
    private Boolean status;

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
