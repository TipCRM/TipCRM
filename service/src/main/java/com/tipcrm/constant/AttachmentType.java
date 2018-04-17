package com.tipcrm.constant;
public enum AttachmentType {
    AVATAR("头像"),
    FILE("文件");

    private String value;

    AttachmentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
