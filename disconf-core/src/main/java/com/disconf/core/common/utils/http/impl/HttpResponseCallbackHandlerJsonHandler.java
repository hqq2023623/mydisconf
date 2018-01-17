package com.disconf.core.common.utils.http.impl;

import java.io.IOException;

import com.alibaba.fastjson.JSON;
import com.disconf.core.common.utils.http.HttpResponseCallbackHandler;
import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;

/**
 * Created by knightliao on 16/1/7.
 */
public class HttpResponseCallbackHandlerJsonHandler<T> implements HttpResponseCallbackHandler<T> {

    private Class<T> clazz = null;

    public HttpResponseCallbackHandlerJsonHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T handleResponse(String requestBody, HttpEntity entity) throws IOException {

        String json = EntityUtils.toString(entity, "UTF-8");

        T response = JSON.parseObject(json,clazz);

        return response;
    }
}
