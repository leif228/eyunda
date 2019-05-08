package com.hangyi.eyunda.data.order;

import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderTotebinData extends OrderCommonData {

	private static final long serialVersionUID = 1L;

	private String startPortNo = ""; // 装货港编号
	private PortData startPort = null; // 装货港
	private String endPortNo = ""; // 卸货港编号
	private PortData endPort = null; // 卸货港

	private String cargoNames = ""; // 规格
	private String teus = ""; // 箱量(个)
	private String prices = ""; // 运价(元/个)

	private String[] arrCargoName = { "20'E", "20'F", "40'E", "40'F", "45'E", "45'F" }; // 规格
	private Integer[] arrTeu = { 0, 0, 0, 0, 0, 0 }; // 箱量(个)
	private Double[] arrPrice = { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0 }; // 运价(元/个)

	private Double worth = 0.00D; // 价值(元)
	private Double demurrage = 0.00D; // 滞期费率（元/天）

	private String upDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 装货日期
	private String downDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 卸货日期

	private Integer upDay = 0; // 允许装货时间（天）
	private Integer downDay = 0; // 允许卸货时间（天）
	private Integer upHour = 0; // 允许装货时间（小时）
	private Integer downHour = 0; // 允许卸货时间（小时）
	private String cargoDesc = "封仓交接，船方不理货。"; // 货物细节说明
	private String payDesc = "双方约定支付通过易运达钱包进行，转帐手续费及代理人佣金从支付款项中按比例扣除，转帐手续费1%，代理人佣金4%。"; // 支付结算说明

	public OrderTotebinData() {
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

	public String getCargoNames() {
		return cargoNames;
	}

	public void setCargoNames(String cargoNames) {
		this.cargoNames = cargoNames;

		String[] ss = cargoNames.split(",");
		if (ss.length == 6)
			for (int i = 0; i < 6; i++)
				this.arrCargoName[i] = ss[i];
	}

	public String getTeus() {
		return teus;
	}

	public void setTeus(String teus) {
		this.teus = teus;

		String[] ss = teus.split(",");
		if (ss.length == 6)
			for (int i = 0; i < 6; i++)
				this.arrTeu[i] = Integer.parseInt(ss[i]);
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;

		String[] ss = prices.split(",");
		if (ss.length == 6)
			for (int i = 0; i < 6; i++)
				this.arrPrice[i] = Double.parseDouble(ss[i]);
	}

	public String[] getArrCargoName() {
		return arrCargoName;
	}

	public void setArrCargoName(String[] arrCargoName) {
		this.arrCargoName = arrCargoName;
	}

	public Integer[] getArrTeu() {
		return arrTeu;
	}

	public void setArrTeu(Integer[] arrTeu) {
		this.arrTeu = arrTeu;
	}

	public Double[] getArrPrice() {
		return arrPrice;
	}

	public void setArrPrice(Double[] arrPrice) {
		this.arrPrice = arrPrice;
	}

	public Double getWorth() {
		return worth;
	}

	public void setWorth(Double worth) {
		this.worth = worth;
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
