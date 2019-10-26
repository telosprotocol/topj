package org.topj.methods;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestBody;
import org.topj.methods.Model.RequestModel;
import org.topj.utils.TopjConfig;

import java.io.IOException;

public abstract class RequestTemplate implements Request {

    public RequestModel getDefaultArgs(Account account, String methodName){
        if (account == null || account.getToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        RequestModel requestModel = new RequestModel();
        RequestBody requestBody = new RequestBody();
        try {
            requestModel.setVersion(TopjConfig.getVersion());
            requestModel.setAccountAddress(account.getAddress());
            requestModel.setMethod(methodName);
            requestModel.setSequenceId(account.getSequenceId());
            requestModel.setToken(account.getToken());

            requestBody.setVersion(TopjConfig.getVersion());
            requestBody.setAccountAddress(account.getAddress());
            requestBody.setMethod(methodName);
            requestBody.setSequenceId(account.getSequenceId());

            requestModel.setRequestBody(requestBody);
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestModel;
    }
}
