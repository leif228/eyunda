package com.hangyi.eyunda.data.advert;

import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.ship.ShipData;

public class AdvertData {
	private static final long serialVersionUID = -1L;

	private Long sid; //  资源Id
	private Long order; // 广告排序
	private String title; // 现实标题
	private String ext; // 扩展标题
	private Long adtype; // 广告类型 1船舶，2货物，3油品
	private String logo; // 展示图片
	private UserData contact; // 展示图片

	private ShipData shipData = null; // 船盘信息

	public Long getSid() {
		return sid;
	}

	public void setSid(Long sid) {
		this.sid = sid;
	}

	public Long getOrder() {
		return order;
	}

	public void setOrder(Long order) {
		this.order = order;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public Long getAdtype() {
		return adtype;
	}

	public void setAdtype(Long adtype) {
		this.adtype = adtype;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}

	public ShipData getShipData() {
		return shipData;
	}

	public void setShipData(ShipData shipData) {
		this.shipData = shipData;
	}

	public UserData getContact() {
		return contact;
	}

	public void setContact(UserData contact) {
		this.contact = contact;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
