package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class ResponseBase <T> {

    @JSONField(name="sequence_id")
    private String sequenceId;

    @JSONField(name="errno")
    private Integer errNo;

    @JSONField(name="errmsg")
    private String errMsg;

    @JSONField(name="data")
    private T data;

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public Integer getErrNo() {
        return errNo;
    }

    public void setErrNo(Integer errNo) {
        this.errNo = errNo;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
