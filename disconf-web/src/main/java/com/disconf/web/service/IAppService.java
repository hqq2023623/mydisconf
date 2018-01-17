package com.disconf.web.service;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.AppEntity;
import com.disconf.web.search.AppSearch;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
public interface IAppService {


    Response insert(AppEntity app);

    Response delete(Long id);

    Response update(AppEntity app);

    AppEntity selectById(Long id);

    List<AppEntity> selectAll();

    SearchResult<AppEntity> selectByParam(AppEntity appEntity);


}
