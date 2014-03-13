package com.sgx.download.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgx.download.encrypt.Encrypter;

public class DownLoader {
	private static Logger logger = LoggerFactory.getLogger(DownLoader.class);
	private static CloseableHttpClient httpClient;
	static {
		httpClient = HttpClients.custom()
				.setRedirectStrategy(new LaxRedirectStrategy()).build();
	}

	public static CloseableHttpClient getHttpClient() {
		return httpClient;
	}

	public static boolean login(String user, String password) throws Exception {

		String uri = "https://www1.sgxotc.sgx.com/opensso/UI/Login";
		Encrypter encrypter = new Encrypter(user, password);

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("IDToken0", ""));
		nvps.add(new BasicNameValuePair("IDToken1", encrypter.getIDToken1()));
		nvps.add(new BasicNameValuePair("IDToken2", encrypter.getIDToken2()));
		nvps.add(new BasicNameValuePair("IDButton", "Log In"));
		nvps.add(new BasicNameValuePair("goto",
				"aHR0cHM6Ly93d3cxLnNneG90Yy5zZ3guY29tOjQ0My9zZ3hvdGMvbG9naW5oYW5kbGVyLmRv"));
		nvps.add(new BasicNameValuePair("SunQueryParamsString",
				"cmVhbG09L3d3dzEuc2d4b3RjLnNneC5jb20m"));
		nvps.add(new BasicNameValuePair("encoded", "true"));
		nvps.add(new BasicNameValuePair("dateStr", System.currentTimeMillis()
				+ ""));// 时间磋，会导致超时
		nvps.add(new BasicNameValuePair("pageTimeOut", "300"));
		nvps.add(new BasicNameValuePair("gx_charset", "UTF-8"));

		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		// anaysisResponse(httpResponse);

		return httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}

	public static void download(String startDate, String endDate,
			String filePath) throws Exception {
		String uri = "https://www1.sgxotc.sgx.com/sgxotc/idb/idbsingletradeblotterEntry.do";

		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("value(toRefresh)", "F"));
		nvps.add(new BasicNameValuePair("value(useraction)", "Download"));
		nvps.add(new BasicNameValuePair("value(status)",
				"Confirmed#_#Pending#_#Void#_#Rejected#_#Deleted#_#Pending Delete#_#"));
		nvps.add(new BasicNameValuePair("value(commType)", "F#_#S#_#OS#_#"));
		nvps.add(new BasicNameValuePair("value(gcmIDBCode)", ""));
		nvps.add(new BasicNameValuePair("value(tradeId)", ""));
		nvps.add(new BasicNameValuePair("value(startDate)", startDate));
		nvps.add(new BasicNameValuePair("value(endDate)", endDate));
		nvps.add(new BasicNameValuePair("commTypeElem", "F"));
		nvps.add(new BasicNameValuePair("commTypeElem", "S"));
		nvps.add(new BasicNameValuePair("commTypeElem", "OS"));
		nvps.add(new BasicNameValuePair("value(commodity)", ""));
		nvps.add(new BasicNameValuePair("value(custAccNo)", ""));
		nvps.add(new BasicNameValuePair("statusElem", "Confirmed"));
		nvps.add(new BasicNameValuePair("statusElem", "Pending"));
		nvps.add(new BasicNameValuePair("statusElem", "Void"));
		nvps.add(new BasicNameValuePair("statusElem", "Rejected"));
		nvps.add(new BasicNameValuePair("statusElem", "Deleted"));
		nvps.add(new BasicNameValuePair("statusElem", "Pending Delete"));
		nvps.add(new BasicNameValuePair("value(registeredVia)", ""));
		nvps.add(new BasicNameValuePair("value(regViaValue)", ""));
		nvps.add(new BasicNameValuePair("value(numRowPerPage)", "50"));
		nvps.add(new BasicNameValuePair("value(startRow)", "1"));

		HttpPost httpPost = new HttpPost(uri);
		httpPost.setEntity(new UrlEncodedFormEntity(nvps, "UTF-8"));

		CloseableHttpResponse httpResponse = httpClient.execute(httpPost);
		// anaysisResponse(httpResponse);

		logger.info("begin download: " + filePath);
		InputStream inputStream = httpResponse.getEntity().getContent();
		FileOutputStream fileOutputStream = new FileOutputStream(new File(
				filePath));
		byte[] bytes = new byte[102400];
		int len = 0;
		while ((len = inputStream.read(bytes)) != -1) {
			fileOutputStream.write(bytes, 0, len);
			fileOutputStream.flush();
		}
		fileOutputStream.close();
		logger.info("finish download");
		httpPost.releaseConnection();
	}

	public static void anaysisResponse(HttpResponse httpResponse)
			throws Exception {
		System.err.println(httpResponse.getStatusLine());
		System.err.println(EntityUtils.toString(httpResponse.getEntity()));
	}

}
