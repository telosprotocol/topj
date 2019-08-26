package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.*;
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

public class Vote implements Request {

    private final String METHOD_NAME = "vote";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 3) {
            throw new ArgumentMissingException("except args size 3 , but got " + args.size());
        }
        if (account == null || account.getToken() == null || account.getLastHash() == null) {
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
            xTransaction.setTransactionType(XTransactionType.UnlockToken);
            xTransaction.setLastTransNonce(account.getNonce());
            xTransaction.setFireTimestamp(new Date().getTime() / 1000);
            xTransaction.setExpireDuration(Short.valueOf("100"));
            xTransaction.setLastTransHash(account.getLastHashXxhash64());
//            xTransaction.setGasLimit(10000);
//            xTransaction.setGasPrice(1);

            XAction sourceAction = new XAction();
            sourceAction.setActionType(XActionType.AssertOut);
            sourceAction.setAccountAddr(account.getAddress());
            BufferUtils buSource = new BufferUtils();
            byte[] lockHashBytes = StringUtils.hexToByte(args.get(0).toString().replaceFirst("0x", ""));
            byte[] apBuSource = buSource.stringToBytes(args.get(0).toString())
                    .int32ToBytes(lockHashBytes.length)
                    .bytesArray(lockHashBytes)
                    .longToBytes(Long.valueOf(args.get(1).toString()))
                    .longToBytes(Long.valueOf(args.get(2).toString()))
                    .pack();
            sourceAction.setActionParam("0x" + StringUtils.bytesToHex(apBuSource));

            XAction targetAction = new XAction();
            targetAction.setActionType(XActionType.UnlockToken);
            targetAction.setAccountAddr("T-vote_smart_constract");
            targetAction.setActionName("vote");
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(account.getAddress())
                    .stringToBytes(args.get(0).toString())
                    .int32ToBytes(lockHashBytes.length)
                    .bytesArray(lockHashBytes)
                    .longToBytes(Long.valueOf(args.get(1).toString()))
                    .longToBytes(Long.valueOf(args.get(2).toString()))
                    .pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
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
    public void afterExecution(ResponseBase responseBase) {

    }
}
