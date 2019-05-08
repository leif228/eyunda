package com.hangyi.eyunda.controller.hyquan.hyqh5.shipinfo;

import java.io.File;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.controller.hyquan.HyqBaseController;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.hyquan.HyqUserData;
import com.hangyi.eyunda.data.shipinfo.ShpCertAttaData;
import com.hangyi.eyunda.data.shipinfo.ShpCertShowRecordData;
import com.hangyi.eyunda.data.shipinfo.ShpCertificateData;
import com.hangyi.eyunda.data.shipinfo.ShpShipInfoData;
import com.hangyi.eyunda.domain.enumeric.CertTypeCode;
import com.hangyi.eyunda.domain.enumeric.ReleaseStatusCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertAttaService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertShowRecordService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpShipInfoService;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpUserShipService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.FileMD5Util;
import com.hangyi.eyunda.util.MD5;
import com.hangyi.eyunda.util.qrcode.QRCodeUtil;

@Controller
@RequestMapping("/hyqh5/shipinfo")
public class ShpCertificateController extends HyqBaseController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ShpCertAttaService certAttaService;
	@Autowired
	private ShpCertificateService certificateService;
	@Autowired
	private ShpShipInfoService shipInfoService;
	@Autowired
	private ShpUserShipService userShipService;
	@Autowired
	private ShpCertShowRecordService recordService;

	@RequestMapping(value = "/certificateList", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView certificateList(Page<ShpCertificateData> pageData, String keywords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		certificateService.getPageDataByKeywords(userData.getId(), pageData, keywords);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/certificateList");

		mav.addObject("userData", userData);
		mav.addObject("pageData", pageData);
		mav.addObject("keywords", keywords);

		return mav;
	}

	@RequestMapping(value = "/certificatePage", method = { RequestMethod.GET, RequestMethod.POST })
	public void certificatePage(Page<ShpCertificateData> pageData, String keywords, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			certificateService.getPageDataByKeywords(userData.getId(), pageData, keywords);

			map.put(JsonResponser.CONTENT, pageData);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得下一页成功！");
		} catch (Exception e) {
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
	}

	@RequestMapping(value = "/shipCertShow", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView shipCertShow(String checksum, Long shipId, Long certId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);
		ShpCertificateData certificateData = certificateService.getCertificateData(certId);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/shipCertShow");
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("certificateData", certificateData);

		ShpCertShowRecordData recordData = new ShpCertShowRecordData();
		recordData.setShipId(shipId);
		recordData.setCertId(certId);

		// 要么是公开的，要么是有授权且没有过期的，要么是自己的，要么是分享的，否则对不起，你不能查看该证书
		String encodeContent = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		encodeContent += "/hyqh5/shipinfo/shipCertShow?certId=" + certificateData.getId() + "&shipId="
				+ shipInfoData.getId();
		String validchecksum = MD5.toMD5(encodeContent + Constants.SALT_VALUE);
		boolean isShared = validchecksum.equals(checksum);

		if (isShared) {
			// 分享的
			recordData.setUserId(0L);
			recordService.saveRecord(recordData);
			return mav;
		} else {
			HyqUserData userData = this.getLoginUserData(request, response);
			boolean hasSeeRights = userShipService.hasSeeRights(userData.getId(), shipId, certId);

			if (hasSeeRights) {
				// 自己的，或是有授权且没有过期的
				recordData.setUserId(userData.getId());
				recordService.saveRecord(recordData);
				return mav;
			} else {
				boolean isOpened = certificateData.getOpened() == YesNoCode.yes;
				if (isOpened) {
					// 公开的
					recordData.setUserId(userData.getId());
					recordService.saveRecord(recordData);
					return mav;
				} else {
					throw new Exception("对不起！你没有该般舶证书的查看权。");
				}
			}
		}
	}

	@RequestMapping(value = "/certificateEdit", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView certificateEdit(Long shipId, CertTypeCode certType, Long certId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		if (!userShipService.canModify(userData.getId(), shipId))
			throw new Exception("错误！你没有该般舶资料的修改权。");

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

		ShpCertificateData certificateData = certificateService.getCertificateData(certId);
		if (certificateData == null) {
			certificateData = new ShpCertificateData();
			certificateData.setShipId(shipInfoData.getId());
			certificateData.setCertType(certType);
		}

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/certificateEdit");
		
		String encodeContent = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		encodeContent += "/hyqh5/shipinfo/shipCertShow?certId=" + certificateData.getId() + "&shipId="
				+ shipInfoData.getId();

		String checksum = MD5.toMD5(encodeContent + Constants.SALT_VALUE);
		encodeContent += "&checksum=" + checksum;

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("certificateData", certificateData);
		mav.addObject("statuss", ReleaseStatusCode.values());
		mav.addObject("openeds", YesNoCode.values());
		mav.addObject("certTypes", CertTypeCode.values());
		mav.addObject("certType", certType);
		mav.addObject("shareUrl", encodeContent);

		return mav;
	}

	@RequestMapping(value = "/deleteCertificate", method = { RequestMethod.GET })
	public void deleteCertificate(Long certId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpCertificateData certificateData = certificateService.getCertificateData(certId);
			if (certificateData == null)
				throw new Exception("错误！证书信息没找到。");

			if (!userShipService.canModify(userData.getId(), certificateData.getShipId()))
				throw new Exception("错误！你没有该般舶资料的修改权。");

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
	public void saveCertificate(ShpCertificateData certificateData, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			if (!userShipService.canModify(userData.getId(), certificateData.getShipId()))
				throw new Exception("错误！你没有该般舶资料的修改权。");

			boolean b = certificateService.saveCertificate(certificateData);
			if (b) {
				map.put(JsonResponser.CONTENT, certificateData.getId());
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

	@RequestMapping(value = "/certAttaImg", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView certAttaImg(Long attaId, Long certId, Long shipId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

		ShpCertificateData certificateData = certificateService.getCertificateData(certId);
		if (!userShipService.canModify(userData.getId(), certificateData.getShipId()))
			throw new Exception("错误！你没有该般舶资料的修改权。");

		ShpCertAttaData certAttaData = certAttaService.getCertAttaData(attaId);

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/certificateAttaImg");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("certificateData", certificateData);
		mav.addObject("certAttaData", certAttaData);

		return mav;
	}

	@RequestMapping(value = "/deleteCertAttaImg", method = { RequestMethod.GET })
	public void deleteCertAttaImg(Long attaId, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpCertAttaData certAttaData = certAttaService.getCertAttaData(attaId);
			if (certAttaData == null)
				throw new Exception("错误！证书页图片未找到。");

			ShpCertificateData certificateData = certificateService.getCertificateData(certAttaData.getCertId());
			if (!userShipService.canModify(userData.getId(), certificateData.getShipId()))
				throw new Exception("错误！你没有该般舶资料的修改权。");

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
	public void uploadCertAttaImgs(Long certId, int fileCount, HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();

		// 多文件获取
		MultipartFile[] mpfs = null;
		if (fileCount > 0) {
			mpfs = new MultipartFile[fileCount];
			MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
			for (int i = 0; i < fileCount; i++) {
				mpfs[i] = multipartRequest.getFile("imgFile" + i);
			}
		}

		try {
			// 登录用户
			HyqUserData userData = this.getLoginUserData(request, response);

			ShpCertificateData certificateData = certificateService.getCertificateData(certId);
			if (!userShipService.canModify(userData.getId(), certificateData.getShipId()))
				throw new Exception("错误！你没有该般舶资料的修改权。");

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

	@RequestMapping(value = "/certShareQrcode", method = { RequestMethod.GET })
	public ModelAndView certShareQrcode(Long shipId, Long certId, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HyqUserData userData = this.getLoginUserData(request, response);

		if (!userShipService.canModify(userData.getId(), shipId))
			throw new Exception("错误！你没有该般舶资料的修改权。");

		ShpShipInfoData shipInfoData = shipInfoService.getShipInfoDataById(shipId);

		ShpCertificateData certificateData = certificateService.getCertificateData(certId);

		String encodeContent = "http://" + request.getServerName() + ":" + request.getServerPort()
				+ request.getContextPath();
		encodeContent += "/hyqh5/shipinfo/shipCertShow?certId=" + certificateData.getId() + "&shipId="
				+ shipInfoData.getId();

		String checksum = MD5.toMD5(encodeContent + Constants.SALT_VALUE);
		encodeContent += "&checksum=" + checksum;

		String realPath = Constants.SHARE_DIR;
		String relativePath = ShareDirService.getShipDir(shipInfoData.getMmsi()) + "/"
				+ certificateData.getCertType().toString();

		String certShareQrcodeUrl = QRCodeUtil.encode(encodeContent, realPath + "/default/logo.jpg",
				realPath + relativePath, true);

		File oldFile = new File(certShareQrcodeUrl);
		String fileMD5Id = FileMD5Util.getFileMD5(oldFile);
		String fileExt = FileMD5Util.getFileExtName(certShareQrcodeUrl);
		String fileDir = FileMD5Util.getFileDirName(certShareQrcodeUrl);
		String newUrl = fileDir + "/" + fileMD5Id + "." + fileExt;
		File newFile = new File(newUrl);

		if (!newFile.exists())
			oldFile.renameTo(newFile);
		else
			oldFile.delete();

		ModelAndView mav = new ModelAndView("/hyqh5/shipinfo/certShareQrcode");

		mav.addObject("userData", userData);
		mav.addObject("shipInfoData", shipInfoData);
		mav.addObject("certificateData", certificateData);
		mav.addObject("certShareQrcodeUrl", relativePath + newUrl.substring(newUrl.lastIndexOf("/")));

		return mav;
	}

}
