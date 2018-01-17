package com.disconf.client.zookeeper;

import com.disconf.client.common.model.DisConfCommonModel;
import com.disconf.client.core.processor.DisconfCoreProcessor;
import com.disconf.client.watch.inner.DisconfSysUpdateCallback;
import com.disconf.core.common.constants.DisConfigTypeEnum;
import org.apache.curator.framework.state.ConnectionStateListener;

/**
 * @author lzj
 * @date 2018/1/14
 */
public interface IDisconfClientZkService {

    /**
     * 监听指定path
     */
    void watchWithReload(String monitorPath, DisconfCoreProcessor disconfCoreMgr, String keyName,
                         DisconfSysUpdateCallback disconfSysUpdateCallback) throws Exception;

    /**
     * 创建一个临时节点
     *
     * @param path
     */
    void createTempPath(String path, String data) throws Exception;

    /**
     * 创建一个zk路径
     *
     * @param path
     * @param data
     */
    void createConsistentPath(String path, String data) throws Exception;

    /**
     * 校验一个路径是否存在
     *
     * @return true if exists
     */
    boolean checkExists(String path) throws Exception;

    void release() throws Exception;

    void addSessionExpiredListener(String path, ConnectionStateListener listener) throws Exception;


}
