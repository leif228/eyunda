package com.hangyi.eyunda.controller.interceptor;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLFilter implements Filter {
	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;

		// request.getScheme() + "://" + request.getServerName() + ":" +
		// request.getServerPort()
		// + request.getContextPath() + request.getServletPath() + "?" +
		// request.getQueryString();
		// request.getRequestURI() = request.getContextPath() +
		// request.getServletPath()

		String serverName = request.getServerName().toLowerCase();
		String requestURI = request.getRequestURI();
		if (requestURI.equals("/MP_verify_6eBg35FjVu6NXTBU.txt")){//微信公众号验证
			response.sendRedirect(request.getContextPath() + "/wx/verify");
			return;
		}
		if (requestURI.equals("/") || requestURI.equals("/eyunda/")) {
			if (!"eyd98.com".equals(serverName) && !"www.eyd98.com".equals(serverName) && !"localhost".equals(serverName)
					&& !"127.0.0.1".equals(serverName) && !serverName.startsWith("192.168.")) {
				response.sendRedirect(request.getContextPath() + "/portal/site/index?c=GSJJ");
				return;
			}
		}
		if(requestURI.contains("'") || requestURI.contains("<") || requestURI.contains("\"")){
			response.sendRedirect(request.getContextPath() + "/error/index");
			return;
		}
		if (requestURI.contains("/portal/site") && serverName.indexOf(".") > 0) {
			String secondDomain = serverName.substring(0, serverName.indexOf("."));
			String realURI = request.getServletPath() + "/" + secondDomain;

			request.getRequestDispatcher(realURI).forward(servletRequest, servletResponse);
		} else {
			filterChain.doFilter(servletRequest, servletResponse);
		}

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	@Override
	public void destroy() {

	}
}
