package com.disconf.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.PermissionEntity;
import com.disconf.web.entity.RoleEntity;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.service.IRolePermissionService;
import com.disconf.web.service.IRoleService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.disconf.web.entity.RolePermission;
import com.disconf.web.service.IPermissionService;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @Autowired
    private IRoleService roleService;

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public String permissionPage(Model model) {
        model.addAttribute("roleList", roleService.selectAll());
        return "permission/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list",method = RequestMethod.POST)
    public SearchResult<PermissionEntity> permissionList(PermissionEntity permissionSearch) {
        return permissionService.selectByParam(permissionSearch);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Response addPermission(@RequestBody PermissionEntity permissionEntity) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        permissionEntity.setCreator(currentUser.getUserName());
        permissionEntity.setUpdater(currentUser.getUserName());
        return permissionService.insert(permissionEntity);
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Response<String> deletePermission(@RequestBody Long[] ids) {
        StringBuilder msg = new StringBuilder();
        for (long id : ids) {
            msg.append("id:").append(id).append(permissionService.delete(id));
        }
        return Response.result(Response.Status.SUCCESS.getCode(), msg.toString());
    }

    @ResponseBody
    @RequestMapping(value = "/rolePermission/add")
    public void addPermissionRole(long roleId, String permissionIds) {
        UserEntity currentUser = (UserEntity)SecurityUtils.getSubject().getPrincipal();
        List<Long> permissionIdList = JSONObject.parseArray(permissionIds, Long.class);

        for (long permissionId : permissionIdList) {

            RolePermission permissionRole = new RolePermission();
            RoleEntity role = roleService.selectById(roleId);

            if (role != null) {
                permissionRole.setRoleId(roleId);
                permissionRole.setUpdater(currentUser.getUserName());
                permissionRole.setCreator(currentUser.getUserName());
                permissionRole.setRoleDesc(role.getDescription());
                permissionRole.setRoleName(role.getName());
            }

            PermissionEntity permission = permissionService.selectById(permissionId);

            if (permission != null) {
                permissionRole.setPermissionDesc(permission.getDescription());
                permissionRole.setPermissionId(permissionId);
                permissionRole.setPermissionName(permission.getName());
                permissionRole.setUrl(permission.getUrl());
            }

            rolePermissionService.addRolePermission(permissionRole);
        }

    }


    @ResponseBody
    @RequestMapping(value = "/rolePermission/del")
    public Response delPermissionRole(@RequestBody Long[] ids) {
        for (long id : ids) {
            rolePermissionService.delete(id);
        }
        return Response.success();
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Response updatePermission(@RequestBody PermissionEntity permission) {
        UserEntity currentUser = (UserEntity)SecurityUtils.getSubject().getPrincipal();
        permission.setUpdater(currentUser.getUserName());
        return permissionService.update(permission);
    }


}
