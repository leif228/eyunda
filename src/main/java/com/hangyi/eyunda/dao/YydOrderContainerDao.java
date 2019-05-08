package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderContainer;

@Repository
public class YydOrderContainerDao extends PageHibernateDao<YydOrderContainer, Long> {

	@SuppressWarnings("unchecked")
	public List<YydOrderContainer> findByOrder(Long orderId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("orderId", orderId));

		List<YydOrderContainer> containers = criteria.list();

		return containers;
	}

}
