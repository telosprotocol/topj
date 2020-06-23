package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
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
    private Account centerAccount = null;
    private Account account = null;

    @Before
    public void setUp(){
        HttpService httpService = getHttpService("http://192.168.20.12:19081");
//        HttpService httpService = new HttpService("http://157.245.121.80:19081");
        WebSocketService webSocketService = new WebSocketService("http://192.168.50.35:19085");
        try{
            webSocketService.connect();
        } catch (ConnectException conn){
            conn.printStackTrace();
        }
        topj = Topj.build(httpService);
//        topj.setTransactionReceiptProcessor(new NoOpProcessor(topj));
        account = topj.genAccount("0x750918274d3d07a28ebc825540095bb8e2404b66b06ed7ef283c8f7fcd172e35");
        centerAccount = topj.genAccount("0x7fcf50e425b4ac9c13268505cb3dfac32045457cc6e90500357d00c8cf85f5b9");
    }

    @Test
    public void lottery() throws IOException {
        account = topj.genAccount("0x2e911d949c55ac3f600012e029a50224dfb3a7e52c96678446324e6857c49f2b");
        topj.passport(account);
        TestCommon.createAccount(topj, account);
        ResponseBase<AccountInfoResponse> userInfo = topj.getAccount(account);
        if (Objects.equals(userInfo, null) || userInfo.getErrNo() != 0) {
            System.out.println("***** get account info error ");
            System.out.println(JSON.toJSONString(account));
            System.out.println(JSON.toJSONString(userInfo));
            return;
        }
        System.out.println(JSON.toJSONString(userInfo));
        String codeStr = TestCommon.getResourceFile("lottery.lua");
        ResponseBase<XTransaction> result = topj.deployContract(account, codeStr, BigInteger.valueOf(400000), BigInteger.ZERO, "", "test_tx");
        XTransaction xTransaction = result.getData();
        System.out.println("***** publish contract transaction >> ");
        System.out.println(JSON.toJSONString(result));
        try {
            Thread.sleep(8000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        String contractAddress = xTransaction.getTargetAction().getAccountAddr();
//        Account contractAccount = account.genContractAccount("0x9d5f7421e7493f4c058d006a50e415f210e865c49d28522574d262227a6d0a62");
        System.out.println("***** contractAccountInfo >> " + xTransaction.getTargetAction().getAccountAddr());

        topj.getAccount(account);
//        String contractAddress = "T-3-MkhWCpf5CtnPwtFWNUbfzz3RpTdN2crWk2";
//        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAddress, "lottery", Collections.emptyList());
//        System.out.println("***** callContractResult >> ");
//        System.out.println(JSON.toJSONString(callContractResult));
//        try {
//            Thread.sleep(8000);
//        } catch (InterruptedException es) {
//            es.printStackTrace();
//        }
        TestCommon.getListProperty(topj, account, contractAddress, "random_list");
        TestCommon.getListProperty(topj, account, contractAddress, "user_list");
        TestCommon.getListProperty(topj, account, contractAddress, "random_user");
    }

    @Ignore
    @Test
    public void getLotteryResult() throws IOException {
        topj.passport(account);
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
//        account = topj.genAccount();
        topj.passport(account);
//        TestCommon.createAccount(topj, account);
        centerAccountTransfer(account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> pledgeTgas = topj.stakeGas(account, BigInteger.valueOf(4000));
        System.out.println("pledgeTgas >> ");
        System.out.println(JSON.toJSONString(pledgeTgas));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResponseBase<XTransaction> accountTransaction = topj.getTransaction(account, pledgeTgas.getData().getTransactionHash());
        System.out.println(JSON.toJSONString(accountTransaction));
        Boolean isSucc = topj.isTxSuccess(account, pledgeTgas.getData().getTransactionHash());
        System.out.println("tx hash >> " + pledgeTgas.getData().getTransactionHash() + " > is success > " + isSucc);

        TestCommon.getAccountInfo(topj, account);

        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_TGAS_KEY);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.LAST_TX_HOUR_KEY);
    }

    @Test
    public void redeem() throws IOException {
        account = topj.genAccount("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
        topj.passport(account);
        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> redeemTgas = topj.unStakeGas(account, BigInteger.valueOf(5));
        System.out.println("redeemTgas >> ");
        System.out.println(JSON.toJSONString(redeemTgas));

        TestCommon.getStringProperty(topj, account, account.getAddress(), "@30");
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_TGAS_KEY);
    }

    @Test
    public void disk() throws IOException {
        account = topj.genAccount("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
        topj.passport(account);
//        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> pledgeDisk = topj.stakeDisk(account, BigInteger.valueOf(500));
        System.out.println("pledgeDisk >> ");
        System.out.println(JSON.toJSONString(pledgeDisk));
        TestCommon.getAccountInfo(topj, account);
//        ResponseBase<XTransaction> redeemDisk = topj.redeemDisk(account, new TransferParams(BigInteger.valueOf(5)));
//        System.out.println("redeemDisk >> ");
//        System.out.println(JSON.toJSONString(redeemDisk));
        // 获取用户已质押的disk
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_DISK_KEY);
        // 获取用户已使用的disk
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_DISK_KEY);
    }

    @Ignore
    @Test
    public void setVote() throws UnsupportedEncodingException, IOException {

//        Account nodeAccount = new Account();
//        topj.requestToken(nodeAccount);
//        TestCommon.createAccount(topj, nodeAccount);
//        TestCommon.getAccountInfo(topj, nodeAccount);
//        TestCommon.nodeRegister(topj, nodeAccount);

        String nodeAddress = "T-0-LSZM1stXX48bCdf5cPbVZ2goiyQ2m6GBWE";

        topj.passport(account);
        TestCommon.createAccount(topj, account);
        TestCommon.getAccountInfo(topj, account);

//        TestCommon.setVote(topj, account, nodeAddress);

        String voteContractAddress = "T-s-ucPXFNzeqEGSQUpxVnymM5s4seXSCFMJz";
        String userKey = TopUtils.getUserVoteKey(account.getAddress(), voteContractAddress);
        System.out.println(userKey);
        TestCommon.getMapProperty(topj, account, voteContractAddress, XProperty.CONTRACT_VOTER_KEY, userKey);
    }

//    @Ignore
    @Test
    public void getProperty() throws IOException {
        topj.passport(account);
//        TestCommon.getAccountInfo(topj, account);
        String contractAddress = "T-3-Mfz12at3HXDQcBksLVzj4owo2y2f81x2y4";
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_2");
        TestCommon.getMapProperty(topj, account, contractAddress, "hmap", "inkey");

//        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAddress, "set_new", Arrays.asList("中文"));
//        System.out.println("***** call contract transaction >> ");
//        System.out.println(JSON.toJSONString(callContractResult));
//                try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
    }

    @Test
    public void testAccountInfo() throws IOException {
        account = topj.genAccount("0x6f9934428ffdf520dfd088ae59e25f1f25532e7e310d5fb2d930b0e978322c48");
        topj.passport(account);
//        TestCommon.createAccount(topj, account);
//        centerAccountTransfer(account);
        TestCommon.getAccountInfo(topj, account);

        XTransaction xTransaction = TestCommon.publishContract(topj, account);

        String contractAddress = xTransaction.getTargetAction().getAccountAddr();
//        topj.requestToken(contractAccount);
//        TestCommon.getAccountInfo(topj, contractAccount);
        ResponseBase<XTransaction> accountTransaction = topj.getTransaction(account, xTransaction.getTransactionHash());
//        Boolean isSucc = topj.isTxSuccess(account, xTransaction.getTransactionHash());
        System.out.println("tx hash >> " + xTransaction.getTransactionHash() + " > is success > " + accountTransaction.getData().isSuccess());

        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_2");

        ResponseBase<XTransaction> callContractResult = topj.callContract(account, contractAddress, "set_new", Arrays.asList("中文"));
        System.out.println("***** call contract transaction >> ");
        System.out.println(JSON.toJSONString(callContractResult));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TestCommon.getStringProperty(topj, account, contractAddress, "temp_1");

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

    private void centerAccountTransfer(Account account) throws IOException {
        topj.passport(centerAccount);
        topj.getAccount(centerAccount);
        ResponseBase<XTransaction> xTransactionResponseBase = topj.transfer(centerAccount, account.getAddress(), BigInteger.valueOf(100000001l), "create");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (xTransactionResponseBase.getData() == null) {
            System.out.println("center transfer result is null ");
        } else {
            System.out.println("center transfer is " + xTransactionResponseBase.getData().isSuccess());
        }
    }
}
