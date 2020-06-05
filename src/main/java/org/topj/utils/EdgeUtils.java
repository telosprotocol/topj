package org.topj.utils;

import org.topj.core.Topj;

import java.util.ArrayList;
import java.util.List;

public class EdgeUtils {

    private static List<String> ipList = new ArrayList<>();

    public static boolean updateTopjServiceIp(Topj topj) {
        if (ipList.size() == 0) {
            return false;
        }
        topj.getTopjService().updateServiceByIp(ipList.get(0));
        ipList.remove(0);
        return true;
    }
}
