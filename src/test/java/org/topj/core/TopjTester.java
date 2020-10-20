package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.Proposal;
import org.topj.methods.response.*;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
import org.topj.procotol.websocket.WebSocketService;
import org.topj.tx.PollingTransactionReceiptProcessor;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.util.List;
import java.util.Map;

import static org.topj.core.TestCommon.getResourceFile;

public class TopjTester {

    private String host = "157.230.84.88";
//    private String host = "192.168.50.187";
    private String httpUrl = "http://" + host + ":19081";
    private String wsUrl = "ws://" + host + ":19085";

    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;

    @Before
    public void setUp() throws IOException {
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
        firstAccount = topj.genAccount("ff867b2ceb48f6bfc8a93d6c6aac05a29baad5da18ab5fb2bb9758379475fad8");
        secondAccount = topj.genAccount("f71f5cc46a2b42d6be2e6f98477313292bd4781d106c4129470dc6dc3d401702");
        topj.passport(firstAccount);
        ResponseBase<PassportResponse> s = topj.passport(secondAccount);
//        System.out.println(JSON.toJSONString(s));
//        System.out.println(secondAccount.getPrivateKey() + " >> " + firstAccount.getAddress());
//        ResponseBase<XTransactionResponse> xTransactionResponseBase = topj.createAccount(firstAccount);
//        System.out.println("create account hash >> " + xTransactionResponseBase.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + xTransactionResponseBase.getData().isSuccess());
//        if (xTransactionResponseBase.getErrNo() != 0) {
//            System.out.println("create account err > " + xTransactionResponseBase.getErrMsg());
//            return;
//        }
    }

    @Test
    public void ga() throws IOException {
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

        Proposal proposal = new Proposal();
        proposal.setProposalId("sss");
        proposal.setParameter("archive_deposit");
        proposal.setOrigValue("10000");
        proposal.setNewValue("26");
        proposal.setModificationDescription("ttt");
        proposal.setProposalClientAddress("T-0-1Kc3sQi7wiX9STHjCYMpxbER9daPXc7wNe");
        proposal.setDeposit(BigInteger.valueOf(400));
        proposal.setChainTimerHeight(BigInteger.valueOf(40));
        proposal.setUpdateType("update_action_parameter");
        proposal.setPriority(BigInteger.valueOf(3));
//        ResponseBase<XTransactionResponse> addProposalResult = topj.addProposal(firstAccount, proposal);
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
        ResponseBase<CGPResponse> cgp = topj.getCGP(firstAccount, "T-21-38QMHWxXshXyZa1E48JU1LREu3UrT5KGD2U@0");
        System.out.println("cgp > " + JSON.toJSONString(cgp));

        ResponseBase<ChainInfoResponse> chainInfo = topj.getChainInfo(firstAccount);
        System.out.println("chain info > " + JSON.toJSONString(chainInfo.getData()));
//        ResponseBase<NodeBaseInfo> standby = topj.getStandbys(firstAccount, "T-0-LKfBYfwTcNniDSQqj8fj5atiDqP8ZEJJv6");
//        System.out.println("standbys info > " + JSON.toJSONString(standby.getData()));
//        ResponseBase<StandBysResponse> standbys = topj.getAllStandbys(firstAccount);
//        System.out.println("standbys info > " + JSON.toJSONString(standbys.getData()));
        ResponseBase<ClockBlockResponse> timerInfo = topj.getClockBlock(firstAccount);
        System.out.println("timerInfo > " + JSON.toJSONString(timerInfo.getData()));
//        ResponseBase<EdgeStatusResponse> edgeStatus = topj.getEdgeStatus(firstAccount);
//        System.out.println("edge status > " + JSON.toJSONString(edgeStatus));
        ResponseBase<List<String>> edgeNeighbors = topj.getEdgeNeighbors(firstAccount);
        System.out.println("edge neighbors > " + JSON.toJSONString(edgeNeighbors));

    }

    @Test
    public void testNodeRegister() throws IOException {
        ResponseBase<XTransactionResponse> nodeRegisterResult;
        TestCommon.getAccountInfo(topj, firstAccount);

//        Base64.Encoder encoder = Base64.getEncoder();
//        String key = encoder.encodeToString(topj.genAccount().getPublicKey().getBytes());
//        nodeRegisterResult = topj.registerNode(firstAccount, BigInteger.valueOf(100000000000l), NodeType.edge, "nick", key);
//        System.out.println("node register hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        TestCommon.getTx(topj,secondAccount,"0xdf28d0cd7c6905c254c0ed84c720af90d5258e15bcab77e857c6efc605fa733f");

        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(firstAccount, firstAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

        ResponseBase<StandBysDetail> cnd = topj.getStandBys(firstAccount, "T-0-LKfBYfwTcNniDSQqj8fj5atiDqP8ZEJJv6");
        System.out.println("Candidate Node Detail info > " + JSON.toJSONString(cnd));

        ResponseBase<StandBysResponse> cnr = topj.getAllStandBys(firstAccount);
        System.out.println("all Candidate Node Detail info > " + JSON.toJSONString(cnr));

//        nodeRegisterResult = topj.updateNodeType(secondAccount, BigInteger.valueOf(1000000000000l), NodeType.advanced);
//        System.out.println("update node type hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        nodeRegisterResult = topj.setNodeName(secondAccount, "nick2");
//        System.out.println("setNickname hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.setDividendRate(secondAccount, BigInteger.valueOf(95));
//        System.out.println("setDividendRate hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.stakeDeposit(secondAccount, BigInteger.valueOf(5000));
//        System.out.println("stakeDeposit hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.unStakeDeposit(secondAccount, BigInteger.valueOf(3500));
//        System.out.println("unStakeDeposit hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
//
//        accountInfoResponseBase = topj.getAccount(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
//
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
        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(firstAccount, "T-0-LMSZRUSNomEq92G2giWsDLQZ93DPtfg3cy");
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
        ResponseBase<Map<String, NodeInfoResponse>> a = topj.queryAllNodeInfo(firstAccount);
        System.out.println("node info > " + JSON.toJSONString(a));

        ResponseBase<NodeRewardResponse> nodeRewardResult = topj.queryNodeReward(firstAccount, "T-0-La8cTjNyTEmspAyTbXEsMhRPN6U9A7JRvH");
        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));
        ResponseBase<Map<String, NodeRewardResponse>> all = topj.queryAllNodeReward(firstAccount);
        System.out.println("all node reward result > " + JSON.toJSONString(all));
//        TestCommon.getAccountInfo(topj, firstAccount);
//        Boolean result = topj.isTxSuccess(firstAccount, firstAccount.getLastHash());
//        System.out.println("last tx result > " + result);
    }

    @Test
    public void getTx() throws IOException {
        String hash = "0xe6327857e609a3f105ed96b1af523e2296d4490179cc14b7f99a97be52a940d3";
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
    public void contractTest() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());
        String codeStr = getResourceFile("opt_map.lua");
        ResponseBase<XTransactionResponse> transactionResponseBase = topj.deployContract(firstAccount, codeStr);
        if (transactionResponseBase.getErrNo() != 0) {
            System.out.println("contract publish err > " + transactionResponseBase.getErrMsg());
            return;
        }
        String contractAddress = transactionResponseBase.getData().getOriginalTxInfo().getReceiverAction().getTxReceiverAccountAddr();
        System.out.println("publish contract hash > " + transactionResponseBase.getData().getOriginalTxInfo().getTxHash());
        System.out.println("contract address > " + contractAddress);
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(firstAccount, contractAddress, "string", "temp_a");
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
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
