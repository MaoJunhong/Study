package net.atp.trader.client.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class MultiClient {
	private static String ip = "127.0.0.1";
	public static String send[] = new String[] { "First", "Second", "Third" };

	public static void main(String[] args) {
		Socket s = null;
		OutputStream os = null;
		InputStream is = null;
		try {
			s = new Socket(ip, MultiServer.port);

			os = s.getOutputStream();
			is = s.getInputStream();
			System.out.println("s: " + s + "\nos: " + os + "\nis: " + is);

			byte[] bt = new byte[1 << 10];
			for (String msg : send) {
				os.write(msg.getBytes());
				int n = is.read(bt);
				System.out.println("client: " + new String(bt, 0, n));
			}

		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
