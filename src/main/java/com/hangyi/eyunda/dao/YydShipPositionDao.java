package com.hangyi.eyunda.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain.YydShipPosition;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydShipPositionDao extends PageHibernateDao<YydShipPosition, Long> {

	public List<YydShipPosition> getByShipId(String shipID) {
		return super.findBy("cbZid", shipID);
	}
	
	@SuppressWarnings("static-access")
	public Page<YydShipPosition> findPage() {
		Page<YydShipPosition> page = new Page<YydShipPosition>();
		page.setPageSize(super.getTotalCount(super.createCriteria()));
		page.setPageNo(1);
		page.setOrderBy("id");
		page.setOrder(page.ASC);

		return super.getAll(page);
	}

	@SuppressWarnings("static-access")
	public Page<YydShipPosition> findPage(int pageSize, int pageNo, String parameter) {
		Page<YydShipPosition> page = new Page<YydShipPosition>();
		page.setPageSize(pageSize);
		page.setPageNo(pageNo);
		page.setOrder(page.ASC);
		page.setOrderBy(parameter);

		return super.getAll(page);
	}

	@SuppressWarnings("unchecked")
	public YydShipPosition getByShipId(Long shipId) {
		YydShipPosition yydShipPosition = new YydShipPosition();
		String sql = "select * from yyd_ship_position ysp where "
				+ "ysp.cb_zid="+shipId+" order by ysp.id desc limit 0,1";
		Query query = super.getSession().createSQLQuery(sql);
		List<YydShipPosition> yydShipPositions = query.list();
		if(!yydShipPositions.isEmpty())
			yydShipPosition = yydShipPositions.get(0);
		return yydShipPosition;
	}

	@SuppressWarnings("unchecked")
	public YydShipPosition getByPoint(String[] onePoint) {
		Criteria criteria = super.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("baiduLng", onePoint[0]));
		criteria.add(Restrictions.eq("baiduLat", onePoint[1]));
		List<YydShipPosition> yydShipPositions = criteria.list();
		YydShipPosition shipPosition = new YydShipPosition();
		if(!yydShipPositions.isEmpty())
			return shipPosition=yydShipPositions.get(0);
		return shipPosition;
	}

	@SuppressWarnings("unchecked")
	public List<YydShipPosition> getPositions(Long shipId, String startTime,String endTime) {
		Criteria criteria = super.getSession().createCriteria(entityClass);
		criteria.add(Restrictions.eq("cbZid", shipId.toString()));
		criteria.add(Restrictions.ge("gpsTime", CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(startTime)));
		criteria.add(Restrictions.le("gpsTime", CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(endTime)));
		
		List<YydShipPosition> yydShipPositions = criteria.list();
		return yydShipPositions;
	}

}
