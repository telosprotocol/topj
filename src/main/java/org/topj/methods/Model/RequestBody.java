package org.topj.methods.Model;

import com.alibaba.fastjson.JSON;
import org.topj.methods.response.XTransaction;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private String version;
    private String accountAddress;
    private String method;
    private String sequenceId;
    private XTransaction xTransaction;
    private Map<String, Object> argsMap;

    public Map<String, Object> toMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("version", TopjConfig.getVersion());
        map.put("target_account_addr", this.accountAddress);
        map.put("method", this.method);
        map.put("sequence_id", this.sequenceId);
        this.xTransaction.setReceiverAction(xTransaction.getxAction().getReceiverAction());
        this.xTransaction.setSenderAction(xTransaction.getxAction().getSenderAction());
        map.put("params", this.xTransaction);
        return map;
    }

    public Map<String, Object> toSimpleMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("version", TopjConfig.getVersion());
        map.put("target_account_addr", this.accountAddress);
        map.put("method", this.method);
        map.put("sequence_id", this.sequenceId);
        map.put("params", this.argsMap);
        return map;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAccountAddress() {
        return accountAddress;
    }

    public void setAccountAddress(String accountAddress) {
        this.accountAddress = accountAddress;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getSequenceId() {
        return sequenceId;
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public XTransaction getxTransaction() {
        return xTransaction;
    }

    public void setxTransaction(XTransaction xTransaction) {
        this.xTransaction = xTransaction;
    }

    public Map<String, Object> getArgsMap() {
        return argsMap;
    }

    public void setArgsMap(Map<String, Object> argsMap) {
        this.argsMap = argsMap;
    }
}
