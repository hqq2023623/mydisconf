package com.disconf.web.search;

import com.disconf.web.common.PaginationParameter;

/**
 * @author lzj
 * @date 2018/1/5
 */
public class PermissionSearch extends PaginationParameter {

    //
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
