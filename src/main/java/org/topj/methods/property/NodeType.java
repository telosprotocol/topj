package org.topj.methods.property;

import java.math.BigInteger;

public class NodeType {
    private final static Long _auditor  = Long.valueOf(0x0001);
    private final static Long _validator  = Long.valueOf(0x0002);
    private final static Long _archive  = Long.valueOf(0x0004);
    private final static Long _edge  = Long.valueOf(0x0008);

    public final static BigInteger advanced = BigInteger.valueOf(_auditor | _validator | _archive);
    public final static BigInteger validator = BigInteger.valueOf(_validator);
    public final static BigInteger edge = BigInteger.valueOf(_edge);
}
