package com.disconf.core.common.restful;

import com.disconf.core.common.restful.impl.RestfulMgrImpl;
import com.disconf.core.common.restful.retry.impl.RetryStrategyRoundBin;

/**
 * @author liaoqiqi
 * @version 2014-8-4
 */
public class RestfulFactory {

    /**
     * 获取一个默认的抓取器
     *
     * @return
     *
     * @throws Exception
     */
    public static RestfulMgr getRestfulMgrNomal() throws Exception {

        RestfulMgr restfulMgr = new RestfulMgrImpl(new RetryStrategyRoundBin());

        return restfulMgr;
    }
}
