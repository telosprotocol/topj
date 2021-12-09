package org.topj.core;

import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;

import java.io.IOException;
import java.math.BigInteger;

public class xxhash {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() throws IOException {
//        String url = Topj.getDefaultServerUrl();
        HttpService httpService = new HttpService("http://192.168.50.192:19081");
        topj = Topj.build(httpService);
        account = topj.genAccount();
        System.out.println(account.getAddress());
        System.out.println(account.getPrivateKey());
    }

    @Test
    public void test64Hash() throws IOException {
        topj.passport(account);
        topj.getAccount(account);
        ResponseBase<XTransactionResponse> createAccountXt = topj.createAccount(account);
        account.setNonce(account.getNonce().add(BigInteger.ONE));

        ResponseBase<XTransactionResponse> transferResponseBase1 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", BigInteger.valueOf(150), "hello top");
        account.setNonce(account.getNonce().add(BigInteger.ONE));
        System.out.println(transferResponseBase1.getData().getOriginalTxInfo().getTxHash());

        ResponseBase<XTransactionResponse> transferResponseBase2 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", BigInteger.valueOf(150), "hello top");
        account.setNonce(account.getNonce().add(BigInteger.ONE));

        ResponseBase<XTransactionResponse> transferResponseBase3 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", BigInteger.valueOf(200), "hello top");
        account.setNonce(account.getNonce().add(BigInteger.ONE));

        ResponseBase<XTransactionResponse> transferResponseBase4 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", BigInteger.valueOf(200), "hello top");
        account.setNonce(account.getNonce().add(BigInteger.ONE));

        try {
            Thread.sleep(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseBase<AccountInfoResponse> accountInfoResponseResponseBase = topj.getAccount(account);
        System.out.printf(accountInfoResponseResponseBase.getData().getBalance().toString());
    }
}
