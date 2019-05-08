package com.hangyi.eyunda.controller.space;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.CompanyData;
import com.hangyi.eyunda.data.account.OperatorData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.wallet.UserBankData;
import com.hangyi.eyunda.domain.PubPayCity;
import com.hangyi.eyunda.domain.PubPayNode;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.PCAreaService;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.account.OperatorService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.portal.login.UserService.ResultType;
import com.hangyi.eyunda.service.pubpay.PubPayCityService;
import com.hangyi.eyunda.service.pubpay.PubPayNodeService;
import com.hangyi.eyunda.service.qrcode.QrcodeService;
import com.hangyi.eyunda.service.wallet.UserBankCardService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/account")
public class MyAccountController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private OperatorService operatorService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PCAreaService pCAreaService;
	@Autowired
	private UserBankCardService walletBankService;
	@Autowired
	private PubPayCityService pubPayCityService;
	@Autowired
	private PubPayNodeService pubPayNodeService;
	@Autowired
	private QrcodeService qrcodeService;

	// 获取当前用户对象，含用户所在公司列表及当前公司
	private UserData getLoginUserData(HttpServletRequest request) throws Exception {
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录不能进行此项操作!");

		String strCompId = CookieUtil.getCookieValue(request, LoginController.CURR_COMP_ID);
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
	public ModelAndView myAccount(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-account");

		String tab = ServletRequestUtils.getStringParameter(request, "tab", "user-baseinfo");
		mav.addObject("tab", tab);

		UserData userData = this.getLoginUserData(request);
		mav.addObject("userData", userData);

		// 用户钱包账号
		AccountData add = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		if (add == null)
			add = new AccountData();

		// 省市区
		List<YydCity> currc = pCAreaService.getCurrAllCitys(userData.getAreaCode());
		mav.addObject("currc", currc != null ? currc : new ArrayList<YydCity>());
		List<YydArea> curra = pCAreaService.getCurrAllArea(userData.getAreaCode());
		mav.addObject("curra", curra != null ? curra : new ArrayList<YydArea>());
		mav.addObject("allProvince", pCAreaService.getCurrAllProvince(userData.getAreaCode()));

		// 申请成为船代信息
		OperatorData operatorData = operatorService.getSelfOperatorDataByUserId(userData.getId());
		if (operatorData == null) {
			operatorData = new OperatorData();
			operatorData.setUserData(new UserData());
		}
		mav.addObject("operatorData", operatorData);

		// 用户是否成功绑定提现银行卡（只能绑定一张提现卡）
		List<UserBankData> ubds = walletBankService.getBankDatas(userData.getId());
		if (ubds.isEmpty()) {
			mav.addObject("settedPW", YesNoCode.no.toString());

			// 银行信息
			BankCode[] banks = BankCode.values();
			banks = Arrays.copyOfRange(banks, 1, BankCode.values().length);
			BankCode[] banks2 = new BankCode[banks.length - 1];
			int i = 0;
			for (BankCode bc : banks) {
				if (!bc.name().equals(BankCode.SPABANK.name())) {
					banks2[i] = bc;
					i++;
				}
			}
			mav.addObject("banks", banks2);

			// 开户行地区名称
			List<PubPayNode> ppns = pubPayNodeService.getAllPubPayNode();
			mav.addObject("allPro", ppns);
			List<PubPayCity> ppcs = pubPayCityService
					.getPPCByPPN(ppns.isEmpty() ? new PubPayNode().getNodeCode() : ppns.get(0).getNodeCode());
			mav.addObject("currPc", ppcs);
		} else {
			String idCode = add.getIdCode().length() > 6
					? "*** " + "*** " + add.getIdCode().substring(add.getIdCode().length() - 6) : add.getIdCode();
			String mobilePhone = add.getMobile().length() > 4
					? "*** " + "*** " + add.getMobile().substring(add.getMobile().length() - 4) : add.getMobile();
			add.setIdCode(idCode);
			add.setMobile(mobilePhone);

			mav.addObject("settedPW", YesNoCode.yes.toString());

			String cardNo = ubds.get(0).getCardNo().length() > 4
					? "*** " + "*** " + ubds.get(0).getCardNo().substring(ubds.get(0).getCardNo().length() - 4)
					: ubds.get(0).getCardNo();
			ubds.get(0).setCardNo(cardNo);

			mav.addObject("userBankData", ubds.get(0));
			mav.addObject("bindedCard", ubds.get(0).getBankCode());
		}

		mav.addObject("accountData", add);
		mav.addObject("accountNo", add.getAccountNo());

		// 菜单信息
		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_ACCOUNT);

		return mav;
	}

	@RequestMapping(value = "/myAccount/saveBaseInfo", method = { RequestMethod.POST })
	public void saveBaseInfo(UserData userData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData ud = this.getLoginUserData(request);
			if (!ud.getId().equals(userData.getId()))
				throw new Exception("错误！非法的用户操作。");

			if (userService.checkUploadFile(userData)) {
				ResultType result = userService.update(userData);
				if (result == ResultType.SUCCESS)
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				else
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, result.getDescription());
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "上传的图片格式不正确或图片大小超过1M！");
			}

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
			JsonResponser.respondWithText(response, map);
		}
		return;
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
				map.put(JsonResponser.MESSAGE, "提交成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提交失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	@RequestMapping(value = "/myAccount/savePasswd", method = { RequestMethod.GET })
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
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "提交失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	@RequestMapping(value = "/myAccount/inviteOwnerRegQrcode", method = { RequestMethod.POST, RequestMethod.GET })
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

	@RequestMapping(value = "/myAccount/getCitys", method = { RequestMethod.GET })
	public void getCitys(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String proCode = ServletRequestUtils.getStringParameter(request, "proCode", "");
			List<YydCity> citys = pCAreaService.getCitysByProCode(proCode);

			List<YydArea> areas = null;
			if (citys.size() > 0) {
				areas = pCAreaService.getAreasByCityCode(citys.get(0).getCityNo());
			}

			map.put("citys", citys);
			map.put("areas", areas == null ? new ArrayList<YydArea>() : areas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	@RequestMapping(value = "/myAccount/getAreas", method = { RequestMethod.GET })
	public void getAreas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", "");
			List<YydArea> areas = pCAreaService.getAreasByCityCode(cityCode);

			map.put("areas", areas);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithText(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "操作失败！");
			JsonResponser.respondWithText(response, map);
		}
		return;
	}

	@RequestMapping(value = "/myAccount/setCurrCompany", method = { RequestMethod.GET })
	public void setCurrCompany(Long compId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UserData ud = this.getLoginUserData(request);
			if (ud.getCurrCompanyData().getId().equals(compId))
				throw new Exception("错误！指定公司已经是当前公司，无需设置。");

			int maxAge = 10 * 24 * 60 * 60;
			CookieUtil.setCookie(request, response, LoginController.CURR_COMP_ID, compId.toString(), maxAge);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "当前公司已经重新设置。");

		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);

		return;
	}

}
