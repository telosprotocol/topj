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
