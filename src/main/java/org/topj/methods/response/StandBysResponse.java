package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class StandBysResponse {

    @JSONField(name = "arc")
    private List<StandBysDetail> arc;

    @JSONField(name = "auditor")
    private List<StandBysDetail> auditor;

    @JSONField(name = "edge")
    private List<StandBysDetail> edge;

    @JSONField(name = "root_beacon")
    private List<StandBysDetail> rootBeacon ;

    @JSONField(name = "validator")
    private List<StandBysDetail> validator;

    @JSONField(name = "sub_beacon")
    private List<StandBysDetail> subBeacon;

    public List<StandBysDetail> getArc() {
        return arc;
    }

    public void setArc(List<StandBysDetail> arc) {
        this.arc = arc;
    }

    public List<StandBysDetail> getAuditor() {
        return auditor;
    }

    public void setAuditor(List<StandBysDetail> auditor) {
        this.auditor = auditor;
    }

    public List<StandBysDetail> getEdge() {
        return edge;
    }

    public void setEdge(List<StandBysDetail> edge) {
        this.edge = edge;
    }

    public List<StandBysDetail> getRootBeacon() {
        return rootBeacon;
    }

    public void setRootBeacon(List<StandBysDetail> rootBeacon) {
        this.rootBeacon = rootBeacon;
    }

    public List<StandBysDetail> getValidator() {
        return validator;
    }

    public void setValidator(List<StandBysDetail> validator) {
        this.validator = validator;
    }

    public List<StandBysDetail> getSubBeacon() {
        return subBeacon;
    }

    public void setSubBeacon(List<StandBysDetail> subBeacon) {
        this.subBeacon = subBeacon;
    }
}
