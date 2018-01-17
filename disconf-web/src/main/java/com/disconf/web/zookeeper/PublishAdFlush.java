//package com.disconf.web.zookeeper;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.api.CuratorWatcher;
//import org.apache.zookeeper.CreateMode;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Date;
//
//public class PublishAdFlush extends ZKBase {
//    private static Logger logger = LoggerFactory
//            .getLogger(PublishAdFlush.class);
//
//    //要监听的路径
//    private String path;
//
//    public PublishAdFlush(CuratorFramework curatorFramework, String path) {
//        super(curatorFramework);
//        this.path = path;
//    }
//
//    /*
//    *具体监听的方法
//    */
//    private String watcherPath(String path, CuratorWatcher watcher)
//            throws Exception {
//        Stat stat = curatorFramework.checkExists().forPath(path);
//        String date = new Date().toString();
//        if (stat == null) { // 没有节点创建节点
//            curatorFramework.create().creatingParentsIfNeeded()
//                    .withMode(CreateMode.EPHEMERAL)
//                    .forPath(path, date.getBytes());
//        }
//        byte[] buffer = curatorFramework.getData().usingWatcher(watcher)
//                .forPath(path);
//        System.out.println(new String(buffer));
//        return new String(buffer);
//    }
//
//    /*
//    *获取所监听路径的数据
//    */
//    private String readPath(String path) throws Exception {
//        byte[] buffer = curatorFramework.getData().forPath(path);
//        return new String(buffer);
//    }
//
//    /**
//     * 跟新节点数据
//     */
//    public void updateData() throws Exception {
//        String date = new Date().toString();
//        Stat stat = curatorFramework.checkExists().forPath(this.path);
//        if (stat == null) { // 没有节点创建节点
//            curatorFramework.create().creatingParentsIfNeeded()
//                    .withMode(CreateMode.EPHEMERAL)
//                    .forPath(this.path, date.getBytes());
//            System.out.println(date + ":数据更新,创建新节点！");
//        } else {
//            curatorFramework.setData().forPath(this.path, date.getBytes());
//            System.out.println(date + ":数据更新，不用创建新节点！");
//        }
//    }
//
//    /**
//     * 实现监听器和监听事件后的操作
//     */
//    private CuratorWatcher pathWatcher = new CuratorWatcher() {
//        public void process(WatchedEvent event) throws Exception {
//            String date = new Date().toString();
//            // 当数据变化后，重新获取数据信息
//            if (event.getType() == Watcher.Event.EventType.NodeDataChanged) {
//                // 获取更改后的数据，进行相应的业务处理
//                //继续调用监听
//                curatorFramework.getData().usingWatcher(pathWatcher)
//                .forPath(path);
//                System.out.println("处理数据");
//                String value = readPath(path);
//                String uuid = "uuid";
//                logger.info("{},数据开始更新", uuid);
//                AdShowService adShowService = SpringContextHolder
//                        .getBean("adShowService");
//                boolean flag = adShowService.flushData(CommonConstants.COMMON_FLUSH);
//                logger.info("{},数据更新完成结果为:{}", uuid, flag);
//            } else if (event.getType() == Watcher.Event.EventType.NodeDeleted) {
//                Stat stat = curatorFramework.checkExists().forPath(path);
//                if (stat == null) { // 没有节点创建节点
//                    curatorFramework.create().creatingParentsIfNeeded()
//                            .withMode(CreateMode.EPHEMERAL)
//                            .forPath(path, date.getBytes());
//                }
//                //继续调用监听
//                curatorFramework.getData().usingWatcher(pathWatcher)
//                .forPath(path);
//
//            } else if (event.getType() == Watcher.Event.EventType.NodeCreated) {
//                //继续调用监听
//                curatorFramework.getData().usingWatcher(pathWatcher)
//                .forPath(path);
//            }
//        }
//    };
//}