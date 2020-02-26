package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.XTransaction;

public class NoOpProcessor extends TransactionReceiptProcessor {
    public NoOpProcessor(Topj topj) {
        super(topj);
    }

    @Override
    public XTransaction waitForTransactionReceipt(Account account, String transactionHash) {
        return null;
    }
}
