package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydStatShipCall;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydStatShipCallDao extends PageHibernateDao<YydStatShipCall, Long> {
	//统计船舶
	public List<YydStatShipCall> getOneYear() {
		Criteria criteria = getSession().createCriteria(entityClass);
		
		Calendar today = Calendar.getInstance();
		Calendar todayAtPrevYear = CalendarUtil.addMonths(today, -11);

		criteria.add(Restrictions.le("yearMonth", CalendarUtil.toYYYYMM(today)));
		criteria.add(Restrictions.ge("yearMonth", CalendarUtil.toYYYYMM(todayAtPrevYear)));

		criteria.addOrder(Order.asc("yearMonth"));
		criteria.addOrder(Order.asc("roleCode"));

		@SuppressWarnings("unchecked")
		List<YydStatShipCall> yydStatShipCalls = criteria.list();

		return yydStatShipCalls;
	}
	
	public YydStatShipCall getLastEntity() {
		Page<YydStatShipCall> page = new Page<YydStatShipCall>();
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
	
	public YydStatShipCall getByYearMonth(String ym, UserPrivilegeCode rc) {
		Page<YydStatShipCall> page = new Page<YydStatShipCall>();
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
