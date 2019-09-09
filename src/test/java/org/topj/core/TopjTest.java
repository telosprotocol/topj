package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
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
        HttpService httpService = new HttpService("http://128.199.174.23:19081");
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
//        account = topj.genAccount("a58b4be85809981f1439c3cdfd71ff2a8f7f89fc62d16e9e8175d358ec3f74a3");
        System.out.println(account.getPrivateKey());
    }

    @Test
    public void testAccountInfo() {

        ResponseBase<RequestTokenResponse> requestTokenResponse = topj.requestToken(account);
        assert (account.getToken() != null);
        System.out.println(JSON.toJSONString(requestTokenResponse));

        ResponseBase<XTransaction> createAccountXt = topj.createAccount(account);
        System.out.println("createAccount transaction >> ");
        System.out.println(JSON.toJSONString(createAccountXt));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
//
        ResponseBase<AccountInfoResponse> accountInfoResponse = topj.accountInfo(account);
        System.out.println(JSON.toJSONString(accountInfoResponse));

        ResponseBase<XTransaction> transactionResponseBase = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", 140, "");
        System.out.println("transfer transaction >> ");
        System.out.println(JSON.toJSONString(transactionResponseBase));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
//
        Map<String, Long> voteInfo = new HashMap<>();
        voteInfo.put("T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(10));
        voteInfo.put("T-0-1B75FnoqfrNu6fuADADzwohLdzJ7Lm29bV", Long.valueOf(11));
        voteInfo.put("T-user", Long.valueOf(453));
        ResponseBase<XTransaction> voteXt = topj.setVote(account, voteInfo);
        System.out.println("setVote transaction >> ");
        System.out.println(JSON.toJSONString(voteXt));

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.accountInfo(account);
        System.out.println("accountInfo >> ");
        System.out.println(JSON.toJSONString(accountInfoResponse2));

        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, account.getLastHash());
        System.out.println("accountTransaction >> ");
        System.out.println(JSON.toJSONString(accountTransaction));
    }
}
