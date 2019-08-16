package org.topj.secp256K1Native;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.topj.utils.StringUtils;

import java.math.BigInteger;

public class Secp256k1Helper {

    public static String signData(byte[] dataBytes, BigInteger privateKey) throws Exception {
        ECKey ceKey = ECKey.fromPrivate(privateKey, false);
        ECKey.ECDSASignature sig = ceKey.sign(Sha256Hash.wrap(dataBytes));
        String authHex = StringUtils.bytesToHex(sig.r.toByteArray()) + StringUtils.bytesToHex(sig.s.toByteArray());
        String zeroHex = "0000000000000000000000000000000000000000000000000000000000000000";
        Integer zeroLength = 130 - authHex.length();
        if (zeroLength > 0){
            authHex = zeroHex.substring(0, zeroLength) + authHex;
        }
        return "0x" + authHex;
    }

}
