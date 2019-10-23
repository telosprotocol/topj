package org.topj.methods;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.response.ResponseBase;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class RequestTemplate implements Request {

    public RequestModel getDefaultArgs(Account account, String methodName){
        if (account == null || account.getToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> body=new HashMap<String,Object>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("account_address", account.getAddress());
            map.put("method", methodName);
            map.put("sequence_id", account.getSequenceId());
            map.put("token", account.getToken());

            body.put("version", TopjConfig.getVersion());
            body.put("account_address", account.getAddress());
            body.put("method", methodName);
            body.put("sequence_id", account.getSequenceId());

//            params.put("account", args.get(0));
//            params.put("type", args.get(1));
//            if (args.size() == 3) {
//                params.put("data", args.get(2));
//            }
//
//            body.put("params", params);
            map.put("body", JSON.toJSONString(body));
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
