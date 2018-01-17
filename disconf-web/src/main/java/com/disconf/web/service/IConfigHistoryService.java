package com.disconf.web.service;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.AppEntity;
import com.disconf.web.entity.ConfigEntityHistory;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
public interface IConfigHistoryService {

    Response insert(ConfigEntityHistory app);

    Response delete(Long id);

    Response update(ConfigEntityHistory app);

    ConfigEntityHistory selectById(Long id);

    List<ConfigEntityHistory> selectAll();

    SearchResult<ConfigEntityHistory> selectByParam(ConfigEntityHistory appEntity);


}
