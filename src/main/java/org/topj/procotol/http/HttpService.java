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
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.topj.methods.response.ResponseBase;
import org.topj.procotol.TopjService;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpService implements TopjService {

    private String url;
    private int connectTimeout = -1;
    private int connectionRequestTimeout = -1;
    private int socketTimeout = -1;

    public static final String DEFAULT_URL = "http://localhost:19081/";

    public HttpService(String url) {
        this.url = url;
    }

    /**
     * HttpService
     * @param url url
     * @param connectTimeout connect time out (s)
     * @param connectionRequestTimeout connect request time out (s)
     * @param socketTimeout read time out (s)
     */
    public HttpService(String url, Integer connectTimeout, Integer connectionRequestTimeout, Integer socketTimeout) {
        this.url = url;
        this.connectTimeout = connectTimeout;
        this.connectionRequestTimeout = connectionRequestTimeout;
        this.socketTimeout = socketTimeout;
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
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
        for (Map.Entry<String, String> entry : args.entrySet()) {
            parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
        httpPost.setEntity(formEntity);
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .build();
        httpPost.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(httpPost);
            if (response.getStatusLine().getStatusCode() == 200) {
                String respStr = EntityUtils.toString(response.getEntity(), "UTF-8");
//                System.out.println("args > " + JSON.toJSONString(args));
//                System.out.println("resp > " + respStr);
                ResponseBase responseBase = JSON.parseObject(respStr, new TypeReference<ResponseBase<T>>(responseClass) {});
                return responseBase;
            }
            throw new IOException("请求失败: " + response);
        } finally {
            if (response != null) {
                response.close();
            }
            httpclient.close();
        }
    }

    @Override
    public void close() throws IOException {

    }
}
