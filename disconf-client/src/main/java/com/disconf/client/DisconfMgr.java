package com.disconf.client;

import java.util.Collection;
import java.util.List;

import com.disconf.core.common.lock.Locker;
import com.disconf.client.config.ConfigMgr;
import com.disconf.client.config.DisClientConfig;
import com.disconf.client.core.DisconfCoreMgr;
import com.disconf.client.scan.ScanFactory;
import com.disconf.client.scan.ScanMgr;
import com.disconf.client.store.DisconfStoreProcessorFactory;
import com.disconf.client.support.registry.Registry;
import com.disconf.client.support.registry.RegistryFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.disconf.client.core.DisconfCoreFactory;

/**
 * Disconf Client 总入口
 *
 * @author liaoqiqi
 * @version 2014-5-23
 */
public class DisconfMgr implements ApplicationContextAware {

    protected static final Logger LOGGER = LoggerFactory.getLogger(DisconfMgr.class);

    // 本实例不能初始化两次
    private boolean isFirstInit = false;
    private boolean isSecondInit = false;

    // application context
    private ApplicationContext applicationContext;

    // 核心处理器
    private DisconfCoreMgr disconfCoreMgr = null;

    // scan mgr
    private ScanMgr scanMgr = null;

    protected static final DisconfMgr INSTANCE = new DisconfMgr();

    private static final Locker _locker = new Locker();


    public static DisconfMgr getInstance() {
        return INSTANCE;
    }

    private DisconfMgr() {

    }

    /**
     * 第一次扫描，静态扫描 for annotation config
     */
    protected void firstScan(Collection<String> scanPackageList) {
        try (Locker.Lock lock = _locker.lock()) {
            // 该函数不能调用两次
            if (isFirstInit) {
                LOGGER.info("DisConfMgr has been init, ignore........");
                return;
            }

            // 导入配置
            ConfigMgr.init();

            LOGGER.info("******************************* DISCONF START FIRST SCAN *******************************");

            // registry
            Registry registry = RegistryFactory.getSpringRegistry(applicationContext);

            // 扫描器
            scanMgr = ScanFactory.getScanMgr(registry);

            // 第一次扫描并入库 ,DisconfCenterStore
            scanMgr.firstScan(scanPackageList);

            // 获取数据/注入/Watch
            disconfCoreMgr = DisconfCoreFactory.getDisconfCoreMgr(registry);
            disconfCoreMgr.process();

            //
            isFirstInit = true;

            LOGGER.info("******************************* DISCONF END FIRST SCAN *******************************");

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new BeanCreationException(e.getMessage());
        }
    }

    /**
     * 第二次扫描, 动态扫描, for annotation config
     */
    protected void secondScan() {
        try (Locker.Lock lock = _locker.lock()) {
            // 该函数必须第一次运行后才能运行
            if (!isFirstInit) {
                LOGGER.info("should run First Scan before Second Scan.");
                return;
            }

            // 第二次扫描也只能做一次
            if (isSecondInit) {
                LOGGER.info("should not run twice.");
                return;
            }

            LOGGER.info("******************************* DISCONF START SECOND SCAN *******************************");

            // 扫描回调函数
            if (scanMgr != null) {
                scanMgr.secondScan();
            }

            // 注入数据至配置实体中
            // 获取数据/注入/Watch
            if (disconfCoreMgr != null) {
                disconfCoreMgr.inject2DisconfInstance();
            }

            isSecondInit = true;

            // 打印最终的properties文件内容
            // 不开启 则不要打印变量map
            //
            if (DisClientConfig.getInstance().ENABLE_DISCONF) {

                //
                String data = DisconfStoreProcessorFactory.getDisconfStoreFileProcessor()
                        .confToString();
                if (!StringUtils.isEmpty(data)) {
                    LOGGER.info("Conf File Map: {}", data);
                }

                //
                data = DisconfStoreProcessorFactory.getDisconfStoreItemProcessor()
                        .confToString();
                if (!StringUtils.isEmpty(data)) {
                    LOGGER.info("Conf Item Map: {}", data);
                }
            }
            LOGGER.info("******************************* DISCONF END *******************************");

        } catch (Exception e) {
            LOGGER.error("disconf secondScan exception", e);
        }


    }

    /**
     * reloadable config file scan, for xml config
     */
    public void reloadableScan(String fileName) {
        try (Locker.Lock lock = _locker.lock()) {
            if (!isFirstInit) {
                return;
            }

            if (DisClientConfig.getInstance().ENABLE_DISCONF) {

                if (!DisClientConfig.getInstance().getIgnoreDisconfKeySet().contains(fileName)) {

                    if (scanMgr != null) {
                        scanMgr.reloadableScan(fileName);
                    }

                    if (disconfCoreMgr != null) {
                        disconfCoreMgr.processFile(fileName);
                    }
                    LOGGER.debug("disconf reloadable file: {}", fileName);
                }
            }

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }


    }

    /**
     * @Description: 总关闭
     */
    public void close() {
        try (Locker.Lock lock = _locker.lock()) {
            // disconfCoreMgr
            LOGGER.info("******************************* DISCONF CLOSE *******************************");
            if (disconfCoreMgr != null) {
                disconfCoreMgr.release();
            }

            // close, 必须将其设置为False,以便重新更新
            isFirstInit = false;
            isSecondInit = false;

        } catch (Exception e) {
            LOGGER.error("DisConfMgr close exception.", e);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
