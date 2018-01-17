package com.disconf.web.util.test;

import static org.junit.Assert.*;
import org.junit.Test;
import com.disconf.web.util.Md5Util;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class TestMd5Util {

    @Test
    public void testEncode() throws Exception {
        String expected = "dRXRlc6aUCMPY3iKTUU6Fg==";
        String passwd = "admin";
        String actual = Md5Util.encode(passwd);
        System.out.println(actual);

        assertEquals(expected,actual);



    }


}
