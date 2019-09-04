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
import org.topj.methods.response.*;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.ArgsUtils;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transfer implements Request {
    private final String METHOD_NAME = "send_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 3) {
            throw new ArgumentMissingException("except args size 3 , but got " + args.size());
        }
        if (account == null || account.getToken() == null || account.getLastHashXxhash64() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
            map.put("version", TopjConfig.getVersion());
            map.put("account_address", account.getAddress());
            map.put("method", METHOD_NAME);
            map.put("sequence_id", account.getSequenceId());
            map.put("token", account.getToken());

            params.put("version", TopjConfig.getVersion());
            params.put("account_address", account.getAddress());
            params.put("method", METHOD_NAME);
            params.put("sequence_id", account.getSequenceId());

            XTransaction xTransaction = new XTransaction();
            xTransaction.setTransactionType(XTransactionType.Transfer);
            xTransaction.setLastTransNonce(account.getNonce());
            xTransaction.setFireTimestamp(new Date().getTime()/1000);

            xTransaction.setExpireDuration(TopjConfig.getExpireDuration());
            xTransaction.setLastTransHash(account.getLastHashXxhash64());
            xTransaction.setDeposit(TopjConfig.getDeposit());

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes("").longToBytes(Long.valueOf(args.get(1).toString())).stringToBytes(args.get(2).toString()).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            XAction sourceAction = new XAction();
            sourceAction.setActionType(XActionType.AssertOut);
            sourceAction.setAccountAddr(account.getAddress());
            sourceAction.setActionParam(actionParamHex);

            XAction targetAction = new XAction();
            targetAction.setActionType(XActionType.AssetIn);
            targetAction.setAccountAddr(args.get(0).toString());
            targetAction.setActionParam(actionParamHex);

            xTransaction.setSourceAction(sourceAction);
            xTransaction.setTargetAction(targetAction);

            byte[] dataBytes = xTransaction.set_digest();

            BigInteger privKey = new BigInteger(account.getPrivateKey(), 16);
            String authHex = Secp256k1Helper.signData(dataBytes, privKey);

            xTransaction.setAuthorization(authHex);
            xTransaction.setPublicKey("0x" + account.getPublicKey());

            params.put("params", xTransaction);
            map.put("body", JSON.toJSONString(params));
        } catch (IOException e){
            e.printStackTrace();;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
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
}
