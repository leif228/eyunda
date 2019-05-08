package com.hangyi.eyunda.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
 
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

public class HttpClientSendHttpsUtil {
	
	public static final String sendHttpsRequestByPost(String url, Map<String, String> params) {
	    String responseContent = null;
	    HttpClient httpClient = new DefaultHttpClient();
	    //创建TrustManager
	    X509TrustManager xtm = new X509TrustManager() {
	        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {}
	        public X509Certificate[] getAcceptedIssuers() {
	            return null;
	        }
	    };
	    //这个好像是HOST验证
	    X509HostnameVerifier hostnameVerifier = new X509HostnameVerifier() {
	        public boolean verify(String arg0, SSLSession arg1) {
	            return true;
	        }
	        public void verify(String arg0, SSLSocket arg1) throws IOException {}
	        public void verify(String arg0, String[] arg1, String[] arg2) throws SSLException {}
	        public void verify(String arg0, X509Certificate arg1) throws SSLException {}
	    };
	    try {
	        //TLS1.0与SSL3.0基本上没有太大的差别，可粗略理解为TLS是SSL的继承者，但它们使用的是相同的SSLContext
	        SSLContext ctx = SSLContext.getInstance("TLS");
	        //使用TrustManager来初始化该上下文，TrustManager只是被SSL的Socket所使用
	        ctx.init(null, new TrustManager[] { xtm }, null);
	        //创建SSLSocketFactory
	        SSLSocketFactory socketFactory = new SSLSocketFactory(ctx);
	        socketFactory.setHostnameVerifier(hostnameVerifier);
	        //通过SchemeRegistry将SSLSocketFactory注册到我们的HttpClient上
	        httpClient.getConnectionManager().getSchemeRegistry().register(new Scheme("https", socketFactory, 443));
	        HttpPost httpPost = new HttpPost(url);
	        List<NameValuePair> formParams = new ArrayList<NameValuePair>(); // 构建POST请求的表单参数
	        for (Map.Entry<String, String> entry : params.entrySet()) {
	            formParams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
	        }
	        httpPost.setEntity(new UrlEncodedFormEntity(formParams, "UTF-8"));
	        HttpResponse response = httpClient.execute(httpPost);
	        HttpEntity entity = response.getEntity(); // 获取响应实体
	        if (entity != null) {
	            responseContent = EntityUtils.toString(entity, "UTF-8");
	        }
	    } catch (KeyManagementException e) {
	        e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    } catch (UnsupportedEncodingException e) {
	        e.printStackTrace();
	    } catch (ClientProtocolException e) {
	        e.printStackTrace();
	    } catch (ParseException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } finally {
	        // 关闭连接,释放资源
	        httpClient.getConnectionManager().shutdown();
	    }
	    System.out.println(responseContent);
	    return responseContent;
	}
}
