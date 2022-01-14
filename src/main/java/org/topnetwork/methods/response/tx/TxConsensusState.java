package org.topnetwork.methods.response.tx;

import com.alibaba.fastjson.annotation.JSONField;

public class TxConsensusState {

    @JSONField(name = "confirm_block_info")
    private ConfirmBlockInfo confirmBlockInfo;

    @JSONField(name = "recv_block_info")
    private RecvBlockInfo recvBlockInfo;

    @JSONField(name = "send_block_info")
    private SendBlockInfo sendBlockInfo;

    public ConfirmBlockInfo getConfirmBlockInfo() {
        return confirmBlockInfo;
    }

    public void setConfirmBlockInfo(ConfirmBlockInfo confirmBlockInfo) {
        this.confirmBlockInfo = confirmBlockInfo;
    }

    public RecvBlockInfo getRecvBlockInfo() {
        return recvBlockInfo;
    }

    public void setRecvBlockInfo(RecvBlockInfo recvBlockInfo) {
        this.recvBlockInfo = recvBlockInfo;
    }

    public SendBlockInfo getSendBlockInfo() {
        return sendBlockInfo;
    }

    public void setSendBlockInfo(SendBlockInfo sendBlockInfo) {
        this.sendBlockInfo = sendBlockInfo;
    }
}
