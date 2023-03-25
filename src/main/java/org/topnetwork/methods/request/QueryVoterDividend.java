package org.topnetwork.methods.request;

import com.alibaba.fastjson.JSON;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.RequestTemplate;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.reward.VoterDividendResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryVoterDividend extends RequestTemplate {
    private final String METHOD_NAME = "queryVoterDividend";

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
            for (Object key : m.keySet()) {
                if (m.get(key) == null) {
                    continue;
                }
                VoterDividendResponse n = JSON.parseObject(m.get(key).toString(), VoterDividendResponse.class);
                responseBase.setData(n);
            }
        }
    }
}
