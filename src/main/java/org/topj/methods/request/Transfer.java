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
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Request;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
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

public class Transfer extends RequestTransactionTemplate {
    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 4) {
            throw new ArgumentMissingException("except args size 4 , but got " + args.size());
        }
        if (account == null || account.getIdentityToken() == null || account.getLastHashXxhash64() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.Transfer);

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(args.get(1).toString())
                    .longToBytes(Long.valueOf(args.get(2).toString()))
                    .stringToBytes(args.get(3).toString()).pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            SenderAction senderAction = xTransaction.getxAction().getSenderAction();
            senderAction.setActionParam(actionParamHex);

            ReceiverAction receiverAction = xTransaction.getxAction().getReceiverAction();
            receiverAction.setActionType(XActionType.AssetIn);
            receiverAction.setTxReceiverAccountAddr(args.get(0).toString());
            receiverAction.setActionParam(actionParamHex);

            super.SetSignResult(account, requestModel);
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
