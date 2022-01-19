package org.topnetwork.methods.request;

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.Model.TransferParams;
import org.topnetwork.methods.RequestTransactionTemplate;
import org.topnetwork.methods.property.XTransactionType;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.XTransaction;
import org.topnetwork.utils.BufferUtils;
import org.topnetwork.utils.StringUtils;
import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class RegisterNode extends RequestTransactionTemplate {

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

            TransferParams transferParams = (TransferParams)args.get(0);
            xTransaction.setNote(transferParams.getNote());
            xTransaction.setTxDeposit(transferParams.getTransDeposit());
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(transferParams.getCoinType())
                    .BigIntToBytes(transferParams.getAmount(), 64).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            xTransaction.setSenderActionParam(actionParamHex);

            xTransaction.setReceiverAccount(TopjConfig.getRegistration());
            xTransaction.setReceiverActionName("registerNode");
            BufferUtils tBufferUtils = new BufferUtils();
            tBufferUtils.stringToBytes(args.get(1).toString()).stringToBytes(args.get(2).toString()).stringToBytes(args.get(3).toString()).BigIntToBytes((BigInteger) args.get(4),64);
            if (args.size() == 6) {
                tBufferUtils.BigIntToBytes((BigInteger)args.get(5), 32);
                xTransaction.setReceiverActionName("registerNode2");
            }
            xTransaction.setReceiverActionParam("0x" + StringUtils.bytesToHex(tBufferUtils.pack()));

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
