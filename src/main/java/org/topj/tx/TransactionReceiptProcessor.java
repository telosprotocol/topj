package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.tx.XTransactionResponse;

import java.io.IOException;

public abstract class TransactionReceiptProcessor {
    public abstract ResponseBase<XTransactionResponse> waitForTransactionReceipt(Topj topj, Account account, String transactionHash) throws IOException;

    ResponseBase<XTransactionResponse> sendTransactionReceiptRequest(Topj topj, Account account, String txHash) throws IOException {
        ResponseBase<XTransactionResponse> result = topj.getTransaction(account, txHash);
        return result;
    }
}
