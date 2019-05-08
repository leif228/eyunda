package com.hangyi.eyunda.weixing.timer;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class AccessTokenTimer extends QuartzJobBean{
	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	private AccessTokenService accessTokenService;

	public void setAccessTokenService(AccessTokenService accessTokenService) {
		this.accessTokenService = accessTokenService;
	}

	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
			
		try {
			accessTokenService.doAccessToken();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

}
