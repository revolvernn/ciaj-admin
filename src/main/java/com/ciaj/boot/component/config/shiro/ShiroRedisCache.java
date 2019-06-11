package com.ciaj.boot.component.config.shiro;

import com.ciaj.boot.component.config.redis.StringRedisSerializer;
import com.ciaj.comm.utils.ObjectUtil;
import com.google.common.collect.Sets;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/27 13:43
 * @Description:
 */
@Log4j2
public class ShiroRedisCache<K, V> implements Cache<K, V> {
	private RedisTemplate redisTemplate;
	private String prefix = "shiro_redis:";

	public ShiroRedisCache(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	public ShiroRedisCache(RedisTemplate shiroRedisTemplate, String prefix) {
		this(shiroRedisTemplate);
		if (StringUtils.isNoneBlank(prefix)) {
			this.prefix = prefix;
		}
	}

	@Override
	public V get(K key) throws CacheException {
		if (key == null) {
			return null;
		}

		V v = (V) redisTemplate.opsForValue().get(getKey(key));
		if (log.isDebugEnabled()) {
			log.debug("ShiroRedisCache get Key: {},V：{}", key, v);
		}
		return v;
	}

	@Override
	public V put(K key, V value) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("ShiroRedisCache put Key: {}, value: {}", key, value);
		}

		if (key == null || value == null) {
			return null;
		}

		redisTemplate.opsForValue().set(getKey(key), value);
		return value;
	}

	@Override
	public V remove(K key) throws CacheException {
		if (log.isDebugEnabled()) {
			log.debug("ShiroRedisCache remove Key: {}", key);
		}

		if (key == null) {
			return null;
		}

		String key1 = getKey(key);
		ValueOperations<byte[], V> vo = redisTemplate.opsForValue();
		V value = vo.get(key1);
		redisTemplate.delete(key1);
		return value;
	}

	@Override
	public void clear() throws CacheException {
		for (K key : keys()) {
			if (log.isDebugEnabled()) {
				log.debug("ShiroRedisCache clear Key: {}", key);
			}
			remove(key);
		}
	}

	@Override
	public int size() {
		Long len = redisTemplate.getConnectionFactory().getConnection().dbSize();
		return len.intValue();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Set<K> keys() {
		byte[] bkey = (prefix + "*").getBytes();
		Set<byte[]> set = redisTemplate.keys(new StringRedisSerializer().deserialize(bkey));
		Set<K> result = Sets.newHashSet();

		if (CollectionUtils.isEmpty(set)) {
			return Collections.emptySet();
		}

		for (byte[] key : set) {
			result.add((K) key);
		}
		return result;
	}

	@Override
	public Collection<V> values() {
		Set<K> keys = keys();
		List<V> values = new ArrayList<>(keys.size());
		for (K k : keys) {
			values.add((V) redisTemplate.opsForValue().get(getKey(k)));
		}
		return values;
	}

	private byte[] getByteKey(K key) {
		if (key instanceof String) {
			String preKey = this.prefix + key;
			return new StringRedisSerializer().serialize(preKey);
		} else {
			return ObjectUtil.serialize(key);
		}
	}

	private String getKey(K key) {
		return new StringRedisSerializer().deserialize(getByteKey(key));
	}

	public String getPrefix() {
		return prefix;
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}
}
