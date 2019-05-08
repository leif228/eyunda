package com.hangyi.eyunda.service.hyquan.shipinfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertificateDao;
import com.hangyi.eyunda.data.shipinfo.ShpCertAttaData;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipinfo.ShpUserShipData;
import com.hangyi.eyunda.domain.enumeric.AuditStatusCode;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertificate;
import com.hangyi.eyunda.domain.shipinfo.ShpShipInfo;
import com.hangyi.eyunda.domain.shipinfo.ShpUserShip;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpCertificateService extends BaseService<ShpCertificate, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertificateDao certificateDao;
	@Autowired
	private ShpCertAttaService certAttaService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpUserShipService userShipService;

	@Override
	public PageHibernateDao<ShpCertificate, Long> getDao() {
		return (PageHibernateDao<ShpCertificate, Long>) certificateDao;
	}

	public ShpCertificateData getCertificateData(ShpCertificate certificate) {
		if (certificate != null) {
			ShpCertificateData certificateData = new ShpCertificateData();
			CopyUtil.copyProperties(certificateData, certificate,
					new String[] { "issueDate", "maturityDate", "remindDate", "createTime" });
			certificateData.setIssueDate(CalendarUtil.toYYYY_MM_DD(certificate.getIssueDate()));
			if(null != certificate.getMaturityDate())
				certificateData.setMaturityDate(CalendarUtil.toYYYY_MM_DD(certificate.getMaturityDate()));
			if(null != certificate.getRemindDate())
				certificateData.setRemindDate(CalendarUtil.toYYYY_MM_DD(certificate.getRemindDate()));
			certificateData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(certificate.getCreateTime()));

			if(null != certificate.getMaturityDate()){
				int diff = CalendarUtil.dayDiff(CalendarUtil.getTheDayZero(CalendarUtil.now()),
						CalendarUtil.getTheDayZero(certificate.getMaturityDate()));
				String warnCentent = "";
				if (diff > 0)
					warnCentent = "证书还差" + diff + "天到期";
				else if (diff < 0)
					warnCentent = "证书已经过期" + (-1 * diff) + "天";
				else
					warnCentent = "证书今天到期";
				certificateData.setWarnCentent(warnCentent);
			}

			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(certificate.getShipId());
			certificateData.setShipName(shipInfoData.getShipName());
			certificateData.setMmsi(shipInfoData.getMmsi());

			List<ShpCertAttaData> attaDatas = certAttaService.getCertAttaDatas(certificate.getId());
			certificateData.setCertAttaDatas(attaDatas);

			return certificateData;
		}
		return null;
	}

	public ShpCertificateData getCertificateData(ShpCertificate certificate, ShpShipInfo shipInfo) {
		if (certificate != null) {
			ShpCertificateData certificateData = new ShpCertificateData();
			CopyUtil.copyProperties(certificateData, certificate,
					new String[] { "issueDate", "maturityDate", "remindDate", "createTime" });
			certificateData.setIssueDate(CalendarUtil.toYYYY_MM_DD(certificate.getIssueDate()));
			if(null != certificate.getMaturityDate())
				certificateData.setMaturityDate(CalendarUtil.toYYYY_MM_DD(certificate.getMaturityDate()));
			if(null != certificate.getRemindDate())
				certificateData.setRemindDate(CalendarUtil.toYYYY_MM_DD(certificate.getRemindDate()));
			certificateData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(certificate.getCreateTime()));

			if(null != certificate.getMaturityDate()){
				int diff = CalendarUtil.dayDiff(CalendarUtil.getTheDayZero(CalendarUtil.now()),
						CalendarUtil.getTheDayZero(certificate.getMaturityDate()));
				String warnCentent = "";
				if (diff > 0)
					warnCentent = "证书还差" + diff + "天到期";
				else if (diff < 0)
					warnCentent = "证书已经过期" + (-1 * diff) + "天";
				else
					warnCentent = "证书今天到期";
				certificateData.setWarnCentent(warnCentent);
			}

			certificateData.setShipName(shipInfo.getShipName());
			certificateData.setMmsi(shipInfo.getMmsi());

			return certificateData;
		}
		return null;
	}

	public ShpCertificateData getCertificateData(Long certId) {
		ShpCertificate certificate = certificateDao.get(certId);
		if (certificate == null)
			return null;
		else
			return this.getCertificateData(certificate);
	}
	
	public long[] getCertificateIds(Long shipId) {
		List<ShpCertificate> certificates = certificateDao.getByShipId(shipId);
		long[] ids = new long[certificates.size()];
		for (int i=0;i<certificates.size();i++) {
			ids[i] = certificates.get(i).getId();
		}
		return ids;
	}

	public List<ShpCertificateData> getCertificateDatas(Long shipId) {
		List<ShpCertificateData> datas = new ArrayList<ShpCertificateData>();
		List<ShpCertificate> certificates = certificateDao.getByShipId(shipId);
		for (ShpCertificate certificate : certificates) {
			ShpCertificateData data = this.getCertificateData(certificate);
			datas.add(data);
		}
		return datas;
	}

	public Page<ShpCertificateData> getPageDataByKeywords(Long masterId, Page<ShpCertificateData> pageData,
			String keywords) {
		List<ShpCertificateData> datas = new ArrayList<ShpCertificateData>();

		Page<ShpCertificate> page = certificateDao.getPageByKeywords(masterId, pageData.getPageNo(),
				pageData.getPageSize(), keywords);
		for (ShpCertificate certificate : page.getResult()) {
			ShpCertificateData data = this.getCertificateData(certificate);
			datas.add(data);
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(datas);

		return pageData;
	}

	public Page<ShpCertificateData> getPageDataForMaster(Long masterId, Long shipId, CertTypeCode certType,
			Page<ShpCertificateData> pageData) {

		List<ShpCertificateData> datas = new ArrayList<ShpCertificateData>();

		Page<Object> page = certificateDao.getPageForMaster(masterId, shipId, certType, pageData.getPageNo(),
				pageData.getPageSize());

		if (!page.getResult().isEmpty()) {
			for (Object object : page.getResult()) {
				Object[] os = (Object[]) object;

				ShpCertificate certificate = (ShpCertificate) os[0];
				ShpShipInfo shipInfo = (ShpShipInfo) os[1];
				// ShpUserShip userShip = (ShpUserShip) os[2];

				ShpCertificateData data = this.getCertificateData(certificate, shipInfo);
				datas.add(data);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(datas);

		return pageData;
	}

	public boolean saveCertificate(ShpCertificateData certificateData) {
		try {
			ShpCertificate certificate = certificateDao.get(certificateData.getId());

			if (certificate == null) {
				certificate = new ShpCertificate();
			}

			CopyUtil.copyProperties(certificate, certificateData,
					new String[] { "issueDate", "maturityDate", "remindDate", "createTime" });
			certificate.setIssueDate(CalendarUtil.parseYYYY_MM_DD(certificateData.getIssueDate()));
			
			if("".equals(certificateData.getMaturityDate()))
				certificate.setMaturityDate(null);
			else
				certificate.setMaturityDate(CalendarUtil.parseYYYY_MM_DD(certificateData.getMaturityDate()));
			
			if("".equals(certificateData.getRemindDate()))
				certificate.setRemindDate(null);
			else
				certificate.setRemindDate(CalendarUtil.parseYYYY_MM_DD(certificateData.getRemindDate()));

			certificateDao.save(certificate);
			certificateData.setId(certificate.getId());

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean auditCertificate(Long id, AuditStatusCode status) throws Exception {
		ShpCertificate certificate = certificateDao.get(id);

		if (certificate != null) {
			if (status != AuditStatusCode.audit)
				certificate.setStatus(AuditStatusCode.unaudit);
			else
				certificate.setStatus(AuditStatusCode.audit);

			certificateDao.save(certificate);
			return true;
		}
		throw new Exception("错误！指定的证书记录已经不存在。");
	}

	public boolean deleteCertificate(Long id) throws Exception {
		ShpCertificate certificate = certificateDao.get(id);

		if (certificate != null) {
			List<ShpCertAttaData> certAttaDatas = certAttaService.getCertAttaDatas(certificate.getId());
			for (ShpCertAttaData certAttaData : certAttaDatas) {
				certAttaService.deleteCertAtta(certAttaData.getId());
			}
			certificate.setBeDeleted(YesNoCode.yes);
			certificateDao.save(certificate);
			return true;
		}
		throw new Exception("错误！指定的证书记录已经不存在。");
	}

	// 后台管理
	public Page<Map<String, Object>> getPageMap(Page<Map<String, Object>> pageData, ShpCertSysCode certSys, String mmsi,
			CertTypeCode certType, YesNoCode beDeleted) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Page<Object> page = certificateDao.getPage(pageData.getPageNo(), pageData.getPageSize(), certSys, mmsi,
				certType, beDeleted);

		if (!page.getResult().isEmpty()) {
			for (Object object : page.getResult()) {
				Object[] os = (Object[]) object;

				ShpCertificate cert = (ShpCertificate) os[0];
				ShpShipInfo ship = (ShpShipInfo) os[1];
				ShpUserShip us = (ShpUserShip) os[2];

				ShpCertificateData certData = this.getCertificateData(cert);
				ShpShipInfoData shipData = shipInfoService.getShipInfoData(ship);
				ShpUserShipData usData = userShipService.getUserShipData(us);

				Map<String, Object> mapData = new HashMap<String, Object>();
				mapData.put("cert", certData);
				mapData.put("ship", shipData);
				mapData.put("us", usData);

				list.add(mapData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(list);

		return pageData;
	}

}
