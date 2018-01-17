package com.disconf.web.service.impl;

import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.mapper.RoleEntityMapper;
import com.disconf.web.mapper.RolePermissionMapper;
import com.disconf.web.mapper.UserRoleEntityMapper;
import com.disconf.web.entity.RoleEntity;
import com.disconf.web.entity.RolePermission;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.entity.UserRoleEntity;
import com.disconf.web.search.RoleSearch;
import com.disconf.web.service.IRoleService;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Service
public class RoleServiceImpl implements IRoleService {

    private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);


    @Autowired
    private RoleEntityMapper roleMapper;

    @Autowired
    private UserRoleEntityMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RoleEntity> selectAll() {
        return roleMapper.selectAll();
    }

    @Override
    public Response addRole(RoleEntity role) {
        String roleName = role.getName();
        RoleEntity localRole = roleMapper.selectByRoleName(roleName);
        if (localRole != null) {
            return Response.result(Response.Status.FAILURE.getCode(), "角色名称已存在！");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        role.setCreator(currentUser.getUserName());
        role.setUpdater(currentUser.getUserName());
        roleMapper.insert(role);
        return Response.success();
    }

    @Override
    public Response delete(long id) {
        List<UserRoleEntity> list = userRoleMapper.getRoleListByRoleId(id);
        if (!CollectionUtils.isEmpty(list)) {
            logger.warn("cannot delete this role {} , some user is bind to this role", id);
            return Response.success("删除失败，请先删除相关用户角色绑定！");
        }
        List<RolePermission> rpList = rolePermissionMapper.selectByRoleId(id);
        if (!CollectionUtils.isEmpty(rpList)) {
            logger.warn("cannot delete this role {} , some permission is bind to this role", id);
            return Response.success("删除失败，请先删除相关的权限角色绑定！");
        }
        roleMapper.delete(id);
        return Response.success("已删除");
    }

    @Override
    public SearchResult<RoleEntity> selectByParam(RoleEntity roleEntity) {
        List<RoleEntity> roleList = roleMapper.selectByParam(roleEntity);
        int total = roleMapper.selectCountByParam(roleEntity);
        return new SearchResult<>(total, roleList);
    }

    @Override
    public RoleEntity selectById(long id) {
        return roleMapper.selectById(id);
    }

    @Override
    public Response updateRole(RoleEntity role) {
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        role.setUpdater(currentUser.getUserName());
        String roleName = role.getName();
        RoleEntity localRole = roleMapper.selectByRoleName(roleName);
        if (localRole != null && !localRole.getId().equals(role.getId())) {
            return Response.result(Response.Status.FAILURE.getCode(), "角色名称已存在！");
        }
        //更新角色表
        roleMapper.update(role);

        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(role.getId());
        rolePermission.setRoleName(role.getName());
        rolePermission.setRoleDesc(role.getDescription());
        rolePermission.setUpdater((role.getUpdater()));
        rolePermission.setUpdateTime(role.getUpdateTime());
        //更新相应已绑定的角色权限表
        rolePermissionMapper.updateByRoleId(rolePermission);

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setRoleId(role.getId());
        userRole.setRoleName(role.getName());
        userRole.setRoleDesc(role.getDescription());
        userRole.setUpdateTime(role.getUpdateTime());
        userRole.setUpdater(role.getUpdater());
        //更新对应的用户角色表
        userRoleMapper.updateUserRoleByRoleId(userRole);
        return Response.success();
    }

}
