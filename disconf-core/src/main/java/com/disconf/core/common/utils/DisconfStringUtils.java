package com.disconf.core.common.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * 字符串处理
 *
 * @author lzj
 * @date 2018/1/9
 */
public class DisconfStringUtils {


    /**
     * @param source
     * @param token
     * @return empty list if blank
     */
    public static List<String> parseStringToStringList(String source,
                                                       String token) {
        List<String> result = new LinkedList<>();

        if (StringUtils.isBlank(source) || StringUtils.isBlank(token)) {
            return result;
        }

        String[] units = source.split(token);
        for (String unit : units) {
            result.add(unit);
        }
        return result;
    }


    /**
     * 分割并通过set去重
     * @param source
     * @param token
     * @return empty list if blank
     */
    public static Set<String> parseStringToStringSet(String source,
                                                     String token) {
        Set<String> result = new HashSet<>();

        if (StringUtils.isBlank(source) || StringUtils.isBlank(token)) {
            return result;
        }

        String[] units = source.split(token);
        for (String unit : units) {
            result.add(unit);
        }
        return result;
    }


}
