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
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertificateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpShipInfoDao;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpShipAttaData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipinfo.ShpUserShipData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpShipInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpShipInfoService extends BaseService<ShpShipInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpShipInfoDao shipInfoDao;
	@Autowired
	private ShpCertificateDao certificateDao;

	@Autowired
	private ShpUserShipService userShipService;
	@Autowired
	private ShpShipAttaService shipAttaService;
	@Autowired
	private HyqUserService userService;

	@Override
	public PageHibernateDao<ShpShipInfo, Long> getDao() {
		return (PageHibernateDao<ShpShipInfo, Long>) shipInfoDao;
	}

	public ShpShipInfoData getShipInfoData(ShpShipInfo shipInfo) {
		if (shipInfo != null) {
			ShpShipInfoData shipInfoData = new ShpShipInfoData();
			CopyUtil.copyProperties(shipInfoData, shipInfo, new String[] { "builtDate", "createTime" });
			if(shipInfoData.getShipSmallLogo()==null||"".equals(shipInfoData.getShipSmallLogo())) {
				shipInfoData.setShipSmallLogo(shipInfoData.getShipLogo());
			}
			shipInfoData.setBuiltDate(CalendarUtil.toYYYY_MM_DD(shipInfo.getBuiltDate()));
			shipInfoData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(shipInfo.getCreateTime()));

			List<ShpShipAttaData> ads = shipAttaService.getShipAttaDatas(shipInfo.getId());
			shipInfoData.setShipAttaDatas(ads);

			long certCount = certificateDao.statCertCount(shipInfo.getId());
			shipInfoData.setCertCount(certCount);
			
			HyqUserData userData = userService.getById(shipInfo.getCreaterUserId());
			shipInfoData.setCreaterUserData(userData);

			return shipInfoData;
		}
		return null;
	}

	public ShpShipInfoData getShipInfoData(String mmsi, ShpCertSysCode certSys) {
		ShpShipInfo shipInfo = shipInfoDao.getByMmsi(mmsi, certSys);
		if (shipInfo == null)
			return null;
		else
			return this.getShipInfoData(shipInfo);
	}

	public ShpShipInfoData getShipInfoDataByMmsiOrShipName(String mmsi, ShpCertSysCode certSys) {
		ShpShipInfo shipInfo = shipInfoDao.getByMmsiOrShipName(mmsi, certSys);
		if (shipInfo == null)
			return null;
		else
			return this.getShipInfoData(shipInfo);
	}

	public ShpShipInfoData getShipInfoDataById(Long shipId) {
		ShpShipInfo shipInfo = shipInfoDao.get(shipId);
		if (shipInfo == null)
			return null;
		else
			return this.getShipInfoData(shipInfo);
	}

	public Page<ShpShipInfoData> getSearchPageByKeywords(String keywords, Page<ShpShipInfoData> pageData) {

		List<ShpShipInfoData> shipInfoDatas = new ArrayList<ShpShipInfoData>();

		Page<ShpShipInfo> page = shipInfoDao.getSearchPageByKeywords(keywords, pageData.getPageNo(),
				pageData.getPageSize());

		if (!page.getResult().isEmpty()) {
			for (ShpShipInfo shipInfo : page.getResult()) {
				ShpShipInfoData shipInfoData = this.getShipInfoData(shipInfo);
				shipInfoDatas.add(shipInfoData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(shipInfoDatas);

		return pageData;
	}

	public boolean saveShipInfo(ShpShipInfoData shipInfoData) {
		try {
			ShpShipInfo shipInfo = shipInfoDao.get(shipInfoData.getId());

			if (shipInfo == null) {
				shipInfo = new ShpShipInfo();
			}

			CopyUtil.copyProperties(shipInfo, shipInfoData, new String[] { "id", "shipLogo", "createTime" });

			shipInfoDao.save(shipInfo);
			shipInfoData.setId(shipInfo.getId());

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public String uploadShipLogo(Long shipId, MultipartFile shipLogoFile) throws Exception {
		ShpShipInfo shipInfo = shipInfoDao.get(shipId);

		if (shipInfo == null)
			throw new Exception("警告！指定的船舶信息找不到。");

		// 如果上传了Logo
		if (shipLogoFile != null && !shipLogoFile.isEmpty()) {
			// 拷贝新文件到指定位置
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getShipDir(shipInfo.getMmsi());
			String prefix = "ShipLogo";
			String url = MultipartUtil.uploadBSImg(shipLogoFile, realPath, relativePath, prefix);
			//大、小图
			String urlB = "";
			String urlS = "";
			if(url != null) {
				String[] arr = url.split(":");
				urlB = arr[0];
				urlS = arr[1];
			}
			// 修改数据库中文件路径
			shipInfo.setShipLogo(urlB);
			shipInfo.setShipSmallLogo(urlS);

			shipInfoDao.save(shipInfo);
			return urlS;
		} else {
			throw new Exception("错误！请选择文件进行上传。");
		}
	}

	public boolean deleteShipInfo(Long id) {
		try {
			ShpShipInfo shipInfo = shipInfoDao.get(id);

			if (shipInfo != null) {
				List<ShpUserShipData> usds = userShipService.getDatasByShip(id);
				for (ShpUserShipData usd : usds) {
					userShipService.deleteUserShip(usd.getId());
				}

				List<ShpShipAttaData> ads = shipAttaService.getShipAttaDatas(shipInfo.getId());
				for (ShpShipAttaData ad : ads) {
					shipAttaService.deleteShipAtta(ad.getId());
				}

				shipInfo.setBeDeleted(YesNoCode.yes);
				shipInfoDao.save(shipInfo);

				return true;
			}
			throw new Exception("错误！指定的船舶记录已经不存在。");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
