package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderSign;

@Repository
public class YydOrderSignDao extends PageHibernateDao<YydOrderSign, Long> {

	public YydOrderSign getByUserOrder(Long userId, Long orderId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("orderId", orderId));

		@SuppressWarnings("unchecked")
		List<YydOrderSign> signs = criteria.list();

		if (!signs.isEmpty())
			return signs.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<YydOrderSign> findByOrder(Long orderId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("orderId", orderId));

		List<YydOrderSign> signs = criteria.list();

		return signs;
	}

}
