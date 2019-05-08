package com.hangyi.eyunda.service.hyquan.shipinfo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertRightsDao;
import com.hangyi.eyunda.data.shipinfo.ShpCertRightsData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertRights;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpCertRightsService extends BaseService<ShpCertRights, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertRightsDao certRightsDao;
	@Autowired
	private HyqUserService userService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpUserShipService userShipService;

	@Override
	public PageHibernateDao<ShpCertRights, Long> getDao() {
		return (PageHibernateDao<ShpCertRights, Long>) certRightsDao;
	}

	private ShpCertRightsData getData(ShpCertRights certRights) {
		if (certRights != null) {
			ShpCertRightsData data = new ShpCertRightsData();
			CopyUtil.copyProperties(data, certRights, new String[] { "createTime", "startDate", "endDate" });
			data.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(certRights.getCreateTime()));
			data.setStartDate(CalendarUtil.toYYYY_MM_DD(certRights.getStartDate()));
			data.setEndDate(CalendarUtil.toYYYY_MM_DD(certRights.getEndDate()));

			data.setUserData(userService.getById(certRights.getUserId()));
			data.setMasterData(userService.getById(certRights.getMasterId()));

			data.setShipInfoData(shipInfoService.getShipInfoDataById(certRights.getShipId()));
			data.setCertificateData(certificateService.getCertificateData(certRights.getCertId()));

			return data;
		}
		return null;
	}

	public List<ShpCertRightsData> getCertRightsDatas(Long userId, Long shipId) {

		List<ShpCertRightsData> datas = new ArrayList<ShpCertRightsData>();

		List<ShpCertRights> certRightss = certRightsDao.getByUserIdShipId(userId, shipId);

		for (ShpCertRights certRights : certRightss) {
			ShpCertRightsData data = this.getData(certRights);
			datas.add(data);
		}

		return datas;
	}

	public boolean saveCertRights(ShpCertSysCode certSys, Long userId, Long masterId, long shipId, long[] certIds,
			String sds, String eds) {
		try {
			if (userShipService.canModify(masterId, shipId, certSys)) {
				List<ShpCertRights> l = certRightsDao.getByUserIdShipId(userId, shipId, certSys);
				for (ShpCertRights certRights : l) {
					certRightsDao.delete(certRights);
				}
				
				for (int i = 0; i < certIds.length; i++) {
					ShpCertRights certRights = new ShpCertRights();
					
					certRights.setCertSys(certSys);
					certRights.setUserId(userId);
					certRights.setMasterId(masterId);
					certRights.setShipId(shipId);
					certRights.setCertId(certIds[i]);
					certRights.setStartDate(CalendarUtil.parseYYYY_MM_DD(sds));
					certRights.setEndDate(CalendarUtil.parseYYYY_MM_DD(eds));
					
					certRightsDao.save(certRights);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean saveCertRights(ShpCertSysCode certSys, Long userId, Long masterId, Long shipId, long[] certIds,
			String[] sds, String[] eds) {
		try {
			if (userShipService.canModify(masterId, shipId, certSys)) {
				List<ShpCertRights> l = certRightsDao.getByUserIdShipId(userId, shipId, certSys);
				for (ShpCertRights certRights : l) {
					certRightsDao.delete(certRights);
				}

				for (int i = 0; i < certIds.length; i++) {
					ShpCertRights certRights = new ShpCertRights();

					certRights.setCertSys(certSys);
					certRights.setUserId(userId);
					certRights.setMasterId(masterId);
					certRights.setShipId(shipId);
					certRights.setCertId(certIds[i]);
					certRights.setStartDate(CalendarUtil.parseYYYY_MM_DD(sds[i]));
					certRights.setEndDate(CalendarUtil.parseYYYY_MM_DD(eds[i]));

					certRightsDao.save(certRights);
				}
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
