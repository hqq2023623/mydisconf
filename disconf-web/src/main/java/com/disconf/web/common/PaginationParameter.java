package com.disconf.web.common;

/**
 * 分页参数
 *
 * @author lzj
 * @date 2018/1/4
 */
public abstract class PaginationParameter {

    private String order;

    private Integer limit;

    private Long offset;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Long getOffset() {
        return offset;
    }

    public void setOffset(Long offset) {
        this.offset = offset;
    }
}
