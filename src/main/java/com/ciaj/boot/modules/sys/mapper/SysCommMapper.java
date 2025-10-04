package com.ciaj.boot.modules.sys.mapper;

import com.ciaj.boot.component.config.redis.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * @Description
 * @Author Ciaj.
 * @Date 2019/4/29 16:49
 * @Version 1.0
 */
@CacheNamespace(implementation = MybatisRedisCache.class, flushInterval = 60000)
public interface SysCommMapper {
	@Select("show table status")
	List<Map<String, Object>> selectTableStatus();
}
