package com.disconf.web.service;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.RoleEntity;
import com.disconf.web.search.RoleSearch;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
public interface IRoleService {

    List<RoleEntity> selectAll();

    Response addRole(RoleEntity role);

    Response delete(long id);

    SearchResult<RoleEntity> selectByParam(RoleEntity roleEntity);

    RoleEntity selectById(long id);

    Response updateRole(RoleEntity role);



}
