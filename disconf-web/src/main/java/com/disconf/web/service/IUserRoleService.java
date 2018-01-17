package com.disconf.web.service;

import com.disconf.web.entity.UserRoleEntity;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
public interface IUserRoleService {

    List<UserRoleEntity> selectAll();

    void addUserRole(UserRoleEntity userRole);

    void delUserRoles(long id);

    void update(UserRoleEntity userRole);

    List<UserRoleEntity> getRoleListByUserId(long userId);
}
