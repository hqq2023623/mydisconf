package com.disconf.web.mapper;

import com.disconf.web.entity.RolePermission;

import java.util.List;
import java.util.Map;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insertSelective(RolePermission record);

    RolePermission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RolePermission record);

    int updateByPrimaryKey(RolePermission record);

    List<RolePermission> selectByRoleId(long id);

    void updateByRoleId(RolePermission permissionRole);

    List<RolePermission> getPermissionRoleBypermissionId(long id);

    void updateByPermissionId(RolePermission permissionRole);

    List<RolePermission> selectAll();

    RolePermission getPermisionRoleByRoleIdAndPermissionId(Map<String, Long> map);

    List<RolePermission> getPermissionListByRoleId(long roleId);

    void deletPermissionRoleByRoleId(long roleId);
}