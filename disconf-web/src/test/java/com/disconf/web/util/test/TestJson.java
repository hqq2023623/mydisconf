package com.disconf.web.util.test;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import com.disconf.core.common.model.Response;

/**
 * @author lzj
 * @date 2018/1/5
 */
public class TestJson {


    @Test
    public void test11() throws Exception {
        Response<String> res = Response.success();

        System.out.println(JSON.toJSON(res));



    }

}
