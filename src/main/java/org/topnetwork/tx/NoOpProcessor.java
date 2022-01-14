package org.topnetwork.tx;

import org.topnetwork.account.Account;
import org.topnetwork.core.Topj;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.tx.XTransactionResponse;

public class NoOpProcessor extends TransactionReceiptProcessor {

    @Override
    public ResponseBase<XTransactionResponse> waitForTransactionReceipt(Topj topj, Account account, String transactionHash) {
        return null;
    }
}
