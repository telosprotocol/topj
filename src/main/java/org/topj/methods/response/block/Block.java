package org.topj.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

public class Block<T> {

    /**
     * 区块类型
     */
    @JSONField(name = "block_type")
    private Integer blockType;

    @JSONField(name = "body")
    private T body;

    @JSONField(name = "cluster_id")
    private Integer clusterId;

    @JSONField(name = "hash")
    private String hash;

    @JSONField(name = "header")
    private BlockHeader blockHeader;

    @JSONField(name = "height")
    private Integer height;

    /**
     * 区块所属账户
     */
    @JSONField(name = "owner")
    private String owner;

    @JSONField(name = "prev_hash")
    private String prevHash;

    /**
     * [64，127）之间，则减去64为该账户所在shard id,若不在该范围内，则账户不属于任何shard
     */
    @JSONField(name = "shard_id")
    private Integer shardId;

    @JSONField(name = "signature")
    private String signature;

    @JSONField(name = "table_id")
    private Integer tableId;

    @JSONField(name = "timestamp")
    private long timestamp;

    @JSONField(name = "zone_id")
    private Integer zoneId;

    public Integer getBlockType() {
        return blockType;
    }

    public void setBlockType(Integer blockType) {
        this.blockType = blockType;
    }

    public T getBody() {
        return body;
    }

    public void setBody(T body) {
        this.body = body;
    }

    public Integer getClusterId() {
        return clusterId;
    }

    public void setClusterId(Integer clusterId) {
        this.clusterId = clusterId;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BlockHeader getBlockHeader() {
        return blockHeader;
    }

    public void setBlockHeader(BlockHeader blockHeader) {
        this.blockHeader = blockHeader;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getPrevHash() {
        return prevHash;
    }

    public void setPrevHash(String prevHash) {
        this.prevHash = prevHash;
    }

    public Integer getShardId() {
        return shardId;
    }

    public void setShardId(Integer shardId) {
        this.shardId = shardId;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }
}
