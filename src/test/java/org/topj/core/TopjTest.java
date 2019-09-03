package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;
import org.topj.procotol.websocket.WebSocketService;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

public class TopjTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() {
        HttpService httpService = new HttpService("http://192.168.50.159:19081");
//        HttpService httpService = new HttpService("http://127.0.0.1:19090");
//        HttpService httpService = new HttpService("http://192.168.50.71:19081");
        topj = Topj.build(httpService);
//        WebSocketService wsService = new WebSocketService("ws://127.0.0.1:19085");
//        WebSocketService wsService = new WebSocketService("ws://128.199.181.220:19085");
//        try{
//            wsService.connect();
//        } catch (ConnectException conne){
//            conne.printStackTrace();
//        }
//        topj = Topj.build(wsService);
        account = new Account();
//        account = topj.genAccount("47ce7e773f76df0a43ebfb243e7fffcc0f67a37fd4b8c05700ec107e2c25b7a5");
    }

    @Test
    public void testAccountInfo() {

        RequestTokenResponse requestTokenResponse = topj.requestToken(account);
        assert (account.getToken() != null);
        System.out.println(JSON.toJSONString(requestTokenResponse));

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        System.out.println(JSON.toJSONString(account));
        topj.createAccount(account);
        System.out.println("create account 1 >> ");
//        topj.createAccount(account);
//        System.out.println("create account 2 >> ");
//        topj.createAccount(account);
//        System.out.println("create account 3 >> ");
//        topj.createAccount(account);
//        System.out.println("create account 4 >> ");
//
        try {
            Thread.sleep(1000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }

        AccountInfoResponse accountInfoResponse = topj.accountInfo(account);
        System.out.println(JSON.toJSONString(accountInfoResponse));
//        AccountInfoResponse accountInfoResponse1 = topj.accountInfo(account);
//        System.out.println(JSON.toJSONString(accountInfoResponse1));
//        AccountInfoResponse accountInfoResponse2 = topj.accountInfo(account);
//        System.out.println(JSON.toJSONString(accountInfoResponse2));
//        AccountInfoResponse accountInfoResponse3 = topj.accountInfo(account);
//        System.out.println(JSON.toJSONString(accountInfoResponse3));
//        AccountInfoResponse accountInfoResponse4 = topj.accountInfo(account);
//        System.out.println(JSON.toJSONString(accountInfoResponse4));

//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException es) {
//            es.printStackTrace();
//        }

//       topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", 140, "");
//        System.out.println("send transaction >> ");
//
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException es) {
//            es.printStackTrace();
//        }

//        Map<String, Long> voteInfo = new HashMap<>();
//        voteInfo.put("T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(10));
//        voteInfo.put("T-0-1B75FnoqfrNu6fuADADzwohLdzJ7Lm29bV", Long.valueOf(11));
//        voteInfo.put("T-user", Long.valueOf(453));
//        topj.setVote(account, voteInfo);
//
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

//        AccountInfoResponse accountInfoResponse2 = topj.accountInfo(account);
//        System.out.println(JSON.toJSONString(accountInfoResponse2));
//
//        try {
//            Thread.sleep(15000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        XTransaction accountTransaction = topj.accountTransaction(account, account.getLastHash());
//        System.out.println(JSON.toJSONString(accountTransaction));
    }
}
