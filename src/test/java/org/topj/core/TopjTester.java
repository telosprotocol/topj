package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.request.AccountInfo;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.GetPropertyResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.http.HttpService;
import org.topj.tx.PollingTransactionReceiptProcessor;

import java.io.IOException;

import static org.topj.core.TestCommon.getResourceFile;

public class TopjTester {

    private String host = "192.168.50.35";
    private String httpUrl = "http://" + host + ":19081";
    private String wsUrl = "ws://" + host + ":19085";

    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;

    @Before
    public void setUp() {
        HttpService httpService = new HttpService(httpUrl);
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(topj, 3000, 10));
        firstAccount = topj.genAccount();
        secondAccount = topj.genAccount();
        topj.requestToken(firstAccount);
        ResponseBase<XTransaction> xTransactionResponseBase = topj.createAccount(firstAccount);
        if (xTransactionResponseBase.getErrNo() != 0) {
            System.out.println("create account err > " + xTransactionResponseBase.getErrMsg());
            return;
        }
    }

    @Test
    public void contractTest() throws IOException {
        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.accountInfo(firstAccount);
        System.out.println("account address > " + accountInfoResponseBase.getData().getAccountAddress() + " balance > " + accountInfoResponseBase.getData().getBalance());
        String codeStr = getResourceFile("opt_map.lua");
        ResponseBase<XTransaction> transactionResponseBase = topj.publishContract(firstAccount, codeStr, 400000, 0, "", "test_tx");
        if (transactionResponseBase.getErrNo() != 0) {
            System.out.println("contract publish err > " + transactionResponseBase.getErrMsg());
            return;
        }
        String contractAddress = transactionResponseBase.getData().getTargetAction().getAccountAddr();
        System.out.println("publish contract hash > " + transactionResponseBase.getData().getTransactionHash());
        System.out.println("contract address > " + contractAddress);
        ResponseBase<GetPropertyResponse> voteXt = topj.getProperty(firstAccount, contractAddress, "string", "temp_a");
        System.out.println("get property >>>>> ");
        System.out.println(JSON.toJSONString(voteXt));
    }
}
