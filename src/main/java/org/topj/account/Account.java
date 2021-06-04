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

import org.bitcoinj.core.*;
import org.topj.account.property.AccountUtils;
import org.topj.account.property.AddressType;
import org.topj.account.property.ChainId;
import org.topj.account.property.ZoneIndex;
import org.topj.methods.property.NetType;
import org.topj.utils.IntToBytes;
import org.topj.utils.StringUtils;

import java.math.BigInteger;
import java.util.Base64;
import java.util.InputMismatchException;

/**
 *
 */
public class Account {
    private String privateKey;
    private byte[] privateKeyBytes;
    private String publicKey;
    private String address;
    private String identityToken;
    private String sequenceId;
    private String addressType;
    private int netType;

    private String lastHash;
    private String lastHashXxhash64;
    private BigInteger lastUnitHeight = BigInteger.ZERO;
    private BigInteger nonce = BigInteger.ZERO;
    private BigInteger balance = BigInteger.ZERO;
    public static final int ADDRESS_SIZE = 160;
    public static final int ADDRESS_LENGTH_IN_HEX = ADDRESS_SIZE >> 2;
    static final int PUBLIC_KEY_SIZE = 64;
    static final int PUBLIC_KEY_LENGTH_IN_HEX = PUBLIC_KEY_SIZE << 1;

    public Account(String privateKey, String addressType, String parentAddress, int netType){
        ECKey ecKey;
        if (privateKey.isEmpty()){
            ecKey = new ECKey();
            ecKey = ecKey.decompress();
            privateKey = ecKey.getPrivateKeyAsHex();
        }
        if (privateKey.indexOf("0x") == 0 || privateKey.length() == 64){
            ECKeyPair ecKeyPair = getECKeyPair(privateKey);
            this.privateKey = ecKeyPair.getPrivateKey().toString(16);
            privateKeyBytes = ecKeyPair.getPrivateKey().toByteArray();
            publicKey = ecKeyPair.getPublicKey().toString(16);
            address = "T80000" + getAddress(Numeric.toHexStringWithPrefixZeroPadded(ecKeyPair.getPublicKey(), PUBLIC_KEY_LENGTH_IN_HEX));
            return;
        }
        byte[] bp = Base64.getDecoder().decode(privateKey);
        bp[0] &= 0x7F;
        bp[31] &= 0x7F;
        privateKey = StringUtils.bytesToHex(bp);
        BigInteger privKey = new BigInteger(privateKey, 16);
        ecKey = ECKey.fromPrivate(privKey, false);
        this.privateKey = ecKey.getPrivateKeyAsHex();
        privateKeyBytes = ecKey.getPrivKeyBytes();
        publicKey = ecKey.getPublicKeyAsHex();
        address = genAddressFromPubKey(publicKey, addressType, parentAddress, netType);
        this.addressType = addressType;
        this.netType = netType;
    }
    public static String getAddress(String publicKey) {
        String publicKeyNoPrefix = Numeric.cleanHexPrefix(publicKey);

        if (publicKeyNoPrefix.length() < PUBLIC_KEY_LENGTH_IN_HEX) {
            publicKeyNoPrefix =
                    Strings.zeros(PUBLIC_KEY_LENGTH_IN_HEX - publicKeyNoPrefix.length())
                            + publicKeyNoPrefix;
        }
        String hash = Hash.sha3(publicKeyNoPrefix);
        return hash.substring(hash.length() - ADDRESS_LENGTH_IN_HEX); // right most 160 bits
    }

    private ECKeyPair getECKeyPair(String pk) {
        pk = pk.indexOf("0x") < 0 ? pk : pk.substring(2);
        return ECKeyPair.create(new BigInteger(pk, 16));
    }

    public Account newAccount(String privateKey, AddressType addressType, ChainId chainId, ZoneIndex zoneIndex, String parentAddress) {
        int ledgerId = AccountUtils.makeLedgerId(chainId, zoneIndex);
        return new Account(privateKey, String.valueOf(addressType.getValue()), parentAddress, ledgerId);
    }

    /**
     * get account obj
     */
    public Account() {
        this("", AddressType.ACCOUNT.toString(), "", NetType.MAIN.getValue());
    }

    public Account(NetType netType){
        this("", AddressType.ACCOUNT.toString(), "", netType.getValue());
    }

    /**
     * get account obj by private key
     * @param privateKey private key
     */
    public Account(String privateKey){
        this(privateKey, AddressType.ACCOUNT.toString(), "", NetType.MAIN.getValue());
    }

    public Account(String privateKey, NetType netType){
        this(privateKey, AddressType.ACCOUNT.toString(), "", netType.getValue());
    }

    public Account genSubAccount(){
        if (!AddressType.ACCOUNT.toString().equals(addressType)) {
            throw new InputMismatchException("only main account can create sub account");
        }
        return new Account("", AddressType.SUB_ACCOUNT.toString(), address, netType);
    }

    public Account genSubAccount(String privateKey){
        if (!AddressType.ACCOUNT.toString().equals(addressType)) {
            throw new InputMismatchException("only main account can create sub account");
        }
        return new Account(privateKey, AddressType.SUB_ACCOUNT.toString(), address, netType);
    }

    /**
     * generate contract account address
     * @return contract Account
     */
    public Account genContractAccount(){
        if (!AddressType.ACCOUNT.toString().equals(addressType)) {
            throw new InputMismatchException("only main account can create contract account");
        }
        return new Account("", AddressType.CUSTOM_CONTRACT.toString(), address, netType);
    }

    /**
     * generate contract account address by privateKey
     * @return contract Account
     */
    public Account genContractAccount(String privateKey){
        if (!AddressType.ACCOUNT.toString().equals(addressType)) {
            throw new InputMismatchException("only main account can create contract account");
        }
        return new Account(privateKey, AddressType.CUSTOM_CONTRACT.toString(), address, netType);
    }

    private String genAddressFromPubKey(String publicKey, String addressType, String parentAddress, int netType){
        byte[] pubKeyBytes = StringUtils.hexToByte(publicKey);
        String addressBody = "";

        pubKeyBytes = execParentAddress(pubKeyBytes, parentAddress);
        byte[] newPubKeyBytes = execPublicKey(pubKeyBytes);

        String addressPrefix = execAddressPrefix(addressType, netType);
        int addressPrefixNum = execAddressPrefixNum(addressType, netType);

        byte[] ripemd160Bytes = Utils.sha256hash160(newPubKeyBytes);
        addressBody = encodeChecked(addressPrefixNum, ripemd160Bytes);
        return  "T" + addressPrefix + addressBody;
    }

    private String encodeChecked(int version, byte[] payload){
        int versionLength = getAddressPrefixLength(version);
        byte[] addressBytes = new byte[versionLength + payload.length + 4];
        writeAddressPrefix(version, addressBytes);
        System.arraycopy(payload, 0, addressBytes, versionLength, payload.length);
        byte[] checksum = Sha256Hash.hashTwice(addressBytes, 0, payload.length + versionLength);
        System.arraycopy(checksum, 0, addressBytes, payload.length + versionLength, 4);
        return Base58.encode(addressBytes);
    }

    private int getAddressPrefixLength(int value){
        if (value <= 0xFF) return 1;
        if (value <= 0xFFFF) return 2;
        if (value <= 0xFFFFFF) return 3;
        return 4;
    }

    private void writeAddressPrefix(int version, byte[] addressBytes){
        int index = 0;
        if (version > 0xFFFFFF) addressBytes[index++] = (byte)(version >> 24);
        if (version > 0xFFFF) addressBytes[index++] = (byte)(version >> 16);
        if (version > 0xFF) addressBytes[index++] = (byte)(version >> 8);
        addressBytes[index++] = (byte)(version & 0xFF);
    }

    private byte[] execParentAddress(byte[] publicKeyBytes, String parentAddress){
        if (!parentAddress.isEmpty()) {
            Integer size = parentAddress.length() > 65 ? 65 : parentAddress.length();
            for (int i = 0; i < size; i++) {
                publicKeyBytes[i] += parentAddress.charAt(i);
            }
        }
        return publicKeyBytes;
    }

    private byte[] execPublicKey(byte[] publicKeyBytes){
        int size = 33;
        if (publicKeyBytes[0] == (byte)4) {
            size = 65;
        } else if (publicKeyBytes[0] == (byte)0) {
            size = 1;
        }
        byte[] newPubKeyBytes = new byte[size];
        for (int i = 0; i < size; i++){
            newPubKeyBytes[i] = publicKeyBytes[i];
        }
        return newPubKeyBytes;
    }

    private int execAddressPrefixNum(String addressType, int netType){
        if (addressType.isEmpty()){
            return 0;
        }
        if (NetType.MAIN.getValue() != netType) {
            return addressType.charAt(0) + (netType << 8);
        }
        return addressType.charAt(0);
    }

    private String execAddressPrefix(String addressType, int netType){
        if (addressType.isEmpty()){
            return null;
        }
        byte[] netTypeBytes = IntToBytes.intToBytes(netType);
        String netTypeStr = StringUtils.bytesToHex(netTypeBytes);
        if (netTypeStr.length() != 4) {
            netTypeStr = (netTypeStr + "0000").substring(0, 4);
        }
        return addressType + netTypeStr;
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

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String identityToken) {
        this.identityToken = identityToken;
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

    public String getAddressType() {
        return addressType;
    }

    public int getNetType() {
        return netType;
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

    public BigInteger getNonce() {
        return nonce;
    }

    public void setNonce(BigInteger nonce) {
        this.nonce = nonce;
    }

    public BigInteger getBalance() {
        return balance;
    }

    public void setBalance(BigInteger balance) {
        this.balance = balance;
    }
}
