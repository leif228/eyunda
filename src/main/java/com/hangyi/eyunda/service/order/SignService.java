package com.hangyi.eyunda.service.order;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydOrderSignDao;
import com.hangyi.eyunda.data.order.OrderSignData;
import com.hangyi.eyunda.domain.YydOrderSign;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class SignService extends BaseService<YydOrderSign, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydOrderSignDao signDao;

	@Override
	public PageHibernateDao<YydOrderSign, Long> getDao() {
		return (PageHibernateDao<YydOrderSign, Long>) signDao;
	}

	public OrderSignData getOrderSignData(Long id) {
		YydOrderSign yydOrderSign = signDao.get(id);
		if (yydOrderSign != null) {
			return this.getOrderSignData(yydOrderSign);
		}
		return null;
	}

	public OrderSignData getOrderSignData(YydOrderSign yydOrderSign) {
		if (yydOrderSign != null) {
			OrderSignData orderSignData = new OrderSignData();
			CopyUtil.copyProperties(orderSignData, yydOrderSign);

			orderSignData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM(yydOrderSign.getCreateTime()));

			return orderSignData;
		}
		return null;
	}

	public OrderSignData getOrderSignData(Long userId, Long orderId) {
		YydOrderSign yydOrderSign = signDao.getByUserOrder(userId, orderId);

		if (yydOrderSign != null) {
			return this.getOrderSignData(yydOrderSign);
		}
		return null;
	}

}
