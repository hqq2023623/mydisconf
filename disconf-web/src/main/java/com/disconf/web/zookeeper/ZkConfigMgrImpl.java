package com.disconf.web.zookeeper;

import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.service.IConfigService;
import com.disconf.web.util.ConfigPathUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.text.Collator;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author lzj
 * @date 2018/1/9
 */
@Service
public class ZkConfigMgrImpl implements ZkConfigMgr {


    private static final Logger logger = LoggerFactory.getLogger(ZkConfigMgrImpl.class);

    @Autowired
    private CuratorFramework curatorFramework;

    @Autowired
    private IConfigService configService;

    @Override
    public void createConsistentPath(Long configId) throws Exception {
        ConfigEntity configEntity = this.checkConfig(configId);
        String path = ConfigPathUtils.pathForUpdateNode(configEntity);
        if (this.checkExists(path)) {
            logger.warn("path exists ,won't create again , path=" + path);
        } else {
            curatorFramework.create().withMode(CreateMode.PERSISTENT).forPath(path);
            if (StringUtils.isNotBlank(configEntity.getValue())) {
                this.updateNode(configEntity);
            }
        }
    }

    @Override
    public void deleteNode(String path) throws Exception {
        if (this.checkExists(path)) {
            curatorFramework.delete().forPath(path);
        }
    }

    @Override
    public void updateNode(ConfigEntity configEntity) throws Exception {
        String path = ConfigPathUtils.pathForUpdateNode(configEntity);
        curatorFramework.setData().forPath(path, configEntity.getValue().getBytes(Charset.forName("utf-8")));
    }

    @Override
    public boolean checkExists(String path) throws Exception {
        return curatorFramework.checkExists().forPath(path) != null;
    }

    @Override
    public String queryStringValue(String path) throws Exception {
        if (this.checkExists(path)) {
            return new String(curatorFramework.getData().forPath(path), Charset.forName("utf-8"));
        }
        return "";
    }

    private ConfigEntity checkConfig(Long configId) throws Exception {
        if (configId == null || configId <= 0) {
            throw new IllegalArgumentException("configId is error , configId=" + configId);
        }
        ConfigEntity configEntity = configService.selectById(configId);
        if (configEntity == null) {
            throw new IllegalArgumentException("no config with id " + configId);
        }
        if (StringUtils.isBlank(configEntity.getGroupName())) {
            throw new IllegalArgumentException("groupName is blank");
        }
        if (StringUtils.isBlank(configEntity.getAppName())) {
            throw new IllegalArgumentException("appName is blank");
        }
        if (StringUtils.isBlank(configEntity.getVersion())) {
            throw new IllegalArgumentException("versionName is blank");
        }
        return configEntity;
    }

    @Override
    public List<String> listChildren(String path) throws Exception {
        List<String> childrenList = new LinkedList<>();
        if (!this.checkExists(path)) {
            return childrenList;
        }
        this.doListChildren(path, path, childrenList);
        return childrenList;
    }

    private void doListChildren(String groupName, String displayName, List<String> retList) throws Exception {
        StringBuffer sb = new StringBuffer();

        int pathLength = StringUtils.countMatches(groupName, "/");
        for (int i = 0; i < pathLength - 2; ++i) {
            sb.append("\t");
        }

        List<String> children = curatorFramework.getChildren().forPath(groupName);

        if (!"/".equals(groupName)) {

            sb.append("|----" + displayName);
            String data = this.queryStringValue(groupName);
            if (StringUtils.isNotBlank(data)) {
                sb.append("\t" + data);
            }
        } else {
            sb.append(groupName);
        }
        retList.add(sb.toString());

        //
        Collections.sort(children, Collator.getInstance(java.util.Locale.CHINA));
        for (String child : children) {

            String nextName = groupName + "/" + child;
            ;

            String node = StringUtils.substringAfterLast(nextName, "/");

            doListChildren(groupName + "/" + child, node, retList);
        }

    }


}
