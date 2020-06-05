package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

import java.io.IOException;

public abstract class TransactionReceiptProcessor {
    public abstract ResponseBase<XTransaction> waitForTransactionReceipt(Topj topj, Account account, String transactionHash) throws IOException;

    ResponseBase<XTransaction> sendTransactionReceiptRequest(Topj topj, Account account, String txHash) throws IOException {
        ResponseBase<XTransaction> result = topj.getTransaction(account, txHash);
        return result;
    }
}
