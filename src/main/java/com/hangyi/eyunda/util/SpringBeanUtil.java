package com.hangyi.eyunda.util;

import javax.servlet.ServletContext;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public final class SpringBeanUtil
{
	private SpringBeanUtil() {
		
	}
	
	public static Object getBean(ServletContext sc, String beanName)
	{
		Object obj = null;

		WebApplicationContext wac = WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
		obj = wac.getBean(beanName);
		
		return obj;
	}
}
