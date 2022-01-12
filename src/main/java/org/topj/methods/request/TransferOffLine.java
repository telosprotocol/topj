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

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class TransferOffLine extends RequestTransactionTemplate {
    private final String METHOD_NAME = "sendTransaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 1) {
            throw new ArgumentMissingException("except args size 1 , but got " + args.size());
        }
        if (account == null) {
            throw new ArgumentMissingException("account is required");
        }
        account.setIdentityToken("123");//no use
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            TransferParams transferParams = (TransferParams) args.get(0);
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.Transfer);
            xTransaction.setNote(transferParams.getNote());
            xTransaction.setTxDeposit(transferParams.getTransDeposit());

            xTransaction.setAmount(transferParams.getAmount());
            xTransaction.setReceiverAccount(transferParams.getTo());

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
