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

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ContractTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() {
        HttpService httpService = new HttpService("http://192.168.50.171:19081");
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
//        account = topj.genAccount("a3aab9c186458ffd07ce1c01ba7edf9919724224c34c800514c60ac34084c63e");
        System.out.println(account.getAddress());
        System.out.println(account.getPrivateKey());
    }

    @Test
    public void testAccountInfo() throws IOException {

        ResponseBase<RequestTokenResponse> requestTokenResponse = topj.requestToken(account);
        assert (account.getToken() != null);
        Objects.requireNonNull(account);
        System.out.println(JSON.toJSONString(requestTokenResponse));

        TestCommon.createAccount(topj, account);

        TestCommon.getAccountInfo(topj, account);

        Account contractAccount = topj.createAccount();
        System.out.println(contractAccount.getAddress());
        System.out.println(contractAccount.getPrivateKey());

        TestCommon.publishContract(topj, account, contractAccount);

        TestCommon.getProperty(topj, account, contractAccount.getAddress(), "hmap", "key");

        TestCommon.getAccountInfo(topj, account);

        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAccount.getAddress(), "opt_map", Arrays.asList("inkey", Long.valueOf(65)));
        System.out.println("***** call contract transaction >> ");
        System.out.println(JSON.toJSONString(callContractResult));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestCommon.getProperty(topj, account, contractAccount.getAddress(), "hmap", "inkey");

//        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, account.getLastHash());
//        System.out.println("accountTransaction >> ");
//        System.out.println(JSON.toJSONString(accountTransaction));
    }
}
