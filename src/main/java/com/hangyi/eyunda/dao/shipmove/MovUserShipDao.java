package com.hangyi.eyunda.dao.shipmove;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.shipmove.MovUserShip;
import com.hangyi.eyunda.util.Constants;

@Repository
public class MovUserShipDao extends PageHibernateDao<MovUserShip, Long> {

	public MovUserShip getByUserShip(Long userId, String mmsi) {

		String hql = "select a from MovUserShip a where a.userId = " + userId + " and a.mmsi = '" + mmsi + "'";

		List<MovUserShip> userShips = super.find(hql);

		if (!userShips.isEmpty())
			return userShips.get(0);
		else
			return null;
	}

	public List<MovUserShip> getListByMmsi(String mmsi) {

		String hql = "select a from MovUserShip a where a.mmsi = '" + mmsi + "'";

		List<MovUserShip> userShips = super.find(hql);

		return userShips;
	}

	public MovUserShip getModifyByMmsi(String mmsi) {
		String hql = "select a from MovUserShip a where a.mmsi = '" + mmsi + "' and rights = "
				+ ShpRightsCode.managedShip.ordinal();

		List<MovUserShip> userShips = super.find(hql);
		if (userShips.isEmpty())
			return null;
		else
			return userShips.get(0);
	}

	public Page<MovUserShip> getPageByUserId(Long userId, String keywords, int pageNo, int pageSize) {
		Page<MovUserShip> page = new Page<MovUserShip>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from MovUserShip a where a.userId = " + userId;

		if (keywords != null && !"".equals(keywords))
			hql += " and a.mmsi like '%" + keywords + "%'";

		hql += " order by a.createTime desc";

		super.findPage(page, hql);

		return page;
	}

}
