package org.topj.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

public class TxConsensusState {

    @JSONField(name = "confirm_unit_info")
    private ConfirmUnitInfo confirmUnitInfo;

    @JSONField(name = "recv_unit_info")
    private RecvUnitInfo recvUnitInfo;

    @JSONField(name = "send_unit_info")
    private SendUnitInfo sendUnitInfo;

    public ConfirmUnitInfo getConfirmUnitInfo() {
        return confirmUnitInfo;
    }

    public void setConfirmUnitInfo(ConfirmUnitInfo confirmUnitInfo) {
        this.confirmUnitInfo = confirmUnitInfo;
    }

    public RecvUnitInfo getRecvUnitInfo() {
        return recvUnitInfo;
    }

    public void setRecvUnitInfo(RecvUnitInfo recvUnitInfo) {
        this.recvUnitInfo = recvUnitInfo;
    }

    public SendUnitInfo getSendUnitInfo() {
        return sendUnitInfo;
    }

    public void setSendUnitInfo(SendUnitInfo sendUnitInfo) {
        this.sendUnitInfo = sendUnitInfo;
    }
}
