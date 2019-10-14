package org.topj.methods;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestBody;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.response.XAction;
import org.topj.methods.response.XTransaction;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

public abstract class RequestTransactionTemplate implements Request {

    public RequestModel getDefaultArgs(Account account, String methodName){
        if (account == null || account.getToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        RequestModel requestModel = new RequestModel();
        RequestBody requestBody = new RequestBody();
        try {
            requestModel.setVersion(TopjConfig.getVersion());
            requestModel.setAccountAddress(account.getAddress());
            requestModel.setMethod(methodName);
            requestModel.setSequenceId(account.getSequenceId());
            requestModel.setToken(account.getToken());

            requestBody.setVersion(TopjConfig.getVersion());
            requestBody.setAccountAddress(account.getAddress());
            requestBody.setMethod(methodName);
            requestBody.setSequenceId(account.getSequenceId());

            XTransaction xTransaction = new XTransaction();
            xTransaction.setLastTransNonce(account.getNonce());
            xTransaction.setFireTimestamp(new Date().getTime()/1000);
            xTransaction.setExpireDuration(TopjConfig.getExpireDuration());
            String lastXXHash = account.getLastHashXxhash64() == null ? TopjConfig.getCreateAccountLastTransHash() : account.getLastHashXxhash64();
            xTransaction.setLastTransHash(lastXXHash);
            xTransaction.setDeposit(TopjConfig.getDeposit());

            XAction sourceAction = new XAction();
            sourceAction.setAccountAddr(account.getAddress());
            sourceAction.setActionParam("0x");

            XAction targetAction = new XAction();
            targetAction.setAccountAddr(account.getAddress());
            targetAction.setActionParam("0x");

            xTransaction.setSourceAction(sourceAction);
            xTransaction.setTargetAction(targetAction);

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
            xTransaction.setPublicKey("0x" + account.getPublicKey());
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
