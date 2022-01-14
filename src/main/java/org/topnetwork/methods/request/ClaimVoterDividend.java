package org.topnetwork.methods.request;

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.Model.TransferParams;
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
                    .BigIntToBytes(transferParams.getAmount(), 64).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            xTransaction.setSenderActionParam(actionParamHex);
            xTransaction.setReceiverAccount(TopjConfig.getVoteContractAddress());
            xTransaction.setReceiverActionName(args.get(0).toString());

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