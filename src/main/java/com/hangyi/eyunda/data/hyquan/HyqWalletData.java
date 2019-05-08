package com.hangyi.eyunda.data.hyquan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.wallet.WalletKhsdData;
import com.hangyi.eyunda.data.wallet.WalletZjjzData;
import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.BankCode2;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.CapitalizednumbersUtil;

public class HyqWalletData extends BaseData {
	private static final long serialVersionUID = 1L;

	private Long id = 0L; // 钱包账务id

	private SettleStyleCode settleStyle = null; // 帐务类型
	private String paymentNo = ""; // 支付序列号：客户号10位＋日期8位＋序列号8位

	private FeeItemCode feeItem = null; // 费项类型
	private Long orderId = 0L; // 订单ID

	private Long buyerId = 0L;
	private HyqUserData buyerData = null; // 买家
	private String sndAccountName = ""; // 付款帐户名
	private String sndCardNo = ""; // 付款帐号

	private Long brokerId = 0L; // 中间人ID
	private HyqUserData brokerData = null; // 中间人
	private String brokerAccountName = ""; // 收款帐户名
	private String brokerCardNo = ""; // 收款帐号

	private Long sellerId = 0L;
	private HyqUserData sellerData = null; // 卖家
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

	public HyqWalletData() {
		super();
	}

	@SuppressWarnings("unchecked")
	public HyqWalletData(Map<String, Object> params) {
		super();
		if (params != null && !params.isEmpty()) {
			this.id = ((Double) params.get("id")).longValue();
			if (params.get("settleStyle") != null)
				this.settleStyle = SettleStyleCode.valueOf((String) params.get("settleStyle"));
			this.paymentNo = (String) params.get("paymentNo");
			if (params.get("feeItem") != null)
				this.feeItem = FeeItemCode.valueOf((String) params.get("feeItem"));
			this.orderId = ((Double) params.get("orderId")).longValue();
			this.buyerId = ((Double) params.get("buyerId")).longValue();
			this.buyerData = new HyqUserData((Map<String, Object>) params.get("buyerData"));
			this.sndAccountName = (String) params.get("sndAccountName");
			this.sndCardNo = (String) params.get("sndCardNo");
			this.brokerId = ((Double) params.get("brokerId")).longValue();
			this.brokerData = new HyqUserData((Map<String, Object>) params.get("brokerData"));
			this.brokerAccountName = (String) params.get("brokerAccountName");
			this.brokerCardNo = (String) params.get("brokerCardNo");
			this.sellerId = ((Double) params.get("sellerId")).longValue();
			this.sellerData = new HyqUserData((Map<String, Object>) params.get("sellerData"));
			this.rcvAccountName = (String) params.get("rcvAccountName");
			this.rcvCardNo = (String) params.get("rcvCardNo");
			this.subject = (String) params.get("subject");
			this.body = (String) params.get("body");
			this.totalFee = (Double) params.get("totalFee");
			this.middleFee = (Double) params.get("middleFee");
			this.serviceFee = (Double) params.get("serviceFee");
			this.handFee = (Double) params.get("handFee");
			this.totalFeeChinese = (String) params.get("totalFeeChinese");
			this.middleFeeChinese = (String) params.get("middleFeeChinese");
			this.serviceFeeChinese = (String) params.get("serviceFeeChinese");
			this.handFeeChinese = (String) params.get("handFeeChinese");
			if (params.get("paymentStatus") != null)
				this.paymentStatus = PayStatusCode.valueOf((String) params.get("paymentStatus"));
			this.gmtPayment = (String) params.get("gmtPayment");
			this.suretyDays = ((Double) params.get("suretyDays")).intValue();
			this.gmtSurety = (String) params.get("gmtSurety");
			if (params.get("refundStatus") != null)
				this.refundStatus = ApplyReplyCode.valueOf((String) params.get("refundStatus"));
			this.gmtRefund = (String) params.get("gmtRefund");
			if (params.get("khFlag") != null)
				this.khFlag = YesNoCode.valueOf((String) params.get("khFlag"));
			this.bankCardNo4 = (String) params.get("bankCardNo4");
			this.plantBankName = (String) params.get("plantBankName");
			if (params.get("khRefundFlag") != null)
				this.khRefundFlag = YesNoCode.valueOf((String) params.get("khRefundFlag"));
			if (params.get("errorStatus") != null)
				this.errorStatus = YesNoCode.valueOf((String) params.get("errorStatus"));
			this.walletZjjzDatas = new ArrayList<>();
			List<Map<String, Object>> walletZjjzDatasMap = (List<Map<String, Object>>) params.get("walletZjjzDatas");
			if (walletZjjzDatasMap != null && walletZjjzDatasMap.size() > 0) {
				for (Map<String, Object> map : walletZjjzDatasMap) {
					WalletZjjzData data = new WalletZjjzData(map);
					this.walletZjjzDatas.add(data);
				}
			}
			if (params.get("khErrorStatus") != null)
				this.khErrorStatus = YesNoCode.valueOf((String) params.get("khErrorStatus"));
			this.walletKhsdDatas = new ArrayList<>();
			List<Map<String, Object>> walletKhsdDatasMap = (List<Map<String, Object>>) params.get("walletKhsdDatas");
			if (walletKhsdDatasMap != null && walletKhsdDatasMap.size() > 0) {
				for (Map<String, Object> map : walletKhsdDatasMap) {
					WalletKhsdData data = new WalletKhsdData(map);
					this.walletKhsdDatas.add(data);
				}
			}

			this.ops = (Map<String, Boolean>) params.get("ops");

			this.temps = (Map<String, String>) params.get("temps");
		}
	}

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

	public Long getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	public HyqUserData getBuyerData() {
		return buyerData;
	}

	public void setBuyerData(HyqUserData buyerData) {
		this.buyerData = buyerData;
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

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public HyqUserData getBrokerData() {
		return brokerData;
	}

	public void setBrokerData(HyqUserData brokerData) {
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

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	public HyqUserData getSellerData() {
		return sellerData;
	}

	public void setSellerData(HyqUserData sellerData) {
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
	}

	public Double getMiddleFee() {
		return middleFee;
	}

	public void setMiddleFee(Double middleFee) {
		this.middleFee = middleFee;
	}

	public Double getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	public Double getHandFee() {
		return handFee;
	}

	public void setHandFee(Double handFee) {
		this.handFee = handFee;
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

	public PayStatusCode getPaymentStatus() {
		return paymentStatus;
	}

	public void setPaymentStatus(PayStatusCode paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	public String getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(String gmtPayment) {
		this.gmtPayment = gmtPayment;
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

	public YesNoCode getKhFlag() {
		return khFlag;
	}

	public void setKhFlag(YesNoCode khFlag) {
		this.khFlag = khFlag;
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

	public YesNoCode getKhRefundFlag() {
		return khRefundFlag;
	}

	public void setKhRefundFlag(YesNoCode khRefundFlag) {
		this.khRefundFlag = khRefundFlag;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setDescByLoginUser(HyqUserData userData) {
		Long userId = userData.getId();
		HyqAccountData zjjzAccountData = userData.getAccountData();

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
