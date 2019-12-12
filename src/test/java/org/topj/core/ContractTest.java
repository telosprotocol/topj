package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.property.XProperty;
import org.topj.methods.response.*;
import org.topj.procotol.http.HttpService;
import org.topj.procotol.websocket.WebSocketService;
import org.topj.utils.TopUtils;

import java.io.*;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.*;

public class ContractTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp(){
        HttpService httpService = getHttpService("http://192.168.50.187:19081");
//        HttpService httpService = new HttpService("http://157.245.121.80:19081");
        topj = Topj.build(httpService);
        account = topj.genAccount("0x750918274d3d07a28ebc825540095bb8e2404b66b06ed7ef283c8f7fcd172e35");
    }

    @Test
    public void lottery() throws IOException {
        account = topj.genAccount("0x6ef3b57fe2c0e977a5db67e6426f5676689ca7ec4ea59906a1604cae013b40b8");
        topj.requestToken(account);
        ResponseBase<AccountInfoResponse> userInfo = topj.accountInfo(account);
        if (Objects.equals(userInfo, null) || userInfo.getErrNo() != 0) {
            System.out.println("***** get account info error ");
            System.out.println(JSON.toJSONString(account));
            System.out.println(JSON.toJSONString(userInfo));
            return;
        }
        System.out.println(JSON.toJSONString(userInfo));
        String codeStr = TestCommon.getResourceFile("lottery.lua");
        ResponseBase<PublishContractResponse> result = topj.publishContract(account, codeStr, 200000000, 0, "", "test_tx");
        XTransaction xTransaction = result.getData().getxTransaction();
        Account contractAccount = result.getData().getContractAccount();
        System.out.println("***** publish contract transaction >> ");
        System.out.println(JSON.toJSONString(result));
        try {
            Thread.sleep(8000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
//        Account contractAccount = account.genContractAccount("0x968a7c85eff40321f46d2e3e24d1ae0c7d5e02e3c43e4729766f300be7cfa10e");
        topj.requestToken(contractAccount);
        ResponseBase<AccountInfoResponse> contractAccountInfo = topj.accountInfo(contractAccount);
        System.out.println("***** contractAccountInfo >> ");
        System.out.println(JSON.toJSONString(contractAccountInfo));

//        topj.accountInfo(account);
//        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAccount.getAddress(), "lottery", Collections.emptyList());
//        System.out.println("***** callContractResult >> ");
//        System.out.println(JSON.toJSONString(callContractResult));
//        try {
//            Thread.sleep(8000);
//        } catch (InterruptedException es) {
//            es.printStackTrace();
//        }
        TestCommon.getListProperty(topj, account, contractAccount.getAddress(), "random_list");
        TestCommon.getListProperty(topj, account, contractAccount.getAddress(), "user_list");
        TestCommon.getListProperty(topj, account, contractAccount.getAddress(), "random_user");
    }

    @Ignore
    @Test
    public void getLotteryResult(){
        topj.requestToken(account);
        String contractAddress = "T-3-MfCtfpfvMNbcPxMJjT9LSVA1GbX7Q7DuKm";
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_2");

        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAddress, "set_new", Arrays.asList("中文"));
        System.out.println("***** call contract transaction >> ");
        System.out.println(JSON.toJSONString(callContractResult));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
    }

    @Test
    public void pledge() throws IOException {
        account = topj.genAccount("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
        topj.requestToken(account);
//        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> pledgeTgas = topj.pledgeTgas(account, new TransferParams(BigInteger.valueOf(10)));
        System.out.println("pledgeTgas >> ");
        System.out.println(JSON.toJSONString(pledgeTgas));

        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_TGAS_KEY);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_TGAS_KEY);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.LAST_TX_HOUR_KEY);
    }

    @Test
    public void redeem() throws IOException {
        account = topj.genAccount("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> redeemTgas = topj.redeemTgas(account, new TransferParams(BigInteger.valueOf(5)));
        System.out.println("redeemTgas >> ");
        System.out.println(JSON.toJSONString(redeemTgas));

        TestCommon.getStringProperty(topj, account, account.getAddress(), "@30");
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_TGAS_KEY);
    }

    @Ignore
    @Test
    public void disk(){
        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> pledgeDisk = topj.pledgeDisk(account, new TransferParams(BigInteger.valueOf(5)));
        System.out.println("pledgeDisk >> ");
        System.out.println(JSON.toJSONString(pledgeDisk));
        ResponseBase<XTransaction> redeemDisk = topj.redeemDisk(account, new TransferParams(BigInteger.valueOf(5)));
        System.out.println("redeemDisk >> ");
        System.out.println(JSON.toJSONString(redeemDisk));
        // 获取用户已质押的disk
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_DISK_KEY);
        // 获取用户已使用的disk
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_DISK_KEY);
    }

    @Ignore
    @Test
    public void setVote() throws UnsupportedEncodingException {

//        Account nodeAccount = new Account();
//        topj.requestToken(nodeAccount);
//        TestCommon.createAccount(topj, nodeAccount);
//        TestCommon.getAccountInfo(topj, nodeAccount);
//        TestCommon.nodeRegister(topj, nodeAccount);

        String nodeAddress = "T-0-LSZM1stXX48bCdf5cPbVZ2goiyQ2m6GBWE";

        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        TestCommon.getAccountInfo(topj, account);

        TestCommon.setVote(topj, account, nodeAddress);

        String voteContractAddress = "T-s-ucPXFNzeqEGSQUpxVnymM5s4seXSCFMJz";
        String userKey = TopUtils.getUserVoteKey(account.getAddress(), voteContractAddress);
        System.out.println(userKey);
        TestCommon.getMapProperty(topj, account, voteContractAddress, XProperty.CONTRACT_VOTER_KEY, userKey);
    }

    @Ignore
    @Test
    public void getProperty(){
        topj.requestToken(account);
        TestCommon.getAccountInfo(topj, account);
        String contractAddress = "T-3-MfCtfpfvMNbcPxMJjT9LSVA1GbX7Q7DuKm";
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_2");

        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAddress, "set_new", Arrays.asList("中文"));
        System.out.println("***** call contract transaction >> ");
        System.out.println(JSON.toJSONString(callContractResult));
                try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
    }

    @Ignore
    @Test
    public void testAccountInfo() throws IOException {
        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        TestCommon.getAccountInfo(topj, account);

        PublishContractResponse publishContractResponse = TestCommon.publishContract(topj, account);

        Account contractAccount = publishContractResponse.getContractAccount();
        topj.requestToken(contractAccount);
        TestCommon.getAccountInfo(topj, contractAccount);

        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");
        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_2");

        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAccount.getAddress(), "set_new", Arrays.asList("中文"));
        System.out.println("***** call contract transaction >> ");
        System.out.println(JSON.toJSONString(callContractResult));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");

//        TestCommon.getMapProperty(topj, account, contractAccount.getAddress(), "hmap", "key");
//
//        TestCommon.getAccountInfo(topj, account);
//
//        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAccount.getAddress(), "save", Arrays.asList(Long.valueOf(99), "中文", true));
//        System.out.println("***** call contract transaction >> ");
//        System.out.println(JSON.toJSONString(callContractResult));
////
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_1");
//        TestCommon.getStringProperty(topj, account, contractAccount.getAddress(), "temp_2");

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
