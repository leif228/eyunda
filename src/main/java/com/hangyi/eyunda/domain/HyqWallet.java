package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ApplyReplyCode;
import com.hangyi.eyunda.domain.enumeric.FeeItemCode;
import com.hangyi.eyunda.domain.enumeric.PayStatusCode;
import com.hangyi.eyunda.domain.enumeric.SettleStyleCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "HyqWallet")
public class HyqWallet extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 26)
	private String paymentNo = ""; // 支付号
	@Enumerated(EnumType.ORDINAL)
	private SettleStyleCode settleStyle = null; // 帐务类型
	@Enumerated(EnumType.ORDINAL)
	private FeeItemCode feeItem = null; // 费项类型
	@Column
	private Long orderId = 0L; // 订单ID
	@Column
	private Long sellerId = 0L; // 卖家ID
	@Column
	private Long brokerId = 0L; // 代理人ID
	@Column
	private Long buyerId = 0L; // 买家ID
	@Column(nullable = false, length = 200)
	private String subject = ""; // 交易标题
	@Column(nullable = true, length = 500)
	private String body = ""; // 交易描述
	@Column
	private Double totalFee = 0.00D; // 交易金额
	@Column
	private Double middleFee = 0.00D; // 代理人佣金
	@Column
	private Double serviceFee = 0.00D; // 平台服务费
	@Enumerated(EnumType.ORDINAL)
	private PayStatusCode paymentStatus = null; // 支付状态
	@Column
	private Calendar gmtPayment = Calendar.getInstance(); // 支付时间
	@Column
	private Integer suretyDays = 0; // 担保天数
	@Column
	private Calendar gmtSurety = Calendar.getInstance(); // 担保时间
	@Enumerated(EnumType.ORDINAL)
	private ApplyReplyCode refundStatus = null; // 退款状态
	@Column
	private Calendar gmtRefund = Calendar.getInstance(); // 退款时间
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode khFlag = null; // 跨行收款
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode khRefundFlag = null; // 跨行退款
	@Column(nullable = true, length = 4)
	private String bankCardNo4 = ""; // 银行卡号后4位
	@Column(nullable = true, length = 20)
	private String plantBankName = ""; // 银行名称

	/**
	 * 取得支付号
	 */
	public String getPaymentNo() {
		return paymentNo;
	}

	/**
	 * 设置支付号
	 */
	public void setPaymentNo(String paymentNo) {
		this.paymentNo = paymentNo;
	}

	/**
	 * 取得帐务类型
	 */
	public SettleStyleCode getSettleStyle() {
		return settleStyle;
	}

	/**
	 * 设置帐务类型
	 */
	public void setSettleStyle(SettleStyleCode settleStyle) {
		this.settleStyle = settleStyle;
	}

	/**
	 * 取得费项类型
	 */
	public FeeItemCode getFeeItem() {
		return feeItem;
	}

	/**
	 * 设置费项类型
	 */
	public void setFeeItem(FeeItemCode feeItem) {
		this.feeItem = feeItem;
	}

	/**
	 * 取得订单ID
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单ID
	 */
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	/**
	 * 取得卖家ID
	 */
	public Long getSellerId() {
		return sellerId;
	}

	/**
	 * 设置卖家ID
	 */
	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
	}

	/**
	 * 取得代理人ID
	 */
	public Long getBrokerId() {
		return brokerId;
	}

	/**
	 * 设置代理人ID
	 */
	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	/**
	 * 取得买家ID
	 */
	public Long getBuyerId() {
		return buyerId;
	}

	/**
	 * 设置买家ID
	 */
	public void setBuyerId(Long buyerId) {
		this.buyerId = buyerId;
	}

	/**
	 * 取得交易标题
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * 设置交易标题
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * 取得交易描述
	 */
	public String getBody() {
		return body;
	}

	/**
	 * 设置交易描述
	 */
	public void setBody(String body) {
		this.body = body;
	}

	/**
	 * 取得交易金额
	 */
	public Double getTotalFee() {
		return totalFee;
	}

	/**
	 * 设置交易金额
	 */
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	/**
	 * 取得代理人佣金
	 */
	public Double getMiddleFee() {
		return middleFee;
	}

	/**
	 * 设置代理人佣金
	 */
	public void setMiddleFee(Double middleFee) {
		this.middleFee = middleFee;
	}

	/**
	 * 取得平台服务费
	 */
	public Double getServiceFee() {
		return serviceFee;
	}

	/**
	 * 设置平台服务费
	 */
	public void setServiceFee(Double serviceFee) {
		this.serviceFee = serviceFee;
	}

	/**
	 * 取得支付状态
	 */
	public PayStatusCode getPaymentStatus() {
		return paymentStatus;
	}

	/**
	 * 设置支付状态
	 */
	public void setPaymentStatus(PayStatusCode paymentStatus) {
		this.paymentStatus = paymentStatus;
	}

	/**
	 * 取得支付时间
	 */
	public Calendar getGmtPayment() {
		return gmtPayment;
	}

	/**
	 * 设置支付时间
	 */
	public void setGmtPayment(Calendar gmtPayment) {
		this.gmtPayment = gmtPayment;
	}

	/**
	 * 取得担保天数
	 */
	public Integer getSuretyDays() {
		return suretyDays;
	}

	/**
	 * 设置担保天数
	 */
	public void setSuretyDays(Integer suretyDays) {
		this.suretyDays = suretyDays;
	}

	/**
	 * 取得担保时间
	 */
	public Calendar getGmtSurety() {
		return gmtSurety;
	}

	/**
	 * 设置担保时间
	 */
	public void setGmtSurety(Calendar gmtSurety) {
		this.gmtSurety = gmtSurety;
	}

	/**
	 * 取得退款状态
	 */
	public ApplyReplyCode getRefundStatus() {
		return refundStatus;
	}

	/**
	 * 设置退款状态
	 */
	public void setRefundStatus(ApplyReplyCode refundStatus) {
		this.refundStatus = refundStatus;
	}

	/**
	 * 取得退款时间
	 */
	public Calendar getGmtRefund() {
		return gmtRefund;
	}

	/**
	 * 设置退款时间
	 */
	public void setGmtRefund(Calendar gmtRefund) {
		this.gmtRefund = gmtRefund;
	}

	/**
	 * 取得跨行收款
	 */
	public YesNoCode getKhFlag() {
		return khFlag;
	}

	/**
	 * 设置跨行收款
	 */
	public void setKhFlag(YesNoCode khFlag) {
		this.khFlag = khFlag;
	}

	/**
	 * 取得跨行退款
	 */
	public YesNoCode getKhRefundFlag() {
		return khRefundFlag;
	}

	/**
	 * 设置跨行退款
	 */
	public void setKhRefundFlag(YesNoCode khRefundFlag) {
		this.khRefundFlag = khRefundFlag;
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

}
