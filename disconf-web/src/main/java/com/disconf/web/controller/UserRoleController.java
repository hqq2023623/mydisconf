package com.disconf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.disconf.web.entity.RoleEntity;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.service.IRoleService;
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
import com.disconf.web.entity.UserRoleEntity;
import com.disconf.web.service.IUserRoleService;
import com.disconf.web.service.IUserService;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Controller
@RequestMapping("/userRole")
public class UserRoleController {

    private static final Logger logger = LoggerFactory.getLogger(UserRoleController.class);

    @Autowired
    private IUserRoleService userRoleService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String userRoleListPage() {
        return "user/userRoleList";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SearchResult<UserRoleEntity> userRoleList(Long userId) {
        return new SearchResult<>(Integer.MAX_VALUE, userRoleService.getRoleListByUserId(userId));
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Response<String> addUerRole(long userId, String roleIds) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        UserEntity user = userService.selectById(userId);
        List<Long> roleIdList = JSONObject.parseArray(roleIds, Long.class);
        for (Long roleId : roleIdList) {
            UserRoleEntity userRole = new UserRoleEntity();
            userRole.setUserId(userId);
            if (user != null) {
                userRole.setUserName(user.getUserName());
            }

            userRole.setCreator(currentUser.getUserName());
            userRole.setUpdater(currentUser.getUserName());

            RoleEntity role = roleService.selectById(roleId);
            userRole.setRoleId(roleId);
            if (role != null) {
                userRole.setRoleName(role.getName());
                userRole.setRoleDesc(role.getDescription());
            }
            userRoleService.addUserRole(userRole);
        }
        return Response.success("绑定成功");
    }

    @ResponseBody
    @RequestMapping(value = "/delete")
    public Response updateUserRole(@RequestBody Long[] ids) {
        for (long id : ids) {
            userRoleService.delUserRoles(id);
        }
        return Response.success();
    }


}
