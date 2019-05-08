package com.hangyi.eyunda.controller.mobile;

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

import com.hangyi.eyunda.chat.data.OnlineUser;
import com.hangyi.eyunda.chat.redis.recorder.OnlineUserRecorder;
import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.wallet.UserPinganBankData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayKhsdCode;
import com.hangyi.eyunda.domain.enumeric.WalletOptCode;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.wallet.WalletFillPayService;
import com.hangyi.eyunda.service.wallet.WalletService;
import com.hangyi.eyunda.util.Constants;

@Controller
@RequestMapping("/mobile/pinganpay")
public class MblWalletPinanPayController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	public static final int PAGE_SIZE = 10;

	@Autowired
	private UserService userService;
	@Autowired
	private WalletFillPayService pinganpayService;
	@Autowired
	private WalletService walletService;
	@Autowired
	private OnlineUserRecorder onlineUserRecorder;

	private static final String RETURN_URL = "http://" + Constants.DOMAIN_NAME + "/space/pinganpay/returnpage/1";
	private static final String NOTIFY_URL = "http://" + Constants.DOMAIN_NAME + "/space/pinganpay/notifypage/1";
	private static final String NOTIFY_URL2 = "http://" + Constants.DOMAIN_NAME + "/space/pinganpay/notifypage/2";
	private static final String KHREFUNDNOTIFY_URL = "http://" + Constants.DOMAIN_NAME
			+ "/space/pinganpay/khRefundNotify";

	@RequestMapping(value = "/payAction", method = { RequestMethod.GET, RequestMethod.POST })
	public void payAction(FeeItemCode feeItem, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			UserData userData = userService.getById(ou.getId());

			Long walletId = ServletRequestUtils.getLongParameter(request, "walletId", 0L);
			Long orderId = ServletRequestUtils.getLongParameter(request, "orderId", 0L);
			Double payMoney = ServletRequestUtils.getDoubleParameter(request, "payMoney", 0.00D);
			Integer suretyDays = ServletRequestUtils.getIntParameter(request, "suretyDays", 0);
			String remark = ServletRequestUtils.getStringParameter(request, "remark", "");

			// 取得生成的订单信息
			WalletData walletData = pinganpayService.getWalletData(userData.getId(), walletId, orderId, feeItem,
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
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			// 验证该账单是否是当前用户的，否则有安全隐患
			WalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				Map<String, String> mapResult = pinganpayService.noBindPay(walletData);
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
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			// 验证该账单是否是当前用户的，否则有安全隐患
			WalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				Map<String, String> mapResult = pinganpayService.bindAndPay(walletData, plantBankId);
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
		ModelAndView mav = new ModelAndView("/mobile/space-auto-jump");

		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
		int type = ServletRequestUtils.getIntParameter(request, "type", 1);
		String plantBankId = ServletRequestUtils.getStringParameter(request, "plantBankId", "0");

		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
		UserData userData = userService.getById(ou.getId());

		// 取得生成的订单信息
		WalletData walletData = pinganpayService.getWalletData(userData.getId(), walletId, 0L, feeItem, 0.00D, 0,
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
			mav.addObject("sessionId", sessionId);
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
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			String contentMD5 = ServletRequestUtils.getStringParameter(request, JsonResponser.CONTENTMD5, "");
			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			// UserData userData = userService.getById(ou.getId());
			WalletData walletData = walletService.getBillData(walletId);
			if (walletData != null) {
				// 用户绑定的银行卡列表
				List<UserPinganBankData> userBankDatas = pinganpayService.getBindCards(walletData);

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
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			String pwd = ServletRequestUtils.getStringParameter(request, "pwd", "");
			int type = ServletRequestUtils.getIntParameter(request, "type", 1);
			String plantBankId = ServletRequestUtils.getStringParameter(request, "plantBankId", "0");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			UserData userData = userService.getById(ou.getId());

			// 验证该账单是否是当前用户的，否则有安全隐患
			WalletData walletData = walletService.getBillData(walletId);
			// if (walletData.getSettleStyle() != SettleStyleCode.fill)
			if (!userService.checkPayPwd(userData, pwd))
				throw new Exception("支付密码输入错误，如未设置支付密码请到设置中进行设置,以确保您的支付安全!");
			if (walletData != null) {
				Map<String, String> mapResult = new HashMap<>();
				if (type == 1) {
					mapResult = pinganpayService.noBindPay(walletData);
					map.put("url", Constants.NOBINDPAY_URL);
				} else if (type == 2) {
					mapResult = pinganpayService.bindAndPay(walletData, plantBankId);
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
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			int source = ServletRequestUtils.getIntParameter(request, "source", 1);// 钱包还是绑定卡
			String bindId = ServletRequestUtils.getStringParameter(request, "bindId", "0");

			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			// UserData userData = userService.getById(ou.getId());

			WalletData walletData = walletService.getBillData(walletId);
			String sign = "";
			if (2 == source) {
				sign = pinganpayService.getValidCode(walletData, bindId); // 签名
			} else {
				// 申请动态短信验证码
				Map<String, String> mapResult = pinganpayService.getSerialNo(walletData, "2");
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
		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
		UserData userData = userService.getById(ou.getId());

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
			WalletData walletData = walletService.getBillData(walletId);
			if (1 == source) {
				boolean b = pinganpayService.walletPay(walletData, sign, verifyCode);
				if (b) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "支付成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "支付失败！");
				}
			} else if (2 == source) {
				PayKhsdCode result = pinganpayService.bindFastPay(walletData, bindId, verifyCode, sign, NOTIFY_URL2);// API007
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
		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
		OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
		if (ou == null)
			throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
		// UserData userData = userService.getById(ou.getId());

		Map<String, Object> map = new HashMap<String, Object>();
		try {

			WalletData walletData = walletService.getBillData(walletId);
			boolean result = false;
			if (walletOptCode.equals(WalletOptCode.confirmPay)) {
				result = pinganpayService.confirmPay(walletData);
			} else if (walletOptCode.equals(WalletOptCode.applyRefund)) {
				result = pinganpayService.refundApply(walletData);
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
			String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId", "");
			OnlineUser ou = onlineUserRecorder.getOnlineUser(sessionId);
			if (ou == null)
				throw new Exception("session信息已丢失，正在重新连接，请稍后重试！");
			// UserData userData = userService.getById(ou.getId());

			WalletData walletData = walletService.getBillData(walletId);

			if (walletData != null) {
				boolean result = pinganpayService.doRefund(walletData, applyReply, KHREFUNDNOTIFY_URL);
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

}
