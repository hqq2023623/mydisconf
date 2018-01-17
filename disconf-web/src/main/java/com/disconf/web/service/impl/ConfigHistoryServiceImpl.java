package com.disconf.web.service.impl;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.ConfigEntityHistory;
import com.disconf.web.mapper.ConfigEntityHistoryMapper;
import com.disconf.web.search.AppSearch;
import com.disconf.web.service.IAppService;
import com.disconf.web.service.IConfigHistoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Service
public class ConfigHistoryServiceImpl implements IConfigHistoryService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigHistoryServiceImpl.class);

    @Autowired
    private ConfigEntityHistoryMapper configHistoryMapper;

    @Override
    public List<ConfigEntityHistory> selectAll() {
        return configHistoryMapper.selectAll();
    }

    @Override
    public Response insert(ConfigEntityHistory configEntityHistory) {
        configHistoryMapper.insert(configEntityHistory);
        return Response.success();
    }

    @Override
    public Response delete(Long id) {
        configHistoryMapper.delete(id);
        return Response.success();
    }

    @Override
    public SearchResult<ConfigEntityHistory> selectByParam(ConfigEntityHistory ConfigEntityHistory) {
        List<ConfigEntityHistory> rows = configHistoryMapper.selectByParam(ConfigEntityHistory);
        int total = configHistoryMapper.selectCountByParam(ConfigEntityHistory);
        return new SearchResult<>(total, rows);
    }

    @Override
    public ConfigEntityHistory selectById(Long id) {
        return configHistoryMapper.selectById(id);
    }

    @Override
    public Response update(ConfigEntityHistory app) {
        configHistoryMapper.update(app);
        return Response.success();
    }
}
