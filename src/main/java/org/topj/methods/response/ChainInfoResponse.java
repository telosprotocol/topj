package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class ChainInfoResponse {

    @JSONField(name = "first_timerblock_stamp")
    private String firstTimerBlockStamp;

    @JSONField(name = "version")
    private String version;

    @JSONField(name = "first_timerblock_hash")
    private String firstTimerBlockHash;

    @JSONField(name = "init_total_locked_token")
    private String initTotalLockedToken;

    @JSONField(name = "token_price")
    private String tokenPrice;

    @JSONField(name = "total_gas_shard")
    private String totalGasShard;

    @JSONField(name = "validator_group_count")
    private String validatorGroupCount;

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

    public String getInitTotalLockedToken() {
        return initTotalLockedToken;
    }

    public void setInitTotalLockedToken(String initTotalLockedToken) {
        this.initTotalLockedToken = initTotalLockedToken;
    }

    public String getTokenPrice() {
        return tokenPrice;
    }

    public void setTokenPrice(String tokenPrice) {
        this.tokenPrice = tokenPrice;
    }

    public String getTotalGasShard() {
        return totalGasShard;
    }

    public void setTotalGasShard(String totalGasShard) {
        this.totalGasShard = totalGasShard;
    }

    public String getValidatorGroupCount() {
        return validatorGroupCount;
    }

    public void setValidatorGroupCount(String validatorGroupCount) {
        this.validatorGroupCount = validatorGroupCount;
    }
}
