/*
 * Copyright 2019 Sawyer Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetAccount implements Request {
    private final String METHOD_NAME = "getAccount";

    private Account account = null;

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        this.account = account;
        if (args.size() != 1 && args.size() != 3) {
            throw new ArgumentMissingException("except args size 1 , but got " + args.size());
        }
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("target_account_addr", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());

            Map<String, String> argsMap = new HashMap<>();
            argsMap.put("account_addr", args.get(0).toString());
            if (args.size() == 3) {
                argsMap.put("contract_code", args.get(1).toString());
                argsMap.put("contract_parent_account", args.get(2).toString());
            }
            params.put("params", argsMap);

            map.put("body", JSON.toJSONString(params));
        } catch (IOException e){
        }
        return map;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {
        if(Objects.isNull(responseBase.getData())) {
            return;
        }
        AccountInfoResponse accountInfoResponse = (AccountInfoResponse)responseBase.getData();
        if (accountInfoResponse.getNonce() != null) {
            account.setNonce(accountInfoResponse.getNonce());
        }
        if (accountInfoResponse.getLatestTxHash() != null) {
            account.setLastHash(accountInfoResponse.getLatestTxHash());
        }
        if (accountInfoResponse.getLatestTxHash() != null) {
            account.setLastHashXxhash64(accountInfoResponse.getLatestTxHashXxhash64());
        }
        if (accountInfoResponse.getLatestUnitHeight() != null) {
            account.setLastUnitHeight(accountInfoResponse.getLatestUnitHeight());
        }
        if (accountInfoResponse.getBalance() != null) {
            account.setBalance(accountInfoResponse.getBalance());
        }
    }
}
