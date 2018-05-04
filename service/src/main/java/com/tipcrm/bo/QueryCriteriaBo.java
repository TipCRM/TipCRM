package com.tipcrm.bo;

import com.tipcrm.constant.Conjunction;
import com.tipcrm.constant.CriteriaMethod;

public class QueryCriteriaBo {

    private String fieldName;

    private Conjunction conjunction;

    private CriteriaMethod method;

    private Object value;

    public Conjunction getConjunction() {
        return conjunction;
    }

    public void setConjunction(Conjunction conjunction) {
        this.conjunction = conjunction;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public CriteriaMethod getMethod() {
        return method;
    }

    public void setMethod(CriteriaMethod method) {
        this.method = method;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
