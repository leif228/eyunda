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
@Table(name = "YydWallet")
public class YydWallet extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Enumerated(EnumType.ORDINAL)
	private SettleStyleCode settleStyle = null; // 帐务类型
	@Column(nullable = false, length = 26)
	private String paymentNo = ""; // 支付序列号：客户号10位＋日期8位＋序列号8位
	@Enumerated(EnumType.ORDINAL)
	private FeeItemCode feeItem = FeeItemCode.inaccount; // 费项类型
	@Column
	private Long orderId = 0L; // 订单ID改为支付ID

	@Column
	private Long buyerId = 0L; // 买家ID
	@Column
	private Long brokerId = 0L; // 中间人ID
	@Column
	private Long sellerId = 0L; // 卖家ID

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
	private PayStatusCode paymentStatus = PayStatusCode.WAIT_PAYMENT; // 支付状态
	@Column
	private Calendar gmtPayment = Calendar.getInstance(); // 支付时间
	@Column
	private Integer suretyDays = 0; // 资金托管天数
	@Column
	private Calendar gmtSurety = Calendar.getInstance(); // 资金托管时间
	@Enumerated(EnumType.ORDINAL)
	private ApplyReplyCode refundStatus = null; // 退款状态
	@Column
	private Calendar gmtRefund = Calendar.getInstance(); // 退款时间

	@Enumerated(EnumType.ORDINAL)
	private YesNoCode khFlag = YesNoCode.no; // yes跨行收单，no钱包支付
	@Column(nullable = true, length = 4)
	private String bankCardNo4 = ""; // 银行卡号后4位
	@Column(nullable = true, length = 20)
	private String plantBankName = ""; // 银行名称
	
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode khRefundFlag = YesNoCode.no; // yes:跨行支付等待中,no:缺省

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

	public Long getBrokerId() {
		return brokerId;
	}

	public void setBrokerId(Long brokerId) {
		this.brokerId = brokerId;
	}

	public Long getSellerId() {
		return sellerId;
	}

	public void setSellerId(Long sellerId) {
		this.sellerId = sellerId;
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

	public Calendar getGmtPayment() {
		return gmtPayment;
	}

	public void setGmtPayment(Calendar gmtPayment) {
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

	public Calendar getGmtSurety() {
		return gmtSurety;
	}

	public void setGmtSurety(Calendar gmtSurety) {
		this.gmtSurety = gmtSurety;
	}

	public ApplyReplyCode getRefundStatus() {
		return refundStatus;
	}

	public void setRefundStatus(ApplyReplyCode refundStatus) {
		this.refundStatus = refundStatus;
	}

	public Calendar getGmtRefund() {
		return gmtRefund;
	}

	public void setGmtRefund(Calendar gmtRefund) {
		this.gmtRefund = gmtRefund;
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
