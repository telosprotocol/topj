package org.topj.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

public class XTransactionResponse {
    @JSONField(name = "original_tx_info")
    private OriginalTxInfo originalTxInfo;

    @JSONField(name = "tx_consensus_state")
    private TxConsensusState txConsensusState;

    /**
     * 判断该交易是否成功
     * 交易为单账户交易，交易只在交易发送方下进行一次共识，查询交易最终返回结果中只有"confirm_unit_info"的信息。
     * 交易为跨账户交易，交易总共需要进行三次共识，查询交易最终返回结果中包括三次共识的信息，包括"confirm_unit_info（发送方第二次共识）"、"recv_unit_info（接收方共识）"、"send_unit_info"（发送方第一次共识）
     * 根据返回参数confirm_unit_info中exec_status判断交易最终是否成功：
     * 1.当exec_status返回值为"success"，证明交易最终成功。
     * 2.当exec_status返回值为"failure"，则交易失败，此时recv_tx_exec_status返回值为"failure"，表明交易接收方共识失败。
     * 如查询交易返回的结果中，没有出现以上三个字段，证明此时交易共识还未完全结束，请稍后再查询。
     * @return (boolean) isSuccess
     */
    public Boolean isSuccess() {
        if (txConsensusState == null ||
                txConsensusState.getConfirmUnitInfo() == null ||
                "".equals(txConsensusState.getConfirmUnitInfo().getExecStatus())) {
            return null;
        }
        return "success".equals(txConsensusState.getConfirmUnitInfo().getExecStatus());
    }

    public Boolean isFailure() {
        if (txConsensusState == null ||
                txConsensusState.getConfirmUnitInfo() == null ||
                "".equals(txConsensusState.getConfirmUnitInfo().getExecStatus())) {
            return null;
        }
        return "failure".equals(txConsensusState.getConfirmUnitInfo().getExecStatus());
    }

    public OriginalTxInfo getOriginalTxInfo() {
        return originalTxInfo;
    }

    public void setOriginalTxInfo(OriginalTxInfo originalTxInfo) {
        this.originalTxInfo = originalTxInfo;
    }

    public TxConsensusState getTxConsensusState() {
        return txConsensusState;
    }

    public void setTxConsensusState(TxConsensusState txConsensusState) {
        this.txConsensusState = txConsensusState;
    }
}
