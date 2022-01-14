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

import org.topnetwork.ErrorException.ArgumentMissingException;
import org.topnetwork.account.Account;
import org.topnetwork.methods.Model.RequestModel;
import org.topnetwork.methods.Model.TransferParams;
import org.topnetwork.methods.RequestTransactionTemplate;
import org.topnetwork.methods.property.XTransactionType;
import org.topnetwork.methods.response.*;

import java.io.IOException;
import java.security.*;
import java.util.List;
import java.util.Map;

public class Transfer extends RequestTransactionTemplate {
    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 1) {
            throw new ArgumentMissingException("except args size 1 , but got " + args.size());
        }
        if (account == null || account.getIdentityToken() == null || account.getLastHashXxhash64() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            TransferParams transferParams = (TransferParams) args.get(0);
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.Transfer);
            xTransaction.setNote(transferParams.getNote());
            xTransaction.setTxDeposit(transferParams.getTransDeposit());

            xTransaction.setAmount(transferParams.getAmount());
            xTransaction.setReceiverAccount(transferParams.getTo());

//            xTransaction.setTxType(XTransactionType.CreateUserAccount);
//            xTransaction.setAmount(BigInteger.ZERO);
//            xTransaction.setAuthorization("");
//            xTransaction.setExt("");
//            xTransaction.setNote("");
//            xTransaction.setReceiverAccount("T8000037d4fbc08bf4513a68a287ed218b0adbd497ef30");
//            BufferUtils b = new BufferUtils();
//            xTransaction.setReceiverActionParam(StringUtils.bytesToHex(b.stringToBytes("T8000037d4fbc08bf4513a68a287ed218b0adbd497ef30").pack()));
//            xTransaction.setSendTimestamp(BigInteger.valueOf(1639019408));
//            xTransaction.setSenderAccount("T8000037d4fbc08bf4513a68a287ed218b0adbd497ef30");

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
