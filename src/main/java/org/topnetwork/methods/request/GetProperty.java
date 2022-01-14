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

public class GetProperty implements Request {
    private final String METHOD_NAME = "getProperty";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 2 & args.size() != 3) {
            throw new ArgumentMissingException("except args size 2 or 3 , but got " + args.size());
        }
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> body=new HashMap<String,Object>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("target_account_addr", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());
            map.put("token", account.getIdentityToken());

            params.put("account_addr", args.get(0));
            params.put("type", args.get(1));
            if (args.size() == 3) {
                params.put("data", args.get(2));
            }

            body.put("params", params);
            map.put("body", JSON.toJSONString(body));
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {
        return;
    }
}
