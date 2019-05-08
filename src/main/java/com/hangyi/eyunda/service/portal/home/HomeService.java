package com.hangyi.eyunda.service.portal.home;

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
import com.hangyi.eyunda.dao.YydCargoInfoDao;
import com.hangyi.eyunda.dao.YydShipDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.YydCargoInfo;
import com.hangyi.eyunda.domain.YydShip;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.manage.stat.RecordShipCallService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HomeService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipDao shipDao;
	@Autowired
	private YydCargoInfoDao cargoDao;

	@Autowired
	private CargoService cargoService;

	@Autowired
	private RecordShipCallService recordShipCallService;

	public void saveShipPointCount(UserData userData, ShipData myShipData) {
		recordShipCallService.recordShipCall(userData, myShipData);

		YydShip ship = shipDao.get(myShipData.getId());
		if (ship != null) {
			ship.setPointCount(ship.getPointCount() + 1);
			shipDao.save(ship);
		}
	}

	// 新首页取5条货盘记录
	public Page<CargoData> getHomeCargo5(int pageSize, int pageNo) {
		return this.getHomeCargoList(pageSize, pageNo, null, null, null);
	}

	// 新首页关键字查找货盘
	public Page<CargoData> getHomeCargoList(int pageSize, int pageNo, String keyWords, String startDate,
			String endDate) {
		Page<YydCargoInfo> page = cargoDao.findCargoPage(pageSize, pageNo, keyWords, startDate, endDate);

		List<CargoData> cargoDatas = new ArrayList<CargoData>();
		for (YydCargoInfo cargoInfo : page.getResult()) {
			CargoData cargoData = cargoService.getCargoData(cargoInfo);
			if (cargoData != null) {
				cargoDatas.add(cargoData);
			}
		}

		Page<CargoData> pageData = new Page<CargoData>(pageNo, pageSize, page.getOrderBy(), page.getOrder(), cargoDatas,
				page.getTotalCount());

		return pageData;
	}

}
