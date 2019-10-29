package org.topj.methods.response;

import org.topj.account.Account;

public class PublishContractResponse {
    private Account contractAccount;
    private XTransaction xTransaction;

    public Account getContractAccount() {
        return contractAccount;
    }

    public void setContractAccount(Account contractAccount) {
        this.contractAccount = contractAccount;
    }

    public XTransaction getxTransaction() {
        return xTransaction;
    }

    public void setxTransaction(XTransaction xTransaction) {
        this.xTransaction = xTransaction;
    }
}
