package org.topj.core;

import org.junit.Test;
import org.topj.account.Account;

import java.io.IOException;

public class AccountTest {
    private Account account = null;
    @Test
    public void testAccount() throws IOException {
        account = new Account("e3244d994c4f817574c192789f044748984072e2c6b037a07dc6f43be8fbaf4f");
        Account ca = account.genContractAccount("718fcf714f20b7604dd915629e9b467acf57193fb2577177e4862e83a75a7270", account.getAddress());
        System.out.println("parent account address >>>> " + account.getAddress());
        System.out.println("contract account address >>>> " + ca.getAddress());
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
