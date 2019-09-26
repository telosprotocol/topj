package org.topj.methods;

import org.topj.account.Account;
import org.topj.methods.response.ResponseBase;

import java.util.List;
import java.util.Map;

public class RequestTemplate implements Request {

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {

    }
}
