package com.disconf.web.mapper;

import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.AppEntity;
import com.disconf.web.search.AppSearch;

import java.util.List;

public interface AppEntityMapper extends BaseMapper<AppEntity> {

    List<AppEntity> search(AppSearch appSearch);

}