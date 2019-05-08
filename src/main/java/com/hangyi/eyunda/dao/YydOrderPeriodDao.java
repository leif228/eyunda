package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderPeriod;

@Repository
public class YydOrderPeriodDao extends PageHibernateDao<YydOrderPeriod, Long> {

	public YydOrderPeriod getByOrderId(Long orderId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("orderId", orderId));

		List<YydOrderPeriod> orders = super.find(criteria);

		if (orders.isEmpty())
			return null;
		else
			return orders.get(0);
	}

}
