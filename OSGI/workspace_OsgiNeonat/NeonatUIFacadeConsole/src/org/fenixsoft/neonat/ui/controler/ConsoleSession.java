package org.fenixsoft.neonat.ui.controler;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * 每一个 ConsoleSession对应于一个通过控制台进行访问的用户
 * 
 * @author IcyFenix
 */
public class ConsoleSession implements Runnable {

	private ConsoleCommandor nc;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;

	public BufferedReader getReader() {
		return in;
	}

	public PrintWriter getWriter() {
		return out;
	}

	public ConsoleSession(Socket socket) throws IOException {
		this.nc = new ConsoleCommandor(this);
		this.socket = socket;
		this.in = new BufferedReader(new InputStreamReader(
				socket.getInputStream(), "utf-8"));
		this.out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
				socket.getOutputStream(), "utf-8")), true);
	}

	@Override
	public void run() {
		String cmdLine;
		nc.doCommand("welcome");
		while (true) {
			try {
				while (!in.ready()) {
					Thread.sleep(300);
				}
				cmdLine = in.readLine();
				if (cmdLine == null) {
					break;
				} else {
					nc.doCommand(cmdLine);
				}
			} catch (SocketTimeoutException e) {
				System.out.println("客户端超过" + ConsoleManager.SOCKET_TIME + "毫秒未响应，自动断开连接");
				try {
					socket.close();
				} catch (IOException e1) {
				}
				return;
			} catch (Exception e) {
				e.printStackTrace(out);
			}
		}
	}
}
