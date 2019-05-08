package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydSequence;

@Repository
public class YydSequenceDao extends PageHibernateDao<YydSequence, Long> {
	public YydSequence getByName(String sequenceName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("sequenceName", sequenceName));

		@SuppressWarnings("unchecked")
		List<YydSequence> yydSequence = criteria.list();

		if (!yydSequence.isEmpty())
			return yydSequence.get(0);
		else
			return null;
	}
}
