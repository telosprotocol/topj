package org.topj.methods.property;

public enum BlockParameterName {
    LATEST("last"),
    HEIGHT("height"),
    PROP("prop");


    private String name;

    BlockParameterName(String name) {
        this.name = name;
    }

    public String getValue() {
        return name;
    }

}
