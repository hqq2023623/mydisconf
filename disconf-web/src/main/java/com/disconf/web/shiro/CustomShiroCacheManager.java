package com.disconf.web.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.util.Destroyable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 *
 *
 * @author lzj
 * @date 2018/1/4
 */
public class CustomShiroCacheManager<K, V> implements CacheManager, Destroyable {

    private static final Logger logger = LoggerFactory.getLogger(CustomShiroCacheManager.class);

    private RedisTemplate<K, V> redisTemplate;

    public void setRedisTemplate(RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new JedisShiroCache(name, redisTemplate);
    }

    @Override
    public void destroy() throws Exception {
        Boolean isClose = redisTemplate.getConnectionFactory().getConnection().isClosed();
        if (!isClose) {
            redisTemplate.getConnectionFactory().getConnection().close();
        }
        logger.warn("CustomShiroCacheManager is destroying-------------");
    }


}
