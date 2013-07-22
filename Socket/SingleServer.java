package net.atp.trader.client.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServer {
	public static int port = 10000;

	public static void main(String[] args) {
		ServerSocket ss = null;
		Socket s = null;
		InputStream is = null;
		OutputStream os = null;

		try {
			ss = new ServerSocket(port);

			s = ss.accept();
			is = s.getInputStream();
			byte[] bt = new byte[1 << 10];
			int n = is.read(bt);
			System.out.println("server: " + new String(bt, 0, n));

			os = s.getOutputStream();
			// os.write(bt, 0, n);
			os.write(new String(bt, 0, n).getBytes());
			System.out.println("s: " + s + "\nos: " + os + "\nis: " + is);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
				os.close();
				s.close();
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
