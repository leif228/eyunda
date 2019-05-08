package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydChatRoomStatus;

@Repository
public class YydChatRoomStatusDao extends PageHibernateDao<YydChatRoomStatus, Long> {

	public List<YydChatRoomStatus> getByRoomId(Long roomId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("roomId", roomId));

		List<YydChatRoomStatus> statuss = super.find(criteria);

		return statuss;
	}

	public YydChatRoomStatus getByRoomIdAndUserId(Long roomId, Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("roomId", roomId));
		criteria.add(Restrictions.eq("userId", userId));

		List<YydChatRoomStatus> statuss = super.find(criteria);

		if (!statuss.isEmpty())
			return statuss.get(0);
		else
			return null;
	}

	public YydChatRoomStatus getByTwoUserId(Long userId1, Long userId2) {
		String hql = "select a from YydChatRoomStatus a where a.userId = ? ";

		List<YydChatRoomStatus> rooms = super.find(hql, new Object[] { userId1 });
		for (YydChatRoomStatus r : rooms) {
			List<YydChatRoomStatus> ss = this.getByRoomId(r.getRoomId());
			if (ss.size() == 2 && (ss.get(0).getUserId().equals(userId2) || ss.get(1).getUserId().equals(userId2))) {
				return r;
			}
		}

		return null;
	}

}
