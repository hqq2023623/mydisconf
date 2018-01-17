package com.disconf.web.search;

import com.disconf.web.common.PaginationParameter;
import com.disconf.web.entity.ConfigEntity;

/**
 * @author lzj
 * @date 2018/1/7
 */
public class ConfigSearch extends PaginationParameter {

    //
    private String groupName;

    //
    private String appName;

    //
    private String configName;

    public static ConfigSearch fromConfigEntity(ConfigEntity configEntity) {
        ConfigSearch search = new ConfigSearch();
        search.setAppName(configEntity.getAppName());
        search.setConfigName(configEntity.getConfigName());
        search.setGroupName(configEntity.getGroupName());
        return search;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getConfigName() {
        return configName;
    }

    public void setConfigName(String configName) {
        this.configName = configName;
    }
}
