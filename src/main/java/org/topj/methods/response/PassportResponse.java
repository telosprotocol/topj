/*
 * Copyright 2019 Sawyer Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.topj.methods.response;

import com.alibaba.fastjson.annotation.JSONField;

public class PassportResponse {

    @JSONField(name = "secret_key")
    private String secretKey;

    @JSONField(name = "signature_method")
    private String signatureMethod;

    @JSONField(name = "signature_ver_code")
    private String signature_ver_code;

    @JSONField(name = "identity_token")
    private String identityToken;

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

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String identityToken) {
        this.identityToken = identityToken;
    }
}
