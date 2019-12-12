package org.topj.methods.property;

public class NodeType {
    private final static Long _auditor  = Long.valueOf(0x0001);
    private final static Long _validator  = Long.valueOf(0x0002);
    private final static Long _archive  = Long.valueOf(0x0004);

    public final static Long auditor = _auditor | _validator | _archive;
    public final static Long validator = _validator;
    public final static Long archive = _archive;
}
