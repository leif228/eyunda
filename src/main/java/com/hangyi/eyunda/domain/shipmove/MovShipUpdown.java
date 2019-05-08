package com.hangyi.eyunda.domain.shipmove;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import com.hangyi.eyunda.domain.BaseEntity;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.MeasureCode;

@Entity
@Table(name = "MovShipUpdown")
public class MovShipUpdown extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column
	private Long arvlftId = 0L; // 到离ID
	@Enumerated(EnumType.ORDINAL)
	private CargoTypeCode cargoType = CargoTypeCode.container20e; // 货类
	@Column(nullable = false, length = 50)
	private String cargoName = ""; // 货名
	@Enumerated(EnumType.ORDINAL)
	private MeasureCode measure = null; // 计量单位
	@Column
	private Integer tonTeu = 0; // 货量或箱量
	@Column
	private Calendar createTime = Calendar.getInstance(); // 建立时间

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
