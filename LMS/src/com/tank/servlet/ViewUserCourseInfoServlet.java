package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;
import com.tank.model.UserScoInfo;

/**
 * @Method ViewUserCourseInfoServlet()
 * @Description 管理员模块--查看用户学习信息
 * @author lwq 2014.05.23
 * @return
 */
public class ViewUserCourseInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public List<UserScoInfo> viewMyCourseScoInfoList = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// 处理业务逻辑
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String username = request.getParameter("userName");
		HttpSession session = request.getSession();
		session.setAttribute("viewspeuser", username);
		viewMyCourseScoInfoList = new ArrayList<UserScoInfo>();
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		SqlDao db3 = new SqlDao();
		UserScoInfo mainuserscoinfo;
		String time = null;
		// String sqlSelectMultyDB = "SELECT * FROM userscoinfo a,courseinfo b"+
		// " WHERE a.username = '"+ username +"' AND a.CourseID = b.CourseID";
		String sqlSelectMultyDB = "SELECT * , COUNT(DISTINCT CourseTitle) FROM"
				+ "(SELECT userscoinfo.LessonStatus , courseinfo.CourseTitle , userscoinfo.CourseID "
				+ "FROM userscoinfo ,courseinfo WHERE userscoinfo.UserName = '"
				+ username
				+ "' AND userscoinfo.CourseID = courseinfo.CourseID) "
				+ "AS total GROUP BY CourseTitle";

		// 查询userscoinfo、courseinfo两个表，查到CourseTitle,LessonStatus
		ResultSet userscoinfoRS = db.executeQuery(sqlSelectMultyDB);
		try {
			while (userscoinfoRS.next()) {
				boolean completed = true;
				// courseTitle和CourseID是不断变化的，注意这个取值的位置！
				String courseID = userscoinfoRS.getString("CourseID");
				String courseTitle = userscoinfoRS.getString("CourseTitle");
				// 查出改用户的一个课程 检查完成状态
				String sqlSelectComplete = "SELECT LessonStatus FROM userscoinfo WHERE UserName = '"
						+ username + "' AND CourseID = '" + courseID + "'";
				ResultSet userScoRS = db1.executeQuery(sqlSelectComplete);
				while (userScoRS.next()) {
					String lessonStatus = userScoRS.getString("LessonStatus");
					if (!(lessonStatus.equalsIgnoreCase("Completed"))
							&& !(lessonStatus.equalsIgnoreCase("Passed"))) {
						completed = false;
						break;
					}
				}
				// 查询noteinfo表，确定课堂笔记的数量
				String sqlSelectNoteinfo = "SELECT * FROM noteinfo WHERE UserName = '"
						+ username
						+ "' AND courseTitle = '"
						+ courseTitle
						+ "'";
				ResultSet noteNoRS = db2.executeQuery(sqlSelectNoteinfo);
				noteNoRS.last();
				int noteNo = noteNoRS.getRow();// 得到笔记的行数！

				// 查询课程播放的时间，确定totalTime.
				String sqlSelectCourseTotalTime = "SELECT SUM(TotalTime) AS time  FROM userscoinfo WHERE UserName = '"
						+ username + "' AND " + "CourseID = '" + courseID + "'";
				ResultSet totalTimeRS = db3
						.executeQuery(sqlSelectCourseTotalTime);
				if (totalTimeRS.next()) {
					time = totalTimeRS.getString("time");
				}
				if (null == time || time.equals("")) {
					time = "0";
				}
				String formatTotalTime = getFormatTime(time);
				// 至此，已经获得了课程的完成状态、对应笔记的数目,下面开始赋值
				mainuserscoinfo = new UserScoInfo();
				if (true == completed) {
					mainuserscoinfo.setLessonStatus("已通过");
				} else {
					mainuserscoinfo.setLessonStatus("未完成");
				}
				mainuserscoinfo.setTotalTime(formatTotalTime);
				mainuserscoinfo.setNoteNum(noteNo);
				mainuserscoinfo.setCourseTitle(userscoinfoRS
						.getString("CourseTitle"));
				viewMyCourseScoInfoList.add(mainuserscoinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		db1.CloseDataBase();
		db2.CloseDataBase();
		db3.CloseDataBase();
		request.setAttribute("MyCourseScoInfoList", viewMyCourseScoInfoList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin/viewusercourseinfo.jsp");
		rd.forward(request, response);
	}

	public String getFormatTime(String time) {
		String shour = null;
		String smin = null;
		String ssec = null;

		int formattime = Integer.parseInt(time);
		int hour = formattime / 10000;
		int min = (formattime - hour * 10000) / 100;
		int sec = formattime - hour * 10000 - min * 100;

		// 针对mysql对time类型的求和要转换进制
		if (sec > 60) {
			sec = sec - 60;
			min = min + 1;
		}
		if (min > 60) {
			min = min - 60;
			hour = hour + 1;
		}

		// 转换格式
		if (hour >= 0 && hour < 10) {
			shour = "0" + String.valueOf(hour);
		} else {
			shour = String.valueOf(hour);
		}

		if (min >= 0 && min < 10) {
			smin = "0" + String.valueOf(min);
		} else {
			smin = String.valueOf(min);
		}

		if (sec >= 0 && sec < 10) {
			ssec = "0" + String.valueOf(sec);
		} else {
			ssec = String.valueOf(sec);
		}

		String formatTime = shour + ":" + smin + ":" + ssec;
		return formatTime;
	}

}
