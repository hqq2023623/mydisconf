package com.disconf.client.scan.inner.statically.strategy;

import java.util.Collection;
import java.util.List;

import com.disconf.client.scan.inner.statically.model.ScanStaticModel;

/**
 * 扫描静态注解，并且进行分析整合数据
 *
 * @author liaoqiqi
 * @version 2014-6-6
 */
public interface ScanStaticStrategy {

    ScanStaticModel scan(Collection<String> packNameList);
}
