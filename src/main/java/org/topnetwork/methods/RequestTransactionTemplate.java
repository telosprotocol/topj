package org.topnetwork.methods;

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestBody;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.response.XTransaction;
import org.topnetwork.secp256K1Native.Secp256k1Helper;
import org.topnetwork.utils.TopjConfig;

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

            XTransaction xTransaction = new XTransaction();
            xTransaction.setLastTxNonce(account.getNonce());
            xTransaction.setSendTimestamp(BigInteger.valueOf(new Date().getTime()/1000));
            xTransaction.setTxExpireDuration(TopjConfig.getExpireDuration());
            xTransaction.setTxDeposit(TopjConfig.getDeposit());
            xTransaction.setTxStructureVersion(BigInteger.valueOf(2));

            xTransaction.setReceiverAccount(account.getAddress());
            xTransaction.setReceiverActionParam("0x");
            xTransaction.setSenderAccount(account.getAddress());
            xTransaction.setSenderActionParam("0x");

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
            BigInteger privKey = new BigInteger(account.getPrivateKeyBytes());
            String authHex = Secp256k1Helper.signData(dataBytes, privKey);

            xTransaction.setAuthorization(authHex);
//            xTransaction.setPublicKey("0x" + account.getPublicKey());
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }
}
