package org.topj.account.property;

public enum ChainId {
    MAIN(0), ROOTBEACON(128), TEST(255);

    private int value;
    private ChainId(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
