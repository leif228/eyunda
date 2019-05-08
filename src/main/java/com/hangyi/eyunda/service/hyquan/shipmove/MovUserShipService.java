package com.hangyi.eyunda.service.hyquan.shipmove;

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
import com.hangyi.eyunda.dao.shipmove.MovUserShipDao;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipmove.MovUserShipData;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.domain.shipmove.MovUserShip;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MovUserShipService extends BaseService<MovUserShip, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private MovUserShipDao userShipDao;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private HyqUserService userService;

	@Override
	public PageHibernateDao<MovUserShip, Long> getDao() {
		return (PageHibernateDao<MovUserShip, Long>) userShipDao;
	}

	public MovUserShipData getUserShipData(MovUserShip userShip) {
		if (userShip != null) {
			MovUserShipData userShipData = new MovUserShipData();
			CopyUtil.copyProperties(userShipData, userShip, new String[] { "createTime" });
			userShipData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(userShip.getCreateTime()));

			HyqUserData userData = userService.getById(userShip.getUserId());
			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoData(userShip.getMmsi(), ShpCertSysCode.hyq);

			userShipData.setUserData(userData);
			userShipData.setShipInfoData(shipInfoData);

			return userShipData;
		}
		return null;
	}

	public MovUserShipData getUserShipData(Long usId) {
		MovUserShip userShip = userShipDao.get(usId);
		if (userShip == null)
			return null;
		else
			return this.getUserShipData(userShip);
	}

	public MovUserShipData getUserShipData(Long userId, String mmsi) {
		MovUserShip userShip = userShipDao.getByUserShip(userId, mmsi);
		if (userShip == null)
			return null;
		else
			return this.getUserShipData(userShip);
	}

	public MovUserShipData getModifyByMmsi(String mmsi) {
		MovUserShip userShip = userShipDao.getModifyByMmsi(mmsi);
		if (userShip == null)
			return null;
		else
			return this.getUserShipData(userShip);
	}

	public boolean canModify(Long userId, String mmsi) {
		MovUserShip userShip = userShipDao.getByUserShip(userId, mmsi);
		if (userShip == null)
			return false;
		else if (userShip.getRights() == ShpRightsCode.managedShip)
			return true;
		else
			return false;
	}

	public boolean aloneModifier(Long userId, String mmsi) {
		boolean isAlone = true;
		List<MovUserShip> userShips = userShipDao.getListByMmsi(mmsi);
		for (MovUserShip userShip : userShips) {
			if (!userShip.getUserId().equals(userId) && userShip.getRights() == ShpRightsCode.managedShip) {
				isAlone = false;
				break;
			}
		}
		return isAlone;
	}

	public List<MovUserShipData> getDatasByShip(String mmsi) {
		List<MovUserShipData> datas = new ArrayList<MovUserShipData>();
		List<MovUserShip> userShips = userShipDao.getListByMmsi(mmsi);
		for (MovUserShip userShip : userShips) {
			MovUserShipData data = this.getUserShipData(userShip);
			datas.add(data);
		}
		return datas;
	}

	public Page<MovUserShipData> getPageByUserId(Long userId, String keywords, Page<MovUserShipData> pageData) {

		List<MovUserShipData> userShipDatas = new ArrayList<MovUserShipData>();

		Page<MovUserShip> page = userShipDao.getPageByUserId(userId, keywords, pageData.getPageNo(),
				pageData.getPageSize());

		if (!page.getResult().isEmpty()) {
			for (MovUserShip userShip : page.getResult()) {
				MovUserShipData userShipData = this.getUserShipData(userShip);
				userShipDatas.add(userShipData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(userShipDatas);

		return pageData;
	}

	public boolean saveUserShip(MovUserShipData userShipData) {
		try {
			MovUserShip userShip = userShipDao.get(userShipData.getId());

			if (userShip == null) {
				userShip = new MovUserShip();
			}

			userShip.setUserId(userShipData.getUserId());
			userShip.setMmsi(userShipData.getMmsi());
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
			MovUserShip userShip = userShipDao.get(id);

			userShipDao.delete(userShip);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
