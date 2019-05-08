package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.PubPayCity;

@Repository
public class PubPayCityDao extends PageHibernateDao<PubPayCity, Long> {

	public List<PubPayCity> getPPCByPPN(String nodeCode) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("nodeCode", nodeCode));

		@SuppressWarnings("unchecked")
		List<PubPayCity> ppcs = criteria.list();

		return ppcs;
	}

}
