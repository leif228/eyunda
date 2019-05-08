package com.hangyi.eyunda.controller.hyquan.wallet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.controller.hyquan.HyqLoginController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqAccountData;
import com.hangyi.eyunda.data.hyquan.HyqUserBankData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.hyquan.HyqWalletData;
import com.hangyi.eyunda.data.hyquan.HyqWalletSettleData;
import com.hangyi.eyunda.data.wallet.CustAcctData;
import com.hangyi.eyunda.domain.PubPayCity;
import com.hangyi.eyunda.domain.PubPayCnapsBank;
import com.hangyi.eyunda.domain.PubPayNode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqAccountService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqUserBankCardService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqWalletFetchService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqWalletFillPayService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqWalletService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqWalletSettleService;
import com.hangyi.eyunda.service.pubpay.PubPayCityService;
import com.hangyi.eyunda.service.pubpay.PubPayCnapsBankService;
import com.hangyi.eyunda.service.pubpay.PubPayNodeService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.HtmlToPdfUtil;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/hyquan/wallet")
public class HyqWalletController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int PAGE_SIZE = 10;

	@Autowired
	private HyqUserService userService;
	@Autowired
	private HyqUserBankCardService walletBankService;
	@Autowired
	private HyqAccountService accountService;
	@Autowired
	private PubPayCityService pubPayCityService;
	@Autowired
	private PubPayNodeService pubPayNodeService;
	@Autowired
	private PubPayCnapsBankService pubPayCnapsBankService;
	@Autowired
	private HyqWalletService walletService;
	@Autowired
	private HyqWalletFillPayService walletFillPayService;
	@Autowired
	private HyqWalletFetchService walletFetchService;
	@Autowired
	private HyqWalletSettleService walletSettleService;

	@RequestMapping(value = "/myWallet", method = RequestMethod.GET)
	public void myWallet(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");
			// 查询期限:默认一个月
			String startTime = ServletRequestUtils.getStringParameter(request, "startTime", "");
			String endTime = ServletRequestUtils.getStringParameter(request, "endTime",
					CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()));

			HyqAccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			if ((payAccountData != null) && !("".equals(payAccountData.getAccountNo()))) {
				userData.setAccountData(payAccountData);
			} else {
				throw new Exception("请先绑定银行卡，才能进行后续操作!");
			}

			Map<String, Object> content = new HashMap<String, Object>();

			Integer pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);

			Page<HyqWalletData> pageData = walletService.getWalletDatas(userData, startTime, endTime, pageNo,
					PAGE_SIZE);

			content.put("walletDatas", pageData.getResult());
			content.put("totalPages", pageData.getTotalPages());

			// 用户是否设置过支付密码
			List<HyqUserBankData> ubds = walletBankService.getBankDatas(userData.getId());
			if (ubds.isEmpty())
				content.put("settedPW", YesNoCode.no.toString());
			else
				content.put("settedPW", YesNoCode.yes.toString());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/getBindCards", method = RequestMethod.GET)
	public void getBindCards(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");
			Boolean includeEyunda = ServletRequestUtils.getBooleanParameter(request, "includeEyunda", false);

			List<HyqUserBankData> bankDatas = walletFetchService.getBindCards(userData, includeEyunda);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, bankDatas);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/setpw", method = RequestMethod.GET)
	public void setpw(String type, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			if ("S".equals(type)) {
				List<HyqUserBankData> ubds = walletBankService.getBankDatas(userData.getId());
				if (ubds.isEmpty())
					throw new Exception("设置支付密码前，请先绑定一张银行卡!");
			}

			HyqAccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
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
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/bindCardInit", method = RequestMethod.GET)
	public void bindCardInit(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String, Object> content = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			// 用户钱包账号
			HyqAccountData add = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			if (add == null)
				add = new HyqAccountData();

			// 用户是否成功绑定提现银行卡（只能绑定一张提现卡）
			List<HyqUserBankData> ubds = walletBankService.getBankDatas(userData.getId());
			if (ubds.isEmpty()) {
				content.put("settedPW", YesNoCode.no.toString());

				// 开户行地区名称
				List<PubPayNode> ppns = pubPayNodeService.getAllPubPayNode();
				content.put("allProvince", ppns);
				List<PubPayCity> ppcs = pubPayCityService
						.getPPCByPPN(ppns.isEmpty() ? new PubPayNode().getNodeCode() : ppns.get(0).getNodeCode());
				content.put("currc", ppcs);
			} else {
				content.put("settedPW", YesNoCode.yes.toString());

				String cardNo = ubds.get(0).getCardNo().length() > 4
						? "*** " + "*** " + ubds.get(0).getCardNo().substring(ubds.get(0).getCardNo().length() - 4)
						: ubds.get(0).getCardNo();
				ubds.get(0).setCardNo(cardNo);

				content.put("userBankData", ubds.get(0));

				// String idCode = add.getIdCode().length() > 6 ? "*** "
				// + "*** "
				// + add.getIdCode().substring(
				// add.getIdCode().length() - 6) : add.getIdCode();
				String idCode = add.getIdCode();
				String mobilePhone = add.getMobile().length() > 4
						? "*** " + "*** " + add.getMobile().substring(add.getMobile().length() - 4)
						: add.getMobile();

				add.setIdCode(idCode);
				add.setMobile(mobilePhone);

			}
			content.put("accountData", add);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.CONTENTMD5, contentMD5);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/prebindCard", method = RequestMethod.POST)
	public void prebindCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

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
			boolean result = walletFetchService.prebindCard(userService.getById(userData.getId()), IdCode, accountName,
					cardNo, MobilePhone, BankType, sbType, bankCode, openBankId, type);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/getAreas", method = RequestMethod.GET)
	public void getAreas(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			String cityCode = ServletRequestUtils.getStringParameter(request, "cityCode", "");
			List<PubPayCity> ppcs = pubPayCityService.getPPCByPPN(cityCode);

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("areas", ppcs);
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/searchOpenBank", method = RequestMethod.POST)
	public void searchOpenBank(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			String keyword = ServletRequestUtils.getStringParameter(request, "keyword", "");
			String bank = ServletRequestUtils.getStringParameter(request, "bank", "");
			String acode = ServletRequestUtils.getStringParameter(request, "acode", "");

			List<PubPayCnapsBank> ppcbs = pubPayCnapsBankService.searchOpenBank(keyword, bank, acode);

			map.put("ppcbs", ppcbs);
			Map<String, Object> content = new HashMap<String, Object>();
			content.put("ppcbs", ppcbs);
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取信息成功");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/bindCard", method = RequestMethod.POST)
	public void bindCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

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

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/unBindCard", method = RequestMethod.POST)
	public void unBindCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

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

	@RequestMapping(value = "/myWallet/setPWSuccess", method = RequestMethod.GET)
	public void setPWSuccess(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			HyqAccountData ad = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			if (ad != null) {
				ad.setPayPassWord(YesNoCode.yes);
				accountService.saveAccount(ad);
			} else
				throw new Exception("找不到用户平安账户!");

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存密码设置成功标志！");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/setRcvCard", method = RequestMethod.POST)
	public void setRcvCard(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			Long bankCardId = ServletRequestUtils.getLongParameter(request, "cardId", 0L);

			boolean result = walletFetchService.setRcvCard(userData, bankCardId);

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "设置收款账户成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "设置收款账户失败！");
			}

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 预提现
	@RequestMapping(value = "/myWallet/fetch", method = RequestMethod.POST)
	public void fetch(Long userBankId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			Long walletId = ServletRequestUtils.getLongParameter(request, "walletId", 0L);
			double fetchMoney = ServletRequestUtils.getDoubleParameter(request, "fetchMoney", 0.00D);

			// ScfUserBankData bankData =
			// walletBankService.getBindBankData(userBankId);

			HyqAccountData accountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			HyqUserBankData userBankData = walletBankService.findBankCard(userData.getId());
			userData.setAccountData(accountData);
			userData.setUserBankData(userBankData);
			HashMap<String, String> rspMap = walletFetchService.preFetch(userData, fetchMoney, walletId);

			Map<String, Object> content = new HashMap<String, Object>();
			content.put("paymentNo", rspMap.get("paymentNo"));
			content.put("serialNo", rspMap.get("serialNo"));
			content.put("revMobilePhone", rspMap.get("revMobilePhone"));
			content.put("fetchBody", rspMap.get("fetchBody"));
			// 传参数到jsp
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, content);

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 提现
	@RequestMapping(value = "/myWallet/surefetch", method = { RequestMethod.POST })
	public void surefetch(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			HyqAccountData accountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
			HyqUserBankData userBankData = walletBankService.findBankCard(userData.getId());
			if (accountData == null || userBankData == null)
				throw new Exception("未绑定提现银行卡不能提现！");
			userData.setAccountData(accountData);
			userData.setUserBankData(userBankData);

			String paymentNo = ServletRequestUtils.getStringParameter(request, "paymentNo", "");
			String serialNo = ServletRequestUtils.getStringParameter(request, "serialNo", "");
			String paypwd = ServletRequestUtils.getStringParameter(request, "paypwd", "");
			String MessageCode = ServletRequestUtils.getStringParameter(request, "MessageCode", "");

			// 验证支付密码
			if (!userService.checkPayPwd(userData, paypwd))
				throw new Exception("支付密码输入错误!");

			String result = walletFetchService.surefetch(userData, paymentNo, serialNo, MessageCode);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");
			map.put(JsonResponser.CONTENT, result);

		} catch (Exception e) {

			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/myWallet/userRcvBank", method = RequestMethod.GET)
	public void userRcvBank(String loginName, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			// 收款用户信息
			HyqUserData rcvUserData = userService.getByLoginName(loginName);
			if (rcvUserData == null) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "登录名错误，找不到用户信息");
			} else {
				HyqUserBankData ubData = walletBankService.findBankCard(rcvUserData.getId());
				if (ubData != null) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "获取用户指定收款银行信息成功！");
					map.put(JsonResponser.CONTENT, ubData);
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "获取用户指定收款银行信息失败！");
				}
			}

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	//
	@RequestMapping(value = "/myWallet/findUserAccount", method = RequestMethod.GET)
	public void findUserAccount(String loginName, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			List<HyqAccountData> accountDatas = accountService.getAccountsLikeLoginName(loginName,
					PayStyleCode.pinganpay);

			if (!accountDatas.isEmpty()) {
				map.put(JsonResponser.CONTENT, accountDatas);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "成功");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "取得用户钱包账户失败！");
			}

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 获取账号余额
	@RequestMapping(value = "/getAccoutNum", method = RequestMethod.GET)
	public void getAccoutNum(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			// 会员账户钱包余额
			// HyqAccountData payAccountData =
			// accountService.getAccountByUserId(userData.getId(),
			// PayStyleCode.pinganpay);

			// List<CustAcctData> cads =
			// walletFillPayService.api6010(payAccountData.getAccountNo(), "2",
			// "1");

			// 可用余额totalBalance,可提现余额TotalTranOutAmount
			// Double totalBalance =
			// walletSettleService.getUsableMoney(userData.getId());
			// Double totalTranOutAmount =
			// walletSettleService.getFetchableMoney(userData.getId());
			HyqAccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
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

			Map<String, Object> content = new HashMap<>();
			content.put("totalBalance", totalBalance);
			content.put("totalTranOutAmount", totalTranOutAmount);

			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "成功");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 删除处理
	@RequestMapping(value = "/delete", method = { RequestMethod.GET })
	public void doDelete(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

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

	// 查询钱包明细帐目
	@RequestMapping(value = "/getUserSettles", method = { RequestMethod.POST, RequestMethod.GET })
	public void getUserSettles(HttpServletRequest request, HttpServletResponse response) throws Exception {

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			int pageNo = ServletRequestUtils.getIntParameter(request, "pageNo", 1);
			int pageSize = 10;

			String startTime = ServletRequestUtils.getStringParameter(request, "startTime", "");
			String endTime = ServletRequestUtils.getStringParameter(request, "endTime",
					CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()));

			Map<String, Object> content = new HashMap<String, Object>();
			// 可用余额totalBalance,可提现余额TotalTranOutAmount
			// Double totalBalance =
			// walletSettleService.getUsableMoney(userData.getId());
			// Double totalTranOutAmount =
			// walletSettleService.getFetchableMoney(userData.getId());

			HyqAccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
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
			content.put("totalBalance", totalBalance);
			content.put("totalTranOutAmount", totalTranOutAmount);

			// 用户账务列表
			Page<HyqWalletSettleData> pageData = walletSettleService.getSettleByUserId(userData.getId(), pageNo,
					pageSize, startTime, endTime);
			content.put("userSettles", pageData.getResult());
			content.put("totalPages", pageData.getTotalPages());
			content.put("totalCount", pageData.getTotalCount());
			map.put(JsonResponser.CONTENT, content);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "获取数据成功！");

		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		JsonResponser.respondWithJson(response, map);
	}

	// 账务
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(Long id, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用户信息
		HyqUserData userData = this.getLoginUserData(request, response);

		HyqWalletData walletData = walletService.getBillData(id);

		HyqAccountData payAccountData = accountService.getAccountByUserId(userData.getId(), PayStyleCode.pinganpay);
		if ((payAccountData != null) && !("".equals(payAccountData.getAccountNo()))) {
			userData.setAccountData(payAccountData);
		} else {
			throw new Exception("请先绑定银行卡，才能进行后续操作!");
		}
		walletData.setDescByLoginUser(userData);

		ModelAndView mav = new ModelAndView("/space/wallet/wallet-info");

		mav.addObject("walletData", walletData);
		mav.addObject("settleType", settleType);
		return mav;
	}

	@RequestMapping(value = "/showAct", method = RequestMethod.GET)
	public ModelAndView showAct(Long id, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用户信息
		@SuppressWarnings("unused")
		HyqUserData userData = this.getLoginUserData(request, response);

		// String urlShort = "http://" + Constants.LOCALE_NAME +
		// "/space/wallet/show?settleType=" + settleType.toString()+ "&id=" +
		// id;
		// String urlShort = "http://" + Constants.LOCALE_NAME
		// +"/mobile/wallet/show?settleType="+settleType.toString()+ "&id=" + id
		// + "&sessionId="+sessionId;
		ModelAndView mav = new ModelAndView("/mobile/wallet-info-container");
		// mav.addObject("url", urlShort);
		mav.addObject("walletId", id);
		mav.addObject("settleType", settleType);

		mav.addObject("sessionId",
				ServletRequestUtils.getStringParameter(request, HyqLoginController.MBL_SESSION_ID, ""));
		return mav;
	}

	// 下载PDF
	@RequestMapping(value = "/downloadPdf", method = { RequestMethod.GET })
	public void downloadPdf(Long id, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			HyqWalletData walletData = walletService.getBillData(id);
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

			// if (pdfFile.exists()&& pdfFile.length() > 0) {
			// ModelAndView mav = new
			// ModelAndView("redirect:/space/download/fileDownload?url=" +
			// relativePath);
			// return mav;
			// } else {
			// throw new Exception("错误!生成账单时出现错误。");
			// }
			// } catch (Exception e) {
			// throw new Exception("错误!生成账单文件时出现错误。");
			// }
			if (pdfFile.exists() && pdfFile.length() > 0) {
				// 设置下载文件的类型为任意类型
				response.setContentType("application/x-msdownload");
				// 添加下载文件的头信息。此信息在下载时会在下载面板上显示，比如：
				String fileName = pdfFile.getName();
				String downLoadName = new String(fileName.getBytes("GBK"), "iso8859-1");
				response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName + "\"");
				response.setHeader("Content_Length", String.valueOf(pdfFile.length()));

				ServletOutputStream sos = response.getOutputStream();
				FileInputStream fis = new FileInputStream(pdfFile);
				IOUtils.copy(fis, sos);
			} else {
				throw new IOException("生成PDF文件时出错！");
			}
		} catch (Exception e) {
			logger.error("DOWNLOAD EXCEPTION：", e);
		}
	}
}
