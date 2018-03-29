package com.tipcrm.bo;
import java.util.List;

public class QueryResultBo<T> {

    private List<T> data;

    private Integer page;

    private Integer size;

    private Long totalElements;

    private Integer totalPage;

    public QueryResultBo(List<T> data, Integer page, Integer size, Long totalElements, Integer totalPage) {
        this.data = data;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPage = totalPage;
    }

    public QueryResultBo() {
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
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

    public Long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(Long totalElements) {
        this.totalElements = totalElements;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
