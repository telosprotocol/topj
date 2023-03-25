package org.topnetwork.account.property;

public enum ZoneIndex {
    CONSENSUS(0),
    BEACON(1),
    ZEC(2),
    ARCHIVE(14),
    EDGE(15)
    ;

    private int value;
    private ZoneIndex(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
