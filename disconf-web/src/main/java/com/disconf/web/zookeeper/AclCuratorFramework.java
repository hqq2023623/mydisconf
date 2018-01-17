//package com.disconf.web.zookeeper;
//
//import org.apache.curator.framework.CuratorFramework;
//import org.apache.curator.framework.CuratorFrameworkFactory;
//import org.apache.curator.framework.api.ACLProvider;
//import org.apache.curator.retry.RetryNTimes;
//import org.apache.zookeeper.ZooDefs;
//import org.apache.zookeeper.data.ACL;
//import org.apache.zookeeper.data.Id;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author lzj
// * @date 2018/1/9
// */
//public class AclCuratorFramework {
//
//    //    <bean id="curatorFramework" class="com.disconf.web.zookeeper.AclCuratorFramework" factory-method="newClient" >
//    public CuratorFramework newClient() {
//        //默认创建的根节点是没有做权限控制的--需要自己手动加权限???----
//        ACLProvider aclProvider = new ACLProvider() {
//            private List<ACL> acl;
//
//            @Override
//            public List<ACL> getDefaultAcl() {
//                if (acl == null) {
//                    ArrayList<ACL> acl = ZooDefs.Ids.CREATOR_ALL_ACL;
//                    acl.clear();
//                    acl.add(new ACL(ZooDefs.Perms.ALL, new Id("auth", "admin:admin")));
//                    this.acl = acl;
//                }
//                return acl;
//            }
//
//            @Override
//            public List<ACL> getAclForPath(String path) {
//                return acl;
//            }
//        };
//        String scheme = "digest";
//        byte[] auth = "admin:admin".getBytes();
//        int connectionTimeoutMs = 5000;
//        String connectString = "localhost:2181";
//        CuratorFramework client = CuratorFrameworkFactory.builder().aclProvider(aclProvider).
//                authorization(scheme, auth).
//                connectionTimeoutMs(connectionTimeoutMs).
//                connectString(connectString).
//                namespace("lzj").
//                retryPolicy(new RetryNTimes(Integer.MAX_VALUE, 1000)).build();
//        client.start();
//        return client;
//    }
//
//
//
//
//
//
//
//
//
//}
