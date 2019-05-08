package com.hangyi.eyunda.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hangyi.eyunda.service.manage.AdminService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class YydAdminRoleModuleTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private AdminService adminService;

	@Test
	public void test() {
		try {
			adminService.initUserRoleModule();
			Assert.assertEquals("成功！", 1, 1);
		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.assertEquals("失败！", 1, 0);
		}
	}
}
