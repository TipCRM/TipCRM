package com.tipcrm.constant;

public enum Levels {
    NEW_USER("新员工");
    private String value;

    Levels(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
