package com.hangyi.eyunda.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipArvlft;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydShipArvlftDao extends PageHibernateDao<YydShipArvlft, Long> {

	public Page<YydShipArvlft> getMyShipArvlfts(String mmsi, String startTime, String endTime, Integer pageNo,
			Integer pageSize) {

		Page<YydShipArvlft> page = new Page<YydShipArvlft>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", mmsi));

		if (startTime != null && !"".equals(startTime)) {
			Calendar caleStart = CalendarUtil.getTheDayZero(CalendarUtil.parseYY_MM_DD(startTime));
			criteria.add(Restrictions.gt("arvlftTime", caleStart));
		}

		if (endTime != null && !"".equals(endTime)) {
			Calendar caleEnd = CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endTime));
			criteria.add(Restrictions.lt("arvlftTime", caleEnd));
		}

		super.findPageByCriteria(page, criteria);

		return page;
	}

	public YydShipArvlft getPreviousArvlft(YydShipArvlft shipArvlft) {
		if (shipArvlft == null)
			return null;

		Page<YydShipArvlft> page = new Page<YydShipArvlft>();
		page.setPageSize(1);
		page.setPageNo(1);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", shipArvlft.getMmsi()));
		criteria.add(Restrictions.lt("id", shipArvlft.getId()));

		criteria.addOrder(Order.desc("id"));

		this.findPageByCriteria(page, criteria);

		if (!page.getResult().isEmpty())
			return page.getResult().get(0);
		else
			return null;
	}

	public YydShipArvlft getNextArvlft(YydShipArvlft shipArvlft) {
		if (shipArvlft == null)
			return null;

		Page<YydShipArvlft> page = new Page<YydShipArvlft>();
		page.setPageSize(1);
		page.setPageNo(1);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", shipArvlft.getMmsi()));
		criteria.add(Restrictions.gt("id", shipArvlft.getId()));

		criteria.addOrder(Order.asc("id"));

		this.findPageByCriteria(page, criteria);

		if (!page.getResult().isEmpty())
			return page.getResult().get(0);
		else
			return null;
	}

	public List<YydShipArvlft> getLast10ShipArvlft(String mmsi) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", mmsi));

		Page<YydShipArvlft> page = new Page<YydShipArvlft>();
		page.setPageSize(10);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		super.findPageByCriteria(page, criteria);
		List<YydShipArvlft> yydShipArvlft = page.getResult();

		return yydShipArvlft;
	}

	public List<YydShipArvlft> getLast2ShipArvlft(String mmsi) {
		List<YydShipArvlft> yydShipArvlfts = new ArrayList<YydShipArvlft>();
		YydShipArvlft lastShipArvlft = this.getLastShipArvlft(mmsi);
		if (lastShipArvlft != null) {
			yydShipArvlfts.add(lastShipArvlft);
			YydShipArvlft previousArvlft = this.getPreviousArvlft(lastShipArvlft);
			if (previousArvlft != null)
				yydShipArvlfts.add(previousArvlft);
		}
		return yydShipArvlfts;
	}

	public YydShipArvlft getLastShipArvlft(String mmsi) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", mmsi));

		Page<YydShipArvlft> page = new Page<YydShipArvlft>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		super.findPageByCriteria(page, criteria);
		List<YydShipArvlft> yydShipArvlft = page.getResult();

		if (!yydShipArvlft.isEmpty())
			return yydShipArvlft.get(0);
		else
			return null;
	}

}
