package com.hangyi.eyunda.service.manage;

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
import com.hangyi.eyunda.dao.YydAdminInfoDao;
import com.hangyi.eyunda.dao.YydLogInfoDao;
import com.hangyi.eyunda.dao.YydModuleInfoDao;
import com.hangyi.eyunda.data.manage.LogInfoData;
import com.hangyi.eyunda.domain.YydAdminInfo;
import com.hangyi.eyunda.domain.YydLogInfo;
import com.hangyi.eyunda.domain.YydModuleInfo;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class LogService extends BaseService<YydLogInfo, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydLogInfoDao logDao;
	@Autowired
	YydAdminInfoDao adminDao;
	@Autowired
	YydModuleInfoDao moduleDao;

	@Override
	public PageHibernateDao<YydLogInfo, Long> getDao() {
		return (PageHibernateDao<YydLogInfo, Long>) logDao;
	}

	/**
	 * 取得列表的日志数据对象
	 */
	public List<LogInfoData> getLogDatas() {
		List<LogInfoData> logDatas = new ArrayList<LogInfoData>();
		try {
			Page<YydLogInfo> page = logDao.getPrevThousandLogs();
			if (page != null && !page.getResult().isEmpty()) {
				List<YydLogInfo> logs = (List<YydLogInfo>) page.getResult();
				for (YydLogInfo log : logs) {
					LogInfoData logData = new LogInfoData();

					CopyUtil.copyProperties(logData, log);

					YydAdminInfo yydAdminInfo = adminDao.get(log.getAdminId());
					logData.setAdminName(yydAdminInfo.getTrueName());

					YydModuleInfo yydModuleInfo = moduleDao.get(log.getModuleId());
					logData.setModuleName(yydModuleInfo.getModuleName());

					logData.setOptionTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(log.getOptionTime()));

					logDatas.add(logData);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return logDatas;
	}

	public boolean saveOptLog(Long adminId, Long moduleId) {
		try {
			YydLogInfo logInfo = new YydLogInfo();

			logInfo.setAdminId(adminId);
			logInfo.setModuleId(moduleId);

			logDao.save(logInfo);

			return true;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

}
