package com.disconf.web.controller;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.RoleEntity;
import com.disconf.web.entity.RolePermission;
import com.disconf.web.search.RoleSearch;
import com.disconf.web.service.IRolePermissionService;
import com.disconf.web.service.IRoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Controller
@RequestMapping("/role")
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IRolePermissionService rolePermissionService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String roleListPage() {
        return "role/list";
    }

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public SearchResult<RoleEntity> roleList(RoleEntity roleEntity) {
        return roleService.selectByParam(roleEntity);
    }

    @ResponseBody
    @RequestMapping(value = "/add")
    public Response addRole(@RequestBody RoleEntity role) {
        return roleService.addRole(role);
    }

    @ResponseBody
    @RequestMapping(value = "/del")
    public Response<String> delete(@RequestBody Long[] ids) {
        StringBuilder msg = new StringBuilder();
        for (long id : ids) {
            msg.append("id:").append(id).append(roleService.delete(id));
        }
        return Response.result(Response.Status.SUCCESS, msg.toString());
    }

    @RequestMapping(value = "/bindPermission")
    public String bindPermissionPage(Model model, Long roleId) {
        model.addAttribute("roleList", roleService.selectAll());
        model.addAttribute("roleId", roleId);
        return "role/bindPermission";
    }

    @ResponseBody
    @RequestMapping(value = "/permissionList")
    public SearchResult<RolePermission> permissionRoleList(long roleId) {
        return new SearchResult<>(Integer.MAX_VALUE, rolePermissionService.getPermissionListByRoleId(roleId));
    }

    @ResponseBody
    @RequestMapping(value = "/update")
    public Response updateRole(@RequestBody RoleEntity role) {
        return roleService.updateRole(role);
    }


}
