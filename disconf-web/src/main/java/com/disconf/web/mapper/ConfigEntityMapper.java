package com.disconf.web.mapper;

import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.search.ConfigSearch;

import java.util.List;

public interface ConfigEntityMapper extends BaseMapper<ConfigEntity> {

    //精确查询
    List<ConfigEntity> search(ConfigSearch configSearch);

    int updateWithoutValue(ConfigEntity configEntity);


}