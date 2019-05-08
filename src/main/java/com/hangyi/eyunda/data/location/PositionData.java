package com.hangyi.eyunda.data.location;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.YydShipPosition;

public class PositionData extends BaseData {
	private static final long serialVersionUID = -1L;

	private String lng = ""; // 经度
	private String lat = ""; // 纬度
	private String tag = "12"; // 第一个字符1东经2西经度，第二个字符1南纬2北纬
	private String direction = "0.0"; // 方向0.0正北，0.0-359.9,按照顺时针方向定位
	private String time = "";//时间
	private String shipId = "";//船舶ID
	private String pic = "";

	public PositionData(){
		this.lng = "";
		this.lat = "";
		this.tag = "12";
		this.direction = "0.0";
		this.shipId = "";
		this.time = "";
		this.pic = "";
	}
	public PositionData(String shipId,String x,String y,String direction,String time,String pic){
		this.lng = x;
		this.lat = y;
		this.tag = "12";
		this.direction = direction;
		this.time = time;
		this.shipId = shipId;
		this.pic = pic;
	}
	public PositionData(YydShipPosition ysp){
		this.lng = ysp.getBaiduLng();
		this.lat = ysp.getBaiduLat();
		String str = ysp.getDaData();
		String dInt = str.substring(46, 49);
		String dFloat = str.substring(49, 50);
		this.tag = str.substring(44, 46);
		this.direction = dInt + "." + dFloat;
		this.setTime(str.substring(12, 26));
		this.setShipId(ysp.getCbZID());
		this.setPic("");// 生成图片访问地址
	}
	
	public String getLng() {
		return lng;
	}


	public void setLng(String lng) {
		this.lng = lng;
	}


	public String getLat() {
		return lat;
	}


	public void setLat(String lat) {
		this.lat = lat;
	}


	public String getTag() {
		return tag;
	}


	public void setTag(String tag) {
		this.tag = tag;
	}


	public String getDirection() {
		return direction;
	}


	public void setDirection(String direction) {
		this.direction = direction;
	}


	public String getShipId() {
		return shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getPic() {
		return pic;
	}
	public void setPic(String pic) {
		this.pic = pic;
	}

}
