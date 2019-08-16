package org.topj.methods;

import org.topj.account.Account;
import org.topj.methods.response.ResponseBase;

import java.util.List;
import java.util.Map;

public interface Request {
    Map<String, String> getArgs(Account account, List<?> args);
    void afterExecution(ResponseBase responseBase);
}
