package net.atp.trader.client.test.net;

import java.io.IOException;
import java.net.ServerSocket;

public class ThreadServer {
	public static int port = 10000;

	public static void main(String[] args) {
		ServerSocket ss = null;

		try {
			ss = new ServerSocket(port);

			while (true) {
				new LogicThread(ss.accept()).start();
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
