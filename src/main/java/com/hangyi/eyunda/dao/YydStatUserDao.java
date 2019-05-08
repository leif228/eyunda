package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydStatUser;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydStatUserDao extends PageHibernateDao<YydStatUser, Long> {
	public YydStatUser getByYearMonth(String yearMonth) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("yearMonth", yearMonth));

		@SuppressWarnings("unchecked")
		List<YydStatUser> yydStatUsers = criteria.list();

		if (!yydStatUsers.isEmpty())
			return yydStatUsers.get(0);
		else
			return null;
	}

	public List<YydStatUser> getInAYear() {
		Criteria criteria = getSession().createCriteria(entityClass);

		Calendar currDate = Calendar.getInstance();
		Calendar tempDate = CalendarUtil.addMonths(currDate, -11);
		String yearMonth = CalendarUtil.toYYYYMM(tempDate);

		criteria.add(Restrictions.ge("yearMonth", yearMonth));
		criteria.addOrder(Order.asc("yearMonth"));

		@SuppressWarnings("unchecked")
		List<YydStatUser> yydStatUsers = criteria.list();

		return yydStatUsers;
	}

	public YydStatUser getLastEntity() {
		Page<YydStatUser> page = new Page<YydStatUser>();
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

}
