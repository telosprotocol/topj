package org.topnetwork.methods.request;

import com.alibaba.fastjson.JSON;
import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Request;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCGP implements Request {
    private final String METHOD_NAME = "getCGP";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("target_account_addr", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());

            Map<String, String> argsMap = new HashMap<>();
            argsMap.put("account_addr", account.getAddress());
            params.put("params", argsMap);

            map.put("body", JSON.toJSONString(params));
        } catch (IOException e){
        }
        return map;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {

    }
}
