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

package org.topj.account;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;

/**
 *
 */
public class Account {

    private String privateKey;
    private byte[] privateKeyBytes;
    private String publicKey;
    private String address;
    private String token;
    private String sequenceId;

    private String lastHash;
    private String lastHashXxhash64;
    private BigInteger lastUnitHeight;
    private Long nonce = Long.valueOf(0);
    private BigInteger balance;

    public Account() {
        privateKey = genRandomPriKey();
        publicKey = genPubKeyFromPriKey(this.privateKey);
        address = genAddressFromPubKey(this.publicKey);
    }

    public Account(String privateKey){
        this.privateKey = privateKey;
        publicKey = genPubKeyFromPriKey(this.privateKey);
        address = genAddressFromPubKey(this.publicKey);
    }

    private String genRandomPriKey() {
        ECKey ceKey = new ECKey();
        String priKey = ceKey.getPrivateKeyAsHex();
        privateKeyBytes = ceKey.getPrivKeyBytes();
        return priKey;
    }

    private String genPubKeyFromPriKey(String privateKey){
        BigInteger privKey = new BigInteger(privateKey, 16);
        ECKey ceKey = ECKey.fromPrivate(privKey);
        privateKeyBytes = ceKey.getPrivKeyBytes();
        byte[] pubKeyBytes = ECKey.publicKeyFromPrivate(privKey, false);
        String publicKey = StringUtils.bytesToHex(pubKeyBytes);
        return publicKey;
    }

    private String genAddressFromPubKey(String publicKey){
        byte[] pubKeyBytes = StringUtils.hexToByte(publicKey);
        byte[] ripemd160Bytes = Utils.sha256hash160(pubKeyBytes);
        String address = "T-0-" + Base58.encodeChecked(0, ripemd160Bytes);
        return address;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public byte[] getPrivateKeyBytes() {
        return privateKeyBytes;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public String getAddress() {
        return address;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getSequenceId() {
//        if (sequenceId == null) {
//            sequenceId = String.valueOf(System.currentTimeMillis());
//        }
//        return sequenceId;
        return String.valueOf(System.currentTimeMillis());
    }

    public void setSequenceId(String sequenceId) {
        this.sequenceId = sequenceId;
    }

    public String getLastHash() {
        return lastHash;
    }

    public void setLastHash(String lastHash) {
        this.lastHash = lastHash;
    }

    public String getLastHashXxhash64() {
        return lastHashXxhash64;
    }

    public void setLastHashXxhash64(String lastHashXxhash64) {
        this.lastHashXxhash64 = lastHashXxhash64;
    }

    public BigInteger getLastUnitHeight() {
        return lastUnitHeight;
    }

    public void setLastUnitHeight(BigInteger lastUnitHeight) {
        this.lastUnitHeight = lastUnitHeight;
    }

    public Long getNonce() {
        return nonce;
    }

    public void setNonce(Long nonce) {
        this.nonce = nonce;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }
}
