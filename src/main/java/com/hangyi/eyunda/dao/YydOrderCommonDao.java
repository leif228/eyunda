package com.hangyi.eyunda.dao;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydOrderCommon;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.StringUtil;

@Repository
public class YydOrderCommonDao extends PageHibernateDao<YydOrderCommon, Long> {

	public Page<YydOrderCommon> getByUserId(Long userId, String startTime, String endTime, int pageNo, int pageSize) {
		Page<YydOrderCommon> page = new Page<YydOrderCommon>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.or(Restrictions.eq("ownerId", userId), Restrictions.eq("brokerId", userId),
				Restrictions.eq("masterId", userId), Restrictions.eq("handlerId", userId)));

		if (startTime != null && !"".equals(startTime)) {
			Calendar caleStart = CalendarUtil.getTheDayZero(CalendarUtil.parseYY_MM_DD(startTime));
			criteria.add(Restrictions.ge("createTime", caleStart));
		}

		if (endTime != null && !"".equals(endTime)) {
			Calendar caleEnd = CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endTime));
			criteria.add(Restrictions.le("createTime", caleEnd));
		}

		criteria.addOrder(Order.desc("createTime"));

		return super.findPageByCriteria(page, criteria);
	}

	public Page<YydOrderCommon> findOrderPage(int pageSize, int pageNo, String keyWords, String startDate,
			String endDate) {
		Page<YydOrderCommon> page = new Page<YydOrderCommon>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		String hql = "select y from YydOrderCommon y where 1 = 1";

		if (keyWords != null && !"".equals(keyWords)) {
			// 判断是否为合同号
			if (StringUtil.isNumeric(keyWords)) {
				hql += " and y.id = " + keyWords;
			} else {
				;
			}
		}

		if (startDate != null && !"".equals(startDate)) {
			hql += " and y.createTime > '" + startDate + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endDate)));
			hql += " and y.createTime <= '" + sEnd + "'";
		}

		hql += " order by y.createTime desc";

		super.findPage(page, hql);

		return page;
	}

	public YydOrderCommon getMyOrder(Long userId, Long id) {
		Criteria criteria = getSession().createCriteria(entityClass);

		// 代理人可见全部自己代理的合同，承运人、托运人不可见编辑中的合同
		criteria.add(Restrictions.or(Restrictions.eq("ownerId", userId), Restrictions.eq("brokerId", userId),
				Restrictions.eq("masterId", userId), Restrictions.eq("handlerId", userId)));
		criteria.add(Restrictions.eq("id", id));

		List<YydOrderCommon> orders = super.find(criteria);

		if (orders.isEmpty())
			return null;
		else
			return orders.get(0);
	}

	public List<YydOrderCommon> getByShipId(Long shipId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipId", shipId));

		@SuppressWarnings("unchecked")
		List<YydOrderCommon> orders = criteria.list();

		return orders;
	}

	public Long getMySignedOrderNum(Long userId) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.or(Restrictions.eq("ownerId", userId), Restrictions.eq("brokerId", userId),
				Restrictions.eq("masterId", userId), Restrictions.eq("handlerId", userId)));

		ProjectionList prolist = Projections.projectionList();
		prolist.add(Projections.alias(Projections.rowCount(), "orderNum"));

		criteria.setProjection(prolist);

		Object statOrderNum = (Object) criteria.uniqueResult();

		if (statOrderNum != null)
			return Long.parseLong(statOrderNum.toString());
		else
			return 0L;
	}

	public YydOrderCommon findFirstOrder() {
		Page<YydOrderCommon> page = new Page<YydOrderCommon>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.ASC);
		page.setOrderBy("createTime");

		super.findPage(page);
		List<YydOrderCommon> orders = page.getResult();

		if (!orders.isEmpty())
			return orders.get(0);
		else
			return null;
	}

	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> statOrderOnMonth(Calendar month) {
		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.ge("createTime", month));
		criteria.add(Restrictions.lt("createTime", CalendarUtil.addMonths(month, 1)));
		/*
		 * criteria.add(Restrictions.or(Restrictions.eq("state",
		 * OrderStateCode.endsign), Restrictions.eq("state",
		 * OrderStateCode.archive)));
		 */

		ProjectionList prolist = Projections.projectionList();

		prolist.add(Projections.alias(Projections.rowCount(), "sumOrderCount"));
		prolist.add(Projections.alias(Projections.sum("transFee"), "sumTransFee"));
		prolist.add(Projections.alias(Projections.sum("brokerFee"), "sumBrokerFee"));
		prolist.add(Projections.alias(Projections.sum("platFee"), "sumPlatFee"));

		criteria.setProjection(prolist);
		List<Object> statsShip = criteria.list();
		// 将结果集中的数据取出来，放进 List 集合中
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		if (!statsShip.isEmpty()) {
			for (Object obj : statsShip) {
				Object[] arr = (Object[]) obj;
				if (arr != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("sumOrderCount", (Long) arr[0]);
					map.put("sumTransFee", (Double) arr[1]);
					map.put("sumBrokerFee", (Double) arr[2]);
					map.put("sumPlatFee", (Double) arr[3]);
					result.add(map);
				}
			}
		}
		return result;
	}

}
