package org.topnetwork.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;
import java.util.Map;

public class VoteUsedResponse {

    @JSONField(name = "vote_infos")
    private Map<String, BigInteger> voteInfos;

    public Map<String, BigInteger> getVoteInfos() {
        return voteInfos;
    }

    public void setVoteInfos(Map<String, BigInteger> voteInfos) {
        this.voteInfos = voteInfos;
    }
}
