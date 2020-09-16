package org.topj.methods.Model;

import com.alibaba.fastjson.JSON;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestModel {
    private String version;
    private String accountAddress;
    private String method;
    private String sequenceId;
    private String token;
    private RequestBody requestBody;

    public Map<String, String> toMap() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("version", TopjConfig.getVersion());
        map.put("target_account_addr", this.accountAddress);
        map.put("method", this.method);
        map.put("sequence_id", this.sequenceId);
        map.put("token", this.token);

        Map<String, Object> params = requestBody.toMap();
        map.put("body", JSON.toJSONString(params));
        System.out.println(">>>" + JSON.toJSONString(params));
        return map;
    }

    public Map<String, String> toSimpleMap() throws IOException {
        Map<String, String> map = new HashMap<>();
        map.put("version", TopjConfig.getVersion());
        map.put("target_account_addr", this.accountAddress);
        map.put("method", this.method);
        map.put("sequence_id", this.sequenceId);
        map.put("token", this.token);

        Map<String, Object> params = requestBody.toSimpleMap();
        map.put("body", JSON.toJSONString(params));
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public RequestBody getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }
}
