package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.reward.VoterRewardResponse;
import org.topj.procotol.http.HttpService;
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
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(topj, 3000, 10));
        account = topj.genAccount("0xf1c8d8027d1660f737c3267dd607e0e0feb4809bc97cebc2ff3d56cd32477d97");
        topj.requestToken(account);
//        TestCommon.createAccount(topj, account);
//        System.out.println("private Key > " + account.getPrivateKey());
    }

    @Test
    public void pledgeRedeemVoteTest() throws IOException {
        // pledge vote
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> result = topj.pledgeTokenVote(account, new BigInteger("10002"), new BigInteger("30"), "");
        System.out.println("pledgeTokenVote transaction >> " + result.getData().getTransactionHash());
        TestCommon.getAccountInfo(topj, account);
//
//        // redeem vote
//        ResponseBase<XTransaction> redeemTokenVote = topj.redeemTokenVote(account, new BigInteger("1000"), "");
//
//        System.out.print("redeemTokenVote transaction >> " + redeemTokenVote.getData().getTransactionHash());
        TestCommon.getAccountInfo(topj, account);

        // set vote
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LYwrLA6c6US8KWVSZ1TjGGgq1YD9snLZcn";
        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
        ResponseBase<XTransaction> setVoteResult = topj.setVote(account, voteInfo);
//        System.out.println(JSON.toJSONString(setVoteResult));
        System.out.println("set vote hash >> " + setVoteResult.getData().getTransactionHash() + " >> is success > " + setVoteResult.getData().isSuccess());

//        TestCommon.getAccountInfo(topj, account);
//
//        // cancel vote
//        voteInfo.put(nodeAddress, BigInteger.valueOf(200));
//        ResponseBase<XTransaction> cancelVoteResult = topj.cancelVote(account, voteInfo);
//        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getTransactionHash());

        TestCommon.getAccountInfo(topj, account);
    }

    @Test
    public void testGetReward() throws IOException {
//        ResponseBase<String> nodeRewardResult = topj.getNodeReward(account);
//        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));

//        TestCommon.getAccountInfo(topj, account);
        ResponseBase<VoterRewardResponse> voterRewardResult = topj.getVoterReward(account, "T-0-LWEtvHrpc3JpzDB76LS9NtDok3KYs5etBq");
        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));
    }

    @Test
    public void testCancelVote() throws IOException {
        // cancel vote
        TestCommon.getAccountInfo(topj, account);
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LLdWiAhUMyiXq39pUbSSRUdjNN6gQHb9bm";
        voteInfo.put(nodeAddress, BigInteger.valueOf(150));
        ResponseBase<XTransaction> cancelVoteResult = topj.cancelVote(account, voteInfo);
        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getTransactionHash());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, account);
    }
}
