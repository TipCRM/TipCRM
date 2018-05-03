package com.tipcrm.bo;

import java.util.List;

public class QueryRequestBo {

    private List<QueryCriteriaBo> criteria;

    private QuerySortBo sort;

    private Integer page;

    private Integer size;

    public List<QueryCriteriaBo> getCriteria() {
        return criteria;
    }

    public void setCriteria(List<QueryCriteriaBo> criteria) {
        this.criteria = criteria;
    }

    public QuerySortBo getSort() {
        return sort;
    }

    public void setSort(QuerySortBo sort) {
        this.sort = sort;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
