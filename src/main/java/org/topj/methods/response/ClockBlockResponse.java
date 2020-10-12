package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

import java.math.BigInteger;

public class ClockBlockResponse {

    @JSONField(name = "timer_height")
    private BigInteger timerHeight;

    public BigInteger getTimerHeight() {
        return timerHeight;
    }

    public void setTimerHeight(BigInteger timerHeight) {
        this.timerHeight = timerHeight;
    }
}
