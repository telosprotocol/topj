package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.Proposal;
import org.topj.methods.property.NodeType;
import org.topj.methods.response.*;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
import org.topj.tx.NoOpProcessor;
import org.topj.tx.PollingTransactionReceiptProcessor;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.Date;
import java.util.List;

import static org.topj.core.TestCommon.getResourceFile;

public class TopjTester {

    private String host = "192.168.50.193";
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
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(30000, 10));
//        topj.setTransactionReceiptProcessor(new NoOpProcessor(topj));
        firstAccount = topj.genAccount("fd0ba745fd120e072b3aa422aea589e44d71b55d6926c196278f75d91b958d91");
        secondAccount = topj.genAccount("f71f5cc46a2b42d6be2e6f98477313292bd4781d106c4129470dc6dc3d401702");
        topj.passport(firstAccount);
        ResponseBase<PassportResponse> s = topj.passport(secondAccount);
//        System.out.println(JSON.toJSONString(s));
//        System.out.println(secondAccount.getPrivateKey() + " >> " + firstAccount.getAddress());
//        ResponseBase<XTransactionResponse> xTransactionResponseBase = topj.createAccount(secondAccount);
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
//        ResponseBase<ChainInfoResponse> chainInfo = topj.getChainInfo(firstAccount);
//        System.out.println("chain info > " + JSON.toJSONString(chainInfo.getData()));
//        ResponseBase<EdgeStatusResponse> edgeStatus = topj.getEdgeStatus(firstAccount);
//        System.out.println("edge status > " + JSON.toJSONString(edgeStatus));
        ResponseBase<List<String>> edgeNeighbors = topj.getEdgeNeighbors(firstAccount);
        System.out.println("edge neighbors > " + JSON.toJSONString(edgeNeighbors));

    }

    @Test
    public void testNodeRegister() throws IOException {
        ResponseBase<XTransactionResponse> nodeRegisterResult;
        TestCommon.getAccountInfo(topj, secondAccount);

//        Base64.Encoder encoder = Base64.getEncoder();
//        String key = encoder.encodeToString(topj.genAccount().getPublicKey().getBytes());
//        nodeRegisterResult = topj.registerNode(secondAccount, BigInteger.valueOf(100000000000l), NodeType.edge, "nick", key);
//        System.out.println("node register hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        TestCommon.getTx(topj,secondAccount,"0xdf28d0cd7c6905c254c0ed84c720af90d5258e15bcab77e857c6efc605fa733f");

        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

//        nodeRegisterResult = topj.updateNodeType(secondAccount, BigInteger.valueOf(1000000000000l), NodeType.advanced);
//        System.out.println("update node type hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());
//
//        nodeRegisterResult = topj.setNodeName(secondAccount, "nick2");
//        System.out.println("setNickname hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        topj.getAccount(secondAccount);
        nodeRegisterResult = topj.setDividendRate(secondAccount, BigInteger.valueOf(95));
        System.out.println("setDividendRate hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        topj.getAccount(secondAccount);
//        nodeRegisterResult = topj.stakeDeposit(secondAccount, BigInteger.valueOf(5000));
//        System.out.println("stakeDeposit hash >> " + nodeRegisterResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

        nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

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
        ResponseBase<NodeInfoResponse> nodeInfo = topj.queryNodeInfo(secondAccount, secondAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

        ResponseBase<NodeRewardResponse> nodeRewardResult = topj.queryNodeReward(secondAccount, secondAccount.getAddress());
        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));
    }

    @Test
    public void getTx() throws IOException {
        String hash = "0xf0bc34ff1dbb0be4296b4178109b9a7c4585cad3c17b3983551e092335a0276a";
        ResponseBase<XTransactionResponse> tx = topj.getTransaction(firstAccount, hash);
        System.out.println("tx success > " + tx.getData().isSuccess());
        System.out.println("tx > " + JSON.toJSONString(tx));
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
        ResponseBase<XTransactionResponse> transactionResponseBase = topj.deployContract(firstAccount, codeStr, BigInteger.valueOf(400000));
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
}
