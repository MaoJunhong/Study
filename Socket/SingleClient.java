package net.atp.trader.client.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SingleClient {
	private static String ip = "127.0.0.1";

	public static void main(String[] args) {
		Socket s = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			s = new Socket(ip, MultiServer.port);
			s.setSoTimeout(10000);

			os = s.getOutputStream();
			os.write("abcdefg".getBytes());

			is = s.getInputStream();
			byte[] bt = new byte[1 << 10];
			int n = is.read(bt);
			System.out.println("client: " + new String(bt, 0, n));
			System.out.println("s: " + s + "\nos: " + os + "\nis: " + is);

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
