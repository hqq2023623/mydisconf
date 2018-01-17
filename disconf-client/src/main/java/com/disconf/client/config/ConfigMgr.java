package com.disconf.client.config;

import com.disconf.client.config.inner.DisClientComConfig;
import com.disconf.core.common.lock.Locker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.disconf.client.config.inner.DisInnerConfigHelper;

/**
 * 配置模块
 *
 * @author liaoqiqi
 * @version 2014-6-6
 */
public class ConfigMgr {

    protected static final Logger LOGGER = LoggerFactory.getLogger(ConfigMgr.class);

    private static boolean isInit = false;

    private static final Locker _locker = new Locker();

    /**
     * 初始化配置
     *
     * @throws Exception
     */
    public static void init() throws Exception {
        try (Locker.Lock lock = _locker.lock()) {
            LOGGER.info("--------------- LOAD CONFIG START ---------------");

            //机器指纹 host_port_randomUUID
            LOGGER.info("Finer print: " + DisClientComConfig.getInstance().getInstanceFingerprint());

            // 导入系统配置
            DisClientSysConfig.getInstance().loadConfig(null);

            // 校验 系统配置
            DisInnerConfigHelper.verifySysConfig();

            // 导入用户配置
            DisClientConfig.getInstance().loadConfig(null);

            // 校验 用户配置
            DisInnerConfigHelper.verifyUserConfig();

            isInit = true;

            LOGGER.info("--------------- LOAD CONFIG END ---------------");
        }

    }

    /**
     */
    public synchronized static boolean isInit() {
        return isInit;
    }

    /**
     */
    public static void main(String[] args) {

        try {

            ConfigMgr.init();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
