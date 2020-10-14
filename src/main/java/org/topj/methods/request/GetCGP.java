package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.ResponseBase;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetCGP implements Request {
    private final String METHOD_NAME = "getCGP";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 1) {
            throw new ArgumentMissingException("except args size 1 , but got " + args.size());
        }
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
            argsMap.put("account_addr", args.get(0).toString());
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
