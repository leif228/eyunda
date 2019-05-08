package com.hangyi.eyunda.chat.redis.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPubSub;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.chat.DwrMessageListener;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.MessageListener;
import com.hangyi.eyunda.chat.MinaMessageListener;
import com.hangyi.eyunda.chat.event.BaseEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.NotifyEvent;
import com.hangyi.eyunda.chat.event.StatusEvent;
import com.hangyi.eyunda.util.Constants;

public class MessageReceiver {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JedisPool redisPool;

	@Autowired
	private MinaMessageListener minaMessageListener;
	@Autowired
	private DwrMessageListener dwrMessageListener;

	private final Executor exec = Executors.newSingleThreadExecutor();
	private Runnable pubsubThread;

	private final Set<MessageListener> listeners = new HashSet<MessageListener>();

	public MessageReceiver() {
	}

	public void addListener(MessageListener listener) {
		listeners.add(listener);
	}

	public void removeListener(MessageListener listener) {
		listeners.remove(listener);
	}

	public void start() {
		this.addListener(this.minaMessageListener);
		this.addListener(this.dwrMessageListener);

		final Jedis jedis = redisPool.getResource();
		try {
			pubsubThread = new Runnable() {
				public void run() {
					String queue = MessageConstants.SERVER_QUEUE + Constants.MESSAGE_SERVER_NAME;
					jedis.subscribe(new PubSubListener(), queue);
				}
			};
			exec.execute(pubsubThread);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stop() {
		try {
			redisPool.destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private class PubSubListener extends JedisPubSub {

		public PubSubListener() {
			super();
		}

		// 初始化订阅时候的处理
		@Override
		public void onSubscribe(String channel, int subscribedChannels) {
			logger.info("初始化订阅时候的处理");
			logger.info(channel + "=" + subscribedChannels);
		}

		// 取消订阅时候的处理
		@Override
		public void onUnsubscribe(String channel, int subscribedChannels) {
			logger.info("取消订阅时候的处理");
			logger.info(channel + "=" + subscribedChannels);
		}

		// 初始化按表达式的方式订阅时候的处理
		@Override
		public void onPSubscribe(String pattern, int subscribedChannels) {
			logger.info("初始化按表达式的方式订阅时候的处理");
			logger.info(pattern + "=" + subscribedChannels);
		}

		// 取消按表达式的方式订阅时候的处理
		@Override
		public void onPUnsubscribe(String pattern, int subscribedChannels) {
			logger.info("取消按表达式的方式订阅时候的处理");
			logger.info(pattern + "=" + subscribedChannels);
		}

		// 取得订阅的消息后的处理
		@Override
		public void onPMessage(String pattern, String channel, String message) {
			logger.info("取得按表达式的方式订阅的消息后的处理");
			logger.info(pattern + "=" + channel + "=" + message);
		}

		// 取得按表达式的方式订阅的消息后的处理
		@Override
		public void onMessage(String channel, String message) {

			if (channel.equals(MessageConstants.SERVER_QUEUE + Constants.MESSAGE_SERVER_NAME)) {

				Gson gson = new Gson();
				HashMap<String, String> map = gson.fromJson(message, new TypeToken<Map<String, String>>() {
				}.getType());

				BaseEvent baseEvent = new BaseEvent(map);

				String messageType = baseEvent.getMessageType();

				if (messageType != null) {

					if (messageType.equals(MessageConstants.NOTIFY_EVENT)) {
						NotifyEvent event = new NotifyEvent(baseEvent.getEventMap());

						for (MessageListener listener : listeners) {
							try {
								listener.sendNotify(event);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (messageType.equals(MessageConstants.MESSAGE_EVENT)) {
						MessageEvent event = new MessageEvent(baseEvent.getEventMap());

						for (MessageListener listener : listeners) {
							try {
								listener.sendMessage(event);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else if (messageType.equals(MessageConstants.STATUS_EVENT)) {
						StatusEvent event = new StatusEvent(baseEvent.getEventMap());

						for (MessageListener listener : listeners) {
							try {
								listener.sendStatus(event);
							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					}

				}
			}
		}

	}

}
