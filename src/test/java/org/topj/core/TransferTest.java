package org.topj.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.http.HttpService;
import org.topj.tx.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;

public class TransferTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferTest.class);
    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;

    @Test
    public void transferTest() throws IOException {
        HttpService httpService = new HttpService("http://206.189.210.106:19081");
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(3000, 15));
        firstAccount = topj.genAccount();
        secondAccount = topj.genAccount();
        topj.passport(firstAccount);
        ResponseBase<AccountInfoResponse> accountResult = topj.getAccount(firstAccount);
        if(accountResult.getErrNo() != 0) {
            LOGGER.error("get account info error > " + accountResult.getErrMsg());
            System.out.println("get account info error > " + accountResult.getErrMsg());
            return;
        }
        LOGGER.debug("first account balance is " + accountResult.getData().getBalance());
        System.out.println("first account balance is " + accountResult.getData().getBalance());
        ResponseBase<XTransactionResponse> transferResult = topj.transfer(firstAccount,
                secondAccount.getAddress(), BigInteger.valueOf(10), "transfer test");
        LOGGER.debug("transfer tx success is " + transferResult.getData().isSuccess());
        System.out.println("transfer tx success is " + transferResult.getData().isSuccess());
        System.out.println("transfer tx result is " + transferResult.getErrMsg());
        System.out.println("transfer tx hash is " + transferResult.getData().getOriginalTxInfo().getTxHash());
        ResponseBase<AccountInfoResponse> accountResult2 = topj.getAccount(firstAccount);
        if(accountResult2.getErrNo() != 0) {
            LOGGER.error("get account info error");
            return;
        }
        LOGGER.debug("first account balance is " + accountResult2.getData().getBalance());
        System.out.println("first account balance is " + accountResult2.getData().getBalance());
    }
}
