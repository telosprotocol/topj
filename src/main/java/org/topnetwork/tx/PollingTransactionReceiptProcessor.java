package org.topnetwork.tx;

import org.topnetwork.account.Account;
import org.topnetwork.core.Topj;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.tx.XTransactionResponse;

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
    public ResponseBase<XTransactionResponse> waitForTransactionReceipt(Topj topj, Account account, String txHash) throws IOException {
        ResponseBase<XTransactionResponse> result = sendTransactionReceiptRequest(topj, account, txHash);
        for (int i = 0; i < attempts; i++) {
            try {
                Thread.sleep(sleepDuration);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            result = sendTransactionReceiptRequest(topj, account, txHash);
            if ((result != null && result.getData() != null && result.getData().isSuccess() != null) || (result != null && result.getErrNo() != 0)) {
                return result;
            }
        }
        return result;
    }
}
