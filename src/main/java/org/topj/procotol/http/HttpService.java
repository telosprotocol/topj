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
package org.topj.procotol.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import okhttp3.*;
import org.topj.methods.response.ResponseBase;
import org.topj.procotol.TopjService;

import java.io.IOException;
import java.util.Map;

public class HttpService implements TopjService {

    private final String url;

    public static final String DEFAULT_URL = "http://localhost:19090/";

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FORM_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public HttpService(String url) {
        this.url = url;
    }

    public HttpService() {
        this(DEFAULT_URL);
    }

    @Override
    public <T> ResponseBase<T> send(Map<String, String> args, Class<T> responseClass) throws IOException {
        OkHttpClient httpClient = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : args.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }

        Request request = new Request.Builder()
                .url(url)
                .post(builder.build())
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new IOException("服务器端错误: " + response);
        }
        String respStr = response.body().string();
        ResponseBase responseBase = JSON.parseObject(respStr, new TypeReference<ResponseBase<T>>(responseClass) {});
        return responseBase;
    }

    @Override
    public void close() throws IOException {

    }
}
