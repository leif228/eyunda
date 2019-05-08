package com.hangyi.eyunda.service.hyquan;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.dao.HyqCompanyDao;
import com.hangyi.eyunda.dao.HyqDepartmentDao;
import com.hangyi.eyunda.dao.HyqUserDeptDao;
import com.hangyi.eyunda.dao.HyqUserInfoDao;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.HyqCompany;
import com.hangyi.eyunda.domain.HyqDepartment;
import com.hangyi.eyunda.domain.HyqUserDept;
import com.hangyi.eyunda.domain.HyqUserInfo;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.DESHelper;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.MultipartUtil;
import com.hangyi.eyunda.util.SessionUtil;

import jodd.util.StringUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class HyqUserService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private HyqUserInfoDao userDao;
	@Autowired
	private HyqUserDeptDao userDeptDao;
	@Autowired
	private HyqCompanyDao companyDao;
	@Autowired
	private HyqDepartmentDao departmentDao;

	public HyqUserData getUserData(HyqUserInfo user) {
		if (user != null) {
			HyqUserData userData = new HyqUserData();
			CopyUtil.copyProperties(userData, user);

			// 如果不存在logo文件
			String realPath = Constants.SHARE_DIR;
			String logoPathFile = userData.getUserLogo();
			if (logoPathFile == null || "".equals(logoPathFile) || !new File(realPath + logoPathFile).exists()) {
				userData.setUserLogo("/default/user.jpg");
			}

			userData.setCreateTime(CalendarUtil.toYYYY_MM_DD_HH_MM(user.getCreateTime()));

			userData.setPassword("");
			userData.setPaypwd("");
			userData.setSimCardNo("");
			userData.setBindingCode("");

			userData.setMobile(DESHelper.DoDES(user.getMobile(), Constants.SALT_VALUE, DESHelper.DECRYPT_MODE));
			userData.setIdCardNo(DESHelper.DoDES(user.getIdCardNo(), Constants.SALT_VALUE, DESHelper.DECRYPT_MODE));

			return userData;
		}
		return null;
	}

	public boolean canMdfCompany(Long userId, Long compId) {
		HyqCompany company = companyDao.get(compId);
		if (company != null)
			return company.getManagerId().equals(userId);
		else
			return false;
	}

	public boolean canMdfDepartment(Long userId, Long deptId) {
		HyqDepartment department = departmentDao.get(deptId);
		if (department != null)
			return this.canMdfCompany(userId, department.getCompId());
		else
			return false;
	}

	public HyqUserData getByCookie(String uck) {
		Long id = 0L;
		String loginToken = "";
		try {
			String[] ss = uck.split(",");

			id = Long.parseLong(ss[0]);
			loginToken = ss[1];
		} catch (Exception nfe) {
			return null;
		}

		HyqUserData userData = this.getById(id);
		if (userData != null) {
			if (this.valiLoginToken(userData, loginToken)) {
				return userData;
			} else {
				return null;
			}
		} else
			return null;
	}

	/** 验证登录令牌 */
	public boolean valiLoginToken(HyqUserData userData, String loginToken) {
		String valiToken = getLoginToken(userData);

		return loginToken.equals(valiToken);
	}

	/** 通过用户信息生成一个令牌 */
	public String getLoginToken(HyqUserData userData) {
		StringBuffer tokensb = new StringBuffer();
		tokensb.append("UserInfo").append(userData.getLoginName()).append(userData.getId())
				.append(userData.getCreateTime());

		return MD5.toMD5(tokensb.toString());
	}

	public HyqUserData getById(Long id) {
		HyqUserInfo user = userDao.get(id);
		return this.getUserData(user);
	}

	public HyqUserData getByLoginName(String loginName) {
		HyqUserInfo user = userDao.getByLoginName(loginName);
		return this.getUserData(user);
	}

	public HyqUserData getBySimCardNo(String simCardNo) {
		HyqUserInfo user = userDao.getBySimCardNo(simCardNo);
		return this.getUserData(user);
	}

	public HyqUserData getByEmail(String email) {
		HyqUserInfo user = userDao.getByEmail(email);
		return this.getUserData(user);
	}

	public HyqUserData getByMobile(String mobile) {
		HyqUserInfo user = userDao.getByMobile(mobile);
		return this.getUserData(user);
	}

	public Page<HyqUserData> getUserPageData(Page<HyqUserData> pageData, String keyWords, UserStatusCode status) {
		List<HyqUserData> userDatas = new ArrayList<HyqUserData>();

		Page<HyqUserInfo> page = userDao.getUserPage(pageData.getPageNo(), pageData.getPageSize(), keyWords, status);

		if (!page.getResult().isEmpty()) {
			for (HyqUserInfo user : page.getResult()) {
				HyqUserData userData = this.getUserData(user);
				userDatas.add(userData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(userDatas);

		return pageData;
	}

	public boolean checkPayPwd(HyqUserData userData, String payPwd) {
		try {
			HyqUserInfo user = userDao.get(userData.getId());
			if (!MD5.checkpw(payPwd, user.getPaypwd(), Constants.SALT_VALUE + user.getId()))
				return false;
			else
				return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	public ResultType login(String loginName, String password) {
		try {
			// 登录名为空
			if (StringUtil.isBlank(loginName))
				return ResultType.LOGINNAME_NULL;

			// 密码为空
			if (StringUtil.isBlank(password))
				return ResultType.PASSWD_NULL;

			// 检查是否存在这个用户
			HyqUserInfo user = userDao.getByLoginName(loginName);
			if (user == null) {
				return ResultType.USER_NOTEXIST;
			}

			// 检查密码
			if (!MD5.checkpw(password, user.getPassword(), Constants.SALT_VALUE + user.getId())) {
				return ResultType.PASSWD_ERROR;
			}

			// 检查激活状态
			if (user.getStatus() == UserStatusCode.inactivity) {
				return ResultType.USER_INACTIVATE;
			}

			return ResultType.SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResultType.FAILURE;
		}
	}

	public ResultType autoLogin(String simCardNo, String bindingCode) {
		try {
			// 登录名为空
			if (StringUtil.isBlank(simCardNo))
				return ResultType.LOGINNAME_NULL;

			// 密码为空
			if (StringUtil.isBlank(bindingCode))
				return ResultType.PASSWD_NULL;

			// 检查是否存在这个用户
			HyqUserInfo user = userDao.getBySimCardNo(simCardNo);
			if (user == null) {
				return ResultType.USER_NOTEXIST;
			}

			// 检查密码
			if (!bindingCode.equals(SessionUtil.getBindingCode(user.getId(), user.getLoginName()))) {
				// System.out.println("bindingCode=" + bindingCode);
				// System.out.println("SessionUtil=" +
				// SessionUtil.getBindingCode(user.getId(),
				// user.getLoginName()));
				return ResultType.PASSWD_ERROR;
			}

			// 检查激活状态
			if (user.getStatus() == UserStatusCode.inactivity) {
				return ResultType.USER_INACTIVATE;
			}

			return ResultType.SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResultType.FAILURE;
		}
	}

	public ResultType register(HyqUserData userData) throws Exception {
		// 登录名为空
		if (StringUtil.isBlank(userData.getLoginName()))
			userData.setLoginName(userData.getMobile());

		// 密码为空
		if (StringUtil.isBlank(userData.getPassword()))
			throw new Exception("错误！" + ResultType.PASSWD_NULL.getDescription());

		// 检查登录名是否唯一
		HyqUserInfo user = userDao.getByLoginName(userData.getLoginName());
		if (user != null)
			throw new Exception("错误！" + ResultType.LOGINNAME_ISUSED.getDescription());

		user = new HyqUserInfo();

		CopyUtil.copyProperties(user, userData,
				new String[] { "createTime", "password", "paypwd", "mobile", "idCardNo" });
		user.setMobile(DESHelper.DoDES(userData.getMobile(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
//		user.setIdCardNo(DESHelper.DoDES(userData.getIdCardNo(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
		user.setCreateTime(Calendar.getInstance());

		if (user.getLoginName() == null || "".equals(user.getLoginName()))
			user.setLoginName(userData.getMobile());
		if (user.getTrueName() == null || "".equals(user.getTrueName()))
			user.setTrueName(userData.getLoginName());
		if (user.getNickName() == null || "".equals(user.getNickName()))
			user.setNickName(user.getTrueName());

		userDao.save(user);

		String encryptPwd = MD5.toMD5(userData.getPassword() + Constants.SALT_VALUE + user.getId());
		user.setPassword(encryptPwd);
		userDao.save(user);

		userData.setId(user.getId());

		return ResultType.SUCCESS;
	}

	public void updateSimCardNo(Long userId, String simCardNo) {
		try {
			HyqUserInfo user = userDao.getBySimCardNo(simCardNo);
			if (user != null) {
				user.setSimCardNo("");
				userDao.save(user);
			}

			HyqUserInfo userInfo = userDao.get(userId);
			if (userInfo != null) {
				userInfo.setSimCardNo(simCardNo);
				userDao.save(userInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return;
		}
	}

	public ResultType update(HyqUserData userData) {
		try {
			// 登录名为空
			if (StringUtil.isBlank(userData.getLoginName()))
				return ResultType.LOGINNAME_NULL;

			HyqUserInfo user = userDao.get(userData.getId());
			if (user == null)
				return ResultType.USER_NOTEXIST;

			// 检查登录名是否唯一
			if (userDao.getByLoginName(userData.getLoginName(), user.getId()) != null)
				return ResultType.LOGINNAME_ISUSED;

			CopyUtil.copyProperties(user, userData,
					new String[] { "createTime", "password", "paypwd", "role", "status", "simCardNo", "userLogo" });
			user.setMobile(DESHelper.DoDES(userData.getMobile(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
			user.setIdCardNo(DESHelper.DoDES(userData.getIdCardNo(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
			// user.setCreateTime(Calendar.getInstance());

			if (user.getLoginName() == null || "".equals(user.getLoginName()))
				user.setLoginName(userData.getMobile());
			if (user.getTrueName() == null || "".equals(user.getTrueName()))
				user.setTrueName(userData.getLoginName());
			if (user.getNickName() == null || "".equals(user.getNickName()))
				user.setNickName(user.getTrueName());

			userDao.save(user);

			return ResultType.SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResultType.FAILURE;
		}
	}

	public ResultType changePasswd(Long userId, String password, String newpassword) {
		try {
			// 密码为空
			if (StringUtil.isBlank(password))
				return ResultType.PASSWD_NULL;

			// 检查是否存在这个用户
			HyqUserInfo user = userDao.get(userId);
			if (user == null)
				return ResultType.USER_NOTEXIST;

			// 检查密码
			if (!MD5.checkpw(password, user.getPassword(), Constants.SALT_VALUE + user.getId()))
				return ResultType.CHANGEPASSWD_ERROR;

			// 检查激活状态
			if (user.getStatus() == UserStatusCode.inactivity)
				return ResultType.USER_INACTIVATE;

			String encryptPwd = MD5.toMD5(newpassword + Constants.SALT_VALUE + user.getId());
			user.setPassword(encryptPwd);
			userDao.save(user);

			return ResultType.SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResultType.FAILURE;
		}
	}

	public ResultType resetPasswd(Long id, String password, String password2) {
		try {
			// 密码为空
			if (StringUtil.isBlank(password) || StringUtil.isBlank(password2))
				return ResultType.PASSWD_NULL;

			// 输入密码和确认密码不一致
			if (!password.equals(password2))
				return ResultType.FAILURE;

			// 检查是否存在这个用户
			HyqUserInfo user = userDao.get(id);
			if (user == null)
				return ResultType.USER_NOTEXIST;

			// 检查激活状态
			if (user.getStatus() == UserStatusCode.inactivity)
				return ResultType.USER_INACTIVATE;

			String encryptPwd = MD5.toMD5(password + Constants.SALT_VALUE + user.getId());
			user.setPassword(encryptPwd);
			userDao.save(user);

			return ResultType.SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResultType.FAILURE;
		}
	}

	public enum ResultType {
		FAILURE("0", "失败"),

		SUCCESS("1", "成功"),

		LOGINNAME_NULL("2", "登录名为空"),

		PASSWD_NULL("3", "密码为空"),

		LOGINNAME_ISUSED("4", "手机号作为登录名已被注册"),

		EMAIL_ISUSED("5", "邮箱已被注册"),

		CAPTCHA_ERROR("6", "验证码输入错误"),

		USER_NOTEXIST("7", "用户不存在"),

		PASSWD_ERROR("8", "登录名或密码错误"),

		USER_INACTIVATE("9", "用户未激活"),

		USER_LOGON("10", "用户已经在别处登录"),

		CHANGEPASSWD_ERROR("11", "原密码错误"),

		REGISTER_PINGAN_ERROR("12", "注册平安账户失败");

		private String code;
		private String description;

		public String getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}

		private ResultType(String code, String description) {
			this.code = code;
			this.description = description;
		}
	}

	// 后台维护用，前台不要用
	public boolean deleteUser(Long userId) {
		try {
			// 用户相关信息也要删除
			userDao.delete(userId);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	// 激活
	public boolean activity(Long id) {
		HyqUserInfo user = userDao.get(id);
		if (user != null) {
			if (user.getStatus() == UserStatusCode.inactivity) {
				user.setStatus(UserStatusCode.activity);
				userDao.save(user);
				return true;
			}
		}
		return false;
	}

	// 取消激活
	public boolean unActivity(Long id) {
		HyqUserInfo user = userDao.get(id);
		if (user != null) {
			if (user.getStatus() == UserStatusCode.activity) {
				user.setStatus(UserStatusCode.inactivity);
				userDao.save(user);
				return true;
			}
		}
		return false;
	}

	public String uploadLogo(Long userId, MultipartFile logoMpf) throws Exception {
		// 如果上传了Image
		if (logoMpf != null && !logoMpf.isEmpty()) {
			HyqUserInfo user = userDao.get(userId);
			if (user == null) {
				throw new Exception("错误！用户信息未找到，上传用户头像失败。");
			}

			// 拷贝新文件到指定位置
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getUserDir(userId);
			String prefix = "UserLogo";
			String url = MultipartUtil.uploadFile(logoMpf, realPath, relativePath, prefix);

			File newF = new File(realPath + url);
			if (newF.exists()) {
				user.setUserLogo(url);
				userDao.save(user);

				return url;
			} else {
				throw new Exception("错误！用户头像文件上传失败。");
			}
		} else {
			throw new Exception("错误！未选择图片文件进行上传。");
		}
	}

	public List<HyqUserData> findSixUsers(String userInfo) {
		List<HyqUserData> userDatas = new ArrayList<HyqUserData>();

		List<HyqUserInfo> users = userDao.getUserPage(1, 6, userInfo, UserStatusCode.activity).getResult();
		if (!users.isEmpty()) {
			for (HyqUserInfo user : users) {
				HyqUserData userData = this.getUserData(user);
				userDatas.add(userData);
			}
		}

		return userDatas;
	}

	public List<HyqUserData> getByCompIdKeyWords(Long compId, String keyWords) {
		List<HyqUserData> userDatas = new ArrayList<HyqUserData>();

		List<HyqUserDept> userDepts = userDeptDao.getByCompIdKeyWords(compId, keyWords, UserStatusCode.activity);
		if (!userDepts.isEmpty()) {
			for (HyqUserDept userDept : userDepts) {
				HyqUserData userData = this.getById(userDept.getUserId());
				userDatas.add(userData);
			}
		}

		return userDatas;
	}

}
