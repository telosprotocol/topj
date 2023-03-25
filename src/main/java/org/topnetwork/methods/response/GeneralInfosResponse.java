package org.topnetwork.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class GeneralInfosResponse {
    @JSONField(name = "disk_price")
    private String diskPrice;

    @JSONField(name = "free_disk")
    private String freeDisk;

    @JSONField(name = "init_pledge_token")
    private String initPledgeToken;

    @JSONField(name = "shard_num")
    private String shardNum;

    @JSONField(name = "shard_gas")
    private String shardGas;

    @JSONField(name = "total_issuance")
    private String totalIssuance;

    @JSONField(name = "token_price")
    private String tokenPrice;

    /**
     * 创世时间戳
     */
    @JSONField(name = "genesis_time")
    private String genesisTime;

    public String getDiskPrice() {
        return diskPrice;
    }

    public void setDiskPrice(String diskPrice) {
        this.diskPrice = diskPrice;
    }

    public String getFreeDisk() {
        return freeDisk;
    }

    public void setFreeDisk(String freeDisk) {
        this.freeDisk = freeDisk;
    }

    public String getInitPledgeToken() {
        return initPledgeToken;
    }

    public void setInitPledgeToken(String initPledgeToken) {
        this.initPledgeToken = initPledgeToken;
    }

    public String getShardNum() {
        return shardNum;
    }

    public void setShardNum(String shardNum) {
        this.shardNum = shardNum;
    }

    public String getShardGas() {
        return shardGas;
    }

    public void setShardGas(String shardGas) {
        this.shardGas = shardGas;
    }

    public String getTotalIssuance() {
        return totalIssuance;
    }

    public void setTotalIssuance(String totalIssuance) {
        this.totalIssuance = totalIssuance;
    }

    public String getTokenPrice() {
        return tokenPrice;
    }

    public void setTokenPrice(String tokenPrice) {
        this.tokenPrice = tokenPrice;
    }

    public String getGenesisTime() {
        return genesisTime;
    }

    public void setGenesisTime(String genesisTime) {
        this.genesisTime = genesisTime;
    }
}
