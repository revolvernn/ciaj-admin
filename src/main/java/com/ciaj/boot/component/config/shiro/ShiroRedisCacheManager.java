package com.ciaj.boot.component.config.shiro;

import org.apache.shiro.cache.AbstractCacheManager;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/23 16:09
 * @Version 1.0
 */
public class ShiroRedisCacheManager extends AbstractCacheManager{
	private RedisTemplate redisTemplate;

	public ShiroRedisCacheManager(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected Cache<byte[], Object> createCache(String name) throws CacheException {
		return new ShiroRedisCache<byte[], Object>(redisTemplate, name);
	}
}