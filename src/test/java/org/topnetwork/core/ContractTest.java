package org.topnetwork.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topnetwork.account.Account;
import org.topnetwork.methods.property.XProperty;
import org.topnetwork.methods.response.*;
import org.topnetwork.methods.response.tx.XTransactionResponse;
import org.topnetwork.procotol.http.HttpService;
import org.topnetwork.procotol.websocket.WebSocketService;

import java.io.*;
import java.math.BigInteger;
import java.net.ConnectException;

public class ContractTest {
    private Topj topj = null;
    private Account centerAccount = null;
    private Account account = null;

    @Before
    public void setUp(){
        HttpService httpService = getHttpService("http://192.168.20.13:19081");
//        HttpService httpService = new HttpService("http://157.245.121.80:19081");
//        WebSocketService webSocketService = new WebSocketService("http://192.168.50.35:19085");
//        try{
//            webSocketService.connect();
//        } catch (ConnectException conn){
//            conn.printStackTrace();
//        }
        topj = Topj.build(httpService);
//        topj.setTransactionReceiptProcessor(new NoOpProcessor(topj));
        account = topj.genAccount("ff867b2ceb48f6bfc8a93d6c6aac05a29baad5da18ab5fb2bb9758379475fad8");
        centerAccount = topj.genAccount("0x7fcf50e425b4ac9c13268505cb3dfac32045457cc6e90500357d00c8cf85f5b9");
    }

    @Test
    public void pledge() throws IOException {
        account = topj.genAccount("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
//        account = topj.genAccount();
        topj.passport(account);
//        TestCommon.createAccount(topj, account);
        centerAccountTransfer(account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransactionResponse> pledgeTgas = topj.stakeGas(account, BigInteger.valueOf(4000));
        System.out.println("pledgeTgas >> ");
        System.out.println(JSON.toJSONString(pledgeTgas));
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ResponseBase<XTransactionResponse> accountTransaction = topj.getTransaction(account, pledgeTgas.getData().getOriginalTxInfo().getTxHash());
        System.out.println(JSON.toJSONString(accountTransaction));
        Boolean isSucc = topj.isTxSuccess(account, pledgeTgas.getData().getOriginalTxInfo().getTxHash());
        System.out.println("tx hash >> " + pledgeTgas.getData().getOriginalTxInfo().getTxHash() + " > is success > " + isSucc);

        TestCommon.getAccountInfo(topj, account);

        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.USED_TGAS_KEY);
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.LAST_TX_HOUR_KEY);
    }

    @Test
    public void redeem() throws IOException {
        account = topj.genAccount("0xe7cd3bc643e84c6d7cc2ccfefa3b4a56eff21bf600b7998a1a748efc61b9ac65");
        topj.passport(account);
        TestCommon.createAccount(topj, account);
        System.out.println(account.getAddress());
        TestCommon.getAccountInfo(topj, account);
        ResponseBase<XTransactionResponse> redeemTgas = topj.unStakeGas(account, BigInteger.valueOf(5));
        System.out.println("redeemTgas >> ");
        System.out.println(JSON.toJSONString(redeemTgas));

        TestCommon.getStringProperty(topj, account, account.getAddress(), "@30");
        TestCommon.getStringProperty(topj, account, account.getAddress(), XProperty.PLEDGE_TOKEN_TGAS_KEY);
    }

    private HttpService getHttpService(String url) {
        // http://127.0.0.1:19090
        // http://192.168.50.71:19081
        return new HttpService(url);
    }

    private WebSocketService getWebSocketService(String url){
        // ws://127.0.0.1:19085
        // ws://128.199.181.220:19085
        WebSocketService wsService = new WebSocketService(url);
        try{
            wsService.connect();
            return wsService;
        } catch (ConnectException conne){
            conne.printStackTrace();
            return null;
        }
    }

    private void centerAccountTransfer(Account account) throws IOException {
        topj.passport(centerAccount);
        topj.getAccount(centerAccount);
        ResponseBase<XTransactionResponse> xTransactionResponseBase = topj.transfer(centerAccount, account.getAddress(), BigInteger.valueOf(100000001l), "create");
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if (xTransactionResponseBase.getData() == null) {
            System.out.println("center transfer result is null ");
        } else {
            System.out.println("center transfer is " + xTransactionResponseBase.getData().isSuccess());
        }
    }
}
