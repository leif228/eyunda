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

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertShowRecordDao;
import com.hangyi.eyunda.data.shipinfo.ShpCertShowRecordData;
import com.hangyi.eyunda.domain.shipinfo.ShpCertShowRecord;
import com.hangyi.eyunda.domain.shipinfo.ShpCertificate;
import com.hangyi.eyunda.domain.shipinfo.ShpShipInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpCertShowRecordService extends BaseService<ShpCertShowRecord, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertShowRecordDao certShowRecordDao;
	@Autowired
	private HyqUserService userService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpUserShipService userShipService;

	@Override
	public PageHibernateDao<ShpCertShowRecord, Long> getDao() {
		return (PageHibernateDao<ShpCertShowRecord, Long>) certShowRecordDao;
	}

	public ShpCertShowRecordData getData(ShpCertShowRecord certShowRecord, ShpCertificate certificate,
			ShpShipInfo shipInfo) {
		if (certShowRecord != null) {
			ShpCertShowRecordData data = new ShpCertShowRecordData();
			CopyUtil.copyProperties(data, certShowRecord, new String[] { "createTime" });
			data.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(certShowRecord.getCreateTime()));

			data.setUserData(userService.getById(certShowRecord.getUserId()));
			data.setCertificateData(certificateService.getCertificateData(certificate));
			data.setShipInfoData(shipInfoService.getShipInfoData(shipInfo));

			return data;
		}
		return null;
	}

	public Page<ShpCertShowRecordData> getPageData(Long masterId, String keywords,
			Page<ShpCertShowRecordData> pageData) {

		List<ShpCertShowRecordData> datas = new ArrayList<ShpCertShowRecordData>();

		Page<Object> page = certShowRecordDao.getPage(masterId, keywords, pageData.getPageNo(), pageData.getPageSize());

		if (!page.getResult().isEmpty()) {
			for (Object object : page.getResult()) {
				Object[] os = (Object[]) object;

				ShpCertShowRecord certShowRecord = (ShpCertShowRecord) os[0];
				ShpCertificate certificate = (ShpCertificate) os[1];
				ShpShipInfo shipInfo = (ShpShipInfo) os[2];
				// ShpUserShip userShip = (ShpUserShip) os[3];

				ShpCertShowRecordData data = this.getData(certShowRecord, certificate, shipInfo);
				datas.add(data);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(datas);

		return pageData;
	}

	public boolean saveRecord(ShpCertShowRecordData recordData) {
		try {
			// 自己看自己管理的船舶证书就不必记录了
			if (!userShipService.canModify(recordData.getUserId(), recordData.getShipId(), recordData.getCertSys())) {
				ShpCertShowRecord record = new ShpCertShowRecord();
				CopyUtil.copyProperties(record, recordData, new String[] { "createTime" });
				certShowRecordDao.save(record);
			}
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
