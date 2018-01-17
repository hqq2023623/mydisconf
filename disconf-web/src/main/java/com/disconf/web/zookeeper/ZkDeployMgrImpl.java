package com.disconf.web.zookeeper;

import com.disconf.core.common.model.Response;
import com.disconf.web.form.ZkDeployForm;
import com.disconf.web.util.ConfigPathUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/10
 */
@Service
public class ZkDeployMgrImpl implements ZkDeployMgr {

    private static final Logger logger = LoggerFactory.getLogger(ZkDeployMgrImpl.class);

    @Autowired
    private ZkConfigMgr zkConfigMgr;

    @Override
    public Response getDeployInfo(ZkDeployForm zkDeployForm) {
        if (zkDeployForm == null) {
            return Response.fail("zkDeployForm is null");
        }
        if (StringUtils.isBlank(zkDeployForm.getAppName())) {
            return Response.fail("appName is blank");
        }
        if (StringUtils.isBlank(zkDeployForm.getEnvName())) {
            return Response.fail("envName is blank");
        }
        if (StringUtils.isBlank(zkDeployForm.getVersion())) {
            return Response.fail("version is blank");
        }
        logger.info("getZkDeployInfo" + zkDeployForm);

        String path = ConfigPathUtils.pathWithAev(zkDeployForm);

        List<String> hostInfoList;
        try {
            hostInfoList = zkConfigMgr.listChildren(path);
        } catch (Exception e) {
            logger.error("getDeployInfo exception,zkDeployForm=" + zkDeployForm, e);
            return Response.fail("getDeployInfo fail");
        }
        String joinString = StringUtils.join(hostInfoList, ',');
        return Response.success(joinString);


    }


}
