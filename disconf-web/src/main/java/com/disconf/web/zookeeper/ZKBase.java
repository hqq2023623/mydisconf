package com.disconf.web.zookeeper;

import org.apache.curator.framework.CuratorFramework;

/**
 *
 * @author lzj
 * @date 2018/1/9
 */
public abstract class ZKBase {

    protected CuratorFramework curatorFramework;

    public ZKBase(CuratorFramework curatorFramework) {
        this.curatorFramework = curatorFramework;
    }

    public void init() {
        try {
            call();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void call() throws Exception;
}