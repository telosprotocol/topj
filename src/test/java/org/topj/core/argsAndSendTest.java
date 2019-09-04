package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.request.*;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;
import org.topj.utils.ArgsUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class argsAndSendTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() {
        HttpService httpService = new HttpService("http://192.168.50.192:19081");
        topj = Topj.build(httpService);
//        account = new Account();
        account = topj.genAccount("47ce7e773f76df0a43ebfb243e7fffcc0f67a37fd4b8c05700ec107e2c25b7a5");
    }

    @Test
    public void testAccountInfo() {
        try{
            // requestToken
            RequestToken requestToken = new RequestToken();
            Map<String, String> requestTokenArgsMap = requestToken.getArgs(account, Collections.emptyList());
            ResponseBase<RequestTokenResponse> requestTokenResponse = topj.getTopjService().send(requestTokenArgsMap, RequestTokenResponse.class);
            RequestTokenResponse result = requestTokenResponse.getData();
            account.setToken(result.getToken());
            System.out.println(JSON.toJSONString(requestTokenResponse));

            // createAccount
            CreateAccount createAccount = new CreateAccount();
            Map<String, String> createAccountArgsMap = createAccount.getArgs(account, Collections.emptyList());
            ResponseBase<XTransaction> createAccountXt = topj.getTopjService().send(createAccountArgsMap, XTransaction.class);
            XTransaction ca = ArgsUtils.decodeXTransFromArgs(createAccountArgsMap);
            createAccountXt.setData(ca);
            System.out.println(JSON.toJSONString(createAccountXt));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException eca) {
                eca.printStackTrace();
            }

            // AccountInfo
            AccountInfo accountInfo = new AccountInfo();
            Map<String, String> accountInfoMap = accountInfo.getArgs(account, Arrays.asList(account.getAddress()));
            ResponseBase<AccountInfoResponse> accountInfoResponse = topj.getTopjService().send(accountInfoMap, AccountInfoResponse.class);
            AccountInfoResponse ar = accountInfoResponse.getData();
            account.setLastHash(ar.getLastHash());
            account.setLastHashXxhash64(ar.getLastHashXxhash64());
            System.out.println(JSON.toJSONString(accountInfoResponse));

            // Transfer
            Transfer transfer = new Transfer();
            String to = "T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF";
            Integer amount = 100;
            String note = "";
            Map<String, String> transferMap = transfer.getArgs(account, Arrays.asList(to, amount, note));
            ResponseBase<XTransaction> transactionResponseBase = topj.getTopjService().send(transferMap, XTransaction.class);
            XTransaction t = ArgsUtils.decodeXTransFromArgs(transferMap);
            transactionResponseBase.setData(t);
            System.out.println(JSON.toJSONString(transactionResponseBase));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException eca) {
                eca.printStackTrace();
            }

            // AccountInfo
            ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.getTopjService().send(accountInfoMap, AccountInfoResponse.class);
            AccountInfoResponse ar2 = accountInfoResponse.getData();
            account.setLastHash(ar2.getLastHash());
            account.setLastHashXxhash64(ar2.getLastHashXxhash64());
            System.out.println(JSON.toJSONString(accountInfoResponse2));

            // AccountTransaction
            AccountTransaction accountTransaction = new AccountTransaction();
            Map<String, String> accountTransactionMap = accountTransaction.getArgs(account, Arrays.asList(account.getLastHash()));
            ResponseBase<XTransaction> accountTransactionResponse = topj.getTopjService().send(accountTransactionMap, XTransaction.class);
            System.out.println(JSON.toJSONString(accountTransactionResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
