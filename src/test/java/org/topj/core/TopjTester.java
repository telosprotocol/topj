package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.Proposal;
import org.topj.methods.property.NodeType;
import org.topj.methods.response.*;
import org.topj.methods.response.block.TableBlockResponse;
import org.topj.methods.response.block.UnitBlockResponse;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
import org.topj.procotol.websocket.WebSocketService;
import org.topj.tx.PollingTransactionReceiptProcessor;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.URISyntaxException;
import java.util.Base64;
import java.util.List;
import java.util.Map;

import static org.topj.core.TestCommon.getResourceFile;

public class TopjTester {

    private String host = "bounty.grpc.topnetwork.org";
    private String httpUrl = "http://" + host + ":19081";
    private String wsUrl = "ws://" + host + ":19085";

    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;

    @Before
    public void setUp() throws IOException, URISyntaxException {
//        String serverUrl = topj.getDefaultServerUrl();
        HttpService httpService = new HttpService(httpUrl);
        topj = Topj.build(httpService);
//        WebSocketService wsService = new WebSocketService(wsUrl);
//        try{
//            wsService.connect();
//        } catch (ConnectException conn){
//            conn.printStackTrace();
//            return;
//        }
//        topj = Topj.build(wsService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(30000, 10));
//        topj.setTransactionReceiptProcessor(new NoOpProcessor());
        firstAccount = topj.genAccount("0x1199413ef66195dd25ed51cd832e7dd0ecd817f3b47a907b424afb7a15f48a61");
//        secondAccount = topj.genAccount("f71f5cc46a2b42d6be2e6f98477313292bd4781d106c4129470dc6dc3d401702");
        topj.passport(firstAccount);
//        ResponseBase<PassportResponse> s = topj.passport(secondAccount);

//        Base64.Decoder decoder = Base64.getDecoder();
//        String text = "ViozcHV2UwMhzmwZRt1LOs05bxTfa+VevqynkjOAxAQ=";
//        byte[] bytes = decoder.decode(text);
//        String ps = StringUtils.bytesToHex(bytes);
//        Account b6 = topj.genAccount(ps);
//        System.out.println("> " + ps + " > " + b6.getPublicKey() + " > " + b6.getAddress());
//
//        BigInteger lh = new BigInteger("17791961111430640000");
//        String lhex = lh.toString(16);
//        System.out.println(lhex);
    }

    @Test
    public void blockTest() throws IOException {
        topj.getAccount(secondAccount);
//        topj.transfer(secondAccount, firstAccount.getAddress(), BigInteger.valueOf(110), "");
        ResponseBase<TableBlockResponse> ubr = topj.getLastTableBlock(secondAccount, secondAccount.getAddress());
        System.out.println("unit block >> " + JSON.toJSONString(ubr));
    }

    @Test
    public void ga() throws IOException {
        firstAccount.setAddress("T800000655d2406485c6c39c1b2cc2e3fd7006894da9d5");
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());

        ResponseBase<XTransactionResponse> result =  topj.transfer(firstAccount, secondAccount.getAddress(), new BigInteger("140"), "1231fsd");
        System.out.println("transfer result > " + JSON.toJSONString(result));

        accountInfoResponseBase = topj.getAccount(firstAccount, secondAccount.getAddress());
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());

    }

    @Test
    public void testProposal() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());
        ResponseBase<GetPropertyResponse> propertyResult = topj.getMapProperty(firstAccount, TopjConfig.getBeaconCgcAddress(), "onchain_params", "archive_deposit");
        System.out.println("onchain_params > " + JSON.toJSONString(propertyResult.getData()));

        ResponseBase<XTransactionResponse> addProposalResult = topj.submitProposal(firstAccount, BigInteger.TEN,
                "T-!-Ebj8hBvoLdvcEEUwNZ423zM3Kh9d4nL1Ug", "T-!-Ebj8hBvoLdvcEEUwNZ423zM3Kh9d4nL1Ua", BigInteger.valueOf(100000000l), BigInteger.valueOf(18400));
//        ResponseBase<XTransactionResponse> addProposalResult = topj.withdrawProposal(firstAccount, proposal.getProposalId());
        String clientAddress1 = "T-0-Lh5GLYuH3Lf5h1zRoNYdpBgB918BYxJXDc";
        String clientAddress2 = "T-0-Lfn49TvyHq3itBg458JfX9vyJ5kMk4NbZm ";
        String clientAddress3 = "T-0-LVZYEUK41j53RJUrGdxYTYSWcMFtVVmrD5 ";
//        ResponseBase<XTransactionResponse> addProposalResult = topj.voteProposal(firstAccount, proposal.getProposalId(), clientAddress3, true);
//        System.out.println("add proposal hash >> " + addProposalResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + addProposalResult.getData().isSuccess());

//        propertyResult = topj.getMapProperty(firstAccount, "T-x-qZV6Nm6HdynbTPHwaGWj96cZyevzsyWHsU", Arrays.asList("onchain_params", "archive_deposit"));
//        System.out.println("onchain_params > " + JSON.toJSONString(propertyResult.getData()));

        ResponseBase<Proposal> proposalResponseBase = topj.queryProposal(firstAccount, "sss");
        System.out.println("my proposal > " + JSON.toJSONString(proposalResponseBase.getData()));
    }

    @Test
    public void testGetChainInfo() throws IOException {
//        ResponseBase<CGPResponse> cgp = topj.getCGP(firstAccount);
//        System.out.println("cgp > " + JSON.toJSONString(cgp));

        ResponseBase<List<BigInteger>> latestTables = topj.getLatestTables(firstAccount);
        System.out.println("latestTables info > " + JSON.toJSONString(latestTables.getData()));

        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(firstAccount);
        System.out.println("account > " + JSON.toJSONString(accountInfoResponseBase));

        ResponseBase<TableBlockResponse> ubr = topj.getTableBlockByHeight(secondAccount, TopjConfig.getShardingTableBlockAddr() + "@3", 113);
        System.out.println("table block >> " + JSON.toJSONString(ubr));

//        ResponseBase<ChainInfoResponse> chainInfo = topj.getChainInfo(firstAccount);
//        System.out.println("chain info > " + JSON.toJSONString(chainInfo.getData()));
//        ResponseBase<StandBysDetail> standby = topj.getStandBys(firstAccount, "T00000LM7EeZRkwpHhrLG54CeTZ5VawQdBxmKxAA");
//        System.out.println("standbys info > " + JSON.toJSONString(standby.getData()));
//        ResponseBase<StandBysResponse> standbys = topj.getAllStandBys(firstAccount);
//        System.out.println("standbys info > " + JSON.toJSONString(standbys.getData()));
////        ResponseBase<StandBysResponse> standbys = topj.listVoteUsed(firstAccount);
////        System.out.println("standbys info > " + JSON.toJSONString(standbys.getData()));
//        ResponseBase<ClockBlockResponse> timerInfo = topj.getClockBlock(firstAccount);
//        System.out.println("timerInfo > " + JSON.toJSONString(timerInfo.getData()));
////        ResponseBase<EdgeStatusResponse> edgeStatus = topj.getEdgeStatus(firstAccount);
////        System.out.println("edge status > " + JSON.toJSONString(edgeStatus));
//        ResponseBase<List<String>> edgeNeighbors = topj.getEdgeNeighbors(firstAccount);
//        System.out.println("edge neighbors > " + JSON.toJSONString(edgeNeighbors));

    }

    @Test
    public void testNodeRegister() throws IOException {
        ResponseBase<XTransactionResponse> nodeRegisterResult;
        TestCommon.getAccountInfo(topj, firstAccount);

//        Base64.Encoder encoder = Base64.getEncoder();
//        String key = encoder.encodeToString(firstAccount.getPublicKey().getBytes());
//        System.out.println(key);
//        String key = encoder.encodeToString(topj.genAccount().getPublicKey().getBytes());
        String key = "BC5AYAe8U2jvK0ycGpm0PE1WUYNrjw80VR1bSokH32fv9HLs75hFSfPehFDQkQocFARy66mmxj6rRELXoCpi1TQ=";

        nodeRegisterResult = topj.registerNode(firstAccount, BigInteger.valueOf(1000000000000l), NodeType.advance, "jode11", key,BigInteger.ZERO);
        System.out.println("node register hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        TestCommon.getTx(topj,secondAccount,"0xdf28d0cd7c6905c254c0ed84c720af90d5258e15bcab77e857c6efc605fa733f");

//        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(firstAccount, "T00000Len3LsArY8TwzQ9yWfejA1ZcdRnsxP4r4W");
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
//
//        ResponseBase<String> getIssuanceDetailResult = topj.getIssuanceDetail(firstAccount, 4275l);
//
//        ResponseBase<StandBysDetail> cnd = topj.getStandBys(firstAccount, "T00000LWuAfQogkTN8tyuKJRoHBDtsmaZaqVhXrs");
//        System.out.println("Candidate Node Detail info > " + JSON.toJSONString(cnd));

//        ResponseBase<StandBysResponse> cnr = topj.getAllStandBys(firstAccount);
//        System.out.println("all Candidate Node Detail info > " + JSON.toJSONString(cnr));

//        nodeRegisterResult = topj.updateNodeType(secondAccount, BigInteger.valueOf(1000000000000l), NodeType.advance);
//        System.out.println("update node type hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        nodeRegisterResult = topj.setNodeName(secondAccount, "nick2");
//        System.out.println("setNickname hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.setDividendRate(secondAccount, BigInteger.valueOf(95));
//        System.out.println("setDividendRate hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.stakeDeposit(secondAccount, BigInteger.valueOf(5000));
//        System.out.println("stakeDeposit hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
//
//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.unStakeDeposit(secondAccount, BigInteger.valueOf(3500));
//        System.out.println("unStakeDeposit hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

//        accountInfoResponseBase = topj.getAccount(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

//        ResponseBase<XTransactionResponse> nodeDeRegisterResult = topj.unRegisterNode(secondAccount);
//        System.out.println("node deRegister hash >> " + nodeDeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeDeRegisterResult.getData().isSuccess());
//
//        nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
    }

    @Test
    public void testRedeem() throws IOException {
        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(secondAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());

//        ResponseBase<XTransactionResponse> claimRewardResult = topj.redeemNodeDeposit(secondAccount);
//        System.out.println("node redeem hash >> " + claimRewardResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + claimRewardResult.getData().isSuccess());
//        accountInfoResponseBase = topj.getAccount(secondAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());
    }

    @Test
    public void testGetNodeInfo() throws IOException {
//        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(firstAccount, "T-0-LMSZRUSNomEq92G2giWsDLQZ93DPtfg3cy");
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
//        ResponseBase<Map<String, NodeInfoResponse>> a = topj.queryAllNodeInfo(firstAccount);
//        System.out.println("node info > " + JSON.toJSONString(a));

//        ResponseBase<NodeRewardResponse> nodeRewardResult = topj.queryNodeReward(firstAccount, "T-0-La8cTjNyTEmspAyTbXEsMhRPN6U9A7JRvH");
//        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));
        ResponseBase<Map<String, NodeRewardResponse>> all = topj.queryAllNodeReward(firstAccount);
        System.out.println("all node reward result > " + JSON.toJSONString(all));
//        TestCommon.getAccountInfo(topj, firstAccount);
//        Boolean result = topj.isTxSuccess(firstAccount, firstAccount.getLastHash());
//        System.out.println("last tx result > " + result);
    }

    @Test
    public void getTx() throws IOException {
        String hash = "0xfac0e39c94562d1b61cc3e2ab6255651fe9d4ea9dc3d897e588c44d75afe0c94";
        ResponseBase<XTransactionResponse> tx = topj.getTransaction(firstAccount, hash);
        System.out.println("tx success > " + tx.getData().isSuccess());
        System.out.println("tx > " + JSON.toJSONString(tx));
//        Account a = new Account();
//        topj.passport(a);
//        ResponseBase<XTransactionResponse> result = topj.createAccount(a);
//        for (int i=0;i<200;i++) {
//            String txStatus = topj.getTxStatus(firstAccount,result.getData().getOriginalTxInfo().getTxHash());
//            System.out.println("txStatus >> "+ txStatus);
//            if ("success".equals(txStatus)) {
//                topj.getAccount(a);
//                result = topj.transfer(a,topj.genAccount().getAddress(), BigInteger.valueOf(150), "hello top");
//            }
//            try {
//                Thread.sleep(2000);
//            } catch (InterruptedException es) {
//                es.printStackTrace();
//            }
//        }
    }

    @Test
    public void testGetReward() throws IOException {
        ResponseBase<NodeRewardResponse> nodeRewardResult = topj.queryNodeReward(secondAccount, secondAccount.getAddress());
        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));

//        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

//        ResponseBase<XTransactionResponse> claimRewardResult = topj.claimNodeReward(firstAccount);
//        System.out.println("node claim reward hash >> " + claimRewardResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + claimRewardResult.getData().isSuccess());
//
//        nodeRewardResult = topj.getNodeReward(firstAccount, firstAccount.getAddress());
//        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));
//
//        accountInfoResponseBase = topj.accountInfo(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

//        ResponseBase<VoterRewardResponse> voterRewardResult = topj.getVoterReward(secondAccount, secondAccount.getAddress());
//        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));
    }

    @Test
    public void tokenTest() throws IOException {
        for (int i=0;i<1000;i++){
            Account a = new Account();
            System.out.println(" > " + i);
            ResponseBase<PassportResponse> pp = topj.passport(a);
            try {
                Thread.sleep(10);
            } catch (InterruptedException es) {
                es.printStackTrace();
            }
            String at = i%2 == 0 ? firstAccount.getAddress() : secondAccount.getAddress();
            ResponseBase<AccountInfoResponse> air = topj.getAccount(a, at);
            try {
                Thread.sleep(10);
            } catch (InterruptedException es) {
                es.printStackTrace();
            }
            topj.queryNodeInfo(a, "T-0-LKQL3wAHtqHwbocbZRyobXUbaff94H6tCd");
        }
    }
}
