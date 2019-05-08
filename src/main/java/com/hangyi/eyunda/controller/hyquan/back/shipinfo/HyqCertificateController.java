package com.hangyi.eyunda.controller.hyquan.back.shipinfo;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.domain.enumeric.AuditStatusCode;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/back/ship")
public class HyqCertificateController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertificateService certificateService;

	@RequestMapping(value = "/certificate", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView certificate(Page<Map<String, Object>> pageData, ShpCertSysCode certSys, String mmsi,
			CertTypeCode certType, YesNoCode beDeleted, HttpServletRequest request, HttpServletResponse response) {

		pageData.setPageSize(8);
		pageData = certificateService.getPageMap(pageData, certSys, mmsi, certType, beDeleted);

		if (!pageData.getResult().isEmpty()) {
			for (Map<String, Object> map : pageData.getResult()) {
				ShpCertificateData certData = (ShpCertificateData) map.get("cert");
				ShpShipInfoData shipData = (ShpShipInfoData) map.get("ship");

				String encodeContent = "http://" + request.getServerName() + ":" + request.getServerPort()
						+ request.getContextPath();
				encodeContent += "/hyqh5/shipinfo/shipCertShow?certId=" + certData.getId() + "&shipId="
						+ shipData.getId();

				String checksum = MD5.toMD5(encodeContent + Constants.SALT_VALUE);
				map.put("checksum", checksum);
			}
		}

		ModelAndView mav = new ModelAndView("/back/shipinfo/certificate");
		mav.addObject("pageData", pageData);
		mav.addObject("certSys", certSys);
		mav.addObject("mmsi", mmsi);
		mav.addObject("certType", certType);
		mav.addObject("beDeleted", beDeleted);
		mav.addObject("certSyss", ShpCertSysCode.values());
		mav.addObject("certTypes", CertTypeCode.values());
		mav.addObject("yesNos", YesNoCode.values());

		return mav;
	}

	@RequestMapping(value = "/certificate/audit", method = { RequestMethod.GET })
	public void audit(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			certificateService.auditCertificate(id, AuditStatusCode.audit);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "证书认证处理成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/certificate/unaudit", method = { RequestMethod.GET })
	public void unaudit(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			certificateService.auditCertificate(id, AuditStatusCode.unaudit);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "证书取消认证处理成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/certificate/delete", method = { RequestMethod.GET })
	public void delete(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			certificateService.deleteCertificate(id);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "证书删除成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

}
