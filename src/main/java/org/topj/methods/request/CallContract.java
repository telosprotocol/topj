package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Request;
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
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class CallContract implements Request {
    private final String METHOD_NAME = "send_transaction";

    /**
     * 调用合约方法
     * @param account 调用方账户
     * @param args 三个参数，合约地址、合约方法、方法序列化后的参数
     * @return
     */
    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
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
            xTransaction.setTransactionType(XTransactionType.RunContract);
            xTransaction.setLastTransNonce(account.getNonce());
            xTransaction.setFireTimestamp(new Date().getTime()/1000);
            xTransaction.setExpireDuration(TopjConfig.getExpireDuration());
            xTransaction.setLastTransHash(account.getLastHashXxhash64());
            xTransaction.setDeposit(TopjConfig.getDeposit());

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(args.get(3).toString())
                    .longToBytes(Long.valueOf(args.get(4).toString()))
                    .stringToBytes(args.get(5).toString()).pack();
            String sourceActionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            XAction sourceAction = new XAction();
            sourceAction.setActionType(XActionType.AssertOut);
            sourceAction.setAccountAddr(account.getAddress());
            sourceAction.setActionParam(sourceActionParamHex);

            XAction targetAction = new XAction();
            targetAction.setActionType(XActionType.RunConstract);
            targetAction.setAccountAddr(args.get(0).toString());
            targetAction.setActionName(args.get(1).toString());
            targetAction.setActionParam(initTargetActionParam((List<?>)args.get(2)));

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
        try {
            XTransaction xTransaction = ArgsUtils.decodeXTransFromArgs(args);
            responseBase.setData(xTransaction);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String initTargetActionParam(List<?> contractParams) throws Exception {
        if (contractParams.size() == 0) {
            return "0x";
        }
        BufferUtils bufferUtils = new BufferUtils();
        bufferUtils.byteToBytes((byte)contractParams.size());
        for (Object o : contractParams) {
            if (o instanceof Long) {
                bufferUtils.byteToBytes((byte)1);
                bufferUtils.longToBytes((Long)o);
            } else if (o instanceof String) {
                bufferUtils.byteToBytes((byte)2);
                bufferUtils.stringToBytes((String) o);
            } else if (o instanceof Boolean) {
                bufferUtils.byteToBytes((byte)3);
                bufferUtils.byteToBytes((boolean)o ? (byte)1 : (byte)0);
            } else {
                throw new Exception("only support type Long String Boolean");
            }
        }
        return "0x" + StringUtils.bytesToHex(bufferUtils.pack());
    }
}
