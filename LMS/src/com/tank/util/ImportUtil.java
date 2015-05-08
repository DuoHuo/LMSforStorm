package com.tank.util;

import java.io.FileNotFoundException;
import java.io.FileReader;

import org.xml.sax.InputSource;

public class ImportUtil {

	/**
	 * @Method setUpInputSource()
	 * @Description 创建输入流
	 * @author liubin 2014.04.01
	 * @return
	 */
	public InputSource setUpInputSource(String fileName) {
		InputSource is = new InputSource();
		is = setupFileSource(fileName);
		return is;
	}

	/**
	 * @Method setupFileSource()
	 * @Description 返回输入流，由setUpInputSource()调用
	 * @author liubin 2014.04.01
	 * @return
	 */
	private InputSource setupFileSource(String filename) {
		try {
			java.io.File xmlFile = new java.io.File(filename);
			if (xmlFile.isFile()) {
				FileReader fr = new FileReader(xmlFile);
				InputSource is = new InputSource(fr);
				return is;
			} else {
			}
		} catch (NullPointerException npe) {
			System.out.println("Null pointer exception" + npe);
		} catch (SecurityException se) {
			System.out.println("Security Exception" + se);
		} catch (FileNotFoundException fnfe) {
			System.out.println("File Not Found Exception" + fnfe);
		}
		return new InputSource();
	}

}
