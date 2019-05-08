package com.hangyi.eyunda.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

public final class XmlResponser {

	private XmlResponser() {

	}

	public static void writeXml(HttpServletResponse response, String xml) {
		if (xml == null)
			return;

		PrintWriter writer = xmlWriter(response);

		writer.write(xml);
		writer.flush();
		writer.close();
	}

	private static PrintWriter xmlWriter(HttpServletResponse response) {
		PrintWriter writer = null;

		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");

		try {
			writer = response.getWriter();
			writer.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
		} catch (IOException e) {
			e.printStackTrace();
		}

		return writer;
	}
}
