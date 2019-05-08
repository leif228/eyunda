package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipUpdown;

@Repository
public class YydShipUpdownDao extends PageHibernateDao<YydShipUpdown, Long> {

	@SuppressWarnings("unchecked")
	public List<YydShipUpdown> getShipUpdowns(Long arvlftId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("arvlftId", arvlftId));

		return criteria.list();
	}

	public int delShipUpdowns(Long arvlftId) {
		String hql = "delete from YydShipUpdown where arvlftId=?";
		int n = super.batchExecute(hql, new Object[] { arvlftId });
		return n;
	}

}
