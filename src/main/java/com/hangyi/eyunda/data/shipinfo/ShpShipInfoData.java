package com.hangyi.eyunda.data.shipinfo;

import java.util.List;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.domain.enumeric.ShipTypeCode;

public class ShpShipInfoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private Long createrUserId = 0L; // 
	private ShipTypeCode shipType = ShipTypeCode.cargoShip; // 船类
	private String masterName = ""; // 船舶所有人
	private String masterTel = ""; // 船舶所有人电话
	private String operatorName = ""; // 船上业务员
	private String operatorTel = ""; // 船上业务员电话
	private String shipLogo = ""; // 船舶Logo
	private String shipSmallLogo = ""; // 船舶Logo缩略图
	private String shipName = ""; // 船舶名称
	private String mmsi = ""; // MMSI编号
	private String shipDesc = ""; // 船舶描述
	private String shipPort = ""; // 船籍港
	private String builtDate = ""; // 建造日期
	private String power = ""; // 主机功率
	private Double minFreeboard = 0.00D; // 最小干舷
	private Double length = 0.00D; // 船长
	private Double breadth = 0.00D; // 船宽
	private Double depth = 0.00D; // 型深
	private Double draught = 0.00D; // 吃水深度
	private Double height = 0.00D; // 进孔高度
	private Integer sumTons = 0; // 总吨
	private Integer cleanTons = 0; // 净吨
	private Integer aaTons = 0; // 载重A级
	private Integer bbTons = 0; // 载重B级
	private Integer fullContainer = 0; // 重箱(TEU)
	private Integer spaceContainer = 0; // 吉箱(TEU)
	private Integer halfContainer = 0; // 半重箱(TEU)
	private String createTime = ""; // 建立时间

	private String sailArea = ""; // 船舶航区
	private String bonnet = ""; // 前或后置驾驶
	private String cabinMonitor = ""; // 已安装航易科技的货舱监控

	private Long certCount = 0L;

	private HyqUserData createrUserData = null;
	private List<ShpShipAttaData> shipAttaDatas = null;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCreaterUserId() {
		return createrUserId;
	}

	public void setCreaterUserId(Long createrUserId) {
		this.createrUserId = createrUserId;
	}

	public HyqUserData getCreaterUserData() {
		return createrUserData;
	}

	public void setCreaterUserData(HyqUserData createrUserData) {
		this.createrUserData = createrUserData;
	}

	public ShipTypeCode getShipType() {
		return shipType;
	}

	public void setShipType(ShipTypeCode shipType) {
		this.shipType = shipType;
	}

	public String getMasterName() {
		return masterName;
	}

	public void setMasterName(String masterName) {
		this.masterName = masterName;
	}

	public String getMasterTel() {
		return masterTel;
	}

	public void setMasterTel(String masterTel) {
		this.masterTel = masterTel;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getOperatorTel() {
		return operatorTel;
	}

	public void setOperatorTel(String operatorTel) {
		this.operatorTel = operatorTel;
	}

	public String getShipLogo() {
		return shipLogo;
	}

	public void setShipLogo(String shipLogo) {
		this.shipLogo = shipLogo;
	}

	public String getShipSmallLogo() {
		return shipSmallLogo;
	}

	public void setShipSmallLogo(String shipSmallLogo) {
		this.shipSmallLogo = shipSmallLogo;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getShipDesc() {
		return shipDesc;
	}

	public void setShipDesc(String shipDesc) {
		this.shipDesc = shipDesc;
	}

	public String getShipPort() {
		return shipPort;
	}

	public void setShipPort(String shipPort) {
		this.shipPort = shipPort;
	}

	public String getBuiltDate() {
		return builtDate;
	}

	public void setBuiltDate(String builtDate) {
		this.builtDate = builtDate;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public Double getLength() {
		return length;
	}

	public void setLength(Double length) {
		this.length = length;
	}

	public Double getBreadth() {
		return breadth;
	}

	public void setBreadth(Double breadth) {
		this.breadth = breadth;
	}

	public Double getDepth() {
		return depth;
	}

	public void setDepth(Double depth) {
		this.depth = depth;
	}

	public Double getDraught() {
		return draught;
	}

	public void setDraught(Double draught) {
		this.draught = draught;
	}

	public Double getHeight() {
		return height;
	}

	public void setHeight(Double height) {
		this.height = height;
	}

	public Integer getSumTons() {
		return sumTons;
	}

	public void setSumTons(Integer sumTons) {
		this.sumTons = sumTons;
	}

	public Integer getCleanTons() {
		return cleanTons;
	}

	public void setCleanTons(Integer cleanTons) {
		this.cleanTons = cleanTons;
	}

	public Integer getAaTons() {
		return aaTons;
	}

	public void setAaTons(Integer aaTons) {
		this.aaTons = aaTons;
	}

	public Integer getBbTons() {
		return bbTons;
	}

	public void setBbTons(Integer bbTons) {
		this.bbTons = bbTons;
	}

	public Integer getFullContainer() {
		return fullContainer;
	}

	public void setFullContainer(Integer fullContainer) {
		this.fullContainer = fullContainer;
	}

	public Integer getSpaceContainer() {
		return spaceContainer;
	}

	public void setSpaceContainer(Integer spaceContainer) {
		this.spaceContainer = spaceContainer;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public List<ShpShipAttaData> getShipAttaDatas() {
		return shipAttaDatas;
	}

	public void setShipAttaDatas(List<ShpShipAttaData> shipAttaDatas) {
		this.shipAttaDatas = shipAttaDatas;
	}

	public Double getMinFreeboard() {
		return minFreeboard;
	}

	public void setMinFreeboard(Double minFreeboard) {
		this.minFreeboard = minFreeboard;
	}

	public Integer getHalfContainer() {
		return halfContainer;
	}

	public void setHalfContainer(Integer halfContainer) {
		this.halfContainer = halfContainer;
	}

	public String getSailArea() {
		return sailArea;
	}

	public void setSailArea(String sailArea) {
		this.sailArea = sailArea;
	}

	public String getBonnet() {
		return bonnet;
	}

	public void setBonnet(String bonnet) {
		this.bonnet = bonnet;
	}

	public String getCabinMonitor() {
		return cabinMonitor;
	}

	public void setCabinMonitor(String cabinMonitor) {
		this.cabinMonitor = cabinMonitor;
	}

	public Long getCertCount() {
		return certCount;
	}

	public void setCertCount(Long certCount) {
		this.certCount = certCount;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
