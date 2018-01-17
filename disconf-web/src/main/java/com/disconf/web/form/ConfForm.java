package com.disconf.web.form;


import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author liaoqiqi
 * @version 2014-1-24
 */
public class ConfForm {

    /**
     *
     */
    private static final long serialVersionUID = 4556205917730218419L;

    private String app;

    private String version;

    private String env;

    private String key;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
