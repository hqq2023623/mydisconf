package com.disconf.web.zookeeper;

import com.disconf.core.common.model.Response;
import com.disconf.web.form.ZkDeployForm;

/**
 * @author lzj
 * @date 2018/1/10
 */
public interface ZkDeployMgr {

    Response getDeployInfo(ZkDeployForm zkDeployForm);


}
