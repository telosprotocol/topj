package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class CandidateNodeResponse {

    @JSONField(name = "arc")
    private List<CandidateNodeDetail> arc;

    @JSONField(name = "auditor")
    private List<CandidateNodeDetail> auditor;

    @JSONField(name = "edge")
    private List<CandidateNodeDetail> edge;

    @JSONField(name = "rec")
    private List<CandidateNodeDetail> rec;

    @JSONField(name = "validator")
    private List<CandidateNodeDetail> validator;

    @JSONField(name = "zec")
    private List<CandidateNodeDetail> zec;

    public List<CandidateNodeDetail> getArc() {
        return arc;
    }

    public void setArc(List<CandidateNodeDetail> arc) {
        this.arc = arc;
    }

    public List<CandidateNodeDetail> getAuditor() {
        return auditor;
    }

    public void setAuditor(List<CandidateNodeDetail> auditor) {
        this.auditor = auditor;
    }

    public List<CandidateNodeDetail> getEdge() {
        return edge;
    }

    public void setEdge(List<CandidateNodeDetail> edge) {
        this.edge = edge;
    }

    public List<CandidateNodeDetail> getRec() {
        return rec;
    }

    public void setRec(List<CandidateNodeDetail> rec) {
        this.rec = rec;
    }

    public List<CandidateNodeDetail> getValidator() {
        return validator;
    }

    public void setValidator(List<CandidateNodeDetail> validator) {
        this.validator = validator;
    }

    public List<CandidateNodeDetail> getZec() {
        return zec;
    }

    public void setZec(List<CandidateNodeDetail> zec) {
        this.zec = zec;
    }
}
