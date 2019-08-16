package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AccountInfo implements Request {
    private final String METHOD_NAME = "account_info";

    private Account account = null;

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        this.account = account;
        if (args.size() != 1) {
            // TODO: exception
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("account_address", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());

            params.put("version", TopjConfig.getVersion());
            params.put("account_address", account.getAddress());
            params.put("method", METHOD_NAME);
            params.put("sequence_id", account.getSequenceId());

            Map<String, String> argsMap = new HashMap<>();
            argsMap.put("account", args.get(0).toString());
            params.put("params", argsMap);

            map.put("body", JSON.toJSONString(params));
        } catch (IOException e){
        }
        return map;
    }

    @Override
    public void afterExecution(ResponseBase responseBase) {
        AccountInfoResponse accountInfoResponse = (AccountInfoResponse)responseBase.getData();
        account.setNonce(accountInfoResponse.getNonce());
        account.setLastHash(accountInfoResponse.getLastHash());
        account.setLastHashXxhash64(accountInfoResponse.getLastHashXxhash64());
        account.setLastUnitHeight(accountInfoResponse.getLastUnitHeight());
        account.setBalance(accountInfoResponse.getBalance());
    }
}
