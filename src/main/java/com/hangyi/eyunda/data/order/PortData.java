package com.hangyi.eyunda.data.order;

import java.util.ArrayList;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.ship.PortCooordData;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;

public class PortData extends BaseData {
	private static final long serialVersionUID = -1L;

	private ScfPortCityCode portCity = null;
	private String portNo = ""; // 港口编码
	private String portName = ""; // 港口名称
	private Double latitude = 0.00D; // 纬度
	private Double longitude = 0.00D; // 经度
	private String fullName = ""; // 港口全名
	
	private List<PortCooordData> portCooordDatas = new ArrayList<PortCooordData>();

	public List<PortCooordData> getPortCooordDatas() {
		return portCooordDatas;
	}

	public void setPortCooordDatas(List<PortCooordData> portCooordDatas) {
		this.portCooordDatas = portCooordDatas;
	}

	public String getFullName() {
		if (portCity == null)
			fullName = "";
		else if ("".equals(portName))
			fullName = "";
		else
			fullName = portCity.getDescription() + "." + portName;

		return fullName;
	}

	public ScfPortCityCode getPortCity() {
		return portCity;
	}

	public void setPortCity(ScfPortCityCode portCity) {
		this.portCity = portCity;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
