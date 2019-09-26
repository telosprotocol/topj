package org.topj.methods.request;

import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Request;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.util.List;
import java.util.Map;

public class CreateContractAccount extends RequestTransactionTemplate implements Request {
    private final String METHOD_NAME = "send_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
        xTransaction.setTransactionType(XTransactionType.CreateContractAccount);
        xTransaction.getSourceAction().setActionType(XActionType.SourceNull);
        xTransaction.getTargetAction().setActionType(XActionType.CreateConstractAccount);

        BufferUtils bufferUtils = new BufferUtils();
        byte[] actionParamBytes = bufferUtils.stringToBytes(account.getAddress()).pack();
        String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
        xTransaction.getTargetAction().setActionParam(actionParamHex);

        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {

    }
}
