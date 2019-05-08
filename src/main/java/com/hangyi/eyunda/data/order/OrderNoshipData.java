package com.hangyi.eyunda.data.order;

import com.hangyi.eyunda.domain.enumeric.BulkTypeCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderNoshipData extends OrderCommonData {

	private static final long serialVersionUID = 1L;

	private String startPortNo = ""; // 装货港编号
	private PortData startPort = null; // 装货港
	private String endPortNo = ""; // 卸货港编号
	private PortData endPort = null; // 卸货港

	private BulkTypeCode cargoType = null; // 货类
	private String cargoName = ""; // 货名
	private Double ton = 0.00D; // 重量
	private Double worth = 0.00D; // 价值
	private Double price = 0.00D; // 运价(元/吨)

	private String startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 开始运输日期
	private String endDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 结束运输日期

	private String calcDesc = "货物保证不低于50000吨，货物不足50000吨按照50000吨结算，超过则按照实际装货吨结算。"; // 运费计算说明
	private String payDesc = "托运人将租金预付到易运达第三方支付平台账户，合同签订后按照总运费的50%预付运费，最后一船货物运抵卸货港开始卸货前预交另外50%。货物全部交付完毕后7日内结清运费。"; // 支付结算说明
	private String cargoDesc = "封仓交接，船方不理货。"; // 货物交接说明

	public OrderNoshipData() {
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

	public BulkTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(BulkTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
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

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCalcDesc() {
		return calcDesc;
	}

	public void setCalcDesc(String calcDesc) {
		this.calcDesc = calcDesc;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public String getCargoDesc() {
		return cargoDesc;
	}

	public void setCargoDesc(String cargoDesc) {
		this.cargoDesc = cargoDesc;
	}

}
