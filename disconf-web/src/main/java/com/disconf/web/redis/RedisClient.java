package com.disconf.web.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lzj
 * @date 2018/1/4
 */
@Component
public class RedisClient<K, V> implements CacheClient<K, V> {

    private static final Logger LOG = LoggerFactory.getLogger(RedisClient.class);

    private boolean useCodis;

    private final RedisTemplate<K, V> redisTemplate;
    private final ValueOperations<K, V> operations;

    @Autowired
    public RedisClient(@Qualifier("redisTemplate") RedisTemplate<K, V> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForValue();
        this.useCodis = true;

    }

    public V get(K key) {
        LOG.debug("获取数据key={}", key);
        if (useCodis) {
            return operations.get(key);
        } else {
            return null;
        }
    }

    public void put(K key, long expiration, V value) {
        if (useCodis) {
            operations.getAndSet(key, value);
            redisTemplate.expire(key, expiration, TimeUnit.MILLISECONDS);
        }
    }

    public void put(K key, V value, long expiration, TimeUnit timeUnit) {
        if (useCodis) {
            operations.getAndSet(key, value);
            redisTemplate.expire(key, expiration, timeUnit);
        }
    }

    public void delete(K key) {
        if (useCodis) {
            redisTemplate.delete(key);
        }
    }

    public void flush() {
        if (useCodis) {
            redisTemplate.getConnectionFactory().getConnection().flushDb();
        }
    }

    public void batchDelete(List<K> keys) {
        if (useCodis) {
            redisTemplate.delete(keys);
        }
    }

    public Long incr(final K key, final long timeout, final TimeUnit unit) {
        Long current = redisTemplate.boundValueOps(key).increment(1L);
        if (current == 1) {
            redisTemplate.boundValueOps(key).expire(timeout, unit);
        }
        return current;
    }

    public void setUseCodis(boolean useCodis) {
        this.useCodis = useCodis;
    }

    public boolean setIfAbsent(K k, V v) {
        if (useCodis) {
            return operations.setIfAbsent(k, v);
        }
        return false;
    }

    public void expire(K k, long time, TimeUnit timeUnit){
        if(useCodis){
            redisTemplate.expire(k, time, timeUnit);
        }
    }

}