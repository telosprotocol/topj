package org.topnetwork.methods.request;

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.RequestTransactionTemplate;
import org.topnetwork.methods.property.XTransactionType;
import org.topnetwork.methods.response.*;
import org.topnetwork.utils.BufferUtils;
import org.topnetwork.utils.StringUtils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class CreateAccount extends RequestTransactionTemplate {
    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.CreateUserAccount);

            xTransaction.setReceiverAccount(account.getAddress());
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(account.getAddress()).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
            xTransaction.setReceiverActionParam(actionParamHex);

            super.SetSignResult(account, requestModel);
            return requestModel.toMap();
        } catch (IOException e){
            e.printStackTrace();;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {

    }
}
