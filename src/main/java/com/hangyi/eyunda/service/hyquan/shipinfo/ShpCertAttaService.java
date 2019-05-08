package com.hangyi.eyunda.service.hyquan.shipinfo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertAttaDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertificateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpShipInfoDao;
import com.hangyi.eyunda.data.shipinfo.ShpCertAttaData;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertAtta;
import com.hangyi.eyunda.domain.shipinfo.ShpCertificate;
import com.hangyi.eyunda.domain.shipinfo.ShpShipInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpCertAttaService extends BaseService<ShpCertAtta, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertAttaDao shpCertAttaDao;
	@Autowired
	private ShpCertificateDao shpCertificateDao;
	@Autowired
	private ShpShipInfoDao shpShipInfoDao;

	@Override
	public PageHibernateDao<ShpCertAtta, Long> getDao() {
		return (PageHibernateDao<ShpCertAtta, Long>) shpCertAttaDao;
	}

	public ShpCertAttaData getCertAttaData(ShpCertAtta atta) {
		if (atta != null) {
			ShpCertAttaData attaData = new ShpCertAttaData();
			CopyUtil.copyProperties(attaData, atta);
			if(attaData.getSmallImgUrl()==null||"".endsWith(attaData.getSmallImgUrl()))
				attaData.setSmallImgUrl(attaData.getUrl());
			if(attaData.getShowImgUrl()==null||"".endsWith(attaData.getShowImgUrl()))
				attaData.setShowImgUrl(attaData.getUrl());
			attaData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(atta.getCreateTime()));

			return attaData;
		}
		return null;
	}

	public ShpCertAttaData getCertAttaData(Long attaId) {
		ShpCertAtta atta = shpCertAttaDao.get(attaId);
		if (atta == null)
			return null;
		else
			return this.getCertAttaData(atta);
	}

	public List<ShpCertAttaData> getCertAttaDatas(Long certId) {
		List<ShpCertAttaData> attaDatas = new ArrayList<ShpCertAttaData>();
		try {
			List<ShpCertAtta> attas = shpCertAttaDao.findByCertId(certId);
			for (ShpCertAtta atta : attas) {
				ShpCertAttaData attaData = this.getCertAttaData(atta);
				attaDatas.add(attaData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return attaDatas;
	}

	public ShpCertAttaData[] saveCertAtta(Long certId, MultipartFile[] mpfs) throws Exception {
		ShpCertificate shpCertificate = shpCertificateDao.get(certId);
		Long shipId = shpCertificate.getShipId();
		CertTypeCode certType = shpCertificate.getCertType();
		ShpShipInfo shpShipInfo = shpShipInfoDao.get(shipId);

		// 如果上传了
		if (mpfs != null && mpfs.length > 0) {
			ShpCertAttaData[] ds = new ShpCertAttaData[mpfs.length];
			int startNo = shpCertAttaDao.getImageNo(certId);
			for (int i = 0; i < mpfs.length; i++) {
				MultipartFile mpf = mpfs[i];
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getShipDir(shpShipInfo.getMmsi()) + "/" + certType.toString();
				String prefix = "CertAtta";
				String url = MultipartUtil.uploadBShSImg(mpf, realPath, relativePath, prefix);
				//大、小图
				String urlB = "";
				String urlSh = "";
				String urlS = "";
				if(url != null) {
					String[] arr = url.split(":");
					urlB = arr[0];
					urlSh = arr[1];
					urlS = arr[2];
				}
				// 数据库中添加记录
				File newF = new File(realPath + urlB);
				ShpCertAtta atta = new ShpCertAtta();
				atta.setCertId(certId);
				atta.setNo(startNo + i);
				atta.setTitle(Integer.toString(i));
				atta.setUrl(urlB);
				atta.setShowImgUrl(urlSh);
				atta.setSmallImgUrl(urlS);
				atta.setImgType(ImgTypeCode
						.valueOf(newF.getName().substring(newF.getName().lastIndexOf(".") + 1).toLowerCase()));
				atta.setSize(newF.length());
				shpCertAttaDao.save(atta);

				ds[i] = new ShpCertAttaData();
				CopyUtil.copyProperties(ds[i], atta);
			}
			return ds;
		}
		return null;
	}

	public boolean deleteCertAtta(Long id) {
		try {
			ShpCertAtta atta = shpCertAttaDao.get(id);

			if (atta != null) {
				// 如果存在旧文件，不再删除了
				/*String realPath = Constants.SHARE_DIR;
				String oldPathFile = atta.getUrl();
				File oldF = new File(realPath + oldPathFile);
				if (oldPathFile != null && !"".equals(oldPathFile) && oldF.exists())
					oldF.delete();*/

				// 只是改加删除标志
				atta.setBeDeleted(YesNoCode.yes);
				shpCertAttaDao.save(atta);

				return true;
			}
			throw new Exception("错误！指定的图片记录已经不存在。");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
