package org.topnetwork.methods.request;

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.RequestTransactionTemplate;
import org.topnetwork.methods.property.XTransactionType;
import org.topnetwork.methods.response.*;
import org.topnetwork.utils.BufferUtils;
import org.topnetwork.utils.StringUtils;
import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class UnStakeDeposit extends RequestTransactionTemplate {

    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.RunContract);

            xTransaction.setReceiverAccount(TopjConfig.getRegistration());
            xTransaction.setReceiverActionName("unstakeDeposit");
            BufferUtils tBufferUtils = new BufferUtils();
            tBufferUtils.BigIntToBytes((BigInteger)args.get(0), 64);
            xTransaction.setReceiverActionParam("0x" + StringUtils.bytesToHex(tBufferUtils.pack()));

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes("")
                    .BigIntToBytes(BigInteger.ZERO, 64).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
            xTransaction.setSenderActionParam(actionParamHex);
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