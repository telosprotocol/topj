package org.topj.methods.request;

import org.topj.ErrorException.ArgumentMissingException;
import org.topj.account.Account;
import org.topj.methods.Model.RequestModel;
import org.topj.methods.RequestTransactionTemplate;
import org.topj.methods.property.XActionType;
import org.topj.methods.property.XTransactionType;
import org.topj.methods.response.*;
import org.topj.utils.BufferUtils;
import org.topj.utils.StringUtils;

import java.io.IOException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

public class DeployContract extends RequestTransactionTemplate {
    private final String METHOD_NAME = "sendTransaction";
    private Account contractAccount;

    @Override
    public Map<String, String> getArgs(Account account, List<?> args) {
        if (account == null || account.getIdentityToken() == null) {
            throw new ArgumentMissingException("account token is required");
        }
        if (args.size() != 5) {
            throw new ArgumentMissingException("except args size 5 , but got " + args.size());
        }
        RequestModel requestModel = super.getDefaultArgs(account, METHOD_NAME);
        contractAccount = account.genContractAccount();
        try {
            XTransaction xTransaction = requestModel.getRequestBody().getxTransaction();
            xTransaction.setTxType(XTransactionType.CreateContractAccount);
            xTransaction.setNote(args.get(4).toString());

            SenderAction senderAction = xTransaction.getSenderAction();
            BufferUtils sourceBufferUtils = new BufferUtils();
            byte[] sourceParamsBytes = sourceBufferUtils
                    .stringToBytes(args.get(3).toString())
                    .BigIntToBytes((BigInteger)args.get(1), 64).pack();
            String sourceParamsHex = "0x" + StringUtils.bytesToHex(sourceParamsBytes);
            senderAction.setActionParam(sourceParamsHex);

            ReceiverAction receiverAction = xTransaction.getReceiverAction();
            receiverAction.setActionType(XActionType.CreateConstractAccount);
            if (contractAccount == null) {
                throw new ArgumentMissingException("need contract account obj");
            }
            receiverAction.setTxReceiverAccountAddr(contractAccount.getAddress());
            BufferUtils bufferUtils = new BufferUtils();
            byte[] contractCodeBytes = bufferUtils
                    .BigIntToBytes((BigInteger)args.get(2), 64)
                    .stringToBytes(args.get(0).toString()).pack();
            String contractCodeBytesHex = "0x" + StringUtils.bytesToHex(contractCodeBytes);
            receiverAction.setActionParam(contractCodeBytesHex);

//            BigInteger contractPrivKey = new BigInteger(contractAccount.getPrivateKey(), 16);
//            byte[] hashResultBytes = receiverAction.set_digest();
//            String contractAuthHex = Secp256k1Helper.signData(hashResultBytes, contractPrivKey);
//            receiverAction.setActionAuthorization(contractAuthHex);
            receiverAction.setActionAuthorization("{\"authorization\":\"" + "0x" + contractAccount.getPublicKey() + "\"}");

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
}
