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

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpShipAttaDao;
import com.hangyi.eyunda.dao.shipinfo.ShpShipInfoDao;
import com.hangyi.eyunda.data.shipinfo.ShpShipAttaData;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpShipAtta;
import com.hangyi.eyunda.domain.shipinfo.ShpShipInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MultipartUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpShipAttaService extends BaseService<ShpShipAtta, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpShipAttaDao shpShipAttaDao;
	@Autowired
	private ShpShipInfoDao shpShipInfoDao;

	@Override
	public PageHibernateDao<ShpShipAtta, Long> getDao() {
		return (PageHibernateDao<ShpShipAtta, Long>) shpShipAttaDao;
	}

	public ShpShipAttaData getShipAttaData(ShpShipAtta atta) {
		if (atta != null) {
			ShpShipAttaData attaData = new ShpShipAttaData();
			CopyUtil.copyProperties(attaData, atta);
			attaData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(atta.getCreateTime()));

			return attaData;
		}
		return null;
	}

	public ShpShipAttaData getShipAttaData(Long attaId) {
		ShpShipAtta atta = shpShipAttaDao.get(attaId);
		if (atta == null)
			return null;
		else
			return this.getShipAttaData(atta);
	}

	public List<ShpShipAttaData> getShipAttaDatas(Long shipId) {
		List<ShpShipAttaData> attaDatas = new ArrayList<ShpShipAttaData>();
		try {
			List<ShpShipAtta> attas = shpShipAttaDao.findByShipId(shipId);
			for (ShpShipAtta atta : attas) {
				ShpShipAttaData attaData = this.getShipAttaData(atta);
				attaDatas.add(attaData);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return attaDatas;
	}

	public boolean saveShipAtta(ShpShipAttaData attaData) {
		try {
			ShpShipAtta atta = shpShipAttaDao.get(attaData.getId());

			if (atta == null) {
				atta = new ShpShipAtta();
			}

			CopyUtil.copyProperties(atta, attaData, new String[] { "id", "url", "createTime" });

			// 如果上传了
			MultipartFile mpf = attaData.getImgFile();
			if (mpf != null && !mpf.isEmpty()) {
				Long shipId = attaData.getShipId();
				ShpShipInfo shpShipInfo = shpShipInfoDao.get(shipId);

				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getShipDir(shpShipInfo.getMmsi());
				String prefix = "ShipAtta";
				String url = MultipartUtil.uploadFile(mpf, realPath, relativePath, prefix);

				atta.setUrl(url);
			}
			shpShipAttaDao.save(atta);
			attaData.setId(atta.getId());
			attaData.setUrl(atta.getUrl());

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteShipAtta(Long id) {
		try {
			ShpShipAtta atta = shpShipAttaDao.get(id);

			if (atta != null) {
				// 如果存在旧文件删除之
				/*
				 * String realPath = Constants.SHARE_DIR; String oldPathFile = atta.getUrl();
				 * File oldF = new File(realPath + oldPathFile); if (oldPathFile != null &&
				 * !"".equals(oldPathFile) && oldF.exists()) oldF.delete();
				 */

				atta.setBeDeleted(YesNoCode.yes);
				shpShipAttaDao.save(atta);

				return true;
			}
			throw new Exception("错误！指定的图片记录已经不存在。");
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
