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

import org.topj.account.Account;
import org.topj.account.KeyPair;
import org.topj.methods.Request;
import org.topj.methods.request.AccountInfo;
import org.topj.methods.request.RequestToken;
import org.topj.methods.request.Transfer;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XTransaction;
import org.topj.procotol.TopjService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * topj主函数
 */
public class Topj {

    private static Topj instance = null;
    private TopjService topjService = null;
    private Account defaultAccount = null;

    private Topj(){}

    /**
     * 创建一个新的topj对象。
     * @param topjService topj 服务，包含http、grpc、ws等
     * @return topj对象
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

    /**
     * 利用私钥创建账户对象
     * @param privateKey 私钥
     * @return 账户对象
     */
    public Account createAccount(String privateKey) {
        return new Account(privateKey);
    }

    /**
     * 利用随机数创建账户对象
     * @return 账户对象
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * 获取用户token
     * @return RequestTokenResponse
     */
    public RequestTokenResponse requestToken(Account account){
        return _requestCommon(account, Collections.emptyList(), RequestTokenResponse.class, new RequestToken());
    }

    /**
     * 获取用户信息
     * @param address 账户地址，如果查当前账户，可以直接传空字符串
     * @return
     */
    public AccountInfoResponse accountInfo(String address){
        return _requestCommon(null, Arrays.asList(address), AccountInfoResponse.class, new AccountInfo());
    }

    public XTransaction transfer(Account account, String to, Integer amount, String note){
        return _requestCommon(account, Arrays.asList(to, amount, note), XTransaction.class, new Transfer());
    }

    /**
     * 功能方法
     * @param account 账户对象
     * @param args 参数list
     * @param responseClassType 返回的类对象类型
     * @param request 请求类
     * @param <T> 返回的类对象类型
     * @return 返回数据结果对象
     */
    private <T> T _requestCommon(Account account, List<?> args, Class responseClassType, Request request){
        if (account == null) {
            account = instance.defaultAccount;
        }
        Map<String, String> argsMap = request.getArgs(account, args);
        ResponseBase<T> responseBase = instance.topjService.send(argsMap, responseClassType);
        request.afterExecution(responseBase);
        return responseBase.getData();
    }
}
