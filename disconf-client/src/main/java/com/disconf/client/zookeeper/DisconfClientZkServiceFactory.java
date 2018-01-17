package com.disconf.client.zookeeper;

import com.disconf.core.common.constants.DisconfConstant;
import com.disconf.core.common.lock.Locker;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzj
 * @date 2018/1/14
 */
public class DisconfClientZkServiceFactory {

    private static final Logger logger = LoggerFactory.getLogger(DisconfClientZkServiceFactory.class);

    private static DisconfClientZkService INSTANCE;

    public static DisconfClientZkService newInstance(String connString) {
        Locker locker = new Locker();
        try (Locker.Lock lock = locker.lock()) {
            if (INSTANCE == null) {
                CuratorFramework client = CuratorFrameworkFactory.builder().
                        connectionTimeoutMs(50000).
                        connectString(connString).
                        namespace(DisconfConstant.DEFAULT_PREFIX).
                        retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000))
                        .build();
                client.start();
                INSTANCE = new DisconfClientZkService(client);
            }
        } catch (Exception e) {
            logger.error("newInstance error,connString=" + connString, e);
        }
        return INSTANCE;
    }


}
