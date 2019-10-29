package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.topj.account.Account;
import org.topj.methods.response.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class TestCommon {

    public static PublishContractResponse publishContract(Topj topj, Account account) throws IOException {
        URL url = Thread.currentThread().getContextClassLoader().getResource("opt_param.lua");
        File file = new File(url.getPath());
        InputStream inputStream = new FileInputStream(file);
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        String codeStr = new String(bytes);

        ResponseBase<PublishContractResponse> transactionResponseBase = topj.publishContract(account, codeStr, 200, 0, "", "test_tx");
        XTransaction xTransaction = transactionResponseBase.getData().getxTransaction();
        account.setLastHashXxhash64(xTransaction.getXx64Hash());
        account.setNonce(account.getNonce() + 1);

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
        account.setNonce(account.getNonce() + 1);
        System.out.print("createAccount transaction >> ");
        System.out.println(JSON.toJSONString(createAccountXt));

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
