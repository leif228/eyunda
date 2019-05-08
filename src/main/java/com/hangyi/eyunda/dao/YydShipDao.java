package com.hangyi.eyunda.dao;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShip;
import com.hangyi.eyunda.domain.enumeric.ShipStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;

@Repository
public class YydShipDao extends PageHibernateDao<YydShip, Long> {

	// 查询代理人代理的船舶
	public Page<YydShip> getShipPage(Long compId, String keyWords, int pageNo, int pageSize, String mmsi, Long deptId,
			Long masterId) {
		Page<YydShip> page = new Page<YydShip>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC);
		page.setOrderBy("d.createTime");

		String hql = "select distinct d from YydCompany a, YydDepartment b, YydUserDept c, YydShip d where a.id = "
				+ compId + " and b.deptType = " + UserPrivilegeCode.master.ordinal()
				+ " and a.id = b.compId and b.id = c.deptId and c.userId = d.masterId";

		if (deptId != null && deptId > 0)
			hql += " and b.id = " + deptId;

		if (masterId != null && masterId > 0)
			hql += " and d.masterId = " + masterId;

		if (mmsi != null && !"".equals(mmsi))
			hql += " and d.mmsi ='" + mmsi + "'";

		if (keyWords != null && !"".equals(keyWords))
			hql += " and (d.shipName like '%" + keyWords + "%' or d.mmsi like '%" + keyWords + "%')";

		super.findPage(page, hql);

		return page;
	}

	public String getSpcShipCode(String typeCode) {

		Criteria criteria = getSession().createCriteria(entityClass);
		criteria.add(Restrictions.like("shipCode", typeCode, MatchMode.START));
		criteria.addOrder(Order.desc("shipCode"));

		@SuppressWarnings("unchecked")
		List<YydShip> ships = criteria.list();
		if (!ships.isEmpty()) {
			YydShip lastShip = ships.get(0);
			Long nextCode = Long.parseLong(lastShip.getShipCode().split("_")[1]) + 1;
			if (nextCode < 10)
				return typeCode + "_000" + nextCode;
			else if (nextCode < 100)
				return typeCode + "_00" + nextCode;
			else if (nextCode < 1000)
				return typeCode + "_0" + nextCode;
			else
				return typeCode + "_" + nextCode;
		} else {
			return typeCode + "_0001";
		}
	}

	// 总上线船舶数量统计
	public int statShipOnMonth(Calendar month) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.lt("releaseTime", CalendarUtil.addMonths(month, 1)));
		criteria.add(Restrictions.eq("shipStatus", ShipStatusCode.publish));

		ProjectionList prolist = Projections.projectionList();
		prolist.add(Projections.alias(Projections.rowCount(), "sumShip")); // 新上线船舶数

		criteria.setProjection(prolist);

		Object statShip = (Object) criteria.uniqueResult();

		// 将结果集中的数据取出来，放进 Map集合中
		int sumShip = 0;
		if (statShip != null) {
			sumShip = Integer.parseInt(statShip.toString());
		}
		return sumShip;
	}

	// 新上线船舶数量统计
	public int statUpShipOnMonth(Calendar month) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.ge("releaseTime", month));
		criteria.add(Restrictions.lt("releaseTime", CalendarUtil.addMonths(month, 1)));
		criteria.add(Restrictions.eq("shipStatus", ShipStatusCode.publish));

		ProjectionList prolist = Projections.projectionList();
		prolist.add(Projections.alias(Projections.rowCount(), "sumShip")); // 总上线船舶

		criteria.setProjection(prolist);

		Object statShip = (Object) criteria.uniqueResult();

		// 将结果集中的数据取出来，放进 Map集合中
		int sumShip = 0;
		if (statShip != null) {
			sumShip = Integer.parseInt(statShip.toString());
		}
		return sumShip;
	}

	public Page<YydShip> findByNameOrMmsi(int pageNo, String nameOrMmsi, ShipStatusCode statusCode) {
		Page<YydShip> page = new Page<YydShip>();
		page.setPageNo(pageNo);
		page.setOrder(Page.DESC);
		page.setOrderBy("pointCount");

		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.ne("shipStatus", ShipStatusCode.edit));

		if (nameOrMmsi != null && !"".equals(nameOrMmsi))
			criteria.add(Restrictions.or(Restrictions.like("shipName", nameOrMmsi, MatchMode.ANYWHERE),
					Restrictions.like("mmsi", nameOrMmsi, MatchMode.ANYWHERE)));

		if (statusCode != null)
			criteria.add(Restrictions.eq("shipStatus", statusCode));

		return findPageByCriteria(page, criteria);
	}

	public YydShip findByMmsi(String mmsi) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("mmsi", mmsi));

		List<YydShip> ships = super.find(criteria);
		if (ships.isEmpty())
			return null;
		else
			return ships.get(0);
	}

	public YydShip getShipByShipName(String shipName) {
		Criteria criteria = getSession().createCriteria(entityClass);

		criteria.add(Restrictions.eq("shipName", shipName));

		List<YydShip> ships = super.find(criteria);
		if (ships.isEmpty())
			return null;
		else
			return ships.get(0);
	}

	public YydShip findFirstShip() {
		Page<YydShip> page = new Page<YydShip>();
		page.setPageSize(1);
		page.setPageNo(1);
		page.setOrder(Page.ASC);
		page.setOrderBy("createTime");

		super.findPage(page);
		List<YydShip> yydUserInfos = page.getResult();

		if (!yydUserInfos.isEmpty())
			return yydUserInfos.get(0);
		else
			return null;
	}

	// 查询代理人公司代理的所有船舶
	public List<YydShip> getShipsByCompId(Long compId) {
		Page<YydShip> page = new Page<YydShip>();
		page.setPageSize(Constants.ALL_SIZE);
		page.setPageNo(1);

		String hql = "select distinct d from YydCompany a, YydDepartment b, YydUserDept c, YydShip d where a.id = "
				+ compId + " and b.deptType = " + UserPrivilegeCode.master.ordinal()
				+ " and a.id = b.compId and b.id = c.deptId and c.userId = d.masterId order by d.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page.getResult();
	}

}
