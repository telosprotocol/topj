package org.topj.methods.request;

import com.alibaba.fastjson.JSON;
import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.Request;
import org.topj.methods.RequestTransactionTemplate;
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

public class CallContract extends RequestTransactionTemplate {
    private final String METHOD_NAME = "sendTransaction";

    /**
     * 调用合约方法
     * @param account 调用方账户
     * @param args 三个参数，合约地址、合约方法、方法序列化后的参数
     * @return args
     */
    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null || account.getLastHash() == null) {
            throw new ArgumentMissingException("account token and last hash is required");
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.RunContract);
            xTransaction.setNote(args.get(5).toString());

            SenderAction senderAction = xTransaction.getSenderAction();
            BufferUtils bufferUtils = new BufferUtils();
            byte[] actionParamBytes = bufferUtils.stringToBytes(args.get(3).toString())
                    .BigIntToBytes((BigInteger)args.get(4), 64).pack();
            String sourceActionParamHex = "0x" + StringUtils.bytesToHex(actionParamBytes);
            senderAction.setActionParam(sourceActionParamHex);

            ReceiverAction receiverAction = xTransaction.getReceiverAction();
            receiverAction.setActionType(XActionType.RunConstract);
            receiverAction.setTxReceiverAccountAddr(args.get(0).toString());
            receiverAction.setActionName(args.get(1).toString());
            receiverAction.setActionParam(initTargetActionParam((List<?>)args.get(2)));

            super.SetSignResult(account, requestModel);
            return requestModel.toMap();
        } catch (IOException e){
            e.printStackTrace();;
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void afterExecution(ResponseBase responseBase, Map<String, String> args) {

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
                bufferUtils.byteToBytes((Boolean)o ? (byte)1 : (byte)0);
            } else {
                throw new Exception("only support type Long String Boolean");
            }
        }
        return "0x" + StringUtils.bytesToHex(bufferUtils.pack());
    }
}
