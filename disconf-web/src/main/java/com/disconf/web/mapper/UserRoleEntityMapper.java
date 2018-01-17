package com.disconf.web.mapper;

import com.disconf.web.entity.UserRoleEntity;

import java.util.List;
import java.util.Map;

public interface UserRoleEntityMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserRoleEntity record);

    int insertSelective(UserRoleEntity record);

    UserRoleEntity selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserRoleEntity record);

    int updateByPrimaryKey(UserRoleEntity record);

    int updateUserRoleByRoleId(UserRoleEntity userRole);

    int updateUserRoleByUserId(UserRoleEntity userRole);

    List<UserRoleEntity> getRoleListByUserId(long id);

    List<UserRoleEntity> getRoleListByRoleId(long id);


    List<UserRoleEntity> selectAll();

    UserRoleEntity getUserRoleByUserIdAndRoleId(Map<String, Long> map);
}