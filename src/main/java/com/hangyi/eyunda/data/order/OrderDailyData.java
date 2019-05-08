package com.hangyi.eyunda.data.order;

import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class OrderDailyData extends OrderCommonData {

	private static final long serialVersionUID = 1L;

	private String startDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 租期开始日期
	private String endDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 租期结束日期

	private String rcvDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 交船日期
	private String rcvPortNo = ""; // 交船码头
	private PortData rcvPort = null; // 交船码头
	private String retDate = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now()); // 还船日期
	private String retPortNo = ""; // 还船码头
	private PortData retPort = null; // 交船码头

	private Integer days = 0; // 租期天数
	private Double price = 0.00D; // 租金率
	private Double oilPrice = 0.00D; // 燃油费计算

	public OrderDailyData() {
	}

	public OrderDailyData(OrderTypeCode orderType) {
		this.setOrderType(orderType);
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

	public PortData getRcvPort() {
		return rcvPort;
	}

	public void setRcvPort(PortData rcvPort) {
		this.rcvPort = rcvPort;
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

	public PortData getRetPort() {
		return retPort;
	}

	public void setRetPort(PortData retPort) {
		this.retPort = retPort;
	}

	public Integer getDays() {
		return days;
	}

	public void setDays(Integer days) {
		this.days = days;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Double getOilPrice() {
		return oilPrice;
	}

	public void setOilPrice(Double oilPrice) {
		this.oilPrice = oilPrice;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
