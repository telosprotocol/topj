package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.reward.VoterDividendResponse;
import org.topj.procotol.http.HttpService;
import org.topj.tx.NoOpProcessor;
import org.topj.tx.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class voteTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() throws IOException {
        HttpService httpService = new HttpService("http://192.168.50.26:19081");
        topj = Topj.build(httpService);
//        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(topj, 30000, 10));
        topj.setTransactionReceiptProcessor(new NoOpProcessor());
        account = topj.genAccount("4d613d40cb4c8917d9b1937f13b604e48f2efb08d0c61fcf8a7587c30cc6d9eb");
        topj.passport(account);
//        TestCommon.createAccount(topj, account);
//        System.out.println("private Key > " + account.getPrivateKey());
    }

    @Test
    public void pledgeRedeemVoteTest() throws IOException {
        // pledge vote
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> result = topj.stakeVote(account, new BigInteger("10002"), new BigInteger("30"), "");
        System.out.println("pledgeTokenVote transaction >> " + result.getData().getTxHash());
        TestCommon.getAccountInfo(topj, account);
//
//        // redeem vote
//        ResponseBase<XTransaction> redeemTokenVote = topj.redeemTokenVote(account, new BigInteger("1000"), "");
//
//        System.out.print("redeemTokenVote transaction >> " + redeemTokenVote.getData().getTxHash());
        TestCommon.getAccountInfo(topj, account);

        // set vote
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LazNzvyHLptzdPFkaynNHKqDY4qXZ2gCVh";
        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
        ResponseBase<XTransaction> setVoteResult = topj.voteNode(account, voteInfo);
//        System.out.println(JSON.toJSONString(setVoteResult));
        System.out.println("set vote hash >> " + setVoteResult.getData().getTxHash() + " >> is success > " + setVoteResult.getData().isSuccess());

//        TestCommon.getAccountInfo(topj, account);
//
//        // cancel vote
//        voteInfo.put(nodeAddress, BigInteger.valueOf(200));
//        ResponseBase<XTransaction> cancelVoteResult = topj.cancelVote(account, voteInfo);
//        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getTxHash());

        TestCommon.getAccountInfo(topj, account);
    }

    @Test
    public void testGetReward() throws IOException {
//        ResponseBase<String> nodeRewardResult = topj.getNodeReward(account);
//        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));

//        TestCommon.getAccountInfo(topj, account);
        ResponseBase<VoterDividendResponse> voterRewardResult = topj.queryVoterDividend(account, account.getAddress());
        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));

        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(account);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());

        ResponseBase<XTransaction> claimRewardResult = topj.claimVoterDividend(account);
        System.out.println("node claim reward hash >> " + claimRewardResult.getData().getTxHash() + " >> is success > " + claimRewardResult.getData().isSuccess());

        voterRewardResult = topj.queryVoterDividend(account, account.getAddress());
        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));

        accountInfoResponseBase = topj.getAccount(account);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
    }

    @Test
    public void testCancelVote() throws IOException {
        // cancel vote
        TestCommon.getAccountInfo(topj, account);
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LLdWiAhUMyiXq39pUbSSRUdjNN6gQHb9bm";
        voteInfo.put(nodeAddress, BigInteger.valueOf(150));
        ResponseBase<XTransaction> cancelVoteResult = topj.unVoteNode(account, voteInfo);
        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getTxHash());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, account);
    }
}
