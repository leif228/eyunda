package com.hangyi.eyunda.service.manage.stat;

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
import com.hangyi.eyunda.dao.YydStatShipDao;
import com.hangyi.eyunda.data.manage.stat.StatShipData;
import com.hangyi.eyunda.domain.YydStatShip;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class StatShipService extends BaseService<YydStatShip, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydStatShipDao statShipDao;

	@Override
	public PageHibernateDao<YydStatShip, Long> getDao() {
		return (PageHibernateDao<YydStatShip, Long>) statShipDao;
	}

	public List<StatShipData> getStatShipDatas() {
		List<YydStatShip> statShips = statShipDao.getOneYear();
		List<StatShipData> statShipDatas = new ArrayList<StatShipData>();

		for (YydStatShip statShip : statShips) {
			StatShipData statShipData = new StatShipData();
			CopyUtil.copyProperties(statShipData, statShip);

			String yearMonth = statShip.getYearMonth();
			statShipData.setYearMonth(yearMonth.substring(0, 4) + "年" + yearMonth.substring(4) + "月");

			statShipDatas.add(statShipData);
		}
		return statShipDatas;
	}

}
