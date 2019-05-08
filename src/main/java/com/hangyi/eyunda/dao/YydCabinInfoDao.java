package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydCabinInfo;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydCabinInfoDao extends PageHibernateDao<YydCabinInfo, Long> {
	public YydCabinInfo getByCabin(WaresBigTypeCode waresBigType, WaresTypeCode waresType, CargoTypeCode cargoType,
			Long shipId, Long masterId) {

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("waresBigType", waresBigType));
		criteria.add(Restrictions.eq("waresType", waresType));
		criteria.add(Restrictions.eq("cargoType", cargoType));
		criteria.add(Restrictions.eq("shipId", shipId));
		criteria.add(Restrictions.eq("masterId", masterId));

		criteria.addOrder(Order.desc("createTime"));

		List<YydCabinInfo> orders = super.find(criteria);

		if (orders.isEmpty())
			return null;
		else
			return orders.get(0);
	}

	public Page<YydCabinInfo> getCabinPage(Long publisherId, int pageNo, int pageSize, String keyWords,
			String startDate, String endDate) {
		Page<YydCabinInfo> page = new Page<YydCabinInfo>();
		page.setPageNo(pageNo);
		page.setPageSize(pageSize);

		String hql = "select y from YydShip x, YydCabinInfo y where x.id = y.shipId and y.publisherId = " + publisherId;

		if (keyWords != null && !"".equals(keyWords)) {
			hql += " and (x.shipName like '%" + keyWords + "%' or x.mmsi like '%" + keyWords + "%')";
		}

		if (startDate != null && !"".equals(startDate)) {
			hql += " and y.createTime > '" + startDate + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endDate)));
			hql += " and y.createTime<='" + sEnd + "'";
		}

		hql += " order by y.createTime desc";

		super.findPage(page, hql);

		return page;
	}

	public Page<YydCabinInfo> getCabinHomePage(int pageNo, int pageSize, WaresBigTypeCode waresBigType,
			WaresTypeCode waresType, CargoTypeCode cargoType, String selPortNos, String keyWords) {

		Page<YydCabinInfo> page = new Page<YydCabinInfo>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);

		String hql = "select b from YydShip a, YydCabinInfo b where a.id = b.shipId and b.status = "
				+ ReleaseStatusCode.publish.ordinal();

		if (waresBigType != null) {
			hql += " and b.waresBigType = " + waresBigType.ordinal();
		}

		if (waresType != null) {
			hql += " and b.waresType = " + waresType.ordinal();
		}

		if (cargoType != null) {
			hql += " and b.cargoType = " + cargoType.ordinal();
		}

		if (selPortNos != null && !"".equals(selPortNos) && waresBigType != null) {
			if (waresBigType == WaresBigTypeCode.daily) {
				hql += " and b.ports like '%" + selPortNos + ":%'";
			} else if (waresBigType == WaresBigTypeCode.voyage) {
				hql += " and b.prices like '%" + selPortNos + ":%'";
			}
		}

		if (keyWords != null && !"".equals(keyWords)) {
			hql += " and (a.shipName like '%" + keyWords + "%' or a.mmsi like '%" + keyWords + "%')";
		}

		hql += " order by b.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

	public Page<YydCabinInfo> getCabinPageForManager(int pageSize, int pageNo, String keyWords, String startDate,
			String endDate) {
		Page<YydCabinInfo> page = new Page<YydCabinInfo>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);

		String hql = "select b from YydShip a, YydCabinInfo b where a.id = b.shipId ";

		if (keyWords != null && !"".equals(keyWords)) {
			hql += " and (a.shipName like '%" + keyWords + "%' or a.mmsi like '%" + keyWords + "%')";
		}

		if (startDate != null && !"".equals(startDate)) {
			hql += " and b.createTime > '" + startDate + "'";
		}

		if (endDate != null && !"".equals(endDate)) {
			String sEnd = CalendarUtil
					.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.getTheDayMidnight(CalendarUtil.parseYY_MM_DD(endDate)));
			hql += " and b.createTime<='" + sEnd + "'";
		}

		hql += " order by b.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

}
