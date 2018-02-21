package com.tipcrm.bo;
import org.springframework.data.domain.Sort;

public class QuerySortBo {

    private String fieldName;

    private Sort.Direction direction;

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Sort.Direction getDirection() {
        return direction;
    }

    public void setDirection(Sort.Direction direction) {
        this.direction = direction;
    }
}
