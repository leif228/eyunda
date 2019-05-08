package com.hangyi.eyunda.service.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydAdminInfoDao;
import com.hangyi.eyunda.dao.YydModuleInfoDao;
import com.hangyi.eyunda.data.manage.AdminInfoData;
import com.hangyi.eyunda.domain.YydAdminInfo;
import com.hangyi.eyunda.domain.YydModuleInfo;
import com.hangyi.eyunda.domain.YydRoleInfo;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.MD5;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
public class LoginService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public final String SUPER_ADMIN = "superadmin";

	@Autowired
	private YydAdminInfoDao adminDao;
	@Autowired
	private YydModuleInfoDao moduleDao;

	public AdminInfoData getByCookie(String ack) {
		Long id = 0L;
		String loginToken = "";
		try {
			String[] ss = ack.split(",");

			id = Long.parseLong(ss[0]);
			loginToken = ss[1];
		} catch (Exception nfe) {
			return null;
		}

		YydAdminInfo admin = adminDao.get(id);
		if (admin != null) {
			AdminInfoData adminData = new AdminInfoData();
			CopyUtil.copyProperties(adminData, admin);

			if (this.valiLoginToken(adminData, loginToken))
				return adminData;
			else
				return null;
		} else
			return null;
	}

	public AdminInfoData getByLoginName(String loginName) {
		YydAdminInfo admin = adminDao.getByLoginName(loginName);

		if (admin != null) {
			AdminInfoData adminData = new AdminInfoData();
			CopyUtil.copyProperties(adminData, admin);

			return adminData;
		}

		return null;
	}

	/** 通过用户信息生成一个令牌 */
	public String getLoginToken(AdminInfoData adminData) {
		StringBuffer tokensb = new StringBuffer();
		tokensb.append("AdminInfo").append(adminData.getLoginName()).append(adminData.getMobile())
				.append(adminData.getId()).append(adminData.getCreateTime());

		return MD5.toMD5(tokensb.toString());
	}

	/** 验证登录令牌 */
	public boolean valiLoginToken(AdminInfoData adminData, String loginToken) {
		String valiToken = getLoginToken(adminData);

		return loginToken.equals(valiToken);
	}

	public AdminInfoData login(String username, String password) {
		try {
			YydAdminInfo admin = adminDao.getByLoginName(username);
			if (admin != null) {
				if (MD5.checkpw(password, admin.getPassword(), Long.toString(admin.getId()))) {
					AdminInfoData data = new AdminInfoData();
					CopyUtil.copyProperties(data, admin);
					return data;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	public boolean changePwd(Long adminId, String adminPwd) {
		try {
			YydAdminInfo admin = adminDao.get(adminId);
			if (admin != null) {
				admin.setPassword(MD5.toMD5(adminPwd + admin.getId()));
				adminDao.save(admin);
				return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	public List<String> getPowerModules(Long adminId) {
		List<String> powerModules = new ArrayList<String>();
		try {
			YydAdminInfo admin = adminDao.get(adminId);

			if (admin.getLoginName().equals(SUPER_ADMIN)) {
				List<YydModuleInfo> modules = moduleDao.getAll();
				if (modules != null && !modules.isEmpty()) {
					for (YydModuleInfo m : modules) {
						String mUrl = m.getModuleUrl();
						powerModules.add(mUrl);
					}
				}
			} else {
				Set<YydRoleInfo> roles = admin.getRoles();
				if (roles != null && !roles.isEmpty()) {
					for (YydRoleInfo r : roles) {
						Set<YydModuleInfo> modules = r.getModules();
						if (modules != null && !modules.isEmpty()) {
							for (YydModuleInfo m : modules) {
								String mUrl = m.getModuleUrl();
								powerModules.add(mUrl);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return powerModules;
	}

	public boolean checkPasswd(Long adminId, String password) {
		try {
			YydAdminInfo admin = adminDao.get(adminId);
			if (admin != null) {
				if (MD5.checkpw(password, admin.getPassword(), Long.toString(admin.getId()))) {
					return true;
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

}
