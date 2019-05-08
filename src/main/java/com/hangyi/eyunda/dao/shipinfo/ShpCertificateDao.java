package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertificate;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;

@Repository
public class ShpCertificateDao extends PageHibernateDao<ShpCertificate, Long> {

	public Page<ShpCertificate> getPageByKeywords(Long masterId, int pageNo, int pageSize, String keywords) {
		Page<ShpCertificate> page = new Page<ShpCertificate>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from ShpCertificate a,ShpShipInfo b,ShpUserShip c where a.shipId = b.id and c.shipId = b.id";
		hql += " and c.certSys = " + ShpCertSysCode.hyq.ordinal();
		hql += " and c.userId = " + masterId;
		hql += " and c.rights = " + ShpRightsCode.managedShip.ordinal();
		hql += " and a.beDeleted = " + YesNoCode.no.ordinal();

		if (keywords != null && !"".equals(keywords)) {
			hql += " and (b.shipName like '%" + keywords + "%' or b.mmsi like '%" + keywords + "%'";
			List<CertTypeCode> ctcs = CertTypeCode.getByKeywords(keywords);
			for (CertTypeCode ctc : ctcs) {
				hql += " or a.certType = " + ctc.ordinal();
			}
			hql += ")";
		}

		super.findPage(page, hql);
		return page;
	}

	public Page<Object> getPageForMaster(Long masterId, Long shipId, CertTypeCode certType, int pageNo, int pageSize,
			ShpCertSysCode... certSys) {
		Page<Object> page = new Page<Object>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select b, c, d from ShpCertificate b, ShpShipInfo c, ShpUserShip d";
		hql += " where b.shipId = c.id and c.id = d.shipId and b.beDeleted = " + YesNoCode.no.ordinal();
		hql += " and b.remindDate <= '" + CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()) + "'";

		if (certSys != null && certSys.length > 0) {
			switch (certSys[0]) {
			case hyq:
				hql += " and d.certSys = " + ShpCertSysCode.hyq.ordinal();
				break;
			case scf:
				hql += " and d.certSys = " + ShpCertSysCode.scf.ordinal();
				break;
			default:
				;
			}
		} else {
			hql += " and d.certSys = " + ShpCertSysCode.hyq.ordinal();
		}

		if (masterId != null && masterId > 0)
			hql += " and d.userId = " + masterId + " and d.rights = " + ShpRightsCode.managedShip.ordinal();

		if (shipId != null && shipId > 0)
			hql += " and c.id = " + shipId;

		if (certType != null)
			hql += " and b.certType = " + certType.ordinal();

		hql += " order by b.maturityDate asc";

		super.findPage(page, hql);

		return page;
	}

	public Page<Object> getPage(int pageNo, int pageSize, ShpCertSysCode certSys, String mmsi, CertTypeCode certType,
			YesNoCode beDeleted) {
		Page<Object> page = new Page<Object>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a, b, c from ShpCertificate a,ShpShipInfo b,ShpUserShip c where a.shipId = b.id and c.shipId = b.id";
		hql += " and c.rights = " + ShpRightsCode.managedShip.ordinal();

		if (certSys != null)
			hql += " and c.certSys = " + certSys.ordinal();

		if (mmsi != null && !"".equals(mmsi))
			hql += " and (b.shipName like '%" + mmsi + "%' or b.mmsi like '%" + mmsi + "%')";

		if (certType != null)
			hql += " and a.certType = " + certType.ordinal();

		if (beDeleted != null)
			hql += " and a.beDeleted = " + beDeleted.ordinal();

		super.findPage(page, hql);
		return page;
	}

	public List<ShpCertificate> getByShipId(Long shipId) {

		String hql = "select a from ShpCertificate a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.shipId = "
				+ shipId + " order by a.certType asc";

		List<ShpCertificate> certs = super.find(hql);

		return certs;
	}

	public long statCertCount(Long shipId) {
		String hql = "select count(*) from shp_certificate where be_deleted = " + YesNoCode.no.ordinal()
				+ " and ship_id = " + shipId;

		Object object = (Object) super.findSQLUnique(hql);

		if (object != null)
			return Long.parseLong(object.toString());
		else
			return 0L;
	}

}
