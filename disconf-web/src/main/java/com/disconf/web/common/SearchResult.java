package com.disconf.web.common;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class SearchResult<T> {

    private int total;

    private List<T> rows;

    public SearchResult(int total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
