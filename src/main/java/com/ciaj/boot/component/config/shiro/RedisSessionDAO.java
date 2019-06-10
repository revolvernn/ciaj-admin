package com.ciaj.boot.component.config.shiro;

import com.ciaj.boot.component.config.redis.StringRedisSerializer;
import com.ciaj.comm.utils.ObjectUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.SimpleSession;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.*;

/**
 * @Description TODO
 * @Author Ciaj.
 * @Date 2019/6/10 11:31
 * @Version 1.0
 */
@Log4j2
public class RedisSessionDAO extends EnterpriseCacheSessionDAO {
	// 创建session，保存到数据库
	private RedisTemplate redisTemplate;
	private String prefix = "redis-session:";

	public RedisSessionDAO(RedisTemplate redisTemplate) {
		this.redisTemplate = redisTemplate;
	}

	@Override
	protected Serializable doCreate(Session session) {
		Serializable sessionId = super.doCreate(session);
		String key = getKey(sessionId.toString());
		if (log.isDebugEnabled()) {
			log.debug("RedisSessionDAO doCreate set session is null Key: {}", key);
		}
		redisTemplate.opsForValue().set(key, sessionToByte(session));
		return sessionId;
	}

	// 获取session
	@Override
	protected Session doReadSession(Serializable sessionId) {
		// 先从缓存中获取session，如果没有再去数据库中获取
		Session session = super.doReadSession(sessionId);
		if (session == null) {
			String key = getKey(sessionId.toString());
			if (log.isDebugEnabled()) {
				log.debug("RedisSessionDAO doReadSession get session is null Key: {}", key);
			}
			byte[] bytes = (byte[]) redisTemplate.opsForValue().get(key);
			if (bytes != null && bytes.length > 0) {
				session = byteToSession(bytes);
			}
		}
		return session;
	}

	// 更新session的最后一次访问时间
	@Override
	protected void doUpdate(Session session) {
		super.doUpdate(session);
		String key = getKey(session.getId().toString());
		if (log.isDebugEnabled()) {
			log.debug("RedisSessionDAO doUpdate set session Key: {}", key);
		}
		redisTemplate.opsForValue().set(key, sessionToByte(session));
	}

	// 删除session
	@Override
	protected void doDelete(Session session) {
		super.doDelete(session);
		String key = getKey(session.getId());
		if (log.isDebugEnabled()) {
			log.debug("RedisSessionDAO doDelete delete session Key: {}", key);
		}
		redisTemplate.delete(key);
	}

	private byte[] getByteKey(Object key) {
		if (key instanceof String) {
			String preKey = this.prefix + key;
			return new StringRedisSerializer().serialize(preKey);
		} else {
			return ObjectUtil.serialize(key);
		}
	}

	private String getKey(Object key) {
		return new StringRedisSerializer().deserialize(getByteKey(key));
	}

	// 把session对象转化为byte保存到redis中
	public byte[] sessionToByte(Session session) {
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		byte[] bytes = null;
		try {
			ObjectOutputStream oo = new ObjectOutputStream(bo);
			oo.writeObject(session);
			bytes = bo.toByteArray();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return bytes;
	}

	// 把byte还原为session
	public Session byteToSession(byte[] bytes) {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytes);
		ObjectInputStream in;
		SimpleSession session = null;
		try {
			in = new ObjectInputStream(bi);
			session = (SimpleSession) in.readObject();
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage(), e);
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}

		return session;
	}

}
