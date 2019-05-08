package com.hangyi.eyunda.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydChatMessage;
import com.hangyi.eyunda.domain.enumeric.ReadStatusCode;

@Repository
public class YydChatMessageDao extends PageHibernateDao<YydChatMessage, Long> {

	public Integer getNoReadCount(Long roomId, Long userId) {
		String hql = "select a from YydChatMessage a, YydChatMsgStatus b where a.id = b.msgId and a.roomId = ? and b.userId = ? and b.readStatus = ? order by a.createTime desc";

		List<YydChatMessage> rooms = super.find(hql, new Object[] { roomId, userId, ReadStatusCode.noread });

		if (!rooms.isEmpty())
			return rooms.size();
		else
			return 0;
	}

	// 会排除我已经删除了的消息
	public Page<YydChatMessage> getPageByRoomId(int pageSize, int pageNo, Long roomId, Long userId) {
		Page<YydChatMessage> page = new Page<YydChatMessage>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from YydChatMessage a, YydChatMsgStatus b where a.id = b.msgId and a.roomId = ? and b.userId = ? order by a.createTime desc";

		super.findPage(page, hql, new Object[] { roomId, userId });

		return page;
	}

	// 查询指定聊天室的全部消息用于删除
	public List<YydChatMessage> getAllMsgsByRoomId(Long roomId) {
		String hql = "select a from YydChatMessage a where a.roomId = ? order by a.createTime desc";

		List<YydChatMessage> msgs = super.find(hql, new Object[] { roomId });

		return msgs;
	}

	// 删除指定聊天室的全部消息
	public int deleteAllMsgsByRoomId(Long roomId) {
		String hql = "delete from YydChatMessage where roomId = ? ";

		int n = super.batchExecute(hql, new Object[] { roomId });

		return n;
	}

}
