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

package org.topnetwork.methods.request;

import com.alibaba.fastjson.JSON;
import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Request;
import org.topnetwork.methods.response.ResponseBase;
import org.topnetwork.methods.response.tx.XTransactionResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class GetTransaction implements Request {
    private final String METHOD_NAME = "getTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 1) {
            throw new ArgumentMissingException("except args size 1 , but got " + args.size());
        }
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> body=new HashMap<String,Object>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", "2.0");
            map.put("target_account_addr", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());
            map.put("token", account.getIdentityToken());

            params.put("account_addr", account.getAddress());
            params.put("tx_hash", args.get(0));

            body.put("params", params);
            map.put("body", JSON.toJSONString(body));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {
        if(Objects.isNull(responseBase.getData())) {
            return;
        }
        XTransactionResponse xTransactionResponse = (XTransactionResponse) responseBase.getData();
        if (Objects.isNull(xTransactionResponse.getOriginalTxInfo()) || Objects.isNull(xTransactionResponse.getOriginalTxInfo().getTxHash())) {
            return;
        }
    }
}
