package org.topj.core;

import org.junit.Test;
import org.topj.account.Account;
import org.topj.methods.property.NetType;
import sun.nio.ch.Net;

import java.io.IOException;

public class AccountTest {
    private Account account = null;
    @Test
    public void testAccount() throws IOException {
        account = new Account("0xf4c3c4267315a2a59452adc0c4e85279452e814d2e5db5455caf1c931af301ba");
        Account ca = account.genContractAccount("1f2c65a02f61d9e95a90bbe2ec1f14e93de0ad12e409e5753b9a435e25c85374");
        System.out.println("parent account address >>>> " + account.getAddress());
        System.out.println("parent account address >>>> " + account.getPublicKey());
        System.out.println("contract account address >>>> " + ca.getAddress());
        System.out.println("contract account address >>>> " + ca.getPublicKey());

        Account account2 = new Account("0x881123515b8a6173c844d3db8fd2825e655204d247b3379005795d3f176cf054", NetType.TEST);
        Account ca2 = account2.genContractAccount("0x0bf1cbfa693941c4ad8eac2ebf12d81c5c0c8cd2ae10f6f77577293940f98687");
        System.out.println("parent account address >>>> " + account2.getAddress());
        System.out.println("parent account address >>>> " + account2.getPublicKey());
        System.out.println("contract account address >>>> " + ca2.getAddress());
        System.out.println("contract account address >>>> " + ca2.getPublicKey());
        Float f = Float.valueOf("0.15625");
        byte[] bytes = floatToByteArray(f);
        System.out.println(bytes);
    }
    public byte[] floatToByteArray(float value) {
        int intBits =  Float.floatToIntBits(value);
        return new byte[] {
                (byte) (intBits >> 24), (byte) (intBits >> 16), (byte) (intBits >> 8), (byte) (intBits) };
    }
}
