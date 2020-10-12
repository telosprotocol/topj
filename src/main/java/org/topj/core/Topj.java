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
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.topj.ErrorException.RequestTimeOutException;
import org.topj.account.Account;
import org.topj.exceptions.ArgsIllegalException;
import org.topj.methods.Model.Proposal;
import org.topj.methods.Model.ServerInfoModel;
import org.topj.methods.Model.TransferParams;
import org.topj.methods.Request;
import org.topj.methods.property.*;
import org.topj.methods.request.*;
import org.topj.methods.response.*;
import org.topj.methods.response.block.TableBlockResponse;
import org.topj.methods.response.block.UnitBlockResponse;
import org.topj.methods.response.reward.NodeRewardResponse;
import org.topj.methods.response.reward.VoterDividendResponse;
import org.topj.methods.response.tx.OriginalTxInfo;
import org.topj.methods.response.tx.XTransactionResponse;
import org.topj.procotol.TopjService;
import org.topj.tx.PollingTransactionReceiptProcessor;
import org.topj.tx.TransactionReceiptProcessor;
import org.topj.utils.ArgsUtils;
import org.topj.utils.EdgeUtils;
import org.topj.utils.TopUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
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
            instance.transactionReceiptProcessor = new PollingTransactionReceiptProcessor();
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
    public ResponseBase<PassportResponse> passport(Account account) throws IOException {
        return _requestDirect(account, Collections.emptyList(), PassportResponse.class, new Passport());
    }

    /**
     * get account info
     * @param account account
     * @return ResponseBase , contains AccountInfoResponse
     */
    public ResponseBase<AccountInfoResponse> getAccount(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(account.getAddress()), AccountInfoResponse.class, new GetAccount());
    }

    /**
     * get account info
     * @param account account
     * @return ResponseBase , contains AccountInfoResponse
     */
    public ResponseBase<AccountInfoResponse> getAccount(Account account, String address) throws IOException {
        return _requestCommon(account, Arrays.asList(address), AccountInfoResponse.class, new GetAccount());
    }

    /**
     * create account on top
     * @param account account
     * @return transaction obj
     */
    public ResponseBase<XTransactionResponse> createAccount(Account account) throws IOException {
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
    public ResponseBase<XTransactionResponse> transfer(Account account, String to, BigInteger amount, String note) throws IOException {
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
    public ResponseBase<XTransactionResponse> transfer(Account account, String to, String coinType, BigInteger amount, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(to, coinType, amount, note), new Transfer());
    }

    /**
     * get transaction detail
     * @param account account
     * @param txHash hash of transaction
     * @return transaction obj
     */
    public ResponseBase<XTransactionResponse> getTransaction(Account account, String txHash) throws IOException {
        return _requestCommon(account, Arrays.asList(txHash), XTransactionResponse.class, new GetTransaction());
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
    public ResponseBase<XTransactionResponse> callContract(Account account, String contractAddress, String actionName, List<?> contractParams) throws IOException {
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
    public ResponseBase<XTransactionResponse> callContract(Account account, String contractAddress, String actionName, List<?> contractParams, String coinType, BigInteger amount, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(contractAddress, actionName, contractParams, coinType, amount, note), new CallContract());
    }

    /**
     * publish contract
     * @param account account
     * @param contractCode contract code
     * @param deposit this deposit of this contract
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> deployContract(Account account, String contractCode, BigInteger deposit) throws IOException {
        return deployContract(account, contractCode, deposit, BigInteger.ZERO, "", "");
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
    public ResponseBase<XTransactionResponse> deployContract(Account account, String contractCode, BigInteger deposit, BigInteger gasLimit, String type, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(contractCode, deposit, gasLimit, type, note), new DeployContract());
    }

    /**
     * set vote
     * @param account account
     * @param voteInfo vote info
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> voteNode(Account account, Map<String, BigInteger> voteInfo) throws IOException {
        return _sendTxCommon(account, Arrays.asList(voteInfo, XTransactionType.Vote, "voteNode"), new VoteNode());
    }

    /**
     * cancel vote
     * @param account account
     * @param voteInfo vote info
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unVoteNode(Account account, Map<String, BigInteger> voteInfo) throws IOException {
        return _sendTxCommon(account, Arrays.asList(voteInfo, XTransactionType.AbolishVote, "unvoteNode"), new VoteNode());
    }

    public ResponseBase<String> listVoteUsed(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), String.class, new ListVoteUsed());
    }

    public ResponseBase<String> listVoteUsed(Account account, String nodeAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddress), String.class, new ListVoteUsed());
    }

    /**
     * pledge top gas, default state self
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeGas(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return stakeGas(account, transferParams, account.getAddress());
    }

    /**
     * pledge top gas, state for other
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeGas(Account account, BigInteger amount, String to) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return stakeGas(account, transferParams, to);
    }

    /**
     * pledge top gas
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeGas(Account account, TransferParams transferParams, String to) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams, to), new StakeGas());
    }

    /**
     * redeem top gas
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeGas(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return unStakeGas(account, transferParams, account.getAddress());
    }

    /**
     * redeem top gas
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeGas(Account account, BigInteger amount, String to) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return unStakeGas(account, transferParams, to);
    }

    /**
     * redeem top gas
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeGas(Account account, TransferParams transferParams, String to) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams, to), new UnStakeGas());
    }

    /**
     * pledge disk
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeDisk(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return stakeDisk(account, transferParams, account.getAddress());
    }

    /**
     * pledge disk
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeDisk(Account account, BigInteger amount, String to) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return stakeDisk(account, transferParams, to);
    }

    /**
     * pledge disk
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeDisk(Account account, TransferParams transferParams, String to) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams, to), new StakeDisk());
    }


    /**
     * redeem top disk
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeDisk(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return unStakeDisk(account, transferParams, account.getAddress());
    }

    /**
     * redeem top disk
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeDisk(Account account, BigInteger amount, String to) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        return unStakeDisk(account, transferParams, to);
    }


    /**
     * redeem top disk
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeDisk(Account account, TransferParams transferParams, String to) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new UnStakeDisk());
    }

    /**
     * node register
     * @param account account
     * @param mortgage mortgage for node
     * @param nodeType node type
     * @return transaction obj
     */
    public ResponseBase<XTransactionResponse> registerNode(Account account, BigInteger mortgage, String nodeType, String nickName, String key) throws IOException {
        checkNodeMortgage(mortgage, nodeType);
        if (nickName == null || nickName.length() < 4 || nickName.length() > 16) {
            throw new ArgsIllegalException("nickName length must be 4 ~ 16");
        }
        return _sendTxCommon(account, Arrays.asList(new TransferParams(mortgage), nodeType, nickName, key), new RegisterNode());
    }
    public ResponseBase<XTransactionResponse> registerNode(Account account, BigInteger mortgage, String nodeType, String nickName, String key, BigInteger networkId) throws IOException {
        checkNodeMortgage(mortgage, nodeType);
        if (nickName == null || nickName.length() < 4 || nickName.length() > 16) {
            throw new ArgsIllegalException("nickName length must be 4 ~ 16");
        }
        return _sendTxCommon(account, Arrays.asList(new TransferParams(mortgage), nodeType, nickName, key, networkId), new RegisterNode());
    }
    public ResponseBase<XTransactionResponse> updateNodeType(Account account, BigInteger mortgage, String nodeType) throws IOException {
        checkNodeMortgage(mortgage, nodeType);
        return _sendTxCommon(account, Arrays.asList(new TransferParams(mortgage), nodeType), new UpdateNodeType());
    }
    private void checkNodeMortgage(BigInteger mortgage, String nodeType){
        switch (nodeType){
            case NodeType.edge:
                if (mortgage.compareTo(BigInteger.valueOf(100000000000l)) < 0) {
                    throw new ArgsIllegalException("mortgage can not be less than 100000000000 utop");
                }
                break;
            case NodeType.validator:
                if (mortgage.compareTo(BigInteger.valueOf(500000000000l)) < 0) {
                    throw new ArgsIllegalException("mortgage can not be less than 500000000000 utop");
                }
                break;
            case NodeType.advanced:
            case NodeType.auditor:
            case NodeType.archive:
                if (mortgage.compareTo(BigInteger.valueOf(1000000000000l)) < 0) {
                    throw new ArgsIllegalException("mortgage can not be less than 1000000000000 utop");
                }
                break;
            default:
                throw new ArgsIllegalException("not support node type");
        }
    }
    public ResponseBase<XTransactionResponse> setDividendRate(Account account, BigInteger dividendRate) throws IOException {
        return _sendTxCommon(account, Arrays.asList(dividendRate), new SetDividendRate());
    }
    public ResponseBase<XTransactionResponse> stakeDeposit(Account account, BigInteger mortgage) throws IOException {
        return _sendTxCommon(account, Arrays.asList(new TransferParams(mortgage)), new StakeDeposit());
    }
    public ResponseBase<XTransactionResponse> unStakeDeposit(Account account, BigInteger mortgage) throws IOException {
        return _sendTxCommon(account, Arrays.asList(mortgage), new UnStakeDeposit());
    }
    public ResponseBase<XTransactionResponse> setNodeName(Account account, String nickname) throws IOException {
        if (nickname == null || (nickname.length() < 4 || nickname.length() > 16)) {
            throw new RuntimeException("nickname is between 4 and 16 ");
        }
        return _sendTxCommon(account, Arrays.asList(nickname), new SetNodeName());
    }

    /**
     * node register
     * @param account account
     * @return transaction obj
     */
    public ResponseBase<XTransactionResponse> unRegisterNode(Account account) throws IOException {
        return _sendTxCommon(account, Collections.emptyList(), new UnRegisterNode());
    }

    /**
     * redeem node
     * @param account account
     * @return x
     */
    public ResponseBase<XTransactionResponse> redeemNodeDeposit(Account account) throws IOException {
        return _sendTxCommon(account, Collections.emptyList(), new RedeemNodeDeposit());
    }

    public ResponseBase<XTransactionResponse> submitProposal(Account account, Proposal proposal) throws IOException {
        return _sendTxCommon(account, Arrays.asList(proposal), new SubmitProposal());
    }

    public ResponseBase<XTransactionResponse> withdrawProposal(Account account, String proposalId) throws IOException {
        return _sendTxCommon(account, Arrays.asList(proposalId), new WithdrawProposal());
    }

    public ResponseBase<XTransactionResponse> tccVote(Account account, String proposalId, String proposalClientAddress, Boolean option) throws IOException {
        return _sendTxCommon(account, Arrays.asList(proposalId, proposalClientAddress, option), new TCCVote());
    }

    public ResponseBase<Proposal> queryProposal(Account account, String proposalId) throws IOException {
        return _requestCommon(account, Arrays.asList(proposalId), Proposal.class, new QueryProposal());
    }

    /**
     * pledge token vote
     * @param account account
     * @param voteAmount pledge amount
     * @param lockTime lock time
     * @return x
     */
    public ResponseBase<XTransactionResponse> stakeVote(Account account, BigInteger voteAmount, BigInteger lockTime) throws IOException {
        if (voteAmount.compareTo(BigInteger.valueOf(1000)) < 0) {
            throw new ArgsIllegalException("amount can not be less than 1000");
        }
        if (lockTime.compareTo(BigInteger.valueOf(30)) < 0) {
            throw new ArgsIllegalException("lockTime can not be less than 30 (day)");
        }
        BigInteger[] resBigIntegers = lockTime.divideAndRemainder(BigInteger.valueOf(30));
        if (resBigIntegers.length != 2 || resBigIntegers[1].compareTo(BigInteger.ZERO) != 0) {
            throw new ArgsIllegalException("lockTime can not be divide by 30");
        }
        return _sendTxCommon(account, Arrays.asList(voteAmount, lockTime), new StakeVote());
    }

    /**
     * redeem token vote
     * @param account account
     * @param amount redeem amount
     * @return x
     */
    public ResponseBase<XTransactionResponse> unStakeVote(Account account, BigInteger amount) throws IOException {
        return _sendTxCommon(account, Arrays.asList(amount), new UnStakeVote());
    }

    /**
     * claim account reward
     * @param account account
     * @return x
     */
    public ResponseBase<XTransactionResponse> claimVoterDividend(Account account) throws IOException {
        return _sendTxCommon(account, Arrays.asList("claimVoterDividend"), new ClaimVoterDividend());
    }

    /**
     * claim node reward
     * @param account account
     * @return x
     */
    public ResponseBase<XTransactionResponse> claimNodeReward(Account account) throws IOException {
        return _sendTxCommon(account, Arrays.asList("claimNodeReward"), new ClaimVoterDividend());
    }

    public ResponseBase<ChainInfoResponse> getChainInfo(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), ChainInfoResponse.class, new GetChainInfo());
    }

    public ResponseBase<ClockBlockResponse> getClockBlock(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), ClockBlockResponse.class, new GetClockBlock());
    }

    public ResponseBase<CandidateNodeDetail> getCandidateNode(Account account, String nodeAddr) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddr), CandidateNodeDetail.class, new GetCandidateNode());
    }

    public ResponseBase<CandidateNodeResponse> getAllCandidateNode(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(""), CandidateNodeResponse.class, new GetCandidateNode());
    }

    public ResponseBase<NodeInfoResponse> queryNodeInfo(Account account, String nodeAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddress), NodeInfoResponse.class, new QueryNodeInfo());
    }

    public ResponseBase<Map<String, NodeInfoResponse>> queryAllNodeInfo(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(""), Map.class, new QueryNodeInfo());
    }

    public ResponseBase<EdgeStatusResponse> getEdgeStatus(Account account) throws IOException {
        return _requestDirect(account, Collections.emptyList(), EdgeStatusResponse.class, new GetEdgeStatus());
    }

    public ResponseBase<List<String>> getEdgeNeighbors(Account account) throws IOException {
        return _requestDirect(account, Collections.emptyList(), List.class, new GetEdgeNeighbors());
    }

    public ResponseBase<NodeRewardResponse> queryNodeReward(Account account, String nodeAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddress), NodeRewardResponse.class, new QueryNodeReward());
    }

    public ResponseBase<Map<String, NodeRewardResponse>> queryAllNodeReward(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(""), Map.class, new QueryNodeReward());
    }

    public ResponseBase<VoterDividendResponse> queryVoterDividend(Account account, String voterAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(voterAddress), VoterDividendResponse.class, new QueryVoterDividend());
    }

    /**
     * 判断交易是否成功
     * @param account 交易发送者
     * @param hash 交易hash
     * @return 是否成功
     */
    public Boolean isTxSuccess(Account account, String hash) throws IOException {
        ResponseBase<XTransactionResponse> xTransactionResponseBase = getTransaction(account, hash);
        XTransactionResponse xTransactionResponse = xTransactionResponseBase.getData();
        return xTransactionResponse != null ? xTransactionResponse.isSuccess() : null;
    }

    /**
     * 判断交易是否成功
     * @param account 交易发送者
     * @param hash 交易hash
     * @return 是否成功
     */
    public String getTxStatus(Account account, String hash) throws IOException {
        ResponseBase<XTransactionResponse> xTransactionResponseBase = getTransaction(account, hash);
        XTransactionResponse xTransactionResponse = xTransactionResponseBase.getData();
        if (xTransactionResponse == null) {
            return TxStatus.NULL.getStatus();
        }
        if (xTransactionResponse.getTxConsensusState() == null ||
                xTransactionResponse.getTxConsensusState().getConfirmUnitInfo() == null ||
                "".equals(xTransactionResponse.getTxConsensusState().getConfirmUnitInfo().getExecStatus())) {
            return TxStatus.PENDING.getStatus();
        }
        if ("success".equals(xTransactionResponse.getTxConsensusState().getConfirmUnitInfo().getExecStatus())) {
            return TxStatus.SUCCESS.getStatus();
        } else if ("failure".equals(xTransactionResponse.getTxConsensusState().getConfirmUnitInfo().getExecStatus())) {
            return TxStatus.SUCCESS.getStatus();
        }
        throw new RuntimeException("unknown tx status");
    }

    public boolean checkedAddress(String address) {
        return checkedAddress(address, AccountType.MAIN, NetType.MAIN);
    }

    public boolean checkedAddress(String address, String accountType, NetType netType) {
        if (address == null || "".equals(address)) {
            return false;
        }
        String[] addrs = address.split("-");
        if (addrs.length != 3 || !"T".equals(addrs[0])) {
            return false;
        }
        if (accountType.isEmpty()){
            return false;
        }
        String addressPrefix = NetType.MAIN.getValue() != netType.getValue() ? accountType + netType.getValue() : accountType;
        if (addressPrefix.isEmpty() || !addressPrefix.equals(addrs[1])) {
            return false;
        }
        try {
            Base58.decodeChecked(addrs[2]);
        } catch (AddressFormatException e) {
            return false;
        }
        return true;
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

    private <T> ResponseBase<T> _requestDirect(Account account, List<?> args, Class responseClassType, Request request) throws RequestTimeOutException, IOException {
        if (account == null) {
            account = instance.defaultAccount;
        }
        Map<String, String> argsMap = request.getArgs(account, args);
        ResponseBase<T> responseBase = null;
        responseBase = instance.topjService.send(argsMap, responseClassType);
        request.afterExecution(responseBase, argsMap);
        return responseBase;
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
        try {
            Map<String, String> argsMap = request.getArgs(account, args);
            ResponseBase<T> responseBase = null;
            responseBase = instance.topjService.send(argsMap, responseClassType);
            request.afterExecution(responseBase, argsMap);
            return responseBase;
        } catch (ConnectException | SocketTimeoutException e) {
            if (!EdgeUtils.updateTopjServiceIp(instance)) {
                e.printStackTrace();
                throw e;
            }
            return _requestCommon(account, args, responseClassType, request);
        }
    }

    private ResponseBase<XTransactionResponse> _sendTxCommon(Account account, List<?> args, Request request) throws RequestTimeOutException, IOException {
        if (account == null) {
            account = instance.defaultAccount;
        }
        try {
            Map<String, String> argsMap = request.getArgs(account, args);
            ResponseBase<XTransactionResponse> responseBase;
            responseBase = instance.topjService.send(argsMap, XTransactionResponse.class);
            XTransaction xTransaction = ArgsUtils.decodeXTransFromArgs(argsMap);
            XTransactionResponse xTransactionResponse = new XTransactionResponse();
            xTransactionResponse.setOriginalTxInfo(OriginalTxInfo.buildFromTx(xTransaction));
            responseBase.setData(xTransactionResponse);
            if (responseBase.getErrNo() == 0) {
                ResponseBase<XTransactionResponse> xTransactionPoll = transactionReceiptProcessor.waitForTransactionReceipt(instance, account, xTransaction.getTxHash());
                if (xTransactionPoll != null && xTransactionPoll.getData() != null) {
                    xTransactionPoll.getData().getOriginalTxInfo().setXx64Hash(xTransaction.getXx64Hash());
                    responseBase.setData(xTransactionPoll.getData());
                } else if (xTransaction != null && xTransactionPoll != null) {
                    responseBase.setErrNo(xTransactionPoll.getErrNo());
                    responseBase.setErrMsg(xTransactionPoll.getErrMsg());
                }
            }
            request.afterExecution(responseBase, argsMap);
            return responseBase;
        } catch (ConnectException | SocketTimeoutException e) {
            if (!EdgeUtils.updateTopjServiceIp(instance)) {
                e.printStackTrace();
                throw e;
            }
            return _sendTxCommon(account, args, request);
        }
    }
}
