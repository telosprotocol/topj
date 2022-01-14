package org.topnetwork.methods.property;

public enum NetType {
//    public final static String MAIN                   = "0";
//    public final static String TEST                   = "65535";
    MAIN(0), TEST(65535);

    private int value;
    private NetType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
