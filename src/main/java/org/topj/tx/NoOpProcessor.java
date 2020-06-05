package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

public class NoOpProcessor extends TransactionReceiptProcessor {

    @Override
    public ResponseBase<XTransaction> waitForTransactionReceipt(Topj topj, Account account, String transactionHash) {
        return null;
    }
}
