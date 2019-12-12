package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.TransferActionParam;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;
import org.topj.procotol.websocket.WebSocketService;
import org.topj.utils.ArgsUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class TopjTest {
    private Topj topj = null;
    private Account account = null;
    private Account account2 = null;

    @Before
    public void setUp() throws IOException {
//        String url = Topj.getDefaultServerUrl();
//        HttpService httpService = new HttpService(url);
//        HttpService httpService = new HttpService("http://127.0.0.1:19081");
//        HttpService httpService = new HttpService("http://192.168.20.27:19081");
        HttpService httpService = new HttpService("http://192.168.50.156:19081");
        topj = Topj.build(httpService);
//        WebSocketService wsService = new WebSocketService("ws://192.168.10.29:19085");
////        WebSocketService wsService = new WebSocketService("ws://128.199.181.220:19085");
//        try{
//            wsService.connect();
//        } catch (ConnectException conne){
//            conne.printStackTrace();
//        }
//        topj = Topj.build(wsService);
        account = new Account("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
        account2 = topj.genAccount("0x14430e97e45467335b5b7872fd850d67fc4511607c51ed8b2f0f05bfebdeaf80");
    }

    @Test
    public void testAccountInfo() throws IOException {

        topj.requestToken(account);
//        topj.requestToken(account2);

        TestCommon.getAccountInfo(topj, account);
//        TestCommon.getAccountInfo(topj, account2);

        ResponseBase<XTransaction> transferResponseBase = topj.transfer(account,account2.getAddress(), BigInteger.valueOf(100), "hello top");
//        TransferActionParam transferActionParam = new TransferActionParam();
//        transferActionParam.decode(transferResponseBase.getData().getTargetAction().getActionParam());
//        System.out.print(">>>>> transfer targetActionData >> ");
//        System.out.println(JSON.toJSONString(transferActionParam));
        System.out.print(">>>>> transfer transaction >> ");
        System.out.println(JSON.toJSONString(transferResponseBase));
//
        try {
            Thread.sleep(2000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
//
//        Map<String, Long> voteInfo = new HashMap<>();
//        voteInfo.put("T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(10));
//        voteInfo.put("T-0-1B75FnoqfrNu6fuADADzwohLdzJ7Lm29bV", Long.valueOf(11));
//        voteInfo.put("T-user", Long.valueOf(453));
//        ResponseBase<XTransaction> voteXt = topj.setVote(account, voteInfo);
//        System.out.println("setVote transaction >> ");
//        System.out.println(JSON.toJSONString(voteXt));
//
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
        TestCommon.getAccountInfo(topj, account);
//        TestCommon.getAccountInfo(topj, account2);
//        topj.getUnitBlock(account);
//
        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, account.getLastHash());
        System.out.println("accountTransaction >> ");
        System.out.println(JSON.toJSONString(accountTransaction));
    }
}
