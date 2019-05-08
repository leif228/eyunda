package com.hangyi.eyunda.controller.manage.ship;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.ship.UpDownPortData;
import com.hangyi.eyunda.domain.enumeric.CargoTypeCode;
import com.hangyi.eyunda.domain.enumeric.OrderTypeCode;
import com.hangyi.eyunda.domain.enumeric.ScfBigAreaCode;
import com.hangyi.eyunda.domain.enumeric.ScfPortCityCode;
import com.hangyi.eyunda.domain.enumeric.WaresBigTypeCode;
import com.hangyi.eyunda.domain.enumeric.WaresTypeCode;
import com.hangyi.eyunda.service.EnumConst.MenuCode;
import com.hangyi.eyunda.service.ShareDirService;
import com.hangyi.eyunda.service.ship.UpDownPortService;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.MultipartUtil;
import com.hangyi.eyunda.util.OSUtil;

import jodd.io.FileUtil;

@Controller
@RequestMapping("/manage/ship")
public class UpDownPortController {
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UpDownPortService upDownPortService;

	// 获取一组对象，以列表的形式返回
	@RequestMapping(value = "/upDownPort", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView upDownPort(Page<UpDownPortData> pageData, OrderTypeCode orderType, String keyWords,
			HttpServletRequest request, HttpServletResponse response) {

		ModelAndView mav = new ModelAndView("/manage/manage-upDownPort");

		if (orderType == null)
			orderType = OrderTypeCode.daily_gaxjzx_container20e;

		WaresBigTypeCode waresBigType = orderType.getWaresBigType();
		WaresTypeCode waresType = orderType.getWaresType();
		CargoTypeCode cargoType = orderType.getCargoType();

		// 港口分页列表
		pageData = upDownPortService.getPageUpDownPortsByType(pageData, waresBigType, waresType, cargoType, keyWords);

		mav.addObject("selOrderType", orderType);
		// 船盘分类即合同分类
		mav.addObject("orderTypes", OrderTypeCode.getCodesByWaresBigType(WaresBigTypeCode.daily));
		mav.addObject("keyWords", keyWords);

		mav.addObject("waresBigType", waresBigType);
		mav.addObject("waresType", waresType);
		mav.addObject("cargoType", cargoType);
		mav.addObject("pageData", pageData);

		// 港口城市分组列表
		ScfBigAreaCode[] bigAreas = ScfBigAreaCode.values();
		mav.addObject("bigAreas", this.getMapBigAreas(bigAreas));

		// 菜单信息
		mav.addObject("menuMap", MenuCode.getLayerMenuMap());
		mav.addObject("menuOpen", MenuCode.SHIP);
		mav.addObject("menuAct", MenuCode.SHIP_UPDOWNPORT);

		return mav;
	}

	// 获取一个对象
	@RequestMapping(value = "/upDownPort/edit", method = { RequestMethod.GET })
	public void edit(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			UpDownPortData upDownPortData = upDownPortService.getById(id);

			if (upDownPortData == null) {
				upDownPortData = new UpDownPortData();

				WaresBigTypeCode waresBigType = WaresBigTypeCode.voyage;
				WaresTypeCode waresType = WaresTypeCode.gaxjzx;
				CargoTypeCode cargoType = CargoTypeCode.container20e;

				upDownPortData.setWaresBigType(waresBigType);
				upDownPortData.setWaresType(waresType);
				upDownPortData.setCargoType(cargoType);
			}

			map.put("upDownPortData", upDownPortData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "取得数据成功！");

			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "取得数据失败！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	private List<Map<String, Object>> getMapBigAreas(ScfBigAreaCode[] bigAreas) {
		List<Map<String, Object>> mapBigAreas = new ArrayList<Map<String, Object>>();
		for (ScfBigAreaCode bigArea : bigAreas) {
			Map<String, Object> mapBigArea = new HashMap<String, Object>();

			mapBigArea.put("code", bigArea.getCode());
			mapBigArea.put("description", bigArea.getDescription());

			List<ScfPortCityCode> portCityCodes = ScfPortCityCode.getPortCities(bigArea);
			List<Map<String, Object>> mapPortCityCodes = new ArrayList<Map<String, Object>>();
			for (ScfPortCityCode portCityCode : portCityCodes) {
				mapPortCityCodes.add(this.getMapPortCity(portCityCode));
			}
			mapBigArea.put("portCities", mapPortCityCodes);

			mapBigAreas.add(mapBigArea);
		}

		return mapBigAreas;
	}

	private Map<String, Object> getMapPortCity(ScfPortCityCode portCityCode) {
		Map<String, Object> mapPortCityCode = new HashMap<String, Object>();

		mapPortCityCode.put("code", portCityCode.getCode());
		mapPortCityCode.put("description", portCityCode.getDescription());

		return mapPortCityCode;
	}

	@RequestMapping(value = "/upDownPort/save", method = { RequestMethod.GET, RequestMethod.POST })
	public void save(UpDownPortData upDownPortData, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			upDownPortService.saveOrUpdate(upDownPortData);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "保存成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	@RequestMapping(value = "/upDownPort/delete", method = { RequestMethod.GET, RequestMethod.DELETE })
	public void delete(Long id, HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			upDownPortService.deleteUpDownPort(id);

			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "删除成功！");
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, e.getMessage());
		}
		JsonResponser.respondWithJson(response, map);
		return;
	}

	// 导入船舶Excel信息
	@RequestMapping(value = "/importUpDownPort", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView importUpDownPort(OrderTypeCode orderType, MultipartFile upDownPortExcelFile,
			HttpServletRequest request, HttpServletResponse response) throws Exception {

		String urlPath = "";
		if (OSUtil.isWindows()) {
			urlPath = request.getSession().getServletContext().getRealPath("/")
					+ "\\WEB-INF\\static\\shipExcel\\upDownPortExcel.xls";
		} else {
			urlPath = request.getSession().getServletContext().getRealPath("/")
					+ "/WEB-INF/static/shipExcel/upDownPortExcel.xls";
		}
		// 模板文件
		File modelFile = new File(urlPath);
		boolean isRight = upDownPortService.isRightFormat(upDownPortExcelFile, modelFile);
		if (isRight) {
			// 拷贝新文件到指定位置
			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getManageDir();
			String prefix = "upDownPort";
			String url = MultipartUtil.uploadFile(upDownPortExcelFile, realPath, relativePath, prefix);
			boolean result = upDownPortService.importUpDownPortExcel(orderType, realPath + url);
			if (result) {
				// 重定向到/space/ship/myShip
				ModelAndView mav = new ModelAndView("redirect:/manage/ship/upDownPort?orderType=" + orderType);
				return mav;
			} else {
				throw new Exception("导入航线EXCEL失败，请检查文件中数据格式是否正确！");
			}
		} else {
			throw new Exception("航线EXCEL文件格式不正确！");
		}
	}

	// 船舶EXCEL模板下载
	@RequestMapping(value = "/exportUpDownPort", method = { RequestMethod.GET })
	public void exportUpDownPort(OrderTypeCode orderType, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		try {

			WaresBigTypeCode waresBigType = orderType.getWaresBigType();
			WaresTypeCode waresType = orderType.getWaresType();
			CargoTypeCode cargoType = orderType.getCargoType();

			List<UpDownPortData> upDownPortDatas = upDownPortService.getUpDownPortsByType(waresBigType, waresType,
					cargoType);

			String srcFile = "";
			if (OSUtil.isWindows()) {
				srcFile = request.getSession().getServletContext().getRealPath("/")
						+ "\\WEB-INF\\static\\shipExcel\\upDownPortExcel.xls";
			} else {
				srcFile = request.getSession().getServletContext().getRealPath("/")
						+ "/WEB-INF/static/shipExcel/upDownPortExcel.xls";
			}

			String realPath = Constants.SHARE_DIR;
			String relativePath = ShareDirService.getManageDir();
			String prefix = "upDownPort";
			String newFileName = MultipartUtil.getFileName(FilenameUtils.getExtension(srcFile));
			String destFile = realPath + relativePath + File.separator + prefix + newFileName;

			FileUtil.copy(srcFile, destFile);

			File file = new File(destFile);
			boolean result = upDownPortService.exportUpDownPortExcel(file, upDownPortDatas);
			if (!result)
				return;

			// 设置下载文件的类型为任意类型
			response.setContentType("application/x-msdownload");
			// 添加下载文件的头信息。此信息在下载时会在下载面板上显示，比如：
			String fileName = file.getName();
			String downLoadName = new String(fileName.getBytes("GBK"), "iso8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + downLoadName + "\"");
			response.setHeader("Content_Length", String.valueOf(file.length()));

			ServletOutputStream sos = response.getOutputStream();
			FileInputStream fis = new FileInputStream(file);
			IOUtils.copy(fis, sos);
		} catch (IOException ex) {
			logger.error("DOWNLOAD EXCEPTION：", ex);
		}
	}

}
