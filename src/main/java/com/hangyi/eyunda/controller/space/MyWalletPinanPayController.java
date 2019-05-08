package com.hangyi.eyunda.controller.space;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.portal.login.LoginController;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.wallet.UserPinganBankData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.BankCode2;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayKhsdCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.wallet.WalletFillPayService;
import com.hangyi.eyunda.service.wallet.WalletService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.CookieUtil;

@Controller
@RequestMapping("/space/pinganpay")
public class MyWalletPinanPayController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());
	public static final int PAGE_SIZE = 10;

	@Autowired
	private UserService userService;
	@Autowired
	private WalletFillPayService walletFillPayService;
	@Autowired
	private WalletService walletService;

	private static final String RETURN_URL = "http://" + Constants.DOMAIN_NAME + "/space/pinganpay/returnpage/1";
	private static final String NOTIFY_URL = "http://" + Constants.DOMAIN_NAME + "/space/pinganpay/notifypage/1";
	private static final String NOTIFY_URL2 = "http://" + Constants.DOMAIN_NAME + "/space/pinganpay/notifypage/2";
	private static final String KHREFUNDNOTIFY_URL = "http://" + Constants.DOMAIN_NAME
			+ "/space/pinganpay/khRefundNotify";

	// 全部支付
	@RequestMapping(value = "/orderPay", method = { RequestMethod.POST })
	public ModelAndView orderPay(Long walletId, Long orderId, FeeItemCode feeItem, Double payMoney, Integer suretyDay,
			String remark, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-pinganpay");

		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录无权进行此项操作!");

		// 取得生成的帐单信息
		WalletData walletData = walletFillPayService.getWalletData(userData.getId(), walletId, orderId, feeItem,
				payMoney, suretyDay, remark);
		// 验证支付密码
		String paypwd = ServletRequestUtils.getStringParameter(request, "paypwd", "");

		if (walletData != null) {
			if (walletData.getSettleStyle() != SettleStyleCode.fill)
				if (!userService.checkPayPwd(userData, paypwd))
					throw new Exception("支付密码输入错误，如未设置支付密码请到设置中进行设置,以确保您的支付安全!");
			// 绑定并支付银行编码
			mav.addObject("walletData", walletData);

			BankCode2[] plantBanks = BankCode2.values();
			mav.addObject("plantBanks", plantBanks);

			// 用户绑定的银行卡列表
			List<UserPinganBankData> userBankDatas = walletFillPayService.getBindCards(walletData);
			mav.addObject("userBankDatas", userBankDatas);

			mav.addObject("netpayurl", Constants.NETPAY_URL);
			mav.addObject("nobindpayurl", Constants.NOBINDPAY_URL);
			mav.addObject("bindandpayurl", Constants.BINDANDPAY_URL);

			mav.addObject("returnurl", RETURN_URL);
			mav.addObject("notifyurl", NOTIFY_URL);
		} else {
			throw new Exception("未找到或未生成支付记录!");
		}

		return mav;
	}

	// 绑定并支付选择银行，重新生成orig和sign
	@RequestMapping(value = "/noBindPay", method = { RequestMethod.POST })
	public void nobindPay(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				Map<String, String> mapResult = walletFillPayService.noBindPay(walletData);

				if (!"".equals(mapResult.get("orig")))
					map.put("orig", mapResult.get("orig"));
				if (!"".equals(mapResult.get("sign")))
					map.put("sign", mapResult.get("sign"));
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未找到或未生成支付记录!");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 钱包支付
	@RequestMapping(value = "/walletPay", method = { RequestMethod.POST })
	public void walletPay(Long walletId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
		UserData userData = userService.getByCookie(uck);
		if (userData == null)
			throw new Exception("未登录无权进行此项操作!");

		Map<String, Object> map = new HashMap<String, Object>();
		try {
			WalletData walletData = walletService.getBillData(walletId);
			String serialNo = ServletRequestUtils.getStringParameter(request, "serialNo", "");
			String messageCode = ServletRequestUtils.getStringParameter(request, "messageCode", "");

			boolean result = walletFillPayService.walletPay(walletData, serialNo, messageCode);
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "支付成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "支付失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 绑定并支付选择银行，重新生成orig和sign
	@RequestMapping(value = "/bindAndPay", method = { RequestMethod.POST })
	public void bindAndPay(Long walletId, String plantBankId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);
			if (walletData != null) {
				Map<String, String> mapInfo = walletFillPayService.bindAndPay(walletData, plantBankId);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put("orig", mapInfo.get("orig"));
				map.put("sign", mapInfo.get("sign"));
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "未找到或未生成支付记录!");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取短信验证码发送时间
	@RequestMapping(value = "/getValidCode", method = { RequestMethod.POST })
	public void getValidCode(Long walletId, String bindId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);
			String dataTime = walletFillPayService.getValidCode(walletData, bindId); // 签名
			if (dataTime != null && !"".equals(dataTime)) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put("dataTime", dataTime);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "获取验证码失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 绑定卡支付，重新生成orig和sign
	@RequestMapping(value = "/bindCardPay", method = { RequestMethod.POST })
	public void bindCardPay(Long walletId, String bindId, String verifyCode, String dataTime,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);
			PayKhsdCode result = walletFillPayService.bindFastPay(walletData, bindId, verifyCode, dataTime,
					NOTIFY_URL2); // API007
			switch (result) {
			case paying:
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "支付处理中，你的银行卡转帐将会在24小时内完成。");
				break;
			case success:
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "支付成功！");
				break;
			case failure:
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "支付失败，请重新支付！");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 钱包支付验证密码
	@RequestMapping(value = "/walletPayVerifyCode", method = { RequestMethod.POST })
	public void walletPayVerifyCode(Long walletId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);
			// 申请动态短信验证码
			Map<String, String> mapResult = walletFillPayService.getSerialNo(walletData, "2");
			String serialNo = (String) mapResult.get("serialNo");

			if (serialNo != null && !"".equals(serialNo)) {
				map.put("serialNo", serialNo);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "申请动态验证码失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 解除已绑定的银行卡
	@RequestMapping(value = "/removeBindCard", method = { RequestMethod.POST })
	public void removeBindCard(String bindId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			boolean result = walletFillPayService.removeBindCard(userData.getId(), bindId);
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "解除绑定成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "解除绑定失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 确认付款
	@RequestMapping(value = "/confirmPay ", method = { RequestMethod.POST })
	public void confirmPay(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);

			boolean result = walletFillPayService.confirmPay(walletData);
			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "确认付款成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "确认付款失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 申请退款
	@RequestMapping(value = "/myWallet/refundapply", method = { RequestMethod.POST })
	public void refundapply(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				boolean result = walletFillPayService.refundApply(walletData);

				if (result) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "已申请退款！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "申请退款失败！");
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 退款处理
	@RequestMapping(value = "/myWallet/doRefund", method = { RequestMethod.POST })
	public void doRefund(Long walletId, ApplyReplyCode applyReply, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String uck = CookieUtil.getCookieValue(request, LoginController.USER_COOKIE);
			UserData userData = userService.getByCookie(uck);
			if (userData == null)
				throw new Exception("未登录无权进行此项操作!");

			WalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				boolean result = walletFillPayService.doRefund(walletData, applyReply, KHREFUNDNOTIFY_URL);
				if (result) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "退款处理完成！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "退款处理失败！");
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 返回页面
	@RequestMapping(value = "/returnpage/{flag}", method = { RequestMethod.POST, RequestMethod.GET })
	public ModelAndView returnpage(@PathVariable("flag") String flag, String sign, String orig,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView("/space/space-pinganReturnPage");
		String message = "";
		try {
			YesNoCode khFlag = YesNoCode.no;
			if (flag != null && "1".equals(flag))
				khFlag = YesNoCode.yes;

			PayKhsdCode result = walletFillPayService.doReturn(khFlag, sign, orig);
			switch (result) {
			case paying:
				message = "支付处理中，你的银行卡转帐将会在24小时内完成。";
				break;
			case success:
				message = "支付成功！";
				break;
			case failure:
				message = "支付失败，请重新支付！";
				break;
			default:
				break;
			}
		} catch (Exception e) {
			message = "支付失败！" + e.getMessage();
		}

		mav.addObject("message", message);

		return mav;
	}

	// 后台通知，要看接口要求正确处理后返回什么？
	@RequestMapping(value = "/notifypage/{flag}", method = { RequestMethod.POST, RequestMethod.GET })
	public void notifypage(@PathVariable("flag") String flag, String sign, String orig, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			YesNoCode khFlag = YesNoCode.no;
			if (flag != null && "1".equals(flag))
				khFlag = YesNoCode.yes;

			PayKhsdCode result = walletFillPayService.doNotify(khFlag, sign, orig);
			switch (result) {
			case paying:
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "支付处理中，你的银行卡转帐将会在24小时内完成。");
				break;
			case success:
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "支付成功！");
				break;
			case failure:
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "支付失败，请重新支付！");
				break;
			default:
				break;
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 跨行退款通知
	@RequestMapping(value = "/khRefundNotify", method = { RequestMethod.POST, RequestMethod.GET })
	public void khRefundNotify(String sign, String orig, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {

			walletFillPayService.doKHRefundNotify(sign, orig);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "支付成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/pinganCallBack", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView pinganCallBack(String result, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView mav = new ModelAndView("/space/space-pingan-callback");

		mav.addObject("result", result);
		return mav;
	}
}
