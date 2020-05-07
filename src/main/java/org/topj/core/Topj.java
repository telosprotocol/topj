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
import org.topj.methods.Model.Proposal;
import org.topj.methods.Model.ServerInfoModel;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.Request;
import org.topj.methods.property.BlockParameterName;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.request.*;
import org.topj.methods.response.*;
import org.topj.methods.response.block.TableBlockResponse;
import org.topj.methods.response.block.UnitBlockResponse;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.reward.VoterRewardResponse;
import org.topj.procotol.TopjService;
import org.topj.tx.PollingTransactionReceiptProcessor;
import org.topj.tx.TransactionReceiptProcessor;
import org.topj.utils.ArgsUtils;
import org.topj.utils.TopUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.util.*;

/**
 * topj main
 */
public class Topj {

    private static Topj instance = null;
    private TopjService topjService = null;
    private Account defaultAccount = null;
    private TransactionReceiptProcessor transactionReceiptProcessor = null;

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
        if (instance.transactionReceiptProcessor == null) {
            instance.transactionReceiptProcessor = new PollingTransactionReceiptProcessor(instance);
        }
        return instance;
    }

    public static Topj build(TopjService topjService, TransactionReceiptProcessor transactionReceiptProcessor) {
        Topj topj = build(topjService);
        topj.setTransactionReceiptProcessor(transactionReceiptProcessor);
        return topj;
    }

    /**
     * get topj provider Service
     * @return topj provider service
     */
    public TopjService getTopjService() {
        return topjService;
    }

    /**
     * set top provider service
     * @param topjService provider service
     */
    public void setTopjService(TopjService topjService) {
        this.topjService = topjService;
    }

    public void setTransactionReceiptProcessor(TransactionReceiptProcessor transactionReceiptProcessor){
        this.transactionReceiptProcessor = transactionReceiptProcessor;
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

    /**
     * request token
     * @param account account
     * @return ResponseBase , contains RequestTokenResponse
     */
    public ResponseBase<RequestTokenResponse> requestToken(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), RequestTokenResponse.class, new RequestToken());
    }

    /**
     * get account info
     * @param account account
     * @return ResponseBase , contains AccountInfoResponse
     */
    public ResponseBase<AccountInfoResponse> accountInfo(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(account.getAddress()), AccountInfoResponse.class, new AccountInfo());
    }

    /**
     * create account on top
     * @param account account
     * @return transaction obj
     */
    public ResponseBase<XTransaction> createAccount(Account account) throws IOException {
        return _sendTxCommon(account, Collections.emptyList(), new CreateAccount());
    }

    /**
     * transfer one to another
     * @param account account
     * @param to to
     * @param amount amount
     * @param note note of this transfer
     * @return transaction obj
     */
    public ResponseBase<XTransaction> transfer(Account account, String to, BigInteger amount, String note) throws IOException {
        return transfer(account, to, "", amount, note);
    }

    /**
     * transfer one to another
     * @param account account
     * @param to to
     * @param coinType coin type, default top
     * @param amount amount
     * @param note note of this transfer
     * @return transaction obj
     */
    public ResponseBase<XTransaction> transfer(Account account, String to, String coinType, BigInteger amount, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(to, coinType, amount, note), new Transfer());
    }

    /**
     * get transaction detail
     * @param account account
     * @param txHash hash of transaction
     * @return transaction obj
     */
    public ResponseBase<XTransaction> accountTransaction(Account account, String txHash) throws IOException {
        return _requestCommon(account, Arrays.asList(txHash), XTransaction.class, new AccountTransaction());
    }

    /**
     * get String property
     * @param account account
     * @param contractAddress target address
     * @param key key
     * @return property
     */
    public ResponseBase<GetPropertyResponse> getStringProperty(Account account, String contractAddress, String key) throws IOException {
        return getProperty(account, contractAddress, "string", key);
    }

    /**
     * get map property
     * @param account account
     * @param contractAddress  contract address
     * @param mapKey map key
     * @param key key
     * @return property
     * @throws IOException
     */
    public ResponseBase<GetPropertyResponse> getMapProperty(Account account, String contractAddress, String mapKey, String key) throws IOException {
        return getProperty(account, contractAddress, "map", Arrays.asList(mapKey, key));
    }

    /**
     * get list property
     * @param account account
     * @param contractAddress target address
     * @param key key
     * @return property
     */
    public ResponseBase<GetPropertyResponse> getListProperty(Account account, String contractAddress, String key) throws IOException {
        return getProperty(account, contractAddress, "list", key);
    }

    /**
     * get property
     * @param account account
     * @param contractAddress target address
     * @param dataType data type, eg: string, map, list
     * @param params keys
     * @return property
     */
    public ResponseBase<GetPropertyResponse> getProperty(Account account, String contractAddress, String dataType, Object params) throws IOException {
        return _requestCommon(account, Arrays.asList(contractAddress, dataType, params), GetPropertyResponse.class, new GetProperty());
    }

    /**
     * call contract
     * @param account account
     * @param contractAddress contract address
     * @param actionName contract action name
     * @param contractParams action params list
     * @return transaction
     */
    public ResponseBase<XTransaction> callContract(Account account, String contractAddress, String actionName, List<?> contractParams) throws IOException {
        return callContract(account, contractAddress, actionName, contractParams, "", new BigInteger("0"), "");
    }

    /**
     * call contract
     * @param account account
     * @param contractAddress contract address
     * @param actionName action name
     * @param contractParams action params list
     * @param coinType coin type, default top
     * @param amount amount
     * @param note note for this transfer
     * @return transaction
     */
    public ResponseBase<XTransaction> callContract(Account account, String contractAddress, String actionName, List<?> contractParams, String coinType, BigInteger amount, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(contractAddress, actionName, contractParams, coinType, amount, note), new CallContract());
    }

    /**
     * publish contract
     * @param account account
     * @param contractCode contract code
     * @param deposit this deposit of this contract
     * @return transaction
     */
    public ResponseBase<XTransaction> publishContract(Account account, String contractCode, BigInteger deposit) throws IOException {
        return publishContract(account, contractCode, deposit, BigInteger.ZERO, "", "");
    }

    /**
     * publish contract
     * @param account account
     * @param contractCode contract code
     * @param deposit this deposit of this contract
     * @param gasLimit gas limit
     * @param type coin type, default top
     * @param note note of this trans
     * @return transaction
     */
    public ResponseBase<XTransaction> publishContract(Account account, String contractCode, BigInteger deposit, BigInteger gasLimit, String type, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(contractCode, deposit, gasLimit, type, note), new PublishContract());
    }

    /**
     * set vote
     * @param account account
     * @param voteInfo vote info
     * @return transaction
     */
    public ResponseBase<XTransaction> setVote(Account account, Map<String, BigInteger> voteInfo) throws IOException {
        return _sendTxCommon(account, Arrays.asList(new TransferParams(BigInteger.ZERO), voteInfo, XTransactionType.Vote, "set_vote"), new SetVote());
    }

    /**
     * cancel vote
     * @param account account
     * @param voteInfo vote info
     * @return transaction
     */
    public ResponseBase<XTransaction> cancelVote(Account account, Map<String, BigInteger> voteInfo) throws IOException {
        return _sendTxCommon(account, Arrays.asList(new TransferParams(BigInteger.ZERO), voteInfo, XTransactionType.AbolishVote, "abolish_vote"), new SetVote());
    }

    /**
     * pledge top gas
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransaction> pledgeTgas(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return pledgeTgas(account, transferParams);
    }

    /**
     * pledge top gas
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransaction> pledgeTgas(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new PledgeTGas());
    }

    /**
     * redeem top gas
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransaction> redeemTgas(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return redeemTgas(account, transferParams);
    }

    /**
     * redeem top gas
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransaction> redeemTgas(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new RedeemTgas());
    }

    /**
     * pledge disk
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransaction> pledgeDisk(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return pledgeDisk(account, transferParams);
    }

    /**
     * pledge disk
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransaction> pledgeDisk(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new PledgeDisk());
    }


    /**
     * redeem top disk
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransaction> redeemDisk(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return redeemDisk(account, transferParams);
    }


    /**
     * redeem top disk
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransaction> redeemDisk(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new RedeemDisk());
    }

    /**
     * node register
     * @param account account
     * @param mortgage mortgage for node
     * @param nodeType node type
     * @return transaction obj
     */
    public ResponseBase<XTransaction> nodeRegister(Account account, BigInteger mortgage, String nodeType) throws IOException {
        return _sendTxCommon(account, Arrays.asList(new TransferParams(mortgage), nodeType), new NodeRegister());
    }

    /**
     * node register
     * @param account account
     * @return transaction obj
     */
    public ResponseBase<XTransaction> nodeDeRegister(Account account) throws IOException {
        return _sendTxCommon(account, Collections.emptyList(), new NodeDeRegister());
    }

    /**
     * redeem node
     * @param account account
     * @return x
     */
    public ResponseBase<XTransaction> redeem(Account account) throws IOException {
        return _sendTxCommon(account, Collections.emptyList(), new Redeem());
    }

    public ResponseBase<XTransaction> addProposal(Account account, Proposal proposal) throws IOException {
        return _sendTxCommon(account, Arrays.asList(proposal), new AddProposal());
    }

    public ResponseBase<XTransaction> withdrawProposal(Account account, String proposalId) throws IOException {
        return _sendTxCommon(account, Arrays.asList(proposalId), new WithdrawProposal());
    }

    public ResponseBase<XTransaction> voteProposal(Account account, String proposalId, String proposalClientAddress, Boolean option) throws IOException {
        return _sendTxCommon(account, Arrays.asList(proposalId, proposalClientAddress, option), new VoteProposal());
    }

    public ResponseBase<GetPropertyResponse> getProposal(Account account, String proposalId) throws IOException {
        return getMapProperty(account, TopjConfig.getBeaconCgcAddress(), "proposal_map", proposalId);
    }

    /**
     * pledge token vote
     * @param account account
     * @param amount pledge amount
     * @param lockTime lock time
     * @param note note for tx
     * @return x
     */
    public ResponseBase<XTransaction> pledgeTokenVote(Account account, BigInteger amount, BigInteger lockTime, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(amount, lockTime, note), new PledgeTokenVote());
    }

    /**
     * redeem token vote
     * @param account account
     * @param amount redeem amount
     * @param note note for tx
     * @return x
     */
    public ResponseBase<XTransaction> redeemTokenVote(Account account, BigInteger amount, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList("", amount, note), new RedeemTokenVote());
    }

    /**
     * claim account reward
     * @param account account
     * @return x
     */
    public ResponseBase<XTransaction> claimReward(Account account) throws IOException {
        return _sendTxCommon(account, Arrays.asList("claim_reward"), new ClaimReward());
    }

    /**
     * claim node reward
     * @param account account
     * @return x
     */
    public ResponseBase<XTransaction> claimNodeReward(Account account) throws IOException {
        return _sendTxCommon(account, Arrays.asList("claim_node_reward"), new ClaimReward());
    }

    public ResponseBase<ChainInfoResponse> getChainInfo(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), ChainInfoResponse.class, new GetChainInfo());
    }

    public ResponseBase<NodeInfoResponse> getNodeInfo(Account account, String nodeAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddress), NodeInfoResponse.class, new GetNodeInfo());
    }

    public ResponseBase<NodeRewardResponse> getNodeReward(Account account, String nodeAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddress), NodeRewardResponse.class, new GetNodeReward());
    }

    public ResponseBase<VoterRewardResponse> getVoterReward(Account account, String voterAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(voterAddress), VoterRewardResponse.class, new GetVoterReward());
    }

    /**
     * 判断交易是否成功
     * @param account 交易发送者
     * @param hash 交易hash
     * @return 是否成功
     */
    public Boolean isTxSuccess(Account account, String hash) throws IOException {
        ResponseBase<XTransaction> xTransactionResponseBase = accountTransaction(account, hash);
        XTransaction xTransaction = xTransactionResponseBase.getData();
        if (xTransaction == null) {
            return null;
        }
        String targetAccountAddr = xTransaction.getTargetAction().getAccountAddr();
        if (!xTransaction.getSourceAction().getAccountAddr().equals(targetAccountAddr)) {
            Account targetAccount = genAccount();
            targetAccount.setAddress(targetAccountAddr);
            requestToken(targetAccount);
            ResponseBase<XTransaction> targetBase = accountTransaction(targetAccount, hash);
            if (targetBase.getData() == null) {
                return false;
            }
            if (BigInteger.ZERO.equals(targetBase.getData().getRecvUnitHeight())) {
                return false;
            }
            return true;
        } else {
            return xTransaction.isSuccess();
        }
    }

    public ResponseBase<UnitBlockResponse> getLastUnitBlock(Account account) throws IOException {
        return getBlock(account, 2, BlockParameterName.LATEST, 0, UnitBlockResponse.class);
    }

    public ResponseBase<UnitBlockResponse> getUnitBlockByHeight(Account account, Integer height) throws IOException {
        return getBlock(account, 2, BlockParameterName.HEIGHT, height, UnitBlockResponse.class);
    }

    public ResponseBase<TableBlockResponse> getLastTableBlock(Account account) throws IOException {
        return getBlock(account, 3, BlockParameterName.LATEST, 0, TableBlockResponse.class);
    }

    public ResponseBase<TableBlockResponse> getTableBlockByHeight(Account account, Integer height) throws IOException {
        return getBlock(account, 3, BlockParameterName.HEIGHT, height, TableBlockResponse.class);
    }

    public <T> ResponseBase<T> getBlock(Account account, Integer blockType, BlockParameterName blockParameterName, Integer height, Class responseClass) throws IOException {
        return _requestCommon(account, Arrays.asList(blockType, blockParameterName.getValue(), height), responseClass, new GetBlock());
    }

    public <T> ResponseBase<T> getBlockProperties(Account account) throws IOException {
        String key = TopUtils.getUserVoteKey(account.getAddress(), "T-s-oedRLvZ3eM5y6Xsgo4t137An61uoPiM9vS");
        return _requestCommon(account, Arrays.asList(2, BlockParameterName.PROP.getValue(), "award_info", key), Object.class, new GetBlock());
    }

    /**
     * get default provider server url
     * @return server url
     * @throws IOException  IOException
     */
    public static String getDefaultServerUrl() throws IOException {
        return getDefaultServerUrl("http://testnet.topnetwork.org/", "http");
    }

    /**
     * get default provider server url by a common url
     * @param serverUrl serverUrl
     * @return serverUrl
     * @throws IOException IOException
     */
    public static String getDefaultServerUrl(String serverUrl) throws IOException {
        return getDefaultServerUrl(serverUrl, "http");
    }

    /**
     * get default provider server url by a common url
     * @param serverUrl serverUrl
     * @param portType portType
     * @return serverUrl
     * @throws IOException IOException
     */
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
    private <T> ResponseBase<T> _requestCommon(Account account, List<?> args, Class responseClassType, Request request) throws RequestTimeOutException, IOException {
        if (account == null) {
            account = instance.defaultAccount;
        }
        Map<String, String> argsMap = request.getArgs(account, args);
        ResponseBase<T> responseBase = null;
        responseBase = instance.topjService.send(argsMap, responseClassType);
        request.afterExecution(responseBase, argsMap);
        return responseBase;
    }

    private ResponseBase<XTransaction> _sendTxCommon(Account account, List<?> args, Request request) throws RequestTimeOutException, IOException {
        if (account == null) {
            account = instance.defaultAccount;
        }
        Map<String, String> argsMap = request.getArgs(account, args);
        ResponseBase<XTransaction> responseBase;
        responseBase = instance.topjService.send(argsMap, XTransaction.class);
        XTransaction xTransaction = ArgsUtils.decodeXTransFromArgs(argsMap);
        responseBase.setData(xTransaction);
        if (responseBase.getErrNo() == 0) {
            ResponseBase<XTransaction> xTransactionPoll = transactionReceiptProcessor.waitForTransactionReceipt(account, responseBase.getData().getTransactionHash());
            if (xTransactionPoll != null && xTransactionPoll.getData() != null) {
                xTransactionPoll.getData().setXx64Hash(xTransaction.getXx64Hash());
                responseBase.setData(xTransactionPoll.getData());
            } else if (xTransaction != null) {
                responseBase.setErrNo(xTransactionPoll.getErrNo());
                responseBase.setErrMsg(xTransactionPoll.getErrMsg());
            }
        }
        request.afterExecution(responseBase, argsMap);
        return responseBase;
    }
}
