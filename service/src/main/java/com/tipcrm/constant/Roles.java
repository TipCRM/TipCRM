package com.tipcrm.constant;
public enum Roles {
    GENERAL_MANAGER("总经理"),
    NORMAL("普通员工"),
    DEPARTMENT_MANAGER("部门经理");

    private String value;

    Roles(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue() {
        return value;
    }
}
