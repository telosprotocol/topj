package org.topj.core;

import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

public class voteTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp(){
        HttpService httpService = new HttpService("http://192.168.20.27:19081");
        topj = Topj.build(httpService);
        account = topj.genAccount("a01decfa081c741d05fa56ef58f48861c7d539a717533c7026123fb297bd08b7");
        topj.requestToken(account);
        TestCommon.createAccount(topj, account);
        System.out.println("private Key > " + account.getPrivateKey());
    }

    @Test
    public void pledgeRedeemVoteTest() throws IOException {
        // pledge vote
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransaction> result = topj.pledgeTokenVote(account, new BigInteger("10002"), new BigInteger("30"), "");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        System.out.print("pledgeTokenVote transaction >> " + result.getData().getTransactionHash());
        TestCommon.getAccountInfo(topj, account);

        // redeem vote
        ResponseBase<XTransaction> redeemTokenVote = topj.redeemTokenVote(account, new BigInteger("1000"), "");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        System.out.print("redeemTokenVote transaction >> " + redeemTokenVote.getData().getTransactionHash());
        TestCommon.getAccountInfo(topj, account);

        // set vote
        Map<String, BigInteger> voteInfo = new HashMap<>();
        String nodeAddress = "T-0-LLdWiAhUMyiXq39pUbSSRUdjNN6gQHb9bm";
        voteInfo.put(nodeAddress, BigInteger.valueOf(500));
        ResponseBase<XTransaction> setVoteResult = topj.setVote(account, voteInfo);
        System.out.println("set vote hash >> " + setVoteResult.getData().getTransactionHash());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, account);

        // cancel vote
        voteInfo.put(nodeAddress, BigInteger.valueOf(200));
        ResponseBase<XTransaction> cancelVoteResult = topj.cancelVote(account, voteInfo);
        System.out.println("cancel vote hash >> " + cancelVoteResult.getData().getTransactionHash());

        try {
            Thread.sleep(8000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
        TestCommon.getAccountInfo(topj, account);
    }

    @Test
    public void testCancelVote(){
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
