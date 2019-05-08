package com.hangyi.eyunda.data.search;

import java.io.Serializable;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.document.Field.Store;

public class CargoIndexData implements Serializable {
	private static final long serialVersionUID = -1L;

	public static final String ID = "id";// Id号
	public static final String CARGOTYPE = "cargoType";// 货类
	public static final String CARGONAME = "cargoName";// 货名
	public static final String STARTPORT = "startPort";// 装货港
	public static final String ENDPORT = "endPort";// 卸货港
	public static final String TRUENAME = "trueName";
	public static final String NICKNAME = "nickName";

	private String id = "";// Id号,不分词索引存储
	private String cargoType = null; // 货类,分词索引存储
	private String cargoName = ""; // 货名,分词索引存储
	private String startPort = ""; // 装货港,分词索引存储
	private String endPort = ""; // 卸货港,分词索引存储
	private String trueName = "";// 承运人姓名,不分词索引存储
	private String nickName = "";// 承运人昵称,不分词索引存储

	public Document toDocument() {
		Document document = new Document();

		Field idField = new StringField(ID, id, Field.Store.YES);
		Field cargoTypeField = new StringField(CARGOTYPE, cargoType, Field.Store.YES);
		Field cargoNameField = new StringField(CARGONAME, cargoName, Field.Store.YES);
		Field startPortField = new TextField(STARTPORT, startPort, Field.Store.YES);
		Field endPortField = new TextField(ENDPORT, endPort, Field.Store.YES);
		Field trueNameField = new StringField(TRUENAME, trueName, Store.YES);
		Field nickNameField = new StringField(NICKNAME, nickName, Store.YES);

		document.add(idField);
		document.add(cargoTypeField);
		document.add(cargoNameField);
		document.add(startPortField);
		document.add(endPortField);
		document.add(trueNameField);
		document.add(nickNameField);

		return document;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCargoType() {
		return cargoType;
	}

	public void setCargoType(String cargoType) {
		this.cargoType = cargoType;
	}

	public String getCargoName() {
		return cargoName;
	}

	public void setCargoName(String cargoName) {
		this.cargoName = cargoName;
	}

	public String getStartPort() {
		return startPort;
	}

	public void setStartPort(String startPort) {
		this.startPort = startPort;
	}

	public String getEndPort() {
		return endPort;
	}

	public void setEndPort(String endPort) {
		this.endPort = endPort;
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
