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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class voteTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() throws IOException {
        HttpService httpService = new HttpService("http://142.93.30.153:19081");
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(30000, 30));
//        topj.setTransactionReceiptProcessor(new NoOpProcessor());
        account = topj.genAccount("R9Pd5vh/dyI3IkKQrUsYFVq8t2L44JWWf4PW+RwwgQE=");
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
        TestCommon.getAccountInfo(topj, account);

        // redeem vote
//        ResponseBase<XTransactionResponse> redeemTokenVote = topj.unStakeVote(account, new BigInteger("1000"));
//        System.out.println("un stake vote hash >> " + redeemTokenVote.getData().getOriginalTxInfo().getTxHash() + " > is success > " + redeemTokenVote.getData().isSuccess());
//        TestCommon.getAccountInfo(topj, account);

        // set vote
//        Map<String, BigInteger> voteInfo = new HashMap<>();
//        String nodeAddress = "T-0-LaFmRAybSKTKjE8UXyf7at2Wcw8iodkoZ8";
//        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
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
//        ResponseBase<VoteUsedResponse> r = topj.listVoteUsed(account);
//        ResponseBase<VoteUsedResponse> r = topj.listVoteUsed(account, "T00000LKF18dpN5yGuBBpg38ZQyC8vpdzy6YQfPe");
//        System.out.println("list vote used > " + r.getData().getVoteInfos().get("T00000LKF18dpN5yGuBBpg38ZQyC8vpdzy6YQfPe"));

        Map<String, String> result = Topj.generateV3Args("T80000968927100f3cb7b23e8d477298311648978d8613", Arrays.asList("f8a49466ab344963eaa071f9636faac26b0d1a399003259466ab344963eaa071f9636faac26b0d1a3990032586010203040506830304058801020304050607088831323334353637388b68656c6c6f20776f726c648d746f7020756e69742074657374b8410051a134afd1fc323b4477d774a249742860c0d200f874ad6f3299c5270304e7f501423897a3d8e1613d339102af7f3011f901d85b0f848a27434a261563e259ee"));

        System.out.println("generateV3Args > " + JSON.toJSONString(result));
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
