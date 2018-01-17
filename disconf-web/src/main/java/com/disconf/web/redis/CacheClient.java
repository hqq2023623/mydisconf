package com.disconf.web.redis;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/4
 */
public interface CacheClient<K, V> {

	V get(K key);

	void put(K key, long expiration, V value);

	void delete(K key);

	void flush();

	void batchDelete(List<K> keys);
}
