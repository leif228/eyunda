package com.hangyi.eyunda.service.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Service
public class RedisService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JedisPool redisPool;

	public String getValueFromQueue(String key, String field) {
		String value = "";
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
			value = jedis.hget(key, field);
		} catch (JedisConnectionException jedisEx) {
			redisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null)
				redisPool.returnResource(jedis);
		}
		return value;
	}

	public void setValueToQueue(String key, String field, String value) {
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
			jedis.hset(key, field, value);
		} catch (JedisConnectionException jedisEx) {
			redisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null)
				redisPool.returnResource(jedis);
		}
	}

	public void delFieldFromQueue(String key, String field) {
		Jedis jedis = null;
		try {
			jedis = redisPool.getResource();
			jedis.hdel(key, field);
		} catch (JedisConnectionException jedisEx) {
			redisPool.returnBrokenResource(jedis);
		} catch (Exception e) {
			throw e;
		} finally {
			if (jedis != null)
				redisPool.returnResource(jedis);
		}
	}

}
