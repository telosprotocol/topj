package org.topnetwork.account.property;

public enum AddressType {
    INVALID(0),

    ED25519_ACCOUNT(Character.getNumericValue('#')),
    ED25519_SUB_ACCOUNT(Character.getNumericValue('*')),

    ACCOUNT(Character.getNumericValue('0')),
    SUB_ACCOUNT(Character.getNumericValue('1')),
    CUSTOM_CONTRACT(Character.getNumericValue('3')),

    BLOCK_CONTRACT(Character.getNumericValue('a'))
    ;

    private int value;
    private AddressType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public String toString() {
        return String.valueOf(value);
    }
}
