package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydStatLogin;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydStatLoginDao extends PageHibernateDao<YydStatLogin, Long> {
	public List<YydStatLogin> getInYear() {
		Criteria criteria = getSession().createCriteria(entityClass);

		Calendar today = Calendar.getInstance();
		Calendar todayAtPrevYear = CalendarUtil.addMonths(today, -11);

		criteria.add(Restrictions.ge("yearMonth", CalendarUtil.toYYYYMM(todayAtPrevYear)));
		criteria.add(Restrictions.le("yearMonth", CalendarUtil.toYYYYMM(today)));

		criteria.addOrder(Order.asc("yearMonth"));
		criteria.addOrder(Order.asc("roleCode"));
		
		@SuppressWarnings("unchecked")
		List<YydStatLogin> yydStatsLogin = criteria.list();

		return yydStatsLogin;
	}

	public YydStatLogin getLastEntity() {
		Page<YydStatLogin> page = new Page<YydStatLogin>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("yearMonth");

		Criteria criteria = getSession().createCriteria(entityClass);

		super.findPageByCriteria(page, criteria);

		if (!page.getResult().isEmpty())
			return page.getResult().get(0);
		else
			return null;
	}

	public YydStatLogin findByYearMonthAndRole(String ym, UserPrivilegeCode rc) {
		Page<YydStatLogin> page = new Page<YydStatLogin>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("yearMonth");

		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("yearMonth", ym));
		criteria.add(Restrictions.eq("roleCode", rc));

		super.findPageByCriteria(page, criteria);

		if (!page.getResult().isEmpty())
			return page.getResult().get(0);
		else
			return null;
	}

}
