package com.disconf.web.service.impl;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.mapper.PermissionEntityMapper;
import com.disconf.web.mapper.RolePermissionMapper;
import com.disconf.web.entity.PermissionEntity;
import com.disconf.web.entity.RolePermission;
import com.disconf.web.search.PermissionSearch;
import com.disconf.web.service.IPermissionService;
import com.disconf.web.shiro.ShiroFilterChainManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Service
public class PermissionServiceImpl implements IPermissionService {

    private static final Logger logger = LoggerFactory.getLogger(PermissionServiceImpl.class);

    @Autowired
    private PermissionEntityMapper permissionMapper;

    @Autowired
    private RolePermissionMapper permissionRoleMapper;

    @Autowired
    private ShiroFilterChainManager shiroFilerChainManager;

    @Override
    public List<PermissionEntity> selectAll() {
        return permissionMapper.selectAll();
    }

    @Override
    public Response insert(PermissionEntity permission) {
        PermissionSearch search = new PermissionSearch();
        search.setName(permission.getName());
        List<PermissionEntity> localPermission = permissionMapper.search(search);
        if (!localPermission.isEmpty()) {
            return Response.result(Response.Status.FAILURE.getCode(), "权限名称已存在！");
        }
        permission.setParentId(permission.getParentId() == null ? 0 : permission.getParentId());
        permissionMapper.insert(permission);
        updatePermissionFilter();
        return Response.success();
    }

    @Override
    public Response delete(long id) {
        List<RolePermission> list = permissionRoleMapper.getPermissionRoleBypermissionId(id);
        if (!CollectionUtils.isEmpty(list)) {
            logger.warn("cannot delete this permission {} , some role is bind to this permission", id);
            return Response.success("删除权限失败，请先删除角色与权限的绑定！");
        }
        permissionMapper.delete(id);
        updatePermissionFilter();
        return Response.success("已删除！");
    }

    @Override
    public SearchResult<PermissionEntity> selectByParam(PermissionEntity permissionSearch) {
        List<PermissionEntity> permissionList = permissionMapper.selectByParam(permissionSearch);
        int total = permissionMapper.selectCountByParam(permissionSearch);
        return new SearchResult<>(total, permissionList);
    }

    @Override
    public PermissionEntity selectById(long id) {
        return permissionMapper.selectById(id);
    }

    @Override
    public Response update(PermissionEntity permission) {
        PermissionSearch search = new PermissionSearch();
        search.setName(permission.getName());
        List<PermissionEntity> localPermission = permissionMapper.search(search);
        if (!localPermission.isEmpty()) {
            return Response.result(Response.Status.FAILURE.getCode(), "权限名称已存在！");
        }
        permission.setUpdateTime(new Date());
        //更新权限表
        permissionMapper.update(permission);
        RolePermission permissionRole = new RolePermission();
        permissionRole.setPermissionId(permission.getId());
        permissionRole.setPermissionName(permission.getName());
        permissionRole.setPermissionDesc(permission.getDescription());
        permissionRole.setUpdater(permission.getUpdater());
        permissionRole.setUrl(permission.getUrl());
        //更新相应已绑定的角色权限表
        permissionRoleMapper.updateByPermissionId(permissionRole);
        updatePermissionFilter();
        return Response.success();
    }

    private void updatePermissionFilter() {
        List<PermissionEntity> permissionList = permissionMapper.selectAll();
        shiroFilerChainManager.initFilterChains(permissionList);
    }



}
