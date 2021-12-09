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
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
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
        HttpService httpService = new HttpService("http://161.35.98.159:19081");
        topj = Topj.build(httpService);
//        WebSocketService wsService = new WebSocketService("ws://192.168.10.29:19085");
////        WebSocketService wsService = new WebSocketService("ws://128.199.181.220:19085");
//        try{
//            wsService.connect();
//        } catch (ConnectException conne){
//            conne.printStackTrace();
//        }
//        topj = Topj.build(wsService);
        account = new Account("0x497a926c1ede4e2bb709b2de662c15cd886466609fa0c7cba513a7d21bb7023b");
        account2 = topj.genAccount("0xbfcadcef5f7edcff0290f9379e6484eb3482fdf0455971d08a22827bf86fba5d");
    }

    @Test
    public void bitVpnTest() throws IOException {
        // bit vpn 中央账号，私钥需保存
//        Account bitVpnAccount = topj.genAccount("0xf1c8d8027d1660f737c3267dd607e0e0feb4809bc97cebc2ff3d56cd32477d97");
        Account bitVpnAccount = topj.genAccount("0x63e21850eca851e8cbe4aed81dcb34b932d886bac7e847ea84e6f7d61fc2de05");
//        Account bitVpnAccount = topj.genAccount();
        // 生成新用户私钥、公钥和地址
        Account user = topj.genAccount();
        // 获取中央账号的token，为后续发交易请求做准备。
        topj.passport(bitVpnAccount);
//        TestCommon.createAccount(topj, bitVpnAccount);
        // 获取中央账号的accountInfo，方法实现里会获取用户最新nonce和lasthash
        // topj.accountInfo(bitVpnAccount);
        TestCommon.getAccountInfo(topj, bitVpnAccount);
        // 给新用户转账，新用户即被在链上创建，
        ResponseBase<XTransactionResponse> transferResponseBase = topj.transfer(bitVpnAccount, user.getAddress(), BigInteger.valueOf(1000000), "bit vpn create users");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, bitVpnAccount);
        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(bitVpnAccount, transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println("tx hash >> " + transferResponseBase.getData().getOriginalTxInfo().getTxHash() + " > is success > " + accountTransaction.getData().isSuccess());
//        System.out.println(JSON.toJSONString(accountTransaction));
    }

    @Test
    public void getTx() throws IOException {
        Account bitVpnAccount = topj.genAccount("0x7243f2cd2c6ea8aa67908de7f5e660b89237684143f111d2be6b12818b7e38fa");
        topj.passport(bitVpnAccount);
        TestCommon.getAccountInfo(topj, bitVpnAccount);
        String txHash = "0x3075e0cc89a011c73f55f18e1d280746e45415e40b2f136a2f4e8bdf2dcad1e0";
        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(bitVpnAccount, txHash);
        XTransactionResponse xTransaction = accountTransaction.getData();
        System.out.println("tx hash >> " + xTransaction.getOriginalTxInfo().getTxHash() + " > is success > " + xTransaction.isSuccess());
    }

    @Test
    public void testPollTxResult() throws IOException  {

        topj.passport(account);
        ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.getAccount(account);
        System.out.println("accountInfo >>>>> ");
        System.out.println(JSON.toJSONString(accountInfoResponse2));

//        ResponseBase<XTransactionResponse> transferResponseBase = topj.transfer(account,account2.getAddress(), BigInteger.valueOf(500), "hello top");
//
//        System.out.println("transfer is Success >> " + transferResponseBase.getData().isSuccess());
//
//        accountInfoResponse2 = topj.getAccount(account);
//        System.out.println("accountInfo >>>>> ");
//        System.out.println(JSON.toJSONString(accountInfoResponse2));
//
//        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(account, transferResponseBase.getData().getOriginalTxInfo().getTxHash());
//        System.out.println("accountTransaction >> " + transferResponseBase.getData().getOriginalTxInfo().getTxHash());
//        System.out.println(JSON.toJSONString(accountTransaction));

        ResponseBase<UnitBlockResponse> unitBlockResult = topj.getLastUnitBlock(account, account.getAddress());
        System.out.println("unitBlock result >>>>> ");
        System.out.println(JSON.toJSONString(unitBlockResult));
    }

    @Test
    public void testAccountInfo() throws IOException {
        topj.passport(account);
        topj.passport(account2);
        TestCommon.getAccountInfo(topj, account);

        ResponseBase<XTransactionResponse> stakeGasResult = topj.stakeVote(account, BigInteger.valueOf(1000), new BigInteger("30"));
        TestCommon.getTx(topj, account, stakeGasResult.getTxHash());
        TestCommon.getAccountInfo(topj, account);

//        TestCommon.getAccountInfo(topj, account2);
//        ResponseBase<XTransactionResponse> transferResponseBase = topj.transfer(account,account2.getAddress(), BigInteger.valueOf(200), "hello top");
//        TransferActionParam transferActionParam = new TransferActionParam();
//        transferActionParam.decode(transferResponseBase.getData().getOriginalTxInfo().getReceiverAction().getActionParam());
//        System.out.print(">>>>> transfer targetActionData >> ");
//        System.out.println(JSON.toJSONString(transferActionParam));
//        System.out.print(">>>>> transfer transaction >> ");
//        System.out.println(JSON.toJSONString(transferResponseBase.getData().getOriginalTxInfo().getXx64Hash()));
//
//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException es) {
//            es.printStackTrace();
//        }
//
//        TestCommon.getAccountInfo(topj, account);
//        TestCommon.getAccountInfo(topj, account2);
//        topj.getUnitBlock(account);
//
//        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(account, transferResponseBase.getData().getOriginalTxInfo().getTxHash());
//        System.out.println("accountTransaction >> " + transferResponseBase.getData().getOriginalTxInfo().getTxHash());
//        System.out.println(JSON.toJSONString(accountTransaction));
    }

    @Test
    public void testNodeRegister() throws IOException {
        topj.passport(account);
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransactionResponse> transferResponseBase = topj.registerNode(account, BigInteger.valueOf(100000000000l), NodeType.edge, "sawyer node", "key");
        System.out.println(JSON.toJSONString(transferResponseBase));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(account, transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println(JSON.toJSONString(accountTransaction));
        System.out.println(JSON.toJSONString(accountTransaction.getData().isSuccess()));
        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(account, account.getAddress());
        System.out.println(JSON.toJSONString(nodeInfo));
    }

    @Test
    public void testNodeDeRegister() throws IOException {
        topj.passport(account);
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransactionResponse> transferResponseBase = topj.unRegisterNode(account);
        System.out.println(JSON.toJSONString(transferResponseBase));
        try {
            Thread.sleep(2000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(account, transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println(JSON.toJSONString(accountTransaction));
        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(account, account.getAddress());
        System.out.println(JSON.toJSONString(nodeInfo));
    }

    @Test
    public void testGetProperty() throws IOException {
        topj.passport(account);
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
        topj.passport(account);
        TestCommon.getAccountInfo(topj, account);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_DISK_KEY);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_DISK_KEY);

//        Map<String, BigInteger> voteInfo = new HashMap<>();
//        String nodeAddress = "T-0-LfVaEWJkZhqQ8skZcC5mrzbGb1STsmgUKC";
//        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
//        voteInfo.put("T-0-LLM1XS61PaQvETTQrLF2hHEw4y1G5JAPS7", BigInteger.valueOf(5000));
//        voteInfo.put("T-0-LTU7KxYkccK9C9W4SG3mXQ6bcXciSWFyJi", BigInteger.valueOf(5000));
//        ResponseBase<XTransactionResponse> result = topj.setVote(account, voteInfo);

//        ResponseBase<XTransactionResponse> result = topj.claimReward(account);
//        ResponseBase<XTransactionResponse> accountTransaction = topj.accountTransaction(account, result.getData().getOriginalTxInfo().getTxHash());
//        System.out.println("tx hash >> " + result.getData().getOriginalTxInfo().getTxHash() + " > is success > " + accountTransaction.getData().isSuccess());
        TestCommon.getAccountInfo(topj, account);
    }
}
