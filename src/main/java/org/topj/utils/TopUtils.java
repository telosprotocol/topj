package org.topj.utils;

import net.jpountz.xxhash.XXHash32;
import net.jpountz.xxhash.XXHashFactory;

import java.io.UnsupportedEncodingException;
import java.util.List;

public class TopUtils {

    public static byte[] initParam (List<?> params) {
        if (params.size() == 0) {
            return new byte[0];
        }
        BufferUtils bufferUtils = new BufferUtils();
        for (Object o : params) {
            if (o instanceof Short) {
                bufferUtils.shortToBytes((Short)o);
            } else if (o instanceof Integer) {
                bufferUtils.int32ToBytes((Integer)o);
            } else if (o instanceof Long) {
                bufferUtils.longToBytes((Long)o);
            } else if (o instanceof String) {
                bufferUtils.stringToBytes((String)o);
            }
        }
        return bufferUtils.pack();
    }

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
