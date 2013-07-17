package net.atp.trader.client.test.log;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogJULTest {

	/**
	 * 日志输出，7个等级
	 * */
	private static void writeMessage(Logger logger) {
		logger.log(Level.SEVERE, "severe");
		logger.log(Level.WARNING, "warning");
		logger.log(Level.INFO, "info");
		logger.log(Level.CONFIG, "config");
		logger.log(Level.FINE, "fine");
		logger.log(Level.FINER, "finer");
		logger.log(Level.FINEST, "finest");
	}

	/**
	 * 测试默认设置 输出INFO以上等级的日志
	 * */
	@SuppressWarnings("deprecation")
	public static void testAcquiesce() {
		Logger logger = Logger.getLogger("global"); // Logger.global is ok if
													// [Logger.getLogger("global")]
													// called before
		System.out.println(logger == Logger.global); // true
		writeMessage(logger);
		// writeMessage(Logger.global);
		// Logger.global.log(Level.INFO, "Logger.global.info");
	}

	/**
	 * 获取记录所有层次的日志记录器
	 * */
	private static Logger getAllLevelLogger() {
		Logger logger = Logger.getLogger("global");
		logger.setLevel(Level.ALL); // 设置日志记录器层次

		Handler handler = new ConsoleHandler();
		handler.setLevel(Level.ALL); // 设置日志处理器层次

		logger.addHandler(handler);
		logger.setUseParentHandlers(false); // do not send log to its father
											// handler

		return logger;
	}

	/**
	 * 测试改变输出日志的默认等级 需要改变日志记录器和日志处理器
	 * */
	public static void testChangLevel() {
		writeMessage(getAllLevelLogger());
	}

	/**
	 * 测试跟踪程序执行流程
	 * */
	public static void testProgramFlow() {
		Logger logger = getAllLevelLogger();

		logger.entering("net.atp.trader.client.widgets.test.LoggerUtils",
				"testProgramFlow"); // 进入
		writeMessage(logger);
		logger.exiting("net.atp.trader.client.widgets.test.LoggerUtils",
				"testProgramFlow"); // 退出
	}

	/**
	 * 测试异常记录, divisor/dividend
	 * */
	public static void testExceptionRecords(double dividend, double divisor) {
		Logger logger = getAllLevelLogger();

		try {
			if (dividend == 0) {
				Exception exception = new Exception("dividend is 0.");
				logger.throwing(
						"net.atp.trader.client.widgets.test.LoggerUtils",
						"testExceptionRecords", exception); // place 1
				throw exception;
			}
		} catch (Exception e) {
			logger.log(Level.WARNING, "dividend is 0.", e); // place 2
		}
	}

	/**
	 * 获取文件的完整路径，空则以时间命名
	 * */
	private static String getLogName(String fileName) {
		StringBuffer logPath = new StringBuffer();
		logPath.append(System.getProperty("user.home"));
		logPath.append("/Documents/LogFiles");
		File file = new File(logPath.toString());
		if (!file.exists()) {
			file.mkdir();
		}

		if (fileName == null || fileName == "") {
			logPath.append("/");
		} else {
			logPath.append("/" + fileName + ".");
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		logPath.append(sdf.format(new Date()) + ".log");

		// System.out.println(logPath.toString());
		return logPath.toString();
	}

	public static Logger getFileLogger(String fileName) {
		Logger logger = Logger.getLogger("local" + fileName);
		try {
			FileHandler fh = new FileHandler(getLogName(fileName), true);
			logger.addHandler(fh);// 日志输出文件
			logger.setLevel(Level.ALL);
			fh.setFormatter(new SimpleFormatter());// 输出格式，默认格式XML
			logger.addHandler(new ConsoleHandler());// 输出到控制台
			logger.setUseParentHandlers(false);
		} catch (SecurityException e) {
			logger.log(Level.SEVERE, "安全性错误", e);
		} catch (IOException e) {
			logger.log(Level.SEVERE, "读取文件日志错误", e);
		}
		return logger;
	}

	/**
	 * 测试记录输出到文件
	 * */
	public static void testFileRecords() {
		writeMessage(getFileLogger(""));
	}

	/**
	 * 测试层次
	 * 
	 * failed
	 * */
	public static void testLevel() {
		Logger log1 = getFileLogger("a");
		Logger log2 = getFileLogger("a.b");
		log2.setLevel(Level.WARNING);
		log1.info("log1");
		log2.info("log2");
	}

	/**
	 * 测试自定义Formatter
	 * */
	public static void testMyFormatter() {
		Logger logger = Logger.getLogger("local");
		ConsoleHandler handler = new ConsoleHandler();
		handler.setFormatter(new Formatter() {
			@Override
			public String format(LogRecord record) {
				return "[my handler] " + record.getLevel() + ": "
						+ record.getMessage() + "\n";
			}
		});
		logger.addHandler(handler);
		logger.setUseParentHandlers(false);
		writeMessage(logger);
	}

	public static void main(String[] args) {
		// testAcquiesce();
		// testChangLevel();
		// testProgramFlow();
		// testExceptionRecords(0, 0);
		// testFileRecords();
		// testLevel();
		testMyFormatter();
	}
}