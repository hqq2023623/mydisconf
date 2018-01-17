package com.disconf.web.shiro;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class RedisShiroSessionDAO extends AbstractSessionDAO {

    private RedisTemplate redisTemplate;

    private ValueOperations operations;

    private String keyPrefix = "shiro_session:";

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForValue();
    }


    private String getKey(Serializable sessionId) {
        return (this.keyPrefix + sessionId);
    }

    @Override
    protected Serializable doCreate(Session session) {

        Serializable sessionId = this.generateSessionId(session);
        this.assignSessionId(session, sessionId);
        operations.getAndSet(getKey(sessionId), session);
        redisTemplate.expire(getKey(sessionId), session.getTimeout(), TimeUnit.MILLISECONDS);
        return getKey(sessionId);
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        return (Session) operations.get(getKey(sessionId));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        operations.getAndSet(getKey(session.getId()), session);
        redisTemplate.expire(getKey(session.getId()), session.getTimeout(), TimeUnit.MILLISECONDS);
    }

    @Override
    public void delete(Session session) {
        redisTemplate.delete(getKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        Set<Session> sessions = new HashSet<Session>();
        Set<byte[]> keys = redisTemplate.keys(getKey("*"));
        if (keys != null && keys.size() > 0) {
            for (byte[] key : keys) {
                Session s = (Session) operations.get(key);
                sessions.add(s);
            }
        }

        return sessions;
    }
}