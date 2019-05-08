package com.hangyi.eyunda.chat.redis.recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;

@Service
public class OnlineUserRecorder {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private JedisPool redisPool;

	public OnlineUserRecorder() {
	}

	public long getOnlineUserSize() {
		Jedis jedis = redisPool.getResource();
		try {
			long ouCount = jedis.hlen(MessageConstants.ONLINE_USER_QUEUE);

			return ouCount;
		} catch (Exception e) {
			return 0;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public void addOnlineUser(OnlineUser ou, LoginSourceCode src) {
		Jedis jedis = redisPool.getResource();
		try {
			if (src == LoginSourceCode.mobile) {
				String oldSessionId = jedis.hget(MessageConstants.MINA_USERID_QUEUE, Long.toString(ou.getId()));
				if (oldSessionId != null)
					jedis.hdel(MessageConstants.MINA_SESSION_QUEUE, oldSessionId);

				jedis.hset(MessageConstants.MINA_SESSION_QUEUE, ou.getSessionId(), Long.toString(ou.getId()));
				jedis.hset(MessageConstants.MINA_USERID_QUEUE, Long.toString(ou.getId()), ou.getSessionId());
			} else if (src == LoginSourceCode.web) {
				String oldSessionId = jedis.hget(MessageConstants.DWR_USERID_QUEUE, Long.toString(ou.getId()));
				if (oldSessionId != null)
					jedis.hdel(MessageConstants.DWR_SESSION_QUEUE, oldSessionId);

				jedis.hset(MessageConstants.DWR_SESSION_QUEUE, ou.getDwrSessionId(), Long.toString(ou.getId()));
				jedis.hset(MessageConstants.DWR_USERID_QUEUE, Long.toString(ou.getId()), ou.getDwrSessionId());
			}
			// 要覆盖的
			jedis.hset(MessageConstants.ONLINE_USER_QUEUE, Long.toString(ou.getId()), ou.getJsonString());

			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public void removeOnlineUser(OnlineUser ou, LoginSourceCode src) {
		Jedis jedis = redisPool.getResource();
		try {
			if (src == LoginSourceCode.mobile) {
				if (ou.getSessionId() != null && !"".equals(ou.getSessionId()))
					jedis.hdel(MessageConstants.MINA_SESSION_QUEUE, ou.getSessionId());
				ou.setSessionId(null);
			} else if (src == LoginSourceCode.web) {
				if (ou.getDwrSessionId() != null && !"".equals(ou.getDwrSessionId()))
					jedis.hdel(MessageConstants.DWR_SESSION_QUEUE, ou.getDwrSessionId());
				ou.setDwrSessionId(null);
			}

			if (ou.getSessionId() == null && ou.getDwrSessionId() == null) {
				// 删除
				jedis.hdel(MessageConstants.ONLINE_USER_QUEUE, Long.toString(ou.getId()));
			} else {
				// 要覆盖的
				jedis.hset(MessageConstants.ONLINE_USER_QUEUE, Long.toString(ou.getId()), ou.getJsonString());
			}

			return;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public OnlineUser getOnlineUser(Long userId) {
		Jedis jedis = redisPool.getResource();
		try {
			String jsonStr = jedis.hget(MessageConstants.ONLINE_USER_QUEUE, Long.toString(userId));

			if (jsonStr != null) {
				OnlineUser ou = new OnlineUser(jsonStr);
				return ou;
			}
			return null;
		} catch (Exception e) {
			return null;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public OnlineUser getOnlineUser(String sessionId) {
		Jedis jedis = redisPool.getResource();
		try {
			String userId = jedis.hget(MessageConstants.MINA_SESSION_QUEUE, sessionId);

			if (userId != null) {
				String jsonStr = jedis.hget(MessageConstants.ONLINE_USER_QUEUE, userId);

				if (jsonStr != null) {
					OnlineUser ou = new OnlineUser(jsonStr);
					return ou;
				}
			}

			return null;
		} catch (Exception e) {
			logger.error("loginException " + sessionId + " - " + e.getMessage());
			return null;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public boolean existOnlineUser(Long userId) {
		Jedis jedis = redisPool.getResource();
		try {

			boolean b = jedis.hexists(MessageConstants.ONLINE_USER_QUEUE, userId.toString());

			return b;
		} catch (Exception e) {
			return false;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

	public List<OnlineUser> getAllOnlineUsers() {
		List<OnlineUser> ous = new ArrayList<OnlineUser>();

		Jedis jedis = redisPool.getResource();
		try {
			Map<String, String> map = jedis.hgetAll(MessageConstants.ONLINE_USER_QUEUE);

			if (map != null && !map.isEmpty()) {
				for (String key : map.keySet()) {
					String jsonStr = map.get(key);
					OnlineUser ou = new OnlineUser(jsonStr);

					ous.add(ou);
				}
			}
			return ous;
		} catch (Exception e) {
			return null;
		} finally {
			redisPool.returnResource(jedis);
		}
	}

}
