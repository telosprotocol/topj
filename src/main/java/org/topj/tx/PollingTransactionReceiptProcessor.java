package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

public class PollingTransactionReceiptProcessor extends TransactionReceiptProcessor {

    protected final long sleepDuration;
    protected final int attempts;

    public PollingTransactionReceiptProcessor(Topj topj, long sleepDuration, int attempts) {
        super(topj);
        this.sleepDuration = sleepDuration;
        this.attempts = attempts;
    }

    public PollingTransactionReceiptProcessor(Topj topj) {
        this(topj, 3000, 100);
    }

    @Override
    public ResponseBase<XTransaction> waitForTransactionReceipt(Account account, String txHash) {
        ResponseBase<XTransaction> result = sendTransactionReceiptRequest(account, txHash);
        for (int i = 0; i < attempts; i++) {
            if (result != null && result.getData() != null && result.getData().isSuccess()) {
                return result;
            } else {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                result = sendTransactionReceiptRequest(account, txHash);
            }
        }
        return result;
    }
}
