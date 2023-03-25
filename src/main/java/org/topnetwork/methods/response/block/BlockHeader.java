package org.topnetwork.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class BlockHeader {

    /**
     * 区块的审计节点
     */
    @JSONField(name = "auditor")
    private String auditor;

    @JSONField(name = "auditor_xip")
    private String auditorXip;

    @JSONField(name = "multisign_auditor")
    private String multisignAuditor;

    @JSONField(name = "multisign_validator")
    private String multisignValidator;

    @JSONField(name = "timerblock_height")
    private Integer timerBlockHeight;

    @JSONField(name = "validator_xip")
    private String validatorXip;

    @JSONField(name = "version")
    private String version;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public String getAuditorXip() {
        return auditorXip;
    }

    public void setAuditorXip(String auditorXip) {
        this.auditorXip = auditorXip;
    }

    public String getMultisignAuditor() {
        return multisignAuditor;
    }

    public void setMultisignAuditor(String multisignAuditor) {
        this.multisignAuditor = multisignAuditor;
    }

    public String getMultisignValidator() {
        return multisignValidator;
    }

    public void setMultisignValidator(String multisignValidator) {
        this.multisignValidator = multisignValidator;
    }

    public Integer getTimerBlockHeight() {
        return timerBlockHeight;
    }

    public void setTimerBlockHeight(Integer timerBlockHeight) {
        this.timerBlockHeight = timerBlockHeight;
    }

    public String getValidatorXip() {
        return validatorXip;
    }

    public void setValidatorXip(String validatorXip) {
        this.validatorXip = validatorXip;
    }
}