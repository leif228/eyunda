package com.hangyi.eyunda.service.portal.login;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.event.OnlineStatusCode;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.dao.YydAreaDao;
import com.hangyi.eyunda.dao.YydCityDao;
import com.hangyi.eyunda.dao.YydProvinceDao;
import com.hangyi.eyunda.dao.YydUserInfoDao;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.account.UserPrivilegeData;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.domain.YydProvince;
import com.hangyi.eyunda.domain.YydUserInfo;
import com.hangyi.eyunda.domain.enumeric.ImgTypeCode;
import com.hangyi.eyunda.domain.enumeric.UserPrivilegeCode;
import com.hangyi.eyunda.domain.enumeric.UserStatusCode;
import com.hangyi.eyunda.domain.enumeric.UserTypeCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.account.CompanyService;
import com.hangyi.eyunda.service.account.OperatorService;
import com.hangyi.eyunda.service.account.PrivilegeService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CopyUtil;
import com.hangyi.eyunda.util.DESHelper;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.MultipartUtil;
import com.hangyi.eyunda.util.SessionUtil;

import jodd.util.StringUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class UserService {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydUserInfoDao userDao;

	@Autowired
	private YydProvinceDao provinceDao;
	@Autowired
	private YydCityDao cityDao;
	@Autowired
	private YydAreaDao areaDao;

	@Autowired
	private CompanyService companyService;
	@Autowired
	private PrivilegeService privilegeService;
	@Autowired
	private OperatorService operatorService;

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private JedisPool redisPool;

	public Page<UserData> getPageData(Page<UserData> pageData, Long compId, Long deptId, String keyWords) {
		List<UserData> userDatas = new ArrayList<UserData>();
		Page<YydUserInfo> page = userDao.getPageData(compId, deptId, keyWords, pageData.getPageNo(),
				pageData.getPageSize());
		if (!page.getResult().isEmpty()) {
			for (YydUserInfo user : page.getResult()) {
				UserData userData = this.getUserData(user);
				userDatas.add(userData);
			}
		}

		CopyUtil.copyProperties(pageData, page);
		pageData.setResult(userDatas);

		return pageData;
	}

	public boolean checkPayPwd(UserData userData, String payPwd) {
		try {
			YydUserInfo yui = userDao.get(userData.getId());
			if (!MD5.checkpw(payPwd, yui.getPaypwd(), Constants.SALT_VALUE + yui.getId()))
				return false;
			else
				return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	// 检查当前用户在当前公司是否具有某种权限
	public boolean isRole(UserData userData, UserPrivilegeCode privil) {
		boolean flag = false;
		try {
			boolean hasPrivilege = privilegeService.hasPrivilege(userData.getId(),
					userData.getCurrCompanyData().getId(), privil);
			return hasPrivilege;
		} catch (Exception e) {
			logger.error(e.toString());
		}
		return flag;
	}

	// 查找当前用户在当前公司的代理人，最终是不再用该方法
	public UserData getUpData(Long compId) throws Exception {
		UserData broker = null;
		// 查找公司管理员
		List<UserPrivilegeData> upds = privilegeService.getUserPrivilegeDatas(compId, UserPrivilegeCode.manager);
		for (UserPrivilegeData upd : upds) {
			// 判断是不是代理人
			OperatorData od = operatorService.getOperatorDataByUserId(upd.getUserId());
			if (od != null) {
				broker = od.getUserData();
			}
		}

		if (broker == null)
			throw new Exception("错误！你公司还未申请成为代理人，不可进行此项操作！");

		return broker;
	}

	// 查找当前用户的当前公司
	public CompanyData getCompanyData(Long compId) {
		CompanyData currCompanyData = companyService.getCompanyData(compId);
		return currCompanyData;
	}

	// 查找当前用户的全部公司列表
	public List<CompanyData> getCompanyDatas(Long userId) {
		List<CompanyData> companyDatas = companyService.getCompanyDatas(userId);
		return companyDatas;
	}

	public int getSumUsers() {
		try {
			Integer sumUsers = userDao.getStatSumUser(Calendar.getInstance());
			return sumUsers;
		} catch (Exception e) {
			return 0;
		}
	}

	public UserData getByCookie(String uck) {
		Long id = 0L;
		String loginToken = "";
		try {
			String[] ss = uck.split(",");

			id = Long.parseLong(ss[0]);
			loginToken = ss[1];
		} catch (Exception nfe) {
			return null;
		}

		UserData userData = this.getById(id);
		if (userData != null) {
			if (this.valiLoginToken(userData, loginToken)) {
				return userData;
			} else {
				return null;
			}
		} else
			return null;
	}

	public UserData getUserData(Long userId) {
		return this.getUserData(userDao.get(userId));
	}

	private String getPcaName(String code) {
		if (code != null && code.length() == 6) {
			String areaCode = code;
			String cityCode = code.substring(0, 4);
			String provinceCode = code.substring(0, 2);
			YydArea area = areaDao.getByCode(areaCode);
			YydCity city = cityDao.getByCode(cityCode);
			YydProvince province = provinceDao.getByCode(provinceCode);

			String pcaName = "";
			if (province != null)
				pcaName += province.getProvinceName();
			if (city != null)
				pcaName += city.getCityName();
			if (area != null)
				pcaName += area.getAreaName();

			return pcaName;
		} else
			return null;
	}

	public UserPrivilegeCode getUserRole(Long userId) {
		return userDao.getUserRole(userId);
	}

	public UserData getUserData(YydUserInfo user) {
		if (user != null) {
			UserData userData = new UserData();
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

			userData.setUnitAddr(this.getPcaName(userData.getAreaCode()) + userData.getAddress());

			if (user.getTrueName() == null || "".equals(user.getTrueName()))
				userData.setTrueName(user.getLoginName());

			OnlineUser ou = onlineUserRecorder.getOnlineUser(userData.getId());
			if (ou != null && ou.isPresence())
				userData.setOnlineStatus(ou.getOnlineStatus());
			else
				userData.setOnlineStatus(OnlineStatusCode.ofline);

			return userData;
		}
		return null;

	}

	public UserData getById(Long id) {
		YydUserInfo user = userDao.get(id);
		return this.getUserData(user);
	}

	public UserData getByLoginName(String loginName) {
		YydUserInfo user = userDao.getByLoginName(loginName);
		return this.getUserData(user);
	}

	public UserData getByEmail(String email) {
		YydUserInfo user = userDao.findUniqueBy("email", email);
		return this.getUserData(user);
	}

	public UserData getBySimCardNo(String simCardNo) {
		YydUserInfo user = userDao.findUniqueBy("simCardNo", simCardNo);
		return this.getUserData(user);
	}

	public List<UserData> frontFindUsers(String userInfo) {
		List<UserData> userDatas = new ArrayList<UserData>();

		List<YydUserInfo> users = userDao.frontFindUsers(userInfo);
		if (!users.isEmpty()) {
			for (YydUserInfo user : users) {
				UserData userData = this.getUserData(user);
				userDatas.add(userData);
			}
		}

		return userDatas;
	}

	public Page<UserData> backFindUserDatas(String userInfo, UserStatusCode status, int pageNo, int pageSize) {
		Page<YydUserInfo> pg = userDao.backFindUserDatas(userInfo, status, pageNo, pageSize);

		List<UserData> userDatas = new ArrayList<UserData>();
		if (pg.getResult() != null && !pg.getResult().isEmpty())
			for (YydUserInfo yydUser : pg.getResult())
				userDatas.add(this.getUserData(yydUser));

		Page<UserData> pageData = new Page<UserData>();
		CopyUtil.copyProperties(pageData, pg);
		pageData.setResult(userDatas);

		return pageData;
	}

	/** 通过用户信息生成一个令牌 */
	public String getLoginToken(UserData userData) {
		StringBuffer tokensb = new StringBuffer();
		tokensb.append("UserInfo").append(userData.getLoginName()).append(userData.getId())
				.append(userData.getCreateTime());

		return MD5.toMD5(tokensb.toString());
	}

	/** 验证登录令牌 */
	public boolean valiLoginToken(UserData userData, String loginToken) {
		String valiToken = getLoginToken(userData);

		return loginToken.equals(valiToken);
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
			YydUserInfo user = userDao.getByLoginName(loginName);
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
			YydUserInfo user = userDao.findUniqueBy("simCardNo", simCardNo);
			if (user == null) {
				return ResultType.USER_NOTEXIST;
			}

			// 检查密码
			if (!bindingCode.equals(SessionUtil.getBindingCode(user.getId(), user.getLoginName()))) {
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

	public ResultType register(UserData userData) throws Exception {
		// 登录名为空
		if (StringUtil.isBlank(userData.getLoginName()))
			userData.setLoginName(userData.getMobile());

		// 密码为空
		if (StringUtil.isBlank(userData.getPassword()))
			throw new Exception("错误！" + ResultType.PASSWD_NULL.getDescription());

		// 检查登录名是否唯一
		YydUserInfo userInfo = userDao.getByLoginName(userData.getLoginName());
		if (userInfo != null)
			throw new Exception("错误！" + ResultType.LOGINNAME_ISUSED.getDescription());

		// 检查邮箱是否唯一
		if (!StringUtil.isBlank(userData.getEmail())) {
			userInfo = userDao.getByLoginName(userData.getEmail());
			if (userInfo != null)
				throw new Exception("错误！" + ResultType.EMAIL_ISUSED.getDescription());
		}

		userInfo = new YydUserInfo();

		userInfo.setUserType(userData.getUserType());
		userInfo.setLoginName(userData.getLoginName());
		userInfo.setTrueName(userData.getTrueName());
		userInfo.setPassword("");

		if (userData.getNickName() != null && !"".equals(userData.getNickName()))
			userInfo.setNickName(userData.getNickName());
		else
			userInfo.setNickName(userData.getTrueName());

		userInfo.setEmail(userData.getEmail());
		userInfo.setMobile(DESHelper.DoDES(userData.getMobile(), Constants.SALT_VALUE, DESHelper.ENCRYPT_MODE));
		userInfo.setUserLogo(userData.getUserLogo());
		userInfo.setSignature(userData.getSignature());
		userInfo.setStamp(userData.getStamp());
		userInfo.setSimCardNo(userData.getSimCardNo());

		userInfo.setPostCode(userData.getPostCode());
		userInfo.setAreaCode(userData.getAreaCode());
		userInfo.setAddress(userData.getAddress());

		userInfo.setCreateTime(Calendar.getInstance());
		// 暂时直接激活状态
		userInfo.setStatus(UserStatusCode.activity);
		userInfo.setUserType(userData.getUserType());

		userDao.save(userInfo);

		String encryptPwd = MD5.toMD5(userData.getPassword() + Constants.SALT_VALUE + userInfo.getId());
		userInfo.setPassword(encryptPwd);
		userDao.save(userInfo);

		userData.setId(userInfo.getId());

		// 注册企业用户后建立公司、部门、分配权限
		if (userData.getUserType() == UserTypeCode.enterprise) {
			companyService.compRegister(userData);
		}

		return ResultType.SUCCESS;
	}

	public void updateSimCardNo(Long userId, String simCardNo) {
		try {
			List<YydUserInfo> us = userDao.getBySimCardNo(simCardNo);
			for (YydUserInfo u : us) {
				u.setSimCardNo("");
				userDao.save(u);
			}

			YydUserInfo userInfo = userDao.get(userId);
			if (userInfo != null) {
				userInfo.setSimCardNo(simCardNo);
				userDao.save(userInfo);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return;
		}
	}

	public ResultType update(UserData userData) {
		try {
			// 登录名为空
			if (StringUtil.isBlank(userData.getLoginName()))
				return ResultType.LOGINNAME_NULL;

			YydUserInfo userInfo = userDao.get(userData.getId());
			if (userInfo == null)
				return ResultType.USER_NOTEXIST;

			// 检查登录名是否唯一
			if (userDao.getByLoginName(userData.getLoginName(), userInfo.getId()) != null)
				return ResultType.LOGINNAME_ISUSED;

			// 检查邮箱是否唯一
			if (userDao.getByLoginName(userData.getEmail(), userInfo.getId()) != null)
				return ResultType.EMAIL_ISUSED;

			// 修改公司名称
			if (userInfo.getUserType() == UserTypeCode.enterprise) {
				String oldName = userInfo.getTrueName();
				String newName = userData.getTrueName();
				if (oldName != null && newName != null && !newName.equals(oldName)) {
					companyService.updateCompName(oldName, newName);
				}
			}

			userInfo.setLoginName(userData.getLoginName());
			userInfo.setTrueName(userData.getTrueName());
			userInfo.setNickName(userData.getTrueName());
			userInfo.setEmail(userData.getEmail());
			userInfo.setAreaCode(userData.getAreaCode());
			userInfo.setUserType(userData.getUserType());
			userInfo.setAddress(userData.getAddress());
			// 这里添加新增注册信息

			if (!"".equals(userData.getUserLogo()))
				userInfo.setUserLogo(userData.getUserLogo());
			if (!"".equals(userData.getSignature()))
				userInfo.setSignature(userData.getSignature());
			if (!"".equals(userData.getStamp()))
				userInfo.setStamp(userData.getStamp());

			// 如果上传了Logo
			MultipartFile mpf = userData.getUserLogoFile();
			if (mpf != null && !mpf.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(userData.getId());
				String prefix = "Logo";
				String url = MultipartUtil.uploadFile(mpf, realPath, relativePath, prefix);
				// 修改数据库中文件路径
				userInfo.setUserLogo(url);
			}

			// 如果上传了个性签名
			MultipartFile mpf2 = userData.getSignatureFile();
			if (mpf2 != null && !mpf2.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(userData.getId());
				String prefix = "Sign";
				String url = MultipartUtil.uploadFile(mpf2, realPath, relativePath, prefix);
				// 如果存在旧文件删除之
				/*
				 * String oldPathFile = userInfo.getSignature(); File oldF = new
				 * File(realPath + oldPathFile); if (oldF.exists())
				 * oldF.delete();
				 */
				// 修改数据库中文件路径
				userInfo.setSignature(url);
			}

			// 如果上传了Logo
			MultipartFile mpf3 = userData.getStampFile();
			if (mpf3 != null && !mpf3.isEmpty()) {
				// 拷贝新文件到指定位置
				String realPath = Constants.SHARE_DIR;
				String relativePath = ShareDirService.getUserDir(userData.getId());
				String prefix = "Stmp";
				String url = MultipartUtil.uploadFile(mpf3, realPath, relativePath, prefix);
				// 如果存在旧文件删除之
				/*
				 * String oldPathFile = userInfo.getStamp(); File oldF = new
				 * File(realPath + oldPathFile); if (oldF.exists())
				 * oldF.delete();
				 */
				// 修改数据库中文件路径
				userInfo.setStamp(url);
			}

			userDao.save(userInfo);

			return ResultType.SUCCESS;
		} catch (Exception e) {
			logger.error(e.getMessage());
			return ResultType.FAILURE;
		}
	}

	// 检查上传的图片格式和图片大小
	public boolean checkUploadFile(UserData userData) {
		boolean flag = true;
		try {
			flag = this.checkUploadFile(userData.getUserLogoFile());
			if (!flag)
				return flag;

			flag = this.checkUploadFile(userData.getSignatureFile());
			if (!flag)
				return flag;

			flag = this.checkUploadFile(userData.getStampFile());
			if (!flag)
				return flag;

		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		return flag;
	}

	// 检查上传文件的格式和大小
	private boolean checkUploadFile(MultipartFile multipartFile) {
		try {
			// 如果上传了文件且格式和大小正确就返回true
			if (multipartFile != null && !multipartFile.isEmpty()) {
				String ext = FilenameUtils.getExtension(multipartFile.getOriginalFilename());
				if (ImgTypeCode.contains(ext) && multipartFile.getSize() <= Long.parseLong(Constants.IconSize)) {
					return true;
				} else {
					return false;
				}
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
		}
		// 如果没有上传文件直接返回true，因为可以不用上传该文件
		return true;
	}

	public ResultType changePasswd(Long userId, String password, String newpassword) {
		try {
			// 密码为空
			if (StringUtil.isBlank(password))
				return ResultType.PASSWD_NULL;

			// 检查是否存在这个用户
			YydUserInfo user = userDao.get(userId);
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
			YydUserInfo user = userDao.get(id);
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
			userDao.delete(userId);

			return true;
		} catch (Exception e) {
			logger.error(e.toString());
			return false;
		}
	}

	// 激活
	public boolean activity(Long id) {
		YydUserInfo user = userDao.get(id);
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
		YydUserInfo user = userDao.get(id);
		if (user != null) {
			if (user.getStatus() == UserStatusCode.activity) {
				user.setStatus(UserStatusCode.inactivity);
				userDao.save(user);
				return true;
			}
		}
		return false;
	}

	// 从redis里面找登录用户，找到则返回userDate
	public UserData getUserFromRedis(String code) {
		Jedis jedis = null;
		String user = "";
		try {
			jedis = redisPool.getResource();
			user = jedis.hget(MessageConstants.QCODE_QUEUE, code);
		} catch (JedisConnectionException jedisEx) {
			redisPool.returnBrokenResource(jedis);
		} finally {
			if (jedis != null)
				redisPool.returnResource(jedis);
		}
		UserData userData = null;
		if (!user.equals("")) {
			userData = this.getById(Long.parseLong(user));
		}
		return userData;
	}
	/**
	 * 通过微信openId查询用户
	 * @param openId
	 * @return
	 */
	public UserData getUserByOpenId(String openId){
		UserData ud = null;
		//TODO:数据库查询
		return ud;
	}
}
