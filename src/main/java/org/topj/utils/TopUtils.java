package org.topj.utils;

import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHashFactory;

import java.io.UnsupportedEncodingException;

public class TopUtils {

    /**
     * account address to table id
     * @param address account address
     * @return table id
     * @throws UnsupportedEncodingException
     */
    public static int addressToTableId(String address) throws UnsupportedEncodingException {
        XXHashFactory factory = XXHashFactory.fastestInstance();
        byte[] data = address.getBytes("UTF-8");
        XXHash32 hash32 = factory.hash32();
        int hash = hash32.hash(data, 0, data.length, 0);
        return hash & 1023;
    }

    public static String getUserVoteKey(String address, String voteContractAddress) throws UnsupportedEncodingException {
        int tableId = addressToTableId(address);
        String idHex = "0000" + Integer.toHexString(tableId);
        return voteContractAddress + "-" + idHex.substring(idHex.length() - 4, idHex.length());
    }
}
