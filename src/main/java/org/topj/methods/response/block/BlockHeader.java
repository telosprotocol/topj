package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

public class BlockHeader {

    /**
     * 区块的审计节点
     */
    @JSONField(name = "auditor")
    private String auditor;

    @JSONField(name = "bec_round")
    private Integer becRound;

    @JSONField(name = "m_timerblock_height")
    private Integer mTimerBlockHeight;

    @JSONField(name = "size")
    private Integer size;

    @JSONField(name = "time")
    private Date time;

    /**
     * 区块的验证节点
     */
    @JSONField(name = "validator")
    private String validator;

    @JSONField(name = "zec_round")
    private Integer zecRound;

    public String getAuditor() {
        return auditor;
    }

    public void setAuditor(String auditor) {
        this.auditor = auditor;
    }

    public Integer getBecRound() {
        return becRound;
    }

    public void setBecRound(Integer becRound) {
        this.becRound = becRound;
    }

    public Integer getmTimerBlockHeight() {
        return mTimerBlockHeight;
    }

    public void setmTimerBlockHeight(Integer mTimerBlockHeight) {
        this.mTimerBlockHeight = mTimerBlockHeight;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getValidator() {
        return validator;
    }

    public void setValidator(String validator) {
        this.validator = validator;
    }

    public Integer getZecRound() {
        return zecRound;
    }

    public void setZecRound(Integer zecRound) {
        this.zecRound = zecRound;
    }
}
