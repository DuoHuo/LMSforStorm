package com.tank.server;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;

import com.tank.dao.SqlDao;
import com.tank.datamodels.SCODataManager;
import com.tank.datamodels.cmi.CMICommentsFromLms;
import com.tank.datamodels.cmi.CMICore;
import com.tank.datamodels.cmi.CMILaunchData;
import com.tank.datamodels.cmi.CMIScore;
import com.tank.datamodels.cmi.CMIStudentData;
import com.tank.datamodels.cmi.CMIStudentPreference;
import com.tank.datamodels.cmi.CMISuspendData;

public class RTEHandler {
	private String username;
	private String courseID;
	private String sampleRTERoot;
	private String scoID;
	private String location;
	private String masteryScore;
	private String parameterString;
	private String prerequisites;
	private String dataFromLMS;
	private String maxTimeAllowed;
	private String timeLimitAction;
	private String theWebPath;

	private int sequence;

	public RTEHandler() {
		username = new String();
		courseID = new String();
		scoID = new String();
		location = new String();
		masteryScore = new String();
		parameterString = new String();
		prerequisites = new String();
		dataFromLMS = new String();
		maxTimeAllowed = new String();
		timeLimitAction = new String();
		sequence = 0;
		sampleRTERoot = "SampleRTEFiles";
		theWebPath = new String();
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTheWebPath() {
		return theWebPath;
	}

	public void setTheWebPath(String theWebPath) {
		this.theWebPath = theWebPath;
	}

	/**
	 * @Method initializeCourseFiles()
	 * @Description 初始化RTE环境
	 * @author liubin 2014.04.05
	 * @return
	 */
	public void initializeCourseFiles() {
		try {
			// 创建RTE环境中用户课程记录相关文件
			String sampleRTEPath = theWebPath + sampleRTERoot;
			String userDir = sampleRTEPath + "\\" + username + "\\" + courseID;

			File theRTESCODataDir = new File(userDir);

			// 检查课程文件是否存在
			if (!theRTESCODataDir.isDirectory()) {
				theRTESCODataDir.mkdirs();
			}

			SqlDao db = new SqlDao();
			SqlDao db1 = new SqlDao();

			// 从数据库中获取公共课程信息
			ResultSet itemRS = null;
			String sqlSelectItem = "SELECT * FROM ItemInfo WHERE CourseID = '"
					+ courseID + "'";
			itemRS = db.executeQuery(sqlSelectItem);

			while (itemRS.next()) {
				String type = itemRS.getString("Type");
				if (type.equals("sco") || type.equals("asset")) {
					scoID = itemRS.getString("Identifier");
					location = itemRS.getString("Launch");
					masteryScore = itemRS.getString("MasteryScore");
					prerequisites = itemRS.getString("Prerequisites");
					parameterString = itemRS.getString("ParameterString");
					dataFromLMS = itemRS.getString("DataFromLMS");
					maxTimeAllowed = itemRS.getString("MaxTimeAllowed");
					timeLimitAction = itemRS.getString("TimeLimitAction");
					sequence = itemRS.getInt("Sequence");

					String RTESCODataFile = userDir + "\\" + scoID;

					File theRTESCODataFile = new File(RTESCODataFile);

					SCODataManager rteSCOData = new SCODataManager();
					rteSCOData = initSCOData();

					// 将用户课程信息写到服务器
					FileOutputStream fo = new FileOutputStream(RTESCODataFile);
					ObjectOutputStream out = new ObjectOutputStream(fo);
					out.writeObject(rteSCOData);
					out.close();

					// 将用户课程信息插入数据库
					String sqlInsertUserSCO = "INSERT INTO UserSCOInfo (username, CourseID, SCOID, Launch, ParameterString, "
							+ "LessonStatus, Prerequisites, IsExit, Entry, MasteryScore, Sequence, Type) "
							+ "VALUES('"
							+ username
							+ "','"
							+ courseID
							+ "','"
							+ scoID
							+ "','"
							+ location
							+ "','"
							+ parameterString
							+ "', 'not attempted','"
							+ prerequisites
							+ "','','ab-initio','"
							+ masteryScore
							+ "',"
							+ sequence
							+ ",'"
							+ type
							+ "')";

					db1.executeInsert(sqlInsertUserSCO);

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method deleteCourseFiles()
	 * @Description 删除RTE环境的相关课程记录文件
	 * @author liubin 2014.04.05
	 * @return
	 */
	public void deleteCourseFiles() {
		try {
			String sampleRTEPath = theWebPath + sampleRTERoot;

			String userDir = sampleRTEPath + "\\" + username + "\\" + courseID;

			File theRTESCODataDir = new File(userDir);

			File scoFiles[] = theRTESCODataDir.listFiles();

			for (int i = 0; i < scoFiles.length; i++) {
				scoFiles[i].delete();
			}

			theRTESCODataDir.delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method initSCOData()
	 * @Description 初始化RTE环境scodata
	 * @author liubin 2014.04.06
	 * @return
	 */
	private SCODataManager initSCOData() {
		SCODataManager tmpSCOData = new SCODataManager();

		try {
			// 数据库获取用户信息
			SqlDao db = new SqlDao();

			String sqlSelectUser = "SELECT * FROM UserInfo WHERE username = "
					+ "'" + username + "'";

			ResultSet userRS = null;
			userRS = db.executeQuery(sqlSelectUser);

			if (userRS.next()) {
				username = userRS.getString("username");
			}

			String studentName = username;

			// 创建CMICore类
			CMIScore tmpScore = new CMIScore();
			tmpScore.setRaw("0");
			tmpScore.setMax("0");
			tmpScore.setMin("0");

			CMICore tmpCore = new CMICore();
			tmpCore.setStudentId(username);
			tmpCore.setStudentName(studentName);
			tmpCore.setLessonLocation(location);
			tmpCore.setCredit("credit");
			tmpCore.setLessonStatus("not attempted");
			tmpCore.setEntry("ab-initio");
			tmpCore.setTotalTime("00:00:00.0");
			tmpCore.setLessonMode("normal");
			tmpCore.setExit("");
			tmpCore.setScore(tmpScore);
			tmpSCOData.setCore(tmpCore);

			// 建立挂起数据
			CMISuspendData tmpSuspendData = new CMISuspendData();
			tmpSuspendData.setSuspendData("");
			tmpSCOData.setSuspendData(tmpSuspendData);

			// 建立开始数据
			CMILaunchData tmpLaunchData = new CMILaunchData();
			tmpLaunchData.setLaunchData(dataFromLMS);
			tmpSCOData.setLaunchData(tmpLaunchData);

			// 建立请求
			CMICommentsFromLms tmpComments = new CMICommentsFromLms("");
			tmpSCOData.setCommentsFromLMS(tmpComments);

			// 建立学生数据
			CMIStudentData tmpStudentData = new CMIStudentData();

			tmpStudentData.setMasteryScore(masteryScore);
			tmpStudentData.setMaxTimeAllowed(maxTimeAllowed);
			tmpStudentData.setTimeLimitAction(timeLimitAction);
			tmpSCOData.setStudentData(tmpStudentData);

			// 建立学生相关信息
			CMIStudentPreference tmpStudentPreference = new CMIStudentPreference();
			tmpStudentPreference.setAudio("3");
			tmpStudentPreference.setLanguage("English");
			tmpStudentPreference.setSpeed("4");
			tmpStudentPreference.setText("10");
			tmpSCOData.setStudentPreference(tmpStudentPreference);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return tmpSCOData;
	}

}
