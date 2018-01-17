//package com.disconf.client.watch.inner;
//
//import com.disconf.client.core.processor.DisconfCoreProcessor;
//import org.apache.zookeeper.KeeperException;
//import org.apache.zookeeper.WatchedEvent;
//import org.apache.zookeeper.Watcher;
//import org.apache.zookeeper.Watcher.Event.EventType;
//import org.apache.zookeeper.Watcher.Event.KeeperState;
//import org.apache.zookeeper.data.Stat;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import com.disconf.core.common.constants.DisConfigTypeEnum;
//
///**
// * 结点监控器
// *
// * @author liaoqiqi
// * @version 2014-6-16
// */
//public class NodeWatcher implements Watcher {
//
//    protected static final Logger LOGGER = LoggerFactory.getLogger(NodeWatcher.class);
//
//    private String monitorPath = "";
//    private String keyName = "";
//    private DisConfigTypeEnum disConfigTypeEnum;
//    private DisconfSysUpdateCallback disconfSysUpdateCallback;
//    private boolean debug;
//
//    private DisconfCoreProcessor disconfCoreMgr;
//
//    /**
//     */
//    public NodeWatcher(DisconfCoreProcessor disconfCoreMgr, String monitorPath, String keyName,
//                       DisConfigTypeEnum disConfigTypeEnum, DisconfSysUpdateCallback disconfSysUpdateCallback,
//                       boolean debug) {
//
//        super();
//        this.debug = debug;
//        this.disconfCoreMgr = disconfCoreMgr;
//        this.monitorPath = monitorPath;
//        this.keyName = keyName;
//        this.disConfigTypeEnum = disConfigTypeEnum;
//        this.disconfSysUpdateCallback = disconfSysUpdateCallback;
//    }
//
//    /**
//     * 回调函数
//     */
//    @Override
//    public void process(WatchedEvent event) {
//        //
//        // 结点更新时
//        //
//        if (event.getType() == EventType.NodeDataChanged) {
//
//            try {
//
//                LOGGER.info("============GOT UPDATE EVENT " + event.toString() + ": (" + monitorPath + "," + keyName
//                        + "," + disConfigTypeEnum.getModelName() + ")======================");
//
//                // 调用回调函数, 回调函数里会重新进行监控
//                callback();
//
//            } catch (Exception e) {
//
//                LOGGER.error("monitor node exception. " + monitorPath, e);
//            }
//        }
//
//    }
//
//    /**
//     *
//     */
//    private void callback() {
//
//        try {
//
//            // 调用回调函数, 回调函数里会重新进行监控
//            try {
//
//            } catch (Exception e) {
//                LOGGER.error(e.toString(), e);
//            }
//
//        } catch (Exception e) {
//
//            LOGGER.error("monitor node exception. " + monitorPath, e);
//        }
//    }
//}
