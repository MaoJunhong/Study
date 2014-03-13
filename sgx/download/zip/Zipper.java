package com.sgx.download.zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class Zipper {
	/** 文件缓冲区大小 （越小说明写文件的次数越多，并且越耗时， 当然也不是越大越好，需要考虑JVM所占用内存的大小，一般取值1024和2048） */
	private static final int LEN_BUFFER = 2048;

	/**
	 * 采用 <code>ZipInputStream<code>读Zip文件的方式
	 * 解压到当前目录
	 * 
	 * @param zipFilePath
	 *            需要解压的文件的路径
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath) throws IOException {
		File zipFile = new File(zipFilePath);
		String zipFileName = zipFile.getName();
		String rootFileName = zipFile.getParentFile().getPath() + "/"
				+ zipFileName.substring(0, zipFileName.lastIndexOf("."));

		unzip(zipFilePath, rootFileName);
	}

	/**
	 * 采用 <code>ZipInputStream<code>读Zip文件的方式
	 * 解压到指定目录
	 * 
	 * @param zipFilePath
	 *            需要解压的文件的路径
	 * @param unzipFilePath
	 *            解压后文件的路径
	 * @throws IOException
	 */
	public static void unzip(String zipFilePath, String unzipFilePath)
			throws IOException {
		File rootFile = new File(unzipFilePath);
		rootFile.mkdirs();

		ZipInputStream input = new ZipInputStream(new BufferedInputStream(
				new FileInputStream(zipFilePath)));
		ZipEntry entry = null;

		// Zip文件将里面的每个文件都作为一个ZipEntry, 父目录和子文件为两个单独的entry
		while ((entry = input.getNextEntry()) != null) {
			File tmpFile = new File(unzipFilePath + "/" + entry.getName());
			tmpFile.getParentFile().mkdirs();

			if (entry.isDirectory()) {
				tmpFile.mkdir();
			} else {
				BufferedOutputStream output = new BufferedOutputStream(
						new FileOutputStream(tmpFile));
				byte[] datas = new byte[LEN_BUFFER];
				int count;
				while ((count = input.read(datas)) != -1) {
					output.write(datas, 0, count);
				}
				output.close();
			}

			input.closeEntry();
		}

		input.close();
	}

	/**
	 * 采用 <code>ZipFile.getInputStream(ZipEntry)<code>读Zip文件的方式
	 * 解压到当前目录
	 * 
	 * @param zipFilePath
	 *            需要解压的文件的路径
	 * @throws IOException
	 */
	public static void unzip2(String zipFilePath) throws IOException {
		File zipFile = new File(zipFilePath);
		String zipFileName = zipFile.getName();
		String rootFileName = zipFile.getParentFile().getPath() + "/"
				+ zipFileName.substring(0, zipFileName.lastIndexOf("."));

		unzip2(zipFilePath, rootFileName);
	}

	/**
	 * 采用 <code>ZipFile.getInputStream(ZipEntry)<code>读Zip文件的方式
	 * 解压到指定目录
	 * 
	 * @param zipFilePath
	 *            需要解压的文件的路径
	 * @param unzipFilePath
	 *            解压后文件的路径
	 * @throws IOException
	 */
	public static void unzip2(String zipFilePath, String unzipFilePath)
			throws IOException {
		File unzipFile = new File(unzipFilePath);
		unzipFile.mkdirs();

		ZipFile zipFile = new ZipFile(zipFilePath);
		// Zip文件将里面的每个文件都作为一个ZipEntry, 父目录和子文件为两个单独的entry
		for (Enumeration<? extends ZipEntry> e = zipFile.entries(); e
				.hasMoreElements();) {
			ZipEntry entry = e.nextElement();

			File tmpFile = new File(unzipFilePath + "/" + entry.getName());
			tmpFile.getParentFile().mkdirs();

			if (entry.isDirectory()) {
				tmpFile.mkdir();
				continue;
			}

			BufferedOutputStream output = new BufferedOutputStream(
					new FileOutputStream(tmpFile));
			BufferedInputStream input = new BufferedInputStream(
					zipFile.getInputStream(entry));

			byte[] bytes = new byte[LEN_BUFFER];
			int len;
			while ((len = input.read(bytes)) != -1) {
				output.write(bytes, 0, len);
			}

			output.close();
			input.close();
		}
		zipFile.close();
	}

}
