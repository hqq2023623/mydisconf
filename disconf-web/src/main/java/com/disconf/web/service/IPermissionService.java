package com.disconf.web.service;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.PermissionEntity;
import com.disconf.web.search.PermissionSearch;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
public interface IPermissionService {

    List<PermissionEntity> selectAll();

    Response insert(PermissionEntity permission);

    Response delete(long id);

    SearchResult<PermissionEntity> selectByParam(PermissionEntity permissionSearch);

    PermissionEntity selectById(long id);

    Response update(PermissionEntity permission);



}
