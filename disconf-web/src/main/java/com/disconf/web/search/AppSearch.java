package com.disconf.web.search;

import com.disconf.web.common.PaginationParameter;

/**
 * @author lzj
 * @date 2018/1/5
 */
public class AppSearch extends PaginationParameter {

    //
    private String groupName;

    //
    private String name;


    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
