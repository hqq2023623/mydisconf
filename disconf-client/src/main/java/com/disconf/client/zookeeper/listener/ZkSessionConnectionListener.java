package com.disconf.client.zookeeper.listener;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ZkSessionConnectionListener implements ConnectionStateListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String path;
    private String data;

    public ZkSessionConnectionListener(String path, String data) {
        this.path = path;
        this.data = data;
    }

    @Override
    public void stateChanged(CuratorFramework curatorFramework, ConnectionState connectionState) {
        if (connectionState == ConnectionState.LOST) {
            logger.error("zk session expired");
            while (true) {
                try {
                    if (curatorFramework.getZookeeperClient().blockUntilConnectedOrTimedOut()) {
                        curatorFramework.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(path, data.getBytes("UTF-8"));
                        logger.info("zk reconnect success");
                        break;
                    }
                } catch (InterruptedException e) {
                    break;
                } catch (Exception e) {
                }
            }
        }
    }
}