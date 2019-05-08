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
import com.hangyi.eyunda.dao.YydStatUserDao;
import com.hangyi.eyunda.data.manage.stat.StatUserData;
import com.hangyi.eyunda.domain.YydStatUser;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class StatUserService extends BaseService<YydStatUser, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydStatUserDao statUserDao;

	@Override
	public PageHibernateDao<YydStatUser, Long> getDao() {
		return (PageHibernateDao<YydStatUser, Long>) statUserDao;
	}

	public List<StatUserData> getStatUserDatas() {
		List<StatUserData> statUserDatas = new ArrayList<StatUserData>();
		try {
			// 取出一年的记录按月分组
			Calendar today = Calendar.getInstance();
			Calendar oneYear = CalendarUtil.addMonths(today, -11);

			while (CalendarUtil.compare(oneYear, today) <= 0) {
				YydStatUser statUser = statUserDao.getByYearMonth(CalendarUtil.toYYYYMM(oneYear));

				StatUserData statUserData = this.getByEntity(statUser);
				if (statUserData == null) {
					statUserData = new StatUserData();
					String yearMonth = CalendarUtil.toYYYYMM(oneYear);
					statUserData.setYearMonth(yearMonth.substring(0, 4) + "年" + yearMonth.substring(4) + "月");
				}
				statUserDatas.add(statUserData);
				oneYear = CalendarUtil.addMonths(oneYear, 1);
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return statUserDatas;
	}

	private StatUserData getByEntity(YydStatUser statUser) {
		StatUserData statUserData = null;
		try {
			if (statUser != null) {
				statUserData = new StatUserData();
				CopyUtil.copyProperties(statUserData, statUser);
				statUserData.setYearMonth(statUserData.getYearMonth().substring(0, 4) + "年"
						+ statUserData.getYearMonth().substring(4) + "月");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return statUserData;
	}

}
