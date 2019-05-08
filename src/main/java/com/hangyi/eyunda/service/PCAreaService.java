package com.hangyi.eyunda.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hangyi.eyunda.dao.YydAreaDao;
import com.hangyi.eyunda.dao.YydCityDao;
import com.hangyi.eyunda.dao.YydProvinceDao;
import com.hangyi.eyunda.domain.YydArea;
import com.hangyi.eyunda.domain.YydCity;
import com.hangyi.eyunda.domain.YydProvince;

@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
public class PCAreaService {

	@Autowired
	private YydProvinceDao yydProvinceDao;
	@Autowired
	private YydCityDao yydCityDao;
	@Autowired
	private YydAreaDao yydAreaDao;

	public List<YydArea> getAreasByCityCode(String cityCode){
		List<YydArea> arra = yydAreaDao.getAreas(cityCode);
		return arra!=null?arra:new ArrayList<YydArea>();
	}
	public List<YydCity> getCitysByProCode(String proCode){
		List<YydCity> arrc = yydCityDao.getProCitys(proCode);
		return arrc!=null?arrc:new ArrayList<YydCity>();
	}
	public List<YydProvince> getAllProvince(){
		List<YydProvince> arrp = yydProvinceDao.getProvinces();
		return arrp!=null?arrp:new ArrayList<YydProvince>();
	}
	
	public List<YydProvince> getCurrAllProvince(String areaNo){
		List<YydProvince> arrp = yydProvinceDao.getProvinces();
		if (areaNo != null && (areaNo.length() == 6||areaNo.length() == 4)) {
			String provinceCode = areaNo.substring(0, 2);
			if(arrp!=null){
				YydProvince cp=null;
				for(YydProvince p:arrp){
					if(p.getProvinceNo().equals(provinceCode)){
						cp=arrp.remove(arrp.indexOf(p));
						break;
					}
				}
				if(cp!=null)
					arrp.add(0, cp);
			}
		}else if (areaNo != null && areaNo.length() == 2) {
			if(arrp!=null){
				YydProvince cp=null;
				for(YydProvince p:arrp){
					if(p.getProvinceNo().equals(areaNo)){
						cp=arrp.remove(arrp.indexOf(p));
						break;
					}
				}
				if(cp!=null)
					arrp.add(0, cp);
			}
		}
		return arrp!=null?arrp:new ArrayList<YydProvince>();
	}

	public List<YydCity> getCurrAllCitys(String areaNo) {
		
		if (areaNo != null && areaNo.length() == 6) {
			String pCode = areaNo.substring(0, 2);
			List<YydCity> citys = yydCityDao.getProCitys(pCode);
			if(citys!=null){
				YydCity curr = null;
				for(YydCity c:citys){
					if(c.getCityNo().equals(areaNo.substring(0, 4))){
						curr=citys.remove(citys.indexOf(c));
						break;
					}
				}
				if(curr!=null)
					citys.add(0, curr);
			}
			return citys;
		}else if(areaNo != null && areaNo.length() == 4){
			String pCode = areaNo.substring(0, 2);
			List<YydCity> citys = yydCityDao.getProCitys(pCode);
			if(citys!=null){
				YydCity curr = null;
				for(YydCity c:citys){
					if(c.getCityNo().equals(areaNo)){
						curr=citys.remove(citys.indexOf(c));
						break;
					}
				}
				citys.add(0, curr);
			}
			return citys;
		} else if (areaNo != null && areaNo.length() == 2) {
			return null;
		}
		return null;
	}
	
	public List<YydArea> getCurrAllArea(String areaNo) {
		
		if (areaNo != null && areaNo.length() == 6) {
			String cityCode = areaNo.substring(0, 4);
			List<YydArea> areas = yydAreaDao.getAreas(cityCode);
			if(areas!=null){
				YydArea ap = null;
				for(YydArea ca:areas){
					if(ca.getAreaNo().equals(areaNo)){
						ap = areas.remove(areas.indexOf(ca));
						break;
					}
				}
				if(ap!=null)
					areas.add(0, ap);
			}
			return areas;
		}else if(areaNo != null && areaNo.length() == 4){
			return null;
		} else if (areaNo != null && areaNo.length() == 2) {
			return null;
		}
		return null;
	}

}
