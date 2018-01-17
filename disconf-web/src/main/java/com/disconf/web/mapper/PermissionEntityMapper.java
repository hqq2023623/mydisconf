package com.disconf.web.mapper;

import com.disconf.web.entity.PermissionEntity;
import com.disconf.web.search.PermissionSearch;

import java.util.List;

public interface PermissionEntityMapper extends BaseMapper<PermissionEntity> {

    List<PermissionEntity> search(PermissionSearch permissionSearch);


}