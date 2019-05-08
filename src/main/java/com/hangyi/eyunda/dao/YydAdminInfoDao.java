package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydAdminInfo;

@Repository
public class YydAdminInfoDao extends PageHibernateDao<YydAdminInfo, Long> {

	/** 根据登录名查询对象 */
	public YydAdminInfo getByLoginName(String loginName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("loginName", loginName));

		List<YydAdminInfo> admins = super.find(criteria);

		if (!admins.isEmpty())
			return admins.get(0);
		else
			return null;
	}

	@SuppressWarnings("static-access")
	public Page<YydAdminInfo> findPage() {
		Page<YydAdminInfo> page = new Page<YydAdminInfo>();
		page.setPageSize(super.getTotalCount(super.createCriteria()));
		page.setPageNo(1);
		page.setOrder(page.ASC);
		page.setOrderBy("id");

		return super.getAll(page);
	}

}
