package com.disconf.web.common;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * application.properties
 * @author lzj
 * @date 2018/1/11
 */
@Component
public class ApplicationProperties {

    @Value(value = "${zookeeper.hosts}")
    private String zkHosts;


    public String getZkHosts() {
        return zkHosts;
    }

    public void setZkHosts(String zkHosts) {
        this.zkHosts = zkHosts;
    }
}
