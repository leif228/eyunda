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
import com.hangyi.eyunda.dao.YydStatLoginDao;
import com.hangyi.eyunda.data.manage.stat.MonthStatLoginData;
import com.hangyi.eyunda.data.manage.stat.StatLoginData;
import com.hangyi.eyunda.domain.YydStatLogin;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class StatLoginService extends BaseService<YydStatLogin, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydStatLoginDao statLoginDao;

	@Override
	public PageHibernateDao<YydStatLogin, Long> getDao() {
		return (PageHibernateDao<YydStatLogin, Long>) statLoginDao;
	}

	public List<MonthStatLoginData> getMonthStatLoginDatas() {
		List<MonthStatLoginData> monthStatLoginDatas = new ArrayList<MonthStatLoginData>();
		try {
			// 取出一年的记录按月分组
			Calendar today = Calendar.getInstance();
			Calendar oneYear = CalendarUtil.addMonths(today, -11);
			UserPrivilegeCode[] roleCodes = UserPrivilegeCode.values();

			while (CalendarUtil.compare(oneYear, today) <= 0) {
				MonthStatLoginData monthStatLoginData = new MonthStatLoginData();
				List<StatLoginData> statLoginDatas = new ArrayList<StatLoginData>();
				for (UserPrivilegeCode roleCode : roleCodes) {
					YydStatLogin statLogin = statLoginDao.findByYearMonthAndRole(CalendarUtil.toYYYYMM(oneYear),
							roleCode);

					StatLoginData statLoginData = this.getByEntity(statLogin);
					if (statLoginData == null) {
						statLoginData = new StatLoginData();
						statLoginData.setRoleCode(Integer.toString(roleCode.ordinal()));
						statLoginData.setRoleDesc(roleCode.getDescription());
					}
					statLoginDatas.add(statLoginData);
				}
				monthStatLoginData.setYearMonth(CalendarUtil.toYYYY_MM(oneYear));
				monthStatLoginData.setMonthLoginDatas(statLoginDatas);
				monthStatLoginDatas.add(monthStatLoginData);
				oneYear = CalendarUtil.addMonths(oneYear, 1);
			}

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return monthStatLoginDatas;
	}

	private StatLoginData getByEntity(YydStatLogin statLogin) {
		StatLoginData statLoginData = null;
		try {
			if (statLogin != null) {
				statLoginData = new StatLoginData();
				CopyUtil.copyProperties(statLoginData, statLogin);

				statLoginData.setYearMonth(statLoginData.getYearMonth().substring(0, 4) + "年"
						+ statLoginData.getYearMonth().substring(4) + "月");

				statLoginData.setRoleCode(Integer.toString(statLogin.getRoleCode().ordinal()));
				statLoginData.setRoleDesc(statLogin.getRoleCode().getDescription());

				int timeSpan = statLogin.getTimeSpan();
				int hours = (int) Math.floor(timeSpan / 60 / 60);
				int minutes = (int) (Math.floor(timeSpan / 60) - (int) 60 * Math.floor(timeSpan / 60 / 60));
				statLoginData.setTimeSpanDesc(hours + "小时" + minutes + "分"); // 使用时长
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return statLoginData;
	}

	public List<StatLoginData> getStatLoginDatas() {
		List<StatLoginData> statLoginDatas = new ArrayList<StatLoginData>();
		try {
			List<YydStatLogin> statLogins = statLoginDao.getInYear();
			if (!statLogins.isEmpty()) {
				for (YydStatLogin statLogin : statLogins) {
					StatLoginData statLoginData = this.getByEntity(statLogin);
					statLoginDatas.add(statLoginData);
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return statLoginDatas;
	}

}
