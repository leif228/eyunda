package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydChatMsgStatus;

@Repository
public class YydChatMsgStatusDao extends PageHibernateDao<YydChatMsgStatus, Long> {

	public YydChatMsgStatus getByMsgIdAndUserId(Long msgId, Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("msgId", msgId));
		criteria.add(Restrictions.eq("userId", userId));

		List<YydChatMsgStatus> statuss = super.find(criteria);

		if (!statuss.isEmpty())
			return statuss.get(0);
		else
			return null;
	}

}
