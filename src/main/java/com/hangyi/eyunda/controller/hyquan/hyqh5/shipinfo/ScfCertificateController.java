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
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.data.shipinfo.ShpCertAttaData;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertAttaService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpUserShipService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MD5;

@Controller
@RequestMapping("/scfreight/certificate")
public class ScfCertificateController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertAttaService certAttaService;
	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpUserShipService userShipService;

	@RequestMapping(value = "/certificateList", method = { RequestMethod.GET, RequestMethod.POST })
	public void certificateList(String checksum, Long userId, Long shipId, HttpServletRequest request,
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
			List<ShpCertificateData> certificateDatas = certificateService.getCertificateDatas(shipId);

			map.put("shipInfoData", shipInfoData);
			map.put("certificateDatas", certificateDatas);
			map.put("certTypes", CertTypeCode.values());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/getCertificate", method = { RequestMethod.GET, RequestMethod.POST })
	public void getCertificate(String checksum, Long userId, Long shipId, Long certId, HttpServletRequest request,
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

			ShpCertificateData certificateData = certificateService.getCertificateData(certId);
			if (certificateData == null) {
				certificateData = new ShpCertificateData();
				certificateData.setShipId(shipInfoData.getId());
			}

			map.put("shipInfoData", shipInfoData);
			map.put("certificateData", certificateData);
			map.put("certTypes", CertTypeCode.values());

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/deleteCertificate", method = { RequestMethod.GET })
	public void deleteCertificate(String checksum, Long userId, Long certId, HttpServletRequest request,
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

			ShpCertificateData certificateData = certificateService.getCertificateData(certId);
			if (certificateData == null)
				throw new Exception("错误！证书信息没找到。");

			if (!userShipService.canModify(userId, certificateData.getShipId(), ShpCertSysCode.scf))
				throw new Exception("警告！你没有该般舶资料的管理权。");

			certificateService.deleteCertificate(certId);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/saveCertificate", method = { RequestMethod.POST })
	public void saveCertificate(String checksum, Long userId, ShpCertificateData certificateData,
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

			if (!userShipService.canModify(userId, certificateData.getShipId(), ShpCertSysCode.scf))
				throw new Exception("警告！你没有该般舶资料的管理权。");

			boolean b = certificateService.saveCertificate(certificateData);
			if (b) {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存成功！");
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
				map.put(JsonResponser.MESSAGE, "保存失败！");
			}
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/deleteCertAttaImg", method = { RequestMethod.GET })
	public void deleteCertAttaImg(String checksum, Long userId, Long attaId, HttpServletRequest request,
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

			ShpCertAttaData certAttaData = certAttaService.getCertAttaData(attaId);
			if (certAttaData == null)
				throw new Exception("错误！证书页图片未找到。");

			ShpCertificateData certificateData = certificateService.getCertificateData(certAttaData.getCertId());
			if (certificateData == null)
				throw new Exception("错误！证书信息没找到。");

			if (!userShipService.canModify(userId, certificateData.getShipId(), ShpCertSysCode.scf))
				throw new Exception("警告！你没有该般舶资料的管理权。");

			boolean b = certAttaService.deleteCertAtta(attaId);
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

	@RequestMapping(value = "/uploadCertAttaImgs", method = { RequestMethod.GET, RequestMethod.POST })
	public void uploadCertAttaImgs(String checksum, Long userId, Long certId, int fileCount, HttpServletRequest request,
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

			// 多文件获取
			MultipartFile[] mpfs = null;
			if (fileCount > 0) {
				mpfs = new MultipartFile[fileCount];
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				for (int i = 0; i < fileCount; i++) {
					mpfs[i] = multipartRequest.getFile("imgFile" + i);
				}
			}

			ShpCertificateData certificateData = certificateService.getCertificateData(certId);
			if (certificateData == null)
				throw new Exception("错误！证书信息没找到。");

			if (!userShipService.canModify(userId, certificateData.getShipId(), ShpCertSysCode.scf))
				throw new Exception("警告！你没有该般舶资料的管理权。");

			ShpCertAttaData[] ds = certAttaService.saveCertAtta(certId, mpfs);

			map.put(JsonResponser.CONTENT, ds);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithText(response, map);
	}

}
