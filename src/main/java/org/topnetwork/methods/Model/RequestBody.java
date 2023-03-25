package org.topnetwork.methods.Model;

import org.topnetwork.methods.response.XTransaction;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestBody {
    private XTransaction xTransaction;
    private Map<String, Object> argsMap;

    public Map<String, Object> toMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("params", this.xTransaction);
        return map;
    }

    public Map<String, Object> toSimpleMap() throws IOException {
        Map<String, Object> map = new HashMap<>();
        map.put("params", this.argsMap);
        return map;
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
