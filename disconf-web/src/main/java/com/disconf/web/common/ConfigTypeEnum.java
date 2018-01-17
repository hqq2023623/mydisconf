package com.disconf.web.common;

import org.apache.commons.lang3.StringUtils;

/**
 * @author lzj
 * @date 2018/1/8
 */
public enum ConfigTypeEnum {

    FILE((byte) 0, "配置文件"),
    TEXT((byte) 1, "配置项")

    ;

    private Byte type;

    private String typeDesc;

    ConfigTypeEnum(Byte type, String typeDesc) {
        this.type = type;
        this.typeDesc = typeDesc;
    }

    public Byte getType() {
        return type;
    }

    public String getTypeDesc() {
        return typeDesc;
    }


    public static ConfigTypeEnum getEnum(Byte type) {
        for (ConfigTypeEnum configTypeEnum : ConfigTypeEnum.values()) {
            if (configTypeEnum.getType().equals(type)) {
                return configTypeEnum;
            }
        }
        return null;
    }

}
