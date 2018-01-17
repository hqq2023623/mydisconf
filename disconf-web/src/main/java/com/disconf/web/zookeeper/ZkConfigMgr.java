package com.disconf.web.zookeeper;

import com.disconf.web.entity.ConfigEntity;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/9
 */
public interface ZkConfigMgr {

    /**
     * 创建一个zk路径
     *
     * @param configId
     */
    void createConsistentPath(Long configId) throws Exception;


    /**
     * 删除一个节点
     *
     * @param path
     * @throws Exception
     */
    void deleteNode(String path) throws Exception;

    /**
     * 更新一个节点
     *
     * @param configEntity
     */
    void updateNode(ConfigEntity configEntity) throws Exception;


    /**
     * 校验一个路径是否存在
     *
     * @return true if exists
     */
    boolean checkExists(String path) throws Exception;

    /**
     *
     * @param path
     * @return empty String if not exists
     * @throws Exception
     */
    String queryStringValue(String path) throws Exception;

    /**
     * 获取指定path下的节点树
     * @param path
     * @return
     * @throws Exception
     */
    List<String> listChildren(String path) throws Exception;


}
