package com.hangyi.eyunda.chat.redis.recorder;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.mina.core.future.WriteFuture;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.event.BaseEvent;
import com.hangyi.eyunda.chat.event.MessageEvent;
import com.hangyi.eyunda.chat.event.NotifyEvent;
import com.hangyi.eyunda.chat.mina.server.SessionManager;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class RedisMessageRecorder {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private JedisPool redisPool;

	public RedisMessageRecorder() {
	}

	public void addNoReadMessage(Long userId, String jsonMsg) {
		Jedis jedis = redisPool.getResource();
		try {
			// Long msgid = jedis.incr("global:nextRecordedMsgId");
			String msgid = "";
			Gson gson = new Gson();
			HashMap<String, String> map = gson.fromJson((String) jsonMsg, new TypeToken<Map<String, String>>() {
			}.getType());

			BaseEvent baseEvent = new BaseEvent(map);

			String messageType = baseEvent.getMessageType();
			if (messageType != null) {
				if (MessageConstants.MESSAGE_EVENT.equals(messageType)) {
					MessageEvent event = new MessageEvent(baseEvent.getEventMap());

					msgid = MessageConstants.MESSAGE_EVENT + event.getId();
				} else if (MessageConstants.NOTIFY_EVENT.equals(messageType)) {
					NotifyEvent event = new NotifyEvent(baseEvent.getEventMap());

					msgid = MessageConstants.NOTIFY_EVENT + event.getId();
				}
			}
			if (!"".equals(msgid))
				jedis.hset(MessageConstants.CHAT_MESSAGE_QUEUE + userId, msgid, jsonMsg);

			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public void removeNoReadMessage(Long userId, String key) {
		Jedis jedis = redisPool.getResource();
		try {
			if (jedis.exists(MessageConstants.CHAT_MESSAGE_QUEUE + userId)) {
				jedis.hdel(MessageConstants.CHAT_MESSAGE_QUEUE + userId, key);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	private Map<String, String> getAllNoReadMessages(Long userId) {
		Jedis jedis = redisPool.getResource();
		try {
			if (jedis.hlen(MessageConstants.CHAT_MESSAGE_QUEUE + userId) > 0) {
				Map<String, String> msgs = jedis.hgetAll(MessageConstants.CHAT_MESSAGE_QUEUE + userId);

				return msgs;
			}
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public void sendNoReadMessages(final Long userId) {
		try {
			// 取出未发的聊天消息
			Map<String, String> mapChatMsgs = this.getAllNoReadMessages(userId);

			if (mapChatMsgs != null && !mapChatMsgs.isEmpty()) {
				// 循环处理每一个未发的消息
				for (final String key : mapChatMsgs.keySet()) {
					String msgJsonStr = mapChatMsgs.get(key);

					Gson gs = new Gson();
					HashMap<String, String> m = gs.fromJson(msgJsonStr, new TypeToken<Map<String, String>>() {
					}.getType());

					BaseEvent be = new BaseEvent(m);
					String msgType = be.getMessageType();
					if (msgType != null) {
						if (MessageConstants.MESSAGE_EVENT.equals(msgType)) {
							MessageEvent ev = new MessageEvent(be.getEventMap());

							IoSession session = SessionManager.getSession(userId);
							// 推送该消息
							WriteFuture writeResult = session.write(ev.toJson());
							writeResult.awaitUninterruptibly(1, TimeUnit.SECONDS);
							if (writeResult.isWritten()) {
								// 从redis中删除该消息
								Jedis jedis = redisPool.getResource();
								try {
									if (jedis.exists(MessageConstants.CHAT_MESSAGE_QUEUE + userId)) {
										jedis.hdel(MessageConstants.CHAT_MESSAGE_QUEUE + userId, key);
									}
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									redisPool.returnResource(jedis);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendNoReadNotifies(final Long userId) {
		try {
			// 取出未发的聊天消息
			Map<String, String> mapChatMsgs = this.getAllNoReadMessages(userId);

			if (mapChatMsgs != null && !mapChatMsgs.isEmpty()) {
				// 循环处理每一个未发的消息
				for (final String key : mapChatMsgs.keySet()) {
					String msgJsonStr = mapChatMsgs.get(key);

					Gson gs = new Gson();
					HashMap<String, String> m = gs.fromJson(msgJsonStr, new TypeToken<Map<String, String>>() {
					}.getType());

					BaseEvent be = new BaseEvent(m);
					String msgType = be.getMessageType();
					if (msgType != null) {
						if (MessageConstants.NOTIFY_EVENT.equals(msgType)) {
							NotifyEvent ev = new NotifyEvent(be.getEventMap());

							IoSession session = SessionManager.getSession(userId);
							// 推送该消息
							WriteFuture writeResult = session.write(ev.toJson());
							writeResult.awaitUninterruptibly(1, TimeUnit.SECONDS);
							if (writeResult.isWritten()) {
								// 从redis中删除该消息
								Jedis jedis = redisPool.getResource();
								try {
									if (jedis.exists(MessageConstants.CHAT_MESSAGE_QUEUE + userId)) {
										jedis.hdel(MessageConstants.CHAT_MESSAGE_QUEUE + userId, key);
									}
								} catch (Exception e) {
									e.printStackTrace();
								} finally {
									redisPool.returnResource(jedis);
								}
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
