package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class ChainInfoResponse {

    @JSONField(name = "first_timerblock_stamp")
    private String firstTimerBlockStamp;

    @JSONField(name = "version")
    private String version;

    @JSONField(name = "first_timerblock_hash")
    private String firstTimerBlockHash;

    public String getFirstTimerBlockStamp() {
        return firstTimerBlockStamp;
    }

    public void setFirstTimerBlockStamp(String firstTimerBlockStamp) {
        this.firstTimerBlockStamp = firstTimerBlockStamp;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getFirstTimerBlockHash() {
        return firstTimerBlockHash;
    }

    public void setFirstTimerBlockHash(String firstTimerBlockHash) {
        this.firstTimerBlockHash = firstTimerBlockHash;
    }
}
