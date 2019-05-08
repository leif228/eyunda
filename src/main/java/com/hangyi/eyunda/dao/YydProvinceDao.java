package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydProvince;

@Repository
public class YydProvinceDao extends PageHibernateDao<YydProvince, Long> {
	/** 根据地区编码查询对象 */
	public YydProvince getByCode(String code) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("provinceNo", code));

		@SuppressWarnings("unchecked")
		List<YydProvince> provinces = criteria.list();

		if (!provinces.isEmpty())
			return provinces.get(0);
		else
			return null;
	}
	
	@SuppressWarnings("static-access")
	public Page<YydProvince> findPage() {
		Page<YydProvince> page = new Page<YydProvince>();
		page.setPageSize(super.getTotalCount(super.createCriteria()));
		page.setPageNo(1);
		page.setOrderBy("id");
		page.setOrder(page.ASC);

		return super.getAll(page);
	}
	
	public List<YydProvince> getProvinces() {
		Criteria criteria = getSession().createCriteria(entityClass);

		@SuppressWarnings("unchecked")
		List<YydProvince> provinces = criteria.list();

		if (!provinces.isEmpty())
			return provinces;
		else
			return null;
	}

}
