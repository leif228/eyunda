package com.hangyi.eyunda.controller.manage.wallet;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.wallet.CustAcctData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.data.wallet.WalletSettleData;
import com.hangyi.eyunda.data.wallet.WalletZjjzData;
import com.hangyi.eyunda.data.wallet.ZjjzFetchData;
import com.hangyi.eyunda.data.wallet.ZjjzPayData;
import com.hangyi.eyunda.data.wallet.ZjjzResultData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.EnumConst.RecentPeriodCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.wallet.WalletFillPayService;
import com.hangyi.eyunda.service.wallet.WalletService;
import com.hangyi.eyunda.service.wallet.WalletSettleService;
import com.hangyi.eyunda.service.wallet.WalletZjjzService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.HtmlToPdfUtil;

@Controller
// @RequestMapping("/manage/wallet")
public class WalletManageController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private WalletService walletBillService;
	@Autowired
	private WalletSettleService walletSettleService;
	@Autowired
	private WalletZjjzService walletZjjzService;
	@Autowired
	private WalletFillPayService walletFillPayService;

	private static final String KHREFUNDNOTIFY_URL = "http://" + Constants.DOMAIN_NAME
			+ "/space/pinganpay/khRefundNotify";

	@RequestMapping(value = "/walletQuery", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView walletQuery(String userInfo, SettleStyleCode settleStyle, PayStatusCode paymentStatus,
			ApplyReplyCode refundStatus, String startDate, String endDate, Page<WalletData> pageData) throws Exception {

		Page<WalletData> pd = walletBillService.findWalletByStyle(userInfo, settleStyle, paymentStatus, refundStatus,
				startDate, endDate, pageData.getPageNo(), pageData.getPageSize());

		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-wallet-query");

		List<SettleStyleCode> settleStyles = new ArrayList<SettleStyleCode>();
		settleStyles.add(SettleStyleCode.fill);
		settleStyles.add(SettleStyleCode.fetch);
		settleStyles.add(SettleStyleCode.pay);
		mav.addObject("settleStyles", settleStyles);
		mav.addObject("paymentStatuss", PayStatusCode.values());
		mav.addObject("refundStatuss", ApplyReplyCode.values());

		mav.addObject("userInfo", (userInfo == null) ? "" : userInfo);
		mav.addObject("settleStyle", settleStyle);
		mav.addObject("paymentStatus", paymentStatus);
		mav.addObject("refundStatus", refundStatus);

		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);
		mav.addObject("pageData", pd);
		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.WALLET);
		mav.addObject("menuAct", MenuCode.WALLET_QUERY);
		return mav;
	}

	// 根据id取得一条支付记录
	@RequestMapping(value = "/{id}", method = { RequestMethod.GET })
	public void getWalletData(@PathVariable("id") Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			WalletData walletData = walletBillService.getWalletDataInclZjjz(id);

			if (walletData != null) {
				map.put("wallet", walletData);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
				map.put(JsonResponser.MESSAGE, "支付记录不存在");
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 退款处理
	@RequestMapping(value = "/doRefund", method = { RequestMethod.POST })
	public void doRefund(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			WalletData walletData = walletBillService.getBillData(walletId);
			if (walletData != null) {
				// 若是钱包支付，利用见证宝API退款，若是银行卡支付，利用跨行收单退款接口退款
				boolean result = walletFillPayService.doRefund(walletData, ApplyReplyCode.reply, KHREFUNDNOTIFY_URL);

				if (result) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "退款处理成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "退款处理失败！");
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	// 获取一个对象订单的支付异常信息
	@RequestMapping(value = "/getPayErrorInfo", method = { RequestMethod.GET })
	public ModelAndView getPayErrorInfo(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-wallet-error-info");

		WalletData walletData = walletBillService.getWalletDataInclZjjz(walletId);
		mav.addObject("walletData", walletData);
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.WALLET);
		mav.addObject("menuAct", MenuCode.WALLET_QUERY);
		return mav;
	}

	// 确认付款
	@RequestMapping(value = "/doPayment", method = { RequestMethod.POST })
	public void doPayment(Long walletId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			WalletData walletData = walletBillService.getBillData(walletId);
			if (walletData != null) {
				// 无论是钱包支付，还是银行卡支付，均利用见证宝API确认付款
				boolean result = walletFillPayService.confirmPay(walletData);

				if (result) {
					map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
					map.put(JsonResponser.MESSAGE, "确认付款成功！");
				} else {
					map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
					map.put(JsonResponser.MESSAGE, "确认付款失败！");
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "/walletPlat", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView walletPlat(Page pageData, PayStatusCode statusCode, String startDate, String endDate)
			throws Exception {
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-wallet-plat");

		// 平台子帐户余额
		List<CustAcctData> cads = new ArrayList<CustAcctData>();
		cads = walletFillPayService.api6010("", "3", "1");
		mav.addObject("cads", cads);// 平台子帐户余额
		Map<String, Double> map = walletFillPayService.api6011();
		mav.addObject("supAcct", map);// 平台汇总帐户余额

		// 平台收入查询
		Page<WalletData> pd = walletBillService.walletPlatQuery(statusCode, startDate, endDate, pageData.getPageNo(),
				pageData.getPageSize());

		PayStatusCode[] payStatus = PayStatusCode.values();
		mav.addObject("pageData", pd);
		mav.addObject("payStatus", payStatus);
		mav.addObject("payStatu", statusCode);
		mav.addObject("startDate", startDate);
		mav.addObject("endDate", endDate);

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.WALLET);
		mav.addObject("menuAct", MenuCode.WALLET_PLAT);
		return mav;
	}

	// 获取用户的账务信息
	@RequestMapping(value = "/getUsersSettle", method = { RequestMethod.GET })
	public ModelAndView getUsersSettle(String userInfo, RecentPeriodCode termCode, Page<WalletSettleData> pageData,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
		ModelAndView mav = new ModelAndView("/manage/manage-wallet-check");

		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		Page<WalletSettleData> pg = walletSettleService.walletSettleQuery(userInfo, termCode, pageData.getPageNo(),
				pageData.getPageSize());

		mav.addObject("userInfo", userInfo);
		mav.addObject("termCodes", RecentPeriodCode.values());
		mav.addObject("termCode", termCode);
		mav.addObject("pageData", pg);

		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.WALLET);
		mav.addObject("menuAct", MenuCode.WALLET_CHECK);

		return mav;
	}

	// 核对用户账务
	@RequestMapping(value = "/checkSettle", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView checkSettle(Long userId, RecentPeriodCode termCode, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/manage/manage-wallet-settle");

		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		List<WalletSettleData> settleDatas = walletSettleService.walletSettleQuery(userId, termCode);
		List<ZjjzFetchData> zfDatas = walletFillPayService.queryUserFetch(userId, termCode);
		List<ZjjzPayData> zpDatas = walletFillPayService.queryUserPay(userId, termCode);

		mav.addObject("settleDatas", settleDatas);
		mav.addObject("zfDatas", zfDatas);
		mav.addObject("zpDatas", zpDatas);

		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.WALLET);
		mav.addObject("menuAct", MenuCode.WALLET_CHECK);

		return mav;
	}

	// 核对定时器执行情况
	@RequestMapping(value = "/walletQuery/autoPay", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView checkOrderAutoPay(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/manage/manage-auto-pay");
		String out = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		out += sdf.format(new Date()) + " ---- OrderAutoPay start!" + "<br/>";
		try {
			/*
			 * // 查询跨行支付定单的处理状态 List<WalletData> wds =
			 * walletService.findByKhWaitStatus(YesNoCode.yes);// 查询处于支付等待中的订单
			 * for (WalletData wd : wds)
			 * walletFillPayService.KhOrderStatusQuery(wd);// 已到帐的清分或清分冻结
			 */

			// 提现退单处理
			String yesterday = CalendarUtil.toYYYY_MM_DD(CalendarUtil.addDays(CalendarUtil.now(), -7));
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());

			List<WalletZjjzData> wzds = walletZjjzService.findFetchZjjz(yesterday, today);
			out += "[api6014调用开始，总记录数" + wzds.size() + "]<br/>";
			int i = 0;
			for (WalletZjjzData wzd : wzds) {
				i++;
				out += "</pre>" + wzd.getJsonString() + "</pre><br/>";
				ZjjzResultData zrd = null;
				try {
					zrd = walletFillPayService.api6014(wzd);
				} catch (Exception e1) {
					out += "</pre>" + e1.getMessage() + "</pre><br/>";
					continue;
				}
				out += "</pre>" + zrd.getJsonString() + "</pre><br/>";

				out += "api6014调用中，记录号：" + i + "<br/>";

				WalletData theWalletData = walletBillService.getByPaymentNo(wzd.getPaymentNo());

				if (theWalletData != null) {
					if (!"0".equals(zrd.getTranStatus())) {
						if (theWalletData.getPaymentStatus() == PayStatusCode.TRADE_FINISHED) {
							walletBillService.updatePayStatus(wzd.getPaymentNo(), PayStatusCode.TRADE_CLOSED,
									theWalletData.getBody() + "|" + zrd.getReserve()); // 钱包帐务添加提现退单记录
							walletSettleService.saveWalletSettle(theWalletData.getBuyerId(), theWalletData.getId(),
									WalletSettleCode.backfetch, theWalletData.getTotalFee());
						}
					}
				}
			}

			// 自动确认付款
			List<WalletData> wallets = walletBillService.getExpiredBillDatas();
			out += "[自动确认付款，记录数：" + wallets.size() + "]<br/>";
			for (WalletData wallet : wallets) {
				try {
					boolean result = walletFillPayService.confirmPay(wallet);

					if (result) {
						out += "支付号：" + wallet.getPaymentNo() + "，定时器自动确认付款成功！<br/>";
					} else {
						out += "支付号：" + wallet.getPaymentNo() + "，，定时器自动确认付款失败！<br/>";
					}
				} catch (Exception e) {
					out += "支付号：" + wallet.getPaymentNo() + e.getMessage() + "--></pre>" + wallet.getJsonString()
							+ "</pre><br/>";
					continue;
				}
			}

			// 更新昨天钱包交易帐务的可提现余额等于可用余额
			out += "[更新昨天钱包交易帐务的可提现余额等于可用余额]<br/>";
			walletSettleService.updateFetchableMoney();
			out += "更新昨天钱包交易帐务的可提现余额等于可用余额结束<br/>";

		} catch (Exception e) {
			out += "程序执行异常：" + e.getMessage() + "<br/>";
		}
		out += sdf.format(new Date()) + " ---- OrderAutoPay ended!" + "<br/>";

		mav.addObject("out", out);
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.WALLET);
		mav.addObject("menuAct", MenuCode.WALLET_CHECK);

		return mav;
	}

	// 账务
	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(Long walletId, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/manage/wallet/wallet-info");

		WalletData walletData = walletBillService.getBillData(walletId);
		mav.addObject("walletData", walletData);
		mav.addObject("settleType", settleType);
		return mav;
	}

	@RequestMapping(value = "/showAct", method = RequestMethod.GET)
	public ModelAndView showAct(Long walletId, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("/manage/wallet/wallet-info-container");

		mav.addObject("walletId", walletId);
		mav.addObject("settleType", settleType);
		return mav;
	}

	// 下载PDF
	@RequestMapping(value = "/downloadPdf", method = { RequestMethod.GET })
	public ModelAndView downloadPdf(Long walletId, WalletSettleCode settleType, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			WalletData walletData = walletBillService.getBillData(walletId);
			if (walletData == null) {
				throw new Exception("错误!账务不存在，无法生成账单文件。");
			}

			// 合同页面地址
			String urlStr = "http://" + Constants.LOCALE_NAME + "/manage/wallet/show?settleType="
					+ settleType.toString() + "&walletId=" + walletId;

			// 合同文件名及路径
			String realPath = Constants.SHARE_DIR;

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(walletData.getGmtPayment()));
			String pdfFileName = ShareDirService.getUserDir(0L) + "/wallet/" + walletData.getId() + "/" + "Wallet"
					+ walletData.getId() + "-" + CalendarUtil.toYYYYMMDDHHmmss(calendar);
			String relativePath = (pdfFileName + ".pdf");

			// 生成合同pdf文件
			File pdfFile = HtmlToPdfUtil.exportPdfFile(urlStr, realPath + relativePath);

			if (pdfFile.exists()) {
				ModelAndView mav = new ModelAndView("redirect:/download/fileDownload?url=" + relativePath);
				return mav;
			} else {
				throw new Exception("错误!生成账单时出现错误。");
			}
		} catch (Exception e) {
			throw new Exception("错误!生成账单文件时出现错误。");
		}
	}

}
