package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertShowRecord;
import com.hangyi.eyunda.util.Constants;

@Repository
public class ShpCertShowRecordDao extends PageHibernateDao<ShpCertShowRecord, Long> {

	public Page<Object> getPage(Long masterId, String keywords, int pageNo, int pageSize, ShpCertSysCode... certSys) {
		Page<Object> page = new Page<Object>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a, b, c, d from ShpCertShowRecord a, ShpCertificate b, ShpShipInfo c, ShpUserShip d";
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
