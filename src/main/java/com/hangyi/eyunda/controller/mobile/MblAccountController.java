package com.hangyi.eyunda.controller.mobile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.PCAreaService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.account.OperatorService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.portal.login.UserService.ResultType;
import com.hangyi.eyunda.service.qrcode.QrcodeService;
import com.hangyi.eyunda.util.Constants;

@Controller
@RequestMapping("/mobile/account")
public class MblAccountController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private OnlineUserRecorder onlineUserRecorder;
	@Autowired
	private UserService userService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PCAreaService pCAreaService;
	@Autowired
	private QrcodeService qrcodeService;

	// 获取当前用户对象，含用户所在公司列表及当前公司
	private UserData getLoginUserData(HttpServletRequest request) throws Exception {
		String sessionId = ServletRequestUtils.getStringParameter(request, MblLoginController.MBL_SESSION_ID, "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session已丢失请重新登录！");

		UserData userData = userService.getById(ou.getId());
		if (userData == null)
			throw new Exception("未登录不能进行此项操作！");

		String strCompId = ServletRequestUtils.getStringParameter(request, MblLoginController.CURR_COMP_ID, null);
		if (strCompId == null)
			strCompId = "0"; // throw new Exception("你还不属于任何公司，请先注册公司并加入!");
		Long compId = Long.parseLong(strCompId);

		CompanyData currCompanyData = userService.getCompanyData(compId);
		List<CompanyData> companyDatas = userService.getCompanyDatas(userData.getId());
		userData.setCurrCompanyData(currCompanyData);
		userData.setCompanyDatas(companyDatas);

		return userData;
	}

	@RequestMapping(value = "/myAccount", method = RequestMethod.GET)
	public void myAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			Map<String, Object> content = new HashMap<String, Object>();
			// 用户钱包账号
			AccountData add = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			content.put("accountNo", add != null ? add.getAccountNo() : "");
			if (add == null)
				add = new AccountData();

			content.put("syncMobile", YesNoCode.no.toString());
			content.put("userData", userData);

			// 申请成为船代信息
			OperatorData operatorData = operatorService.getSelfOperatorDataByUserId(userData.getId());
			if (operatorData == null) {
				operatorData = new OperatorData();
				operatorData.setUserData(new UserData());
			}
			content.put("operatorData", operatorData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myAccount/saveBaseInfo", method = { RequestMethod.POST })
	public void saveBaseInfo(UserData userData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData ud = this.getLoginUserData(request);
			if (!ud.getId().equals(userData.getId()))
				throw new Exception("错误！非法的用户操作。");

			ResultType result = userService.update(userData);

			if (result == ResultType.SUCCESS) {
				UserData user = userService.getById(userData.getId());
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
				map.put(JsonResponser.CONTENT, user);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myAccount/saveApply", method = { RequestMethod.GET, RequestMethod.POST })
	public void saveApply(OperatorData operatorData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData ud = this.getLoginUserData(request);
			if (!ud.getId().equals(operatorData.getUserId()))
				throw new Exception("错误！非法的用户操作。");

			operatorData.setUserData(ud);

			boolean result = operatorService.update(operatorData);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "提交成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myAccount/savePasswd", method = { RequestMethod.POST })
	public void savePasswd(Long id, String password, String newpassword, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData ud = this.getLoginUserData(request);
			if (!ud.getId().equals(id))
				throw new Exception("错误！非法的用户操作。");

			ResultType result = userService.changePasswd(id, password, newpassword);

			if (result == ResultType.SUCCESS) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/inviteOwnerRegQrcode", method = { RequestMethod.POST, RequestMethod.GET })
	public void inviteOwnerRegQrcode(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData selfUserData = this.getLoginUserData(request);

			Long uid = selfUserData.getId();
			String site = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
					+ request.getContextPath();
			String url = site + "/portal/login/register";
			String fileName = "register-qrcode.jpg";
			if (uid > 0) {
				url = url + "?u=" + uid;
				fileName = "register-qrcode-" + uid + ".jpg";
			}
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getQcodeDir(uid) + ShareDirService.FILE_SEPA_SIGN;
			String path = realPath + relativePath;

			qrcodeService.encode(url, path, fileName, false, 800, 800, "jpg");

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("imgUrl", relativePath + fileName);
			content.put("inviteUrl", url);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myAccount/getpca", method = { RequestMethod.GET })
	public void getpca(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			UserData userData = this.getLoginUserData(request);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			List<YydCity> currc = pCAreaService.getCurrAllCitys(userData.getAreaCode());
			if (currc != null)
				content.put("currentc", currc);
			List<YydArea> curra = pCAreaService.getCurrAllArea(userData.getAreaCode());
			if (curra != null)
				content.put("currenta", curra);
			content.put("allProvince", pCAreaService.getCurrAllProvince(userData.getAreaCode()));

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}

		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getCitys", method = { RequestMethod.GET })
	public void getCitys(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			String proCode = ServletRequestUtils.getStringParameter(request, "proCode", "");
			List<YydCity> citys = pCAreaService.getCitysByProCode(proCode);
			List<YydArea> areas = null;
			if (citys.size() > 0) {
				areas = pCAreaService.getAreasByCityCode(citys.get(0).getCityNo());
			}
			content.put("citys", citys);
			content.put("areas", areas == null ? new ArrayList<YydArea>() : areas);
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	@RequestMapping(value = "/getAreas", method = { RequestMethod.GET })
	public void getAreas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", "");
			List<YydArea> areas = pCAreaService.getAreasByCityCode(cityCode);

			map.put(JsonResponser.CONTENT, areas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
