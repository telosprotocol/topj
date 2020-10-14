package org.topj.methods.request;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.response.*;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class VoteNode extends RequestTransactionTemplate {

    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        if (args.size() != 5) {
            throw new ArgumentMissingException("args length expect 3");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType((BigInteger)args.get(1));
            xTransaction.setTxDeposit((BigInteger)args.get(3));
            xTransaction.setNote(args.get(4).toString());

            SenderAction senderAction = xTransaction.getSenderAction();
            senderAction.setActionType(XActionType.SourceNull);

            ReceiverAction receiverAction = xTransaction.getReceiverAction();
            receiverAction.setActionType(XActionType.RunConstract);
            receiverAction.setTxReceiverAccountAddr(TopjConfig.getVoteContractAddress());
            receiverAction.setActionName(args.get(2).toString());
            receiverAction.setActionParam(initSetVoteArgs((Map)args.get(0)));

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

    private String initSetVoteArgs(Map voteInfo){
        BufferUtils bufferUtils = new BufferUtils();
        byte[] actionParamBytes = bufferUtils.mapToBytes(voteInfo).pack();
        String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
        return actionParamHex;
    }
}
