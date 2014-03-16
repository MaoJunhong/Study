package net.atp.rcp.client.testlogin;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

public class RcpClientLoginTest {
	private static CloseableHttpClient httpClient;
	private static String uris = "https://113.106.89.18:883/OnlineTrading/Page/";
	private static String uri = "http://113.106.89.18:880/OnlineTrading/Page/";

	public static void main(String[] args) throws Exception {
		httpClient = HttpClients.custom()
				.setConnectionManager(getPoolingHttpClientConnectionManager())
				.build();

		uri = uris;
		login("", "");

		getUserConfig();
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
		CloseableHttpResponse response = httpClient.execute(httpPost);
		System.err.println("login: " + (System.currentTimeMillis() - cur));
	}

	static void getUserConfig() throws Exception {
		HttpPost httpPost = new HttpPost(uri + "appInterface.php");

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("action", "getUserConfig"));
		httpPost.setEntity(new UrlEncodedFormEntity(nvps));

		long cur = System.currentTimeMillis();
		CloseableHttpResponse response = httpClient.execute(httpPost);
		System.err.println("get user config: "
				+ (System.currentTimeMillis() - cur));
	}

	private static HttpClientConnectionManager getPoolingHttpClientConnectionManager()
			throws Exception {
		SSLContextBuilder builder = new SSLContextBuilder();
		builder.loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(X509Certificate[] arg0, String arg1)
					throws CertificateException {
				return true;
			}
		});
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
				builder.build(),
				SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
		Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder
				.<ConnectionSocketFactory> create().register("https", sslsf)
				.register("http", new PlainConnectionSocketFactory()).build();

		return new PoolingHttpClientConnectionManager(socketFactoryRegistry);
	}
}
