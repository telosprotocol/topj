package org.topj.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.tx.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;

public class TransferTest extends TopjTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferTest.class);

    @Test
    public void transferTest() throws IOException {
//        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(3000, 5));
        firstAccount = topj.genAccount("a01856486dd5b798fc6a872b315ecd9f3cf69555de97f95354c408eda14bb2c3");
//        firstAccount = topj.genAccount("3737e856b3099ebd931057e580b4d7a0538bead247edc94123db193310d91fe6");
        topj.passport(firstAccount);
//        ResponseBase<XTransactionResponse> cr = topj.createAccount(firstAccount);
        ResponseBase<AccountInfoResponse> accountResult = topj.getAccount(firstAccount);
        if(accountResult.getErrNo() != 0) {
            LOGGER.error("get account info error");
            return;
        }
        LOGGER.debug("first account balance is " + accountResult.getData().getBalance());
        System.out.println("first account balance is " + accountResult.getData().getBalance());
        ResponseBase<XTransactionResponse> transferResult = topj.transfer(firstAccount,
                secondAccount.getAddress(), BigInteger.valueOf(10), "transfer test");
        LOGGER.debug("transfer tx success is " + transferResult.getData().isSuccess());
        System.out.println("transfer tx success is " + transferResult.getData().isSuccess());
        ResponseBase<AccountInfoResponse> accountResult2 = topj.getAccount(firstAccount);
        if(accountResult2.getErrNo() != 0) {
            LOGGER.error("get account info error");
            return;
        }
        LOGGER.debug("first account balance is " + accountResult2.getData().getBalance());
        System.out.println("first account balance is " + accountResult2.getData().getBalance());
    }
}
