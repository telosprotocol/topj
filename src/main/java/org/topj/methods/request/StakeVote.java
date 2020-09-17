package org.topj.methods.request;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.ReceiverAction;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.SenderAction;
import org.topj.methods.response.XTransaction;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class StakeVote extends RequestTransactionTemplate {

    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.PledgeTokenVote);

            SenderAction senderAction = xTransaction.getSenderAction();
            senderAction.setActionType(XActionType.SourceNull);

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.BigIntToBytes((BigInteger)args.get(0), 64)
                    .BigIntToBytes((BigInteger)args.get(1), 16).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            ReceiverAction receiverAction = xTransaction.getReceiverAction();
            receiverAction.setActionType(XActionType.PledgeTokenVote);
            receiverAction.setActionParam(actionParamHex);

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
