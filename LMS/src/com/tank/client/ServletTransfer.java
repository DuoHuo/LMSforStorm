package com.tank.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.net.URLConnection;

public class ServletTransfer {
	/**
	 * @Method postObjects()
	 * @Description 调用Servlet实现LMS与SCO的传输数据通道(包括数据库更新)
	 * @author liubin 2014.04.21
	 * @return
	 */
	public static ObjectInputStream postObjects(URL servlet,
			Serializable objs[], String sessionid) throws Exception {
		URLConnection con;

		con = servlet.openConnection();

		con.setDoInput(true);
		con.setDoOutput(true);
		con.setUseCaches(false);
		con.setRequestProperty("Content-Type", "text/plain");
		con.setAllowUserInteraction(false);
		con.setRequestProperty("Cookie", sessionid);

		// 建立输出流
		ObjectOutputStream out;
		out = new ObjectOutputStream(con.getOutputStream());

		int numObjects = objs.length;

		for (int x = 0; x < numObjects; x++) {
			out.writeObject(objs[x]);
		}

		out.flush();
		out.close();

		// 建立输出流,此时请求LMSCMIServlet
		ObjectInputStream in;
		in = new ObjectInputStream(con.getInputStream());

		return in;
	}
}
