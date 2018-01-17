package com.disconf.web.service.impl;

import com.disconf.web.common.ConfigTypeEnum;
import com.disconf.core.common.model.Response;
import com.disconf.web.common.SearchResult;
import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.entity.ConfigEntityHistory;
import com.disconf.web.entity.UserEntity;
import com.disconf.web.mapper.ConfigEntityHistoryMapper;
import com.disconf.web.mapper.ConfigEntityMapper;
import com.disconf.web.search.ConfigSearch;
import com.disconf.web.service.IConfigService;
import com.disconf.web.util.ConfigPathUtils;
import com.disconf.web.zookeeper.ZkConfigMgr;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/5
 */
@Service
public class ConfigServiceImpl implements IConfigService {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServiceImpl.class);

    @Autowired
    private ConfigEntityMapper configEntityMapper;

    @Autowired
    private ConfigEntityHistoryMapper historyMapper;

    @Autowired
    private ZkConfigMgr zkConfigMgr;

    @Override
    public List<ConfigEntity> selectAll() {
        return configEntityMapper.selectAll();
    }

    @Override
    public Response insert(ConfigEntity config) throws Exception {
        List<ConfigEntity> list = configEntityMapper.search(ConfigSearch.fromConfigEntity(config));
        if (!list.isEmpty()) {
            return Response.fail("已经存在！");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        config.setCreator(currentUser.getUserName());
        config.setUpdater(currentUser.getUserName());

        configEntityMapper.insert(config);

        //新增zk
//        if(!zkConfigMgr.checkExists(ConfigPathUtils.pathForUpdateNode(config))) {
//            zkConfigMgr.createConsistentPath(config.getId());
//        }

        return Response.success();
    }

    @Override
    public Response delete(Long id) {
        configEntityMapper.delete(id);
        return Response.success();
    }

    @Override
    public SearchResult<ConfigEntity> selectByParam(ConfigEntity configEntity) {
        List<ConfigEntity> rows = configEntityMapper.selectByParam(configEntity);
        int total = configEntityMapper.selectCountByParam(configEntity);
        return new SearchResult<>(total, rows);
    }

    @Override
    public ConfigEntity selectById(Long id) {
        return configEntityMapper.selectById(id);
    }

    @Override
    public Response update(ConfigEntity config) throws Exception {
        Long configId = config.getId();
        ConfigEntity dbEntity = configEntityMapper.selectById(configId);
        if (dbEntity == null) {
            return Response.fail("更新失败");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        String updater = currentUser.getUserName();
        config.setUpdater(updater);
        configEntityMapper.updateWithoutValue(config);
        logger.info("update config success, config=" + config);
        return Response.success("更新成功");
    }

    @Override
    public Response updateByText(Long configId, String newValue) throws Exception {
        ConfigEntity dbEntity = configEntityMapper.selectById(configId);
        if (dbEntity == null) {
            return Response.fail("更新失败");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        String updater = currentUser.getUserName();
        String oldValue = dbEntity.getValue();
        Byte oldType = dbEntity.getType();
        dbEntity.setUpdater(updater);
        dbEntity.setValue(newValue);
        dbEntity.setType(ConfigTypeEnum.FILE.getType());
        dbEntity.setTypeDesc(ConfigTypeEnum.TEXT.getTypeDesc());
        configEntityMapper.update(dbEntity);

        //插入记录到 t_config_history
        dbEntity.setType(oldType);
        ConfigEntityHistory history = this.historyFromConfig(dbEntity, oldValue, newValue);
        historyMapper.insert(history);
        //更新zk，触发更新事件
        zkConfigMgr.updateNode(dbEntity);
        logger.info("update config by text success, configId=" + configId + " newValue=" + newValue);
        return Response.success("更新成功");
    }

    @Override
    public Response<String> doUpload(Long configId, MultipartFile file) throws Exception {
        if (configId == null || configId == 0 || file == null) {
            return Response.fail("上传失败");
        }
        ConfigEntity dbEntity = configEntityMapper.selectById(configId);
        if (dbEntity == null) {
            return Response.fail("上传失败");
        }
        UserEntity currentUser = (UserEntity) SecurityUtils.getSubject().getPrincipal();
        String updater = currentUser.getUserName();
        String newValue = new String(file.getBytes(), "UTF-8");
        String oldValue = dbEntity.getValue();
        Byte oldType = dbEntity.getType();
        dbEntity.setId(configId);
        dbEntity.setValue(newValue);
        dbEntity.setType(Byte.valueOf("0"));
        dbEntity.setTypeDesc(ConfigTypeEnum.FILE.getTypeDesc());
        dbEntity.setUpdater(updater);
        configEntityMapper.update(dbEntity);

        //插入记录到 t_config_history
        dbEntity.setType(oldType);
        ConfigEntityHistory history = this.historyFromConfig(dbEntity, oldValue, newValue);
        historyMapper.insert(history);
        //更新zk，触发更新事件
        zkConfigMgr.updateNode(dbEntity);
        logger.info("upload config success, configId=" + configId + " newValue=" + newValue + ", oldValue=" + oldValue);
        return Response.success("上传成功");
    }

    //复制configEntity到history
    private ConfigEntityHistory historyFromConfig(ConfigEntity configEntity, String oldValue, String newValue) {
        ConfigEntityHistory history = new ConfigEntityHistory();
        history.setConfigId(configEntity.getId());
        history.setOldValue(oldValue);
        history.setNewValue(newValue);
        history.setAppId(configEntity.getAppId());
        history.setAppName(configEntity.getAppName());
        history.setConfigId(configEntity.getId());
        history.setConfigName(configEntity.getConfigName());
        history.setGroupId(configEntity.getGroupId());
        history.setGroupName(configEntity.getGroupName());
        history.setVersion(configEntity.getVersion());
        history.setEnable(configEntity.getEnable());
        history.setType(configEntity.getType());
        history.setTypeDesc(ConfigTypeEnum.getEnum(configEntity.getType()).getTypeDesc());
        history.setUpdater(configEntity.getUpdater());
        history.setCreator(configEntity.getUpdater());
        return history;
    }

}
