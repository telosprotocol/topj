package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.RequestTokenResponse;
import org.topj.procotol.http.HttpService;

public class TopjTest {
    private Topj topj = null;
    private Account account = null;

    @Before
    public void setUp() {
        HttpService httpService = new HttpService("http://128.199.181.220:19081");
        topj = Topj.build(httpService);
        account = topj.createAccount("47ce7e773f76df0a43ebfb243e7fffcc0f67a37fd4b8c05700ec107e2c25b7a5");
    }

    @Test
    public void testRequestToken() {
        RequestTokenResponse requestTokenResponse = topj.requestToken(account);
        assert(account.getToken() != null);
    }

    @Test
    public void testAccountInfo(){
        AccountInfoResponse accountInfoResponse = topj.accountInfo("T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF");
    }

    @Test
    public void testTransfer(){
        topj.transfer(account,"T-0-1EHzT2ejd12uJx7BkDgkA7B5DS1nM6AXyF", 4, "");
    }
}
