package com.hangyi.eyunda.chat.redis.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.event.BaseEvent;

@Service
public class MessageSender {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JedisPool redisPool;

	public MessageSender() {
	}

	public void send(String serverName, BaseEvent ev) {
		Jedis jedis = redisPool.getResource();
		try {
			jedis.publish(MessageConstants.SERVER_QUEUE + serverName, ev.toJson());
			logger.info("@@jedis 发布 MESSAGE_EVENT:"+ev.toJson());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}

}
