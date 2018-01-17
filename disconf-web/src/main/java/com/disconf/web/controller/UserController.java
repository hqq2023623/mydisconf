package com.disconf.web.controller;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.service.IUserService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.disconf.web.search.UserSearch;

import javax.servlet.http.HttpServletRequest;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String users() {
        return "user/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SearchResult<UserEntity> userList(UserEntity entity) throws Exception {
        return userService.selectByParam(entity);
    }

    @ResponseBody
    @RequestMapping("/add")
    public Response<String> addUser(@RequestBody UserEntity user) {
        return userService.addUser(user);
    }

    @ResponseBody
    @RequestMapping("/del")
    public Response<String> delUser(@RequestBody Long[] ids) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        String msg = "删除成功";
        for (Long id : ids) {
            //不能删除自己
            if (!currentUser.getId().equals(id)) {
                userService.delete(id);
            } else {
                msg += " ,不能删除当前登录用户";
            }
        }
        return Response.success(msg);
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Response<String> updateUser(@RequestBody UserEntity user) {
        return userService.update(user);
    }

    @ResponseBody
    @RequestMapping(value = "/resetPwd")
    public Response<String> resetPwd(@RequestBody long userId) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        return Response.result(Response.Status.SUCCESS.getCode(), userService.resetPwd(userId, currentUser.getUserName()));
    }

    @RequestMapping(value = "/update/password", method = RequestMethod.GET)
    public String updatePwdPage(Model model) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        model.addAttribute("useId", currentUser.getId());
        return "user/updatePassword";
    }

    @ResponseBody
    @RequestMapping(value = "/update/password", method = RequestMethod.POST)
    public Response<String> updatePwd(String pwd, String confirmPwd) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        return userService.updatePwd(pwd, confirmPwd, currentUser.getId());
    }

    @RequestMapping("/bindRole")
    public String bindrole(Model model, Long userId) {
        model.addAttribute("userList", userService.selectAll());
        model.addAttribute("userId", userId);
        return "user/bindRole";
    }


}
