package com.hangyi.eyunda.service;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hangyi.eyunda.dao.Page;
import com.hangyi.eyunda.domain.enumeric.ShpCertSysCode;
import com.hangyi.eyunda.domain.enumeric.YesNoCode;
import com.hangyi.eyunda.service.hyquan.shipinfo.ShpCertificateService;

import junit.framework.Assert;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext.xml" })
public class ShpCertificateServiceTest {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private ShpCertificateService certificateService;

	@Test
	public void test() {
		Page<Map<String, Object>> pageData = new Page<Map<String, Object>>();
		try {
			certificateService.getPageMap(pageData, ShpCertSysCode.hyq, "", null, YesNoCode.no);
			Assert.assertTrue("成功！", pageData.getResult().size() > 0);
		} catch (Exception e) {
			logger.error(e.getMessage());
			Assert.assertTrue("失败！", pageData.getResult() == null || pageData.getResult().size() <= 0);
		}
	}
}
