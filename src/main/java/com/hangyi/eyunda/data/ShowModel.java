package com.hangyi.eyunda.data;
public class ShowModel {
	public Long id;
	public String name;
	public String num;
	public String descrip;
	public String oilfee;
	public String dayfee;
	public String logo;
	public Long getId(){
		return id;
	}
	public void setId(Long id){
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getDescrip() {
		return descrip;
	}
	public void setDescrip(String descrip) {
		this.descrip = descrip;
	}
	public String getOilfee() {
		return oilfee;
	}
	public void setOilfee(String oilfee) {
		this.oilfee = oilfee;
	}
	public String getDayfee() {
		return dayfee;
	}
	public void setDayfee(String dayfee) {
		this.dayfee = dayfee;
	}
	public String getLogo(){
		return logo;
	}
	public void setLogo(String logo){
		this.logo = logo;
	}
}
