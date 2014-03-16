package net.atp.rcp.client.testlogin;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpHost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.protocol.HttpContext;

public class TestSSLConnectionSocketFactory extends SSLConnectionSocketFactory {

	public TestSSLConnectionSocketFactory(SSLContext sslContext,
			X509HostnameVerifier hostnameVerifier) {
		super(sslContext, hostnameVerifier);
		// TODO Auto-generated constructor stub
	}

	public Socket connectSocket(int connectTimeout, Socket socket,
			HttpHost host, InetSocketAddress remoteAddress,
			InetSocketAddress localAddress, HttpContext context)
			throws IOException {
		InetSocketAddress tmp = new InetSocketAddress(InetAddress.getByAddress(
				"", remoteAddress.getAddress().getAddress()),
				remoteAddress.getPort());
		System.err.println(remoteAddress.getAddress().getClass());
		System.err.println(tmp.getAddress().getClass());
		return super.connectSocket(connectTimeout, socket, host, tmp,
				localAddress, context);
	}
}
