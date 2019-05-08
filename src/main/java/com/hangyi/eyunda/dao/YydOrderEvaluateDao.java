package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderEvaluate;

@Repository
public class YydOrderEvaluateDao extends PageHibernateDao<YydOrderEvaluate, Long> {

	public YydOrderEvaluate getByOrderId(Long orderId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("orderId", orderId));
		@SuppressWarnings("unchecked")
		List<YydOrderEvaluate> evaluates = criteria.list();

		if (evaluates.isEmpty())
			return null;
		else
			return evaluates.get(0);
	}

	public List<YydOrderEvaluate> getByShipId(Long shipId, int preTwenty) {
		Page<YydOrderEvaluate> page = new Page<YydOrderEvaluate>();
		page.setPageSize(preTwenty);
		page.setPageNo(1);
		
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("shipId", shipId));
		
		return super.findPageByCriteria(page, criteria).getResult();
	}
}
