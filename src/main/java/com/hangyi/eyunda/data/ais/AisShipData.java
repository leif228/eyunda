package com.hangyi.eyunda.data.ais;

public class AisShipData {
	private String mmsi; // mmsi
	private String shipname; // 船舶名称
	private String callsign; // 船舶呼号
	private String imo; // Imo号
	private String shiptype; // 船舶类型
	private String length; // 船长
	private String breadth; // 船宽
	private String eta; // 预到时间
	private String dest_port; // 目的地
	private String draught; // 吃水

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getShipname() {
		return shipname;
	}

	public void setShipname(String shipname) {
		this.shipname = shipname;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
	}

	public String getImo() {
		return imo;
	}

	public void setImo(String imo) {
		this.imo = imo;
	}

	public String getShiptype() {
		return shiptype;
	}

	public void setShiptype(String shiptype) {
		this.shiptype = shiptype;
	}

	public String getLength() {
		return length;
	}

	public void setLength(String length) {
		this.length = length;
	}

	public String getBreadth() {
		return breadth;
	}

	public void setBreadth(String breadth) {
		this.breadth = breadth;
	}

	public String getEta() {
		return eta;
	}

	public void setEta(String eta) {
		this.eta = eta;
	}

	public String getDest_port() {
		return dest_port;
	}

	public void setDest_port(String dest_port) {
		this.dest_port = dest_port;
	}

	public String getDraught() {
		return draught;
	}

	public void setDraught(String draught) {
		this.draught = draught;
	}

}
