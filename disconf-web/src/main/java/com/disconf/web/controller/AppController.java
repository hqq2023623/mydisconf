package com.disconf.web.controller;

import com.disconf.web.entity.AppEntity;
import com.disconf.web.entity.UserEntity;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.service.IAppService;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Controller
@RequestMapping("/app")
public class AppController {

    private static final Logger logger = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private IAppService appService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "app/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SearchResult<AppEntity> listJson(AppEntity entity) {
        return appService.selectByParam(entity);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Response addPermission(@RequestBody AppEntity appEntity) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        appEntity.setCreator(currentUser.getUserName());
        appEntity.setUpdater(currentUser.getUserName());
        return appService.insert(appEntity);
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Response<String> deletePermission(@RequestBody Long[] ids) {
        StringBuilder msg = new StringBuilder();
        for (long id : ids) {
            msg.append("id:").append(id).append(appService.delete(id));
        }
        return Response.result(Response.Status.SUCCESS.getCode(), msg.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Response updatePermission(@RequestBody AppEntity appEntity) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        appEntity.setUpdater(currentUser.getUserName());
        return appService.update(appEntity);
    }


}
