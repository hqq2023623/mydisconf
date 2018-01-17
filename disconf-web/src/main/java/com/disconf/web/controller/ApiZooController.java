package com.disconf.web.controller;

import com.disconf.web.common.ApplicationProperties;
import com.disconf.core.common.constants.DisconfConstant;
import com.disconf.core.common.model.Response;
import com.disconf.web.form.ZkDeployForm;
import com.disconf.web.zookeeper.ZkDeployMgr;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lzj
 * @date 2018/1/10
 */
@RestController
@RequestMapping("/api/zoo")
public class ApiZooController {

    private static final Logger LOG = LoggerFactory.getLogger(ApiZooController.class);

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ZkDeployMgr zkDeployMgr;

    /**
     * 获取Zookeeper地址
     *
     * @return
     */
    @RequestMapping(value = "/hosts", method = RequestMethod.GET)
    public Response getHosts() {
        return Response.success(applicationProperties.getZkHosts());
    }

    /**
     * 获取ZK prefix
     *
     * @return
     */
    @RequestMapping(value = "/prefix", method = RequestMethod.GET)
    public Response getPrefixUrl() {
        return Response.success(DisconfConstant.DEFAULT_PREFIX);
    }

    /**
     * 获取ZK 部署情况
     *
     * @param zkDeployForm
     *
     * @return
     */
    @RequestMapping(value = "/zk/deploy", method = RequestMethod.GET)
    public Object getZkDeployInfo(ZkDeployForm zkDeployForm) {
        return zkDeployMgr.getDeployInfo(zkDeployForm);
    }



}
