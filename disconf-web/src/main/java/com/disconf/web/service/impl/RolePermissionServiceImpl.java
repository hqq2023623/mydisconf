package com.disconf.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.disconf.web.mapper.RolePermissionMapper;
import com.disconf.web.entity.RolePermission;
import com.disconf.web.service.IRolePermissionService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Service
public class RolePermissionServiceImpl implements IRolePermissionService {

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public void delete(Long id) {
        rolePermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void addRolePermission(RolePermission permissionRole) {
        Map<String, Long> map = new HashMap<>();
        map.put("roleId", permissionRole.getRoleId());
        map.put("permissionId", permissionRole.getPermissionId());
        RolePermission rolePermission = rolePermissionMapper.getPermisionRoleByRoleIdAndPermissionId(map);
        if (rolePermission != null) {
            //已经绑定过的权限不能再绑定
            return;
        }
        permissionRole.setCreateTime(new Date());
        permissionRole.setUpdateTime(new Date());
        rolePermissionMapper.insertSelective(permissionRole);
    }

    @Override
    public void update(RolePermission rolePermission) {
        if (rolePermission.getId() != null) {
            rolePermissionMapper.updateByPrimaryKeySelective(rolePermission);
        }
    }

    @Override
    public List<RolePermission> getPermissionListByRoleId(Long roleId) {
        return rolePermissionMapper.getPermissionListByRoleId(roleId);
    }

    @Override
    public void delPermissionRoleByRoleId(Long roleId) {
        rolePermissionMapper.deletPermissionRoleByRoleId(roleId);
    }

}
