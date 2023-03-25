/*
 * Copyright 2019 Sawyer Song
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.topnetwork.secp256K1Native;

import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Sha256Hash;
import org.bitcoinj.core.Utils;
import org.topnetwork.utils.StringUtils;

import java.math.BigInteger;

public class Secp256k1Helper {

    public static String signData(byte[] dataBytes, BigInteger privateKey) throws Exception {
        ECKey ceKey = ECKey.fromPrivate(privateKey, false);
        Sha256Hash sha256Hash = Sha256Hash.wrap(dataBytes);
        ECKey.ECDSASignature sig = ceKey.sign(sha256Hash);

        byte recId = ceKey.findRecoveryId(sha256Hash, sig);
        byte[] r = Utils.bigIntegerToBytes(sig.r, 32);
        byte[] s = Utils.bigIntegerToBytes(sig.s, 32);
        String authHex = StringUtils.bytesToHex(r) + StringUtils.bytesToHex(s);
        //String authHex = StringUtils.bytesToHex(sig.r.toByteArray()) + StringUtils.bytesToHex(sig.s.toByteArray());
        if (authHex.length() == 130) {
            if(recId == 1 && "00".equals(authHex.substring(0, 2))) {
                authHex = authHex.replaceFirst("00", "01");
            }
            return "0x" + authHex;
        }
        if(recId == 1) {
            authHex = "01" + authHex;
        } else {
            authHex = "00" + authHex;
        }
        return "0x" + authHex;
    }

}
