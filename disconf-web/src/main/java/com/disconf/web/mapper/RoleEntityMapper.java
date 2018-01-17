package com.disconf.web.mapper;

import com.disconf.web.entity.RoleEntity;
import com.disconf.web.search.RoleSearch;

import java.util.List;

public interface RoleEntityMapper extends BaseMapper<RoleEntity> {

    RoleEntity selectByRoleName(String roleName);

}