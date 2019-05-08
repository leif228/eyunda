package com.hangyi.eyunda.data.ship;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.ArvlftCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;

public class ShipArvlftData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private String mmsi = ""; // MMSI编号
	private String createTime = ""; // 上报时间
	private String arvlftTime = ""; // 到离时间
	private ArvlftCode arvlft = null; // 到离状态

	private String portNo = ""; // 到离港口
	private PortData portData = null;
	private String goPortNo = ""; // 将去港口
	private PortData goPortData = null;
	private YesNoCode status = null;

	private String remark = ""; // 备注
	private String arvlftDesc = "未上报"; // 船舶动态

	private String sailLineData = ""; // 航次航行坐标数据文件

	private Double distance = 0D; // 到港Data中存放航行航程km
	private Double jobDistance = 0D; // 离港Data中存放港作航程km

	private String startTime = ""; // 肮行开始时间
	private String endTime = ""; // 肮行结束时间
	private String jobStartTime = ""; // 港作开始时间
	private String jobEndTime = ""; // 港作结束时间

	private Integer hours = 0;
	private Integer minutes = 0;

	private Integer jobHours = 0;
	private Integer jobMinutes = 0;

	private List<ShipUpdownData> shipUpdownDatas = null; // 装卸货物列表

	public YesNoCode getStatus() {
		return status;
	}

	public void setStatus(YesNoCode status) {
		this.status = status;
	}

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

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getArvlftTime() {
		return arvlftTime;
	}

	public void setArvlftTime(String arvlftTime) {
		this.arvlftTime = arvlftTime;
	}

	public ArvlftCode getArvlft() {
		return arvlft;
	}

	public void setArvlft(ArvlftCode arvlft) {
		this.arvlft = arvlft;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}

	public PortData getPortData() {
		return portData;
	}

	public void setPortData(PortData portData) {
		this.portData = portData;
	}

	public String getGoPortNo() {
		return goPortNo;
	}

	public void setGoPortNo(String goPortNo) {
		this.goPortNo = goPortNo;
	}

	public PortData getGoPortData() {
		return goPortData;
	}

	public void setGoPortData(PortData goPortData) {
		this.goPortData = goPortData;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public List<ShipUpdownData> getShipUpdownDatas() {
		return shipUpdownDatas;
	}

	public void setShipUpdownDatas(List<ShipUpdownData> shipUpdownDatas) {
		this.shipUpdownDatas = shipUpdownDatas;
	}

	public String getArvlftDesc() {
		return arvlftDesc;
	}

	public void setArvlftDesc(String arvlftDesc) {
		this.arvlftDesc = arvlftDesc;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getJobStartTime() {
		return jobStartTime;
	}

	public void setJobStartTime(String jobStartTime) {
		this.jobStartTime = jobStartTime;
	}

	public String getJobEndTime() {
		return jobEndTime;
	}

	public void setJobEndTime(String jobEndTime) {
		this.jobEndTime = jobEndTime;
	}

	public Integer getJobHours() {
		return jobHours;
	}

	public void setJobHours(Integer jobHours) {
		this.jobHours = jobHours;
	}

	public Integer getJobMinutes() {
		return jobMinutes;
	}

	public void setJobMinutes(Integer jobMinutes) {
		this.jobMinutes = jobMinutes;
	}

	public Double getDistance() {
		return distance;
	}

	public void setDistance(Double distance) {
		this.distance = distance;
	}

	public Double getJobDistance() {
		return jobDistance;
	}

	public void setJobDistance(Double jobDistance) {
		this.jobDistance = jobDistance;
	}

	public String getSailLineData() {
		return sailLineData;
	}

	public void setSailLineData(String sailLineData) {
		this.sailLineData = sailLineData;
	}

	public Integer getHours() {
		return hours;
	}

	public void setHours(Integer hours) {
		this.hours = hours;
	}

	public Integer getMinutes() {
		return minutes;
	}

	public void setMinutes(Integer minutes) {
		this.minutes = minutes;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
