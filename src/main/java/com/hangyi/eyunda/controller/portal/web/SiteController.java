package com.hangyi.eyunda.controller.portal.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.hangyi.eyunda.controller.JsonResponser;
import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.data.account.UserData;
import com.hangyi.eyunda.data.cargo.CargoData;
import com.hangyi.eyunda.data.order.EvaluateData;
import com.hangyi.eyunda.data.release.CarrierIssueData;
import com.hangyi.eyunda.data.ship.ShipData;
import com.hangyi.eyunda.domain.enumeric.ColumnCode;
import com.hangyi.eyunda.domain.enumeric.ColumnPageCode;
import com.hangyi.eyunda.service.cargo.CargoService;
import com.hangyi.eyunda.service.portal.login.MailUtil;
import com.hangyi.eyunda.service.portal.login.UserService;
import com.hangyi.eyunda.service.release.ReleaseCarrierService;
import com.hangyi.eyunda.service.ship.MyShipService;
import com.hangyi.eyunda.util.CalendarUtil;
import com.hangyi.eyunda.util.Constants;
import com.hangyi.eyunda.util.FileUtil;
import com.hangyi.eyunda.util.OSUtil;

@Controller
@RequestMapping("/portal/site")
public class SiteController {// 统一登录
	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	@Autowired
	private ReleaseCarrierService releaseCarrierService;
	@Autowired
	private CargoService cargoService;
	@Autowired
	private MyShipService myShipService;

	/** 个人主页 */
	@RequestMapping(value = "/index/{logname}", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView index(@PathVariable String logname, String c, String pageNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String viewString = "";
		ColumnCode currentColumn = ColumnCode.valueOf(c);
		if (currentColumn.getListPage().equals(ColumnPageCode.alink)) {
			viewString = currentColumn.getContentPage().getPageUrl();
		} else {
			viewString = currentColumn.getListPage().getPageUrl();
		}

		ModelAndView mv = new ModelAndView(viewString);
		UserData userData = userService.getByLoginName(logname);
		if (userData == null)
			throw new Exception(logname + ":该用户不存在。");

		if (currentColumn == ColumnCode.GSJJ || currentColumn == ColumnCode.LXWM) {
			CarrierIssueData ciData = releaseCarrierService.getCarrierIssueData(currentColumn, userData.getId());
			mv.addObject("ciData", ciData == null ? new CarrierIssueData() : ciData);
		} else {
			Page<CarrierIssueData> pageData = new Page<CarrierIssueData>();
			pageData.setPageNo(Integer.valueOf(pageNo == null ? "1" : pageNo));
			releaseCarrierService.getReleaseType2(pageData, userData.getId(), currentColumn);
			if (pageData.getResult().size() > 0) {
				if (currentColumn == ColumnCode.CBXX) {
					for (CarrierIssueData ci : pageData.getResult()) {
						if (ci.getSmallImage() == null || "".equals(ci.getSmallImage())) {
							ShipData myShipData = myShipService
									.getShipData(myShipService.get(Long.valueOf(ci.getNo())));
							ci.setSource(myShipData != null ? myShipData.getShipType() : "");
						}
					}
				} else if (currentColumn == ColumnCode.HWXX) {
					for (CarrierIssueData ci : pageData.getResult()) {
						if (ci.getSmallImage() == null || "".equals(ci.getSmallImage())) {
							CargoData cargoData = cargoService.getCargoData(Long.valueOf(ci.getNo()));
							ci.setSource(cargoData != null ? cargoData.getCargoType().name() : "");
						}
					}
				}
			}
			// 分页信息
			mv.addObject("pageData", pageData);
		}
		// 用户信息
		mv.addObject("userData", userData);

		ColumnCode[] columnCodes = ColumnCode.values();
		mv.addObject("currentIndex", currentColumn);
		mv.addObject("columnCodes", columnCodes == null ? new ColumnCode[] {} : columnCodes);

		return mv;
	}

	@RequestMapping(value = "/detail/{logname}", method = RequestMethod.GET)
	public ModelAndView detail(@PathVariable String logname, String c, String pageNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ColumnCode currentIndex = ColumnCode.valueOf(c);
		String viewString = currentIndex.getContentPage().getPageUrl();

		ModelAndView mv = new ModelAndView(viewString);
		UserData userData = userService.getByLoginName(logname);
		if (userData == null)
			throw new Exception(logname + ":该用户不存在。");

		Page<CarrierIssueData> pageData = new Page<CarrierIssueData>();
		pageData.setPageNo(Integer.valueOf(pageNo == null ? "1" : pageNo));
		pageData.setPageSize(1);
		releaseCarrierService.getReleaseType2(pageData, userData.getId(), currentIndex);

		if (currentIndex == ColumnCode.HWXX) {
			CargoData cargoData = null;
			if (pageData.getResult().size() > 0)
				cargoData = cargoService.getCargoData(Long.valueOf(pageData.getResult().get(0).getNo()));
			// 货运信息
			mv.addObject("cargoData", cargoData == null ? new CargoData() : cargoData);
		} else if (currentIndex == ColumnCode.CBXX) {
			ShipData myShipData = null;
			List<EvaluateData> evaluateDatas = null;
			if (pageData.getResult().size() > 0) {
				Long id = Long.valueOf(pageData.getResult().get(0).getNo());
				// 取得船舶信息
				myShipData = myShipService.getShipData(id);
				// 取得评价信息
				evaluateDatas = myShipService.getEvaluaDatas(id);
			}
			mv.addObject("myShipData", myShipData == null ? new ShipData() : myShipData);
			mv.addObject("evaluateDatas", evaluateDatas == null ? new ArrayList<EvaluateData>() : evaluateDatas);
		} else {
			// 分页信息
			mv.addObject("pageData", pageData);
		}
		// 用户信息
		mv.addObject("userData", userData);

		ColumnCode[] columnCodes = ColumnCode.values();
		mv.addObject("currentIndex", currentIndex);
		mv.addObject("columnCodes", columnCodes == null ? new ColumnCode[] {} : columnCodes);
		return mv;
	}

	@RequestMapping(value = "/loadmore/{logname}", method = RequestMethod.GET)
	public void loadmore(@PathVariable String logname, String c, String pageNo, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			ColumnCode currentIndex = ColumnCode.valueOf(c);
			UserData userData = userService.getByLoginName(logname);

			Page<CarrierIssueData> pageData = new Page<CarrierIssueData>();
			pageData.setPageNo(Integer.valueOf(pageNo == null ? "1" : pageNo));
			pageData.setPageSize(1);
			releaseCarrierService.getReleaseType2(pageData, userData.getId(), currentIndex);

			if (pageData.getResult().size() > 0) {
				map.put("pageData", pageData);
				map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			} else {
				map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			}
			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

	@RequestMapping(value = "/callform/{logname}", method = RequestMethod.POST)
	public void sendEmail(@PathVariable String logname, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		try {
			String email = ServletRequestUtils.getStringParameter(request, "email", "");
			String name = ServletRequestUtils.getStringParameter(request, "name", "");
			String content = ServletRequestUtils.getStringParameter(request, "content", "");
			String currentTime1 = CalendarUtil.toYYYY_MM_DD(Calendar.getInstance());
			String beforeReplaceEmailFile = "";
			if (OSUtil.isWindows()) {
				beforeReplaceEmailFile = FileUtil.readFromFile(request.getSession().getServletContext().getRealPath("/")
						+ "\\WEB-INF\\static\\email\\emailCallUsTemplate.html");
			} else {
				beforeReplaceEmailFile = FileUtil.readFromFile(request.getSession().getServletContext().getRealPath("/")
						+ "/WEB-INF/static/email/emailCallUsTemplate.html");
			}
			String afterReplaceEmailFile = beforeReplaceEmailFile.replaceAll("\\$\\{name\\}", name)
					.replaceAll("\\$\\{logname\\}", logname).replaceAll("\\$\\{email\\}", email)
					.replaceAll("\\$\\{content\\}", content).replaceAll("\\$\\{commitTime1\\}", currentTime1);
			MailUtil.sendMail("易运达站点-联系我们", afterReplaceEmailFile, Constants.MailFrom);
			map.put(JsonResponser.RETURNCODE, JsonResponser.SUCCESS);
			map.put(JsonResponser.MESSAGE, "邮件已经发送！");

			// 将处理结果传递给视图显示，或转化为JSON串返回到客户端
			JsonResponser.respondWithJson(response, map);
		} catch (Exception e) {
			logger.error(e.getMessage());
			map.put(JsonResponser.RETURNCODE, JsonResponser.FAILURE);
			map.put(JsonResponser.MESSAGE, "发送邮件异常,请重新发送！");
			JsonResponser.respondWithJson(response, map);
		}
		return;
	}

}
