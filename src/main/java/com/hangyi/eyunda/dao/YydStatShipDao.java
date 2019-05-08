package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydStatShip;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydStatShipDao extends PageHibernateDao<YydStatShip, Long> {
	// 统计船舶
	public List<YydStatShip> getOneYear() {
		Criteria criteria = getSession().createCriteria(entityClass);

		Calendar currDate = Calendar.getInstance();
		Calendar tempDate = CalendarUtil.addMonths(currDate, -11);
		String yearMonth = CalendarUtil.toYYYYMM(tempDate);

		criteria.add(Restrictions.ge("yearMonth", yearMonth));
		criteria.addOrder(Order.asc("yearMonth"));

		@SuppressWarnings("unchecked")
		List<YydStatShip> yydStatShips = criteria.list();

		return yydStatShips;
	}

	public YydStatShip getLastEntity() {
		Page<YydStatShip> page = new Page<YydStatShip>();
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

	public YydStatShip getByYyyyMm(Calendar month) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("yearMonth", CalendarUtil.toYYYYMM(month)));

		List<YydStatShip> yydStatShips = super.find(criteria);

		if (!yydStatShips.isEmpty())
			return yydStatShips.get(0);
		else
			return null;
	}

}
