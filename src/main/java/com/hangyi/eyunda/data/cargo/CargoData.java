package com.hangyi.eyunda.data.cargo;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.order.PortData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.util.CalendarUtil;

public class CargoData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long publisherId = 0L; // 发布人ID
	private UserData publisher = null; // 发布人

	private String cargoImage = ""; // 货物图片
	private MultipartFile cargoImageFile = null;

	private String startPortNo = ""; // 装货港
	private PortData startPortData = null;
	private String endPortNo = ""; // 卸货港
	private PortData endPortData = null;

	private CargoTypeCode cargoType = null; // 货类
	private String cargoNames = ""; // 货名或规格
	private String tonTeus = ""; // 重量或数量
	private String prices = ""; // 运费报价

	private Map<Integer, String> mapCargoNames = null;// 货名或规格
	private Map<Integer, Integer> mapTonTeus = null;// 重量或数量
	private Map<Integer, Double> mapPrices = null;// 运价

	private Double transFee = 0.00D; // 运费（元）

	private String description = ""; // 货物描述

	private ReleaseStatusCode status = ReleaseStatusCode.unpublish; // 发布状态

	private String createTime = CalendarUtil.toYYYY_MM_DD_HH_MM_SS(CalendarUtil.now()); // 修改时间

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPublisherId() {
		return publisherId;
	}

	public void setPublisherId(Long publisherId) {
		this.publisherId = publisherId;
	}

	public UserData getPublisher() {
		return publisher;
	}

	public void setPublisher(UserData publisher) {
		this.publisher = publisher;
	}

	public String getCargoImage() {
		return cargoImage;
	}

	public void setCargoImage(String cargoImage) {
		this.cargoImage = cargoImage;
	}

	public MultipartFile getCargoImageFile() {
		return cargoImageFile;
	}

	public void setCargoImageFile(MultipartFile cargoImageFile) {
		this.cargoImageFile = cargoImageFile;
	}

	public String getStartPortNo() {
		return startPortNo;
	}

	public void setStartPortNo(String startPortNo) {
		this.startPortNo = startPortNo;
	}

	public PortData getStartPortData() {
		return startPortData;
	}

	public void setStartPortData(PortData startPortData) {
		this.startPortData = startPortData;
	}

	public String getEndPortNo() {
		return endPortNo;
	}

	public void setEndPortNo(String endPortNo) {
		this.endPortNo = endPortNo;
	}

	public PortData getEndPortData() {
		return endPortData;
	}

	public void setEndPortData(PortData endPortData) {
		this.endPortData = endPortData;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoNames() {
		return cargoNames;
	}

	public void setCargoNames(String cargoNames) {
		this.cargoNames = cargoNames;
	}

	public String getTonTeus() {
		return tonTeus;
	}

	public void setTonTeus(String tonTeus) {
		this.tonTeus = tonTeus;
	}

	public String getPrices() {
		return prices;
	}

	public void setPrices(String prices) {
		this.prices = prices;
	}

	public Map<Integer, String> getMapCargoNames() {
		return mapCargoNames;
	}

	public void setMapCargoNames(Map<Integer, String> mapCargoNames) {
		this.mapCargoNames = mapCargoNames;
	}

	public Map<Integer, Integer> getMapTonTeus() {
		return mapTonTeus;
	}

	public void setMapTonTeus(Map<Integer, Integer> mapTonTeus) {
		this.mapTonTeus = mapTonTeus;
	}

	public Map<Integer, Double> getMapPrices() {
		return mapPrices;
	}

	public void setMapPrices(Map<Integer, Double> mapPrices) {
		this.mapPrices = mapPrices;
	}

	public Double getTransFee() {
		return transFee;
	}

	public void setTransFee(Double transFee) {
		this.transFee = transFee;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ReleaseStatusCode getStatus() {
		return status;
	}

	public void setStatus(ReleaseStatusCode status) {
		this.status = status;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
