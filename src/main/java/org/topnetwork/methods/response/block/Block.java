package org.topnetwork.methods.response.block;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class Block {

    @JSONField(name = "body")
    private Body body;

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

    @JSONField(name = "table_height")
    private BigInteger tableHeight;

    @JSONField(name = "timestamp")
    private long timestamp;

    public BigInteger getTableHeight() {
        return tableHeight;
    }

    public void setTableHeight(BigInteger tableHeight) {
        this.tableHeight = tableHeight;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
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

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
