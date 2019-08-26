package org.topj.methods.request;

import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.ResponseBase;

import java.util.List;
import java.util.Map;

public class GetVote implements Request {

    private final String METHOD_NAME = "get_vote";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase) {

    }
}
