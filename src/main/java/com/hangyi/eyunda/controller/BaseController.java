package com.hangyi.eyunda.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

public class BaseController {
	protected final transient Logger logger = LoggerFactory.getLogger(getClass());

	public static final String COMMON_FAIL_PAGE = "fail";
	public static final String COMMON_FAIL_ALERT_KEY = "fail_key";
	public static final String USER_SESSION_KEY = "_user_session_key_";

	// 直接访问jsp
	@RequestMapping(value = "{jsp}")
	public String jsp(@PathVariable(value = "jsp") String jsp) {
		System.out.println(jsp);
		return jsp;
	}

}
