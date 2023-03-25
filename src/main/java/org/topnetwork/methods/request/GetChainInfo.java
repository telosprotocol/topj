package org.topnetwork.methods.request;

import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.RequestTemplate;
import org.topnetwork.methods.response.ResponseBase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetChainInfo extends RequestTemplate {
    private final String METHOD_NAME = "getChainInfo";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        try {
            RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
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
