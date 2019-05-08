package com.hangyi.eyunda.data.ship;

import java.util.ArrayList;
import java.util.List;

import com.hangyi.eyunda.data.BaseData;

public class TypeData extends BaseData {
private static final long serialVersionUID = -1L;
	
	private Long id = 0L; // ID
	
	private String typeCode = ""; // 类别编码
	
	private String typeName = ""; // 类别名称
	
	private TypeData parent = null; //上级类别编码
	
	private List<TypeData> childrenDatas = new ArrayList<TypeData>(); //下级类别名称

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTypeCode() {
		return typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public TypeData getParent() {
		return parent;
	}

	public void setParent(TypeData parent) {
		this.parent = parent;
	}

	public List<TypeData> getChildrenDatas() {
		return childrenDatas;
	}

	public void setChildrenDatas(List<TypeData> childrenDatas) {
		this.childrenDatas = childrenDatas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
