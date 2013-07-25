package net.atp.trader.client.test.net;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class LogicThread extends Thread {
	Socket s = null;
	InputStream is = null;
	OutputStream os = null;

	public LogicThread(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		try {
			is = s.getInputStream();
			os = s.getOutputStream();
			System.out.println("s: " + s + "\nos: " + os + "\nis: " + is);

			byte[] bt = new byte[1 << 10];
			for (int idx = 0; idx < MultiClient.send.length; ++idx) {
				int n = is.read(bt);
				System.out.println("server: " + new String(bt, 0, n));
				os.write(logic(bt, 0, n), 0, n);
			}
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

	private byte[] logic(byte[] bt, int i, int n) {
		byte[] res = new byte[n];
		System.arraycopy(bt, 0, res, 0, n);
		return res;
	}

}
