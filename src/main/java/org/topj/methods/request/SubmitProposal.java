package org.topj.methods.request;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.*;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class SubmitProposal extends RequestTransactionTemplate {

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

            BufferUtils sendBU = new BufferUtils();
            byte[] sendParam = sendBU.stringToBytes("")
                    .BigIntToBytes((BigInteger)args.get(3), 64).pack();
            String sendParamHex = "0x" + StringUtils.bytesToHex(sendParam);

            xTransaction.setSenderActionParam(sendParamHex);

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParam = bufferUtils.stringToBytes(args.get(1).toString())
                    .stringToBytes(args.get(2).toString())
                    .BigIntToBytes((BigInteger)args.get(0), 8)
                    .BigIntToBytes((BigInteger)args.get(4), 64)
                    .pack();
            String actionParamHex = StringUtils.bytesToHex(actionParam);

            xTransaction.setReceiverAccount(TopjConfig.getBeaconCgcAddress());
            xTransaction.setReceiverActionName("submitProposal");
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
