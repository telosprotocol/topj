package org.topj.core;

import org.junit.Test;
import org.topj.account.Account;

import java.io.IOException;

public class AccountTest {
    private Account account = null;
    @Test
    public void testAccount() throws IOException {
        account = new Account("f4c3c4267315a2a59452adc0c4e85279452e814d2e5db5455caf1c931af301ba");
        String ca = account.genContractAccount("1f2c65a02f61d9e95a90bbe2ec1f14e93de0ad12e409e5753b9a435e25c85374");
        System.out.println("parent account address >>>> " + account.getAddress());
        System.out.println("contract account address >>>> " + ca);
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
