package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;
import org.topj.procotol.websocket.WebSocketService;
import org.topj.utils.TopUtils;

import java.io.*;
import java.net.ConnectException;
import java.net.URL;
import java.util.*;

public class ContractTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp(){
        HttpService httpService = getHttpService("http://192.168.50.192:19081");
        topj = Topj.build(httpService);
        account = topj.genAccount("a3aab9c186458ffd07ce1c01ba7edf9919724224c34c800514c60ac34084c63e");
    }

    @Test
    public void setVote() throws UnsupportedEncodingException {
        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        String contractAddress = "T-s-ucPXFNzeqEGSQUpxVnymM5s4seXSCFMJz";
        String actionName = "set_vote_info";
        String nodeAddress = "T-0-1JFZKhpgMX5mtYpGDU1fJPaLE25tDKJgFa";
        Map<String, Long> voteInfo = new HashMap<>();
        voteInfo.put(nodeAddress, Long.valueOf(5000));
        voteInfo.put(nodeAddress, Long.valueOf(5000));
        ResponseBase<XTransaction> callContractResult = topj.setVote(account, contractAddress, actionName, voteInfo);
        System.out.println("callContractResult >> ");
        System.out.println(JSON.toJSONString(callContractResult));

        String userKey = TopUtils.getUserVoteKey(account.getAddress(), contractAddress);
        System.out.println(userKey);
        TestCommon.getMapProperty(topj, account, contractAddress, "#112", userKey);
        TestCommon.getStringProperty(topj, account, account.getAddress(), "@31");
        TestCommon.getStringProperty(topj, account, account.getAddress(), "@30");
        TestCommon.getStringProperty(topj, account, account.getAddress(), "@29");
    }

    @Ignore
    @Test
    public void testAccountInfo() throws IOException {

        Account contractAccount = topj.genAccount();
        System.out.println(contractAccount.getAddress());
        System.out.println(contractAccount.getPrivateKey());

        TestCommon.publishContract(topj, account, contractAccount);
        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");
        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_2");

//        TestCommon.getMapProperty(topj, account, contractAccount.getAddress(), "hmap", "key");
//
        TestCommon.getAccountInfo(topj, account);

        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAccount.getAddress(), "save", Arrays.asList(Long.valueOf(99), "中文", true));
        System.out.println("***** call contract transaction >> ");
        System.out.println(JSON.toJSONString(callContractResult));
//
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");
        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_2");

//        TestCommon.getMapProperty(topj, account, contractAccount.getAddress(), "hmap", "inkey");
//        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");
//        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_2");
//
//
//        TestCommon.getAccountInfo(topj, account);
//
////         topj.callContract(account, contractAccount.getAddress(), "check_map", Arrays.asList("inkey"));
//
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        TestCommon.getMapProperty(topj, account, contractAccount.getAddress(), "hmap", "inkey");
////        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");
////        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_2");
////        TestCommon.getListProperty(topj, account, contractAccount.getAddress(), "mlist");
//
//        ResponseBase<XTransaction> stringProperty = topj.getStringProperty(account, contractAccount.getAddress(), "temp_1");
//        ResponseBase<XTransaction> listProperty = topj.getListProperty(account, contractAccount.getAddress(), "mlist");
//        List<String> getPropertyParams = new ArrayList<>();
//        getPropertyParams.add("hmap");
//        getPropertyParams.add("inkey");
//        ResponseBase<XTransaction> mapProperty = topj.getMapProperty(account, contractAccount.getAddress(), getPropertyParams);
//        System.out.println(JSON.toJSONString(stringProperty));
//        System.out.println(JSON.toJSONString(listProperty));
//        System.out.println(JSON.toJSONString(mapProperty));
//
//        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, account.getLastHash());
//        System.out.println("accountTransaction >> ");
//        System.out.println(JSON.toJSONString(accountTransaction));
    }

    private HttpService getHttpService(String url) {
        // http://127.0.0.1:19090
        // http://192.168.50.71:19081
        return new HttpService(url);
    }

    private WebSocketService getWebSocketService(String url){
        // ws://127.0.0.1:19085
        // ws://128.199.181.220:19085
        WebSocketService wsService = new WebSocketService(url);
        try{
            wsService.connect();
            return wsService;
        } catch (ConnectException conne){
            conne.printStackTrace();
            return null;
        }
    }
}
