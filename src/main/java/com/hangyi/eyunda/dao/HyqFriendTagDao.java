package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.HyqFriendTag;

@Repository
public class HyqFriendTagDao extends PageHibernateDao<HyqFriendTag, Long> {

	public List<HyqFriendTag> getByUserId(Long userId) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.addOrder(Order.asc("no"));

		return super.find(criteria);
	}

	public HyqFriendTag getTag(Long userId, String tag) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("tag", tag));

		List<HyqFriendTag> tags = super.find(criteria);

		if (!tags.isEmpty())
			return tags.get(0);
		else
			return null;
	}

}
