package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

import java.io.IOException;

public abstract class TransactionReceiptProcessor {
    private final Topj topj;

    public TransactionReceiptProcessor(Topj topj) {
        this.topj = topj;
    }

    public abstract ResponseBase<XTransaction> waitForTransactionReceipt(Account account, String transactionHash) throws IOException;

    ResponseBase<XTransaction> sendTransactionReceiptRequest(Account account, String txHash) throws IOException {
        ResponseBase<XTransaction> result = topj.getTransaction(account, txHash);
        return result;
    }
}
