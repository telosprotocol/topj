package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class RequestTokenResponse {

    @JSONField(name = "secret_key")
    private String secretKey;

    @JSONField(name = "signature_method")
    private String signatureMethod;

    @JSONField(name = "signature_ver_code")
    private String signature_ver_code;

    @JSONField(name = "token")
    private String token;

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public String getSignature_ver_code() {
        return signature_ver_code;
    }

    public void setSignature_ver_code(String signature_ver_code) {
        this.signature_ver_code = signature_ver_code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
