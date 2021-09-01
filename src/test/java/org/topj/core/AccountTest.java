package org.topj.core;

import com.alibaba.fastjson.JSON;
import org.bitcoinj.core.ECKey;
import org.junit.Test;
import org.topj.account.Account;
import org.topj.account.ECKeyPair;
import org.topj.account.property.AddressType;
import org.topj.account.property.ChainId;
import org.topj.account.property.ZoneIndex;
import org.topj.methods.Model.Proposal;
import org.topj.methods.property.NetType;
import org.topj.methods.response.AccountInfoResponse;
import org.topj.methods.response.ResponseBase;
import org.topj.methods.response.block.UnitBlockResponse;
import org.topj.methods.response.reward.VoterDividendResponse;
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

        HttpService httpService = new HttpService("http://grpc.topscan.io:19081");
//        HttpService httpService = new HttpService("http://142.93.30.153:19081");
        Topj topj = Topj.build(httpService);
        System.out.println("-----------------------------------------------------------------");
        account = new Account();
        Account pa = account.newAccount("0x4237d5b5c5d505898c41b14b2f59b06b46dc1d38ac2085b6255f81193124ad65", AddressType.ACCOUNT, ChainId.MAIN, ZoneIndex.CONSENSUS, "");
        System.out.println("parent account address >>>> " + pa.getAddress());
        System.out.println("parent account address >>>> " + pa.getPublicKey());
        Account na = account.newAccount("0x11b94f379a7c7e09990ec41d92496ad702544db3e7548b8f912dda6e8d707d2b", AddressType.CUSTOM_CONTRACT, ChainId.MAIN, ZoneIndex.CONSENSUS, pa.getAddress());
        System.out.println("contract account address >>>> " + na.getAddress());
        System.out.println("contract account address >>>> " + na.getPublicKey());

        System.out.println("check account address >>>> " + topj.checkedAddress(pa.getAddress()));
        System.out.println("check account address >>>> " + topj.checkedAddress("T00000LMH1LWTJxnKGHzg1QsHcppLPDLA8hrV1Gz"));

        for(int i=0;i<20;i++) {
            account = new Account();
            String pk = account.getPrivateKey();
            if (pk.length() == 63) {
                System.out.println(pk);
            }
        }
        System.out.println("done");

//        account.setAddress("T00000LcvZaypD3bHFHHh4PsXsy5ASDGAD4mmpFr");
//        account.setAddress("T00000LLDg1HW9hLAKrtexVRsNp5AMbCtNZvTwCc");
        topj.passport(account);
//        ResponseBase<VoterDividendResponse> s = topj.queryVoterDividend(account, "T00000LcvZaypD3bHFHHh4PsXsy5ASDGAD4mmpFr");
        String otherAddress = "T00000LcvZaypD3bHFHHh4PsXsy5ASDGAD4mmpFr";
        ResponseBase<AccountInfoResponse> infoR = topj.getAccount(account, otherAddress);
        ResponseBase<UnitBlockResponse> r = topj.getUnitBlockByHeight(account, otherAddress, 460);
//        topj.queryAllNodeReward(account);

        Proposal proposal = new Proposal();
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
