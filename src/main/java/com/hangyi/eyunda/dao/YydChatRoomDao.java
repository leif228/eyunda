package com.hangyi.eyunda.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydChatRoom;

@Repository
public class YydChatRoomDao extends PageHibernateDao<YydChatRoom, Long> {

	public List<YydChatRoom> getByUserId(Long userId) {
		String hql = "select a from YydChatRoom a, YydChatRoomStatus b where a.id = b.roomId and b.userId = " + userId
				+ " order by a.recentlyTime desc";

		List<YydChatRoom> rooms = super.find(hql);

		return rooms;
	}

}
