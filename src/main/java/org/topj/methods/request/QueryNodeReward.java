package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParser;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTemplate;
import org.topj.methods.response.NodeInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.reward.NodeRewardResponse;

import java.io.IOException;
import java.math.BigInteger;
import java.text.DateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryNodeReward extends RequestTemplate {
    private final String METHOD_NAME = "queryNodeReward";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        try {
            RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("node_account_addr", args.get(0).toString());
            argsMap.put("account_addr", account.getAddress());
            requestModel.getRequestBody().setArgsMap(argsMap);
            return requestModel.toSimpleMap();
        } catch (IOException err){
            err.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {
        if (responseBase.getData() == null) {
            return;
        }
        if (responseBase.getData() instanceof Map) {
            Map m = (Map) responseBase.getData();
            Map<String, NodeRewardResponse> result = new HashMap<>();
            for (Object key : m.keySet()) {
                if (m.get(key) == null) {
                    continue;
                }
                NodeRewardResponse n = JSON.parseObject(m.get(key).toString(), NodeRewardResponse.class);
                result.put(key.toString(), n);
            }
            responseBase.setData(result);
        }
    }
}
