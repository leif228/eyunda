package com.hangyi.eyunda.service.wallet;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ecc.emp.data.IndexedCollection;
import com.ecc.emp.data.KeyedCollection;
// import com.hangyi.eyunda.dao.YydOrderDao;
import com.hangyi.eyunda.dao.YydWalletDao;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.OrderCommonData;
import com.hangyi.eyunda.data.wallet.BackMoneyData;
import com.hangyi.eyunda.data.wallet.CustAcctData;
import com.hangyi.eyunda.data.wallet.UserPinganBankData;
import com.hangyi.eyunda.data.wallet.WalletData;
import com.hangyi.eyunda.data.wallet.WalletZjjzData;
import com.hangyi.eyunda.data.wallet.ZjjzFetchData;
import com.hangyi.eyunda.data.wallet.ZjjzFillData;
import com.hangyi.eyunda.data.wallet.ZjjzPayData;
import com.hangyi.eyunda.data.wallet.ZjjzResultData;
// import com.hangyi.eyunda.domain.YydOrder;
import com.hangyi.eyunda.domain.YydWallet;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.BankCode2;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayKhsdCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.PayStyleCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.WalletSettleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.pay.pinanpay.PapayHttpProtocolHandler;
import com.hangyi.eyunda.pay.pinanpay.StringUtil;
import com.hangyi.eyunda.service.EnumConst.RecentPeriodCode;
import com.hangyi.eyunda.service.PaySequenceService;
import com.hangyi.eyunda.service.account.AccountService;
import com.hangyi.eyunda.service.order.OrderCommonService;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.NumberFormatUtil;
import com.sdb.payclient.bean.exception.CsiiException;
import com.sdb.payclient.core.PayclientInterfaceUtil;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED, readOnly = false)
public class WalletFillPayService {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private YydWalletDao walletDao;

	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;
	@Autowired
	private WalletService walletBillService;
	@Autowired
	private PaySequenceService paySequenceService;
	@Autowired
	private OrderCommonService orderService;
	@Autowired
	private ZjjzApiService zjjzApiService;
	@Autowired
	private WalletZjjzService walletZjjzService;
	@Autowired
	private WalletKhsdService walletKhsdService;
	@Autowired
	private WalletSettleService walletSettleService;

	// 处理返回
	public PayKhsdCode doReturn(YesNoCode khFlag, String sign, String orig) throws Exception {
		orig = URLDecoder.decode(orig, "GBK");
		sign = URLDecoder.decode(sign, "GBK");

		return this.doNotify(khFlag, sign, orig);
	}

	// 银行通知、支付成功后调用
	public PayKhsdCode doNotify(YesNoCode khFlag, String sign, String orig) throws Exception {
		// 01表示支付成功，其他为支付失败
		PayclientInterfaceUtil util = new PayclientInterfaceUtil();
		KeyedCollection output = new KeyedCollection("output");
		orig = PayclientInterfaceUtil.Base64Decode(orig, "GBK");
		sign = PayclientInterfaceUtil.Base64Decode(sign, "GBK");
		boolean result = util.verifyData(sign, orig);
		if (result) {
			output = util.parseOrigData(orig);

			// 更改订单状态、合同状态、支付状态
			WalletData walletData = walletBillService.getByPaymentNo((String) output.getDataValue("orderId"));
			// 避免重复处理
			if (PayStatusCode.WAIT_PAYMENT == walletData.getPaymentStatus()) {
				if (khFlag == YesNoCode.yes) {
					walletKhsdService.callBehindRecord(walletData.getPaymentNo(), "KHPAYMENT", output);
				} else {
					walletKhsdService.callBehindRecord(walletData.getPaymentNo(), "API007", output);
				}
			}
			String bankCardNo4 = "";// 返回的银行卡号后四位
			String plantBankName = "";// 返回的支付银行名称
			try {
				bankCardNo4 = (String) output.getDataValue("accountNo");
				plantBankName = (String) output.getDataValue("plantBankName");
			} catch (Exception ee) {
				logger.info(ee.getMessage());// 非绑定支付没有以上两字段信息返回
			}
			this.updateUsedBankCard(walletData.getId(), bankCardNo4, plantBankName);// 记下支付使用的银行卡信息

			String status = (String) output.getDataValue("status");
			// status订单状态，“00”，支付处理中，“01”，支付成功，“02”，支付失败
			if ("01".equals(status)) {
				// 清分并改帐单状态为支付成功，周五时这里没有加条件是不是？
				this.qingfenOrSurety(walletData);

				return PayKhsdCode.success;
			} else if ("00".equals(status)) {
				// 改帐单状态为支付处理中...
				this.updateWalletStatus(walletData.getId(), null, null, null, null, null, YesNoCode.yes, YesNoCode.yes);

				return PayKhsdCode.paying; // 支付处理中，你的银行卡转帐将会在24小时内完成。
			} else {
				// 改帐单状态为支付失败
				this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_CLOSED, null, null, null, null,
						YesNoCode.yes, YesNoCode.no);

				return PayKhsdCode.failure; // 跨行收单支付失败，请重新支付！
			}
		} else {
			throw new Exception("跨行收单回调通知，数据校验失败！");
		}
	}

	public void updateWalletStatus(Long walletId, PayStatusCode payStatusCode, ApplyReplyCode applyReplyCode,
			Calendar payTime, Calendar suretyTime, Calendar applyReplyTime, YesNoCode khFlag, YesNoCode khWaitFlag)
			throws Exception {
		YydWallet wallet = walletDao.get(walletId);
		if (wallet != null) {
			if (payStatusCode != null)
				wallet.setPaymentStatus(payStatusCode);
			if (applyReplyCode != null)
				wallet.setRefundStatus(applyReplyCode);
			if (payTime != null)
				wallet.setGmtPayment(payTime);
			if (suretyTime != null)
				wallet.setGmtSurety(suretyTime);
			if (applyReplyTime != null)
				wallet.setGmtRefund(applyReplyTime);
			if (khFlag != null)
				wallet.setKhFlag(khFlag);
			if (khWaitFlag != null)
				wallet.setKhRefundFlag(khWaitFlag);

			walletDao.save(wallet);
		} else {
			throw new Exception("支付记录不能为空！");
		}
	}

	public void updateUsedBankCard(Long walletId, String bankCardNo4, String plantBankName) throws Exception {
		YydWallet wallet = walletDao.get(walletId);
		if (wallet != null) {
			if (bankCardNo4 != null)
				wallet.setBankCardNo4(bankCardNo4);
			if (plantBankName != null)
				wallet.setPlantBankName(plantBankName);

			walletDao.save(wallet);
		} else {
			throw new Exception("支付记录不能为空！");
		}
	}

	// 跨行收单退款通知
	public void doKHRefundNotify(String sign, String orig) {
		try {
			PayclientInterfaceUtil util = new PayclientInterfaceUtil();
			KeyedCollection output = new KeyedCollection("output");
			orig = PayclientInterfaceUtil.Base64Decode(orig, "GBK");
			sign = PayclientInterfaceUtil.Base64Decode(sign, "GBK");
			boolean result = util.verifyData(sign, orig);
			if (result) {
				output = util.parseOrigData(orig);
				if ("01".equals((String) output.getDataValue("status"))) {
					WalletData walletData = walletBillService.getByPaymentNo((String) output.getDataValue("orderId"));
					// 避免重复处理
					if (walletData != null && ApplyReplyCode.reply != walletData.getRefundStatus()) {
						// 更改订单状态
						this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_CLOSED, ApplyReplyCode.reply,
								null, null, Calendar.getInstance(), null, null);
					}
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	// 确认付款
	public boolean confirmPay(WalletData walletData) throws Exception {
		boolean flag = false;
		try {
			YydWallet wallet = walletDao.get(walletData.getId());
			if (wallet != null) {
				if (wallet.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM) {

					if (this.isThreeOrder(walletData)) {
						// 付款方付钱到收款方 （如果为合同支付且船东不是船代）
						flag = this.api6070(walletData);
					} else {
						// 付款方付钱到收款方
						flag = this.api6034(walletData, "2", "", "");
					}

					if (flag) {
						switch (walletData.getFeeItem()) {
						case face:
							// 更改支付状态
							this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_FINISHED,
									ApplyReplyCode.noapply, null, Calendar.getInstance(), null, null, null);
							// 钱包帐务添加收款人直接收款记录
							walletSettleService.saveWalletSettle(walletData.getSellerId(), walletData.getId(),
									WalletSettleCode.receivefee, NumberFormatUtil.format(walletData.getTotalFee()
											- walletData.getMiddleFee() - walletData.getServiceFee()));
							// 钱包帐务添加平台服务费记录
							walletSettleService.saveWalletSettle(0L, walletData.getId(), WalletSettleCode.platfee,
									walletData.getServiceFee());
							break;

						case prefee:
							// 更改订单状态
							this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_FINISHED,
									ApplyReplyCode.noapply, null, Calendar.getInstance(), null, null, null);
							// 钱包帐务添加承运人合同收款记录
							walletSettleService.saveWalletSettle(walletData.getSellerId(), walletData.getId(),
									WalletSettleCode.masterfee, NumberFormatUtil.format(walletData.getTotalFee()
											- walletData.getMiddleFee() - walletData.getServiceFee()));
							// 钱包帐务添加代理人佣金记录
							walletSettleService.saveWalletSettle(walletData.getBrokerId(), walletData.getId(),
									WalletSettleCode.brokerfee, walletData.getMiddleFee());
							// 钱包帐务添加平台服务费记录
							walletSettleService.saveWalletSettle(0L, walletData.getId(), WalletSettleCode.platfee,
									walletData.getServiceFee());
							break;

						case bonus:
							// 更改订单状态
							this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_FINISHED,
									ApplyReplyCode.noapply, null, Calendar.getInstance(), null, null, null);
							// 钱包帐务添加收款人收取红包记录
							walletSettleService.saveWalletSettle(walletData.getSellerId(), walletData.getId(),
									WalletSettleCode.bonus, NumberFormatUtil.format(walletData.getTotalFee()
											- walletData.getMiddleFee() - walletData.getServiceFee()));
							break;

						default:
							break;
						}
					}
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
		return flag;
	}

	// 退款申请
	public boolean refundApply(WalletData walletData) throws Exception {
		try {
			if (walletData != null) {
				if ((walletData.getRefundStatus() == ApplyReplyCode.noapply
						|| walletData.getRefundStatus() == ApplyReplyCode.noreply)
						&& walletData.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM) {
					this.updateWalletStatus(walletData.getId(), null, ApplyReplyCode.apply, null, null,
							Calendar.getInstance(), null, null);
					return true;
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
		return false;
	}

	// 退款处理
	public boolean doRefund(WalletData walletData, ApplyReplyCode applyReply, String khRefundNotifyUrl)
			throws Exception {
		boolean flag = false;
		try {
			if (walletData != null) {
				if (walletData.getPaymentStatus() == PayStatusCode.WAIT_CONFIRM) {
					if (ApplyReplyCode.reply == applyReply) {
						// 银行卡支付线下原路返回
						boolean bKhRefund = false;

						if (!bKhRefund) {
							if (this.isThreeOrder(walletData)) {
								// 如果为合同支付且船东不是船代
								flag = this.api6007(walletData, "2", "", "");
							} else {
								// 资金托管到会员
								flag = this.api6034(walletData, "3", "", "");
							}

							// 退款成功后更改订单状态
							if (flag) {
								// 更改支付订单状态
								this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_CLOSED,
										ApplyReplyCode.reply, null, null, Calendar.getInstance(), null, null);
								// 钱包帐务添加退回托管资金记录
								walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(),
										WalletSettleCode.refund, walletData.getTotalFee());
							}
						}
					} else {
						// 拒绝退款
						flag = true;
						this.updateWalletStatus(walletData.getId(), null, ApplyReplyCode.noreply, null, null, null,
								null, null);
					}
				}
			} else {
				throw new Exception("支付记录不存在！");
			}
		} catch (Exception e) {
			throw new Exception(e.toString());
		}
		return flag;
	}

	// 加密
	public Map<String, String> getPayMapEncode(KeyedCollection signDataput) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		try {
			String orig = (String) signDataput.getDataValue("orig"); // 获取原始数据
			String sign = (String) signDataput.getDataValue("sign"); // 获取签名数据
			orig = PayclientInterfaceUtil.Base64Encode(orig, "GBK"); // 原始数据先做Base64Encode转码
			sign = PayclientInterfaceUtil.Base64Encode(sign, "GBK"); // 签名数据先做Base64Encode转码
			orig = java.net.URLEncoder.encode(orig, "GBK"); // Base64Encode转码后原始数据,再做URL转码
			sign = java.net.URLEncoder.encode(sign, "GBK"); // Base64Encode转码后签名数据,再做URL转码

			map.put("orig", orig);
			map.put("sign", sign);
		} catch (CsiiException e) {
			e.printStackTrace();
		}
		return map;
	}

	// 解密
	public Map<String, String> getPayMapDecode(String strResult) throws Exception {
		Map<String, String> map = new HashMap<String, String>();

		if (strResult != null && !"".equals(strResult)) {
			String orig = (strResult.split("SDBPAYGATE=")[0]).split("=")[2];
			String sign = (strResult.split("orig=")[0]).split("=")[1];

			orig = URLDecoder.decode(orig, "GBK");
			sign = URLDecoder.decode(sign, "GBK");

			orig = PayclientInterfaceUtil.Base64Decode(orig, "GBK");
			sign = PayclientInterfaceUtil.Base64Decode(sign, "GBK");

			map.put("orig", orig);
			map.put("sign", sign);
		}

		return map;
	}

	// 调用支付接口上送字段(非绑定支付)
	private KeyedCollection getPayInputOrig(WalletData walletData) {
		KeyedCollection inputOrig = new KeyedCollection("inputOrig");
		if (walletData != null) {
			inputOrig.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
			inputOrig.put("orderId", walletData.getPaymentNo()); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
			inputOrig.put("currency", "RMB"); // 币种，目前只支持RMB
			inputOrig.put("amount", walletData.getTotalFee().doubleValue()); // 订单金额，12整数，2小数
			inputOrig.put("objectName", walletData.getSubject().substring(17)); // 订单款项描述（商户自定）
			inputOrig.put("paydate", CalendarUtil.toYYYYMMDDHHmmss(CalendarUtil.now())); // 下单时间，YYYYMMDDHHMMSS
			inputOrig.put("validtime", 0); // 订单有效期(秒)，0不生效
			inputOrig.put("remark", walletData.getBody().substring(17)); // 备注字段（商户自定）
		}
		return inputOrig;
	}

	// 非协议支付 orig 和 sign
	public Map<String, String> noBindPay(WalletData walletData) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			KeyedCollection signDataput = new KeyedCollection("signDataput");
			PayclientInterfaceUtil util = new PayclientInterfaceUtil(); // 建立客户端实例
			KeyedCollection input = this.getPayInputOrig(walletData);
			signDataput = util.getSignData(input);
			map = this.getPayMapEncode(signDataput);

			walletKhsdService.callBeforeRecord(walletData.getPaymentNo(), "KHPAYMENT", input);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	// 绑定并支付上送字段（不能和非绑定支付写在一起，否则会出现解包错误）
	private KeyedCollection getPayInputOrig(WalletData walletData, String plantBankId) {
		KeyedCollection inputOrig = new KeyedCollection("inputOrig");
		if (walletData != null) {
			inputOrig.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
			inputOrig.put("orderId", walletData.getPaymentNo()); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
			inputOrig.put("currency", "RMB"); // 币种，目前只支持RMB
			inputOrig.put("amount", walletData.getTotalFee()); // 订单金额，12整数，2小数
			inputOrig.put("paydate", CalendarUtil.toYYYYMMDDHHmmss(CalendarUtil.now())); // 下单时间，YYYYMMDDHHMMSS
			inputOrig.put("objectName", walletData.getBody()); // 订单款项描述（商户自定）
			inputOrig.put("validtime", "0"); // 订单有效期(秒)，0不生效
			inputOrig.put("remark", walletData.getSubject()); // 备注字段（商户自定）
			inputOrig.put("customerId", walletData.getBuyerId()); // 用户ID（平台注册用户ID）
			inputOrig.put("plantBankId", plantBankId); // 绑定并支付银行编码
		}
		return inputOrig;
	}

	// 绑定并支付 orig 和 sign
	public Map<String, String> bindAndPay(WalletData walletData, String plantBankId) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			KeyedCollection signDataput = new KeyedCollection("signDataput");
			PayclientInterfaceUtil util = new PayclientInterfaceUtil();
			KeyedCollection input = this.getPayInputOrig(walletData, plantBankId);
			signDataput = util.getSignData(input);
			map = this.getPayMapEncode(signDataput);

			walletKhsdService.callBeforeRecord(walletData.getPaymentNo(), "KHPAYMENT", input);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	// 绑定卡支付上送字段（不能和非绑定支付写在一起，否则会出现解包错误？）
	private KeyedCollection getPayInputOrig(WalletData walletData, String bindId, String verifyCode, String dataTime,
			String notifyurl) {
		KeyedCollection input = new KeyedCollection("input");
		if (walletData != null) {
			input.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
			input.put("orderId", walletData.getPaymentNo()); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
			input.put("currency", "RMB"); // 币种，目前只支持RMB
			input.put("amount", walletData.getTotalFee()); // 订单金额，12整数，2小数
			input.put("paydate", dataTime); // 下单时间，YYYYMMDDHHMMSS
			input.put("objectName", walletData.getBody()); // 订单款项描述（商户自定）
			input.put("validtime", 0); // 订单有效期(毫秒)，0不生效
			input.put("remark", walletData.getSubject()); // 备注字段（商户自定）
			input.put("customerId", walletData.getBuyerId()); // 用户ID（平台注册用户ID）
			input.put("bindId", bindId);
			input.put("verifyCode", verifyCode);
			input.put("dateTime", dataTime);
			input.put("NOTIFYURL", notifyurl);
		}
		return input;
	}

	// 绑定快捷支付接口调用，要看接口文档
	public PayKhsdCode bindFastPay(WalletData walletData, String bindId, String verifyCode, String dataTime,
			String notifyurl) throws Exception {
		if (PayStatusCode.WAIT_PAYMENT != walletData.getPaymentStatus())
			throw new Exception("该订单已支付过！");

		PayclientInterfaceUtil util = new PayclientInterfaceUtil();
		// 组装
		KeyedCollection input = this.getPayInputOrig(walletData, bindId, verifyCode, dataTime, notifyurl);
		// 签名
		KeyedCollection signDataput = util.getSignData(input);
		// 加密
		Map<String, String> map = this.getPayMapEncode(signDataput);
		// 上送
		walletKhsdService.callBeforeRecord(walletData.getPaymentNo(), "API007", input);
		KeyedCollection output = this.sendData(map, util, Constants.BINDFASTPAY_URL);
		walletKhsdService.callBehindRecord(walletData.getPaymentNo(), "API007", output);

		String bankCardNo4 = (String) output.getDataValue("accountNo");// 返回的银行卡号后四位
		String plantBankName = (String) output.getDataValue("plantBankName");// 返回的支付银行名称
		this.updateUsedBankCard(walletData.getId(), bankCardNo4, plantBankName);// 记下支付使用的银行卡信息

		String status = (String) output.getDataValue("status");
		// status订单状态，“00”，支付处理中，“01”，支付成功，“02”，支付失败
		if ("01".equals(status)) {
			// 改khsdStatus状态为支付成功
			this.qingfenOrSurety(walletData);
			return PayKhsdCode.success;
		} else if ("00".equals(status)) {
			// 改khsdStatus状态为支付处理中...
			this.updateWalletStatus(walletData.getId(), null, null, null, null, null, YesNoCode.yes, YesNoCode.yes);
			return PayKhsdCode.paying;
		} else {
			// 改khsdStatus状态为支付失败
			this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_CLOSED, null, null, null, null,
					YesNoCode.yes, YesNoCode.no);
			return PayKhsdCode.failure;
		}
	}

	private void qingfenOrSurety(WalletData walletData) throws Exception {
		if (PayStatusCode.WAIT_PAYMENT != walletData.getPaymentStatus())
			return;

		boolean flag = false;
		// 处理订单
		switch (walletData.getFeeItem()) {
		case inaccount:
			// 更改订单状态
			this.updateWalletStatus(walletData.getId(), PayStatusCode.TRADE_FINISHED, null, Calendar.getInstance(),
					Calendar.getInstance(), null, YesNoCode.yes, YesNoCode.no);

			flag = this.api6056(walletData);

			if (!flag) {
				throw new Exception("跨行收单支付成功，见证宝清分失败，需要后台处理！");
			} else {
				// 钱包帐务添加充值记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.fill,
						walletData.getTotalFee());
			}

			break;

		case face:
			this.updateWalletStatus(walletData.getId(), PayStatusCode.WAIT_CONFIRM, null, Calendar.getInstance(),
					Calendar.getInstance(), null, YesNoCode.yes, YesNoCode.no);
			// 清分加冻结
			flag = this.api6034(walletData, "8", "", "");

			if (!flag) {
				throw new Exception("跨行收单支付成功，见证宝清分失败，需要后台处理！");
			} else {
				// 钱包帐务添加充值记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.fill,
						walletData.getTotalFee());
				// 钱包帐务添加资金托管记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.pay,
						(-1) * walletData.getTotalFee());
			}

			break;

		case prefee:
			// 更新支付订单状态
			this.updateWalletStatus(walletData.getId(), PayStatusCode.WAIT_CONFIRM, null, Calendar.getInstance(),
					Calendar.getInstance(), null, YesNoCode.yes, YesNoCode.no);

			// 再调用支付接口
			if (this.isThreeOrder(walletData)) {
				// 清分加冻结
				flag = this.api6007(walletData, "3", "", "");
			} else {
				// 清分加冻结
				flag = this.api6034(walletData, "8", "", "");
			}

			if (!flag) {
				throw new Exception("跨行收单支付成功，见证宝冻结失败，需要后台处理！");
			} else {
				// 钱包帐务添加充值记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.fill,
						walletData.getTotalFee());
				// 钱包帐务添加资金托管记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.pay,
						(-1) * walletData.getTotalFee());
			}

			break;
		default:
			break;
		}
	}

	// 获取绑定支付短信验证码上送字段
	private KeyedCollection getValidCodeInput(WalletData walletData, String bindId) {
		KeyedCollection input = new KeyedCollection("input");
		if (walletData != null) {
			input.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
			input.put("customerId", walletData.getBuyerId());
			input.put("bindId", bindId); // 用户ID（平台注册用户ID）
			input.put("amount", walletData.getTotalFee());
			input.put("orderId", walletData.getPaymentNo());
		}
		return input;
	}

	// API006获取短信验证码时间
	public String getValidCode(WalletData walletData, String bindId) {
		try {
			PayclientInterfaceUtil util = new PayclientInterfaceUtil(); // 建立客户端实例

			// 组装
			KeyedCollection input = this.getValidCodeInput(walletData, bindId);
			// 签名
			KeyedCollection signDataput = util.getSignData(input);
			// 加密
			Map<String, String> map = this.getPayMapEncode(signDataput);
			// 上送
			walletKhsdService.callBeforeRecord(walletData.getPaymentNo(), "API006", input);
			KeyedCollection output = this.sendData(map, util, Constants.SENDMESSAGE_URL);
			walletKhsdService.callBehindRecord(walletData.getPaymentNo(), "API006", output);

			String errorCode = (String) output.getDataValue("errorCode");
			String errorMsg = (String) output.getDataValue("errorMsg");

			if ((errorCode == null || errorCode.equals("")) && (errorMsg == null || errorMsg.equals("")))
				return (String) output.getDataValue("dateTime");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	// 获取绑定银行卡列表上送字段
	private KeyedCollection getBindCardInput(Long customerId) {
		KeyedCollection input = new KeyedCollection("input");
		input.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
		input.put("customerId", customerId.toString()); // 用户ID（平台注册用户ID）
		return input;
	}

	// 取得用户绑定的银行卡列表
	public List<UserPinganBankData> getBindCards(WalletData walletData) {
		List<UserPinganBankData> userBankDatas = new ArrayList<UserPinganBankData>();
		try {
			PayclientInterfaceUtil util = new PayclientInterfaceUtil(); // 建立客户端实例

			// 组装
			KeyedCollection input = this.getBindCardInput(walletData.getBuyerId());
			// 签名
			KeyedCollection signDataput = util.getSignData(input);
			// 加密
			Map<String, String> map = this.getPayMapEncode(signDataput);
			// 上送
			walletKhsdService.callBeforeRecord(walletData.getPaymentNo().toString(), "API004", input);
			KeyedCollection output = this.sendData(map, util, Constants.BANKLIST_URL);
			walletKhsdService.callBehindRecord(walletData.getPaymentNo().toString(), "API004", output);

			String errorCode = (String) output.getDataValue("errorCode");
			String errorMsg = (String) output.getDataValue("errorMsg");

			if ((errorCode == null || errorCode.equals("")) && (errorMsg == null || errorMsg.equals(""))) {
				IndexedCollection bindCards = (IndexedCollection) output.getDataElement("iAccInfo");
				for (int i = 0; i < bindCards.size(); i++) {
					UserPinganBankData bankData = new UserPinganBankData();
					KeyedCollection bindCard = (KeyedCollection) bindCards.getElementAt(i);

					bankData.setBindId((String) bindCard.getDataValue("bindId"));
					bankData.setBankName((String) bindCard.getDataValue("plantBankName"));
					bankData.setCardNo((String) bindCard.getDataValue("accountNo"));
					bankData.setTelephone((String) bindCard.getDataValue("telephone"));

					bankData.setBankCode(BankCode2.getByName((String) bindCard.getDataValue("plantBankName")));

					userBankDatas.add(bankData);
				}
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return userBankDatas;
	}

	// 解析银行返回的银行卡列表信息
	private KeyedCollection sendData(Map<String, String> map, PayclientInterfaceUtil util, String url)
			throws Exception {
		KeyedCollection output = new KeyedCollection("output");

		Map<String, String[]> sPara = new HashMap<String, String[]>();
		sPara.put("orig", new String[] { map.get("orig") });
		sPara.put("sign", new String[] { map.get("sign") });
		String PaSignPayData = StringUtil.mapArrayToString(sPara);

		byte[] nameValuePair = PaSignPayData.getBytes("UTF-8");

		// 上送返回结果
		String strResult = new String(PapayHttpProtocolHandler.sendData(nameValuePair, url, null, null), "UTF-8");

		// 结果解密
		Map<String, String> mapResult = this.getPayMapDecode(strResult);

		if (mapResult.containsKey("sign") && mapResult.containsKey("orig")) {
			// 结果校验
			boolean result = util.verifyData(mapResult.get("sign"), mapResult.get("orig"));
			if (result) {
				// 结果组装
				output = util.parseOrigData(mapResult.get("orig"));
			} else {
				throw new Exception("返回结果数据校验失败");
			}
		}

		return output;
	}

	// 解除绑定银行卡上送字段
	private KeyedCollection getRemoveInput(Long customerId, String bindId) {
		KeyedCollection input = new KeyedCollection("input");

		input.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
		input.put("customerId", customerId);
		input.put("bindId", bindId); // 用户ID（平台注册用户ID）
		return input;
	}

	// 解除已绑定的银行卡
	public boolean removeBindCard(Long customerId, String bindId) {
		try {
			PayclientInterfaceUtil util = new PayclientInterfaceUtil(); // 建立客户端实例

			// 组装
			KeyedCollection input = this.getRemoveInput(customerId, bindId);
			// 签名
			KeyedCollection signDataput = util.getSignData(input);
			// 加密
			Map<String, String> map = this.getPayMapEncode(signDataput);
			// 上送
			walletKhsdService.callBeforeRecord(bindId, "API005", input);
			KeyedCollection output = this.sendData(map, util, Constants.REMOVEBIND_URL);
			walletKhsdService.callBehindRecord(bindId, "API005", output);

			String errorCode = (String) output.getDataValue("errorCode");
			String errorMsg = (String) output.getDataValue("errorMsg");

			if ((errorCode == null || errorCode.equals("")) && (errorMsg == null || errorMsg.equals(""))) {
				String status = (String) output.getDataValue("status");
				if ("01".equals(status))
					return true;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return false;
	}

	// 跨行退款input
	private KeyedCollection getRefundInput(WalletData walletData, String notifyurl) {
		KeyedCollection input = new KeyedCollection("input");
		if (walletData != null) {
			input.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
			input.put("refundNo", paySequenceService.getPaymentNo()); // 退款订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
			input.put("orderId", walletData.getPaymentNo()); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
			input.put("currency", "RMB"); // 币种，目前只支持RMB
			input.put("refundAmt", walletData.getTotalFee()); // 订单金额，12整数，2小数
			input.put("objectName", ""); // 退款原因（商户自定）
			input.put("remark", walletData.getSubject()); // 备注字段（商户自定）
			input.put("NOTIFYURL", notifyurl);
		}
		return input;
	}

	// 跨行收单支付退款
	public boolean khRefund(WalletData walletData, String notifyurl) throws Exception {
		try {
			PayclientInterfaceUtil util = new PayclientInterfaceUtil();

			// 组装
			KeyedCollection input = this.getRefundInput(walletData, notifyurl);
			// 签名
			KeyedCollection signDataput = util.getSignData(input);
			// 加密
			Map<String, String> map = this.getPayMapEncode(signDataput);
			// 上送
			walletKhsdService.callBeforeRecord(walletData.getPaymentNo(), "KH0005", input);// 记录跨行退款记录
			KeyedCollection output = this.sendData(map, util, Constants.KHREFUND_URL);
			walletKhsdService.callBehindRecord(walletData.getPaymentNo(), "KH0005", output);// 更新跨行退款记录

			String status = (String) output.getDataValue("status");
			// 解除状态，“01”，解除成功，“02”，解除失败
			if ("01".equals(status)) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 单笔订单状态查询接口
	private KeyedCollection getKhOrderStatusInput(WalletData walletData) {
		KeyedCollection input = new KeyedCollection("input");
		if (walletData != null) {
			input.put("masterId", Constants.CUSTOMER_NUM); // 商户号，注意生产环境上要替换成商户自己的生产商户号
			input.put("orderId", walletData.getPaymentNo()); // 订单号，严格遵守格式：商户号+8位日期YYYYMMDD+8位流水
		}
		return input;
	}

	// KH0001 跨行收单单笔订单查询
	public boolean KhOrderStatusQuery(WalletData walletData) throws Exception {
		try {
			PayclientInterfaceUtil util = new PayclientInterfaceUtil();
			// 组装
			KeyedCollection input = this.getKhOrderStatusInput(walletData);
			// 签名
			KeyedCollection signDataput = util.getSignData(input);
			// 加密
			Map<String, String> map = this.getPayMapEncode(signDataput);
			// 上送
			walletKhsdService.callBeforeRecord(walletData.getPaymentNo(), "KH0001", input);
			KeyedCollection output = this.sendData(map, util, Constants.KHQUERY_URL);
			walletKhsdService.callBehindRecord(walletData.getPaymentNo(), "KH0001", output);

			// 订单本金清算标志：‘1’已清算，‘0’待清算
			String settleflg = (String) output.getDataValue("settleflg");
			if ("1".equals(settleflg)) {
				this.qingfenOrSurety(walletData);
				return true;
			} else {
				/*
				 * // 改khsdStatus状态为支付失败
				 * this.updateWalletStatus(walletData.getId(),
				 * PayStatusCode.TRADE_CLOSED, null, null, null, null,
				 * YesNoCode.yes, YesNoCode.no); // 更新合同状态 if
				 * (walletData.getFeeItem() == FeeItemCode.prefee)
				 * this.updateOrderStatus(walletData.getBuyerData(),
				 * walletData.getOrderId(), OrderStatusCode.refund,
				 * Calendar.getInstance());
				 */

				return false;
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			return false;
		}
	}

	// 使用钱包支付
	public boolean walletPay(WalletData walletData, String serialNo, String messageCode) throws Exception {
		if (PayStatusCode.WAIT_PAYMENT != walletData.getPaymentStatus())
			throw new Exception("该订单已支付过！");
		if (SettleStyleCode.fill == walletData.getSettleStyle())
			throw new Exception("不能使用钱包充值！");

		boolean result = false;
		if (this.isThreeOrder(walletData)) {
			// 付款方付钱到收款方 （如果为合同支付且船东不是船代）
			result = this.api6007(walletData, "1", serialNo, messageCode);
		} else {
			result = this.api6034(walletData, "1", serialNo, messageCode);
		}

		if (result) {
			// 处理订单
			switch (walletData.getFeeItem()) {
			case face:
				// 更新支付订单状态
				this.updateWalletStatus(walletData.getId(), PayStatusCode.WAIT_CONFIRM, null, Calendar.getInstance(),
						Calendar.getInstance(), null, YesNoCode.no, null);
				// 钱包帐务添加资金托管记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.pay,
						(-1) * walletData.getTotalFee());
				break;

			case prefee:
				// 更新支付订单状态
				this.updateWalletStatus(walletData.getId(), PayStatusCode.WAIT_CONFIRM, null, Calendar.getInstance(),
						Calendar.getInstance(), null, YesNoCode.no, null);

				// 钱包帐务添加资金托管记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.pay,
						(-1) * walletData.getTotalFee());
				break;

			case bonus:
				// 更新支付订单状态
				this.updateWalletStatus(walletData.getId(), PayStatusCode.WAIT_CONFIRM, null, Calendar.getInstance(),
						Calendar.getInstance(), null, YesNoCode.no, null);
				// 钱包帐务添加资金托管记录
				walletSettleService.saveWalletSettle(walletData.getBuyerId(), walletData.getId(), WalletSettleCode.pay,
						(-1) * walletData.getTotalFee());
				break;
			default:
				break;
			}
		}

		walletKhsdService.deleteByPaymentNo(walletData.getPaymentNo());

		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean sendMessage2Bank(HashMap parmaKeyDict, String paymentNo) throws Exception {

		/* 获取请求报文 */
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(paymentNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */

		retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);
		walletZjjzService.saveWalletZjjz(paymentNo, parmaKeyDict, retKeyDict);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if ("000000".equals(rspCode)) {
			return true;
		} else {
			throw new Exception(rspMsg);
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map sendMessage2BankRespMap(HashMap parmaKeyDict, String paymentNo) throws Exception {

		/* 获取请求报文 */
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(paymentNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */

		retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);
		walletZjjzService.saveWalletZjjz(paymentNo, parmaKeyDict, retKeyDict);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if ("000000".equals(rspCode)) {
			return retKeyDict;
		} else {
			throw new Exception(rspMsg);
		}
	}

	/**
	 * @param walletData
	 *            订单
	 * @param funcFlag
	 *            1：下单预支付 （付款方→担保） 2：确认并付款（担保→收款方） 3：退款（担保→付款方）
	 *            4：支付到平台（担保→平台，平台退回到银行卡） 6：直接支付（会员A→会员B） 7：支付到平台（会员→平台）
	 *            8：清分支付（清分→会员→担保） 9：直接支付T+0（会员A→会员B）
	 * @return
	 * @throws Exception
	 */
	// pinganpay会员交易，没用上
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6006(WalletData walletData, String funcFlag, AccountData payAccountData,
			AccountData rcvAccountData) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		// 付款方
		if (walletData != null) {
			if (payAccountData != null && rcvAccountData != null) {
				/** 第一部分：生成发送银行的请求的报文的实例 */
				/* 报文参数赋值 */
				parmaKeyDict.put("TranFunc", "6006"); // 交易码
				parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
				parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
				parmaKeyDict.put("FuncFlag", funcFlag); // 功能编码

				parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
				// 付款方
				parmaKeyDict.put("OutCustAcctId", payAccountData.getAccountNo()); // 转出子账户
				parmaKeyDict.put("OutThirdCustId", Long.toString(payAccountData.getUserId())); // 转出会员代码
				parmaKeyDict.put("OutCustName", payAccountData.getAccounter()); // 转出子账户名称
				// 收款方
				parmaKeyDict.put("InCustAcctId", rcvAccountData.getAccountNo()); // 转入子账户
				parmaKeyDict.put("InThirdCustId", Long.toString(rcvAccountData.getUserId())); // 转入会员代码
				parmaKeyDict.put("InCustName", rcvAccountData.getAccounter()); // 转入子账户名称
				// 支付信息
				parmaKeyDict.put("TranAmount", Double.toString(walletData.getTotalFee())); // 交易金额
				parmaKeyDict.put("TranFee", "0.00"); // 交易费用
				parmaKeyDict.put("TranType", "01"); // 普通交易
				parmaKeyDict.put("CcyCode", "RMB"); // 默认：RMB
				parmaKeyDict.put("ThirdHtId", walletData.getPaymentNo()); // 订单id
				parmaKeyDict.put("ThirdHtMsg", walletData.getSubject()); // 订单内容
			}
		}
		return this.sendMessage2Bank(parmaKeyDict, walletData.getPaymentNo());
	}

	/**
	 * 
	 * @param walletData
	 * @param funcFlag
	 *            1：冻结（会员→担保） 2：解冻（担保→会员） 3：清分+冻结
	 * @param messageCode
	 * @param serialNo
	 * @return
	 * @throws Exception
	 */
	// 冻结API6007
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6007(WalletData walletData, String funcFlag, String serialNo, String messageCode)
			throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		if (walletData != null) {
			/** 第一部分：生成发送银行的请求的报文的实例 */
			/* 报文参数赋值 */
			parmaKeyDict.put("TranFunc", "6007"); // 交易码
			parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
			parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
			parmaKeyDict.put("FuncFlag", funcFlag); // 请求流水号

			parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
			parmaKeyDict.put("CustAcctId", walletData.getSndCardNo()); // 账户号
			parmaKeyDict.put("ThirdCustId", Long.toString(walletData.getBuyerId())); // 会员代码
			parmaKeyDict.put("TranAmount", Double.toString(walletData.getTotalFee())); // 交易金额

			parmaKeyDict.put("HandFee", Double.toString(walletData.getServiceFee())); // 平台手续费

			parmaKeyDict.put("CcyCode", "RMB"); // 默认：RMB
			parmaKeyDict.put("ThirdHtId", walletData.getPaymentNo()); // 订单编号
			parmaKeyDict.put("ThirdHtMsg", walletData.getSubject()); // 订单描述
			parmaKeyDict.put("Note", messageCode); // 短信验证码
			parmaKeyDict.put("Reserve", serialNo); // 手机指令码
		}
		return this.sendMessage2Bank(parmaKeyDict, walletData.getPaymentNo());
	}

	// 登记挂账API6008，没用上
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6008(WalletData walletData, AccountData accountData) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		if (walletData != null) {
			/** 第一部分：生成发送银行的请求的报文的实例 */
			/* 报文参数赋值 */
			parmaKeyDict.put("TranFunc", "6008"); // 交易码
			parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
			parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号

			parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
			parmaKeyDict.put("CustAcctId", accountData.getAccountNo()); // 转入子账户
			parmaKeyDict.put("ThirdCustId", Long.toString(accountData.getUserId())); // 转入会员代码
			parmaKeyDict.put("TranAmount", Double.toString(walletData.getTotalFee())); // 交易金额
			parmaKeyDict.put("CcyCode", "RMB"); // 默认：RMB
		}
		return this.sendMessage2Bank(parmaKeyDict, walletData.getPaymentNo());
	}

	// 账户余额清单查询【API6010】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<CustAcctData> api6010(String custAcctId, String selectFlag, String pageNum) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		String logNo = paySequenceService.getLogNo();
		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6010"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
		parmaKeyDict.put("CustAcctId", custAcctId); // 子账户账号
		parmaKeyDict.put("SelectFlag", selectFlag); // 1：全部 2：普通会员子账号 3：功能子账号
		parmaKeyDict.put("PageNum", pageNum); // 页号
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if (retKeyDict.isEmpty()) {
			rspCode = "EYD001";
			rspMsg = "错误！见证宝客户端连接失败！";
			retKeyDict.put("RspCode", rspCode);
			retKeyDict.put("RspMsg", rspMsg);
		}

		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		if ("000000".equals(rspCode)) {

			Integer TotalCount = Integer.parseInt((String) retKeyDict.get("TotalCount"));// 总记录数
			Integer BeginNum = Integer.parseInt((String) retKeyDict.get("BeginNum"));// 起始记录号
			String LastPage = (String) retKeyDict.get("LastPage");// 是否结束包
			Integer RecordNum = Integer.parseInt((String) retKeyDict.get("RecordNum"));// 子账户个数
			String Reserve = (String) retKeyDict.get("Reserve");// 保留域

			logger.info("第 " + pageNum + " 页，账户余额清单查询【API6010】########################################");
			logger.info("总记录数:" + TotalCount.toString() + ",起始记录号:" + BeginNum.toString() + ",是否结束包:" + LastPage
					+ ",子账户个数:" + RecordNum.toString() + ",保留域:" + Reserve);
			logger.info("########################################");

			List<CustAcctData> datas = new ArrayList<CustAcctData>();

			String ArrayContent = (String) retKeyDict.get("ArrayContent");// 数组记录
			logger.info("数组记录:" + ArrayContent);
			String[] arr = ArrayContent.split("\\&");

			// logger.info("数组长度:" + arr.length);

			int recordLen = 7;
			List<String> lst = Arrays.asList(arr);
			// logger.info("列表长度:" + lst.size());

			for (int i = 0; i < RecordNum; i++) {
				CustAcctData data = new CustAcctData(lst.subList(i * recordLen,
						((i + 1) * recordLen < lst.size() ? ((i + 1) * recordLen) : lst.size())));

				logger.info(data.toString());

				datas.add(data);
			}

			if ("1".equals(LastPage))
				if (!"3".equals(selectFlag))
					datas.add(new CustAcctData());

			return datas;
		} else {
			logger.error(custAcctId + rspMsg);
			throw new Exception(custAcctId + rspMsg);
		}
	}

	// 查询银行提现退单信息【API6048】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<BackMoneyData> api6048(String beginDate, String endDate) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		String logNo = paySequenceService.getLogNo();
		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6048"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号

		parmaKeyDict.put("FuncFlag", "1"); // 功能标志
		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
		parmaKeyDict.put("BeginDate", beginDate); // 开始日期
		parmaKeyDict.put("EndDate", endDate); // 结束日期
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if (retKeyDict.isEmpty()) {
			rspCode = "EYD002";
			rspMsg = "错误！查询银行提现退单信息失败！";
			retKeyDict.put("RspCode", rspCode);
			retKeyDict.put("RspMsg", rspMsg);
		}

		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		if ("000000".equals(rspCode)) {

			Integer RecordNum = Integer.parseInt((String) retKeyDict.get("TotalCount"));// 总记录数

			logger.info("查询银行提现退单信息【API6048】########################################");
			logger.info(beginDate + " - " + endDate);
			logger.info("银行提现退单总记录数:" + RecordNum.toString());
			logger.info("########################################");

			List<BackMoneyData> datas = new ArrayList<BackMoneyData>();

			String ArrayContent = (String) retKeyDict.get("ArrayContent");// 数组记录
			String[] arr = ArrayContent.split("\\&");

			logger.info("数组长度:" + arr.length);

			int recordLen = 5;
			List<String> lst = Arrays.asList(arr);
			logger.info("列表长度:" + lst.size());

			for (int i = 0; i < RecordNum; i++) {
				BackMoneyData data = new BackMoneyData(lst.subList(i * recordLen,
						((i + 1) * recordLen < lst.size() ? ((i + 1) * recordLen) : lst.size())));

				logger.info(data.toString());

				datas.add(data);
			}

			return datas;
		} else {
			throw new Exception(rspMsg);
		}
	}

	// 查询资金汇总账户余额【API6011】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map<String, Double> api6011() throws Exception {
		String logNo = paySequenceService.getLogNo();
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6011"); // 交易码
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号

		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if (retKeyDict.isEmpty()) {
			rspCode = "EYD003";
			rspMsg = "错误！查询资金汇总账户余额失败！";
			retKeyDict.put("RspCode", rspCode);
			retKeyDict.put("RspMsg", rspMsg);
		}
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		if ("000000".equals(rspCode)) {
			Map<String, Double> map = new HashMap<String, Double>();

			Double dLastBalance = Double.parseDouble((String) retKeyDict.get("LastBalance"));
			Double dCurBalance = Double.parseDouble((String) retKeyDict.get("CurBalance"));

			map.put("dLastBalance", dLastBalance);
			map.put("dCurBalance", dCurBalance);

			return map;
		} else {
			throw new Exception(rspMsg);
		}
	}

	// 单笔订单查询【API6014】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ZjjzResultData api6014(WalletZjjzData walletZjjz) throws Exception {
		String logNo = paySequenceService.getLogNo();
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6014"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号

		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
		parmaKeyDict.put("FuncFlag", "1"); // 1：清分与提现，2：会员交易（包含登记挂账）
		parmaKeyDict.put("OrigThirdLogNo", walletZjjz.getLogNo()); // 交易网流水号
		parmaKeyDict.put("TranDate", walletZjjz.getLogNo().substring(0, 8)); // 交易日期
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if (retKeyDict.isEmpty()) {
			rspCode = "EYD002";
			rspMsg = "错误！单笔订单查询失败！";
			retKeyDict.put("RspCode", rspCode);
			retKeyDict.put("RspMsg", rspMsg);
		}
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		if ("000000".equals(rspCode)) {
			ZjjzResultData data = new ZjjzResultData();

			data.setTranFlag((String) retKeyDict.get("TranFlag"));
			data.setTranStatus((String) retKeyDict.get("TranStatus"));
			data.setTranAmount((String) retKeyDict.get("TranAmount"));
			data.setTranDate((String) retKeyDict.get("TranDate"));
			data.setTranTime((String) retKeyDict.get("TranTime"));
			data.setInCustAcctId((String) retKeyDict.get("InCustAcctId"));
			data.setOutCustAcctId((String) retKeyDict.get("OutCustAcctId"));
			data.setReserve((String) retKeyDict.get("Reserve"));

			return data;
		} else {
			throw new Exception(rspMsg);
		}
	}

	// 查询普通转账充值明细【API6050】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ZjjzFillData> api6050(String custAcctId, String beginDate, String endDate, String pageNum)
			throws Exception {
		String logNo = paySequenceService.getLogNo();
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6050"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号

		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 必输 资金汇总账号
		parmaKeyDict.put("FuncFlag", "1"); // 默认：1 当日
		// parmaKeyDict.put("CustAcctId", custAcctId); // 必输 子账号
		parmaKeyDict.put("BeginDate", beginDate); // 可选 若是历史查询，则必输，当日查询时，不起作用
		parmaKeyDict.put("EndDate", endDate); // 可选 若是历史查询，则必输，当日查询时，不起作用
		parmaKeyDict.put("PageNum", pageNum); // 必输 起始值为1，每页20条记录，按建立时间排正序
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述

		if ("000000".equals(rspCode)) {

			Integer TotalCount = Integer.parseInt((String) retKeyDict.get("TotalCount"));// 总记录数
			Integer BeginNum = Integer.parseInt((String) retKeyDict.get("BeginNum"));// 起始记录号
			String LastPage = (String) retKeyDict.get("LastPage");// 是否结束包 0：否
																	// 1：是
			Integer RecordNum = Integer.parseInt((String) retKeyDict.get("RecordNum"));// 本次返回流水笔数

			logger.info("第 " + pageNum + " 页，查询普通转账充值明细【API6050】########################################");
			logger.info("总记录数:" + TotalCount.toString() + ",起始记录号:" + BeginNum.toString() + ",是否结束包:" + LastPage
					+ ",流水笔数:" + RecordNum.toString());
			logger.info("########################################");

			List<ZjjzFillData> datas = new ArrayList<ZjjzFillData>();

			String ArrayContent = (String) retKeyDict.get("ArrayContent");// 数组记录
			logger.info("数组记录:" + ArrayContent);
			String[] arr = ArrayContent.split("\\&");

			// logger.info("数组长度:" + arr.length);

			int recordLen = 11;
			List<String> lst = Arrays.asList(arr);
			// logger.info("列表长度:" + lst.size());

			for (int i = 0; i < RecordNum; i++) {
				ZjjzFillData data = new ZjjzFillData(lst.subList(i * recordLen,
						((i + 1) * recordLen < lst.size() ? ((i + 1) * recordLen) : lst.size())));

				logger.info(data.toString());

				datas.add(data);
			}

			return datas;
		} else {
			throw new Exception(rspMsg);
		}
	}

	// 查询银行时间段内交易明细【API6072】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ZjjzPayData> api6072(String custAcctId, String beginDate, String endDate, String pageNum)
			throws Exception {
		String logNo = paySequenceService.getLogNo();
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6072"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号

		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 必输 资金汇总账号
		parmaKeyDict.put("FuncFlag", "2"); // 必输 1:当日，2：历史
		parmaKeyDict.put("CustAcctId", custAcctId); // 必输 子账号
		parmaKeyDict.put("SelectFlag", "1"); // 必输 1：全部 2：转出 3：转入
		parmaKeyDict.put("BeginDate", beginDate); // 可选 若是历史查询，则必输，当日查询时，不起作用
		parmaKeyDict.put("EndDate", endDate); // 可选 若是历史查询，则必输，当日查询时，不起作用
		parmaKeyDict.put("PageNum", pageNum); // 必输 起始值为1，每页20条记录，按建立时间排正序
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述

		if ("000000".equals(rspCode)) {

			Integer TotalCount = Integer.parseInt((String) retKeyDict.get("TotalCount"));// 总记录数
			Integer BeginNum = Integer.parseInt((String) retKeyDict.get("BeginNum"));// 起始记录号
			String LastPage = (String) retKeyDict.get("LastPage");// 是否结束包 0：否
																	// 1：是
			Integer RecordNum = Integer.parseInt((String) retKeyDict.get("RecordNum"));// 本次返回流水笔数

			logger.info("第 " + pageNum + " 页，查询银行时间段内交易明细【API6072】########################################");
			logger.info("总记录数:" + TotalCount.toString() + ",起始记录号:" + BeginNum.toString() + ",是否结束包:" + LastPage
					+ ",流水笔数:" + RecordNum.toString());
			logger.info("########################################");

			List<ZjjzPayData> datas = new ArrayList<ZjjzPayData>();

			String ArrayContent = (String) retKeyDict.get("ArrayContent");// 数组记录
			logger.info("数组记录:" + ArrayContent);
			String[] arr = ArrayContent.split("\\&");
			// logger.info("数组长度:" + arr.length);

			int recordLen = 10;
			List<String> lst = Arrays.asList(arr);
			// logger.info("列表长度:" + lst.size());

			for (int i = 0; i < RecordNum; i++) {
				ZjjzPayData data = new ZjjzPayData(lst.subList(i * recordLen,
						((i + 1) * recordLen < lst.size() ? ((i + 1) * recordLen) : lst.size())));

				logger.info(data.toString());

				datas.add(data);
			}

			return datas;
		} else {
			logger.error(custAcctId + rspMsg);
			throw new Exception(custAcctId + rspMsg);
		}
	}

	// 查询银行时间段内清分提现明细【API6073】
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public List<ZjjzFetchData> api6073(String custAcctId, String beginDate, String endDate, String pageNum)
			throws Exception {
		String logNo = paySequenceService.getLogNo();
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6073"); // 交易码，此处以【6000】接口为例子
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", logNo); // 请求流水号

		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 必输 资金汇总账号
		parmaKeyDict.put("FuncFlag", "2"); // 必输 1:当日，2：历史
		parmaKeyDict.put("CustAcctId", custAcctId); // 必输 子账号
		parmaKeyDict.put("SelectFlag", "1"); // 必输 1：全部 2：提现 3：清分
		parmaKeyDict.put("BeginDate", beginDate); // 可选 若是历史查询，则必输，当日查询时，不起作用
		parmaKeyDict.put("EndDate", endDate); // 可选 若是历史查询，则必输，当日查询时，不起作用
		parmaKeyDict.put("PageNum", pageNum); // 必输 起始值为1，每页20条记录，按建立时间排正序
		parmaKeyDict.put("Reserve", ""); // 保留域

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */
		if (recvMessage != null && !"".equals(recvMessage))
			retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);

		walletZjjzService.saveWalletZjjz(logNo, parmaKeyDict, retKeyDict);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述

		if ("000000".equals(rspCode)) {

			Integer TotalCount = Integer.parseInt((String) retKeyDict.get("TotalCount"));// 总记录数
			Integer BeginNum = Integer.parseInt((String) retKeyDict.get("BeginNum"));// 起始记录号
			String LastPage = (String) retKeyDict.get("LastPage");// 是否结束包 0：否
																	// 1：是
			Integer RecordNum = Integer.parseInt((String) retKeyDict.get("RecordNum"));// 本次返回流水笔数

			logger.info("第 " + pageNum + " 页，查询银行时间段内清分提现明细【API6073】########################################");
			logger.info("总记录数:" + TotalCount.toString() + ",起始记录号:" + BeginNum.toString() + ",是否结束包:" + LastPage
					+ ",流水笔数:" + RecordNum.toString());
			logger.info("########################################");

			List<ZjjzFetchData> datas = new ArrayList<ZjjzFetchData>();

			String ArrayContent = (String) retKeyDict.get("ArrayContent");// 数组记录
			logger.info("数组记录:" + ArrayContent);
			String[] arr = ArrayContent.split("\\&");

			// logger.info("数组长度:" + arr.length);

			int recordLen = 12;
			List<String> lst = Arrays.asList(arr);
			// logger.info("列表长度:" + lst.size());

			for (int i = 0; i < RecordNum; i++) {
				ZjjzFetchData data = new ZjjzFetchData(lst.subList(i * recordLen,
						((i + 1) * recordLen < lst.size() ? ((i + 1) * recordLen) : lst.size())));

				logger.info(data.toString());

				datas.add(data);
			}

			return datas;
		} else {
			logger.error(custAcctId + rspMsg);
			throw new Exception(custAcctId + rspMsg);
		}
	}

	// 非验密会员交易API6034
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6034(WalletData walletData, String funcFlag, String serialNo, String messageCode)
			throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		// 付款方
		if (walletData != null) {
			/** 第一部分：生成发送银行的请求的报文的实例 */
			/* 报文参数赋值 */
			parmaKeyDict.put("TranFunc", "6034"); // 交易码
			parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
			parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
			parmaKeyDict.put("FuncFlag", funcFlag); // 功能编码

			parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
			// 付款方
			parmaKeyDict.put("OutCustAcctId", walletData.getSndCardNo()); // 转出子账户
			parmaKeyDict.put("OutThirdCustId", Long.toString(walletData.getBuyerId())); // 转出会员代码
			parmaKeyDict.put("OutCustName", walletData.getSndAccountName()); // 转出子账户名称
			// 收款方
			parmaKeyDict.put("InCustAcctId", walletData.getRcvCardNo()); // 转入子账户
			parmaKeyDict.put("InThirdCustId", Long.toString(walletData.getSellerId())); // 转入会员代码
			parmaKeyDict.put("InCustName", walletData.getRcvAccountName()); // 转入子账户名称
			// 支付信息
			parmaKeyDict.put("TranAmount", Double.toString(walletData.getTotalFee())); // 交易金额
			parmaKeyDict.put("TranFee", Double.toString(walletData.getServiceFee())); // 交易费用

			parmaKeyDict.put("TranType", "01"); // 普通交易
			parmaKeyDict.put("CcyCode", "RMB"); // 默认：RMB
			parmaKeyDict.put("ThirdHtId", walletData.getPaymentNo()); // 订单id
			parmaKeyDict.put("ThirdHtMsg", walletData.getSubject()); // 订单内容
			parmaKeyDict.put("Note", messageCode); // 短信验证码
			parmaKeyDict.put("Reserve", serialNo); // 手机指令码
			parmaKeyDict.put("WebSign", ""); // 网银签名
		}
		return this.sendMessage2Bank(parmaKeyDict, walletData.getPaymentNo());
	}

	// 清分API6056
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6056(WalletData walletData) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		if (walletData != null) {
			/** 第一部分：生成发送银行的请求的报文的实例 */
			/* 报文参数赋值 */
			parmaKeyDict.put("TranFunc", "6056"); // 交易码
			parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
			parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号

			parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
			parmaKeyDict.put("CustAcctId", walletData.getSndCardNo()); // 转入子账户
			parmaKeyDict.put("ThirdCustId", Long.toString(walletData.getBuyerId())); // 会员代码
			parmaKeyDict.put("TranAmount", Double.toString(walletData.getTotalFee())); // 交易金额
			parmaKeyDict.put("CcyCode", "RMB"); // 默认：RMB
		}
		return this.sendMessage2Bank(parmaKeyDict, walletData.getPaymentNo());
	}

	// 会员资金支付 api6070
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6070(WalletData walletData) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		if (walletData != null) {
			/** 第一部分：生成发送银行的请求的报文的实例 */
			/* 报文参数赋值 */
			parmaKeyDict.put("TranFunc", "6070"); // 交易码
			parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
			parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号

			parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
			// 付款方
			parmaKeyDict.put("OutCustAcctId", walletData.getSndCardNo()); // 转出子账户
			parmaKeyDict.put("OutThirdCustId", Long.toString(walletData.getBuyerId())); // 转出会员代码
			parmaKeyDict.put("HandFee", Double.toString(walletData.getServiceFee())); // 平台手续费
			parmaKeyDict.put("CcyCode", "RMB"); // 默认：RMB
			parmaKeyDict.put("ThirdHtId", walletData.getPaymentNo()); // 订单号
			parmaKeyDict.put("TotalCount", "2"); // 订单号

			parmaKeyDict.put("InCustAcctId0", walletData.getRcvCardNo()); // 承运人账户
			parmaKeyDict.put("InThirdCustId0", Long.toString(walletData.getSellerId())); // 承运人账户
			parmaKeyDict.put("TranAmount0",
					Double.toString(walletData.getTotalFee() - walletData.getMiddleFee() - walletData.getServiceFee())); // 运输费

			parmaKeyDict.put("InCustAcctId1", walletData.getBrokerCardNo()); // 代理人账户
			parmaKeyDict.put("InThirdCustId1", Long.toString(walletData.getBrokerId())); // 代理人账户
			parmaKeyDict.put("TranAmount1", Double.toString(walletData.getMiddleFee())); // 代理费
			parmaKeyDict.put("Note", ""); // 备注
			parmaKeyDict.put("Reserve", ""); // 保留域
		} else {
			throw new Exception("收款账户不存在！");
		}
		return this.sendMessage2Bank(parmaKeyDict, walletData.getPaymentNo());
	}

	// 交易撤销 api6077，没用上
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public boolean api6077(String origThirdLogNo) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数

		/** 第一部分：生成发送银行的请求的报文的实例 */
		/* 报文参数赋值 */
		parmaKeyDict.put("TranFunc", "6077"); // 交易码
		parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
		parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号

		parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
		// 请求流水号
		parmaKeyDict.put("OrigThirdLogNo", origThirdLogNo); // 订单号
		return this.sendMessage2Bank(parmaKeyDict, "");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Map<String, Object> api6082(WalletData walletData, String tranType) throws Exception {
		/* 定义报文参数字典 */
		HashMap parmaKeyDict = new HashMap();// 用于存放生成向银行请求报文的参数
		HashMap retKeyDict = new HashMap();// 用于存放银行发送报文的参数

		/* 报文参数赋值 */
		if (walletData != null) {
			AccountData payAccountData = accountService.getAccountByUserId(walletData.getBuyerId(),
					PayStyleCode.pinganpay);
			if (payAccountData != null) {
				parmaKeyDict.put("TranFunc", "6082"); // 交易码，此处以【6000】接口为例子
				parmaKeyDict.put("Qydm", Constants.QYDM); // 企业代码
				parmaKeyDict.put("ThirdLogNo", paySequenceService.getLogNo()); // 请求流水号
				parmaKeyDict.put("SupAcctId", Constants.SUPACCTID); // 资金汇总账号
				parmaKeyDict.put("ThirdCustId", Long.toString(walletData.getBuyerId())); // 交易网会员代码
				parmaKeyDict.put("CustAcctId", payAccountData.getAccountNo()); // 子账户账号
				parmaKeyDict.put("TranType", tranType); // 交易类型 1：提现 2：支付
				parmaKeyDict.put("TranAmount", Double.toString(walletData.getTotalFee())); // 交易金额
				parmaKeyDict.put("AcctId", ""); // 银行卡号
				parmaKeyDict.put("ThirdHtId", walletData.getPaymentNo()); // 订单号
				parmaKeyDict.put("TranNote", ""); // 备注
				parmaKeyDict.put("Reserve", ""); // 保留域
			} else {
				throw new Exception("收款方钱包账户不存在！");
			}
		} else {
			throw new Exception("支付记录不存在！");
		}

		String tranMessage = zjjzApiService.getTranMessage(parmaKeyDict);// 调用函数生成报文

		/** 第二部分：获取银行返回的报文的实例 */
		/* 发送请求报文 */
		zjjzApiService.SendTranMessage(tranMessage, Constants.SERVERIPADDRESS, Constants.SERVERPORT, retKeyDict);

		/* 获取银行返回报文 */
		walletZjjzService.saveWalletZjjz(walletData.getPaymentNo(), parmaKeyDict, retKeyDict);
		String recvMessage = (String) retKeyDict.get("RecvMessage");// 银行返回的报文

		/** 第三部分：解析银行返回的报文的实例 */

		retKeyDict = zjjzApiService.parsingTranMessageString(recvMessage);
		walletZjjzService.saveWalletZjjz(walletData.getPaymentNo(), parmaKeyDict, retKeyDict);

		String rspCode = (String) retKeyDict.get("RspCode");// 银行返回的应答码
		String rspMsg = (String) retKeyDict.get("RspMsg");// 银行返回的应答描述
		if ("000000".equals(rspCode)) {
			return retKeyDict;
		} else {
			throw new Exception(rspMsg);
		}
	}

	public WalletData getWalletData(Long userId, Long walletId, Long orderId, FeeItemCode feeItem, Double payMoney,
			Integer suretyDays, String remark) throws Exception {
		if (walletId == null)
			walletId = 0L;

		WalletData walletData = walletBillService.getBillData(walletId);

		// 如果不存在支付记录，则建立
		if (walletData == null)
			walletData = this.createWalletData(userId, orderId, feeItem, payMoney, suretyDays, remark);

		return walletData;
	}

	private WalletData createWalletData(Long userId, Long orderId, FeeItemCode feeItem, Double payMoney,
			Integer suretyDays, String remark) throws Exception {
		Long walletId = 0L;

		switch (feeItem) {
		case inaccount:
			// 充值
			walletId = this.fillWalletData(userId, payMoney);
			break;
		case face:
			// 直接支付（orderId 为收款方id）
			walletId = this.faceWalletData(userId, orderId, payMoney, suretyDays, remark);
			break;
		case prefee:
			// 合同订单预付（orderId）
			walletId = this.orderWalletData(userId, orderId, payMoney, suretyDays, remark);
			break;
		case bonus:
			// 发红包（orderId 为收款方id）
			walletId = this.bonusWalletData(userId, orderId, payMoney, suretyDays, remark);
			break;
		default:
			throw new Exception("错误！未知的帐单费项。");
		}
		WalletData walletData = walletBillService.getBillData(walletId);

		return walletData;
	}

	// 充值生成订单
	private Long fillWalletData(Long userId, Double payMoney) {
		YydWallet wallet = new YydWallet();

		wallet.setSettleStyle(SettleStyleCode.fill);
		wallet.setPaymentNo(paySequenceService.getPaymentNo()); // 随机生成订单号
		wallet.setFeeItem(FeeItemCode.inaccount);
		wallet.setOrderId(0L);
		// 卖家
		wallet.setSellerId(userId);
		// 买家
		wallet.setBuyerId(userId);

		Calendar caleTime = CalendarUtil.now();
		String title = CalendarUtil.toYYYY_MM_DD_HH_MM(caleTime) + " 充值 " + payMoney + "元";
		wallet.setSubject(title);
		wallet.setBody(title);
		wallet.setTotalFee(payMoney);

		wallet.setPaymentStatus(PayStatusCode.WAIT_PAYMENT);
		wallet.setGmtPayment(caleTime);
		wallet.setSuretyDays(0);
		wallet.setGmtSurety(caleTime); // 开始资金托管时间
		wallet.setRefundStatus(ApplyReplyCode.noapply);
		wallet.setGmtRefund(caleTime); // 退款时间

		wallet = walletDao.save(wallet);

		return wallet.getId();
	}

	// 合同支付，在钱包中产生记录
	private Long orderWalletData(Long userId, Long orderId, Double payMoney, Integer suretyDays, String remark)
			throws Exception {
		OrderCommonData orderData = orderService.getMyOrderData(userId, orderId);

		if (orderData != null) {
			YydWallet wallet = new YydWallet();
			wallet.setSettleStyle(SettleStyleCode.pay);
			wallet.setPaymentNo(paySequenceService.getPaymentNo()); // 随机生成订单号
			wallet.setFeeItem(FeeItemCode.prefee);
			wallet.setOrderId(orderId);

			// 卖家
			wallet.setSellerId(orderData.getMasterId());
			// 代理
			wallet.setBrokerId(orderData.getBrokerId());
			// 买家
			wallet.setBuyerId(orderData.getOwnerId());

			Calendar caleTime = CalendarUtil.now();
			wallet.setSubject(remark);
			wallet.setBody(remark);

			wallet.setTotalFee(payMoney);// 合同支付金额
			if (!orderData.getBrokerId().equals(orderData.getMasterId())
					&& !orderData.getBrokerId().equals(orderData.getOwnerId()))
				wallet.setMiddleFee(NumberFormatUtil.format(payMoney * Constants.BROKER_RATE / 100)); // 代理人佣金
			wallet.setServiceFee(NumberFormatUtil.format(payMoney * Constants.PLAT_RATE / 100)); // 平台收取服务费

			wallet.setPaymentStatus(PayStatusCode.WAIT_PAYMENT);
			wallet.setGmtPayment(caleTime);
			wallet.setSuretyDays(suretyDays);
			wallet.setGmtSurety(caleTime); // 开始资金托管时间
			wallet.setRefundStatus(ApplyReplyCode.noapply);
			wallet.setGmtRefund(caleTime); // 退款时间

			wallet = walletDao.save(wallet);
			return wallet.getId();
		} else {
			throw new Exception("合同不存在！");
		}
	}

	// 直接付款生成订单
	private Long faceWalletData(Long senderId, Long receiverId, Double payMoney, Integer suretyDays, String remark) {

		YydWallet wallet = new YydWallet();

		wallet.setSettleStyle(SettleStyleCode.pay);
		wallet.setPaymentNo(paySequenceService.getPaymentNo()); // 随机生成订单号
		wallet.setFeeItem(FeeItemCode.face);
		wallet.setOrderId(0L);

		// 买家
		UserData sndUserData = userService.getById(senderId);
		wallet.setBuyerId(senderId);

		// 卖家
		UserData rcvUserData = userService.getById(receiverId);
		wallet.setSellerId(receiverId);

		Calendar caleTime = CalendarUtil.now();
		String title = CalendarUtil.toYYYY_MM_DD_HH_MM(caleTime) + " " + sndUserData.getTrueName() + " 直接付款 " + payMoney
				+ "元 给 " + rcvUserData.getTrueName();

		wallet.setSubject(title);
		if (!"".equals(remark.trim()))
			wallet.setBody(title + "。备注:" + remark);
		else
			wallet.setBody(title);
		wallet.setTotalFee(payMoney);
		wallet.setServiceFee(NumberFormatUtil.format(payMoney * Constants.PLAT_RATE / 100)); // 平台收取服务费

		wallet.setPaymentStatus(PayStatusCode.WAIT_PAYMENT);
		wallet.setGmtPayment(caleTime);
		wallet.setSuretyDays(suretyDays);
		wallet.setGmtSurety(caleTime); // 开始资金托管时间
		wallet.setRefundStatus(ApplyReplyCode.noapply);
		wallet.setGmtRefund(caleTime); // 退款时间

		wallet = walletDao.save(wallet);

		return wallet.getId();
	}

	// 直接付款生成订单
	public Long bonusWalletData(Long senderId, Long receiverId, Double payMoney, Integer suretyDays, String remark,
			Long... cabinIds) {

		YydWallet wallet = new YydWallet();

		wallet.setSettleStyle(SettleStyleCode.pay);
		wallet.setPaymentNo(paySequenceService.getPaymentNo()); // 随机生成订单号
		wallet.setFeeItem(FeeItemCode.bonus);
		wallet.setOrderId(0L);

		// 买家
		UserData sndUserData = userService.getById(senderId);
		wallet.setBuyerId(senderId);

		// 卖家
		UserData rcvUserData = userService.getById(receiverId);
		wallet.setSellerId(receiverId);

		// 后台针对船盘批量发红包时将船盘ID保存，以控制每人每船每天只收一个红包
		if (cabinIds != null && cabinIds.length > 0)
			wallet.setOrderId(cabinIds[0]);

		Calendar caleTime = CalendarUtil.now();
		String title = CalendarUtil.toYYYY_MM_DD_HH_MM(caleTime) + " " + sndUserData.getTrueName() + " 发红包 " + payMoney
				+ "元 给 " + rcvUserData.getTrueName();

		wallet.setSubject(title);
		if (!"".equals(remark.trim()))
			wallet.setBody(title + "。备注:" + remark);
		else
			wallet.setBody(title);
		wallet.setTotalFee(payMoney);
		wallet.setServiceFee(0.00D); // 平台收取服务费

		wallet.setPaymentStatus(PayStatusCode.WAIT_PAYMENT);
		wallet.setGmtPayment(caleTime);
		wallet.setSuretyDays(suretyDays);
		wallet.setGmtSurety(caleTime); // 开始资金托管时间
		wallet.setRefundStatus(ApplyReplyCode.noapply);
		wallet.setGmtRefund(caleTime); // 退款时间

		wallet = walletDao.save(wallet);

		return wallet.getId();
	}

	private boolean isThreeOrder(WalletData walletData) throws Exception {
		if (FeeItemCode.prefee == walletData.getFeeItem()) {
			OrderCommonData orderData = orderService.getOrderData(walletData.getOrderId());
			if (orderData != null) {
				if (!orderData.getOwnerId().equals(orderData.getBrokerId())
						&& !orderData.getMasterId().equals(orderData.getBrokerId())) {
					return true;
				}
			}
		}
		return false;
	}

	public Map<String, String> getSerialNo(WalletData walletData, String tranType) {
		Map<String, String> map = new HashMap<String, String>();
		try {
			Map<String, Object> result = this.api6082(walletData, tranType);

			String msgBody = (String) result.get("BodyMsg");
			if (!"".equals(msgBody) && msgBody != null) {
				String[] strArr = msgBody.split("&");
				map.put("revMobilePhone", strArr[0]);
				map.put("serialNo", strArr[1]);
			}

		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return map;
	}

	public List<ZjjzFetchData> queryUserFetch(Long userId, RecentPeriodCode termCode) throws Exception {
		AccountData payAccountData = accountService.getAccountByUserId(userId, PayStyleCode.pinganpay);
		String custAcctId = payAccountData.getAccountNo();

		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		Calendar endTime = Calendar.getInstance();
		Calendar startTime = CalendarUtil.addDays(endTime, -termCode.getTotalDays());
		String beginDate = CalendarUtil.toYYYYMMDD(startTime);
		String endDate = CalendarUtil.toYYYYMMDD(endTime);

		List<ZjjzFetchData> result = new ArrayList<ZjjzFetchData>();

		Integer p = 0, size = 0;
		do {
			p++;
			try {
				List<ZjjzFetchData> datas = this.api6073(custAcctId, beginDate, endDate, p.toString());
				size = datas.size();
				if (size > 0)
					result.addAll(datas);
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				break;
			}
		} while (size > 0);

		return result;
	}

	public List<ZjjzPayData> queryUserPay(Long userId, RecentPeriodCode termCode) throws Exception {
		AccountData payAccountData = accountService.getAccountByUserId(userId, PayStyleCode.pinganpay);
		String custAcctId = payAccountData.getAccountNo();

		if (termCode == null)
			termCode = RecentPeriodCode.THREE_DAYS;

		Calendar endTime = Calendar.getInstance();
		Calendar startTime = CalendarUtil.addDays(endTime, -termCode.getTotalDays());
		String beginDate = CalendarUtil.toYYYYMMDD(startTime);
		String endDate = CalendarUtil.toYYYYMMDD(endTime);

		List<ZjjzPayData> result = new ArrayList<ZjjzPayData>();

		Integer p = 0, size = 0;
		do {
			p++;
			try {
				List<ZjjzPayData> datas = this.api6072(custAcctId, beginDate, endDate, p.toString());
				size = datas.size();
				if (size > 0)
					result.addAll(datas);
			} catch (Exception e1) {
				logger.error(e1.getMessage());
				break;
			}
		} while (size > 0);

		return result;
	}

}
