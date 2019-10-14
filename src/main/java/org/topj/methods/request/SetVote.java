package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Request;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.*;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.ArgsUtils;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SetVote extends RequestTransactionTemplate {

    private final String METHOD_NAME = "send_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTransactionType(XTransactionType.RunContract);

            XAction sourceAction = xTransaction.getSourceAction();
            sourceAction.setActionType(XActionType.AssertOut);
            sourceAction.setActionParam("0x");

            XAction targetAction = xTransaction.getTargetAction();
            targetAction.setActionType(XActionType.RunConstract);
            targetAction.setAccountAddr(args.get(0).toString());
            targetAction.setActionName(args.get(1).toString());
            targetAction.setActionParam(initSetVoteArgs((Map)args.get(2)));

            super.SetSignResult(account, requestModel);
            System.out.printf(JSON.toJSONString(requestModel.getRequestBody()));
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
        try {
            XTransaction xTransaction = ArgsUtils.decodeXTransFromArgs(args);
            responseBase.setData(xTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String initSetVoteArgs(Map voteInfo){
        BufferUtils bufferUtils = new BufferUtils();
        byte[] actionParamBytes = bufferUtils.mapToBytes(voteInfo).pack();
        String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
        return actionParamHex;
    }
}
