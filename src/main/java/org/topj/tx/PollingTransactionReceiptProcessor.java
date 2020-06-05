package org.topj.tx;

import org.topj.account.Account;
import org.topj.core.Topj;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

import java.io.IOException;

public class PollingTransactionReceiptProcessor extends TransactionReceiptProcessor {

    protected final long sleepDuration;
    protected final int attempts;

    public PollingTransactionReceiptProcessor(long sleepDuration, int attempts) {
        this.sleepDuration = sleepDuration;
        this.attempts = attempts;
    }

    public PollingTransactionReceiptProcessor() {
        this(3000, 100);
    }

    @Override
    public ResponseBase<XTransaction> waitForTransactionReceipt(Topj topj, Account account, String txHash) throws IOException {
        ResponseBase<XTransaction> result = sendTransactionReceiptRequest(topj, account, txHash);
        for (int i = 0; i < attempts; i++) {
            if (result != null && result.getData() != null && result.getData().isSuccess()) {
                return result;
            } else {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                result = sendTransactionReceiptRequest(topj, account, txHash);
            }
        }
        return result;
    }
}
