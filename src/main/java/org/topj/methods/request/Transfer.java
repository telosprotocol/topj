package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.bitcoinj.core.ECKey;
import org.topj.account.Account;
import org.topj.methods.Request;
import org.topj.methods.response.*;
import org.topj.secp256K1Native.Secp256k1Helper;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;
import org.topj.utils.TopjConfig;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Transfer implements Request {
    private final String METHOD_NAME = "send_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (args.size() != 1) {
            // TODO: exception
        }
        Map<String,String> map=new HashMap<String,String>();
        Map<String, Object> params=new HashMap<String,Object>();
        try {
//            account.setSequenceId("1565702778278111");
//            account.setToken("19ffe01d-764c-415c-853e-c002794be07f");

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
            xTransaction.setTransactionType(XTransactionType.Transfer);
            xTransaction.setLastTransNonce(account.getNonce());
            xTransaction.setFireTimestamp(new Date().getTime() / 1000);
            xTransaction.setExpireDuration(Short.valueOf("100"));
            xTransaction.setLastTransHash(account.getLastHashXxhash64());

            // test data
//            xTransaction.setLastTransNonce(Long.valueOf(8));
//            xTransaction.setLastTransHash("0xbf2f01ec56faa1ce");
//            xTransaction.setFireTimestamp(Long.valueOf(1565702800));

            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes("").longToBytes(Long.valueOf(args.get(1).toString())).stringToBytes(args.get(2).toString()).pack();
//            byte[] actionParamBytes = bufferUtils.stringToBytes("").longToBytes(Long.valueOf(110)).stringToBytes("hello top hahah hahah ").pack();
            String actionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);

            XAction sourceAction = new XAction();
            sourceAction.setActionType(XActionType.AssertOut);
            sourceAction.setAccountAddr(account.getAddress());
            sourceAction.setActionParam(actionParamHex);

            XAction targetAction = new XAction();
            targetAction.setActionType(XActionType.AssetIn);
            targetAction.setAccountAddr(args.get(0).toString());
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
