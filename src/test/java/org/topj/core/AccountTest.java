package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.account.property.AddressType;
import org.topj.account.property.ChainId;
import org.topj.account.property.ZoneIndex;
import org.topj.methods.Model.Proposal;
import org.topj.methods.property.AccountType;
import org.topj.methods.property.NetType;
import org.topj.procotol.http.HttpService;
//import sun.nio.ch.Net;

import java.io.IOException;
import java.math.BigInteger;

public class AccountTest {
    private Account account = null;
    @Test
    public void testAccount() throws IOException {
//        account = new Account("0x6bdfd021e19caf382a9ab80361c3f78e5a381efd7c25aa7ddbf9f1fb6f57c354");
////        Account ca = account.genContractAccount("1f2c65a02f61d9e95a90bbe2ec1f14e93de0ad12e409e5753b9a435e25c85374");
//        System.out.println("parent account address >>>> " + account.getAddress());
//        System.out.println("parent account address >>>> " + account.getPublicKey());
//        System.out.println("contract account address >>>> " + ca.getAddress());
//        System.out.println("contract account address >>>> " + ca.getPublicKey());
//
//        Account account2 = new Account("0x881123515b8a6173c844d3db8fd2825e655204d247b3379005795d3f176cf054", NetType.TEST);
//        Account ca2 = account2.genContractAccount("0x0bf1cbfa693941c4ad8eac2ebf12d81c5c0c8cd2ae10f6f77577293940f98687");
//        System.out.println("parent account address >>>> " + account2.getAddress());
//        System.out.println("parent account address >>>> " + account2.getPublicKey());
//        System.out.println("contract account address >>>> " + ca2.getAddress());
//        System.out.println("contract account address >>>> " + ca2.getPublicKey());
//        Float f = Float.valueOf("0.15625");
//        byte[] bytes = floatToByteArray(f);
//        System.out.println(bytes);

        HttpService httpService = new HttpService("http://192.168.50.193:19081");
        Topj topj = Topj.build(httpService);
        System.out.println("-----------------------------------------------------------------");
        account = new Account();
        Account pa = account.newAccount("0x4237d5b5c5d505898c41b14b2f59b06b46dc1d38ac2085b6255f81193124ad65", AddressType.ACCOUNT, ChainId.MAIN, ZoneIndex.CONSENSUS, "");
        System.out.println("parent account address >>>> " + pa.getAddress());
        System.out.println("parent account address >>>> " + pa.getPublicKey());
        Account na = account.newAccount("0x11b94f379a7c7e09990ec41d92496ad702544db3e7548b8f912dda6e8d707d2b", AddressType.CUSTOM_CONTRACT, ChainId.MAIN, ZoneIndex.CONSENSUS, "T-0-LRhN7BTYozT2QLwa66gXYGKS99zuRfXsSB");
        System.out.println("contract account address >>>> " + na.getAddress());
        System.out.println("contract account address >>>> " + na.getPublicKey());

        System.out.println("check account address >>>> " + topj.checkedAddress("T-0-LZSHonyxEKwvt1YrfatmS8G2Lg83uqYd8S"));
        System.out.println("check account address >>>> " + topj.checkedAddress("T-3-MYLZqtX2dqaXtZjmTGFWjmkvR9gSxb12mm", AccountType.CONTRACT, NetType.MAIN));

//        Proposal proposal = new Proposal();
//        proposal.setProposalId("sss");
//        proposal.setParameter("archive_deposit");
//        proposal.setOrigValue("10000");
//        proposal.setNewValue("26");
//        proposal.setModificationDescription("ttt");
//        proposal.setProposalClientAddress("T-0-1Kc3sQi7wiX9STHjCYMpxbER9daPXc7wNe");
//        proposal.setDeposit(BigInteger.valueOf(400));
//        proposal.setChainTimerHeight(BigInteger.valueOf(40));
//        proposal.setUpdateType("update_action_parameter");
//        proposal.setPriority(BigInteger.valueOf(3));
//
//        System.out.println(JSON.toJSONString(proposal));
    }
    public byte[] floatToByteArray(float value) {
        int intBits =  Float.floatToIntBits(value);
        return new byte[] {
                (byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) (intBits) };
    }
}
