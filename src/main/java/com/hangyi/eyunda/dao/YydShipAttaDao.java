package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipAtta;

@Repository
public class YydShipAttaDao extends PageHibernateDao<YydShipAtta, Long> {

	public List<YydShipAtta> findByMyShipId(Long myShipId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipId", myShipId));
		criteria.addOrder(Order.asc("no"));

		return super.find(criteria);
	}

	public Integer getImageNo(Long myShipId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipId", myShipId));
		criteria.addOrder(Order.asc("no"));

		List<YydShipAtta> list = super.find(criteria);
		if (list.isEmpty())
			return 1;
		else {
			return list.get(list.size() - 1).getNo() + 1;
		}
	}

}
