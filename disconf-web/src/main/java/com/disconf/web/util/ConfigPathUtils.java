package com.disconf.web.util;

import com.disconf.core.common.constants.DisconfConstant;
import com.disconf.web.entity.ConfigEntity;
import com.disconf.web.form.ZkDeployForm;

import java.io.File;

/**
 * @author lzj
 * @date 2018/1/9
 */
public class ConfigPathUtils {

    //underline
    private static final String UNDERLINE = "_";
    // slash
    private static final String SLASH = "/";


    /**
     * /disconf _ groupName _ appName _ version
     *
     * @param configEntity
     * @return
     */
    public static String pathForUpdateNode(ConfigEntity configEntity) {
        StringBuilder path = new StringBuilder();
        path.append(DisconfConstant.DEFAULT_PREFIX).append(File.separator);
//        path.append(configEntity.getGroupName());
//        path.append(UNDERLINE);
        path.append(configEntity.getAppName());
        path.append(UNDERLINE);
        path.append(configEntity.getVersion());
        path.append(UNDERLINE);
        path.append(configEntity.getEnvName());
        path.append(SLASH);
        path.append(DisconfConstant.ZK_PATH_KEY_FILE);
//        if (configEntity.getType() == 0) {
//            path.append(DisconfConstant.ZK_PATH_KEY_FILE);
//        } else if (configEntity.getType() == 1) {
//            path.append(DisconfConstant.ZK_PATH_KEY_ITEM);
//        }
        path.append(SLASH);
        path.append(configEntity.getConfigName());
        return path.toString();
    }

    /**
     * /disconf _ appName _ envName _ version
     *
     * @param zkDeployForm
     * @return
     */
    public static String pathWithAev(ZkDeployForm zkDeployForm) {
        StringBuilder path = new StringBuilder();
        path.append(DisconfConstant.DEFAULT_PREFIX).append(SLASH);
        path.append(zkDeployForm.getAppName());
        path.append(UNDERLINE);
        path.append(zkDeployForm.getEnvName());
        path.append(UNDERLINE);
        path.append(zkDeployForm.getVersion());
        return path.toString();
    }


}
