package com.hangyi.eyunda.service.manage.stat;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class StatInfoTimer extends QuartzJobBean {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private StatInfoService statInfoService;

	public void setStatInfoService(StatInfoService statInfoService) {
		this.statInfoService = statInfoService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date()) + " ---- StatInfoTimer is running!");
		try {
			statInfoService.statLogin();
			statInfoService.statShipCall();
			statInfoService.statUser();
			statInfoService.statShip();
			statInfoService.statOrder();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		System.out.println(sdf.format(new Date()) + " ---- StatInfoTimer is stopped!");
	}

}