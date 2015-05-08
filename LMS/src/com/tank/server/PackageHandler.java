package com.tank.server;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

public class PackageHandler {
	public static ZipFile zf;

	/**
	 * @Method PackageHandler()
	 * @Description 构造函数
	 * @author liubin 2014.03.29
	 * @return
	 */
	public PackageHandler() {
	}

	/**
	 * @Method findManifest()
	 * @Description 解压缩Zip文件中的xml文件
	 * @author liubin 2014.03.29
	 * @return
	 */
	public static String extract(String zipFileName, String extractedFile,
			String pathOfExtract) {
		String nameOfExtractedFile = new String("");

		try {
			String pathAndName = new String("");

			// 拿到zip输入流
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));

			int indexOfFileBeginning = extractedFile.lastIndexOf("/") + 1;
			nameOfExtractedFile = extractedFile.substring(indexOfFileBeginning);

			pathAndName = pathOfExtract + "\\" + nameOfExtractedFile;

			OutputStream out = new FileOutputStream(pathAndName);

			ZipEntry entry;
			byte[] buf = new byte[1024];
			int len;
			int flag = 0;

			while (flag != 1) {
				entry = in.getNextEntry();

				if ((entry.getName()).equalsIgnoreCase(extractedFile)) {
					flag = 1;
				}
			}

			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			in.close();
		} catch (IOException e) {
			System.out.println("IO Exception Caught: " + e);
		}

		return nameOfExtractedFile;
	}

	/**
	 * @Method findManifest()
	 * @Description 查找Zip文件中的imsmanifest.xml文件
	 * @author liubin 2014.03.29
	 * @return
	 */
	public static boolean findManifest(String zipFileName) {
		boolean rtn = false;

		try {
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));

			ZipEntry entry;
			int flag = 0;

			while ((flag != 1) && (in.available() != 0)) {
				entry = in.getNextEntry();

				if (in.available() != 0) {
					if ((entry.getName()).equalsIgnoreCase("imsmanifest.xml")) {
						flag = 1;
						rtn = true;
					}
				}
			}

			in.close();
		} catch (IOException e) {
			System.out.println("IO Exception Caught: " + e);
		}
		return rtn;
	}

	/**
	 * @Method findMetadata()
	 * @Description 查找Zip文件中的Xml文件
	 * @author liubin 2014.03.30
	 * @return
	 */
	public static boolean findMetadata(String zipFileName) {
		boolean rtn = false;
		String suffix = ".xml";

		try {
			// 拿到zip输入流
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));

			ZipEntry entry;

			while ((in.available() != 0)) {
				entry = in.getNextEntry();

				if (in.available() != 0) {
					if ((entry.getName()).endsWith(suffix)) {
						rtn = true;
					}
				}
			}

			in.close();
		} catch (IOException e) {
			System.out.println("IO Exception Caught: " + e);
		}
		return rtn;
	}

	/**
	 * @Method locateMetadata()
	 * @Description 获取zip文件中xml数据流，由getListOfMetadata方法调用
	 * @author liubin 2014.03.30
	 * @return
	 */
	public static Vector locateMetadata(String zipFileName) {
		Vector metaDataVector = new Vector();
		String suffix = ".xml";

		try {
			// 拿到Zip文件输入流
			ZipInputStream in = new ZipInputStream(new FileInputStream(zipFileName));

			ZipEntry entry;

			while ((in.available() != 0)) {
				entry = in.getNextEntry();

				if (in.available() != 0) {
					if ((entry.getName()).endsWith(suffix)) {
						metaDataVector.addElement(entry.getName());
					}
				}
			}
			in.close();

		} catch (IOException e) {
			System.out.println("IO Exception Caught: " + e);
		}
		return metaDataVector;
	}

	/**
	 * @Method getListOfMetadata()
	 * @Description 获取zip文件中xml数据列表
	 * @author liubin 2014.03.30
	 * @return
	 */
	public static String getListOfMetadata(String zipFile) {
		Vector mdVector = new Vector();
		mdVector = locateMetadata(zipFile);

		String mdString = new String();
		mdString = mdVector.toString();

		return mdString;
	}

}
