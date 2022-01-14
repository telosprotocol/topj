package org.topnetwork.core;

import com.alibaba.fastjson.JSON;
import org.topnetwork.account.Account;
import org.topnetwork.methods.response.*;
import org.topnetwork.methods.response.tx.XTransactionResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCommon {

    public static String getResourceFile(String fileName) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
        File file = new File(url.getPath());
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String codeStr = new String(bytes);
        return codeStr;
    }

    public static void createAccount(Topj topj, Account account) throws IOException {
        ResponseBase<XTransactionResponse> createAccountXt = topj.createAccount(account);
        account.setNonce(account.getNonce().add(BigInteger.ONE));
        System.out.println("createAccount tx hash >> " + createAccountXt.getData().getOriginalTxInfo().getTxHash()
                + " > is success > " + createAccountXt.getData().isSuccess()
                + " > err msg > " + createAccountXt.getErrMsg());
    }

    public static void nodeRegister(Topj topj, Account account) {
//        String contractAddress = "T-x-thUWvZvuSTc8jWsPWT15wN1wXyf865ECt";
//        String actionName = "node_register";
//        ResponseBase<XTransactionResponse> callContractResult = topj.nodeRegister(account, contractAddress, actionName, NodeType.auditor);
//        System.out.println("node register result >> ");
//        System.out.println(JSON.toJSONString(callContractResult));
//
//        try {
//            Thread.sleep(3000);
//        } catch (InterruptedException eca) {
//            eca.printStackTrace();
//        }
    }

    public static void setVote(Topj topj, Account account, String nodeAddress) throws IOException {
        Map<String, BigInteger> voteInfo = new HashMap<>();
        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
        voteInfo.put(nodeAddress, BigInteger.valueOf(5000));
        ResponseBase<XTransactionResponse> callContractResult = topj.voteNode(account, voteInfo);
        System.out.println("set vote result >> ");
        System.out.println(JSON.toJSONString(callContractResult));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
    }

    public static void getAccountInfo(Topj topj, Account account, String accountAddress) throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.getAccount(account, accountAddress);
        System.out.println("account address > " + accountInfoResponse2.getData().getAccountAddr()
                + " balance > " + accountInfoResponse2.getData().getBalance()
                + " un vote num > " + accountInfoResponse2.getData().getUnusedVoteAmount()
                + " vote staked > " + accountInfoResponse2.getData().getVoteStakedToken()
                + " gas staked > " + accountInfoResponse2.getData().getGasStakedToken());
    }

    public static void getAccountInfo(Topj topj, Account account) throws IOException {
        getAccountInfo(topj, account, account.getAddress());
    }

    public static void getMapProperty(Topj topj, Account account, String contractAddress, String key1, String key2) throws IOException {
        List<String> getPropertyParams = new ArrayList<>();
        getPropertyParams.add(key1);
        getPropertyParams.add(key2);
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, contractAddress, "map", getPropertyParams);
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }

    public static void getStringProperty(Topj topj, Account account, String contractAddress, String key) throws IOException {
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, contractAddress, "string", key);
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }

    public static void getListProperty(Topj topj, Account account, String contractAddress, String key) throws IOException {
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, contractAddress, "list", key);
        System.out.print("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }

    public static void getTx(Topj topj, Account account, String txHash) throws IOException {
        ResponseBase<XTransactionResponse> result = topj.getTransaction(account, txHash);
        System.out.println("tx hash >> " + result.getData().getOriginalTxInfo().getTxHash()
                + " > is success > " + result.getData().isSuccess()
                + " > address > " + result.getData().getOriginalTxInfo().getSenderAccount());
    }
}
