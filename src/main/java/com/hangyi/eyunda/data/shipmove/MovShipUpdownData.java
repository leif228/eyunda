package com.hangyi.eyunda.data.shipmove;

import com.hangyi.eyunda.data.BaseData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.MeasureCode;

public class MovShipUpdownData extends BaseData {
	private static final long serialVersionUID = -1L;

	private Long id = 0L; // ID

	private Long arvlftId = 0L; // 到离ID
	private CargoTypeCode cargoType = CargoTypeCode.container20e; // 货类
	private String cargoName = ""; // 货名
	private MeasureCode measure = null; // 计量单位
	private Integer tonTeu = 0; // 货量或箱量
	private String createTime = ""; // 建立时间

	public MovShipUpdownData() {
		cargoName = CargoTypeCode.container20e.getShortDesc();
	}

	public String getDescription() {
		return cargoType.getCargoBigType().getDescription() + "." + cargoName + " : " + tonTeu
				+ measure.getDescription();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getArvlftId() {
		return arvlftId;
	}

	public void setArvlftId(Long arvlftId) {
		this.arvlftId = arvlftId;
	}

	public CargoTypeCode getCargoType() {
		return cargoType;
	}

	public void setCargoType(CargoTypeCode cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public MeasureCode getMeasure() {
		return measure;
	}

	public void setMeasure(MeasureCode measure) {
		this.measure = measure;
	}

	public Integer getTonTeu() {
		return tonTeu;
	}

	public void setTonTeu(Integer tonTeu) {
		this.tonTeu = tonTeu;
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
