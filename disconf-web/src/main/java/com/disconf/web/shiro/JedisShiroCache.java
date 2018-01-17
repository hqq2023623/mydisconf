package com.disconf.web.shiro;

import com.alibaba.fastjson.JSONObject;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author lzj
 * @date 2018/1/4
 */
public class JedisShiroCache<K, V extends Serializable> implements Cache<K, V> {

    private static final Logger logger = LoggerFactory.getLogger(JedisShiroCache.class);

    private RedisTemplate<K, V> redisTemplate;

    private ValueOperations<K, V> operations;

    private String name;

    public JedisShiroCache(String name, RedisTemplate<K, V> redisTemplate) {
        this.name = name;
        this.redisTemplate = redisTemplate;
        this.operations = redisTemplate.opsForValue();
    }

    /**
     * 自定义relm中的授权/认证的类名加上授权/认证英文名字
     */

    public String getName() {
        if (name == null)
            return "";
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private K getKey(K key){
        JSONObject obj = JSONObject.parseObject(JSONObject.toJSONString(key));
        K keystr = (K)("userealm_id_" + obj.getJSONObject("primaryPrincipal").get("id"));
        return keystr;
    }

    @SuppressWarnings("unchecked")
    @Override
    public V get(K key) throws CacheException {
        return operations.get(getKey(key));
    }

    @Override
    public V put(K key, V value) throws CacheException {
        V previos = get(key);
        try {
            operations.getAndSet(getKey(key), value);
            operations.set(getKey(key), value);
            redisTemplate.expire(getKey(key), 30 * 60 * 1000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("put cache error");
        }
        return previos;
    }

    @Override
    public V remove(K key) throws CacheException {
        V previos = get(key);
        try {
            redisTemplate.delete(getKey(key));
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("remove cache error");
        }
        return previos;
    }

    @Override
    public void clear() throws CacheException {
        redisTemplate.delete(keys());
    }

    @Override
    public int size() {
        if (keys() == null)
            return 0;
        return keys().size();
    }

    @Override
    public Set<K> keys() {
        Set<K> keys = redisTemplate.keys((K)"userealm_id_*");
        return keys;
    }

    @Override
    public Collection<V> values() {
        return operations.multiGet(keys());
    }

}
