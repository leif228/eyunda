package com.hangyi.eyunda.service.hyquan.shipinfo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertAttaDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertRightsDao;
import com.hangyi.eyunda.dao.shipinfo.ShpCertificateDao;
import com.hangyi.eyunda.dao.shipinfo.ShpUserShipDao;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpCertRightsData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipinfo.ShpUserShipData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpFavoriteCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.domain.shipinfo.ShpCertAtta;
import com.hangyi.eyunda.domain.shipinfo.ShpCertRights;
import com.hangyi.eyunda.domain.shipinfo.ShpCertificate;
import com.hangyi.eyunda.domain.shipinfo.ShpUserShip;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.ImageUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class ShpUserShipService extends BaseService<ShpUserShip, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpUserShipDao userShipDao;
	@Autowired
	private ShpCertRightsDao certRightsDao;
	@Autowired
	private ShpCertRightsService certRightsService;

	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private HyqUserService userService;
	@Autowired
	private ShpCertificateDao shpCertificateDao;
	@Autowired
	private ShpCertAttaDao shpCertAttaDao;

	@Override
	public PageHibernateDao<ShpUserShip, Long> getDao() {
		return (PageHibernateDao<ShpUserShip, Long>) userShipDao;
	}

	public ShpUserShipData getUserShipData(ShpUserShip userShip) {
		if (userShip != null) {
			ShpUserShipData userShipData = new ShpUserShipData();
			CopyUtil.copyProperties(userShipData, userShip, new String[] { "createTime" });
			userShipData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(userShip.getCreateTime()));

			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(userShip.getShipId());
			userShipData.setShipInfoData(shipInfoData);

			if (userShip.getCertSys() == ShpCertSysCode.hyq) {
				HyqUserData userData = userService.getById(userShip.getUserId());
				userShipData.setUserData(userData);

				List<ShpCertRightsData> certRightsDatas = certRightsService.getCertRightsDatas(userShip.getUserId(),
						userShip.getShipId());
				userShipData.setCertRightsDatas(certRightsDatas);
			}

			return userShipData;
		}
		return null;
	}

	public ShpUserShipData getUserShipData(Long usId) {
		ShpUserShip userShip = userShipDao.get(usId);
		if (userShip == null)
			return null;
		else
			return this.getUserShipData(userShip);
	}

	public ShpUserShipData getUserShipData(Long userId, Long shipId) {
		ShpUserShip userShip = userShipDao.getByUserShip(userId, shipId);
		if (userShip == null)
			return null;
		else
			return this.getUserShipData(userShip);
	}

	public boolean canModify(Long userId, Long shipId, ShpCertSysCode... certSys) {
		ShpUserShip userShip = userShipDao.getByUserShip(userId, shipId, certSys);
		if (userShip == null)
			return false;
		else if (userShip.getRights() == ShpRightsCode.managedShip)
			return true;
		else
			return false;
	}

	public boolean hasSeeRights(Long userId, Long shipId, Long certId, ShpCertSysCode... certSys) {
		ShpUserShip userShip = userShipDao.getByUserShip(userId, shipId, certSys);
		if (userShip == null)
			return false;
		else if (userShip.getRights() == ShpRightsCode.managedShip)
			return true;
		else if (userShip.getRights() == ShpRightsCode.seeCertRights) {
			// 有具体证书的授权且没有过期
			ShpCertRights cr = certRightsDao.getByCertId(userId, shipId, certId, certSys);
			if (cr != null)
				return true;
			else
				return false;
		} else
			return false;
	}
	
	public ShpUserShipData getFSUserShipData(Long userId, Long shipId) {
		ShpUserShip userShip = userShipDao.getByUserShipFS(userId, shipId);
		if (userShip == null)
			return null;
		else
			return this.getUserShipData(userShip);
	}
	
	public ShpFavoriteCode canRemoveFavorite(Long userId, Long shipId) {
		ShpUserShip userShip = userShipDao.getByUserShipFS(userId, shipId);
		if (userShip == null)
			return ShpFavoriteCode.cantcancel;
		else
			return ShpFavoriteCode.cancancel;
	}
	
	public ShpFavoriteCode canAddFavorite(Long userId, Long shipId) {
		ShpUserShip userShip = userShipDao.getByUserShip(userId, shipId);
		if (userShip == null)
			return ShpFavoriteCode.canfavorite;
		else
			return ShpFavoriteCode.cantfavorite;
	}

	public ShpFavoriteCode canFavorite(Long userId, Long shipId) {
		ShpUserShip userShip = userShipDao.getByUserShipManagedShip(userId, shipId);
		if (userShip == null){
			ShpFavoriteCode sus = this.canRemoveFavorite(userId, shipId);
			if(sus ==  ShpFavoriteCode.cancancel)
				return ShpFavoriteCode.cancancel;
			else
				return ShpFavoriteCode.canfavorite;
		}else
			return ShpFavoriteCode.cantfavorite;
		
	}

	public List<ShpUserShipData> getDatasByShip(Long shipId) {
		List<ShpUserShipData> datas = new ArrayList<ShpUserShipData>();
		List<ShpUserShip> userShips = userShipDao.getListByShipId(shipId);
		for (ShpUserShip userShip : userShips) {
			ShpUserShipData data = this.getUserShipData(userShip);
			datas.add(data);
		}
		return datas;
	}

	public Page<ShpUserShipData> getFavoritePageByUserId(Long userId, Page<ShpUserShipData> pageData) {

		List<ShpUserShipData> userShipDatas = new ArrayList<ShpUserShipData>();

		Page<ShpUserShip> page = userShipDao.getFavoritePageByUserId(userId, pageData.getPageNo(),
				pageData.getPageSize());

		if (!page.getResult().isEmpty()) {
			for (ShpUserShip userShip : page.getResult()) {
				ShpUserShipData userShipData = this.getUserShipData(userShip);
				userShipDatas.add(userShipData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(userShipDatas);

		return pageData;
	}

	public Page<ShpUserShipData> getMyManagePageByUserId(Long userId, Page<ShpUserShipData> pageData,
			ShpCertSysCode... certSys) {

		List<ShpUserShipData> userShipDatas = new ArrayList<ShpUserShipData>();

		Page<ShpUserShip> page = userShipDao.getMyManagePageByUserId(userId, pageData.getPageNo(),
				pageData.getPageSize(), certSys);

		if (!page.getResult().isEmpty()) {
			for (ShpUserShip userShip : page.getResult()) {
				ShpUserShipData userShipData = this.getUserShipData(userShip);
				userShipDatas.add(userShipData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(userShipDatas);

		return pageData;
	}

	public boolean saveUserShip(ShpUserShipData userShipData) {
		try {
			ShpUserShip userShip = userShipDao.get(userShipData.getId());

			if (userShip == null) {
				userShip = new ShpUserShip();
			}

			userShip.setUserId(userShipData.getUserId());
			userShip.setShipId(userShipData.getShipId());
			userShip.setRights(userShipData.getRights());

			userShipDao.save(userShip);
			userShipData.setId(userShip.getId());

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public boolean deleteUserShip(Long id) {
		try {
			ShpUserShip shpUserShip = userShipDao.get(id);

			shpUserShip.setBeDeleted(YesNoCode.yes);
			userShipDao.save(shpUserShip);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	public List<HyqUserData> findUsersByMyShips(Long userId) {
		List<HyqUserData> huds = new ArrayList<HyqUserData>();
		
		try {
			List<ShpUserShip> suss = userShipDao.findMyShips(userId);
			
			List<ShpUserShip> total = new ArrayList<ShpUserShip>();
			Set<Long> set=new HashSet<Long>(); 
			
			for(ShpUserShip sus:suss){
				List<ShpUserShip> scrs = userShipDao.findSeeCertRights(sus.getShipId());
				total.addAll(scrs);
			}
			
			for(ShpUserShip sus:total){
				set.add(sus.getUserId());
			}
			
			for(Iterator<Long>  it = set.iterator();  it.hasNext(); ){  
				huds.add(userService.getById(it.next()));
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return huds;
	}

	//用于将大图转展示小图，临时使用
	public void btos(Long userId) {
		try {
			List<ShpUserShip> suss = userShipDao.findMyShips(userId);
			
			List<ShpCertificate> scfs = new ArrayList<ShpCertificate>();
			for(ShpUserShip sus:suss){
				scfs.addAll(shpCertificateDao.getByShipId(sus.getShipId()));
			}
			
			List<ShpCertAtta> scas = new ArrayList<ShpCertAtta>();
			for(ShpCertificate scf:scfs){
				scas.addAll(shpCertAttaDao.findByCertId(scf.getId()));
			}
			
			for(ShpCertAtta aca : scas){
				File file = new File(Constants.SHARE_DIR + aca.getUrl());
				if (!file.exists()) {
					continue;
				}
				String sImgName = "sh" + file.getName();
				String sPath = file.getParent()+ShareDirService.FILE_SEPA_SIGN+sImgName;
				
				File sfile = new File(sPath);
				FileUtils.copyFile(file, sfile);
				
				ImageUtil.resize(sPath, 800, 1000, false);
				
				String u = aca.getUrl();
				int s = u.lastIndexOf(ShareDirService.FILE_SEPA_SIGN);
				String up = u.substring(0, s);
				String smp = up+ShareDirService.FILE_SEPA_SIGN+sImgName;
				
				aca.setShowImgUrl(smp);
				shpCertAttaDao.save(aca);
				
				file = null;
				sfile = null;
			}
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
	}

	public List<ShpShipInfoData> getMyManageShips(Long userId) {
		List<ShpShipInfoData> ss = new ArrayList<ShpShipInfoData>();
		
		try {
			List<ShpUserShip> suss = userShipDao.findMyShips(userId);
			for(ShpUserShip sus:suss){
				ShpShipInfoData data = shipInfoService.getShipInfoDataById(sus.getShipId());
				if(data != null)
					ss.add(data);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		
		return ss;
	}

}
