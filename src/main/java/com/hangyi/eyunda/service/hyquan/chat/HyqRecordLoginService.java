package com.hangyi.eyunda.service.hyquan.chat;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.dao.HyqRecordLoginDao;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqRecordLoginData;
import com.hangyi.eyunda.domain.HyqRecordLogin;
import com.hangyi.eyunda.domain.HyqUserInfo;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CopyUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqRecordLoginService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqRecordLoginDao recordLoginDao;

	public HyqRecordLoginData getUserLoginLogData(HyqRecordLogin record) {
		if (record != null) {
			HyqRecordLoginData recordData = new HyqRecordLoginData();
			CopyUtil.copyProperties(recordData, record, new String[] { "loginTime", "logoutTime" });
			recordData.setLoginTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(record.getLoginTime()));
			recordData.setLogoutTime(CalendarUtil.toYYYY_MM_DD_HH_MM_SS(record.getLogoutTime()));

			return recordData;
		}
		return null;
	}

	public Page<HyqRecordLoginData> findUserLoginLogs(Page<HyqRecordLoginData> pageData, String userInfo,
			String startDate, String endDate) {
		List<HyqRecordLoginData> list = new ArrayList<HyqRecordLoginData>();

		Page<Object> page = recordLoginDao.findUserLoginLogs(pageData.getPageNo(), pageData.getPageSize(), userInfo,
				startDate, endDate);

		if (!page.getResult().isEmpty()) {
			for (Object object : page.getResult()) {
				Object[] os = (Object[]) object;

				HyqUserInfo user = (HyqUserInfo) os[0];
				HyqRecordLogin record = (HyqRecordLogin) os[1];

				HyqRecordLoginData recordData = this.getUserLoginLogData(record);
				recordData.setUserLogo(user.getUserLogo());
				recordData.setLoginName(user.getLoginName());
				recordData.setTrueName(user.getTrueName());
				recordData.setNickName(user.getNickName());
				recordData.setEmail(user.getEmail());

				list.add(recordData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(list);

		return pageData;
	}

	public void recordLogin(OnlineUser onlineUser, LoginSourceCode loginSource, String ipAddress) {
		HyqRecordLogin hyqRecordLogin = null;
		Calendar loginTime = CalendarUtil.now();
		String sessionId = "";

		if (loginSource == LoginSourceCode.mobile) {
			sessionId = onlineUser.getSessionId();
		} else if (loginSource == LoginSourceCode.web) {
			sessionId = onlineUser.getDwrSessionId();
		}

		hyqRecordLogin = new HyqRecordLogin();

		hyqRecordLogin.setUserId(onlineUser.getId());
		hyqRecordLogin.setLoginSource(loginSource);
		hyqRecordLogin.setIpAddress(ipAddress);
		hyqRecordLogin.setLoginTime(loginTime);
		hyqRecordLogin.setSessionId(sessionId);

		recordLoginDao.save(hyqRecordLogin);
	}

	public void recordLogout(OnlineUser onlineUser, LoginSourceCode loginSource) {
		HyqRecordLogin hyqRecordLogin = null;

		if (loginSource == LoginSourceCode.mobile) {
			hyqRecordLogin = recordLoginDao.getLastByUserId(onlineUser.getId(), LoginSourceCode.mobile,
					onlineUser.getSessionId());
		} else if (onlineUser.getDwrSessionId() != null && !"".equals(onlineUser.getDwrSessionId())) {
			hyqRecordLogin = recordLoginDao.getLastByUserId(onlineUser.getId(), LoginSourceCode.web,
					onlineUser.getDwrSessionId());
		}

		if (hyqRecordLogin != null) {
			hyqRecordLogin.setLogoutTime(CalendarUtil.toCalnedar(new Date()));

			Long logoutTime = hyqRecordLogin.getLogoutTime().getTimeInMillis();
			Long loginTime = hyqRecordLogin.getLoginTime().getTimeInMillis();
			Long timeSpan = (logoutTime - loginTime) / 1000;
			hyqRecordLogin.setTimeSpan(timeSpan.intValue());

			recordLoginDao.save(hyqRecordLogin);
		}
	}

}
