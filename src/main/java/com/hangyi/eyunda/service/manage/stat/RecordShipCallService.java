package com.hangyi.eyunda.service.manage.stat;

import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydRecordShipCallDao;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.YydRecordShipCall;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.portal.login.UserService;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class RecordShipCallService extends BaseService<YydRecordShipCall, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydRecordShipCallDao recordShipCallDao;
	@Autowired
	UserService userService;

	@Override
	public PageHibernateDao<YydRecordShipCall, Long> getDao() {
		return (PageHibernateDao<YydRecordShipCall, Long>) recordShipCallDao;
	}

	public void recordShipCall(UserData userData, ShipData myShipData) {
		Calendar callTime = Calendar.getInstance();
		this.recordShipCall(userData, myShipData, callTime);
	}

	public void recordShipCall(UserData userData, ShipData myShipData, Calendar callTime) {
		YydRecordShipCall yydRecordShipCall = new YydRecordShipCall();

		if (userData != null) {
			yydRecordShipCall.setUserId(userData.getId());
			// 由于是动态角色，按角色分别记录意义已经不大。
			yydRecordShipCall.setRoleCode(userService.getUserRole(userData.getId()));
		} else {
			yydRecordShipCall.setUserId(0L);
		}
		yydRecordShipCall.setShipId(myShipData.getId());
		yydRecordShipCall.setRecordTime(callTime);

		recordShipCallDao.save(yydRecordShipCall);
	}

}
