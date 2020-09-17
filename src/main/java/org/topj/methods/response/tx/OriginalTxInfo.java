package org.topj.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;
import org.topj.methods.response.ReceiverAction;
import org.topj.methods.response.SenderAction;
import org.topj.methods.response.XAction;
import org.topj.methods.response.XTransaction;

public class OriginalTxInfo extends XTransaction {
    @JSONField(name = "tx_action")
    private XAction txAction;

    public static OriginalTxInfo buildFromTx(XTransaction xTransaction){
        if (xTransaction == null) {
            return null;
        }
        OriginalTxInfo originalTxInfo = new OriginalTxInfo();
        originalTxInfo.setAuthorization(xTransaction.getAuthorization());
        originalTxInfo.setChallengeProof(xTransaction.getChallengeProof());
        originalTxInfo.setExt(xTransaction.getExt());
        originalTxInfo.setFromLedgerId(xTransaction.getFromLedgerId());
        originalTxInfo.setLastTxHash(xTransaction.getLastTxHash());
        originalTxInfo.setLastTxNonce(xTransaction.getLastTxNonce());
        originalTxInfo.setNote(xTransaction.getNote());
        originalTxInfo.setSendTimestamp(xTransaction.getSendTimestamp());
        originalTxInfo.setToLedgerId(xTransaction.getToLedgerId());
        originalTxInfo.setTxAction(new XAction());
        originalTxInfo.getTxAction().setSenderAction(xTransaction.getSenderAction());
        originalTxInfo.getTxAction().setReceiverAction(xTransaction.getReceiverAction());
        originalTxInfo.setTxDeposit(xTransaction.getTxDeposit());
        originalTxInfo.setTxExpireDuration(xTransaction.getTxExpireDuration());
        originalTxInfo.setTxHash(xTransaction.getTxHash());
        originalTxInfo.setTxLen(xTransaction.getTxLen());
        originalTxInfo.setTxRandomNonce(xTransaction.getTxRandomNonce());
        originalTxInfo.setTxStructureVersion(xTransaction.getTxStructureVersion());
        originalTxInfo.setTxType(xTransaction.getTxType());
        return originalTxInfo;
    }

    public XAction getTxAction() {
        return txAction;
    }

    public void setTxAction(XAction txAction) {
        this.txAction = txAction;
    }

    public ReceiverAction getReceiverAction() {
        return txAction.getReceiverAction();
    }

    public SenderAction getSenderAction() {
        return txAction.getSenderAction();
    }

}
