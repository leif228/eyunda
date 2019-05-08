package com.hangyi.eyunda.chat.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class OnlineUserTimer extends QuartzJobBean {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private OnlineUserCheckService onlineUserCheckService;

	public void setOnlineUserCheckService(OnlineUserCheckService onlineUserCheckService) {
		this.onlineUserCheckService = onlineUserCheckService;
	}

	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info(sdf.format(new Date()) + " ---- OnlineUserTimer is running!");
		try {
			onlineUserCheckService.checkUserStatus();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.info(sdf.format(new Date()) + " ---- OnlineUserTimer is stopped!");
	}

}