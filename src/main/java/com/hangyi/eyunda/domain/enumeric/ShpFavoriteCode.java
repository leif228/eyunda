package com.hangyi.eyunda.domain.enumeric;

public enum ShpFavoriteCode {
	canfavorite("可收藏"), 
	
	cantfavorite("不可收藏"), 

	cancancel("可取消收藏"),
	
	cantcancel("不可取消收藏");

	private String description;

	private ShpFavoriteCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}
}
