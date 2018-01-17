package com.disconf.web.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.leader.LeaderSelector;
import org.apache.curator.framework.recipes.leader.LeaderSelectorListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Leader调度
 *
 * @author lzj
 * @date 2018/1/9
 */
public class LeaderDispatch extends ZKBase {

    private static Logger logger = LoggerFactory
            .getLogger(LeaderDispatch.class);

    private boolean leader = false;

    public boolean isLeader() {
        return leader;
    }

    private String path;

    public LeaderDispatch(CuratorFramework curatorFramework, String path) {
        super(curatorFramework);
        this.path = path;
    }

    /**
     * 异步监控，存在初始化过快，无法确定Leader，如必须在初始化时Leader执行，就在方法中直接调用
     */
    public void leaderSelector() {
        LeaderSelector selector = new LeaderSelector(curatorFramework, path,
                new LeaderSelectorListenerAdapter() {
                    @Override
                    public void takeLeadership(CuratorFramework curatorFramework)
                            throws Exception {
                        logger.info(" leader 选择成功！！！成为Master");
                        leader = true;
                        while (true) {
                            Thread.sleep(Integer.MAX_VALUE);
                        }
                    }
                });
        selector.autoRequeue();
        selector.start();
    }

    @Override
    public void call() {
        leader = false;
        leaderSelector();
    }

}