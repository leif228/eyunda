package com.hangyi.eyunda.service.manage.stat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydStatShipCallDao;
import com.hangyi.eyunda.data.manage.stat.MonthStatShipCallData;
import com.hangyi.eyunda.data.manage.stat.StatShipCallData;
import com.hangyi.eyunda.domain.YydStatShipCall;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class StatShipCallService extends BaseService<YydStatShipCall, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydStatShipCallDao statShipCallDao;

	@Override
	public PageHibernateDao<YydStatShipCall, Long> getDao() {
		return (PageHibernateDao<YydStatShipCall, Long>) statShipCallDao;
	}

	public List<MonthStatShipCallData> getMonthStatShipCallDatas() {
		List<MonthStatShipCallData> monthStatShipCallDatas = new ArrayList<MonthStatShipCallData>();
		try {
			// 取出一年的记录按月分组
			Calendar today = Calendar.getInstance();
			Calendar oneYear = CalendarUtil.addMonths(today, -11);
			UserPrivilegeCode[] roleCodes = UserPrivilegeCode.values();

			while (CalendarUtil.compare(oneYear, today) <= 0) {
				MonthStatShipCallData monthStatShipCallData = new MonthStatShipCallData();
				List<StatShipCallData> statShipCallDatas = new ArrayList<StatShipCallData>();
				for (UserPrivilegeCode roleCode : roleCodes) {
					YydStatShipCall statShipCall = statShipCallDao.getByYearMonth(CalendarUtil.toYYYYMM(oneYear),
							roleCode);

					StatShipCallData statShipCallData = this.getByEntity(statShipCall);
					if (statShipCallData == null) {
						statShipCallData = new StatShipCallData();
						statShipCallData.setRoleCode(Integer.toString(roleCode.ordinal()));
						statShipCallData.setRoleDesc(roleCode.getDescription());
					}
					statShipCallDatas.add(statShipCallData);
				}
				monthStatShipCallData.setYearMonth(CalendarUtil.toYYYY_MM(oneYear));
				monthStatShipCallData.setStatShipCallDatas(statShipCallDatas);
				monthStatShipCallDatas.add(monthStatShipCallData);
				oneYear = CalendarUtil.addMonths(oneYear, 1);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return monthStatShipCallDatas;
	}

	private StatShipCallData getByEntity(YydStatShipCall statShipCall) {
		StatShipCallData statShipCallData = null;
		try {
			if (statShipCall != null) {
				statShipCallData = new StatShipCallData();
				CopyUtil.copyProperties(statShipCallData, statShipCall);
				statShipCallData.setYearMonth(statShipCallData.getYearMonth().substring(0, 4) + "年"
						+ statShipCallData.getYearMonth().substring(4) + "月");
				statShipCallData.setRoleCode(Integer.toString(statShipCall.getRoleCode().ordinal()));
				statShipCallData.setRoleDesc(statShipCall.getRoleCode().getDescription());
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return statShipCallData;
	}

	public List<StatShipCallData> getStatShipCallDatas() {
		List<StatShipCallData> statShipCallDatas = new ArrayList<StatShipCallData>();
		try {
			List<YydStatShipCall> statShipCalls = statShipCallDao.getOneYear();
			if (!statShipCalls.isEmpty()) {
				for (YydStatShipCall statShipCall : statShipCalls) {
					StatShipCallData statShipCallData = this.getByEntity(statShipCall);
					statShipCallDatas.add(statShipCallData);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return statShipCallDatas;
	}

}
