package com.disconf.web.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lzj
 * @date 2018/1/6
 */
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8817396837874512513L;

    private String creator;

    private String updater;

    private Date createTime;

    private Date updateTime;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdater() {
        return updater;
    }

    public void setUpdater(String updater) {
        this.updater = updater;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

}
