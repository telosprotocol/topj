package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTemplate;
import org.topj.methods.property.BlockParameterName;
import org.topj.methods.response.ResponseBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetBlock extends RequestTemplate {
    private final String METHOD_NAME = "getBlock";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        try {
            RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("account_addr", args.get(0).toString());
            argsMap.put("height", args.get(1).toString());
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
