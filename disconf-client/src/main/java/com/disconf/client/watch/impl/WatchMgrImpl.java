package com.disconf.client.watch.impl;

import com.disconf.client.common.model.DisConfCommonModel;
import com.disconf.client.config.inner.DisClientComConfig;
import com.disconf.client.core.processor.DisconfCoreProcessor;
import com.disconf.client.watch.WatchMgr;
import com.disconf.client.watch.inner.DisconfSysUpdateCallback;
import com.disconf.client.zookeeper.DisconfClientZkService;
import com.disconf.client.zookeeper.DisconfClientZkServiceFactory;
import com.disconf.client.zookeeper.listener.ZkSessionConnectionListener;
import com.disconf.core.common.constants.DisConfigTypeEnum;
import com.disconf.core.common.path.ZooPathMgr;
import com.disconf.core.common.utils.ZooUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Watch 模块的一个实现
 *
 * @author liaoqiqi
 * @version 2014-6-10
 */
public class WatchMgrImpl implements WatchMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(WatchMgrImpl.class);

    private DisconfClientZkService zkService;

    /**
     * zoo prefix
     */
    private String zooUrlPrefix;

    /**
     *
     */
    private boolean debug;

    /**
     * @Description: 获取自己的主备类型
     */
    public void init(String hosts, String zooUrlPrefix, boolean debug) throws Exception {

        this.zooUrlPrefix = zooUrlPrefix;
        this.debug = debug;
        this.zkService = DisconfClientZkServiceFactory.newInstance(hosts);
        zkService.createConsistentPath(zooUrlPrefix, ZooUtils.getIp());

    }

    /**
     * 新建监控
     *
     * @throws Exception
     */
    private String makeMonitorPath(DisConfCommonModel disConfCommonModel,
                                   String key, String value) throws Exception {
        /*
        * 应用根目录，/disconf
        */
        String clientRootZooPath = ZooPathMgr.getZooBaseUrl(zooUrlPrefix, disConfCommonModel.getApp(),
                disConfCommonModel.getEnv(),
                disConfCommonModel.getVersion());
        zkService.createConsistentPath(clientRootZooPath, ZooUtils.getIp());

        // 新建Zoo Store目录
        String clientDisconfFileZooPath = ZooPathMgr.getFileZooPath(clientRootZooPath);
        zkService.createConsistentPath(clientDisconfFileZooPath, ZooUtils.getIp());
        // 监控路径
        String monitorPath = ZooPathMgr.joinPath(clientDisconfFileZooPath, key);

        // 先新建路径
        zkService.createConsistentPath(monitorPath, "");

        // 新建一个代表自己的临时结点

        makeTempChildPath(monitorPath, value);


        return monitorPath;
    }

    /**
     * 在指定路径下创建一个临时结点
     */
    private void makeTempChildPath(String path, String data) {

        String finerPrint = DisClientComConfig.getInstance().getInstanceFingerprint();

        String mainTypeFullStr = path + "/" + finerPrint;
        try {
            ZkSessionConnectionListener listener = new ZkSessionConnectionListener(mainTypeFullStr, data);
            zkService.addSessionExpiredListener(mainTypeFullStr, listener);
        } catch (Exception e) {
            LOGGER.error("cannot create: " + mainTypeFullStr + "\t" + e.toString());
        }
    }

    /**
     * 监控路径,监控前会事先创建路径,并且会新建一个自己的Temp子结点
     */
    public void watchPath(DisconfCoreProcessor disconfCoreMgr, DisConfCommonModel disConfCommonModel, String keyName,
                          DisConfigTypeEnum disConfigTypeEnum, String value) throws Exception {

        // 新建
        String monitorPath = this.makeMonitorPath(disConfCommonModel, keyName, value);
        zkService.watchWithReload(monitorPath, disconfCoreMgr, keyName, new DisconfSysUpdateCallback());

    }

    @Override
    public void release() {
        try {
            zkService.release();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

}
