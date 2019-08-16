package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.procotol.http.HttpService;

public class TopjTest {
    private Topj topj = null;

    @Test
    public void tt() {
        HttpService httpService = new HttpService("http://128.199.181.220:19081");
        topj = Topj.build(httpService);
        topj.updateDefaultAccount("47ce7e773f76df0a43ebfb243e7fffcc0f67a37fd4b8c05700ec107e2c25b7a5");
        RequestTokenResponse requestTokenResponse = topj.requestToken();
        System.out.printf(JSON.toJSONString(requestTokenResponse));
        AccountInfoResponse accountInfoResponse = topj.accountInfo("");
        System.out.printf(JSON.toJSONString(accountInfoResponse));
        topj.transfer("T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", 3, "");
    }
}
