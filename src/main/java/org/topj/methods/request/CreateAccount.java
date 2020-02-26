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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CreateAccount implements Request {
    private final String METHOD_NAME = "send_transaction";

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getToken() == null) {
            throw new ArgumentMissingException("account token is required");
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
            xTransaction.setTransactionType(XTransactionType.CreateUserAccount);
            xTransaction.setLastTransNonce(BigInteger.ZERO);
            xTransaction.setFireTimestamp(BigInteger.valueOf(new Date().getTime()/1000));
            xTransaction.setExpireDuration(TopjConfig.getExpireDuration());
            xTransaction.setLastTransHash(TopjConfig.getCreateAccountLastTransHash());
            xTransaction.setDeposit(TopjConfig.getDeposit());

            XAction sourceAction = new XAction();
            sourceAction.setActionType(XActionType.SourceNull);
            sourceAction.setAccountAddr(account.getAddress());
            sourceAction.setActionParam("0x");

            XAction targetAction = new XAction();
            targetAction.setActionType(XActionType.CreateUserAccount);
            targetAction.setAccountAddr(account.getAddress());
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(account.getAddress()).pack();
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
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {

    }
}
