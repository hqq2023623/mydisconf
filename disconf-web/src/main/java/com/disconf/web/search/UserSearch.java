package com.disconf.web.search;

import com.disconf.web.common.PaginationParameter;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class UserSearch extends PaginationParameter {

    private String userName;

    private String realName;

    private String organizationCode;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }
}
