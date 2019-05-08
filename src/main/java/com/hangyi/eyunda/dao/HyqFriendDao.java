package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqFriend;
import com.hangyi.eyunda.domain.enumeric.ApplyStatusCode;

@Repository
public class HyqFriendDao extends PageHibernateDao<HyqFriend, Long> {

	public List<HyqFriend> getMyFriends(Long userId, ApplyStatusCode status) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));

		if (status != null)
			criteria.add(Restrictions.eq("status", status));
		else
			criteria.add(Restrictions.ne("status", ApplyStatusCode.approve));

		criteria.addOrder(Order.desc("createTime"));

		return super.find(criteria);
	}

	public List<HyqFriend> getTagFriends(Long userId, Long tagId, ApplyStatusCode status) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));

		if (tagId > 0L)
			criteria.add(Restrictions.eq("tagId", tagId));

		if (status != null)
			criteria.add(Restrictions.eq("status", status));

		criteria.addOrder(Order.desc("createTime"));

		return super.find(criteria);
	}

	public HyqFriend getFriend(Long userId, Long friendId) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("friendId", friendId));

		List<HyqFriend> tfs = super.find(criteria);
		if (!tfs.isEmpty())
			return tfs.get(0);
		else
			return null;
	}

}
