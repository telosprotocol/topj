package org.topj.methods;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestBody;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.property.XActionType;
import org.topj.methods.response.ReceiverAction;
import org.topj.methods.response.SenderAction;
import org.topj.methods.response.XTransaction;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public abstract class RequestTransactionTemplate implements Request {

    public RequestModel getDefaultArgs(Account account, String methodName){
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        RequestModel requestModel = new RequestModel();
        RequestBody requestBody = new RequestBody();
        try {
            requestModel.setVersion(TopjConfig.getVersion());
            requestModel.setAccountAddress(account.getAddress());
            requestModel.setMethod(methodName);
            requestModel.setSequenceId(account.getSequenceId());
            requestModel.setToken(account.getIdentityToken());

//            requestBody.setVersion(TopjConfig.getVersion());
//            requestBody.setAccountAddress(account.getAddress());
//            requestBody.setMethod(methodName);
//            requestBody.setSequenceId(account.getSequenceId());

            XTransaction xTransaction = new XTransaction();
            xTransaction.setLastTxNonce(account.getNonce());
            xTransaction.setSendTimestamp(BigInteger.valueOf(new Date().getTime()/1000));
            xTransaction.setTxExpireDuration(TopjConfig.getExpireDuration());
            String lastXXHash = account.getLastHashXxhash64() == null ? TopjConfig.getCreateAccountLastTransHash() : account.getLastHashXxhash64();
            xTransaction.setLastTxHash(lastXXHash);
            xTransaction.setTxDeposit(TopjConfig.getDeposit());

            ReceiverAction receiverAction = new ReceiverAction();
            SenderAction senderAction = new SenderAction();

            receiverAction.setTxReceiverAccountAddr(account.getAddress());
            receiverAction.setActionParam("0x");
            senderAction.setActionType(XActionType.AssertOut);
            senderAction.setTxSenderAccountAddr(account.getAddress());
            senderAction.setActionParam("0x");

            xTransaction.setReceiverAction(receiverAction);
            xTransaction.setSenderAction(senderAction);

            requestBody.setxTransaction(xTransaction);
            requestModel.setRequestBody(requestBody);
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestModel;
    }

    public void SetSignResult(Account account, RequestModel requestModel) throws Exception {
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            byte[] dataBytes = xTransaction.set_digest();

            BigInteger privKey = new BigInteger(account.getPrivateKey(), 16);
            String authHex = Secp256k1Helper.signData(dataBytes, privKey);

            xTransaction.setAuthorization(authHex);
//            xTransaction.setPublicKey("0x" + account.getPublicKey());
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
