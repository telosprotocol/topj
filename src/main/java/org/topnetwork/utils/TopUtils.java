package org.topnetwork.utils;

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
}
