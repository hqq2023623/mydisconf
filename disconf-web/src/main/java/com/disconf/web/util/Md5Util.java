package com.disconf.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.nio.charset.Charset;
import java.security.MessageDigest;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class Md5Util {

    private static final Logger logger = LoggerFactory.getLogger(Md5Util.class);

    public static final String SAULT = "DISCONF";

    public static String encode(String str) {
        String encoded = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            str += SAULT;
            //加密后的字符串
            encoded = base64en.encode(md5.digest(str.getBytes(Charset.forName("utf-8"))));
        } catch (Exception e) {
            logger.error("MD5 error ,str= " + str, e);
        }
        return encoded;
    }


}
