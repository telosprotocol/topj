package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.bitcoinj.protocols.channels.StoredPaymentChannelClientStates;
import org.topj.account.Account;
import org.topj.methods.property.NodeType;
import org.topj.methods.response.*;
import org.topj.utils.TopUtils;
import org.topj.utils.TopjConfig;

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

    public static XTransaction publishContract(Topj topj, Account account) throws IOException {
        String codeStr = getResourceFile("opt_param.lua");
        ResponseBase<XTransaction> transactionResponseBase = topj.publishContract(account, codeStr, 400000, 0, "", "test_tx");
        XTransaction xTransaction = transactionResponseBase.getData();
        account.setLastHashXxhash64(xTransaction.getXx64Hash());
        account.setNonce(account.getNonce().add(BigInteger.ONE));

        System.out.println("***** publish contract transaction >> ");
        System.out.println(JSON.toJSONString(transactionResponseBase));
        try {
            Thread.sleep(8000);
        } catch (InterruptedException es) {
            es.printStackTrace();
        }
        return transactionResponseBase.getData();
    }

    public static void createAccount(Topj topj, Account account){
        ResponseBase<XTransaction> createAccountXt = topj.createAccount(account);
        account.setLastHashXxhash64(createAccountXt.getData().getXx64Hash());
        account.setNonce(account.getNonce().add(BigInteger.ONE));
        System.out.println("createAccount transaction hash >> " + createAccountXt.getData().getTransactionHash());

        try {
            Thread.sleep(5000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
    }

    public static void nodeRegister(Topj topj, Account account) {
//        String contractAddress = "T-x-thUWvZvuSTc8jWsPWT15wN1wXyf865ECt";
//        String actionName = "node_register";
//        ResponseBase<XTransaction> callContractResult = topj.nodeRegister(account, contractAddress, actionName, NodeType.auditor);
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
        ResponseBase<XTransaction> callContractResult = topj.setVote(account, voteInfo);
        System.out.println("set vote result >> ");
        System.out.println(JSON.toJSONString(callContractResult));

        try {
            Thread.sleep(3000);
        } catch (InterruptedException eca) {
            eca.printStackTrace();
        }
    }

    public static void getAccountInfo(Topj topj, Account account){
        ResponseBase<AccountInfoResponse> accountInfoResponse2 = topj.accountInfo(account);
        System.out.println("accountInfo >>>>> ");
        System.out.println(JSON.toJSONString(accountInfoResponse2));
    }

    public static void getMapProperty(Topj topj, Account account, String contractAddress, String key1, String key2){
        List<String> getPropertyParams = new ArrayList<>();
        getPropertyParams.add(key1);
        getPropertyParams.add(key2);
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, contractAddress, "map", getPropertyParams);
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }

    public static void getStringProperty(Topj topj, Account account, String contractAddress, String key){
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, contractAddress, "string", key);
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }

    public static void getListProperty(Topj topj, Account account, String contractAddress, String key){
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(account, contractAddress, "list", key);
        System.out.print("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }
}
