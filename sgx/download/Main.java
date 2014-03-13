package com.sgx.download;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sgx.download.http.DownLoader;
import com.sgx.download.zip.Zipper;

public class Main {
	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private static String user = "stlragphcwk";
	private static String password = "SS82W10R781Di6";
	private static String zipFilePath = "blotter/blotter.zip";

	public static void main(String[] args) throws Exception {
		PropertyConfigurator
				.configure("conf/log4j.properties");

		long start = System.currentTimeMillis(), end;

		DownLoader.login(user, password);
		end = System.currentTimeMillis();
		logger.info("login time: " + (end - start));
		start = end;

		DownLoader.download("12 Mar 2014", "13 Mar 2014", zipFilePath);
		end = System.currentTimeMillis();
		logger.info("download time: " + (end - start));
		start = end;

		Zipper.unzip(zipFilePath);
		end = System.currentTimeMillis();
		logger.info("unzip time: " + (end - start));
	}
}
