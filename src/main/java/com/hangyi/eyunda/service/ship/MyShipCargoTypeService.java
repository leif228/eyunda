package com.hangyi.eyunda.service.ship;

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
import com.hangyi.eyunda.dao.YydShipCargoTypeDao;
import com.hangyi.eyunda.domain.YydShipCargoType;
import com.hangyi.eyunda.domain.enumeric.CargoBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.service.BaseService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class MyShipCargoTypeService extends BaseService<YydShipCargoType, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydShipCargoTypeDao shipCargoTypeDao;

	@Override
	public PageHibernateDao<YydShipCargoType, Long> getDao() {
		return (PageHibernateDao<YydShipCargoType, Long>) shipCargoTypeDao;
	}

	public List<CargoTypeCode> getShipCargoTypes(Long shipId) {
		List<CargoTypeCode> cargoTypes = new ArrayList<CargoTypeCode>();

		// 船舶接货类别列表
		List<YydShipCargoType> shipCargoTypes = shipCargoTypeDao.getShipCargoTypes(shipId);
		for (YydShipCargoType shipCargoType : shipCargoTypes)
			cargoTypes.add(shipCargoType.getCargoType());

		return cargoTypes;
	}

	public boolean saveCargoTypes(Long shipId, String[] cargoTypes) {
		try {
			// 先删除旧的
			shipCargoTypeDao.batchExecute("delete from YydShipCargoType where shipId = ?", shipId);

			// 再保存新的
			for (String cargoType : cargoTypes) {
				YydShipCargoType shipCargoType = new YydShipCargoType();
				if (!cargoType.equals("")) {
					if (CargoBigTypeCode.container.toString().equals(cargoType.trim()))
						shipCargoType.setCargoType(CargoTypeCode.getCodesByCargoBigType(CargoBigTypeCode.container).get(0));
					else
						shipCargoType.setCargoType(CargoTypeCode.valueOf(cargoType));

					shipCargoType.setShipId(shipId);
					shipCargoTypeDao.save(shipCargoType);
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

}
