package com.hangyi.eyunda.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.SimpleHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;

public final class HttpClientUtil2 {

	private static final String TYPE_STRING = "string";
	private static final String TYPE_BYTEARRAY = "byte[]";
	private static final String TYPE_STREAM = "stream";

	private static HttpClientUtil2 instance;

	private HttpClientUtil2() {
	}

	public static HttpClientUtil2 getInstance() {
		return getInstance("UTF-8");
	}

	public static HttpClientUtil2 getInstance(String urlCharset) {
		if (instance == null)
			instance = new HttpClientUtil2();
		// 设置默认的url编码
		instance.setUrlCharset(urlCharset);
		return instance;
	}

	private String urlCharset = "UTF-8";

	public void setUrlCharset(String urlCharset) {
		this.urlCharset = urlCharset;
	}

	public String getStringFromPostRequest(String targetUrl, Map<String, String> params) throws Exception {
		return (String) setPostRequest(targetUrl, params, TYPE_STRING, null);
	}

	public byte[] getBytesFromPostRequest(String targetUrl, Map<String, String> params) throws Exception {
		return (byte[]) setPostRequest(targetUrl, params, TYPE_BYTEARRAY, null);
	}

	public void getStreamFromPostRequest(String targetUrl, Map<String, String> params, OutputStream outputStream)
			throws Exception {
		if (outputStream == null) {
			throw new IllegalArgumentException("调用HttpClientUtil.setPostRequest方法，targetUrl不能为空!");
		}
		// response 的返回结果写到输出流
		setPostRequest(targetUrl, params, TYPE_STREAM, outputStream);
	}

	public String getStringFromGetRequest(String targetUrl) throws Exception {
		return (String) setGetRequest(targetUrl, TYPE_STRING, null);
	}

	public byte[] getBytesFromGetRequest(String targetUrl) throws Exception {
		return (byte[]) setGetRequest(targetUrl, TYPE_BYTEARRAY, null);
	}

	public void getStreamFromGetRequest(String targetUrl, OutputStream outputStream) throws Exception {
		if (outputStream == null) {
			throw new IllegalArgumentException("调用HttpClientUtil.setGetRequest方法，targetUrl不能为空!");
		}
		// response 的返回结果写到输出流
		setGetRequest(targetUrl, TYPE_STREAM, outputStream);
	}

	private Object setGetRequest(String targetUrl, String responseType, OutputStream outputStream) throws Exception {
		if (StringUtils.isBlank(targetUrl)) {
			throw new IllegalArgumentException("调用HttpClientUtil.setGetRequest方法，targetUrl不能为空!");
		}

		Object responseResult = null;
		HttpClient client = null;
		GetMethod getMethod = null;
		SimpleHttpConnectionManager connectionManager = null;
		try {
			connectionManager = new SimpleHttpConnectionManager(true);
			// 连接超时,单位毫秒
			connectionManager.getParams().setConnectionTimeout(10000);
			// 读取超时,单位毫秒
			connectionManager.getParams().setSoTimeout(120000);

			// 设置获取内容编码
			connectionManager.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, urlCharset);
			client = new HttpClient(new HttpClientParams(), connectionManager);

			getMethod = new GetMethod(targetUrl);
			// 设置请求参数的编码
			getMethod.getParams().setContentCharset(urlCharset);

			// 服务端完成返回后，主动关闭链接
			getMethod.setRequestHeader("Connection", "close");

			int sendStatus = client.executeMethod(getMethod);

			if (sendStatus == HttpStatus.SC_OK) {
				if (StringUtils.equals(TYPE_STRING, responseType)) {
					responseResult = getMethod.getResponseBodyAsString();
				} else if (StringUtils.equals(TYPE_BYTEARRAY, responseType)) {
					responseResult = getMethod.getResponseBody();
				} else if (StringUtils.equals(TYPE_STREAM, responseType)) {
					InputStream tempStream = getMethod.getResponseBodyAsStream();
					byte[] temp = new byte[1024];
					int n = -1;
					while ((n = tempStream.read(temp)) != -1) {
						outputStream.write(temp, 0, n);
					}
				}
			} else {
				System.err.println("HttpClientUtil.setGetRequest()-请求url：" + targetUrl + " 出错");
			}
		} catch (Exception e) {
			System.err.println(e.toString());
		} finally {
			// 释放链接
			if (getMethod != null) {
				getMethod.releaseConnection();
			}
			// 关闭链接
			if (connectionManager != null) {
				connectionManager.shutdown();
			}
		}
		return responseResult;
	}

	private Object setPostRequest(String targetUrl, Map<String, String> params, String responseType,
			OutputStream outputStream) throws Exception {
		if (StringUtils.isBlank(targetUrl)) {
			throw new IllegalArgumentException("调用HttpClientUtil.setPostRequest方法，targetUrl不能为空!");
		}

		Object responseResult = null;
		HttpClient client = null;
		PostMethod postMethod = null;
		NameValuePair[] nameValuePairArr = null;
		SimpleHttpConnectionManager connectionManager = null;
		try {
			connectionManager = new SimpleHttpConnectionManager(true);
			// 连接超时,单位毫秒
			connectionManager.getParams().setConnectionTimeout(10000);
			// 读取超时,单位毫秒
			connectionManager.getParams().setSoTimeout(120000);

			// 设置获取内容编码
			connectionManager.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET, urlCharset);
			client = new HttpClient(new HttpClientParams(), connectionManager);

			postMethod = new PostMethod(targetUrl);
			// 设置请求参数的编码
			postMethod.getParams().setContentCharset(urlCharset);

			// 服务端完成返回后，主动关闭链接
			postMethod.setRequestHeader("Connection", "close");
			if (params != null) {
				nameValuePairArr = new NameValuePair[params.size()];

				Set<String> keys = params.keySet();
				Iterator<String> keysIte = keys.iterator();
				int index = 0;
				while (keysIte.hasNext()) {
					String paramName = keysIte.next();
					String paramValue = params.get(paramName);

					NameValuePair pair = new NameValuePair(paramName, paramValue);
					nameValuePairArr[index] = pair;
					index++;
				}

				postMethod.addParameters(nameValuePairArr);
			}

			int sendStatus = client.executeMethod(postMethod);

			if (sendStatus == HttpStatus.SC_OK) {
				if (StringUtils.equals(TYPE_STRING, responseType)) {
					responseResult = postMethod.getResponseBodyAsString();
				} else if (StringUtils.equals(TYPE_BYTEARRAY, responseType)) {
					responseResult = postMethod.getResponseBody();
				} else if (StringUtils.equals(TYPE_STREAM, responseType)) {
					InputStream tempStream = postMethod.getResponseBodyAsStream();
					byte[] temp = new byte[1024];
					int n = -1;
					while ((n = tempStream.read(temp)) != -1) {
						outputStream.write(temp, 0, n);
					}
				}
			} else {
				Gson gson = new Gson();
				String jsonStr = gson.toJson(params);
				System.err.println("HttpClientUtil2.setPostRequest()-请求url：" + targetUrl + " 出错\n请求参数有：" + jsonStr);
			}
		} catch (Exception e) {
			throw new Exception("HttpClientUtil2.setPostRequest()-请求url：" + targetUrl + "，请求超时！");
		} finally {
			// 释放链接
			if (postMethod != null) {
				postMethod.releaseConnection();
			}
			// 关闭链接
			if (connectionManager != null) {
				connectionManager.shutdown();
			}
		}
		return responseResult;
	}

	public static void main(String[] args) throws Exception {
		HttpClientUtil2 util = HttpClientUtil2.getInstance("UTF-8");

		String url = "http://www.myships.com/myships/10026";

		Map<String, String> params = new HashMap<String, String>();
		params.put("mmsi", "413985299");
		params.put("startTime", "2015-03-01 14:40");
		params.put("endTime", "2015-06-01 14:41");

		/*
		 * File f = new File("e:/test3.txt"); OutputStream fos = new
		 * FileOutputStream(f);
		 * 
		 * util.getStreamFromPostRequest(url, params, fos);
		 * 
		 * fos.close();
		 */
		String responseStr = util.getStringFromPostRequest(url, params);
		System.out.println(responseStr.length());
	}

}
