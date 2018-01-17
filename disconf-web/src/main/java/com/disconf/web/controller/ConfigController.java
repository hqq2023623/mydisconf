package com.disconf.web.controller;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.entity.EnvEntity;
import com.disconf.web.service.IConfigService;
import com.disconf.web.service.IEnvService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author lzj
 * @date 2018/1/6
 */
@Controller
@RequestMapping("/config")
public class ConfigController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private IConfigService configService;

    @Autowired
    private IEnvService envService;

    @RequestMapping(value = "/list/{envId}", method = RequestMethod.GET)
    public String list(@PathVariable Long envId, ModelMap map) {
        if (envId == null || envId <= 0) {
            return "unauthorized";
        }
        EnvEntity currentEnv = envService.selectById(envId);
        if (currentEnv == null) {
            return "unauthorized";
        }
        map.put("envList", envService.selectAll());
        map.put("currentEnv", currentEnv);
        return "config/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SearchResult<ConfigEntity> listJson(ConfigEntity entity) {
        return configService.selectByParam(entity);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Response add(@RequestBody ConfigEntity configEntity) {
        try {
            return configService.insert(configEntity);
        } catch (Exception e) {
            logger.error("add config exception,config=" + configEntity, e);
        }
        return Response.fail("add config fail");
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Response<String> del(@RequestBody Long[] ids) {
        StringBuilder msg = new StringBuilder();
        for (long id : ids) {
            msg.append("id:").append(id).append(configService.delete(id));
        }
        return Response.result(Response.Status.SUCCESS.getCode(), msg.toString());
    }

    /**
     * @param configEntity
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/update")
    public Response update(@RequestBody ConfigEntity configEntity) {
        try {
            return configService.update(configEntity);
        } catch (Exception e) {
            logger.error("update config exception ,entity=" + configEntity, e);
            return Response.fail("更新失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/update/text")
    public Response updateByText(@RequestBody ConfigEntity configEntity) {
        try {
            return configService.updateByText(configEntity.getId(), configEntity.getValue());
        } catch (Exception e) {
            logger.error("update by text exception ,configId=" + configEntity.getId() + " newValue=" + configEntity.getValue(), e);
            return Response.fail("更新失败");
        }
    }

    @ResponseBody
    @RequestMapping(value = "/upload/{configId}", method = RequestMethod.POST)
    public Response<String> upload(@PathVariable Long configId, @RequestParam("uploadingFile") MultipartFile uploadingFile) {
        try {
            return configService.doUpload(configId, uploadingFile);
        } catch (Exception e) {
            logger.error("上传文件失败,configId=" + configId + " , fileName=" + uploadingFile.getName(), e);
            return Response.fail("上传失败");
        }
    }


}
