package org.topnetwork.methods.property;

public enum BlockParameterName {
    LATEST("latest"),
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
