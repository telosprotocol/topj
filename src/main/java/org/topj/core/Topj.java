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

package org.topj.core;

import com.alibaba.fastjson.JSON;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.topj.ErrorException.RequestTimeOutException;
import org.topj.account.Account;
import org.topj.methods.Model.ServerInfoModel;
import org.topj.methods.Request;
import org.topj.methods.request.*;
import org.topj.methods.response.*;
import org.topj.procotol.TopjService;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * topj main
 */
public class Topj {

    private static Topj instance = null;
    private TopjService topjService = null;
    private Account defaultAccount = null;

    private Topj(){}

    /**
     * create new topj instance
     * @param topjService topj service，eg: http、grpc、ws
     * @return topj instance
     */
    public static synchronized Topj build(TopjService topjService) {
        if (instance == null) {
            instance = new Topj();
        }
        if (instance.topjService == null || instance.topjService != topjService){
            instance.topjService = topjService;
        }
        if (instance.defaultAccount == null) {
            instance.defaultAccount = new Account();
        }
        return instance;
    }

    public TopjService getTopjService() {
        return topjService;
    }

    public void setTopjService(TopjService topjService) {
        this.topjService = topjService;
    }

    /**
     * gen account by private key
     * @param privateKey private key
     * @return account
     */
    public Account genAccount(String privateKey) {
        return new Account(privateKey);
    }

    /**
     * create account by random
     * @return account
     */
    public Account genAccount() {
        return new Account();
    }

    public ResponseBase<RequestTokenResponse> requestToken(Account account){
        return _requestCommon(account, Collections.emptyList(), RequestTokenResponse.class, new RequestToken());
    }

    public ResponseBase<AccountInfoResponse> accountInfo(Account account){
        return _requestCommon(account, Arrays.asList(account.getAddress()), AccountInfoResponse.class, new AccountInfo());
    }

    public ResponseBase<XTransaction> createAccount(Account account){
        return _requestCommon(account, Collections.emptyList(), XTransaction.class, new CreateAccount());
    }

    public ResponseBase<XTransaction> transfer(Account account, String to, Long amount, String note){
        return transfer(account, to, "", amount, note);
    }

    public ResponseBase<XTransaction> transfer(Account account, String to, String coinType, Long amount, String note){
        return _requestCommon(account, Arrays.asList(to, coinType, amount, note), XTransaction.class, new Transfer());
    }

    public ResponseBase<XTransaction> accountTransaction(Account account, String txHash){
        return _requestCommon(account, Arrays.asList(txHash), XTransaction.class, new AccountTransaction());
    }

    public ResponseBase<XTransaction> getStringProperty(Account account, String contractAddress, String key){
        return getProperty(account, contractAddress, "string", key);
    }

    public ResponseBase<XTransaction> getMapProperty(Account account, String contractAddress, List<String> params){
        return getProperty(account, contractAddress, "map", params);
    }

    public ResponseBase<XTransaction> getListProperty(Account account, String contractAddress, String key){
        return getProperty(account, contractAddress, "list", key);
    }

    public ResponseBase<XTransaction> getProperty(Account account, String contractAddress, String dataType, Object params){
        return _requestCommon(account, Arrays.asList(contractAddress, dataType, params), GetPropertyResponse.class, new GetProperty());
    }

    public ResponseBase<XTransaction> callContract(Account account, String contractAddress, String actionName, List<?> contractParams){
        return callContract(account, contractAddress, actionName, contractParams, "", Long.valueOf(0), "");
    }

    public ResponseBase<XTransaction> callContract(Account account, String contractAddress, String actionName, List<?> contractParams, String coinType, Long amount, String note){
        return _requestCommon(account, Arrays.asList(contractAddress, actionName, contractParams, coinType, amount, note), XTransaction.class, new CallContract());
    }

    public ResponseBase<XTransaction> publishContract(Account account, Account contractAccount, String contractCode, Integer deposit){
        return publishContract(account, contractAccount, contractCode, deposit, 0, "", "");
    }

    public ResponseBase<XTransaction> publishContract(Account account, Account contractAccount, String contractCode, Integer deposit, Integer gasLimit, String type, String note){
        return _requestCommon(account, Arrays.asList(contractAccount, contractCode, deposit, gasLimit, type, note), XTransaction.class, new PublishContract());
    }

    public ResponseBase<XTransaction> setVote(Account account, String contractAddress, String actionName, Map<String, Long> voteInfo){
        return _requestCommon(account, Arrays.asList(contractAddress, actionName, voteInfo), XTransaction.class, new SetVote());
    }

    public static String getDefaultServerUrl() throws IOException {
        return getDefaultServerUrl("http://testnet.topnetwork.org/", "http");
    }

    public static String getDefaultServerUrl(String serverUrl) throws IOException {
        return getDefaultServerUrl(serverUrl, "http");
    }

    public static String getDefaultServerUrl(String serverUrl, String portType) throws IOException {
        if(serverUrl == null || serverUrl == "") {
            return null;
        }
        OkHttpClient httpClient = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(serverUrl)
                .get()
                .build();
        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        String respStr = response.body().string();
        ServerInfoModel serverInfoModel = JSON.parseObject(respStr, ServerInfoModel.class);
        return serverInfoModel.getEdgeUrl(portType);
    }

    /**
     * request common function
     * @param account account
     * @param args args list
     * @param responseClassType responseClassType
     * @param request request
     * @param <T> responseClassType
     * @return response class data
     */
    private <T> ResponseBase<T> _requestCommon(Account account, List<?> args, Class responseClassType, Request request) throws RequestTimeOutException {
        if (account == null) {
            account = instance.defaultAccount;
        }
        Map<String, String> argsMap = request.getArgs(account, args);
        ResponseBase<T> responseBase = null;
        try {
            responseBase = instance.topjService.send(argsMap, responseClassType);
        } catch (IOException e){
            e.printStackTrace();
            return null;
        }
        request.afterExecution(responseBase, argsMap);
        return responseBase;
    }
}
