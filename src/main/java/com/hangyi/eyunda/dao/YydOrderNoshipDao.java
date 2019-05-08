package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderNoship;

@Repository
public class YydOrderNoshipDao extends PageHibernateDao<YydOrderNoship, Long> {

	public YydOrderNoship getByOrderId(Long orderId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("orderId", orderId));

		List<YydOrderNoship> orders = super.find(criteria);

		if (orders.isEmpty())
			return null;
		else
			return orders.get(0);
	}

}
