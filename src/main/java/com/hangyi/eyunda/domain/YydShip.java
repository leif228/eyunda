package com.hangyi.eyunda.domain;

import java.util.Calendar;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.hangyi.eyunda.domain.enumeric.ShipStatusCode;
import com.hangyi.eyunda.domain.enumeric.WarrantTypeCode;

@Entity
@Table(name = "YydShip", indexes = { 
		@Index(name = "idx_shipType", columnList = "shipType", unique = false),
		@Index(name = "idx_shipCode", columnList = "shipCode", unique = true),
		@Index(name = "idx_mmsi", columnList = "mmsi", unique = false),
		@Index(name = "idx_masterId", columnList = "masterId", unique = false)
		})
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class YydShip extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 20)
	private String shipType = ""; // 船舶类别编码
	@Column(nullable = true, length = 50)
	private String mmsi = ""; // MMSI编号
	@Column(nullable = false, length = 50)
	private String shipCode = ""; // 船舶编码
	@Column
	private Integer pointCount = 0; // 点击数
	@Column
	private Integer orderCount = 0; // 成交合同数
	@Column
	private Long masterId = 0L; // 船东
	@Column(nullable = false, length = 50)
	private String shipName = ""; // 船舶名称
	@Column(nullable = true, length = 50)
	private String englishName = ""; // 船舶英文名称

	@Column(nullable = true, length = 20)
	private String imo = ""; // IMO编号
	@Column(nullable = true, length = 20)
	private String callsign = ""; // 呼号
	@Column
	private Double length = 0.00D; // 船长
	@Column
	private Double breadth = 0.00D; // 船宽
	@Column
	private Double mouldedDepth = 0.00D; // 型深
	@Column
	private Double draught = 0.00D; // 吃水深度
	@Column
	private Integer sumTons = 0; // 总吨
	@Column
	private Integer cleanTons = 0; // 净吨
	@Column
	private Integer aTons = 0; // 载重A级(吨)
	@Column
	private Integer bTons = 0; // 载重B级(吨)
	@Column
	private Integer fullContainer = 0; // 重箱(TEU)
	@Column
	private Integer halfContainer = 0; // 半重箱(TEU)
	@Column
	private Integer spaceContainer = 0; // 吉箱(TEU)

	@Column(nullable = true, length = 500)
	private String keyWords = ""; // 航线说明
	@Column(nullable = true, length = 500)
	private String shipTitle = ""; // 摘要描述
	@Column(nullable = true, length = 200)
	private String shipDesc = ""; // 船舶详情
	@Column(nullable = true, length = 200)
	private String shipLogo = ""; // 船舶Logo

	@Enumerated(EnumType.ORDINAL)
	private WarrantTypeCode warrantType = WarrantTypeCode.personWarrant; // 委托类型
	@Column(nullable = true, length = 20)
	private String shipMaster = ""; // 船东姓名
	@Column(nullable = true, length = 200)
	private String idCardFront = ""; // 船东身份证正面/营业执照
	@Column(nullable = true, length = 200)
	private String idCardBack = ""; // 船东身份证反面/组织机构代码证
	@Column(nullable = true, length = 200)
	private String warrant = ""; // 船东委托书
	@Column(nullable = true, length = 200)
	private String certificate = ""; // 船舶运营证
	@Enumerated(EnumType.ORDINAL)
	private ShipStatusCode shipStatus = ShipStatusCode.edit; // 编辑状态

	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间
	@Column
	private Calendar releaseTime = Calendar.getInstance(); // 发布时间

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipId")
	private Set<YydShipCargoType> cargoTypes;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "shipId")
	private Set<YydShipPort> ports;

	/**
	 * 取得船舶类别编码
	 */
	public String getShipType() {
		return shipType;
	}

	/**
	 * 设置船舶类别编码
	 */
	public void setShipType(String shipType) {
		this.shipType = shipType;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	/**
	 * 取得船舶编码
	 */
	public String getShipCode() {
		return shipCode;
	}

	/**
	 * 设置船舶编码
	 */
	public void setShipCode(String shipCode) {
		this.shipCode = shipCode;
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

	public Long getMasterId() {
		return masterId;
	}

	public void setMasterId(Long masterId) {
		this.masterId = masterId;
	}

	/**
	 * 取得船舶名称
	 */
	public String getShipName() {
		return shipName;
	}

	/**
	 * 设置船舶名称
	 */
	public void setShipName(String shipName) {
		this.shipName = shipName;
	}

	public String getEnglishName() {
		return englishName;
	}

	public void setEnglishName(String englishName) {
		this.englishName = englishName;
	}

	/**
	 * 取得关键词
	 */
	public String getKeyWords() {
		return keyWords;
	}

	/**
	 * 设置关键词
	 */
	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	/**
	 * 取得摘要描述
	 */
	public String getShipTitle() {
		return shipTitle;
	}

	/**
	 * 设置摘要描述
	 */
	public void setShipTitle(String shipTitle) {
		this.shipTitle = shipTitle;
	}

	/**
	 * 取得船舶详情
	 */
	public String getShipDesc() {
		return shipDesc;
	}

	/**
	 * 设置船舶详情
	 */
	public void setShipDesc(String shipDesc) {
		this.shipDesc = shipDesc;
	}

	/**
	 * 取得船舶Logo
	 */
	public String getShipLogo() {
		return shipLogo;
	}

	/**
	 * 设置船舶Logo
	 */
	public void setShipLogo(String shipLogo) {
		this.shipLogo = shipLogo;
	}

	/**
	 * 取得建立时间
	 */
	public Calendar getCreateTime() {
		return createTime;
	}

	/**
	 * 设置建立时间
	 */
	public void setCreateTime(Calendar createTime) {
		this.createTime = createTime;
	}

	/**
	 * 取得发布时间
	 */
	public Calendar getReleaseTime() {
		return releaseTime;
	}

	/**
	 * 设置发布时间
	 */
	public void setReleaseTime(Calendar releaseTime) {
		this.releaseTime = releaseTime;
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

	public Set<YydShipCargoType> getCargoTypes() {
		return cargoTypes;
	}

	public void setCargoTypes(Set<YydShipCargoType> cargoTypes) {
		this.cargoTypes = cargoTypes;
	}

	public Set<YydShipPort> getPorts() {
		return ports;
	}

	public void setPorts(Set<YydShipPort> ports) {
		this.ports = ports;
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

}
