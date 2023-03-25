package org.topnetwork.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.TransferParams;
import org.topnetwork.methods.request.TransferOffLine;
import org.topnetwork.methods.response.AccountInfoResponse;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.XTransaction;
import org.topnetwork.methods.response.tx.XTransactionResponse;
import org.topnetwork.procotol.http.HttpService;
import org.topnetwork.tx.PollingTransactionReceiptProcessor;
import org.topnetwork.utils.ArgsUtils;
import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Map;

public class TransferTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransferTest.class);
    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;

    @Test
    public void transferTest() throws IOException {
        HttpService httpService = new HttpService("http://192.168.50.48:19081");
//        HttpService httpService = new HttpService("http://192.168.30.22:19081");
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(3000, 15));
        firstAccount = topj.genAccount("a5c0cacf110b502c8abc1519b1b572706970fb6c5ad20ef312fb91fd34aff481");
        secondAccount = topj.genAccount();
        topj.passport(firstAccount);
//        TestCommon.createAccount(topj, firstAccount);
        ResponseBase<AccountInfoResponse> accountResult = topj.getAccount(firstAccount);
        if(accountResult.getErrNo() != 0) {
            LOGGER.error("get account info error > " + accountResult.getErrMsg());
            System.out.println("get account info error > " + accountResult.getErrMsg());
            return;
        }
        LOGGER.debug("first account balance is " + accountResult.getData().getBalance());
        System.out.println("first account balance is " + accountResult.getData().getBalance());
        ResponseBase<XTransactionResponse> transferResult = topj.transfer(firstAccount,
                secondAccount.getAddress(), BigInteger.valueOf(10), "中文");
        LOGGER.debug("transfer tx success is " + transferResult.getData().isSuccess());
        System.out.println("transfer tx success is " + transferResult.getData().isSuccess());
        System.out.println("transfer tx result is " + transferResult.getErrMsg());
        System.out.println("transfer tx hash is " + transferResult.getData().getOriginalTxInfo().getTxHash());
        ResponseBase<AccountInfoResponse> accountResult2 = topj.getAccount(firstAccount);
        if(accountResult2.getErrNo() != 0) {
            LOGGER.error("get account info error");
            return;
        }
        LOGGER.debug("first account balance is " + accountResult2.getData().getBalance());
        System.out.println("first account balance is " + accountResult2.getData().getBalance());
    }

    @Test
    public void transferOffLineTest() throws IOException, InterruptedException {
        HttpService httpService = new HttpService("http://192.168.50.48:19081");
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(3000, 15));
        Account sendAccount=new Account("a5c0cacf110b502c8abc1519b1b572706970fb6c5ad20ef312fb91fd34aff481");
        //获取地址
        sendAccount.setIdentityToken("123");
        System.out.println("SendAccount > "+sendAccount.getAddress());
        topj.getAccount(sendAccount);
        //构建离线交易体需要先设置nonce
        TransferOffLine transfer = new TransferOffLine();
        TransferParams transferParams = new TransferParams("T80000f4d41bf4035e972649a8168ba06a15fa19a15ded", BigInteger.valueOf(1000000), "hello",null,BigInteger.valueOf(400));
        Map<String, String> requestTokenArgsMap = transfer.getArgs(sendAccount, Arrays.asList(transferParams));
        System.out.println("离线交易体 > "+requestTokenArgsMap);

        //4.发送交易
        XTransaction xTransaction = ArgsUtils.decodeXTransFromArgs(requestTokenArgsMap);
        System.out.println(xTransaction.getTxHash());
        Thread.sleep(390000L);
        ResponseBase<XTransactionResponse> result = httpService.send(requestTokenArgsMap, XTransactionResponse.class);
        XTransactionResponse xTransactionResponse = new XTransactionResponse();
        xTransactionResponse.setOriginalTxInfo(xTransaction);
        result.setData(xTransactionResponse);
        System.out.println(result.getData().getOriginalTxInfo().getTxHash());
    }

    @Test
    public void getTransferTest() throws IOException {
        HttpService httpService = new HttpService("http://192.168.50.48:19081");
        topj = Topj.build(httpService);
        topj.setTransactionReceiptProcessor(new PollingTransactionReceiptProcessor(3000, 15));
        Account sendAccount = topj.genAccount("a5c0cacf110b502c8abc1519b1b572706970fb6c5ad20ef312fb91fd34aff481");
        sendAccount.setIdentityToken("123");
        ResponseBase<XTransactionResponse> v =  topj.getTransaction(sendAccount,"0x3a897809a8fd7ddb02aa29d16c4feff3c6400173684ea1368bd05351b3a2db1d");
        System.out.println(v.getData().isSuccess());
        //获取地址
    }
}
