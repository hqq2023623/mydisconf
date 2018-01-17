package com.disconf.web.listener;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class ShiroSessionListener implements SessionListener {

    private static final Logger logger = LoggerFactory.getLogger(ShiroSessionListener.class);

    /**
     * 会话创建时触发
     *
     * @param session
     */
    @Override
    public void onStart(Session session) {
        logger.info("[" + session.getId() + "] session onStart .");
    }

    /**
     * 退出/会话过期时触发
     *
     * @param session
     */
    @Override
    public void onStop(Session session) {
        logger.info("[" + session.getId() + "] session onStop .");
    }

    /**
     * 会话过期时触发
     *
     * @param session
     */
    @Override
    public void onExpiration(Session session) {
        logger.info("[" + session.getId() + "] session onExpiration.");
    }

}
