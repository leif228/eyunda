package com.hangyi.eyunda.data.order;

import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderTrustData extends OrderCommonData {

	private static final long serialVersionUID = 1L;

	private String startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 租期开始日期
	private String endDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 租期结束日期

	private String rcvDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 交船日期
	private String rcvPortNo = ""; // 交船码头
	private PortData rcvPort = null; // 交船码头
	private String retDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 还船日期
	private String retPortNo = ""; // 还船码头
	private PortData retPort = null; // 交船码头

	private Double rentRate = 0.00D; // 租金率(元/月)
	private Double preRent = 0.00D; // 预付租金(元)
	private Double oilRate = 0.00D; // 油耗(公斤/小时)
	private Double oilPrice = 0.00D; // 油价(元/吨)
	private String shipDesc = "承租人需提前3日返还，航行范围依照船舶证书，禁装危险品。"; // 租船说明
	private String oilDesc = "油价调整以国家每月公布油价为准，油价升降超过5%、10%、15%（以此类推）时，对应调整包干油耗。船舶油耗航行时间以约定航线航行时间为标准，按实际营运航次计算。"; // 燃油费计算说明
	private String payDesc = "每月25日前支付下月租金，租期不足整月的，租金按天计算，不足一天的，按小时计算。"; // 支付结算说明

	public OrderTrustData() {
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
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

	public String getRcvDate() {
		return rcvDate;
	}

	public void setRcvDate(String rcvDate) {
		this.rcvDate = rcvDate;
	}

	public String getRcvPortNo() {
		return rcvPortNo;
	}

	public void setRcvPortNo(String rcvPortNo) {
		this.rcvPortNo = rcvPortNo;
	}

	public String getRetDate() {
		return retDate;
	}

	public void setRetDate(String retDate) {
		this.retDate = retDate;
	}

	public String getRetPortNo() {
		return retPortNo;
	}

	public void setRetPortNo(String retPortNo) {
		this.retPortNo = retPortNo;
	}

	public Double getRentRate() {
		return rentRate;
	}

	public void setRentRate(Double rentRate) {
		this.rentRate = rentRate;
	}

	public Double getPreRent() {
		return preRent;
	}

	public void setPreRent(Double preRent) {
		this.preRent = preRent;
	}

	public Double getOilRate() {
		return oilRate;
	}

	public void setOilRate(Double oilRate) {
		this.oilRate = oilRate;
	}

	public Double getOilPrice() {
		return oilPrice;
	}

	public void setOilPrice(Double oilPrice) {
		this.oilPrice = oilPrice;
	}

	public String getShipDesc() {
		return shipDesc;
	}

	public void setShipDesc(String shipDesc) {
		this.shipDesc = shipDesc;
	}

	public String getOilDesc() {
		return oilDesc;
	}

	public void setOilDesc(String oilDesc) {
		this.oilDesc = oilDesc;
	}

	public String getPayDesc() {
		return payDesc;
	}

	public void setPayDesc(String payDesc) {
		this.payDesc = payDesc;
	}

	public PortData getRcvPort() {
		return rcvPort;
	}

	public void setRcvPort(PortData rcvPort) {
		this.rcvPort = rcvPort;
	}

	public PortData getRetPort() {
		return retPort;
	}

	public void setRetPort(PortData retPort) {
		this.retPort = retPort;
	}

}
