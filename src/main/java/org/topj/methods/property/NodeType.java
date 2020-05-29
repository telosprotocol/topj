package org.topj.methods.property;

import java.math.BigInteger;
import java.util.Arrays;

public class NodeType {
    private final static String _auditor  = "auditor";
    private final static String _validator  = "validator";
    private final static String _archive  = "archive";
    private final static String _edge  = "edge";

    public final static String advanced = _auditor; // String.join(",", Arrays.asList(_auditor, _validator, _archive));
    public final static String validator = _validator;
    public final static String edge = _edge;
}
