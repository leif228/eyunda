package com.hangyi.eyunda.data.wallet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.AccountData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.BankCode2;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CapitalizednumbersUtil;

public class WalletData extends BaseData {

	private static final long serialVersionUID = 1L;

	private Long id = 0L; // 钱包账务id

	private SettleStyleCode settleStyle = null; // 帐务类型
	private String paymentNo = ""; // 支付序列号：客户号10位＋日期8位＋序列号8位

	private FeeItemCode feeItem = null; // 费项类型
	private Long orderId = 0L; // 订单ID

	private Long buyerId = 0L;
	private UserData buyerData = null; // 买家
	private String sndAccountName = ""; // 付款帐户名
	private String sndCardNo = ""; // 付款帐号

	private Long brokerId = 0L; // 中间人ID
	private UserData brokerData = null; // 中间人
	private String brokerAccountName = ""; // 收款帐户名
	private String brokerCardNo = ""; // 收款帐号

	private Long sellerId = 0L;
	private UserData sellerData = null; // 卖家
	private String rcvAccountName = ""; // 收款帐户名
	private String rcvCardNo = ""; // 收款帐号

	private String subject = ""; // 交易标题
	private String body = ""; // 交易描述

	private Double totalFee = 0.00D; // 交易金额
	private Double middleFee = 0.00D; // 代理人佣金
	private Double serviceFee = 0.00D; // 平台服务费
	private Double handFee = 0.00D; // 提现手续费

	private String totalFeeChinese = ""; // 交易金额中文
	private String middleFeeChinese = ""; // 代理人佣金中文
	private String serviceFeeChinese = ""; // 平台服务费中文
	private String handFeeChinese = ""; // 提现手续费中文

	private PayStatusCode paymentStatus = null; // 支付状态
	private String gmtPayment = ""; // 支付时间

	private Integer suretyDays = 0; // 资金托管天数
	private String gmtSurety = ""; // 资金托管时间

	private ApplyReplyCode refundStatus = null; // 退款状态
	private String gmtRefund = ""; // 退款时间

	private YesNoCode khFlag = YesNoCode.no; // 是否跨行收单
	private String bankCardNo4 = ""; // 银行卡号后4位
	private String plantBankName = ""; // 银行名称

	private YesNoCode khRefundFlag = YesNoCode.no; // yes:跨行支付等待中,no:缺省

	private YesNoCode errorStatus = YesNoCode.no;
	private List<WalletZjjzData> walletZjjzDatas = null;
	private YesNoCode khErrorStatus = YesNoCode.no;
	private List<WalletKhsdData> walletKhsdDatas = null;

	// 当前用户可进行的操作
	private Map<String, Boolean> ops = new HashMap<String, Boolean>();

	private Map<String, String> temps = new HashMap<String, String>();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SettleStyleCode getSettleStyle() {
		return settleStyle;
	}

	public void setSettleStyle(SettleStyleCode settleStyle) {
		this.settleStyle = settleStyle;
	}

	public String getShortPaymentNo() {
		return paymentNo;
	}

	public String getPaymentNo() {
		return paymentNo;
	}

	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	public FeeItemCode getFeeItem() {
		return feeItem;
	}

	public void setFeeItem(FeeItemCode feeItem) {
		this.feeItem = feeItem;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public UserData getSellerData() {
		return sellerData;
	}

	public void setSellerData(UserData sellerData) {
		this.sellerData = sellerData;
	}

	public String getRcvAccountName() {
		return rcvAccountName;
	}

	public void setRcvAccountName(String rcvAccountName) {
		this.rcvAccountName = rcvAccountName;
	}

	public String getRcvCardNo() {
		return rcvCardNo;
	}

	public void setRcvCardNo(String rcvCardNo) {
		this.rcvCardNo = rcvCardNo;
	}

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public UserData getBuyerData() {
		return buyerData;
	}

	public void setBuyerData(UserData buyerData) {
		this.buyerData = buyerData;
	}

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public UserData getBrokerData() {
		return brokerData;
	}

	public void setBrokerData(UserData brokerData) {
		this.brokerData = brokerData;
	}

	public String getBrokerAccountName() {
		return brokerAccountName;
	}

	public void setBrokerAccountName(String brokerAccountName) {
		this.brokerAccountName = brokerAccountName;
	}

	public String getBrokerCardNo() {
		return brokerCardNo;
	}

	public void setBrokerCardNo(String brokerCardNo) {
		this.brokerCardNo = brokerCardNo;
	}

	public String getSndAccountName() {
		return sndAccountName;
	}

	public void setSndAccountName(String sndAccountName) {
		this.sndAccountName = sndAccountName;
	}

	public String getSndCardNo() {
		return sndCardNo;
	}

	public void setSndCardNo(String sndCardNo) {
		this.sndCardNo = sndCardNo;
	}

	public YesNoCode getKhFlag() {
		return khFlag;
	}

	public void setKhFlag(YesNoCode khFlag) {
		this.khFlag = khFlag;
	}

	public YesNoCode getKhRefundFlag() {
		return khRefundFlag;
	}

	public void setKhRefundFlag(YesNoCode khRefundFlag) {
		this.khRefundFlag = khRefundFlag;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public Double getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
		this.totalFeeChinese = CapitalizednumbersUtil.convertToChineseNumber(this.totalFee);
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	public PayStatusCode getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PayStatusCode paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public Integer getSuretyDays() {
		return suretyDays;
	}

	public void setSuretyDays(Integer suretyDays) {
		this.suretyDays = suretyDays;
	}

	public String getGmtSurety() {
		return gmtSurety;
	}

	public void setGmtSurety(String gmtSurety) {
		this.gmtSurety = gmtSurety;
	}

	public ApplyReplyCode getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(ApplyReplyCode refundStatus) {
		this.refundStatus = refundStatus;
	}

	public String getGmtRefund() {
		return gmtRefund;
	}

	public void setGmtRefund(String gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	public Double getMiddleFee() {
		return middleFee;
	}

	public void setMiddleFee(Double middleFee) {
		this.middleFee = middleFee;
		this.middleFeeChinese = CapitalizednumbersUtil.convertToChineseNumber(this.middleFee);
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
		this.serviceFeeChinese = CapitalizednumbersUtil.convertToChineseNumber(this.serviceFee);
	}

	public Double getHandFee() {
		return handFee;
	}

	public void setHandFee(Double handFee) {
		this.handFee = handFee;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Map<String, Boolean> getOps() {
		return ops;
	}

	public void setOps(Map<String, Boolean> ops) {
		this.ops = ops;
	}

	public Map<String, String> getTemps() {
		return temps;
	}

	public void setTemps(Map<String, String> temps) {
		this.temps = temps;
	}

	public YesNoCode getErrorStatus() {
		return errorStatus;
	}

	public void setErrorStatus(YesNoCode errorStatus) {
		this.errorStatus = errorStatus;
	}

	public List<WalletZjjzData> getWalletZjjzDatas() {
		return walletZjjzDatas;
	}

	public void setWalletZjjzDatas(List<WalletZjjzData> walletZjjzDatas) {
		this.walletZjjzDatas = walletZjjzDatas;
	}

	public YesNoCode getKhErrorStatus() {
		return khErrorStatus;
	}

	public void setKhErrorStatus(YesNoCode khErrorStatus) {
		this.khErrorStatus = khErrorStatus;
	}

	public List<WalletKhsdData> getWalletKhsdDatas() {
		return walletKhsdDatas;
	}

	public void setWalletKhsdDatas(List<WalletKhsdData> walletKhsdDatas) {
		this.walletKhsdDatas = walletKhsdDatas;
	}

	public String getTotalFeeChinese() {
		return totalFeeChinese;
	}

	public void setTotalFeeChinese(String totalFeeChinese) {
		this.totalFeeChinese = totalFeeChinese;
	}

	public String getMiddleFeeChinese() {
		return middleFeeChinese;
	}

	public void setMiddleFeeChinese(String middleFeeChinese) {
		this.middleFeeChinese = middleFeeChinese;
	}

	public String getServiceFeeChinese() {
		return serviceFeeChinese;
	}

	public void setServiceFeeChinese(String serviceFeeChinese) {
		this.serviceFeeChinese = serviceFeeChinese;
	}

	public String getHandFeeChinese() {
		return handFeeChinese;
	}

	public void setHandFeeChinese(String handFeeChinese) {
		this.handFeeChinese = handFeeChinese;
	}

	public String getBankCardNo4() {
		return bankCardNo4;
	}

	public void setBankCardNo4(String bankCardNo4) {
		this.bankCardNo4 = bankCardNo4;
	}

	public String getPlantBankName() {
		return plantBankName;
	}

	public void setPlantBankName(String plantBankName) {
		this.plantBankName = plantBankName;
	}

	public void setDescByLoginUser(UserData userData) {
		Long userId = userData.getId();
		AccountData zjjzAccountData = userData.getAccountData();

		String fdt[] = CalendarUtil.toHumanFormat(CalendarUtil.parseYYYY_MM_DD_HH_MM_SS(gmtPayment)).split(" ");
		String payDate = fdt[0];
		String payTime = fdt[1];
		String bankIcon = "";
		String bankIconlng = "";
		String tradeDesc = "";
		String bankDesc = "";
		String statusDesc = "";

		switch (this.settleStyle) {
		case fill:
			tradeDesc += "充" + this.totalFee + "元。";
			if (this.paymentStatus == PayStatusCode.WAIT_PAYMENT && this.khRefundFlag == YesNoCode.yes)
				statusDesc += "充值中...";
			else
				statusDesc += "" + this.paymentStatus.getRemark2();
			break;
		case fetch:
			if (this.plantBankName != null && this.plantBankName.indexOf("平安银行") >= 0) {
				this.handFee = 0D;
				tradeDesc += "提" + this.totalFee + "元，免提现手续费。";
			} else {
				if (this.totalFee <= 10000.00D)
					this.handFee = 4D;
				else
					this.handFee = 8D;

				tradeDesc += "提" + this.totalFee + "元，付提现手续费" + this.handFee + "元。";
			}
			this.handFeeChinese = CapitalizednumbersUtil.convertToChineseNumber(this.totalFee + this.handFee);

			statusDesc += "" + this.paymentStatus.getRemark();
			break;
		case pay:
			if (this.buyerId.equals(this.brokerId) || this.sellerId.equals(this.brokerId) || this.brokerId.equals(0L)) {
				if (FeeItemCode.face == this.feeItem) {
					if (userId.equals(this.buyerId)) {
						tradeDesc += "付" + this.sellerData.getTrueName() + "，金额" + this.totalFee + "元。";
					} else if (userId.equals(this.sellerId)) {
						if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT && zjjzAccountData != null) {
							this.plantBankName = "平安见证宝";
							this.bankCardNo4 = zjjzAccountData.getAccountNo()
									.substring(zjjzAccountData.getAccountNo().length() - 4);

							bankIcon = "/img/bank2/pinganzjjz.jpg";
							bankIconlng = "/img/bank2/pinganzjjz.jpg";
						}
						tradeDesc += "收" + this.buyerData.getTrueName() + "，金额" + this.totalFee + "元。";
						tradeDesc += "付平台服务费" + this.serviceFee + "元。";
					}
				} else if (FeeItemCode.prefee == this.feeItem) {
					if (userId.equals(this.buyerId)) {
						tradeDesc += "付" + this.orderId + "号合同金额" + this.totalFee + "元。";
					} else if (userId.equals(this.sellerId)) {
						if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT && zjjzAccountData != null) {
							this.plantBankName = "平安见证宝";
							this.bankCardNo4 = zjjzAccountData.getAccountNo()
									.substring(zjjzAccountData.getAccountNo().length() - 4);

							bankIcon = "/img/bank2/pinganzjjz.jpg";
							bankIconlng = "/img/bank2/pinganzjjz.jpg";
						}
						tradeDesc += "收" + this.orderId + "号合同金额" + this.totalFee + "元。" + "付平台服务费" + this.serviceFee
								+ "元。";
					}
				} else if (FeeItemCode.bonus == this.feeItem) {
					if (userId.equals(this.buyerId)) {
						tradeDesc += "发红包给" + this.sellerData.getTrueName() + "，金额" + this.totalFee + "元。";
					} else if (userId.equals(this.sellerId)) {
						if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT && zjjzAccountData != null) {
							this.plantBankName = "平安见证宝";
							this.bankCardNo4 = zjjzAccountData.getAccountNo()
									.substring(zjjzAccountData.getAccountNo().length() - 4);

							bankIcon = "/img/bank2/pinganzjjz.jpg";
							bankIconlng = "/img/bank2/pinganzjjz.jpg";
						}
						tradeDesc += "收" + this.buyerData.getTrueName() + "红包，金额" + this.totalFee + "元。";
					}
				}
			} else {
				if (userId.equals(this.buyerId)) {
					tradeDesc += "付" + this.orderId + "号合同金额" + this.totalFee + "元。";
				} else if (userId.equals(this.sellerId)) {
					if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT && zjjzAccountData != null) {
						this.plantBankName = "平安见证宝";
						this.bankCardNo4 = zjjzAccountData.getAccountNo()
								.substring(zjjzAccountData.getAccountNo().length() - 4);

						bankIcon = "/img/bank2/pinganzjjz.jpg";
						bankIconlng = "/img/bank2/pinganzjjz.jpg";
					}
					tradeDesc += "收" + this.orderId + "号合同金额" + this.totalFee + "元。" + "付代理人佣金" + this.middleFee
							+ "元，平台服务费" + this.serviceFee + "元。";
				} else if (userId.equals(this.brokerId)) {
					if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT && zjjzAccountData != null) {
						this.plantBankName = "平安见证宝";
						this.bankCardNo4 = zjjzAccountData.getAccountNo()
								.substring(zjjzAccountData.getAccountNo().length() - 4);

						bankIcon = "/img/bank2/pinganzjjz.jpg";
						bankIconlng = "/img/bank2/pinganzjjz.jpg";
					}
					tradeDesc += "收" + this.orderId + "号合同佣金" + this.middleFee + "元。";
				}
			}
			if (this.paymentStatus == PayStatusCode.WAIT_PAYMENT && this.khRefundFlag == YesNoCode.yes)
				statusDesc += "支付中...";
			else
				statusDesc += "" + this.paymentStatus.getDescription();
			if (this.paymentStatus == PayStatusCode.WAIT_CONFIRM && this.refundStatus != ApplyReplyCode.noapply)
				statusDesc += "，" + this.refundStatus.getDescription();

			break;
		default:
			break;
		}

		if (this.paymentStatus != PayStatusCode.WAIT_PAYMENT) {
			if (this.plantBankName != null && !"".equals(this.plantBankName) && this.bankCardNo4 != null
					&& !"".equals(this.bankCardNo4)) {// 银行卡付
				bankDesc += this.plantBankName + "(*" + this.bankCardNo4 + ")";

				BankCode2 bc = BankCode2.getByName(this.plantBankName);
				if (bc != null) {
					bankIcon = bc.getIcon();
					bankIconlng = bc.getIconlng();
				}
			} else {// 钱包付
				this.plantBankName = "平安见证宝";
				this.bankCardNo4 = zjjzAccountData.getAccountNo()
						.substring(zjjzAccountData.getAccountNo().length() - 4);
				bankDesc += this.plantBankName + "(*" + this.bankCardNo4 + ")";

				bankIcon = "/img/bank2/pinganzjjz.jpg";
				bankIconlng = "/img/bank2/pinganzjjz.jpg";
			}
		} else {
			bankIcon = "/img/bank2/bank.png";
			bankIconlng = "/img/bank2/bank.png";
		}
		if ("".equals(bankIcon))
			;// 默认图标

		this.temps.put("payDate", payDate);
		this.temps.put("payTime", payTime);
		this.temps.put("bankIcon", bankIcon);
		this.temps.put("bankIconlng", bankIconlng);
		this.temps.put("tradeDesc", tradeDesc);
		this.temps.put("bankDesc", bankDesc);
		this.temps.put("statusDesc", statusDesc);
	}

}
