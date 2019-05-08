package com.hangyi.eyunda.domain.shipmove;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.MoveStateCode;

@Entity
@Table(name = "MovShipArvlft")
public class MovShipArvlft extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String mmsi = ""; // 船舶mmsi
	@Enumerated(EnumType.ORDINAL)
	private MoveStateCode moveState = null; // 时间点类型
	@Column
	private Calendar arvlftTime = Calendar.getInstance(); // 时间点
	@Column(nullable = false, length = 20)
	private String portName = ""; // 港口
	@Column(nullable = true, length = 200)
	private String remark = ""; // 备注
	@Column
	private Calendar createTime = Calendar.getInstance(); // 上报时间

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
	}

	public Calendar getArvlftTime() {
		return arvlftTime;
	}

	public void setArvlftTime(Calendar arvlftTime) {
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

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
