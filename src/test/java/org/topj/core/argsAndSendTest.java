package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.request.*;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.PassportResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
import org.topj.utils.ArgsUtils;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

public class argsAndSendTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() {
        HttpService httpService = new HttpService("http://128.199.174.23:19081");
        topj = Topj.build(httpService);
        account = new Account();
        account = topj.genAccount("47ce7e773f76df0a43ebfb243e7fffcc0f67a37fd4b8c05700ec107e2c25b7a5");
    }

    @Test
    public void testAccountInfo() {
        try{
            // requestToken
            Passport passport = new Passport();
            Map<String, String> requestTokenArgsMap = passport.getArgs(account, Collections.emptyList());
            ResponseBase<PassportResponse> requestTokenResponse = topj.getTopjService().send(requestTokenArgsMap, PassportResponse.class);
            PassportResponse result = requestTokenResponse.getData();
            account.setIdentityToken(result.getIdentityToken());
            System.out.println(JSON.toJSONString(requestTokenResponse));

            // createAccount
            CreateAccount createAccount = new CreateAccount();
            Map<String, String> createAccountArgsMap = createAccount.getArgs(account, Collections.emptyList());
            ResponseBase<XTransactionResponse> createAccountXt = topj.getTopjService().send(createAccountArgsMap, XTransactionResponse.class);
            System.out.println(JSON.toJSONString(createAccountXt));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException eca) {
                eca.printStackTrace();
            }

            // AccountInfo
            GetAccount getAccount = new GetAccount();
            Map<String, String> accountInfoMap = getAccount.getArgs(account, Arrays.asList(account.getAddress()));
            ResponseBase<AccountInfoResponse> accountInfoResponse = topj.getTopjService().send(accountInfoMap, AccountInfoResponse.class);
            AccountInfoResponse ar = accountInfoResponse.getData();
            account.setLastHash(ar.getLastTxHash());
            account.setLastHashXxhash64(ar.getLastTxHashXxhash64());
            account.setNonce(ar.getNonce());
            System.out.println(JSON.toJSONString(accountInfoResponse));

            // Transfer
            Transfer transfer = new Transfer();
            String to = "T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF";
            Integer amount = 100;
            String note = "";
            Map<String, String> transferMap = transfer.getArgs(account, Arrays.asList(to, amount, note));
            ResponseBase<XTransactionResponse> transactionResponseBase = topj.getTopjService().send(transferMap, XTransactionResponse.class);
            System.out.println(JSON.toJSONString(transactionResponseBase));

            try {
                Thread.sleep(1000);
            } catch (InterruptedException eca) {
                eca.printStackTrace();
            }

            // AccountInfo
            ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.getTopjService().send(accountInfoMap, AccountInfoResponse.class);
            AccountInfoResponse ar2 = accountInfoResponse.getData();
            account.setLastHash(ar2.getLastTxHash());
            account.setLastHashXxhash64(ar2.getLastTxHashXxhash64());
            System.out.println(JSON.toJSONString(accountInfoResponse2));

            // AccountTransaction
            GetTransaction getTransaction = new GetTransaction();
            Map<String, String> accountTransactionMap = getTransaction.getArgs(account, Arrays.asList(account.getLastHash()));
            ResponseBase<XTransactionResponse> accountTransactionResponse = topj.getTopjService().send(accountTransactionMap, XTransactionResponse.class);
            System.out.println(JSON.toJSONString(accountTransactionResponse));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
