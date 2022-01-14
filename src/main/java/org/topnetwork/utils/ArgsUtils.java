package org.topnetwork.utils;

import com.alibaba.fastjson.JSON;
import org.topnetwork.methods.response.XTransaction;

import java.util.Map;

public class ArgsUtils {

    public static XTransaction decodeXTransFromArgs(Map<String, String> args) {
        try {
            if (args.isEmpty() || !args.containsKey("body")) {
                return null;
            }
            Map<String, Object> body = JSON.parseObject(args.get("body"), Map.class);
            if (body.isEmpty() || !body.containsKey("params")) {
                return null;
            }
            XTransaction xTransaction = JSON.parseObject(body.get("params").toString(), XTransaction.class);
            return xTransaction;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
