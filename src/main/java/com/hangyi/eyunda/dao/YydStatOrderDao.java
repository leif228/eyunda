package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydStatOrder;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydStatOrderDao extends PageHibernateDao<YydStatOrder, Long> {

	public List<YydStatOrder> getOneYear() {
		Criteria criteria = getSession().createCriteria(entityClass);

		Calendar currDate = Calendar.getInstance();
		Calendar tempDate = CalendarUtil.addMonths(currDate, -11);
		String yearMonth = CalendarUtil.toYYYYMM(tempDate);

		criteria.add(Restrictions.ge("yearMonth", yearMonth));
		criteria.addOrder(Order.asc("yearMonth"));

		List<YydStatOrder> yydStatOrders = super.find(criteria);

		return yydStatOrders;
	}

	public YydStatOrder getLastEntity() {
		Page<YydStatOrder> page = new Page<YydStatOrder>();
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

	public YydStatOrder getByYyyyMm(String yyyymm) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("yearMonth", yyyymm));

		List<YydStatOrder> ls = super.find(criteria);
		if (!ls.isEmpty())
			return ls.get(0);
		else
			return null;
	}

}
