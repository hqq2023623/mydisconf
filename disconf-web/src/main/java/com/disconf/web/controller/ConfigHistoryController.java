package com.disconf.web.controller;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.ConfigEntityHistory;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.service.IConfigHistoryService;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Controller
@RequestMapping("/config/history")
public class ConfigHistoryController {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHistoryController.class);

    @Autowired
    private IConfigHistoryService historyService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list() {
        return "history/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SearchResult<ConfigEntityHistory> listJson(ConfigEntityHistory entity) {
        return historyService.selectByParam(entity);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Response addPermission(@RequestBody ConfigEntityHistory historyEntity) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        historyEntity.setCreator(currentUser.getUserName());
        historyEntity.setUpdater(currentUser.getUserName());
        return historyService.insert(historyEntity);
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Response<String> deletePermission(@RequestBody Long[] ids) {
        StringBuilder msg = new StringBuilder();
        for (long id : ids) {
            msg.append("id:").append(id).append(historyService.delete(id));
        }
        return Response.result(Response.Status.SUCCESS.getCode(), msg.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Response updatePermission(@RequestBody ConfigEntityHistory historyEntity) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        historyEntity.setUpdater(currentUser.getUserName());
        return historyService.update(historyEntity);
    }


}
