package com.hangyi.eyunda.dao.shipinfo;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpUserShip;
import com.hangyi.eyunda.util.Constants;

@Repository
public class ShpUserShipDao extends PageHibernateDao<ShpUserShip, Long> {

	public List<ShpUserShip> getListByShipId(Long shipId, ShpCertSysCode... certSys) {

		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.shipId = "
				+ shipId;

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

		List<ShpUserShip> userShips = super.find(hql);

		return userShips;
	}

	public Page<ShpUserShip> getFavoritePageByUserId(Long userId, int pageNo, int pageSize, ShpCertSysCode... certSys) {
		Page<ShpUserShip> page = new Page<ShpUserShip>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.userId = "
				+ userId + " and a.rights >= " + ShpRightsCode.favoriteShip.ordinal();

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

		hql += " order by a.createTime desc";

		super.findPage(page, hql);

		return page;
	}
	
	public ShpUserShip getByUserShipFS(Long userId, Long shipId, ShpCertSysCode... certSys) {
		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.userId = "
				+ userId + " and a.shipId = " + shipId;
		
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
		
		hql += " and ( a.rights = " + ShpRightsCode.favoriteShip.ordinal();
		hql += " or  a.rights = " + ShpRightsCode.seeCertRights.ordinal();
		hql += " ) ";
		
		List<ShpUserShip> userShips = super.find(hql);
		
		if (!userShips.isEmpty())
			return userShips.get(0);
		else
			return null;
	}
	
	public ShpUserShip getByUserShipManagedShip(Long userId, Long shipId, ShpCertSysCode... certSys) {
		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.userId = "
				+ userId + " and a.shipId = " + shipId;
		
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
		
		hql += " and a.rights = " + ShpRightsCode.managedShip.ordinal();
		
		List<ShpUserShip> userShips = super.find(hql);
		
		if (!userShips.isEmpty())
			return userShips.get(0);
		else
			return null;
	}

	public ShpUserShip getByUserShip(Long userId, Long shipId, ShpCertSysCode... certSys) {
		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.userId = "
				+ userId + " and a.shipId = " + shipId;

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

		List<ShpUserShip> userShips = super.find(hql);

		if (!userShips.isEmpty())
			return userShips.get(0);
		else
			return null;
	}

	public Page<ShpUserShip> getMyManagePageByUserId(Long userId, int pageNo, int pageSize, ShpCertSysCode... certSys) {
		Page<ShpUserShip> page = new Page<ShpUserShip>();
		page.setPageSize(pageSize <= 0 ? Constants.ALL_SIZE : pageSize);
		page.setPageNo(pageNo);

		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.userId = "
				+ userId + " and a.rights = " + ShpRightsCode.managedShip.ordinal();

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

		hql += " order by a.createTime desc";

		super.findPage(page, hql);

		return page;
	}

	public List<ShpUserShip> findMyShips(Long userId, ShpCertSysCode... certSys) {

		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.userId = "
				+ userId;

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
		
		hql += " and a.rights = " + ShpRightsCode.managedShip.ordinal();

		List<ShpUserShip> userShips = super.find(hql);

		return userShips;
	}

	public List<ShpUserShip> findSeeCertRights(Long shipId, ShpCertSysCode... certSys) {

		String hql = "select a from ShpUserShip a where a.beDeleted = " + YesNoCode.no.ordinal() + " and a.shipId = "
				+ shipId;

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
		
		hql += " and a.rights = " + ShpRightsCode.seeCertRights.ordinal();

		List<ShpUserShip> userShips = super.find(hql);

		return userShips;
	}

}
