package com.disconf.web.service;

import com.disconf.web.entity.RolePermission;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
public interface IRolePermissionService {


    void delete(Long id);

    void addRolePermission(RolePermission permissionRole);

    void update(RolePermission permissionRole);

    List<RolePermission> getPermissionListByRoleId(Long roleId);

    void delPermissionRoleByRoleId(Long roleId);
}
