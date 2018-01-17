package com.disconf.web.service;

import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.search.ConfigSearch;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
public interface IConfigService {

    Response insert(ConfigEntity config) throws Exception;

    Response delete(Long id);

    Response update(ConfigEntity config) throws Exception;

    /**
     * 更新配置value，文本方式
     * @param configId
     * @param value
     * @return
     * @throws Exception
     */
    Response updateByText(Long configId,String value) throws Exception;

    ConfigEntity selectById(Long id);

    List<ConfigEntity> selectAll();

    SearchResult<ConfigEntity> selectByParam(ConfigEntity configEntity);

    /**
     * 上传文件，更新 t_config.value ，并插入一条新数据到 t_config_history
     * @param configId
     * @param file
     * @return
     */
    Response<String> doUpload(Long configId, MultipartFile file) throws Exception;



}
