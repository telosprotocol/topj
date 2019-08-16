package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestToken implements Request {

    private final String METHOD_NAME = "request_token";

    private Account account = null;

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        this.account = account;
        Map<String,String> map=new HashMap<String,String>();
        Map<String,String> params=new HashMap<String,String>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("account_address", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());

            params.put("version", TopjConfig.getVersion());
            params.put("account_address", account.getAddress());
            params.put("method", METHOD_NAME);
            params.put("sequence_id", account.getSequenceId());

            map.put("body", JSON.toJSONString(params));
        } catch (IOException e){
        }
        return map;
    }

    @Override
    public void afterExecution(ResponseBase responseBase) {
        account.setSequenceId(responseBase.getSequenceId() + 1);
        RequestTokenResponse requestTokenResponse = (RequestTokenResponse) responseBase.getData();
        account.setToken(requestTokenResponse.getToken());
    }
}
