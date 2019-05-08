package com.hangyi.eyunda.domain.enumeric;

public enum ImgTypeCode {
	gif("gif"),
	jpeg("jpeg"),
	jpg("jpg"),
	png("png");


	private String description;

	private ImgTypeCode(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public static boolean contains(String ext) {
		ImgTypeCode[] imgCodes = ImgTypeCode.values();
		for(ImgTypeCode imgCode : imgCodes){
			if(imgCode.getDescription().equals(ext.toLowerCase())){
				return true;
			}
		}
		return false;
	}
}
