package org.topj.account;

import org.bitcoinj.core.Base58;
import org.bitcoinj.core.ECKey;
import org.bitcoinj.core.Utils;
import org.topj.utils.StringUtils;

import java.math.BigInteger;

public class KeyPair {
    private String privateKey;
    private byte[] privateKeyBytes;
    private String publicKey;
    private String address;

    public KeyPair() {
        privateKey = genRandomPriKey();
        publicKey = genPubKeyFromPriKey(this.privateKey);
        address = genAddressFromPubKey(this.publicKey);
    }

    public KeyPair(String privateKey){
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
}
