package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydCity;

@Repository
public class YydCityDao extends PageHibernateDao<YydCity, Long> {
	/** 根据地区编码查询对象 */
	public YydCity getByCode(String code) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("cityNo", code));

		@SuppressWarnings("unchecked")
		List<YydCity> cities = criteria.list();

		if (!cities.isEmpty())
			return cities.get(0);
		else
			return null;
	}

	@SuppressWarnings("static-access")
	public Page<YydCity> findPage() {
		Page<YydCity> page = new Page<YydCity>();
		page.setPageSize(super.getTotalCount(super.createCriteria()));
		page.setPageNo(1);
		page.setOrderBy("id");
		page.setOrder(page.ASC);

		return super.getAll(page);
	}

	public List<YydCity> getProCitys(String province_no) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.or(Restrictions.and(Restrictions.like(
				"cityNo", province_no, MatchMode.START))));

		@SuppressWarnings("unchecked")
		List<YydCity> cities = criteria.list();

		if (!cities.isEmpty())
			return cities;
		else
			return null;
	}
	
	public List<YydCity> getCitys() {
		Criteria criteria = getSession().createCriteria(entityClass);

		@SuppressWarnings("unchecked")
		List<YydCity> cities = criteria.list();

		if (!cities.isEmpty())
			return cities;
		else
			return null;
	}

}
