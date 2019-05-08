package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydWalletSettle;
import com.hangyi.eyunda.service.EnumConst.RecentPeriodCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydWalletSettleDao extends PageHibernateDao<YydWalletSettle, Long> {

	public YydWalletSettle getTodayLastOne(Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));

		Page<YydWalletSettle> page = new Page<YydWalletSettle>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		super.findPageByCriteria(page, criteria);
		List<YydWalletSettle> settles = page.getResult();

		if (settles.isEmpty())
			return null;
		else
			return settles.get(0);
	}

	public Double sumTodayTrade(Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.eq("fetchableMoney", -1.00D));

		criteria.addOrder(Order.desc("id"));

		List<YydWalletSettle> settles = super.find(criteria);

		Double todayFetchMoney = 0.00D;
		for (YydWalletSettle settle : settles) {
			todayFetchMoney += settle.getMoney();
		}

		return todayFetchMoney;
	}

	public Double getFetchableMoney(Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("userId", userId));
		criteria.add(Restrictions.ne("fetchableMoney", -1.00D));

		Page<YydWalletSettle> page = new Page<YydWalletSettle>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		super.findPageByCriteria(page, criteria);
		List<YydWalletSettle> settles = page.getResult();

		if (!settles.isEmpty()) {
			Double yesterdayFetchableMoney = settles.get(0).getFetchableMoney();
			Double todayTradeMoney = this.sumTodayTrade(userId);
			Double todayFetchableMoney = yesterdayFetchableMoney;
			if (todayTradeMoney < 0)
				todayFetchableMoney = yesterdayFetchableMoney + todayTradeMoney;
			return todayFetchableMoney;
		}
		return 0.00D;
	}

	public Page<YydWalletSettle> findByUserId(Long userId, int pageNo, int pageSize, String startTime, String endTime) {
		Page<YydWalletSettle> page = new Page<YydWalletSettle>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC);
		page.setOrderBy("id");

		Criteria criteria = this.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("userId", userId));

		if (startTime != null && !"".equals(startTime))
			criteria.add(
					Restrictions.ge("createTime", CalendarUtil.getTheDayZero(CalendarUtil.parseYY_MM_DD(startTime))));
		if (endTime != null && !"".equals(endTime))
			criteria.add(
					Restrictions.le("createTime", CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endTime))));

		return super.findPageByCriteria(page, criteria);
	}

	@SuppressWarnings("unchecked")
	public Page<Long> findUserByTerm(Long userId, RecentPeriodCode termCode, int pageNo, int pageSize) {
		Page<Long> page = new Page<Long>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);

		String hql = "select distinct y.userId from YydWalletSettle y where y.userId > 0";

		if (userId != 0L)
			hql += " and y.userId = " + userId;

		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		Calendar endTime = Calendar.getInstance();
		Calendar startTime = CalendarUtil.addDays(endTime, -termCode.getTotalDays());
		hql += " and y.createTime <= '" + CalendarUtil.toYYYY_MM_DD_HH_MM_SS(endTime) + "'";
		hql += " and y.createTime >= '" + CalendarUtil.toYYYY_MM_DD_HH_MM_SS(startTime) + "'";

		hql += " order by y.userId asc";

		return super.findPage(page, hql, new Object[] {});
	}

	public List<YydWalletSettle> findSettleByUserId(Long userId, RecentPeriodCode termCode) {
		String hql = "select y from YydWalletSettle y where y.userId = " + userId;

		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		Calendar endTime = Calendar.getInstance();
		Calendar startTime = CalendarUtil.addDays(endTime, -termCode.getTotalDays());
		hql += " and y.createTime <= " + "'" + CalendarUtil.toYYYY_MM_DD_HH_MM_SS(endTime) + "'";
		hql += " and y.createTime >= " + "'" + CalendarUtil.toYYYY_MM_DD_HH_MM_SS(startTime) + "'";

		hql += " order by y.id desc";

		return super.find(hql);
	}

}
