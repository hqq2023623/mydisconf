package com.disconf.web.form;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author liaoqiqi
 * @version 2014-9-11
 */
public class ZkDeployForm {

    private String appName;

    private String version;

    private String envName;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getEnvName() {
        return envName;
    }

    public void setEnvName(String envName) {
        this.envName = envName;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
