package com.hangyi.eyunda.controller.hyquan.hyqh5.shipinfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpCertShowRecordData;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipAttaData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipinfo.ShpUserShipData;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShipTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpFavoriteCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertShowRecordService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipAttaService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpUserShipService;

@Controller
@RequestMapping("/hyqh5/shipinfo")
public class ShpShipInfoController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpShipAttaService shipAttaService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpUserShipService userShipService;
	@Autowired
	private ShpCertShowRecordService recordService;

	@RequestMapping(value = "/home", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView home(Page<ShpUserShipData> pageData, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		pageData = userShipService.getFavoritePageByUserId(userData.getId(), pageData);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/home");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);

		return mav;
	}

	@RequestMapping(value = "/favoriteShipPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void favoriteShipPage(Page<ShpUserShipData> pageData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = userShipService.getFavoritePageByUserId(userData.getId(), pageData);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}
	
	//用于将大图转小图，临时使用
	@RequestMapping(value = "/btos", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView btos(Page<ShpUserShipData> pageData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		HyqUserData userData = this.getLoginUserData(request, response);
		boolean used = true;
		
		if(used){
			userShipService.btos(userData.getId());
		}
		pageData = userShipService.getMyManagePageByUserId(userData.getId(), pageData);
		
		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/myShipList");
		
		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);
		
		return mav;
	}

	@RequestMapping(value = "/myShipList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView myShipList(Page<ShpUserShipData> pageData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		pageData = userShipService.getMyManagePageByUserId(userData.getId(), pageData);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/myShipList");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);

		return mav;
	}

	@RequestMapping(value = "/myShipPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void myShipPage(Page<ShpUserShipData> pageData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = userShipService.getMyManagePageByUserId(userData.getId(), pageData);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/searchShipList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView searchShipList(String keywords, Page<ShpShipInfoData> pageData, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		pageData = shipInfoService.getSearchPageByKeywords(keywords, pageData);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/searchShipList");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);
		mav.addObject("keywords", keywords);

		return mav;
	}

	@RequestMapping(value = "/searchShipPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void searchShipPage(String keywords, Page<ShpShipInfoData> pageData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			@SuppressWarnings("unused")
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = shipInfoService.getSearchPageByKeywords(keywords, pageData);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/certShowRecordList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView certShowRecordList(String keywords, Page<ShpCertShowRecordData> pageData,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		pageData = recordService.getPageData(userData.getId(), keywords, pageData);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/certShowRecord");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);
		mav.addObject("keywords", keywords);

		return mav;
	}

	@RequestMapping(value = "/certShowRecordPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void certShowRecordPage(String keywords, Page<ShpCertShowRecordData> pageData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = recordService.getPageData(userData.getId(), keywords, pageData);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/certWarnList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView certWarnList(Long shipId, CertTypeCode certType, Page<ShpCertificateData> pageData,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		pageData = certificateService.getPageDataForMaster(userData.getId(), shipId, certType, pageData);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/certWarn");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);
		mav.addObject("shipId", shipId);
		mav.addObject("certType", certType);

		return mav;
	}

	@RequestMapping(value = "/certWarnPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void certWarnPage(Long shipId, CertTypeCode certType, Page<ShpCertificateData> pageData,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			pageData = certificateService.getPageDataForMaster(userData.getId(), shipId, certType, pageData);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/shipCertEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipCertEdit(Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		ShpShipInfoData shipInfoData = null;
		if (shipId != null && shipId > 0) {
			if (!userShipService.canModify(userData.getId(), shipId))
				throw new Exception("错误！你没有该般舶资料的管理权。");

			shipInfoData = shipInfoService.getShipInfoDataById(shipId);
			if (shipInfoData == null)
				throw new Exception("错误！指定的般舶资料未找到。");
		} else {
			shipInfoData = new ShpShipInfoData();
		}

		List<ShpCertificateData> certificateDatas = certificateService.getCertificateDatas(shipId);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipCertEdit");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("certificateDatas", certificateDatas);
		mav.addObject("shipTypes", ShipTypeCode.values());
		mav.addObject("certTypes", CertTypeCode.values());

		return mav;
	}

	@RequestMapping(value = "/shipCertsShow", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipCertsShow(Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);
		if (shipInfoData == null)
			throw new Exception("错误！指定的般舶资料未找到。");

		ShpFavoriteCode canFavorite = userShipService.canFavorite(userData.getId(), shipId);

		List<ShpCertificateData> certificateDatas = certificateService.getCertificateDatas(shipId);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipCertsShow");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("certificateDatas", certificateDatas);
		mav.addObject("certTypes", CertTypeCode.values());
		mav.addObject("canFavorite", canFavorite);

		return mav;
	}

	@RequestMapping(value = "/deleteShip", method = { RequestMethod.GET })
	public void deleteShip(Long shipId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

			if (shipInfoData != null) {
				if (!userShipService.canModify(userData.getId(), shipId))
					throw new Exception("错误！你没有该般舶资料的修改权。");
				else {
					// 删除船舶
					boolean b = shipInfoService.deleteShipInfo(shipInfoData.getId());
					if (!b)
						throw new Exception("错误！删除船舶信息时出错。");
				}
			} else {
				throw new Exception("错误！船舶信息记录已经不存在。");
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/saveShip", method = { RequestMethod.GET, RequestMethod.POST })
	public void saveShip(ShpShipInfoData shipInfoData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpShipInfoData sd = shipInfoService.getShipInfoDataById(shipInfoData.getId());
			if (sd != null) {
				if (shipInfoData.getId().equals(0L))
					throw new Exception("错误！MMSI:" + shipInfoData.getMmsi() + "的船舶已经存在，请选择添加。");
				if (!userShipService.canModify(userData.getId(), shipInfoData.getId()))
					throw new Exception("错误！你没有该般舶资料的修改权。");

				shipInfoData.setCreaterUserId(sd.getCreaterUserId());
				
				shipInfoService.saveShipInfo(shipInfoData);
			} else {
				shipInfoData.setCreaterUserId(userData.getId());
				
				shipInfoService.saveShipInfo(shipInfoData);

				ShpUserShipData userShipData = new ShpUserShipData();
				userShipData.setUserId(userData.getId());
				userShipData.setShipId(shipInfoData.getId());
				userShipData.setRights(ShpRightsCode.managedShip);
				userShipService.saveUserShip(userShipData);
			}
			
			map.put("shipData", shipInfoData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/shipAttaImg", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipAttaImg(Long attaId, Long shipId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

		ShpShipAttaData shipAttaData = shipAttaService.getShipAttaData(attaId);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipAttaImg");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("shipAttaData", shipAttaData);

		return mav;
	}

	@RequestMapping(value = "/deleteShipAttaImg", method = { RequestMethod.GET })
	public void deleteShipAttaImg(Long attaId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpShipAttaData shipAttaData = shipAttaService.getShipAttaData(attaId);
			if (shipAttaData == null)
				throw new Exception("错误！指定的船舶图片未找到。");

			ShpShipInfoData shipInfoData = shipInfoService
					.getShipInfoData(shipInfoService.get(shipAttaData.getShipId()));
			if (!userShipService.canModify(userData.getId(), shipInfoData.getId()))
				throw new Exception("错误！你没有该般舶资料的修改权。");

			boolean b = shipAttaService.deleteShipAtta(attaId);
			if (b) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "删除失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/uploadShipLogo", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadShipLogo(Long shipId, MultipartFile shipLogoFile, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), shipId))
				throw new Exception("错误！你没有该般舶资料的修改权。");

			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);
			String url = shipInfoService.uploadShipLogo(shipInfoData.getId(), shipLogoFile);

			map.put(JsonResponser.CONTENT, url);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/uploadShipAttaImg", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadShipAttaImg(ShpShipAttaData shipAttaData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpShipInfoData shipInfoData = shipInfoService
					.getShipInfoData(shipInfoService.get(shipAttaData.getShipId()));
			if (!userShipService.canModify(userData.getId(), shipInfoData.getId()))
				throw new Exception("错误！你没有该般舶资料的修改权。");

			shipAttaService.saveShipAtta(shipAttaData);

			map.put(JsonResponser.CONTENT, shipAttaData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

}
