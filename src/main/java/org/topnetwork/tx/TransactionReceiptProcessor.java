package org.topnetwork.tx;

import org.topnetwork.account.Account;
import org.topnetwork.core.Topj;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.tx.XTransactionResponse;

import java.io.IOException;

public abstract class TransactionReceiptProcessor {
    public abstract ResponseBase<XTransactionResponse> waitForTransactionReceipt(Topj topj, Account account, String transactionHash) throws IOException;

    ResponseBase<XTransactionResponse> sendTransactionReceiptRequest(Topj topj, Account account, String txHash) throws IOException {
        ResponseBase<XTransactionResponse> result = topj.getTransaction(account, txHash);
        return result;
    }
}
