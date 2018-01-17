package com.disconf.web.controller;

import com.disconf.core.common.constants.DisConfigTypeEnum;
import com.disconf.web.common.ConfigTypeEnum;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.form.ConfForm;
import com.disconf.web.service.IConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;

/**
 * 给disconf-client调用
 *
 * @author lzj
 * @date 2018/1/11
 */
@Controller
@RequestMapping("/api/config")
public class ApiClientController {

    private static final Logger logger = LoggerFactory.getLogger(ApiClientController.class);


    @Autowired
    private IConfigService configService;

    /**
     * 获取配置文件
     *
     * @return
     */
    @RequestMapping(value = "/file", method = RequestMethod.GET)
    public void download(ConfForm confForm, HttpServletResponse resp) {
        try {
            ConfigEntity configEntity = new ConfigEntity();
            configEntity.setAppName(confForm.getApp());
            configEntity.setEnvName(confForm.getEnv());
            configEntity.setConfigName(confForm.getKey());
            configEntity.setVersion(confForm.getVersion());
//            configEntity.setType(ConfigTypeEnum.FILE.getType());

            //
            SearchResult<ConfigEntity> config = configService.selectByParam(configEntity);
            if (!config.getRows().isEmpty()) {
                //API获取节点内容也需要同样做格式转换
                this.downloadDspBill(confForm.getKey(), config.getRows().get(0).getValue(), resp);
            }
        } catch (Exception e) {
            logger.error("downloadFile exception, param=" + confForm, e);
        }
    }

    /**
     * 下载
     *
     * @param fileName
     * @param resp
     * @return
     */
    public void downloadDspBill(String fileName, String value, HttpServletResponse resp) throws Exception {
        resp.setCharacterEncoding("utf-8");
        resp.addHeader("Content-Disposition",
                "attachment;fileName=" + fileName);// 设置文件名
        OutputStream os = resp.getOutputStream();
        byte[] buffer = value.getBytes(Charset.forName("utf-8"));
        os.write(buffer);
    }

}
