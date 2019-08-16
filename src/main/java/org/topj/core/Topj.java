package org.topj.core;

import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.request.AccountInfo;
import org.topj.methods.request.RequestToken;
import org.topj.methods.request.Transfer;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.TopjService;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class Topj {

    private static Topj instance = null;
    private TopjService topjService = null;
    private Account defaultAccount = null;

    private Topj(){}

    public static synchronized Topj build(TopjService topjService) {
        if (instance == null) {
            instance = new Topj();
        }
        if (instance.topjService == null || instance.topjService != topjService){
            instance.topjService = topjService;
        }
        if (instance.defaultAccount == null) {
            instance.defaultAccount = new Account();
        }
        return instance;
    }

    public void updateDefaultAccount(String privateKey) {
        instance.defaultAccount = new Account(privateKey);
    }


    public RequestTokenResponse requestToken(){
        Request request = new RequestToken();
        Map<String, String> args = request.getArgs(instance.defaultAccount, Collections.emptyList());
        ResponseBase<RequestTokenResponse> responseBase = instance.topjService.send(args, RequestTokenResponse.class);
        request.afterExecution(responseBase);
        return responseBase.getData();
    }


    public AccountInfoResponse accountInfo(String address){
        if (address == null || address.equals("")) {
            address = instance.defaultAccount.getAddress();
        }
        Request request = new AccountInfo();
        Map<String, String> args = request.getArgs(instance.defaultAccount, Arrays.asList(address));
        ResponseBase<AccountInfoResponse> responseBase = instance.topjService.send(args, AccountInfoResponse.class);
        request.afterExecution(responseBase);
        return responseBase.getData();
    }


    public XTransaction transfer(String to, Integer amount, String note){
        Request request = new Transfer();
        Map<String, String> args = request.getArgs(instance.defaultAccount, Arrays.asList(to, amount, note));
        ResponseBase<XTransaction> responseBase = instance.topjService.send(args, XTransaction.class);
        request.afterExecution(responseBase);
        return responseBase.getData();
    }
}
