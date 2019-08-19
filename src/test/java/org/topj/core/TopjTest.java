package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;

public class TopjTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() {
//        HttpService httpService = new HttpService("http://128.199.181.220:19081");
        HttpService httpService = new HttpService("http://127.0.0.1:19090");
        topj = Topj.build(httpService);
        account = new Account();
//        account = topj.genAccount("47ce7e773f76df0a43ebfb243e7fffcc0f67a37fd4b8c05700ec107e2c25b7a5");
    }

    @Test
    public void testAccountInfo(){

        RequestTokenResponse requestTokenResponse = topj.requestToken(account);
        assert(account.getToken() != null);
        System.out.printf(JSON.toJSONString(requestTokenResponse));

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        topj.createAccount(account);
        System.out.printf("create account >> ");

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AccountInfoResponse accountInfoResponse = topj.accountInfo(account);
        System.out.printf(JSON.toJSONString(accountInfoResponse));

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", 140, "");
        System.out.printf("send transaction >> ");

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AccountInfoResponse accountInfoResponse2 = topj.accountInfo(account);
        System.out.printf(JSON.toJSONString(accountInfoResponse2));

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        XTransaction accountTransaction = topj.accountTransaction(account, account.getLastHash());
        System.out.printf(JSON.toJSONString(accountTransaction));
    }
}
