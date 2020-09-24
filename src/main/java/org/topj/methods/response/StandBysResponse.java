package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class StandBysResponse {

    @JSONField(name = "arc")
    private List<NodeBaseInfo> arc;

    @JSONField(name = "auditor")
    private List<NodeBaseInfo> auditor;

    @JSONField(name = "edge")
    private List<NodeBaseInfo> edge;

    @JSONField(name = "rec")
    private List<NodeBaseInfo> rec;

    @JSONField(name = "validator")
    private List<NodeBaseInfo> validator;

    @JSONField(name = "zec")
    private List<NodeBaseInfo> zec;

    public List<NodeBaseInfo> getArc() {
        return arc;
    }

    public void setArc(List<NodeBaseInfo> arc) {
        this.arc = arc;
    }

    public List<NodeBaseInfo> getAuditor() {
        return auditor;
    }

    public void setAuditor(List<NodeBaseInfo> auditor) {
        this.auditor = auditor;
    }

    public List<NodeBaseInfo> getEdge() {
        return edge;
    }

    public void setEdge(List<NodeBaseInfo> edge) {
        this.edge = edge;
    }

    public List<NodeBaseInfo> getRec() {
        return rec;
    }

    public void setRec(List<NodeBaseInfo> rec) {
        this.rec = rec;
    }

    public List<NodeBaseInfo> getValidator() {
        return validator;
    }

    public void setValidator(List<NodeBaseInfo> validator) {
        this.validator = validator;
    }

    public List<NodeBaseInfo> getZec() {
        return zec;
    }

    public void setZec(List<NodeBaseInfo> zec) {
        this.zec = zec;
    }
}
