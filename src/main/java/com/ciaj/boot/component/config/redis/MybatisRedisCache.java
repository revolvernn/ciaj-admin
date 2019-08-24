package com.ciaj.boot.component.config.redis;

import com.ciaj.base.Mapper;
import com.ciaj.boot.component.config.SpringContextUtils;
import com.ciaj.comm.annotation.MultiTableJoins;
import com.ciaj.comm.utils.MD5Util;
import com.ciaj.comm.utils.ObjectUtil;
import com.ciaj.comm.utils.PageUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.cache.Cache;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author: Ciaj.
 * @Date: 2019/2/27 16:59
 * @Description: 采用mybatis注解缓存 @CacheNamespace，主要用于mybatis二级缓存，但是要注意：配置文件和接口注释是不能够配合使用的。只能通过全注解的方式或者全部通过xml配置文件的方式使用
 */
@Log4j2
public class MybatisRedisCache implements Cache {
	private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
	private String id; // cache instance id
	private RedisTemplate redisTemplate;
	private static final long EXPIRE_TIME_IN_MINUTES = 30; // redis过期时间

	private String prefix = "mybatis_redis_cache";
	private static final String MULTITABLE_JOINS_SUFFIX = "MultiTable";


	public String getPrefix() {
		return prefix + ":";
	}

	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	public MybatisRedisCache(String id) {
		if (id == null) {
			throw new IllegalArgumentException("Cache instances require an ID");
		}
		this.id = id;
	}

	@Override
	public String getId() {
		return id;
	}

	/**
	 * Put query result to redis
	 *
	 * @param key
	 * @param value
	 */
	@Override
	public void putObject(Object key, Object value) {
		try {
			//处理随机排序查询不进缓存
			if (PageUtils.isRandOrderBy() || value == null) return;
			//
			RedisTemplate redisTemplate = getRedisTemplate();
			ValueOperations opsForValue = redisTemplate.opsForValue();
			String cacheKey = getCacheKey(key);
			opsForValue.set(cacheKey, value, EXPIRE_TIME_IN_MINUTES, TimeUnit.MINUTES);
			redisTemplate.opsForHash().put(getPrefix() + id, cacheKey, "");

			//将缓存ID添加到多表mapper 集合中，当mapper中操作增删改时清除当前查询缓存
			Class<? extends Mapper>[] multiTableMappers = getMultiTableMappers(key);
			if (multiTableMappers != null) {
				for (Class<? extends Mapper> multiTableMapper : multiTableMappers) {
					redisTemplate.opsForHash().put(getPrefix() + multiTableMapper.getCanonicalName(), cacheKey, "");
					log.info("Put query result to redis id：{}", getPrefix() + multiTableMapper.getCanonicalName());
				}
			}
			log.info("Put query result to redis id：{}，cacheKey：{}", getPrefix() + id, cacheKey);
			log.debug("Put query result to redis key：{}，cacheKey：{}", key.toString(), cacheKey);
		} catch (Throwable t) {
			log.error("Redis put failed key：{}，cause：{}", key.toString(), t);
		}
	}

	/**
	 * Get cached query result from redis
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object getObject(Object key) {
		try {
			String cacheKey = getCacheKey(key);
			RedisTemplate redisTemplate = getRedisTemplate();
			ValueOperations opsForValue = redisTemplate.opsForValue();
			log.debug("Get cached query result from redis id: {}", getPrefix() + id);
			log.info("Get cached query result from cacheKey：{}", cacheKey);
			log.debug("Get cached query result from key: {}", key.toString());
			return opsForValue.get(cacheKey);
		} catch (Throwable t) {
			log.error("Redis get failed id：{} ， key：{}, fail over to db", getPrefix() + id, key.toString(), t);
			return null;
		}
	}

	/**
	 * Remove cached query result from redis
	 *
	 * @param key
	 * @return
	 */
	@Override
	public Object removeObject(Object key) {
		try {
			String cacheKey = getCacheKey(key);
			RedisTemplate redisTemplate = getRedisTemplate();
			redisTemplate.delete(cacheKey);
			log.debug("Remove cached query result from redis cacheKey：{}", cacheKey);
			log.debug("Remove cached query result from redis key：{}", key.toString());
		} catch (Throwable t) {
			log.error("Redis remove failed key: {}, cause：{}", key.toString(), t);
		}
		return null;
	}

	/**
	 * Clears this cache instance
	 */
	@Override
	public void clear() {
		try {
			RedisTemplate redisTemplate = getRedisTemplate();
			Map<String, String> entries = redisTemplate.opsForHash().entries(getPrefix() + id);
			long count = 0;
			if (entries != null) {
				for (Map.Entry<String, String> entry : entries.entrySet()) {
					Long result = redisTemplate.opsForHash().delete(getPrefix() + id, entry.getKey());
					final Boolean delete = redisTemplate.delete(entry.getKey());
					count += result;
					log.info("clear redis cached id: {} , key: {} ,  {}", getPrefix() + id, entry.getKey(), delete);
				}
			}
			log.debug("clear redis cached id: {} the cached query result from redis delete count:{}", getPrefix() + id, count);
		} catch (Exception e) {
			log.error("clear redis cached id:  {} ,error: {}", getPrefix() + id, e);
		}
	}

	/**
	 * This method is not used
	 *
	 * @return
	 */
	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public ReadWriteLock getReadWriteLock() {
		return readWriteLock;
	}

	private RedisTemplate getRedisTemplate() {
		if (redisTemplate == null) {
			redisTemplate = (RedisTemplate) SpringContextUtils.getBean("redisTemplate");
		}
		return redisTemplate;
	}

	private byte[] getBytesKey(Object key) {
		if (key instanceof String) {
			String prekey = this.getPrefix() + key.toString();
			try {
				return new StringRedisSerializer().serialize(prekey);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ObjectUtil.serialize(key);
	}

	/**
	 * 获取 查询缓存key
	 *
	 * @param key
	 * @return
	 */
	private String getCacheKey(Object key) {
		return getPrefix() + MD5Util.encodeMD5Hex(key.toString());
	}

	/**
	 * 多表连接方法定义以MultiTable 结尾
	 *
	 * @param key
	 * @return
	 */
	private Class<? extends Mapper>[] getMultiTableMappers(Object key) {
		String[] keys = key.toString().split(":");
		if (keys.length > 3) {
			try {
				String packageAndMethod = keys[2];
				String[] packageAndMethods = packageAndMethod.split("\\.");
				if (packageAndMethods.length > 0) {
					Class<?> aClass = Class.forName(id);
					String methodName = packageAndMethods[packageAndMethods.length - 1];
					if (methodName.endsWith(MULTITABLE_JOINS_SUFFIX)) {
						log.debug("============MultiTable-methodName=============== {},{}", methodName, packageAndMethod);
						Method[] methods = aClass.getDeclaredMethods();
						Method method = null;
						for (Method m : methods) {
							String name = m.getName();
							if (name.equals(methodName)) {
								method = m;
								break;
							}
						}
						if (method != null) {
							MultiTableJoins multiTable = method.getAnnotation(MultiTableJoins.class);
							return multiTable == null ? null : multiTable.mappers();
						}
					}
				}
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}
}
