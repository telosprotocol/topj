package org.topj.methods.request;

import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTemplate;
import org.topj.methods.response.ResponseBase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GetChainInfo extends RequestTemplate {
    private final String METHOD_NAME = "get_chain_info";

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
