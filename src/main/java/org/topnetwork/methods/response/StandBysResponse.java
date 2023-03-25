package org.topnetwork.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

public class StandBysResponse {


    @JSONField(name = "activated_state")
    private String activatedState;

    @JSONField(name = "archive")
    private List<StandBysDetail> archive;

    @JSONField(name = "auditor")
    private List<StandBysDetail> auditor;

    @JSONField(name = "edge")
    private List<StandBysDetail> edge;

    @JSONField(name = "full_node")
    private List<StandBysDetail> fullNode;

    @JSONField(name = "root_beacon")
    private List<StandBysDetail> rootBeacon ;

    @JSONField(name = "validator")
    private List<StandBysDetail> validator;

    @JSONField(name = "sub_beacon")
    private List<StandBysDetail> subBeacon;

    public String getActivatedState() {
        return activatedState;
    }

    public void setActivatedState(String activatedState) {
        this.activatedState = activatedState;
    }

    public List<StandBysDetail> getArchive() {
        return archive;
    }

    public void setArchive(List<StandBysDetail> archive) {
        this.archive = archive;
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

    public List<StandBysDetail> getFullNode() {
        return fullNode;
    }

    public void setFullNode(List<StandBysDetail> fullNode) {
        this.fullNode = fullNode;
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
