package com.hangyi.eyunda.data.ship;

import java.util.Calendar;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShipMonitorPlantCode;
import com.hangyi.eyunda.domain.enumeric.ShipStatusCode;
import com.hangyi.eyunda.domain.enumeric.WarrantTypeCode;

public class ShipData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID
	private String mmsi = ""; // MMSI编号
	private String shipCode = ""; // 船舶编码
	private String shipName = ""; // 船舶名称
	private String englishName = ""; // 船舶英文名称

	private String imo = ""; // IMO编号
	private String callsign = ""; // 呼号
	private Double length = 0.00D; // 船长
	private Double breadth = 0.00D; // 船宽
	private Double mouldedDepth = 0.00D; // 型深
	private Double draught = 0.00D; // 吃水深度
	private Integer sumTons = 0; // 总吨
	private Integer cleanTons = 0; // 净吨
	private Integer aTons = 0; // 载重A级(吨)
	private Integer bTons = 0; // 载重B级(吨)
	private Integer fullContainer = 0; // 重箱(TEU)
	private Integer halfContainer = 0; // 半重箱(TEU)
	private Integer spaceContainer = 0; // 吉箱(TEU)

	private Integer pointCount = 0; // 点击数
	private Integer orderCount = 0; // 成交合同数
	private String keyWords = ""; // 航线说明
	private String shipTitle = ""; // 摘要描述
	private String shipLogo = ""; // 船舶Logo

	private WarrantTypeCode warrantType = WarrantTypeCode.personWarrant; // 委托类型
	private String shipMaster = ""; // 船东姓名
	private String idCardFront = ""; // 船东身份证正面
	private String idCardBack = ""; // 船东身份证反面
	private String warrant = ""; // 船东委托书
	private String certificate = ""; // 船舶运营证
	private ShipStatusCode shipStatus = ShipStatusCode.edit; // 编辑状态

	private ShipMonitorPlantCode shipPlant = ShipMonitorPlantCode.baochuanwang; // 船舶数据来源
	private String releaseTime = "";
	private Calendar createTime = Calendar.getInstance(); // 申请时间

	private Long masterId = 0L; // 船东ID
	private UserData master = null;

	private String shipType = ""; // ID
	private TypeData typeData = null;

	private MultipartFile shipLogoFile = null;
	private MultipartFile idCardFrontFile = null; // 船东身份证正面
	private MultipartFile idCardBackFile = null; // 船东身份证反面
	private MultipartFile warrantFile = null; // 船东委托书
	private MultipartFile certificateFile = null; // 船舶运营证

	private String arvlftDesc = "未上报"; // 船舶动态

	private List<ShipAttaData> myShipAttaDatas;// 证书
	private List<ShipArvlftData> shipArvlftDatas;// 动态
	private List<CargoTypeCode> shipCargoTypes;// 接货类别
	private List<ShipPortData> shipPortDatas;// 经营区域
	private List<AttrNameData> attrNameDatas;// 船舶属性

	public List<CargoTypeCode> getShipCargoTypes() {
		return shipCargoTypes;
	}

	public void setShipCargoTypes(List<CargoTypeCode> shipCargoTypes) {
		this.shipCargoTypes = shipCargoTypes;
	}

	public List<ShipPortData> getShipPortDatas() {
		return shipPortDatas;
	}

	public void setShipPortDatas(List<ShipPortData> shipPortDatas) {
		this.shipPortDatas = shipPortDatas;
	}

	public List<AttrNameData> getAttrNameDatas() {
		return attrNameDatas;
	}

	public void setAttrNameDatas(List<AttrNameData> attrNameDatas) {
		this.attrNameDatas = attrNameDatas;
	}

	public List<ShipAttaData> getMyShipAttaDatas() {
		return myShipAttaDatas;
	}

	public void setMyShipAttaDatas(List<ShipAttaData> myShipAttaDatas) {
		this.myShipAttaDatas = myShipAttaDatas;
	}

	public List<ShipArvlftData> getShipArvlftDatas() {
		return shipArvlftDatas;
	}

	public void setShipArvlftDatas(List<ShipArvlftData> shipArvlftDatas) {
		this.shipArvlftDatas = shipArvlftDatas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TypeData getTypeData() {
		return typeData;
	}

	public void setTypeData(TypeData typeData) {
		this.typeData = typeData;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getShipCode() {
		return shipCode;
	}

	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
	}

	public String getShipName() {
		return shipName;
	}

	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	public Double getMouldedDepth() {
		return mouldedDepth;
	}

	public void setMouldedDepth(Double mouldedDepth) {
		this.mouldedDepth = mouldedDepth;
	}

	public Integer getPointCount() {
		return pointCount;
	}

	public void setPointCount(Integer pointCount) {
		this.pointCount = pointCount;
	}

	public Integer getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(Integer orderCount) {
		this.orderCount = orderCount;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public String getShipTitle() {
		return shipTitle;
	}

	public void setShipTitle(String shipTitle) {
		this.shipTitle = shipTitle;
	}

	public String getShipLogo() {
		return shipLogo;
	}

	public void setShipLogo(String shipLogo) {
		this.shipLogo = shipLogo;
	}

	public ShipMonitorPlantCode getShipPlant() {
		return shipPlant;
	}

	public void setShipPlant(ShipMonitorPlantCode shipPlant) {
		this.shipPlant = shipPlant;
	}

	public String getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(String releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Calendar getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	public UserData getMaster() {
		return master;
	}

	public void setMaster(UserData master) {
		this.master = master;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public MultipartFile getShipLogoFile() {
		return shipLogoFile;
	}

	public void setShipLogoFile(MultipartFile shipLogoFile) {
		this.shipLogoFile = shipLogoFile;
	}

	public String getArvlftDesc() {
		return arvlftDesc;
	}

	public void setArvlftDesc(String arvlftDesc) {
		this.arvlftDesc = arvlftDesc;
	}

	public String getImo() {
		return imo;
	}

	public void setImo(String imo) {
		this.imo = imo;
	}

	public String getCallsign() {
		return callsign;
	}

	public void setCallsign(String callsign) {
		this.callsign = callsign;
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

	public Double getDraught() {
		return draught;
	}

	public void setDraught(Double draught) {
		this.draught = draught;
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

	public Integer getaTons() {
		return aTons;
	}

	public void setaTons(Integer aTons) {
		this.aTons = aTons;
	}

	public Integer getbTons() {
		return bTons;
	}

	public void setbTons(Integer bTons) {
		this.bTons = bTons;
	}

	public Integer getFullContainer() {
		return fullContainer;
	}

	public void setFullContainer(Integer fullContainer) {
		this.fullContainer = fullContainer;
	}

	public Integer getHalfContainer() {
		return halfContainer;
	}

	public void setHalfContainer(Integer halfContainer) {
		this.halfContainer = halfContainer;
	}

	public Integer getSpaceContainer() {
		return spaceContainer;
	}

	public void setSpaceContainer(Integer spaceContainer) {
		this.spaceContainer = spaceContainer;
	}

	public WarrantTypeCode getWarrantType() {
		return warrantType;
	}

	public void setWarrantType(WarrantTypeCode warrantType) {
		this.warrantType = warrantType;
	}

	public String getShipMaster() {
		return shipMaster;
	}

	public void setShipMaster(String shipMaster) {
		this.shipMaster = shipMaster;
	}

	public String getIdCardFront() {
		return idCardFront;
	}

	public void setIdCardFront(String idCardFront) {
		this.idCardFront = idCardFront;
	}

	public String getIdCardBack() {
		return idCardBack;
	}

	public void setIdCardBack(String idCardBack) {
		this.idCardBack = idCardBack;
	}

	public String getWarrant() {
		return warrant;
	}

	public void setWarrant(String warrant) {
		this.warrant = warrant;
	}

	public String getCertificate() {
		return certificate;
	}

	public void setCertificate(String certificate) {
		this.certificate = certificate;
	}

	public ShipStatusCode getShipStatus() {
		return shipStatus;
	}

	public void setShipStatus(ShipStatusCode shipStatus) {
		this.shipStatus = shipStatus;
	}

	public MultipartFile getIdCardFrontFile() {
		return idCardFrontFile;
	}

	public void setIdCardFrontFile(MultipartFile idCardFrontFile) {
		this.idCardFrontFile = idCardFrontFile;
	}

	public MultipartFile getIdCardBackFile() {
		return idCardBackFile;
	}

	public void setIdCardBackFile(MultipartFile idCardBackFile) {
		this.idCardBackFile = idCardBackFile;
	}

	public MultipartFile getWarrantFile() {
		return warrantFile;
	}

	public void setWarrantFile(MultipartFile warrantFile) {
		this.warrantFile = warrantFile;
	}

	public MultipartFile getCertificateFile() {
		return certificateFile;
	}

	public void setCertificateFile(MultipartFile certificateFile) {
		this.certificateFile = certificateFile;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
