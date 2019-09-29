package org.topj.core;

import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;

import java.io.IOException;

public class xxhash {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() throws IOException {
        String url = Topj.getDefaultServerUrl();
        HttpService httpService = new HttpService(url);
        topj = Topj.build(httpService);
        account = topj.genAccount();
        System.out.println(account.getAddress());
        System.out.println(account.getPrivateKey());
    }

    @Test
    public void test64Hash() throws IOException {
        topj.requestToken(account);
        topj.accountInfo(account);
        ResponseBase<XTransaction> createAccountXt = topj.createAccount(account);
        account.setLastHashXxhash64(createAccountXt.getData().getXx64Hash());
        account.setNonce(account.getNonce() + 1);

        ResponseBase<XTransaction> transferResponseBase1 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(150), "hello top");
        account.setLastHashXxhash64(transferResponseBase1.getData().getXx64Hash());
        account.setNonce(account.getNonce() + 1);

        ResponseBase<XTransaction> transferResponseBase2 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(150), "hello top");
        account.setLastHashXxhash64(transferResponseBase2.getData().getXx64Hash());
        account.setNonce(account.getNonce() + 1);

        ResponseBase<XTransaction> transferResponseBase3 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(200), "hello top");
        account.setLastHashXxhash64(transferResponseBase3.getData().getXx64Hash());
        account.setNonce(account.getNonce() + 1);

        ResponseBase<XTransaction> transferResponseBase4 = topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", Long.valueOf(200), "hello top");
        account.setLastHashXxhash64(transferResponseBase4.getData().getXx64Hash());
        account.setNonce(account.getNonce() + 1);

        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        ResponseBase<AccountInfoResponse> accountInfoResponseResponseBase = topj.accountInfo(account);
        System.out.printf(accountInfoResponseResponseBase.getData().getBalance().toString());
    }
}
