package com.disconf.client.scan.inner.statically.strategy.impl;

import java.util.Collection;
import java.util.List;

import com.disconf.client.scan.inner.statically.model.ScanStaticModel;
import com.disconf.client.scan.inner.statically.strategy.ScanStaticStrategy;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;

/**
 * Created by knightliao on 15/1/23.
 */
public class SpringScanStatic implements ScanStaticStrategy {

    private ApplicationContext context;
    private DefaultListableBeanFactory factory;

    /**
     * 构造函数
     */
    public SpringScanStatic(ApplicationContext context) {
        this.context = context;
    }

    @Override
    public ScanStaticModel scan(Collection<String> packNameList) {

        factory = (DefaultListableBeanFactory) context.getAutowireCapableBeanFactory();
        return null;
    }

}
