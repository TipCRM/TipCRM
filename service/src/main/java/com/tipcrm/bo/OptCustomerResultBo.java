package com.tipcrm.bo;

public class OptCustomerResultBo {

    private String type;

    private Integer id;

    public OptCustomerResultBo(String type, Integer id) {
        this.type = type;
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
