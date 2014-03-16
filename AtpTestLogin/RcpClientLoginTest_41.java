package net.atp.rcp.client.testlogin;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;

public class RcpClientLoginTest_41 {
	private static HttpClient httpClient;
	private static String uris = "https://113.106.89.18:883/OnlineTrading/Page/";
	private static String uri = "http://113.106.89.18:880/OnlineTrading/Page/";

	public static void main(String[] args) throws Exception {
		httpClient = new DefaultHttpClient(
				getPoolingHttpClientConnectionManager());

		uri = uris;
		login("swrd", "atppta");
	}

	static void login(String user, String pass) throws Exception {

		HttpPost httpPost = new HttpPost(uri + "appInterface.php");
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("action", "login"));
		nvps.add(new BasicNameValuePair("user", user));
		nvps.add(new BasicNameValuePair("pass", pass));
		nvps.add(new BasicNameValuePair("from", "1"));

		httpPost.setEntity(new UrlEncodedFormEntity(nvps));
		long cur = System.currentTimeMillis();
		HttpResponse response = httpClient.execute(httpPost);
		System.err.println("login: " + (System.currentTimeMillis() - cur));
	}

	private static ThreadSafeClientConnManager getPoolingHttpClientConnectionManager()
			throws Exception {
		TrustManager easyTrustManager = new X509TrustManager() {

			@Override
			public X509Certificate[] getAcceptedIssuers() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}

			@Override
			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
				// TODO Auto-generated method stub

			}
		};
		SSLContext sslcontext = SSLContext.getInstance("TLS");
		sslcontext.init(null, new TrustManager[] { easyTrustManager }, null);
		SSLSocketFactory sf = new SSLSocketFactory(sslcontext,
				SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);

		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 880, PlainSocketFactory
				.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 883, sf));
		ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager(
				schemeRegistry);

		return cm;
	}
}
