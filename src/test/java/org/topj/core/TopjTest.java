package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.account.property.AccountUtils;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.property.NodeType;
import org.topj.methods.property.XProperty;
import org.topj.methods.request.TransferOffLine;
import org.topj.methods.response.*;
import org.topj.methods.response.block.UnitBlockResponse;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
import org.topj.utils.ArgsUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TopjTest {
    private Topj topj = null;
    HttpService httpService = null;
    private Account account = null;
    private Account account2 = null;

    @Before
    public void setUp() throws IOException {
        httpService = new HttpService("http://206.189.210.106:19081");
        topj = Topj.build(httpService);
//        WebSocketService wsService = new WebSocketService("ws://192.168.10.29:19085");
////        WebSocketService wsService = new WebSocketService("ws://128.199.181.220:19085");
//        try{
//            wsService.connect();
//        } catch (ConnectException conne){
//            conne.printStackTrace();
//        }
//        topj = Topj.build(wsService);
        account = new Account("0x4d5a32d607aa2367315e142f75b38e13c6b9a7ec1c887d0eb04e235b23986507");
        account2 = topj.genAccount();
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

        ResponseBase<XTransactionResponse> transferResponseBase = topj.transfer(account,account2.getAddress(), BigInteger.valueOf(500), "v2 transfer top");

        System.out.println("transfer is Success >> " + transferResponseBase.getData().isSuccess());

        accountInfoResponse2 = topj.getAccount(account);
        System.out.println("accountInfo >>>>> ");
        System.out.println(JSON.toJSONString(accountInfoResponse2));

        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(account, transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println("accountTransaction >> " + transferResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println(JSON.toJSONString(accountTransaction));

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
        ResponseBase<XTransactionResponse> transferResponseBase = topj.registerNode(account, BigInteger.valueOf(100000000000l), NodeType.edge, "sawyer node", "key",BigInteger.ZERO);
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

    @Test
    public void testGenAccountByTable() throws UnsupportedEncodingException, IOException {
        long st = System.currentTimeMillis();
       Account account = AccountUtils.genAccount(0);
       System.out.println("t:" + (System.currentTimeMillis() -st ) + " address :" +account.getAddress());
    }

    @Test
    public void testTransferOffLine() throws UnsupportedEncodingException, IOException {
        // 1.离线创建指定table的地址,table 范围[0,63]
        Account offlineAccount =AccountUtils.genAccount(0);
        System.out.println("account address > " + offlineAccount.getAddress());
        System.out.println("account public key > " + offlineAccount.getPublicKey());
        System.out.println("account privateKey > " + offlineAccount.getPrivateKey());

        //2.通过地址获取nonce,需要连接节点
        Account account=new Account();
        account.setAddress("T80000f4d41bf4035e972649a8168ba06a15fa19a15ded");
        topj.passport(account);
        ResponseBase<AccountInfoResponse> accountResult = topj.getAccount(account);
        System.out.println("当前账户 nonce > "+accountResult.getData().getNonce());

        //3.构建离线交易 私钥对应2 的地址（T80000f4d41bf4035e972649a8168ba06a15fa19a15ded）
        Account sendAccount=new Account("0x638785b5e9bb9271f031f6ef852e3d5f33b9f46bff6d920b8622d44e69d6666f");
        //获取地址
        System.out.println("SendAccount > "+sendAccount.getAddress());
        //构建离线交易体需要先设置nonce
        sendAccount.setNonce(accountResult.getData().getNonce());
        TransferOffLine transfer = new TransferOffLine();
        TransferParams transferParams = new TransferParams(offlineAccount.getAddress(), BigInteger.valueOf(1000000), "hello");
        Map<String, String> requestTokenArgsMap = transfer.getArgs(sendAccount, Arrays.asList(transferParams));
        System.out.println("离线交易体 > "+requestTokenArgsMap);
        //4.发送交易
        ResponseBase<XTransactionResponse> result = httpService.send(requestTokenArgsMap, XTransactionResponse.class);
        XTransaction xTransaction = ArgsUtils.decodeXTransFromArgs(requestTokenArgsMap);
        XTransactionResponse xTransactionResponse = new XTransactionResponse();
        xTransactionResponse.setOriginalTxInfo(xTransaction);
        result.setData(xTransactionResponse);
        System.out.println(result.getData().getOriginalTxInfo().getTxHash());
    }

    @Test
    public void getStandy() throws UnsupportedEncodingException, IOException{
        Account account = new Account();
        account.setIdentityToken("123");
        ResponseBase<StandBysResponse> all = topj.getAllStandBys(account);
        System.out.println(all.getData().getFullNode());
        ResponseBase<StandBysDetail> detail = topj.getStandBys(account, "T800000fe304a43d35321e39d2c9b10183c10ed941d687");
        System.out.println(detail.getData().getConsensusPublicKey());
    }
}
