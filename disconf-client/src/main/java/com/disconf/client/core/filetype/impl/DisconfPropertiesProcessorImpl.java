package com.disconf.client.core.filetype.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.disconf.client.core.filetype.DisconfFileTypeProcessor;
import com.disconf.client.support.utils.ConfigLoaderUtils;

/**
 * Properties 处理器
 *
 * @author knightliao
 */
public class DisconfPropertiesProcessorImpl implements DisconfFileTypeProcessor {

    @Override
    public Map<String, Object> getKvMap(String fileName) throws Exception {

        Properties properties;

        // 读取配置
        properties = ConfigLoaderUtils.loadConfig(fileName);

        Map<String, Object> map = new HashMap<>(properties.size());
        for (Map.Entry<Object, Object> entry : properties.entrySet()) {
            map.put(entry.getKey().toString(), entry.getValue());
        }

        return map;
    }
}
