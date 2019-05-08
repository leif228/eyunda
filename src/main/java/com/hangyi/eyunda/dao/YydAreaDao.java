package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydArea;

@Repository
public class YydAreaDao extends PageHibernateDao<YydArea, Long> {
	/** 根据地区编码查询对象 */
	public YydArea getByCode(String code) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("areaNo", code));

		@SuppressWarnings("unchecked")
		List<YydArea> areas = criteria.list();

		if (!areas.isEmpty())
			return areas.get(0);
		else
			return null;
	}
	
	public List<YydArea> getAreas(String city_no) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.or(Restrictions.and(Restrictions.like(
				"areaNo", city_no, MatchMode.START))));

		@SuppressWarnings("unchecked")
		List<YydArea> areas = criteria.list();

		if (!areas.isEmpty())
			return areas;
		else
			return null;
	}
	
}
