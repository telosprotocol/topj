package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.exceptions.TransactionException;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

import java.io.IOException;

public abstract class TransactionReceiptProcessor {
    private final Topj topj;

    public TransactionReceiptProcessor(Topj topj) {
        this.topj = topj;
    }

    public abstract ResponseBase<XTransaction> waitForTransactionReceipt(Account account, String transactionHash);

    ResponseBase<XTransaction> sendTransactionReceiptRequest(Account account, String txHash) {
        ResponseBase<XTransaction> result = topj.accountTransaction(account, txHash);
        return result;
    }
}
