package com.hangyi.eyunda.service.manage.stat;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.PageHibernateDao;
import com.hangyi.eyunda.dao.YydRecordLoginDao;
import com.hangyi.eyunda.data.manage.UserLoginLogData;
import com.hangyi.eyunda.domain.YydRecordLogin;
import com.hangyi.eyunda.domain.enumeric.LoginSourceCode;
import com.hangyi.eyunda.service.BaseService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class RecordLoginService extends BaseService<YydRecordLogin, Long> {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	YydRecordLoginDao recordLoginDao;
	@Autowired
	UserService userService;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Page findUserLoginLogs(Page pageData, String userInfo, String startDate, String endDate) {
		recordLoginDao.findUserLoginLogs(pageData, userInfo, startDate, endDate);

		List<Map<String, Object>> maps = (List<Map<String, Object>>) pageData.getResult();

		List<UserLoginLogData> loginLogDatas = new ArrayList<UserLoginLogData>();
		if (!maps.isEmpty()) {
			for (Map<String, Object> map : maps) {
				UserLoginLogData userLoginLogData = new UserLoginLogData();

				BigInteger biId = (BigInteger) map.get("id");
				userLoginLogData.setId(biId.longValue()); // 登录记录ID
				userLoginLogData.setUserLogo((String) map.get("userLogo")); // 图标图片
				userLoginLogData.setLoginName((String) map.get("loginName")); // 登录名
				userLoginLogData.setTrueName((String) map.get("trueName")); // 姓名
				userLoginLogData.setNickName((String) map.get("nickName")); // 昵称
				userLoginLogData.setEmail((String) map.get("email")); // 电子邮箱
				userLoginLogData.setIpAddress((String) map.get("ipAddress")); // 登录客户端IP地址

				Timestamp tsLoginTime = (Timestamp) map.get("loginTime");
				if (tsLoginTime != null) {
					Date dLoginTime = new Date(tsLoginTime.getTime());
					Calendar cdLoginTime = Calendar.getInstance();
					cdLoginTime.setTime(dLoginTime);
					String sLoginTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(cdLoginTime);
					userLoginLogData.setLoginTime(sLoginTime); // 登陆时间
				}
				Timestamp tsLogoutTime = (Timestamp) map.get("logoutTime");
				if (tsLogoutTime != null) {
					Date dLogoutTime = new Date(tsLogoutTime.getTime());
					Calendar cdLogoutTime = Calendar.getInstance();
					cdLogoutTime.setTime(dLogoutTime);
					String sLogoutTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(cdLogoutTime);
					userLoginLogData.setLogoutTime(sLogoutTime); // 登出时间
				}

				int timeSpan = (Integer) map.get("timeSpan");
				int hours = (int) Math.floor(timeSpan / 60 / 60);
				int minutes = (int) (Math.floor(timeSpan / 60) - (int) 60 * Math.floor(timeSpan / 60 / 60));
				userLoginLogData.setTimeSpan(hours + "小时" + minutes + "分"); // 使用时长

				loginLogDatas.add(userLoginLogData);
			}
		}

		pageData.setResult(loginLogDatas);
		return pageData;
	}

	@Override
	public PageHibernateDao<YydRecordLogin, Long> getDao() {
		return (PageHibernateDao<YydRecordLogin, Long>) recordLoginDao;
	}

	public void recordLogin(OnlineUser onlineUser, LoginSourceCode loginSource, String ipAddress) {
		YydRecordLogin yydRecordLogin = null;
		Calendar loginTime = CalendarUtil.now();
		String sessionId = "";

		if (loginSource == LoginSourceCode.mobile) {
			sessionId = onlineUser.getSessionId();
		} else if (loginSource == LoginSourceCode.web) {
			sessionId = onlineUser.getDwrSessionId();
		}

		yydRecordLogin = new YydRecordLogin();

		yydRecordLogin.setUserId(onlineUser.getId());
		yydRecordLogin.setLoginSource(loginSource);
		yydRecordLogin.setIpAddress(ipAddress);
		yydRecordLogin.setLoginTime(loginTime);
		yydRecordLogin.setSessionId(sessionId);

		// 由于是动态角色，按角色分别记录意义已经不大。
		yydRecordLogin.setRoleCode(userService.getUserRole(onlineUser.getId()));

		recordLoginDao.save(yydRecordLogin);
	}

	public void recordLogout(OnlineUser onlineUser, LoginSourceCode loginSource) {
		YydRecordLogin yydRecordLogin = null;

		if (loginSource == LoginSourceCode.mobile) {
			yydRecordLogin = recordLoginDao.getLastByUserId(onlineUser.getId(), LoginSourceCode.mobile,
					onlineUser.getSessionId());
		} else if (onlineUser.getDwrSessionId() != null && !"".equals(onlineUser.getDwrSessionId())) {
			yydRecordLogin = recordLoginDao.getLastByUserId(onlineUser.getId(), LoginSourceCode.web,
					onlineUser.getDwrSessionId());
		}

		if (yydRecordLogin != null) {
			yydRecordLogin.setLogoutTime(CalendarUtil.toCalnedar(new Date()));

			Long logoutTime = yydRecordLogin.getLogoutTime().getTimeInMillis();
			Long loginTime = yydRecordLogin.getLoginTime().getTimeInMillis();
			Long timeSpan = (logoutTime - loginTime) / 1000;
			yydRecordLogin.setTimeSpan(timeSpan.intValue());

			recordLoginDao.save(yydRecordLogin);
		}
	}
}
