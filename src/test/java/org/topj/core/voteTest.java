package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.VoteUsedResponse;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.reward.VoterDividendResponse;
import org.topj.methods.response.tx.XTransactionResponse;
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
        HttpService httpService = new HttpService("http://192.168.50.120:19081");
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(30000, 30));
//        topj.setTransactionReceiptProcessor(new NoOpProcessor());
        account = topj.genAccount("4d613d40cb4c8917d9b1937f13b604e48f2efb08d0c61fcf8a7587c30cc6d9eb");
//        account = topj.genAccount();
        topj.passport(account);
//        TestCommon.createAccount(topj, account);

    }

    @Test
    public void pledgeRedeemVoteTest() throws IOException {
        // pledge vote
//        TestCommon.getAccountInfo(topj, account);
//        ResponseBase<XTransactionResponse> result = topj.stakeVote(account, new BigInteger("10002"), new BigInteger("30"));
//        System.out.println("stake vote hash >> " + result.getData().getOriginalTxInfo().getTxHash() + " > is success > " + result.getData().isSuccess());
//        TestCommon.getAccountInfo(topj, account);

        // redeem vote
//        ResponseBase<XTransactionResponse> redeemTokenVote = topj.unStakeVote(account, new BigInteger("1000"));
//        System.out.println("un stake vote hash >> " + redeemTokenVote.getData().getOriginalTxInfo().getTxHash() + " > is success > " + redeemTokenVote.getData().isSuccess());
//        TestCommon.getAccountInfo(topj, account);

        // set vote
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LaFmRAybSKTKjE8UXyf7at2Wcw8iodkoZ8";
        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
//        ResponseBase<XTransactionResponse> setVoteResult = topj.voteNode(account, voteInfo);
//        System.out.println("set vote hash >> " + setVoteResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + setVoteResult.getData().isSuccess());
//
//        TestCommon.getAccountInfo(topj, account);

        // cancel vote
//        voteInfo.put(nodeAddress, BigInteger.valueOf(200));
//        ResponseBase<XTransactionResponse> cancelVoteResult = topj.unVoteNode(account, voteInfo);
//        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + setVoteResult.getData().isSuccess());
//
//        TestCommon.getAccountInfo(topj, account);

//        account.setAddress("T-0-LeP9oXqB8uLCBNCd9BsfULUYhSyDknt1q2");
        ResponseBase<VoteUsedResponse> r = topj.listVoteUsed(account, "T-0-LPbGMaA1pCQ4o8M2P3ZpaEHyXPGti4osGt");
        System.out.println("list vote used > " + r.getData().getVoteInfos().get("T-0-LaFmRAybSKTKjE8UXyf7at2Wcw8iodkoZ8"));
    }

    @Test
    public void testGetReward() throws IOException {
//        ResponseBase<String> nodeRewardResult = topj.getNodeReward(account);
//        System.out.println("node reward result > " + JSON.toJSONString(nodeRewardResult));

//        TestCommon.getAccountInfo(topj, account);
        ResponseBase<VoterDividendResponse> voterRewardResult = topj.queryVoterDividend(account, "T00000La8UTDgQQVRBjkCWEsKYk1XRpjZSK3zUbM");
        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));

        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(account);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());

        ResponseBase<XTransactionResponse> claimRewardResult = topj.claimVoterDividend(account);
        System.out.println("node claim reward hash >> " + claimRewardResult.getData().getOriginalTxInfo().getTxHash() + " >> is success > " + claimRewardResult.getData().isSuccess());

        voterRewardResult = topj.queryVoterDividend(account, account.getAddress());
        System.out.println("voter reward result > " + JSON.toJSONString(voterRewardResult));

        accountInfoResponseBase = topj.getAccount(account);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddr() + " balance > " + accountInfoResponseBase.getData().getBalance());
    }

    @Test
    public void testCancelVote() throws IOException {
        // cancel vote
        TestCommon.getAccountInfo(topj, account);
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LLdWiAhUMyiXq39pUbSSRUdjNN6gQHb9bm";
        voteInfo.put(nodeAddress, BigInteger.valueOf(150));
        ResponseBase<XTransactionResponse> cancelVoteResult = topj.unVoteNode(account, voteInfo);
        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getOriginalTxInfo().getTxHash());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, account);
    }
}
