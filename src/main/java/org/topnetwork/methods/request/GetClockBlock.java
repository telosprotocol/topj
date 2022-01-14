package org.topnetwork.methods.request;

import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.RequestTemplate;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetClockBlock extends RequestTemplate {
    private final String METHOD_NAME = "getTimerInfo";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        try {
            RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("account_addr", TopjConfig.getBeaconTimerAddress());
            requestModel.getRequestBody().setArgsMap(argsMap);
            return requestModel.toSimpleMap();
        } catch (IOException err){
            err.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {
    }
}