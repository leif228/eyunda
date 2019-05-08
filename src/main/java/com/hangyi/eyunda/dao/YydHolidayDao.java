package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydHoliday;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydHolidayDao extends PageHibernateDao<YydHoliday, Long> {

	public YydHoliday getHoliday(Calendar date) {
		Calendar zeroDate = CalendarUtil.getTheDayZero(date);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.ge("startDate", zeroDate));
		criteria.add(Restrictions.le("endDate", zeroDate));

		List<YydHoliday> yydHolidays = super.find(criteria);
		if (yydHolidays.isEmpty())
			return null;
		else
			return yydHolidays.get(0);
	}

	public List<YydHoliday> findHoliday() {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.addOrder(Order.asc("startDate"));

		List<YydHoliday> yydHolidays = super.find(criteria);

		return yydHolidays;
	}

}
