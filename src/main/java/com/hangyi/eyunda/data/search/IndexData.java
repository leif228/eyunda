package com.hangyi.eyunda.data.search;

import java.io.Serializable;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;

public class IndexData implements Serializable {
	private static final long serialVersionUID = -1L;

	public static final String SHIPID = "shipId";
	public static final String MMSI = "mmsi";
	public static final String SHIPTYPE = "shipType";
	public static final String SHIPNAME = "shipName";
	public static final String ENGLISHNAME = "englishName";
	public static final String TRUENAME = "trueName";
	public static final String NICKNAME = "nickName";

	private String shipId = "";// 船舶ID,不分词索引存储
	private String mmsi = "";// 船舶MMSI,不分词索引存储
	private String shipType = "";// 船类,分词索引存储
	private String shipName = "";// 船名,分词索引存储
	private String englishName = "";// 船舶英文名,不分词索引存储
	private String trueName = "";// 承运人姓名,不分词索引存储
	private String nickName = "";// 承运人昵称,不分词索引存储

	public Document toDocument() {
		Document document = new Document();

		Field shipIdField = new StringField(SHIPID, shipId, Store.YES);
		Field mmsiField = new StringField(MMSI, mmsi, Store.YES);
		Field shipTypeField = new TextField(SHIPTYPE, shipType, Store.YES);
		Field shipNameField = new TextField(SHIPNAME, shipName, Store.YES);
		Field englishNameField = new TextField(ENGLISHNAME, englishName, Store.YES);
		Field trueNameField = new StringField(TRUENAME, trueName, Store.YES);
		Field nickNameField = new StringField(NICKNAME, nickName, Store.YES);

		document.add(shipIdField);
		document.add(mmsiField);
		document.add(shipTypeField);
		document.add(shipNameField);
		document.add(englishNameField);
		document.add(trueNameField);
		document.add(nickNameField);

		return document;
	}

	public String getShipId() {
		return shipId;
	}

	public void setShipId(String shipId) {
		this.shipId = shipId;
	}

	public String getMmsi() {
		return mmsi;
	}

	public void setMmsi(String mmsi) {
		this.mmsi = mmsi;
	}

	public String getShipType() {
		return shipType;
	}

	public void setShipType(String shipType) {
		this.shipType = shipType;
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

	public String getTrueName() {
		return trueName;
	}

	public void setTrueName(String trueName) {
		this.trueName = trueName;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
