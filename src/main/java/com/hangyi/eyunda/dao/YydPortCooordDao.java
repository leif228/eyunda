package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydPortCooord;

@Repository
public class YydPortCooordDao extends PageHibernateDao<YydPortCooord, Long> {

	@SuppressWarnings("unchecked")
	public List<YydPortCooord> getAllPortCooords(String portNo) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("portNo", portNo));

		return criteria.list();
	}

}
