package com.hangyi.eyunda.data.shipmove;

import java.util.List;
import java.util.Map;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.MoveStateCode;

public class MovShipArvlftData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String mmsi = ""; // 船舶mmsi
	private MoveStateCode moveState = null; // 时间点类型
	private String arvlftTime = ""; // 时间点
	private String portName = ""; // 港口
	private String remark = ""; // 备注
	private String createTime = ""; // 上报时间

	private Map<String, Object> moveStateMap = null;

	private List<MovShipUpdownData> shipUpdownDatas = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public MoveStateCode getMoveState() {
		return moveState;
	}

	public void setMoveState(MoveStateCode moveState) {
		this.moveState = moveState;
		this.setMoveStateMap(moveState.getMap());
	}

	public Map<String, Object> getMoveStateMap() {
		return moveStateMap;
	}

	public void setMoveStateMap(Map<String, Object> moveStateMap) {
		this.moveStateMap = moveStateMap;
	}

	public String getArvlftTime() {
		return arvlftTime;
	}

	public void setArvlftTime(String arvlftTime) {
		this.arvlftTime = arvlftTime;
	}

	public String getPortName() {
		return portName;
	}

	public void setPortName(String portName) {
		this.portName = portName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<MovShipUpdownData> getShipUpdownDatas() {
		return shipUpdownDatas;
	}

	public void setShipUpdownDatas(List<MovShipUpdownData> shipUpdownDatas) {
		this.shipUpdownDatas = shipUpdownDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
