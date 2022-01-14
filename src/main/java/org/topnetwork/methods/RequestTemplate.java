package org.topnetwork.methods;

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestBody;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class RequestTemplate implements Request {

    public RequestModel getDefaultArgs(Account account, String methodName){
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        RequestModel requestModel = new RequestModel();
        RequestBody requestBody = new RequestBody();
        try {
            requestModel.setVersion(TopjConfig.getVersion());
            requestModel.setAccountAddress(account.getAddress());
            requestModel.setMethod(methodName);
            requestModel.setSequenceId(account.getSequenceId());
            requestModel.setToken(account.getIdentityToken());

            Map<String, Object> argsMap = new HashMap<>();
            argsMap.put("account_addr", account.getAddress());
            requestBody.setArgsMap(argsMap);

            requestModel.setRequestBody(requestBody);
        } catch (IOException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return requestModel;
    }
}
