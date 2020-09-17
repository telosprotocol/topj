package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.tx.XTransactionResponse;

public class NoOpProcessor extends TransactionReceiptProcessor {

    @Override
    public ResponseBase<XTransactionResponse> waitForTransactionReceipt(Topj topj, Account account, String transactionHash) {
        return null;
    }
}
