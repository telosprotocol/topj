package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.TransferActionParam;
import org.topj.methods.property.NodeType;
import org.topj.methods.property.XProperty;
import org.topj.methods.response.*;
import org.topj.methods.response.block.UnitBlockResponse;
import org.topj.procotol.http.HttpService;
import org.topj.utils.TopUtils;
import org.topj.utils.TopjConfig;

import java.io.*;
import java.math.BigInteger;
import java.util.*;

public class TopjTest {
    private Topj topj = null;
    private Account account = null;
    private Account account2 = null;

    @Before
    public void setUp() throws IOException {
//        String url = Topj.getDefaultServerUrl();
//        HttpService httpService = new HttpService(url);
        HttpService httpService = new HttpService("http://192.168.50.156:19081");
//        HttpService httpService = new HttpService("http://192.168.50.171:19081");
//        HttpService httpService = new HttpService("http://192.168.50.136:19081");
        topj = Topj.build(httpService);
//        WebSocketService wsService = new WebSocketService("ws://192.168.10.29:19085");
////        WebSocketService wsService = new WebSocketService("ws://128.199.181.220:19085");
//        try{
//            wsService.connect();
//        } catch (ConnectException conne){
//            conne.printStackTrace();
//        }
//        topj = Topj.build(wsService);
        account = new Account("0x7243f2cd2c6ea8aa67908de7f5e660b89237684143f111d2be6b12818b7e38fa");
        account2 = topj.genAccount("0xbfcadcef5f7edcff0290f9379e6484eb3482fdf0455971d08a22827bf86fba5d");
    }

    @Test
    public void bitVpnTest() throws IOException {
        // bit vpn 中央账号，私钥需保存
        Account bitVpnAccount = topj.genAccount("0xf1c8d8027d1660f737c3267dd607e0e0feb4809bc97cebc2ff3d56cd32477d97");
//        Account bitVpnAccount = topj.genAccount();
        // 生成新用户私钥、公钥和地址
        Account user = topj.genAccount();
        // 获取中央账号的token，为后续发交易请求做准备。
        topj.requestToken(bitVpnAccount);
        TestCommon.createAccount(topj, bitVpnAccount);
        // 获取中央账号的accountInfo，方法实现里会获取用户最新nonce和lasthash
        // topj.accountInfo(bitVpnAccount);
        TestCommon.getAccountInfo(topj, bitVpnAccount);
        // 给新用户转账，新用户即被在链上创建，
        ResponseBase<XTransaction> transferResponseBase = topj.transfer(bitVpnAccount, user.getAddress(), BigInteger.valueOf(1000000), "bit vpn create users");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, bitVpnAccount);
        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(bitVpnAccount, transferResponseBase.getData().getTransactionHash());
        System.out.println("tx hash >> " + transferResponseBase.getData().getTransactionHash() + " > is success > " + accountTransaction.getData().isSuccess());
//        System.out.println(JSON.toJSONString(accountTransaction));
    }

    @Test
    public void getTx() throws IOException {
        Account bitVpnAccount = topj.genAccount("0x7243f2cd2c6ea8aa67908de7f5e660b89237684143f111d2be6b12818b7e38fa");
        topj.requestToken(bitVpnAccount);
        TestCommon.getAccountInfo(topj, bitVpnAccount);
        String txHash = "0x3075e0cc89a011c73f55f18e1d280746e45415e40b2f136a2f4e8bdf2dcad1e0";
        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(bitVpnAccount, txHash);
        XTransaction xTransaction = accountTransaction.getData();
        System.out.println("tx hash >> " + xTransaction.getTransactionHash() + " > is success > " + xTransaction.isSuccess());
    }

    @Test
    public void testPollTxResult() throws IOException  {
        account = topj.genAccount();
        account2 = topj.genAccount();

        topj.requestToken(account);
        ResponseBase<XTransaction> createAccountXt = topj.createAccount(account);
        System.out.println("createAccount transaction hash >> " + createAccountXt.getData().getTransactionHash());

        ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.accountInfo(account);
        System.out.println("accountInfo >>>>> ");
        System.out.println(JSON.toJSONString(accountInfoResponse2));

        ResponseBase<XTransaction> transferResponseBase = topj.transfer(account,account2.getAddress(), BigInteger.valueOf(500), "hello top");
        System.out.println(JSON.toJSONString(transferResponseBase.getData().getXx64Hash()));

        System.out.println("transfer is Success >> " + transferResponseBase.getData().isSuccess());

        accountInfoResponse2 = topj.accountInfo(account);
        System.out.println("accountInfo >>>>> ");
        System.out.println(JSON.toJSONString(accountInfoResponse2));

        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, transferResponseBase.getData().getTransactionHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getTransactionHash());
        System.out.println(JSON.toJSONString(accountTransaction));

        ResponseBase<UnitBlockResponse> unitBlockResult = topj.getLastUnitBlock(account);
        System.out.println("unitBlock result >>>>> ");
        System.out.println(JSON.toJSONString(unitBlockResult));
        System.out.printf(unitBlockResult.getData().getValue().getBody().getLightUnit().getLightUnitInput().getmObjectId().toString());
    }

    @Test
    public void testAccountInfo() throws IOException {
        topj.requestToken(account);
        topj.requestToken(account2);
        TestCommon.createAccount(topj, account);
        TestCommon.getAccountInfo(topj, account);
//        TestCommon.getAccountInfo(topj, account2);
        ResponseBase<XTransaction> transferResponseBase = topj.transfer(account,account2.getAddress(), BigInteger.valueOf(200), "hello top");
        TransferActionParam transferActionParam = new TransferActionParam();
        transferActionParam.decode(transferResponseBase.getData().getTargetAction().getActionParam());
        System.out.print(">>>>> transfer targetActionData >> ");
        System.out.println(JSON.toJSONString(transferActionParam));
        System.out.print(">>>>> transfer transaction >> ");
        System.out.println(JSON.toJSONString(transferResponseBase.getData().getXx64Hash()));
//
        try {
            Thread.sleep(10000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
//
        TestCommon.getAccountInfo(topj, account);
        TestCommon.getAccountInfo(topj, account2);
//        topj.getUnitBlock(account);
//
        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, transferResponseBase.getData().getTransactionHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getTransactionHash());
        System.out.println(JSON.toJSONString(accountTransaction));
    }

    @Test
    public void testNodeRegister() throws IOException {
        account = topj.genAccount();
        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> transferResponseBase = topj.nodeRegister(account, BigInteger.valueOf(10000), NodeType.edge);
        System.out.println(JSON.toJSONString(transferResponseBase));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, transferResponseBase.getData().getTransactionHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getTransactionHash());
        System.out.println(JSON.toJSONString(accountTransaction));
        System.out.println(JSON.toJSONString(accountTransaction.getData().isSuccess()));
    }

    @Test
    public void testNodeDeRegister() throws IOException {
        topj.requestToken(account);
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> transferResponseBase = topj.nodeDeRegister(account);
        System.out.println(JSON.toJSONString(transferResponseBase));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, transferResponseBase.getData().getTransactionHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getTransactionHash());
        System.out.println(JSON.toJSONString(accountTransaction));
    }

    @Test
    public void testGetProperty() throws IOException {
        topj.requestToken(account);
        TestCommon.getAccountInfo(topj, account);
        List<String> getPropertyParams = new ArrayList<>();
        getPropertyParams.add(XProperty.CONTRACT_REG_KEY);
        getPropertyParams.add(account.getAddress());
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, TopjConfig.getRegistration(), "map", getPropertyParams);
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }

    @Test
    public void testClaimReward() throws UnsupportedEncodingException, IOException {
        account = new Account("0x96ed4ff3c2c84fdf87ae5e6141544386b9de19c4d1c5257a95be4d93e5a10262");
        topj.requestToken(account);
        TestCommon.getAccountInfo(topj, account);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_DISK_KEY);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_DISK_KEY);

//        Map<String, BigInteger> voteInfo = new HashMap<>();
//        String nodeAddress = "T-0-LfVaEWJkZhqQ8skZcC5mrzbGb1STsmgUKC";
//        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
//        voteInfo.put("T-0-LLM1XS61PaQvETTQrLF2hHEw4y1G5JAPS7", BigInteger.valueOf(5000));
//        voteInfo.put("T-0-LTU7KxYkccK9C9W4SG3mXQ6bcXciSWFyJi", BigInteger.valueOf(5000));
//        ResponseBase<XTransaction> result = topj.setVote(account, voteInfo);

//        ResponseBase<XTransaction> result = topj.claimReward(account);
//        ResponseBase<XTransaction> accountTransaction = topj.accountTransaction(account, result.getData().getTransactionHash());
//        System.out.println("tx hash >> " + result.getData().getTransactionHash() + " > is success > " + accountTransaction.getData().isSuccess());
        TestCommon.getAccountInfo(topj, account);
    }
}
