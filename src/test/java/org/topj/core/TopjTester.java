package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.Model.Proposal;
import org.topj.methods.property.NodeType;
import org.topj.methods.request.AccountInfo;
import org.topj.methods.response.*;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.reward.VoterRewardResponse;
import org.topj.procotol.http.HttpService;
import org.topj.tx.PollingTransactionReceiptProcessor;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.topj.core.TestCommon.getResourceFile;

public class TopjTester {

//    private String host = "192.168.50.35";
    private String host = "192.168.50.26";
    private String httpUrl = "http://" + host + ":19081";
    private String wsUrl = "ws://" + host + ":19085";

    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;

    @Before
    public void setUp() throws IOException {
        HttpService httpService = new HttpService(httpUrl);
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(topj, 3000, 10));
        firstAccount = topj.genAccount("fd0ba745fd120e072b3aa422aea589e44d71b55d6926c196278f75d91b958d91");
        secondAccount = topj.genAccount("f71f5cc46a2b42d6be2e6f98477313292bd4781d106c4129470dc6dc3d401702");
        topj.requestToken(firstAccount);
        topj.requestToken(secondAccount);
//        System.out.println(firstAccount.getPrivateKey() + " >> " + firstAccount.getPrivateKey());
//        ResponseBase<XTransaction> xTransactionResponseBase = topj.createAccount(firstAccount);
//        if (xTransactionResponseBase.getErrNo() != 0) {
//            System.out.println("create account err > " + xTransactionResponseBase.getErrMsg());
//            return;
//        }
    }

    @Test
    public void testProposal() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
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
//        ResponseBase<XTransaction> addProposalResult = topj.addProposal(firstAccount, proposal);
//        ResponseBase<XTransaction> addProposalResult = topj.withdrawProposal(firstAccount, proposal.getProposalId());
        String clientAddress1 = "T-0-Lh5GLYuH3Lf5h1zRoNYdpBgB918BYxJXDc";
        String clientAddress2 = "T-0-Lfn49TvyHq3itBg458JfX9vyJ5kMk4NbZm ";
        String clientAddress3 = "T-0-LVZYEUK41j53RJUrGdxYTYSWcMFtVVmrD5 ";
//        ResponseBase<XTransaction> addProposalResult = topj.voteProposal(firstAccount, proposal.getProposalId(), clientAddress3, true);
//        System.out.println("add proposal hash >> " + addProposalResult.getData().getTransactionHash() + " >> is success > " + addProposalResult.getData().isSuccess());

//        propertyResult = topj.getMapProperty(firstAccount, "T-x-qZV6Nm6HdynbTPHwaGWj96cZyevzsyWHsU", Arrays.asList("onchain_params", "archive_deposit"));
//        System.out.println("onchain_params > " + JSON.toJSONString(propertyResult.getData()));

        propertyResult = topj.getProposal(firstAccount, "sss");
        System.out.println("my proposal > " + JSON.toJSONString(propertyResult.getData()));
    }

    @Test
    public void testGetChainInfo() throws IOException {
        ResponseBase<ChainInfoResponse> chainInfo = topj.getChainInfo(firstAccount);
        System.out.println("chain info > " + JSON.toJSONString(chainInfo.getData()));
    }

    @Test
    public void testNodeRegister() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(secondAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

        ResponseBase<XTransaction> nodeRegisterResult = topj.nodeRegister(secondAccount, BigInteger.valueOf(1000000), NodeType.advanced);
        System.out.println("node register hash >> " + nodeRegisterResult.getData().getTransactionHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

        ResponseBase<NodeInfoResponse> nodeInfo = topj.getNodeInfo(firstAccount, firstAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

//        accountInfoResponseBase = topj.accountInfo(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
//
//        ResponseBase<XTransaction> nodeDeRegisterResult = topj.nodeDeRegister(firstAccount);
//        System.out.println("node deRegister hash >> " + nodeDeRegisterResult.getData().getTransactionHash() + " >> is success > " + nodeDeRegisterResult.getData().isSuccess());
    }

    @Test
    public void testRedeem() throws IOException {
        ResponseBase<NodeInfoResponse> nodeInfo = topj.getNodeInfo(secondAccount, secondAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(secondAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

        ResponseBase<XTransaction> claimRewardResult = topj.redeem(secondAccount);
        System.out.println("node redeem hash >> " + claimRewardResult.getData().getTransactionHash() + " >> is success > " + claimRewardResult.getData().isSuccess());
        accountInfoResponseBase = topj.accountInfo(secondAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
    }

    @Test
    public void testGetNodeInfo() throws IOException {
        ResponseBase<NodeInfoResponse> nodeInfo = topj.getNodeInfo(firstAccount, firstAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
    }

    @Test
    public void getTx() throws IOException {
        String hash = "0x41fcec0114527119bfec80f99a070acf7a1cd8849e1f7da0f8441f46875d4437";
        ResponseBase<XTransaction> tx = topj.accountTransaction(firstAccount, hash);
        System.out.println("tx success > " + tx.getData().isSuccess());
        System.out.println("tx > " + JSON.toJSONString(tx));
    }

    @Test
    public void testGetReward() throws IOException {
        ResponseBase<NodeRewardResponse> nodeRewardResult = topj.getNodeReward(firstAccount, firstAccount.getAddress());
        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));

//        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

//        ResponseBase<XTransaction> claimRewardResult = topj.claimNodeReward(firstAccount);
//        System.out.println("node claim reward hash >> " + claimRewardResult.getData().getTransactionHash() + " >> is success > " + claimRewardResult.getData().isSuccess());
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
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
        String codeStr = getResourceFile("opt_map.lua");
        ResponseBase<XTransaction> transactionResponseBase = topj.publishContract(firstAccount, codeStr, BigInteger.valueOf(400000));
        if (transactionResponseBase.getErrNo() != 0) {
            System.out.println("contract publish err > " + transactionResponseBase.getErrMsg());
            return;
        }
        String contractAddress = transactionResponseBase.getData().getTargetAction().getAccountAddr();
        System.out.println("publish contract hash > " + transactionResponseBase.getData().getTransactionHash());
        System.out.println("contract address > " + contractAddress);
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(firstAccount, contractAddress, "string", "temp_a");
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }
}
