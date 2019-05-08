package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpShipInfo;
import com.hangyi.eyunda.util.Constants;

@Repository
public class ShpShipInfoDao extends PageHibernateDao<ShpShipInfo, Long> {

	public Page<ShpShipInfo> getSearchPageByKeywords(String keywords, int pageNo, int pageSize) {
		Page<ShpShipInfo> page = new Page<ShpShipInfo>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from ShpShipInfo a, ShpUserShip b where a.id = b.shipId and b.certSys = "
				+ ShpCertSysCode.hyq.ordinal() + " and b.rights = " + ShpRightsCode.managedShip.ordinal()
				+ " and a.beDeleted = " + YesNoCode.no.ordinal();

		if (keywords != null && !"".equals(keywords))
			hql += " and (a.masterName like '%" + keywords + "%' or a.masterTel = '" + keywords
					+ "' or a.shipName like '%" + keywords + "%' or a.mmsi like '%" + keywords + "%')";

		hql += " order by a.createTime desc";

		super.findPage(page, hql, new Object[] {});

		return page;
	}

	public ShpShipInfo getByMmsi(String mmsi, ShpCertSysCode certSys) {

		String hql = "select a from ShpShipInfo a, ShpUserShip b where a.id = b.shipId and b.certSys = "
				+ certSys.ordinal() + " and b.rights = " + ShpRightsCode.managedShip.ordinal() + " and a.beDeleted = "
				+ YesNoCode.no.ordinal() + " and a.mmsi = '" + mmsi + "'";

		List<ShpShipInfo> l = super.find(hql, new Object[] {});

		if (!l.isEmpty())
			return l.get(0);
		else
			return null;
	}

	public ShpShipInfo getByMmsiOrShipName(String mmsi, ShpCertSysCode certSys) {

		String hql = "select a from ShpShipInfo a, ShpUserShip b where a.id = b.shipId and b.certSys = "
				+ certSys.ordinal() + " and b.rights = " + ShpRightsCode.managedShip.ordinal() + " and a.beDeleted = "
				+ YesNoCode.no.ordinal() + " and (a.mmsi = '" + mmsi + "' or a.shipName = '" + mmsi + "')";

		List<ShpShipInfo> l = super.find(hql, new Object[] {});

		if (!l.isEmpty())
			return l.get(0);
		else
			return null;
	}

}
