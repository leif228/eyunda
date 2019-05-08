package com.hangyi.eyunda.data.order;

import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.WrapTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderWrapData extends OrderCommonData {

	private static final long serialVersionUID = 1L;

	private String startPortNo = ""; // 装货港编号
	private PortData startPort = null; // 装货港
	private String endPortNo = ""; // 卸货港编号
	private PortData endPort = null; // 卸货港

	private WrapTypeCode wrapType = null; // 货类
	private String cargoName = ""; // 货名
	private Integer amount = 0; // 数量
	private Double ton = 0.00D; // 重量
	private Double worth = 0.00D; // 价值
	private Double price = 0.00D; // 运价(元/件)
	private Double demurrage = 0.00D; // 滞期费率(元/吨.天)

	private String upDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 装货日期
	private String downDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 卸货日期

	private Integer upDay = 0; // 允许装货时间（天）
	private Integer downDay = 0; // 允许卸货时间（天）
	private Integer upHour = 0; // 允许装货时间（小时）
	private Integer downHour = 0; // 允许卸货时间（小时）
	private String cargoDesc = "封仓交接，船方不理货。"; // 货物细节说明
	private String payDesc = "双方约定支付通过易运达钱包进行，转帐手续费及代理人佣金从支付款项中按比例扣除，转帐手续费1%，代理人佣金4%。"; // 支付结算说明

	public OrderWrapData() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getStartPortNo() {
		return startPortNo;
	}

	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	public PortData getStartPort() {
		return startPort;
	}

	public void setStartPort(PortData startPort) {
		this.startPort = startPort;
	}

	public String getEndPortNo() {
		return endPortNo;
	}

	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	public PortData getEndPort() {
		return endPort;
	}

	public void setEndPort(PortData endPort) {
		this.endPort = endPort;
	}

	public WrapTypeCode getWrapType() {
		return wrapType;
	}

	public void setWrapType(WrapTypeCode wrapType) {
		this.wrapType = wrapType;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Double getTon() {
		return ton;
	}

	public void setTon(Double ton) {
		this.ton = ton;
	}

	public Double getWorth() {
		return worth;
	}

	public void setWorth(Double worth) {
		this.worth = worth;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getDemurrage() {
		return demurrage;
	}

	public void setDemurrage(Double demurrage) {
		this.demurrage = demurrage;
	}

	public String getUpDate() {
		return upDate;
	}

	public void setUpDate(String upDate) {
		this.upDate = upDate;
	}

	public String getDownDate() {
		return downDate;
	}

	public void setDownDate(String downDate) {
		this.downDate = downDate;
	}

	public Integer getUpDay() {
		return upDay;
	}

	public void setUpDay(Integer upDay) {
		this.upDay = upDay;
	}

	public Integer getDownDay() {
		return downDay;
	}

	public void setDownDay(Integer downDay) {
		this.downDay = downDay;
	}

	public Integer getUpHour() {
		return upHour;
	}

	public void setUpHour(Integer upHour) {
		this.upHour = upHour;
	}

	public Integer getDownHour() {
		return downHour;
	}

	public void setDownHour(Integer downHour) {
		this.downHour = downHour;
	}

	public String getCargoDesc() {
		return cargoDesc;
	}

	public void setCargoDesc(String cargoDesc) {
		this.cargoDesc = cargoDesc;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

}
