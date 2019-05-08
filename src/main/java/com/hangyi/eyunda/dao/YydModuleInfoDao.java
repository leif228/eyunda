package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydModuleInfo;

@Repository
public class YydModuleInfoDao extends PageHibernateDao<YydModuleInfo, Long> {

	public YydModuleInfo getModuleByUrl(String moduleUrl) {

		Criteria criteria = this.getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("moduleUrl", moduleUrl));

		List<YydModuleInfo> ms = super.find(criteria);
		if (!ms.isEmpty())
			return ms.get(0);
		else
			return null;
	}

}
