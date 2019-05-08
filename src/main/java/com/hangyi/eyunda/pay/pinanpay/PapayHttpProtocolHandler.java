package com.hangyi.eyunda.pay.pinanpay;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import com.hangyi.eyunda.util.Constants;

public final class PapayHttpProtocolHandler {

	public static boolean hostnameVerifier = false;
	private static ResourceBundle rb;

	private static int getVendorType() {
		Properties tSysProperties = System.getProperties();
		String tJvmVendor = tSysProperties.getProperty("java.vm.vendor");

		if (tJvmVendor.equals("IBM Corporation"))
			return 1;
		else {
			return 0;
		}
	}

	public static SSLSocketFactory getSSLSocket(String jksPath, String pas) throws Exception {
		int iProviderType = getVendorType();
		SSLSocketFactory ssf = null;
		try {
			SSLContext sslContext = null;
			TrustManager[] tm = { new X509TrustManager() {

				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}

				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}

				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

				}
			} };
			TrustManagerFactory tmf;
			KeyManagerFactory kmf;
			if (iProviderType == 1) {
				sslContext = SSLContext.getInstance("TLS", "IBMJSSE2");
				kmf = KeyManagerFactory.getInstance("IbmX509");
				tmf = TrustManagerFactory.getInstance("IbmX509");
			} else {
				sslContext = SSLContext.getInstance("TLS", "SunJSSE");
				kmf = KeyManagerFactory.getInstance("SunX509");
				tmf = TrustManagerFactory.getInstance("SunX509");
			}

			KeyStore ks = KeyStore.getInstance("JKS");
			ks.load(PapayHttpProtocolHandler.class.getResourceAsStream(jksPath), pas.toCharArray());
			kmf.init(ks, pas.toCharArray());

			KeyStore tks = KeyStore.getInstance("JKS");
			tks.load(PapayHttpProtocolHandler.class.getResourceAsStream(jksPath), pas.toCharArray());
			tmf.init(tks);

			sslContext.init(kmf.getKeyManagers(), tm, new SecureRandom());

			ssf = sslContext.getSocketFactory();

			return ssf;
		} catch (Exception e) {
			throw new Exception(e.getMessage(), e);
		}
	}

	public static byte[] sendData(byte[] aOutputData, String serverUrl, String proxyip, String proxyport)
			throws Exception {
		// rb = PropertiesFile.getResources("paygate.properties");
		// String cafilename = LianaStandard.getRootPath() + "WEB-INF/" +
		// rb.getString("cafile");
		// String cafilename = "D:/view/zhuning090_Dev_EBANK-COM_2014S1/vobs/EBANK-COM_VOB/ebank-com_j2ee/src/corporbank/WebContent/WEB-INF/paygate.jks";
		// String pas = rb.getString("key_password");
		String cafilename = Constants.getValue("cafile");
		String pas = Constants.getValue("store_password");

		SSLSocketFactory ssf = getSSLSocket(cafilename, pas);
		HttpsURLConnection.setDefaultSSLSocketFactory(ssf);
		URL url = new URL(serverUrl);
		Proxy proxy = null;
		if (proxyip != null) {
			if (proxyport == null) {
				throw new Exception("validation.yikou.network.proxyport");
			}

			int port = -1;
			try {
				port = Integer.parseInt(proxyport);
			} catch (Exception e) {
				throw new Exception("validation.yikou.network.argument");
			}
			proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyip, port));
		}

		boolean ishttps = false;
		int indexhttps = serverUrl.indexOf("https");
		if (indexhttps >= 0) {
			ishttps = true;
		}

		HttpURLConnection http = null;

		if (proxy != null)
			http = (HttpURLConnection) url.openConnection(proxy);
		else {
			http = (HttpURLConnection) url.openConnection();
		}

		if ((!hostnameVerifier) && (ishttps)) {
			((HttpsURLConnection) http).setHostnameVerifier(new HostnameVerifier() {
				public boolean verify(String arg0, SSLSession arg1) {
					return true;
				}
			});
		}

		if (ishttps) {
			((HttpsURLConnection) http).setSSLSocketFactory(ssf);
		}

		http.setConnectTimeout(3000);
		http.setReadTimeout(60000);
		http.setDoOutput(true);
		http.setDoInput(true);
		http.setAllowUserInteraction(false);
		http.setUseCaches(false);
		http.setRequestMethod("POST");
		http.setRequestProperty("content-type", "application/x-www-form-urlencoded; charset=utf-8");
		http.setRequestProperty("Content-Length", String.valueOf(aOutputData.length));
		http.setRequestProperty("Connection", "close");
		http.setRequestProperty("User-Agent", "sdb client");
		http.setRequestProperty("Accept", "text/xml");
		OutputStream out = http.getOutputStream();
		out.write(aOutputData);
		out.flush();
		ByteArrayOutputStream byteout = new ByteArrayOutputStream(4096);
		InputStream in = http.getInputStream();
		int code = http.getResponseCode();
		if (code != 200) {
			throw new Exception("validation.yikou.network.webservice");
		}
		byte[] buf = new byte[4096];
		while (true) {
			int readlen = in.read(buf, 0, 4096);
			if (readlen < 0) {
				break;
			}
			if (readlen > 0) {
				byteout.write(buf, 0, readlen);
			}
		}
		out.close();
		in.close();

		return byteout.toByteArray();
	}

}
