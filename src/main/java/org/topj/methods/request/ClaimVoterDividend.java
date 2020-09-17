package org.topj.methods.request;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
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

public class ClaimVoterDividend extends RequestTransactionTemplate {

    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        if (args.size() != 1) {
            throw new ArgumentMissingException("args length expect 1");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.RunContract);

            TransferParams transferParams = new TransferParams(BigInteger.ZERO);
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(transferParams.getCoinType())
                    .BigIntToBytes(transferParams.getAmount(), 64)
                    .stringToBytes(transferParams.getNote()).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            SenderAction senderAction = xTransaction.getSenderAction();
            senderAction.setActionParam(actionParamHex);

            ReceiverAction receiverAction = xTransaction.getReceiverAction();
            receiverAction.setActionType(XActionType.RunConstract);
            receiverAction.setTxReceiverAccountAddr(TopjConfig.getVoteContractAddress());
            receiverAction.setActionName(args.get(0).toString());

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