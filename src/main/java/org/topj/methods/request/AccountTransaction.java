package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.*;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountTransaction implements Request {
    private final String METHOD_NAME = "account_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 1) {
            // TODO: exception
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> body=new HashMap<String,Object>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("account_address", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());
            map.put("token", account.getToken());

            params.put("account", account.getAddress());
            params.put("tx_hash", account.getLastHash());

            body.put("version", TopjConfig.getVersion());
            body.put("account_address", account.getAddress());
            body.put("method", METHOD_NAME);
            body.put("sequence_id", account.getSequenceId());

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
    public void afterExecution(ResponseBase responseBase) {

    }
}
