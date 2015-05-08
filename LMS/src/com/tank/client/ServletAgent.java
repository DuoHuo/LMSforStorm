package com.tank.client;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.net.URL;

import com.tank.datamodels.SCODataManager;

public class ServletAgent {
	private URL servletURL;
	private String sessionid;

	/**
	 * @Method ServletAgent()
	 * @Description 构造函数
	 * @author liubin 2014.04.20
	 * @return
	 */
	public ServletAgent(URL url, String sessionid) {
		this.servletURL = url;
		this.sessionid = sessionid;
	}

	/**
	 * @Method GetSCOData()
	 * @Description 获取相关学生数据与sco数据
	 * @author liubin 2014.04.20
	 * @return
	 */
	public SCODataManager GetSCOData() {
		try {
			String servletCommand = "cmigetcat";

			Serializable[] data = { servletCommand };

			// 建立输入对象流
			ObjectInputStream in = ServletTransfer.postObjects(this.servletURL,
					data, sessionid);

			// 从文件中读入对象流,并转换成scoDataManager类型
			SCODataManager scoData = (SCODataManager) in.readObject();

			in.close();

			return scoData;
		} catch (Exception e) {
			e.printStackTrace();
			SCODataManager empty = new SCODataManager();
			return empty;
		}
	}

	/**
	 * @Method PutSCOData()
	 * @Description 设置相关学生数据与sco数据
	 * @author liubin 2014.04.20
	 * @return
	 */
	public String PutSCOData(SCODataManager scoData) {
		try {
			String servletCommand = "cmiputcat";

			Serializable[] data = { servletCommand, scoData };

			ObjectInputStream in = ServletTransfer.postObjects(this.servletURL,
					data, sessionid);

			in.close();
			String result = ("OK");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "FAILED";
		}
	}
}
