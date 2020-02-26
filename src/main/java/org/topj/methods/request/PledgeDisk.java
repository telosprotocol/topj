package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XAction;
import org.topj.methods.response.XTransaction;
import org.topj.utils.ArgsUtils;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class PledgeDisk extends RequestTransactionTemplate {

    private final String METHOD_NAME = "send_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTransactionType(XTransactionType.PledgeTokenDisk);

            TransferParams transferParams = (TransferParams)args.get(0);
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(transferParams.getCoinType())
                    .BigIntToBytes(transferParams.getAmount(), 64)
                    .stringToBytes(transferParams.getNote()).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            XAction sourceAction = xTransaction.getSourceAction();
            sourceAction.setActionType(XActionType.AssertOut);
            sourceAction.setActionParam(actionParamHex);

            XAction targetAction = xTransaction.getTargetAction();
            targetAction.setActionType(XActionType.AssetIn);
            targetAction.setActionParam(actionParamHex);

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

    }
}
