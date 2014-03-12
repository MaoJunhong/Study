/**
 * 
 */
package com.yeetrack.yinyueyun;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ProtocolException;
import org.apache.http.client.CookieStore;
import org.apache.http.client.RedirectStrategy;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.protocol.HttpContext;

/**
 * @author victor
 * 
 */
public class HttpTool {
	private static CloseableHttpClient httpClient;
	private static String cookieName = "BDUSS";
	private static String cookieValue = "XlhbFRneUNEei0zRjJpVDJTWGptcUt2Q3JrU3RFOTM5a3hOWUpVOU12aWFtMGRUQVFBQUFBJCQAAAAAAAAAAAEAAADqcUMWc3dyZGxnYwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAJoOIFOaDiBTW";

	public static CloseableHttpClient getHttpClient() {
		CookieStore cookieStore = new BasicCookieStore();
		BasicClientCookie baiduCookie = new BasicClientCookie(cookieName,
				cookieValue);
		baiduCookie.setDomain(".baidu.com");
		baiduCookie.setPath("/");
		cookieStore.addCookie(baiduCookie);

		httpClient = HttpClients.custom().setDefaultCookieStore(cookieStore)
				.setRedirectStrategy(new RedirectStrategy() {
					@Override
					public boolean isRedirected(HttpRequest request,
							HttpResponse response, HttpContext context)
							throws ProtocolException {
						return false; // To change body of implemented methods
										// use File | Settings | File Templates.
					}

					@Override
					public HttpUriRequest getRedirect(HttpRequest request,
							HttpResponse response, HttpContext context)
							throws ProtocolException {
						return null; // To change body of implemented methods
										// use File | Settings | File Templates.
					}
				}).build();
		return httpClient;
	}
}
