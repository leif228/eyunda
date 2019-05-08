package com.hangyi.eyunda.domain;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.enumeric.ArvlftCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

@Entity
@Table(name = "YydShipArvlft")
public class YydShipArvlft extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String mmsi = ""; // 船舶mmsi
	@Column
	private Calendar createTime = Calendar.getInstance(); // 上报时间
	@Column
	private Calendar arvlftTime = Calendar.getInstance(); // 到离时间
	@Enumerated(EnumType.ORDINAL)
	private ArvlftCode arvlft = null; // 到离状态
	@Column(nullable = false, length = 8)
	private String portNo = ""; // 到离港口
	@Column
	private Double distance = 0D; // 到港时输入的航次航行航程km
	@Column(nullable = false, length = 200)
	private String sailLineData = ""; // 航次航行坐标数据文件
	@Column(nullable = false, length = 8)
	private String goPortNo = ""; // 将去港口
	@Column(nullable = true, length = 200)
	private String remark = ""; // 备注
	@Enumerated(EnumType.ORDINAL)
	private YesNoCode status = YesNoCode.no; // 状态

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	/**
	 * 取得上报时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置上报时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得到离时间
	 */
	public Calendar getArvlftTime() {
		return arvlftTime;
	}

	/**
	 * 设置到离时间
	 */
	public void setArvlftTime(Calendar arvlftTime) {
		this.arvlftTime = arvlftTime;
	}

	/**
	 * 取得到离状态
	 */
	public ArvlftCode getArvlft() {
		return arvlft;
	}

	/**
	 * 设置到离状态
	 */
	public void setArvlft(ArvlftCode arvlft) {
		this.arvlft = arvlft;
	}

	/**
	 * 取得到离港口
	 */
	public String getPortNo() {
		return portNo;
	}

	/**
	 * 设置到离港口
	 */
	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	/**
	 * 取得将去港口
	 */
	public String getGoPortNo() {
		return goPortNo;
	}

	/**
	 * 设置将去港口
	 */
	public void setGoPortNo(String goPortNo) {
		this.goPortNo = goPortNo;
	}

	/**
	 * 取得备注
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * 设置备注
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	public YesNoCode getStatus() {
		return status;
	}

	public void setStatus(YesNoCode status) {
		this.status = status;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public String getSailLineData() {
		return sailLineData;
	}

	public void setSailLineData(String sailLineData) {
		this.sailLineData = sailLineData;
	}

}
