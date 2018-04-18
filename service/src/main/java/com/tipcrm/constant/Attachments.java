package com.tipcrm.constant;
public enum Attachments {
    DEFAULT_AVATAR("default-avatar");

    private String value;

    Attachments(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
