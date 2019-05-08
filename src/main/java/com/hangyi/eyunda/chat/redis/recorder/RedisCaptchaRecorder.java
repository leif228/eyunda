package com.hangyi.eyunda.chat.redis.recorder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.hangyi.eyunda.chat.MessageConstants;

@Service
public class RedisCaptchaRecorder {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JedisPool redisPool;

	public RedisCaptchaRecorder() {
	}

	public void addCaptcha(String uuid, String captcha) {
		Jedis jedis = redisPool.getResource();
		try {
			jedis.hset(MessageConstants.CAPTCHA_QUEUE , uuid, captcha);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public void removeCaptcha(String uuid) {
		Jedis jedis = redisPool.getResource();
		try {
			if (jedis.exists(MessageConstants.CAPTCHA_QUEUE)) {
				jedis.hdel(MessageConstants.CAPTCHA_QUEUE , uuid);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}
	
	public String getCaptcha(String uuid) {
		Jedis jedis = redisPool.getResource();
		String captcha = "";
		try {
			if (jedis.exists(MessageConstants.CAPTCHA_QUEUE)) 
				captcha = jedis.hget(MessageConstants.CAPTCHA_QUEUE, uuid);
				
			return captcha;
		} catch (Exception e) {
			return captcha;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	
}
