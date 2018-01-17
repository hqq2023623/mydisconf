package com.disconf.web.service.impl;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.mapper.AppEntityMapper;
import com.disconf.web.entity.AppEntity;
import com.disconf.web.search.AppSearch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.disconf.web.service.IAppService;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Service
public class AppServiceImpl implements IAppService {

    private static final Logger logger = LoggerFactory.getLogger(AppServiceImpl.class);

    @Autowired
    private AppEntityMapper appEntityMapper;

    @Override
    public List<AppEntity> selectAll() {
        return appEntityMapper.selectAll();
    }

    @Override
    public Response insert(AppEntity app) {
        AppSearch appSearch = new AppSearch();
        appSearch.setName(app.getName());
        appSearch.setGroupName(app.getGroupName());
        List<AppEntity> dbApp = appEntityMapper.search(appSearch);
        if (!dbApp.isEmpty()) {
            return Response.result(Response.Status.FAILURE.getCode(), "Group + APP 已存在！");
        }
        appEntityMapper.insert(app);
        return Response.success();
    }

    @Override
    public Response delete(Long id) {
        appEntityMapper.delete(id);
        return Response.success();
    }

    @Override
    public SearchResult<AppEntity> selectByParam(AppEntity appEntity) {
        List<AppEntity> rows = appEntityMapper.selectByParam(appEntity);
        int total = appEntityMapper.selectCountByParam(appEntity);
        return new SearchResult<>(total, rows);
    }

    @Override
    public AppEntity selectById(Long id) {
        return appEntityMapper.selectById(id);
    }

    @Override
    public Response update(AppEntity app) {
        appEntityMapper.update(app);
        return Response.success();
    }
}
