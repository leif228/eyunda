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

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.data.shipinfo.ShpUserShipData;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.ShpRightsCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpUserShipService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/scfreight/shipinfo")
public class ScfShipInfoController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpUserShipService userShipService;

	@RequestMapping(value = "/myShipPage", method = { RequestMethod.GET, RequestMethod.POST })
	public void myShipPage(String checksum, Long userId, Page<ShpUserShipData> pageData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 开始堵漏洞，进行加密验证
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
			String validchecksum = MD5.toMD5(today + userId + Constants.SCF_SALT_VALUE);
			// 校验合法性
			if (!validchecksum.equals(checksum))
				throw new Exception("错误！非法用户，请使用合法密钥。");
			// 结束堵漏洞，进行加密验证

			pageData = userShipService.getMyManagePageByUserId(userId, pageData, ShpCertSysCode.scf);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/shipCerts", method = { RequestMethod.GET, RequestMethod.POST })
	public void shipCerts(String checksum, Long userId, Long shipId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 开始堵漏洞，进行加密验证
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
			String validchecksum = MD5.toMD5(today + userId + Constants.SCF_SALT_VALUE);
			// 校验合法性
			if (!validchecksum.equals(checksum))
				throw new Exception("错误！非法用户，请使用合法密钥。");
			// 结束堵漏洞，进行加密验证

			if (!userShipService.canModify(userId, shipId, ShpCertSysCode.scf))
				throw new Exception("警告！你没有该般舶资料的管理权。");

			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);
			if (shipInfoData == null)
				throw new Exception("错误！指定的般舶资料未找到。");

			List<ShpCertificateData> certificateDatas = certificateService.getCertificateDatas(shipId);

			map.put("shipInfoData", shipInfoData);
			map.put("certificateDatas", certificateDatas);
			map.put("certTypes", CertTypeCode.values());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得船舶及证书列表成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/deleteShip", method = { RequestMethod.GET })
	public void deleteShip(String checksum, Long userId, Long shipId, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 开始堵漏洞，进行加密验证
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
			String validchecksum = MD5.toMD5(today + userId + Constants.SCF_SALT_VALUE);
			// 校验合法性
			if (!validchecksum.equals(checksum))
				throw new Exception("错误！非法用户，请使用合法密钥。");
			// 结束堵漏洞，进行加密验证

			ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

			if (shipInfoData != null) {
				if (!userShipService.canModify(userId, shipId, ShpCertSysCode.scf))
					throw new Exception("警告！你没有该般舶资料的管理权。");
				// 删除船舶
				boolean b = shipInfoService.deleteShipInfo(shipInfoData.getId());
				if (!b)
					throw new Exception("错误！删除船舶信息时出错。");
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
	public void saveShip(String checksum, Long userId, ShpShipInfoData shipInfoData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 开始堵漏洞，进行加密验证
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
			String validchecksum = MD5.toMD5(today + userId + Constants.SCF_SALT_VALUE);
			// 校验合法性
			if (!validchecksum.equals(checksum))
				throw new Exception("错误！非法用户，请使用合法密钥。");
			// 结束堵漏洞，进行加密验证

			ShpShipInfoData sd = shipInfoService.getShipInfoDataById(shipInfoData.getId());
			if (sd != null) {
				if (!userShipService.canModify(userId, sd.getId(), ShpCertSysCode.scf))
					throw new Exception("警告！你没有该般舶资料的管理权。");

				shipInfoService.saveShipInfo(shipInfoData);
			} else {
				shipInfoService.saveShipInfo(shipInfoData);

				ShpUserShipData userShipData = new ShpUserShipData();
				userShipData.setCertSys(ShpCertSysCode.scf);
				userShipData.setUserId(userId);
				userShipData.setShipId(shipInfoData.getId());
				userShipData.setRights(ShpRightsCode.managedShip);
				userShipService.saveUserShip(userShipData);
			}

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

	@RequestMapping(value = "/uploadShipLogo", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadShipLogo(String checksum, Long userId, Long shipId, MultipartFile shipLogoFile,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			// 开始堵漏洞，进行加密验证
			String today = CalendarUtil.toYYYY_MM_DD(CalendarUtil.now());
			String validchecksum = MD5.toMD5(today + userId + Constants.SCF_SALT_VALUE);
			// 校验合法性
			if (!validchecksum.equals(checksum))
				throw new Exception("错误！非法用户，请使用合法密钥。");
			// 结束堵漏洞，进行加密验证

			if (!userShipService.canModify(userId, shipId, ShpCertSysCode.scf))
				throw new Exception("警告！你没有该般舶资料的管理权。");

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

}
