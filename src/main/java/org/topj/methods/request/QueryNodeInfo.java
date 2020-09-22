package org.topj.methods.request;

import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTemplate;
import org.topj.methods.response.NodeInfoResponse;
import org.topj.methods.response.ResponseBase;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryNodeInfo extends RequestTemplate {
    private final String METHOD_NAME = "queryNodeInfo";

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
            Map<String, NodeInfoResponse> result = new HashMap<>();
            for (Object key : m.keySet()) {
                NodeInfoResponse n = new NodeInfoResponse();
                if (m.get(key) == null) {
                    continue;
                }
                Map d = (Map)m.get(key);
                n.setAccountAddr(d.get("account_addr").toString());
                n.setAuditorCredit(d.get("auditor_credit").toString());
                n.setAuditorStake(new BigInteger(d.get("auditor_stake").toString()));
                n.setDividend_ratio(new BigInteger(d.get("dividend_ratio").toString()));
                n.setNetworkId(d.get("network_id").toString());
                n.setNodeDeposit(new BigInteger(d.get("node_deposit").toString()));
                n.setNodeSignKey(d.get("node_sign_key").toString());
                n.setNodename(d.get("nodename").toString());
                n.setRecStake(new BigInteger(d.get("rec_stake").toString()));
                n.setRegisteredNodeType(d.get("registered_node_type").toString());
                n.setValidatorCredit(d.get("validator_credit").toString());
                n.setValidatorStake(new BigInteger(d.get("validator_stake").toString()));
                n.setVoteAmount(new BigInteger(d.get("vote_amount").toString()));
                n.setZecStake(new BigInteger(d.get("zec_stake").toString()));
                result.put(key.toString(), n);
            }
            responseBase.setData(result);
        }
    }
}
