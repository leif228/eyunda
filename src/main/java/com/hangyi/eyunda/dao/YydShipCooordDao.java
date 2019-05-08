package com.hangyi.eyunda.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.domain2.YydShipCooord;
import com.hangyi.eyunda.util.CalendarUtil;

@Repository
public class YydShipCooordDao extends PageHibernateDao2<YydShipCooord, Long> {

	public YydShipCooord findOneShipCooord(String mmsi) {
		String currTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.addHours(CalendarUtil.now(), -1));

		String hql = " from YydShipCooord where posTime > '" + currTime + "'";
		hql += " and mmsi = '" + mmsi + "'";
		hql += " order by posTime desc";

		List<YydShipCooord> shipCooords = super.find(hql);
		if (!shipCooords.isEmpty())
			return shipCooords.get(0);
		else
			return null;
	}

	public List<YydShipCooord> findShipCooords(String includeMmsis) {
		List<YydShipCooord> shipCooords = new ArrayList<YydShipCooord>();
		if (includeMmsis != null && !"".equals(includeMmsis)) {
			String[] ss = includeMmsis.split(",");
			for (String mmsi : ss) {
				YydShipCooord shipCooord = this.findOneShipCooord(mmsi);
				if (shipCooord != null)
					shipCooords.add(shipCooord);
			}
		}
		return shipCooords;
	}

	public List<YydShipCooord> findShipSailLine(String mmsi, String startTime, String endTime) {
		String hql = " from YydShipCooord where mmsi = '" + mmsi + "'";
		if (startTime != null && !"".equals(startTime))
			hql += " and posTime > '" + startTime + "'";
		if (endTime != null && !"".equals(endTime))
			hql += " and posTime < '" + endTime + "'";
		hql += " order by posTime asc";

		List<YydShipCooord> shipCooords = super.find(hql);
		return shipCooords;
	}

	public YydShipCooord findOneShipCooordByPosTime(String mmsi, String posTime) {
		String hql = " from YydShipCooord where mmsi = '" + mmsi + "' and posTime='" + posTime + "'";

		List<YydShipCooord> shipCooords = super.find(hql);

		if (!shipCooords.isEmpty())
			return shipCooords.get(0);
		else
			return null;
	}

}
