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
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import org.topj.ErrorException.RequestTimeOutException;
import org.topj.methods.response.ResponseBase;
import org.topj.procotol.TopjService;
import org.topj.utils.StringUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class HttpService implements TopjService {

    private String url;
    private final OkHttpClient client;
    private long connectTimeout = 30;
    private long writeTimeout = 30;
    private long readTimeout = 30;

    public static final String DEFAULT_URL = "http://localhost:19081/";

    public static final MediaType JSON_MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FORM_MEDIA_TYPE = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

    public HttpService(String url) {
        this.url = url;
        client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
    }

    /**
     * HttpService
     * @param url url
     * @param connectTimeout connect time out (s)
     * @param writeTimeout write time out (s)
     * @param readTimeout read time out (s)
     */
    public HttpService(String url, Long connectTimeout, Long writeTimeout, Long readTimeout) {
        this.url = url;
        client = new OkHttpClient.Builder()
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .writeTimeout(writeTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeout, TimeUnit.SECONDS)
                .build();
    }

    public HttpService() {
        this(DEFAULT_URL);
    }

    @Override
    public Boolean updateServiceByIp(String ip) {
        this.url = "http://" + ip + ":19081";
        return true;
    }

    @Override
    public <T> ResponseBase<T> send(Map<String, String> args, Class<T> responseClass) throws IOException {
//        FormBody.Builder builder = new FormBody.Builder();
//        for (Map.Entry<String, String> entry : args.entrySet()) {
//            builder.add(entry.getKey(), entry.getValue());
//        }
//        Request request = new Request.Builder()
//                .url(url)
//                .addHeader("Content-Type", "application/json;charset:utf-8")
//                .post(builder.build())
//                .build();
//        Response response = client.newCall(request).execute();
//        if (!response.isSuccessful()) {
//            throw new IOException("服务器端错误: " + response);
//        }
//        byte[] bytes = response.body().bytes();
//        String respStr = new String(bytes, "UTF-8");
        try {
            HttpClient client = HttpClient.newHttpClient();

            String requestBody = "";
            for (Map.Entry<String, String> entry : args.entrySet()) {
                requestBody += entry.getKey()+"="+entry.getValue() + "&";
            }
            requestBody = requestBody.substring(0, requestBody.length()-1);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request,
                    HttpResponse.BodyHandlers.ofString());
            String respStr = response.body();
//            System.out.println("args > " + JSON.toJSONString(args));
//            System.out.println("resp > " + respStr);
            ResponseBase responseBase = JSON.parseObject(respStr, new TypeReference<ResponseBase<T>>(responseClass) {});
            return responseBase;
        } catch (InterruptedException e) {
            e.printStackTrace();
            throw new IOException("连接中断");
        }
    }

    @Override
    public void close() throws IOException {

    }
}
