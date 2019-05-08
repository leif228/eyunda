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
import com.hangyi.eyunda.dao.YydStatOrderDao;
import com.hangyi.eyunda.data.manage.stat.StatOrderData;
import com.hangyi.eyunda.domain.YydStatOrder;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class StatOrderService extends BaseService<YydStatOrder, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydStatOrderDao statOrderDao;

	@Override
	public PageHibernateDao<YydStatOrder, Long> getDao() {
		return (PageHibernateDao<YydStatOrder, Long>) statOrderDao;
	}

	public List<StatOrderData> getStatOrderDatas() {
		List<YydStatOrder> statOrders = statOrderDao.getOneYear();
		List<StatOrderData> statOrderDatas = new ArrayList<StatOrderData>();

		for (YydStatOrder statOrder : statOrders) {
			StatOrderData statOrderData = new StatOrderData();
			CopyUtil.copyProperties(statOrderData, statOrder);

			String yearMonth = statOrder.getYearMonth();
			statOrderData.setYearMonth(yearMonth.substring(0, 4) + "年" + yearMonth.substring(4) + "月");

			statOrderDatas.add(statOrderData);
		}
		return statOrderDatas;
	}

}
