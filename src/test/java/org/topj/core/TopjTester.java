package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.property.NodeType;
import org.topj.methods.request.AccountInfo;
import org.topj.methods.response.*;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.reward.VoterRewardResponse;
import org.topj.procotol.http.HttpService;
import org.topj.tx.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

import static org.topj.core.TestCommon.getResourceFile;

public class TopjTester {

//    private String host = "192.168.50.35";
    private String host = "192.168.20.58";
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
//        System.out.println(firstAccount.getPrivateKey() + " >> " + secondAccount.getPrivateKey());
//        ResponseBase<XTransaction> xTransactionResponseBase = topj.createAccount(firstAccount);
//        if (xTransactionResponseBase.getErrNo() != 0) {
//            System.out.println("create account err > " + xTransactionResponseBase.getErrMsg());
//            return;
//        }
    }

    @Test
    public void testGetChainInfo() throws IOException {
        ResponseBase<ChainInfoResponse> chainInfo = topj.getChainInfo(firstAccount);
        System.out.println("chain info > " + JSON.toJSONString(chainInfo.getData()));
    }

    @Test
    public void testNodeRegister() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

        ResponseBase<XTransaction> nodeRegisterResult = topj.nodeRegister(firstAccount, BigInteger.valueOf(1000000), NodeType.advanced);
        System.out.println("node register hash >> " + nodeRegisterResult.getData().getTransactionHash() + " >> is success > " + nodeRegisterResult.getData().isSuccess());

//        ResponseBase<NodeInfoResponse> nodeInfo = topj.getNodeInfo(firstAccount, firstAccount.getAddress());
//        System.out.println("node info > " + JSON.toJSONString(nodeInfo));

//        accountInfoResponseBase = topj.accountInfo(firstAccount);
//        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
//
//        ResponseBase<XTransaction> nodeDeRegisterResult = topj.nodeDeRegister(firstAccount);
//        System.out.println("node deRegister hash >> " + nodeDeRegisterResult.getData().getTransactionHash() + " >> is success > " + nodeDeRegisterResult.getData().isSuccess());
    }

    @Test
    public void testGetNodeInfo() throws IOException {
        ResponseBase<NodeInfoResponse> nodeInfo = topj.getNodeInfo(firstAccount, firstAccount.getAddress());
        System.out.println("node info > " + JSON.toJSONString(nodeInfo));
    }

    @Test
    public void testGetReward() throws IOException {
        ResponseBase<NodeRewardResponse> nodeRewardResult = topj.getNodeReward(firstAccount, "T-0-LXjmqqMjLee8gRv2zQaLpUSdH8L3ewmAV9");
        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));

        ResponseBase<VoterRewardResponse> voterRewardResult = topj.getVoterReward(secondAccount, "T-0-LWEtvHrpc3JpzDB76LS9NtDok3KYs5etBq");
        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));
    }

    @Test
    public void contractTest() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
        String codeStr = getResourceFile("opt_map.lua");
        ResponseBase<XTransaction> transactionResponseBase = topj.publishContract(firstAccount, codeStr, 400000, 0, "", "test_tx");
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
