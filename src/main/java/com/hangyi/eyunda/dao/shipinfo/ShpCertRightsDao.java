package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertRights;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;

@Repository
public class ShpCertRightsDao extends PageHibernateDao<ShpCertRights, Long> {

	public ShpCertRights getByCertId(Long userId, Long shipId, Long certId, ShpCertSysCode... certSys) {

		String hql = "select a from ShpCertRights a where a.userId = " + userId + " and a.shipId = " + shipId
				+ " and a.certId = " + certId;

		String strNow = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.now());
		hql += " and a.startDate <= '" + strNow + "'";
		hql += " and a.endDate >= '" + strNow + "'";

		if (certSys != null && certSys.length > 0) {
			switch (certSys[0]) {
			case hyq:
				hql += " and a.certSys = " + ShpCertSysCode.hyq.ordinal();
				break;
			case scf:
				hql += " and a.certSys = " + ShpCertSysCode.scf.ordinal();
				break;
			default:
				;
			}
		} else {
			hql += " and a.certSys = " + ShpCertSysCode.hyq.ordinal();
		}

		List<ShpCertRights> l = super.find(hql, new Object[] {});
		if (!l.isEmpty()) {
			return l.get(0);
		} else {
			return null;
		}
	}

	public List<ShpCertRights> getByUserIdShipId(Long userId, Long shipId, ShpCertSysCode... certSys) {

		String hql = "select a from ShpCertRights a where a.userId = " + userId + " and a.shipId = " + shipId;

		if (certSys != null && certSys.length > 0) {
			switch (certSys[0]) {
			case hyq:
				hql += " and a.certSys = " + ShpCertSysCode.hyq.ordinal();
				break;
			case scf:
				hql += " and a.certSys = " + ShpCertSysCode.scf.ordinal();
				break;
			default:
				;
			}
		} else {
			hql += " and a.certSys = " + ShpCertSysCode.hyq.ordinal();
		}

		List<ShpCertRights> l = super.find(hql, new Object[] {});

		return l;
	}

	public Page<Object> getPage(Long masterId, String keywords, int pageNo, int pageSize, ShpCertSysCode... certSys) {
		Page<Object> page = new Page<Object>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a, b, c, d from ShpCertRights a, ShpCertificate b, ShpShipInfo c, ShpUserShip d";
		hql += " where a.certId = b.id and b.shipId = c.id and c.id = d.shipId";

		if (certSys != null && certSys.length > 0) {
			switch (certSys[0]) {
			case hyq:
				hql += " and a.certSys = " + ShpCertSysCode.hyq.ordinal();
				break;
			case scf:
				hql += " and a.certSys = " + ShpCertSysCode.scf.ordinal();
				break;
			default:
				;
			}
		} else {
			hql += " and a.certSys = " + ShpCertSysCode.hyq.ordinal();
		}

		if (masterId != null && masterId > 0)
			hql += " and d.userId = " + masterId + " and d.rights = " + ShpRightsCode.managedShip.ordinal();

		if (keywords != null && !"".equals(keywords)) {
			hql += " and (c.shipName like '%" + keywords + "%' or c.mmsi like '%" + keywords + "%'";
			List<CertTypeCode> ctcs = CertTypeCode.getByKeywords(keywords);
			for (CertTypeCode ctc : ctcs) {
				hql += " or b.certType = " + ctc.ordinal();
			}
			hql += ")";
		}

		hql += " order by a.createTime desc";

		super.findPage(page, hql);

		return page;
	}

}
