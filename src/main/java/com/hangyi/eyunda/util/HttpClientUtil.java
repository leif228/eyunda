package com.hangyi.eyunda.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.io.FileUtils;

public class HttpClientUtil {

	public static String getRemoteMessage(String url) {
		StringBuffer response = null;
		String result = null;
		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();

			httpConnection.setUseCaches(false);
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");

			httpConnection.connect();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream input = httpConnection.getInputStream();

				response = new StringBuffer();
				Reader reader = new InputStreamReader(input, "UTF-8");
				reader = new BufferedReader(reader);
				char[] buffer = new char[1024];
				for (int n = 0; n >= 0;) {
					n = reader.read(buffer, 0, buffer.length);
					if (n > 0)
						response.append(buffer, 0, n);
				}

				input.close();
				httpConnection.disconnect();
			}
			result = response != null ? response.toString() : "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static boolean getRemoteFile(String url, String filePath) {
		try {
			URL u = new URL(url);
			HttpURLConnection httpConnection = (HttpURLConnection) u.openConnection();

			httpConnection.setUseCaches(false);
			httpConnection.setDoOutput(true);
			httpConnection.setRequestMethod("GET");

			httpConnection.connect();
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				InputStream input = httpConnection.getInputStream();

				FileUtils.copyInputStreamToFile(input, new File(filePath));

				input.close();
				httpConnection.disconnect();

				return true;
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

}
