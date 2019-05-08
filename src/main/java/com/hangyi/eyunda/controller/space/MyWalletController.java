package com.hangyi.eyunda.controller.space;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
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

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.common.BitMatrix;
import com.hangyi.eyunda.chat.MessageConstants;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.wallet.CustAcctData;
import com.hangyi.eyunda.data.wallet.UserBankData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.data.wallet.WalletSettleData;
import com.hangyi.eyunda.domain.PubPayCity;
import com.hangyi.eyunda.domain.PubPayCnapsBank;
import com.hangyi.eyunda.domain.PubPayNode;
import com.hangyi.eyunda.domain.enumeric.BankCode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.QCodeResultCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.EnumConst.SpaceMenuCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.pubpay.PubPayCityService;
import com.hangyi.eyunda.service.pubpay.PubPayCnapsBankService;
import com.hangyi.eyunda.service.pubpay.PubPayNodeService;
import com.hangyi.eyunda.service.wallet.UserBankCardService;
import com.hangyi.eyunda.service.wallet.WalletFetchService;
import com.hangyi.eyunda.service.wallet.WalletFillPayService;
import com.hangyi.eyunda.service.wallet.WalletService;
import com.hangyi.eyunda.service.wallet.WalletSettleService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;
import com.hangyi.eyunda.util.DateUtils;
import com.hangyi.eyunda.util.HtmlToPdfUtil;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.MatrixToImageWriter;
import com.hangyi.eyunda.util.StringUtil;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisConnectionException;

@Controller
@RequestMapping("/space/wallet")
public class MyWalletController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 10;

	@Autowired
	private UserService userService;
	@Autowired
	private WalletFillPayService walletFillPayService;
	@Autowired
	private WalletFetchService walletFetchService;
	@Autowired
	private UserBankCardService walletBankService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private WalletSettleService walletSettleService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private PubPayCityService pubPayCityService;
	@Autowired
	private PubPayNodeService pubPayNodeService;
	@Autowired
	private PubPayCnapsBankService pubPayCnapsBankService;
	@Autowired
	private JedisPool redisPool;

	@RequestMapping(value = "/myWallet", method = RequestMethod.GET)
	public ModelAndView myWallet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-myWallet");

		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录无权进行此项操作!");
		mav.addObject("userData", userData);

		String startTime = ServletRequestUtils.getStringParameter(request, "startTime", "");
		mav.addObject("startTime", startTime);

		String endTime = ServletRequestUtils.getStringParameter(request, "endTime",
				CalendarUtil.toYYYY_MM_DD(Calendar.getInstance()));
		mav.addObject("endTime", endTime);

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);

		BankCode[] banks = BankCode.values();
		banks = Arrays.copyOfRange(banks, 1, BankCode.values().length);
		mav.addObject("banks", banks);

		AccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		// 可用余额totalBalance,可提现余额TotalTranOutAmount
		Double totalBalance = 0D, totalTranOutAmount = 0D;
		// 会员账户钱包余额
		if ((payAccountData != null) && !("".equals(payAccountData.getAccountNo()))) {
			userData.setAccountData(payAccountData);
			List<CustAcctData> cads = walletFillPayService.api6010(payAccountData.getAccountNo(), "2", "1");
			if (!cads.isEmpty()) {
				totalBalance = cads.get(0).getTotalBalance();
				totalTranOutAmount = cads.get(0).getTotalTranOutAmount();
			}
		} else {
			throw new Exception("请先绑定银行卡，才能进行后续操作!");
		}

		// 可用余额totalBalance,可提现余额TotalTranOutAmount
		// Double totalBalance =
		// walletSettleService.getUsableMoney(userData.getId());
		// Double totalTranOutAmount =
		// walletSettleService.getFetchableMoney(userData.getId());
		mav.addObject("totalBalance", totalBalance);
		mav.addObject("totalTranOutAmount", totalTranOutAmount);

		// 收款账户钱包
		BankCode receiveBankCode = BankCode.EYUNDA;
		mav.addObject("receiveBankCode", receiveBankCode);

		Page<WalletData> pageData = walletService.getWalletDatas(userData, startTime, endTime, pageNo, PAGE_SIZE);
		mav.addObject("pageData", pageData);

		// 绑定的银行卡列表
		List<UserBankData> bankDatas = walletFetchService.getBindCards(userData);
		mav.addObject("bankDatas", bankDatas);

		// 用户钱包和绑定的银行卡列表
		List<UserBankData> walletDatas = walletFetchService.getBindCards(userData, true);
		mav.addObject("walletDatas", walletDatas);

		// // 用户是否设置过支付密码
		// AccountData ad = accountService.getAccountByUserId(userData.getId(),
		// PayStyleCode.pinganpay);
		// mav.addObject("settedPW", ad != null ? ad.getPayPassWord().toString()
		// : YesNoCode.no.toString());

		// 用户是否成功绑定银行卡
		List<UserBankData> ubds = walletBankService.getBankDatas(userData.getId());
		if (ubds.isEmpty())
			mav.addObject("settedPW", YesNoCode.no.toString());
		else
			mav.addObject("settedPW", YesNoCode.yes.toString());

		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_WALLET);
		return mav;
	}

	// 绑定银行卡信息
	@RequestMapping(value = "/myWallet/bankCardInfo", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getBindCards(UserBankData bankData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-bindCardInfo");

		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录无权进行此项操作!");
		mav.addObject("userData", userData);

		// 用户是否成功绑定银行卡
		List<UserBankData> ubds = walletBankService.getBankDatas(userData.getId());
		if (ubds.isEmpty())
			mav.addObject("settedPW", YesNoCode.no.toString());
		else
			mav.addObject("settedPW", YesNoCode.yes.toString());

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
		mav.addObject("allProvince", ppns);
		List<PubPayCity> ppcs = pubPayCityService
				.getPPCByPPN(ppns.isEmpty() ? new PubPayNode().getNodeCode() : ppns.get(0).getNodeCode());
		mav.addObject("currc", ppcs);

		int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
		mav.addObject("pageNo", pageNo);
		// 绑定的银行卡列表
		@SuppressWarnings("rawtypes")
		Page pageData = walletFetchService.getBindCards(userData, pageNo);
		mav.addObject("pageData", pageData);

		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_WALLET);
		return mav;
	}

	@RequestMapping(value = "/myWallet/getAreas", method = { RequestMethod.GET })
	public void getAreas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", "");
			List<PubPayCity> ppcs = pubPayCityService.getPPCByPPN(cityCode);

			map.put("areas", ppcs);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "操作成功！");

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

	// 提交绑定银行卡信息
	@RequestMapping(value = "/myWallet/prebindCard", method = { RequestMethod.POST })
	public void prebindCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			String IdCode = ServletRequestUtils.getStringParameter(request, "IdCode", "");
			String accountName = ServletRequestUtils.getStringParameter(request, "accountName", "");
			String cardNo = ServletRequestUtils.getStringParameter(request, "cardNo", "");
			String MobilePhone = ServletRequestUtils.getStringParameter(request, "MobilePhone", "");
			String BankType = ServletRequestUtils.getStringParameter(request, "BankType", "");// pinganBank,
																								// otherBank
			String sbType = ServletRequestUtils.getStringParameter(request, "sbType", "");// big5,
																							// small5
			String bankCode = ServletRequestUtils.getStringParameter(request, "bankCode", "");
			String openBankId = ServletRequestUtils.getStringParameter(request, "openBank", "");
			int type = ServletRequestUtils.getIntParameter(request, "type", 1);// 1私人账户，2对公账户
			boolean result = walletFetchService.prebindCard(userData, IdCode, accountName, cardNo, MobilePhone,
					BankType, sbType, bankCode, openBankId, type);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 提交绑定验证码信息
	@RequestMapping(value = "/myWallet/bindCard", method = { RequestMethod.POST })
	public void bindCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			String MessageCode = ServletRequestUtils.getStringParameter(request, "MessageCode", "");
			String cardNo = ServletRequestUtils.getStringParameter(request, "cardNo", "");
			String bankCode = ServletRequestUtils.getStringParameter(request, "bankCode", "");
			String accountName = ServletRequestUtils.getStringParameter(request, "accountName", "");
			String IdCode = ServletRequestUtils.getStringParameter(request, "IdCode", "");
			String MobilePhone = ServletRequestUtils.getStringParameter(request, "MobilePhone", "");
			String paypwd = ServletRequestUtils.getStringParameter(request, "paypwd", "");
			int type = ServletRequestUtils.getIntParameter(request, "type", 1);// 1私人账户，2对公账户
			boolean result = walletFetchService.bindCard(userData, MessageCode, cardNo, bankCode, accountName, IdCode,
					MobilePhone, paypwd, type);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 查找开户行
	@RequestMapping(value = "/myWallet/searchOpenBank", method = { RequestMethod.POST })
	public void searchOpenBank(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
			String bank = ServletRequestUtils.getStringParameter(request, "bank", "");
			String acode = ServletRequestUtils.getStringParameter(request, "acode", "");

			List<PubPayCnapsBank> ppcbs = pubPayCnapsBankService.searchOpenBank(keyword, bank, acode);

			map.put("ppcbs", ppcbs);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功！");
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 设置收款账户
	@RequestMapping(value = "/myWallet/setRcvCard", method = { RequestMethod.POST })
	public void setRcvCard(Long cardId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			boolean result = walletFetchService.setRcvCard(userData, cardId);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "设置收款账户成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "设置收款账户失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "设置收款账户失败!");
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取用户绑定银行列表
	@RequestMapping(value = "/myWallet/getBindCards", method = { RequestMethod.GET })
	public void getBindCards(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			List<UserBankData> bankDatas = walletFetchService.getBindCards(userData);
			if (!bankDatas.isEmpty()) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "获取银行卡列表成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "获取银行卡列表失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 取消绑定
	@RequestMapping(value = "/myWallet/unBindCard", method = { RequestMethod.POST })
	public void unBindCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			Long bankCardId = ServletRequestUtils.getLongParameter(request, "bindCardId", 0L);

			boolean result = walletFetchService.unbindCard(userData, bankCardId);
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "解除绑定银行卡成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "解除绑定银行卡失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 预提现
	@RequestMapping(value = "/myWallet/fetch", method = { RequestMethod.POST })
	public void fetch(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			Long walletId = ServletRequestUtils.getLongParameter(request, "walletId", 0L);
			double fetchMoney = ServletRequestUtils.getDoubleParameter(request, "fetchMoney", 0.00D);

			AccountData accountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			UserBankData userBankData = walletBankService.findBankCard(userData.getId());
			userData.setAccountData(accountData);
			userData.setUserBankData(userBankData);
			HashMap<String, String> rspMap = walletFetchService.preFetch(userData, fetchMoney, walletId);

			// 传参数到jsp
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put("paymentNo", rspMap.get("paymentNo"));
			map.put("serialNo", rspMap.get("serialNo"));
			map.put("revMobilePhone", rspMap.get("revMobilePhone"));

		} catch (Exception e) {

			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 提现
	@RequestMapping(value = "/myWallet/surefetch", method = { RequestMethod.POST })
	public void surefetch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			String paymentNo = ServletRequestUtils.getStringParameter(request, "paymentNo", "");
			String serialNo = ServletRequestUtils.getStringParameter(request, "serialNo", "");
			String paypwd = ServletRequestUtils.getStringParameter(request, "paypwd", "");
			String MessageCode = ServletRequestUtils.getStringParameter(request, "MessageCode", "");

			// 验证支付密码
			if (!userService.checkPayPwd(userData, paypwd))
				throw new Exception("支付密码输入错误!");

			AccountData accountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			UserBankData userBankData = walletBankService.findBankCard(userData.getId());
			if (accountData == null || userBankData == null)
				throw new Exception("未绑定提现银行卡不能提现！");
			userData.setAccountData(accountData);
			userData.setUserBankData(userBankData);
			String result = walletFetchService.surefetch(userData, paymentNo, serialNo, MessageCode);

			// 传参数到jsp
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, result);

		} catch (Exception e) {

			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 取得一条提现记录
	@RequestMapping(value = "/myWallet/getFetchWallet", method = { RequestMethod.POST })
	public void getFetchWallet(Long walletId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);

			// 绑定的银行卡列表
			if (walletData != null) {
				List<UserBankData> bankDatas = walletFetchService.getBindCards(userData);
				map.put("walletData", walletData);
				map.put("bankDatas", bankDatas);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "取得提现记录成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "提现记录不存在！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 删除处理
	@RequestMapping(value = "/myWallet/delete", method = { RequestMethod.GET })
	public void doDelete(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			boolean result = walletService.deleteWallet(walletId);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "删除失败！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "退款处理失败！");
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/findUserAccount", method = { RequestMethod.GET, RequestMethod.POST })
	public void findUserAccount(String loginName, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			List<AccountData> accountDatas = accountService.getAccountsLikeLoginName(loginName, PayStyleCode.pinganpay);

			if (!accountDatas.isEmpty()) {
				map.put("accountDatas", accountDatas);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "查找用户账户成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "查找用户账户失败！");
			}
		} catch (Exception e) {
			logger.debug(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/setpw", method = { RequestMethod.GET })
	public void setpw(String type, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			if ("S".equals(type)) {
				List<UserBankData> ubds = walletBankService.getBankDatas(userData.getId());
				if (ubds.isEmpty())
					throw new Exception("设置支付密码前，请先绑定一张银行卡!");
			}

			AccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			if (ad != null) {
				Map<String, String> content = walletFetchService.pinganSetpw(userData, ad, type, request);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
				map.put(JsonResponser.CONTENT, content);
			} else
				throw new Exception("找不到用户平安子账户!");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 收款生成二维码
	@RequestMapping(value = "/myWallet/create", method = RequestMethod.POST)
	public void create(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			// 接受请求参数
			Double receiveMoney = ServletRequestUtils.getDoubleParameter(request, "receiveMoney", 0.00D);
			int isSurety = ServletRequestUtils.getIntParameter(request, "isSurety", 0);
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");
			int suretyDay = ServletRequestUtils.getIntParameter(request, "suretyDay", 0);

			// 写入数据库并返回记录ID，加入下面字符串中
			String res = QCodeResultCode.fetch.name() + ";" + userData.getId().toString() + ";" + userData.getTrueName()
					+ ";" + receiveMoney.toString() + ";" + remark + ";" + DateUtils.getTime("") + ";" + isSurety + ";"
					+ suretyDay;
			String md5Str = MD5.toMD5(res);
			// md5Str+res 存入redis,供检查用
			Jedis jedis = null;
			try {
				jedis = redisPool.getResource();
				jedis.hset(MessageConstants.QCODE_QUEUE, md5Str, res);
			} catch (JedisConnectionException jedisEx) {
				redisPool.returnBrokenResource(jedis);
			} finally {
				if (jedis != null)
					redisPool.returnResource(jedis);
			}
			String newFileName = md5Str + ".jpg";
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getQcodeDir(userData.getId()) + ShareDirService.FILE_SEPA_SIGN;
			String path = realPath + relativePath;
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

			Map<EncodeHintType, String> hints = new HashMap<EncodeHintType, String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(md5Str + "|" + StringUtil.getRandomString(32),
					BarcodeFormat.QR_CODE, 800, 800, hints);
			File fDir = new File(path);
			if (!fDir.exists()) {
				fDir.mkdirs();
			} else {
				// FileUtil.emptyDirectory(fDir);
			}
			File qcodeFile = new File(path + newFileName);
			MatrixToImageWriter.writeToPath(bitMatrix, "jpg", qcodeFile.toPath());

			map.put("img", relativePath + newFileName);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "二维码生成成功");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 查询钱包明细帐目
	@RequestMapping(value = "/myWallet/getUserSettles", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView getUserSettles(Page<WalletSettleData> pageData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-userSettle");

		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录无权进行此项操作!");
		mav.addObject("userData", userData);

		String startTime = ServletRequestUtils.getStringParameter(request, "startTime", "");
		String endTime = ServletRequestUtils.getStringParameter(request, "endTime",
				CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()));

		mav.addObject("startTime", startTime);
		mav.addObject("endTime", endTime);

		// 可用余额totalBalance,可提现余额TotalTranOutAmount
		// Double totalBalance =
		// walletSettleService.getUsableMoney(userData.getId());
		// Double totalTranOutAmount =
		// walletSettleService.getFetchableMoney(userData.getId());

		AccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		// 可用余额totalBalance,可提现余额TotalTranOutAmount
		Double totalBalance = 0D, totalTranOutAmount = 0D;
		// 会员账户钱包余额
		if ((payAccountData != null) && !("".equals(payAccountData.getAccountNo()))) {
			List<CustAcctData> cads = walletFillPayService.api6010(payAccountData.getAccountNo(), "2", "1");
			if (!cads.isEmpty()) {
				totalBalance = cads.get(0).getTotalBalance();
				totalTranOutAmount = cads.get(0).getTotalTranOutAmount();
			}
		} else {
			throw new Exception("请先绑定银行卡，才能进行后续操作!");
		}

		mav.addObject("totalBalance", totalBalance);
		mav.addObject("totalTranOutAmount", totalTranOutAmount);

		// 用户账务列表
		pageData = walletSettleService.getSettleByUserId(userData.getId(), pageData.getPageNo(), pageData.getPageSize(),
				startTime, endTime);

		mav.addObject("pageData", pageData);

		mav.addObject("menus", SpaceMenuCode.values());
		mav.addObject("menuAct", SpaceMenuCode.MY_WALLET);
		return mav;
	}

	// 账务
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(Long id, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/space/wallet/wallet-info");

		String makePdf = "no";

		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);

		if (userData == null) {
			String checksum = ServletRequestUtils.getStringParameter(request, "checksum", "");
			Long uid = ServletRequestUtils.getLongParameter(request, "uid", 0L);

			userData = userService.getById(uid);

			// 合同页面地址
			String urlStr = "http://" + Constants.LOCALE_NAME + "/space/wallet/show?settleType=" + settleType.toString()
					+ "&id=" + id + "&uid=" + userData.getId();

			String validchecksum = MD5.toMD5(urlStr + Constants.SALT_VALUE);

			if (!validchecksum.equals(checksum)) {// 校验合法
				throw new Exception("对不起，你没有查看该账务的权限!");
			}

			makePdf = "yes";
		}

		WalletData walletData = walletService.getBillData(id);

		AccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		if ((payAccountData != null) && !("".equals(payAccountData.getAccountNo()))) {
			userData.setAccountData(payAccountData);
		} else {
			throw new Exception("请先绑定银行卡，才能进行后续操作!");
		}
		walletData.setDescByLoginUser(userData);

		mav.addObject("walletData", walletData);
		mav.addObject("settleType", settleType);
		mav.addObject("makePdf", makePdf);
		return mav;
	}

	@RequestMapping(value = "/showAct", method = RequestMethod.GET)
	public ModelAndView showAct(Long id, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);

		String urlShort = "http://" + Constants.LOCALE_NAME + "/space/wallet/show?settleType=" + settleType.toString()
				+ "&id=" + id;
		if (userData == null) {
			String checksum = ServletRequestUtils.getStringParameter(request, "checksum", "");
			Long uid = ServletRequestUtils.getLongParameter(request, "uid", 0L);

			userData = userService.getById(uid);

			// 账务页面地址
			String urlStr = urlShort + "&uid=" + userData.getId();
			String validchecksum = MD5.toMD5(urlStr + Constants.SALT_VALUE);

			if (!validchecksum.equals(checksum)) {// 校验合法
				throw new Exception("对不起，你没有查看该账务的权限!");
			}
		}
		ModelAndView mav = new ModelAndView("/space/wallet/wallet-info-container");

		mav.addObject("url", urlShort);
		mav.addObject("walletId", id);
		mav.addObject("settleType", settleType);
		return mav;
	}

	// 下载PDF
	@RequestMapping(value = "/downloadPdf", method = { RequestMethod.GET })
	public ModelAndView downloadPdf(Long id, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录不能进行此项操作!");

			WalletData walletData = walletService.getBillData(id);
			if (walletData == null) {
				throw new Exception("错误!账务不存在，无法生成账单文件。");
			}

			// 合同页面地址
			String urlStr = "http://" + Constants.LOCALE_NAME + "/space/wallet/show?settleType=" + settleType.toString()
					+ "&id=" + id + "&uid=" + userData.getId();
			String checksum = MD5.toMD5(urlStr + Constants.SALT_VALUE);
			urlStr += "&checksum=" + checksum;

			// 合同文件名及路径
			String realPath = Constants.SHARE_DIR;

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(walletData.getGmtPayment()));
			String pdfFileName = ShareDirService.getUserDir(userData.getId()) + "/wallet/" + walletData.getId() + "/"
					+ "Wallet" + walletData.getId() + "-" + settleType.toString() + "-"
					+ CalendarUtil.toYYYYMMDDHHmmss(calendar);
			String relativePath = (pdfFileName + ".pdf");

			// 生成合同pdf文件
			File pdfFile = HtmlToPdfUtil.exportPdfFile(urlStr, realPath + relativePath);

			if (pdfFile.exists()) {
				ModelAndView mav = new ModelAndView("redirect:/space/download/fileDownload?url=" + relativePath);
				return mav;
			} else {
				throw new Exception("错误!生成账单时出现错误。");
			}
		} catch (Exception e) {
			throw new Exception("错误!生成账单文件时出现错误。");
		}
	}

}
