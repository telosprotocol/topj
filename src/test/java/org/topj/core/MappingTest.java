package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Before;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.block.TableBlockResponse;
import org.topj.procotol.http.HttpService;
import org.topj.utils.TopjConfig;

import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class MappingTest {
    protected Topj topj = null;
    protected Account firstAccount = null;
    protected Account secondAccount = null;
    @Before
    public void setUp() throws IOException {
//        HttpService httpService = new HttpService("http://206.189.210.106:19081");
//        HttpService httpService = new HttpService("http://192.168.50.120:19081");
        HttpService httpService = new HttpService("http://grpctn.topscan.io:19081");
        topj = Topj.build(httpService);
        firstAccount = topj.genAccount("VFrYloKdIff3bD3p+iYe8Ah9IsU/HtJ7aH7nT3GuECU=");
        secondAccount = topj.genAccount("OcrsXgxgNt0b/nKc78wU8bnOhQZ+2PKd5gn9DlYid3M=");
        System.out.println("T0 address > " + firstAccount.getAddress() + " > pk > " + firstAccount.getPrivateKey());
        System.out.println(topj.checkedAddress(firstAccount.getAddress()));
        System.out.println("T8 address > " + secondAccount.getAddress() + " > pk > " + secondAccount.getPrivateKey());
        System.out.println(topj.checkedAddress(secondAccount.getAddress()));
        topj.passport(firstAccount);
    }

    @Test
    public void addressTest() {
        firstAccount = topj.genAccount("yExX4ofpJ04JmhkVjFhUOWjfe1nvAUgGqBOuc2cdRxk=");
        secondAccount = topj.genAccount("b0032f8057051b611a7c0ea373da4d7a6764351030ed497e6134fd9e11775b19");
        Account defaultAccount = topj.genAccount();
        System.out.println("T0 address > " + firstAccount.getAddress() + " > pk > " + firstAccount.getPrivateKey());
        System.out.println(topj.checkedAddress(firstAccount.getAddress()));
        System.out.println("T8 address > " + secondAccount.getAddress() + " > pk > " + secondAccount.getPrivateKey());
        System.out.println(topj.checkedAddress(secondAccount.getAddress()));
        System.out.println("default address > " + defaultAccount.getAddress() + " > pk > " + defaultAccount.getPrivateKey());
        System.out.println(topj.checkedAddress(defaultAccount.getAddress()));
        System.out.println(topj.checkedAddress("T80000bb9bc244d798123fde783fcc1c72d3bb8c18941e"));
        System.out.println(topj.checkedAddress("T80000d99F1979c235c2f51b207a731d7a2264cBA62005"));
        System.out.println(topj.checkedAddress("T00000LTT1eq9qG6tv2yEKHNfkwMmtV6Lwf7F1XL"));
    }

    @Test
    public void blockTest() throws IOException {
        ResponseBase<List<BigInteger>> latestTables = topj.getLatestTables(firstAccount);
        System.out.println("latestTables info > " + JSON.toJSONString(latestTables.getData()));

        ResponseBase<AccountInfoResponse> accountInfoResponseBase = topj.getAccount(firstAccount);
        System.out.println("account > " + JSON.toJSONString(accountInfoResponseBase));

        topj.transfer(firstAccount, secondAccount.getAddress(), BigInteger.TEN, "test note");

        ResponseBase<TableBlockResponse> ubr = topj.getTableBlockByHeight(firstAccount, TopjConfig.getShardingTableBlockAddr() + "@15", 23);
        System.out.println("table block >> " + JSON.toJSONString(ubr));
    }
}
