package org.topj.methods.property;

import java.math.BigInteger;
import java.util.Arrays;

public class NodeType {
    private final static String _auditor  = "auditor";
    private final static String _validator  = "validator";
    private final static String _archive  = "archive";
    private final static String _edge  = "edge";
    private final static String _advance  = "advance";

    public final static String advanced =  _advance; //String.join(",", Arrays.asList(_auditor, _validator, _archive));
    public final static String validator = _validator;
    public final static String edge = _edge;
    public final static String archive = _archive;
    public final static String auditor = _auditor;
}
