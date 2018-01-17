package com.disconf.client.zookeeper;

import com.disconf.client.core.processor.DisconfCoreProcessor;
import com.disconf.client.watch.inner.DisconfSysUpdateCallback;
import com.disconf.core.common.constants.DisConfigTypeEnum;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * @author lzj
 * @date 2018/1/14
 */
public class DisconfClientZkService implements IDisconfClientZkService {

    private static final Logger logger = LoggerFactory.getLogger(DisconfClientZkService.class);

    private CuratorFramework client;

    public DisconfClientZkService(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public void watchWithReload(String monitorPath, DisconfCoreProcessor disconfCoreMgr, String keyName,
                                DisconfSysUpdateCallback disconfSysUpdateCallback) throws Exception {
        NodeCache nodeCache = new NodeCache(client, monitorPath, false);
        nodeCache.getListenable().addListener(new NodeCacheListener() {
            @Override
            public void nodeChanged() throws Exception {
                disconfSysUpdateCallback.reload(disconfCoreMgr, DisConfigTypeEnum.FILE, keyName);
                logger.info("reload ok");
            }
        });
        nodeCache.start();
    }

    @Override
    public void createTempPath(String path, String data) throws Exception {
        client.create().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data.getBytes(Charset.forName("utf-8")));
    }

    @Override
    public void createConsistentPath(String path, String data) throws Exception {
        if (this.checkExists(path)) {
            logger.warn("path exists ,won't create again , path=" + path);
        } else {
            client.create().withMode(CreateMode.PERSISTENT).forPath(path);
        }
    }

    @Override
    public boolean checkExists(String path) throws Exception {
        return client.checkExists().forPath(path) != null;
    }

    @Override
    public void release() throws Exception {
        client.create();
    }

    @Override
    public void addSessionExpiredListener(String path, ConnectionStateListener listener) throws Exception {
        client.getConnectionStateListenable().addListener(listener);
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path);
    }

}
