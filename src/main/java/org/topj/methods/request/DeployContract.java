package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.XAction;
import org.topj.methods.response.XTransaction;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeployContract implements Request {
    private final String METHOD_NAME = "send_transaction";
    private Account contractAccount;

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        if (args.size() != 5) {
            throw new ArgumentMissingException("except args size 5 , but got " + args.size());
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> params=new HashMap<String,Object>();
        contractAccount = account.genContractAccount();
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
            xTransaction.setTransactionType(XTransactionType.CreateContractAccount);
            xTransaction.setLastTransNonce(account.getNonce());
            xTransaction.setFireTimestamp(BigInteger.valueOf(new Date().getTime()/1000));
            xTransaction.setExpireDuration(TopjConfig.getExpireDuration());
            xTransaction.setLastTransHash(account.getLastHashXxhash64());
            xTransaction.setDeposit(TopjConfig.getDeposit());

            XAction sourceAction = new XAction();
            sourceAction.setActionType(XActionType.CreateConstractAccount);
            sourceAction.setAccountAddr(account.getAddress());
            BufferUtils sourceBufferUtils = new BufferUtils();
            byte[] sourceParamsBytes = sourceBufferUtils
                    .stringToBytes(args.get(3).toString())
                    .BigIntToBytes((BigInteger)args.get(1), 64)
                    .stringToBytes(args.get(4).toString()).pack();
            String sourceParamsHex = "0x" + StringUtils.bytesToHex(sourceParamsBytes);
            sourceAction.setActionParam(sourceParamsHex);

            XAction targetAction = new XAction();
            targetAction.setActionType(XActionType.CreateConstractAccount);
            if (contractAccount == null) {
                throw new ArgumentMissingException("need contract account obj");
            }
            targetAction.setAccountAddr(contractAccount.getAddress());
            BufferUtils bufferUtils = new BufferUtils();
            byte[] contractCodeBytes = bufferUtils
                    .BigIntToBytes((BigInteger)args.get(2), 64)
                    .stringToBytes(args.get(0).toString()).pack();
            String contractCodeBytesHex = "0x" + StringUtils.bytesToHex(contractCodeBytes);
            targetAction.setActionParam(contractCodeBytesHex);

//            BigInteger contractPrivKey = new BigInteger(contractAccount.getPrivateKey(), 16);
//            byte[] hashResultBytes = targetAction.set_digest();
//            String contractAuthHex = Secp256k1Helper.signData(hashResultBytes, contractPrivKey);
//            targetAction.setActionAuthorization(contractAuthHex);
            targetAction.setActionAuthorization("0x" + contractAccount.getPublicKey());

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

    }
}
