package com.tank.servlet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tank.dao.SqlDao;
import com.tank.datamodels.SCODataManager;
import com.tank.datamodels.cmi.CMICore;

/**
 * @Method LMSCMIServlet()
 * @Description LMS-sco交互模块--处理sco与lms环境的交互信息
 * @author liubin 2014.05.15
 * @return
 */
public class LMSCMIServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private String scoFile;
	private String username;
	private String courseID;
	private String scoID;
//	private boolean logoutFlag;

	private SCODataManager scoData;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		try {
			scoID = (String) request.getParameter("scoID");
			courseID = (String) request.getParameter("courseID");
			username = (String) request.getParameter("username");
			
			String theWebPath = getServletConfig().getServletContext()
					.getRealPath("/");

			scoFile = theWebPath + "SampleRTEFiles\\" + username + "\\"
					+ courseID + "\\" + scoID;
			// 创建输入流
			ObjectInputStream in = new ObjectInputStream(
					request.getInputStream());

			// 创建输出流
			ObjectOutputStream out = new ObjectOutputStream(
					response.getOutputStream());

			String command = (String) in.readObject();

			if (command.equalsIgnoreCase("cmiputcat")) {
				SCODataManager inSCOData = (SCODataManager) in.readObject();

				HandleData(inSCOData);
			} else if (command.equalsIgnoreCase("cmigetcat")) {
				// 创建文件输入流
				FileInputStream fi = new FileInputStream(scoFile);

				// 创建对象输入流
				ObjectInputStream file_in = new ObjectInputStream(fi);

				scoData = (SCODataManager) file_in.readObject();
				scoData.getCore().setSessionTime("00:00:00.0");

				file_in.close();
				out.writeObject(scoData);
			} else {
				String err_msg = "invalid command";
				out.writeObject(err_msg);
			}

			in.close();
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method HandleData()
	 * @Description LMS--sco交互模块--操作交互数据方法
	 * @author liubin 2014.05.15
	 * @return
	 */
	private void HandleData(SCODataManager inSCOData) {
		try {
			String lessonStatus = new String();
			String lessonExit = new String();
			String lessonEntry = new String();

			CMICore lmsCore = inSCOData.getCore();

//			if (lmsCore.getExit().getValue().equalsIgnoreCase("logout")) {
//				logoutFlag = true;
//			}

			lessonStatus = lmsCore.getLessonStatus().getValue();
			lessonExit = lmsCore.getExit().getValue();
			lessonEntry = lmsCore.getEntry().getValue();

			// 在磁盘写入更新的数据
			inSCOData.setCore(lmsCore);

			FileOutputStream fo = new FileOutputStream(scoFile);
			ObjectOutputStream out_file = new ObjectOutputStream(fo);

			out_file.writeObject(inSCOData);
			out_file.close();

			// 更新数据库数据
			SqlDao db = new SqlDao();

			String sqlUpdateUserSCO = "UPDATE userscoinfo SET LessonStatus = '"
					+ lessonStatus + "' , isExit = '" + lessonExit
					+ "', Entry = '" + lessonEntry + "'" + "WHERE UserName = '"
					+ username + "' AND CourseID = '" + courseID
					+ "' AND SCOID = '" + scoID + "'";

			db.Update(sqlUpdateUserSCO);

//			ResultSet userSCORS = null;
//			String sqlSelectUserSCO = "SELECT * FROM userscoinfo WHERE UserName = '"
//					+ username
//					+ "'AND CourseID = '"
//					+ courseID
//					+ "' AND SCOID = '" + scoID + "'";
//
//			userSCORS = db.executeQuery(sqlSelectUserSCO);
//			while (userSCORS.next()) {
//				String newStatus = userSCORS.getString("LessonStatus");
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
