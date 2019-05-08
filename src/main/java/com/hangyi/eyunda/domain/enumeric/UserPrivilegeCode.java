package com.hangyi.eyunda.domain.enumeric;

public enum UserPrivilegeCode {
	manager("管理员"), handler("业务员"), sailor("船员"), master("船东"), owner("货主");

	private String description;

	public String getDescription() {
		return description;
	}

	private UserPrivilegeCode(String description) {
		this.description = description;
	}

}
