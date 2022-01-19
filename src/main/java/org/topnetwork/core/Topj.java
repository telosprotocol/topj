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

package org.topnetwork.core;

import com.alibaba.fastjson.JSON;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bitcoinj.core.AddressFormatException;
import org.bitcoinj.core.Base58;
import org.topnetwork.ErrorException.RequestTimeOutException;
import org.topnetwork.account.Account;
import org.topnetwork.account.Numeric;
import org.topnetwork.account.property.AccountUtils;
import org.topnetwork.exceptions.ArgsIllegalException;
import org.topnetwork.methods.Model.Proposal;
import org.topnetwork.methods.Model.TransferParams;
import org.topnetwork.methods.Request;
import org.topnetwork.methods.property.*;
import org.topnetwork.methods.request.*;
import org.topnetwork.methods.response.*;
import org.topnetwork.methods.response.block.TableBlockResponse;
import org.topnetwork.methods.response.block.UnitBlockResponse;
import org.topnetwork.methods.response.reward.NodeRewardResponse;
import org.topnetwork.methods.response.reward.VoterDividendResponse;
import org.topnetwork.methods.response.tx.XTransactionResponse;
import org.topnetwork.procotol.TopjService;
import org.topnetwork.tx.PollingTransactionReceiptProcessor;
import org.topnetwork.tx.TransactionReceiptProcessor;
import org.topnetwork.utils.*;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
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
     * gen account by private key bytes
     * @param privateKeyBytes private key bytes
     * @return account
     */
    public Account genAccount(byte[] privateKeyBytes) {
        return new Account(privateKeyBytes);
    }

    /**
     * create account by random
     * @return account
     */
    public Account genAccount() {
        return new Account();
    }
    /**
     * create account by table [0-63]
     * @return account
     */
    public Account genAccount(int table) { return AccountUtils.genAccount(table); }
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
        TransferParams transferParams = new TransferParams(to, "", amount, note, TopjConfig.getDeposit());
        return transfer(account, transferParams);
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
        TransferParams transferParams = new TransferParams(to, coinType, amount, note, TopjConfig.getDeposit());
        return transfer(account, transferParams);
    }

    /**
     * transfer one to another
     * @param account account
     * @param transferParams transferParams
     * @return transaction obj
     */
    public ResponseBase<XTransactionResponse> transfer(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new Transfer());
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
     * set vote
     * @param account account
     * @param voteInfo vote info
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> voteNode(Account account, Map<String, BigInteger> voteInfo) throws IOException {
        return _sendTxCommon(account, Arrays.asList(voteInfo, XTransactionType.Vote, "voteNode", TopjConfig.getDeposit(), ""), new VoteNode());
    }
    public ResponseBase<XTransactionResponse> voteNode(Account account, Map<String, BigInteger> voteInfo, BigInteger deposit, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(voteInfo, XTransactionType.Vote, "voteNode", deposit, note), new VoteNode());
    }

    /**
     * cancel vote
     * @param account account
     * @param voteInfo vote info
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unVoteNode(Account account, Map<String, BigInteger> voteInfo) throws IOException {
        return _sendTxCommon(account, Arrays.asList(voteInfo, XTransactionType.AbolishVote, "unvoteNode", TopjConfig.getDeposit(), ""), new VoteNode());
    }
    public ResponseBase<XTransactionResponse> unVoteNode(Account account, Map<String, BigInteger> voteInfo, BigInteger deposit, String note) throws IOException {
        return _sendTxCommon(account, Arrays.asList(voteInfo, XTransactionType.AbolishVote, "unvoteNode", deposit, note), new VoteNode());
    }

    public ResponseBase<VoteUsedResponse> listVoteUsed(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(account.getAddress()), String.class, new ListVoteUsed());
    }

    public ResponseBase<VoteUsedResponse> listVoteUsed(Account account, String voterAddress) throws IOException {
        return _requestCommon(account, Arrays.asList(voterAddress), VoteUsedResponse.class, new ListVoteUsed());
    }

    /**
     * pledge top gas, default state self
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeGas(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        transferParams.setTo(account.getAddress());
        return stakeGas(account, transferParams);
    }

    /**
     * pledge top gas, state for other
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeGas(Account account, BigInteger amount, String to) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        transferParams.setTo(to);
        return stakeGas(account, transferParams);
    }

    /**
     * pledge top gas
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> stakeGas(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new StakeGas());
    }

    /**
     * redeem top gas
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeGas(Account account, BigInteger amount) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        transferParams.setTo(account.getAddress());
        return unStakeGas(account, transferParams);
    }

    /**
     * redeem top gas
     * @param account account
     * @param amount amount
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeGas(Account account, BigInteger amount, String to) throws IOException {
        TransferParams transferParams = new TransferParams(amount);
        transferParams.setTo(to);
        return unStakeGas(account, transferParams);
    }

    /**
     * redeem top gas
     * @param account account
     * @param transferParams transfer params obj
     * @return transaction
     */
    public ResponseBase<XTransactionResponse> unStakeGas(Account account, TransferParams transferParams) throws IOException {
        return _sendTxCommon(account, Arrays.asList(transferParams), new UnStakeGas());
    }

    /**
     * node register
     * @param account account
     * @param mortgage mortgage for node
     * @param nodeType node type
     * @return transaction obj
     */
    public ResponseBase<XTransactionResponse> registerNode(Account account, BigInteger mortgage, String nodeType, String nickName, String key,BigInteger dividendRatio) throws IOException {
        return registerNode(account, mortgage, nodeType, nickName, key, TopjConfig.getDeposit(), "",dividendRatio);
    }
    public ResponseBase<XTransactionResponse> registerNode(Account account, BigInteger mortgage, String nodeType, String nickName, String key, BigInteger deposit, String note,BigInteger dividendRatio) throws IOException {
        checkNodeMortgage(mortgage, nodeType);
        if (nickName == null || nickName.length() < 4 || nickName.length() > 16) {
            throw new ArgsIllegalException("nickName length must be 4 ~ 16");
        }
        TransferParams transferParams = new TransferParams(mortgage);
        transferParams.setTransDeposit(deposit);
        transferParams.setNote(note);
        return _sendTxCommon(account, Arrays.asList(transferParams, nodeType, nickName, key,dividendRatio), new RegisterNode());
    }

    public ResponseBase<XTransactionResponse> registerNode(Account account, BigInteger mortgage, String nodeType, String nickName, String key, BigInteger deposit, String note,BigInteger dividendRatio, BigInteger networkId) throws IOException {
        checkNodeMortgage(mortgage, nodeType);
        if (nickName == null || nickName.length() < 4 || nickName.length() > 16) {
            throw new ArgsIllegalException("nickName length must be 4 ~ 16");
        }
        TransferParams transferParams = new TransferParams(mortgage);
        transferParams.setTransDeposit(deposit);
        transferParams.setNote(note);
        return _sendTxCommon(account, Arrays.asList(transferParams, nodeType, nickName, key,dividendRatio ,networkId), new RegisterNode());
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
            case NodeType.advance:
            case NodeType.auditor:
//            case NodeType.archive:
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

    public ResponseBase<XTransactionResponse> submitProposal(Account account, BigInteger type, String target, String value, BigInteger deposit, BigInteger effectiveHeight) throws IOException {
        return _sendTxCommon(account, Arrays.asList(type, target, value, deposit, effectiveHeight), new SubmitProposal());
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

    public ResponseBase<GeneralInfosResponse> getGeneralInfos(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), ChainInfoResponse.class, new GetGeneralInfos());
    }

    public ResponseBase<ClockBlockResponse> getClockBlock(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), ClockBlockResponse.class, new GetClockBlock());
    }

    public ResponseBase<StandBysDetail> getStandBys(Account account, String nodeAddr) throws IOException {
        return _requestCommon(account, Arrays.asList(nodeAddr), StandBysDetail.class, new GetStandBys());
    }

    public ResponseBase<String> getIssuanceDetail(Account account, Long height) throws IOException {
        return _requestCommon(account, Arrays.asList(height), String.class, new GetIssuanceDetail());
    }

    public ResponseBase<StandBysResponse> getAllStandBys(Account account) throws IOException {
        return _requestCommon(account, Arrays.asList(""), StandBysResponse.class, new GetStandBys());
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

    public ResponseBase<CGPResponse> getCGP(Account account) throws IOException {
        return _requestCommon(account, Collections.EMPTY_LIST, CGPResponse.class, new GetCGP());
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
                xTransactionResponse.getTxConsensusState().getConfirmBlockInfo() == null ||
                "".equals(xTransactionResponse.getTxConsensusState().getConfirmBlockInfo().getExecStatus())) {
            return TxStatus.PENDING.getStatus();
        }
        if ("success".equals(xTransactionResponse.getTxConsensusState().getConfirmBlockInfo().getExecStatus())) {
            return TxStatus.SUCCESS.getStatus();
        } else if ("failure".equals(xTransactionResponse.getTxConsensusState().getConfirmBlockInfo().getExecStatus())) {
            return TxStatus.SUCCESS.getStatus();
        }
        throw new RuntimeException("unknown tx status");
    }

    public boolean checkedAddress(String address) {
        if (address == null || "".equals(address) || address.length() < 5) {
            return false;
        }
        if (!"T".equals(address.substring(0,1))) {
            return false;
        }
        if ("0".equals(address.substring(1, 2))) {
            return checkedT0Address(address, NetType.MAIN);
        } else if ("8".equals(address.substring(1, 2))) {
            return checkedT8Address(address);
        } else {
            return false;
        }
    }

    private boolean checkedT8Address(String address) {
        if (!"0000".equals(address.substring(2,6))) {
            return false;
        }
        String cleanInput = Numeric.cleanHexPrefix(address.substring(6));
        try {
            Numeric.toBigIntNoPrefix(cleanInput);
        } catch (NumberFormatException e) {
            return false;
        }

        return cleanInput.length() == 40;
    }

    private boolean checkedT0Address(String address, NetType netType) {
        byte[] netTypeBytes = IntToBytes.intToBytes(netType.getValue());
        String netTypeStr = StringUtils.bytesToHex(netTypeBytes);
        if (netTypeStr.length() != 4) {
            netTypeStr = (netTypeStr + "0000").substring(0, 4);
        }
        if (!netTypeStr.equals(address.substring(2,6))) {
            return false;
        }
        try {
            Base58.decodeChecked(address.substring(6));
        } catch (AddressFormatException e) {
            return false;
        }
        return true;
    }

    public ResponseBase<UnitBlockResponse> getLastUnitBlock(Account account, String address) throws IOException {
        return getBlock(account, address, BlockParameterName.LATEST.getValue(), UnitBlockResponse.class);
    }

    public ResponseBase<UnitBlockResponse> getUnitBlockByHeight(Account account, String address, Integer height) throws IOException {
        return getBlock(account, address, height.toString(), UnitBlockResponse.class);
    }

    public ResponseBase<TableBlockResponse> getLastTableBlock(Account account, String address) throws IOException {
        return getBlock(account, address, BlockParameterName.LATEST.getValue(), TableBlockResponse.class);
    }

    public ResponseBase<TableBlockResponse> getTableBlockByHeight(Account account, String address, Integer height) throws IOException {
        return getBlock(account, address, height.toString(), TableBlockResponse.class);
    }

    public <T> ResponseBase<T> getBlock(Account account, String address, String height, Class responseClass) throws IOException {
        return _requestCommon(account, Arrays.asList(address, height), responseClass, new GetBlock());
    }

    public ResponseBase<List<BigInteger>> getLatestTables(Account account) throws IOException {
        return _requestCommon(account, Collections.emptyList(), List.class, new GetLatestTables());
    }

//    public <T> ResponseBase<T> getBlockProperties(Account account) throws IOException {
//        String key = TopUtils.getUserVoteKey(account.getAddress(), "T-s-oedRLvZ3eM5y6Xsgo4t137An61uoPiM9vS");
//        return _requestCommon(account, Arrays.asList(2, BlockParameterName.PROP.getValue(), "award_info", key), Object.class, new GetBlock());
//    }

    public static Map<String, String> generateV3Args(String address, List<String> params) throws IOException{
        return generateV3Args(address, "", params);
    }

    public static Map<String, String> generateV3Args(String address, String token, List<String> params) throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("version", TopjConfig.getVersion());
        map.put("target_account_addr", address);
        map.put("method", "sendTransactionV3");
        map.put("sequence_id", String.valueOf(System.currentTimeMillis()));
        map.put("token", token);

        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("params", params);
        map.put("body", JSON.toJSONString(paramsMap));
        return map;
    }

    /**
     * get default provider server url
     * @return server url
     * @throws IOException  IOException
     */
    public static String getDefaultServerUrl() throws IOException, URISyntaxException {
        return getDefaultHttpServerUrl("http://mainnet.edge.topnetwork.org");
    }

    /**
     * get default provider server url
     * @return server url
     * @throws IOException  IOException
     */
    public static String getTestDefaultServerUrl() throws IOException, URISyntaxException {
        return getDefaultHttpServerUrl("http://mainnet.edge.topnetwork.org");
    }

    /**
     * get default provider server url by a common url
     * @param serverUrl serverUrl
     * @return serverUrl
     * @throws IOException IOException
     */
    public static String getDefaultHttpServerUrl(String serverUrl) throws IOException, URISyntaxException {
        if(serverUrl == null || serverUrl == "") {
            return null;
        }
        CloseableHttpClient httpclient = HttpClients.createDefault();
        URI uri = new URIBuilder(serverUrl).build();
        CloseableHttpResponse response = null;
        HttpGet httpGet = new HttpGet(uri);
        try {
            response = httpclient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                String respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                List<String> serverInfoList = JSON.parseObject(respStr, List.class);
                if (serverInfoList == null || serverInfoList.size() == 0) {
                    throw new RuntimeException("获取节点地址列表失败");
                }
                Random rand = new Random();
                return  "http://" + serverInfoList.get(rand.nextInt(serverInfoList.size()));
            }
            throw new IOException("请求失败: " + response);
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
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
            xTransactionResponse.setOriginalTxInfo(xTransaction);
            responseBase.setData(xTransactionResponse);
            if (responseBase.getErrNo() == 0) {
                ResponseBase<XTransactionResponse> xTransactionPoll = transactionReceiptProcessor.waitForTransactionReceipt(instance, account, xTransaction.getTxHash());
                if (xTransactionPoll != null && xTransactionPoll.getData() != null) {
                    responseBase.setData(xTransactionPoll.getData());
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
