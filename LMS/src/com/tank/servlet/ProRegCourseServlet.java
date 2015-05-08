package com.tank.servlet;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.adl.parsers.dom.ADLOrganizations;

import com.tank.dao.SqlDao;
import com.tank.server.RTEHandler;

/**
 * @Method ProRegCourseServlet()
 * @Description 注册课程模块--对注册和删除的课程进行后台处理
 * @author liubin 2014.04.11
 * @return
 */
public class ProRegCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);// 若存在会话则返回该会话，否则新建一个会话。
		String username = (String) session.getAttribute("username");
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		String selectedCourses = "|";

		RTEHandler fileHandler = new RTEHandler();

		Enumeration<?> requestNames = request.getParameterNames();
		String testString = "Course-";
		while (requestNames.hasMoreElements()) {

			String paramName = (String) requestNames.nextElement();
			int locSkillId = paramName.indexOf(testString);

			// 从表单中取出复选框的name
			if (locSkillId != -1) {
				String courseID = paramName;
				selectedCourses += courseID + "|"; // 记录打勾的所有课程

				ResultSet userCourseRS = null;

				// 查询该课程是否已注册
				String sqlSelectUserCourse = "SELECT * FROM usercourseinfo WHERE UserName = "
						+ "'"
						+ username
						+ "'"
						+ " AND CourseID = '"
						+ courseID
						+ "'";

				userCourseRS = db.executeQuery(sqlSelectUserCourse);

				// 添加注册课程信息
				try {
					// 如果没有注册则插入用户与课程关联信息
					if (userCourseRS.next() == false) {
						String sqlInsertUserCourse = "INSERT INTO usercourseinfo (UserName, CourseID) VALUES("
								+ "'" + username + "'" + ",'" + courseID + "')";
						db.executeInsert(sqlInsertUserCourse);

						String theWebPath = getServletConfig()
								.getServletContext().getRealPath("/");

						fileHandler.setUsername(username);
						fileHandler.setCourseID(courseID);
						fileHandler.setTheWebPath(theWebPath);
						fileHandler.initializeCourseFiles();

						String courseSeqFile = theWebPath + "CourseImports\\"
								+ courseID + "\\sequence.obj";
						FileInputStream istream = new FileInputStream(
								courseSeqFile);
						ObjectInputStream ois = new ObjectInputStream(istream);

						ADLOrganizations sequenceObj = (ADLOrganizations) ois
								.readObject();
						istream.close();

						String sequencingFileName = theWebPath
								+ "CourseImports\\" + courseID + "\\sequence."
								+ username;
						java.io.File userSequence = new java.io.File(
								sequencingFileName);
						FileOutputStream ostream = new FileOutputStream(
								userSequence);
						ObjectOutputStream oos = new ObjectOutputStream(ostream);
						oos.writeObject(sequenceObj);
						oos.flush();
						oos.close();

						// 添加注册次数统计
						ResultSet regnum = null;
						String sqlSelectCourse = "SELECT RegNum FROM courseinfo WHERE CourseID = '"
								+ courseID + "'";

						regnum = db.executeQuery(sqlSelectCourse);
						if (regnum.next()) {
							int num = regnum.getInt("RegNum");
							num = num + 1;
							String sqlUpdateCourse = "UPDATE courseinfo SET RegNum = "
									+ num
									+ " WHERE CourseID = '"
									+ courseID
									+ "'";
							db.Update(sqlUpdateCourse);
						}
						
						//添加个人注册次数统计
						String sqlUpdateRegNum = "UPDATE userinfo SET regnum = regnum + 1 WHERE username = '" + username + "'";
						db.Update(sqlUpdateRegNum);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}

		// 取出数据库中所有课程
		ResultSet courseRS = null;
		String sqlSelectCourse = "SELECT * FROM courseinfo";
		courseRS = db.executeQuery(sqlSelectCourse);

		try {
			while (courseRS.next() == true) {

				String courseID = courseRS.getString("CourseID");
				if (selectedCourses.indexOf("|" + courseID + "|") == -1) {
					ResultSet userCourseRS = null;

					// 查询该用户已注册的课程信息
					String sqlSelectUserCourse = "SELECT * FROM usercourseinfo WHERE UserName = '"
							+ username + "' AND CourseID = '" + courseID + "'";
					userCourseRS = db1.executeQuery(sqlSelectUserCourse);

					if (userCourseRS.next() == true) {

						// 删除RTE环境中的Course
						String sqlDeleteUserCourse = "DELETE FROM usercourseinfo WHERE UserName = '"
								+ username
								+ "' AND CourseID = '"
								+ courseID
								+ "'";
						db2.executeDelete(sqlDeleteUserCourse);

						String theWebPath = getServletConfig()
								.getServletContext().getRealPath("/");
						fileHandler.setUsername(username);
						fileHandler.setCourseID(courseID);
						fileHandler.setTheWebPath(theWebPath);
						fileHandler.deleteCourseFiles();

						// 删除数据库记录中的UserSCOInfo
						String sqlDeleteUserSCO = "DELETE FROM userscoinfo WHERE UserName = '"
								+ username
								+ "' AND CourseID = '"
								+ courseID
								+ "'";
						db2.executeDelete(sqlDeleteUserSCO);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		db1.CloseDataBase();
		db2.CloseDataBase();
		request.getRequestDispatcher("/servlet/ViewCourse").forward(
				request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
