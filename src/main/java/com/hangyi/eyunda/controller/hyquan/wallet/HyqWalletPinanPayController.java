package com.hangyi.eyunda.controller.hyquan.wallet;

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
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.controller.hyquan.HyqLoginController;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.hyquan.HyqUserPinganBankData;
import com.hangyi.eyunda.data.hyquan.HyqWalletData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayKhsdCode;
import com.hangyi.eyunda.domain.enumeric.WalletOptCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.hyquan.HyqUserService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqWalletFillPayService;
import com.hangyi.eyunda.service.hyquan.wallet.HyqWalletService;
import com.hangyi.eyunda.util.Constants;

@Controller
@RequestMapping("/hyquan/pinganpay")
public class HyqWalletPinanPayController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int PAGE_SIZE = 10;

	@Autowired
	private HyqUserService userService;
	@Autowired
	private HyqWalletService walletService;
	@Autowired
	private HyqWalletFillPayService walletFillPayService;

	public static final String RETURN_URL = "http://" + Constants.DOMAIN_NAME + "/scfreight/pinganpay/returnpage/1";
	public static final String NOTIFY_URL = "http://" + Constants.DOMAIN_NAME + "/scfreight/pinganpay/notifypage/1";
	public static final String NOTIFY_URL2 = "http://" + Constants.DOMAIN_NAME + "/scfreight/pinganpay/notifypage/2";
	public static final String KHREFUNDNOTIFY_URL = "http://" + Constants.DOMAIN_NAME
			+ "/scfreight/pinganpay/khRefundNotify";

	@RequestMapping(value = "/payAction", method = { RequestMethod.GET, RequestMethod.POST })
	public void payAction(FeeItemCode feeItem, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			Long walletId = ServletRequestUtils.getLongParameter(request, "walletId", 0L);
			Long orderId = ServletRequestUtils.getLongParameter(request, "orderId", 0L);
			Double payMoney = ServletRequestUtils.getDoubleParameter(request, "payMoney", 0.00D);
			Integer suretyDays = ServletRequestUtils.getIntParameter(request, "suretyDays", 99999999);
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");

			// 取得生成的订单信息
			HyqWalletData walletData = walletFillPayService.getWalletData(userData.getId(), walletId, orderId, feeItem,
					payMoney, suretyDays, remark);
			Map<String, Object> content = new HashMap<String, Object>();
			if (walletData != null) {
				content.put("walletData", walletData);
				content.put("netpayurl", Constants.NETPAY_URL);
				content.put("nobindpayurl", Constants.NOBINDPAY_URL);
				content.put("returnurl", RETURN_URL);
				content.put("notifyurl", NOTIFY_URL);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "success");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "获取支付信息失败");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);

	}

	// 非绑定，重新生成orig和sign
	@RequestMapping(value = "/noBindPay", method = { RequestMethod.POST })
	public void nobindPay(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			// 验证该账单是否是当前用户的，否则有安全隐患
			HyqWalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				Map<String, String> mapResult = walletFillPayService.noBindPay(walletData);
				Map<String, Object> content = new HashMap<String, Object>();
				if (!"".equals(mapResult.get("orig")))
					content.put("orig", mapResult.get("orig"));
				if (!"".equals(mapResult.get("sign")))
					content.put("sign", mapResult.get("sign"));
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.MESSAGE, "生成成功");
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

	// 绑定并支付选择银行，重新生成orig和sign
	@RequestMapping(value = "/bindAndPay", method = { RequestMethod.POST })
	public void bindAndPay(Long walletId, String plantBankId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			// 验证该账单是否是当前用户的，否则有安全隐患
			HyqWalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				Map<String, String> mapResult = walletFillPayService.bindAndPay(walletData, plantBankId);
				Map<String, Object> content = new HashMap<String, Object>();
				if (!"".equals(mapResult.get("orig")))
					content.put("orig", mapResult.get("orig"));
				if (!"".equals(mapResult.get("sign")))
					content.put("sign", mapResult.get("sign"));
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.MESSAGE, "生成成功");
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

	@RequestMapping(value = "/autoJump", method = { RequestMethod.GET })
	public ModelAndView autoJump(Long walletId, FeeItemCode feeItem, String remark, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用户信息
		HyqUserData userData = this.getLoginUserData(request, response);

		ModelAndView mav = new ModelAndView("/mobile/scf-space-auto-jump");

		int type = ServletRequestUtils.getIntParameter(request, "type", 1);
		String plantBankId = ServletRequestUtils.getStringParameter(request, "plantBankId", "0");

		// 取得生成的订单信息
		HyqWalletData walletData = walletFillPayService.getWalletData(userData.getId(), walletId, 0L, feeItem, 0.00D, 0,
				remark);
		if (walletData != null) {
			// Map<String, String> map = new HashMap<>();
			// if(type == 1){
			// map = pinganpayService.noBindPay(walletData);
			// }else if(type == 2){
			// map = pinganpayService.bindAndPay(walletData, plantBankId);
			// }
			//
			// if (walletData != null)
			// mav.addObject("walletId", walletData.getId());
			// if (!"".equals(map.get("orig")))
			// mav.addObject("orig", map.get("orig"));
			// if (!"".equals(map.get("sign")))
			// mav.addObject("sign", map.get("sign"));
			mav.addObject("type", type);
			mav.addObject("plantBankId", plantBankId);
			mav.addObject("walletId", walletData.getId());
			if (type == 1) {
				mav.addObject("url", Constants.NOBINDPAY_URL);
			} else if (type == 2) {
				mav.addObject("url", Constants.BINDANDPAY_URL);
			}
			mav.addObject("sessionId",
					ServletRequestUtils.getStringParameter(request, HyqLoginController.MBL_SESSION_ID, ""));
			mav.addObject("returnurl", RETURN_URL);
			mav.addObject("notifyurl", NOTIFY_URL);
		}
		return mav;
	}

	// 获取支付时绑定卡信息
	@RequestMapping(value = "/getBindCards", method = { RequestMethod.GET })
	public void getBindCards(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");

			HyqWalletData walletData = walletService.getBillData(walletId);
			if (walletData != null) {
				// 用户绑定的银行卡列表
				List<HyqUserPinganBankData> userBankDatas = walletFillPayService.getBindCards(walletData);

				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "获取信息成功");
				map.put(JsonResponser.CONTENT, userBankDatas);
				map.put(JsonResponser.CONTENTMD5, contentMD5);
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 验证密码
	@RequestMapping(value = "/valid", method = { RequestMethod.POST })
	public void validPwd(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			HyqUserData userData = this.getLoginUserData(request, response);

			String pwd = ServletRequestUtils.getStringParameter(request, "pwd", "");
			int type = ServletRequestUtils.getIntParameter(request, "type", 1);
			String plantBankId = ServletRequestUtils.getStringParameter(request, "plantBankId", "0");

			// 验证该账单是否是当前用户的，否则有安全隐患
			HyqWalletData walletData = walletService.getBillData(walletId);
			// if (walletData.getSettleStyle() != SettleStyleCode.fill)
			if (!userService.checkPayPwd(userData, pwd))
				throw new Exception("支付密码输入错误，如未设置支付密码请到设置中进行设置,以确保您的支付安全!");
			if (walletData != null) {
				Map<String, String> mapResult = new HashMap<>();
				if (type == 1) {
					mapResult = walletFillPayService.noBindPay(walletData);
					map.put("url", Constants.NOBINDPAY_URL);
				} else if (type == 2) {
					mapResult = walletFillPayService.bindAndPay(walletData, plantBankId);
					map.put("url", Constants.BINDANDPAY_URL);
				}

				if (!"".equals(mapResult.get("orig")))
					map.put("orig", mapResult.get("orig"));
				if (!"".equals(mapResult.get("sign")))
					map.put("sign", mapResult.get("sign"));
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取短信验证码发送时间
	@RequestMapping(value = "/getValidCode", method = { RequestMethod.POST })
	public void getValidCode(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			int source = ServletRequestUtils.getIntParameter(request, "source", 1);// 钱包还是绑定卡
			String bindId = ServletRequestUtils.getStringParameter(request, "bindId", "0");

			HyqWalletData walletData = walletService.getBillData(walletId);
			String sign = "";
			if (2 == source) {
				sign = walletFillPayService.getValidCode(walletData, bindId); // 签名
			} else {
				// 申请动态短信验证码
				Map<String, String> mapResult = walletFillPayService.getSerialNo(walletData, "2");
				sign = (String) mapResult.get("serialNo");
			}

			if (sign != null && !"".equals(sign)) {
				Map<String, Object> content = new HashMap<>();
				content.put("sign", sign);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.CONTENT, content);
				map.put(JsonResponser.MESSAGE, "获取验证码成功！");
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

	// 钱包支付
	@RequestMapping(value = "/walletOrBindCardPay", method = { RequestMethod.POST })
	public void walletPay(Long walletId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 用户信息
		HyqUserData userData = this.getLoginUserData(request, response);

		int source = ServletRequestUtils.getIntParameter(request, "source", 1);
		String sign = ServletRequestUtils.getStringParameter(request, "sign", "");
		String verifyCode = ServletRequestUtils.getStringParameter(request, "verifyCode", "");
		String bindId = ServletRequestUtils.getStringParameter(request, "bindId", "");
		String pwd = ServletRequestUtils.getStringParameter(request, "pwd", "");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 先验证支付密码
			if (pwd.isEmpty()) {
				throw new Exception("请输入支付密码!");
			} else {
				if (!userService.checkPayPwd(userData, pwd))
					throw new Exception("支付密码输入错误，如未设置支付密码请到设置中进行设置,以确保您的支付安全!");
			}
			HyqWalletData walletData = walletService.getBillData(walletId);
			if (1 == source) {
				boolean b = walletFillPayService.walletPay(walletData, sign, verifyCode);
				if (b) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "支付成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "支付失败！");
				}
			} else if (2 == source) {
				PayKhsdCode result = walletFillPayService.bindFastPay(walletData, bindId, verifyCode, sign, NOTIFY_URL2);// API007
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

			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 钱包支付
	@RequestMapping(value = "/walletOpt", method = { RequestMethod.POST })
	public void walletOpt(Long walletId, WalletOptCode walletOptCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// 用户信息
		@SuppressWarnings("unused")
		HyqUserData userData = this.getLoginUserData(request, response);

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			HyqWalletData walletData = walletService.getBillData(walletId);
			boolean result = false;
			if (walletOptCode.equals(WalletOptCode.confirmPay)) {
				result = walletFillPayService.confirmPay(walletData);
			} else if (walletOptCode.equals(WalletOptCode.applyRefund)) {
				result = walletFillPayService.refundApply(walletData);
			}

			if (result) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "操作成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "操作失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 退款处理
	@RequestMapping(value = "/doRefund", method = { RequestMethod.POST })
	public void doRefund(Long walletId, ApplyReplyCode applyReply, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 用户信息
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			HyqWalletData walletData = walletService.getBillData(walletId);

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
		ModelAndView mav = new ModelAndView("/space/scf-space-pingan-callback");

		mav.addObject("result", result);
		return mav;
	}
}
