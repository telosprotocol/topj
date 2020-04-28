package org.topj.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.tx.PollingTransactionReceiptProcessor;

import java.io.IOException;
import java.math.BigInteger;

public class TransferTest extends TopjTester {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransferTest.class);

    @Test
    public void transferTest() throws IOException {
//        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(topj, 3000, 5));
        ResponseBase<AccountInfoResponse> accountResult = topj.accountInfo(firstAccount);
        if(accountResult.getErrNo() != 0) {
            LOGGER.error("get account info error");
            return;
        }
        LOGGER.debug("first account balance is " + accountResult.getData().getBalance());
        System.out.println("first account balance is " + accountResult.getData().getBalance());
        ResponseBase<XTransaction> transferResult = topj.transfer(firstAccount,
                secondAccount.getAddress(), BigInteger.valueOf(6000), "transfer test");
        LOGGER.debug("transfer tx success is " + transferResult.getData().isSuccess());
        System.out.println("transfer tx success is " + transferResult.getData().isSuccess());
        ResponseBase<AccountInfoResponse> accountResult2 = topj.accountInfo(firstAccount);
        if(accountResult2.getErrNo() != 0) {
            LOGGER.error("get account info error");
            return;
        }
        LOGGER.debug("first account balance is " + accountResult2.getData().getBalance());
        System.out.println("first account balance is " + accountResult2.getData().getBalance());
    }
}
