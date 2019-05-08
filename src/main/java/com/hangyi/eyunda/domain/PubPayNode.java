package com.hangyi.eyunda.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PubPayNode")
public class PubPayNode extends BaseEntity {
	private static final long serialVersionUID = -1L;

	@Column(nullable = false, length = 4)
	private String nodeCode = ""; // 省份编码
	@Column(nullable = false, length = 60)
	private String nodeName = ""; // 省份名称

	public String getNodeCode() {
		return nodeCode;
	}

	public void setNodeCode(String nodeCode) {
		this.nodeCode = nodeCode;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

}
