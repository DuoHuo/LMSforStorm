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
 * @Method ShowMyCourseInfoServlet()
 * @Description 个人中心模块--查看个人课程信息
 * @author lwq 2014.04.19  changed by lwq 2014.8.13 修复查询bug
 * @return
 */
public class ShowMyCourseInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public List<UserScoInfo> viewMyCourseScoInfoList = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// 处理业务逻辑
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		String username = (String) session.getAttribute("username");
		viewMyCourseScoInfoList = new ArrayList<UserScoInfo>();
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		SqlDao db3 = new SqlDao();
		SqlDao db4 = new SqlDao();
		UserScoInfo mainuserscoinfo;
		String time = null;
		String score = null;
		
		//查询userscoinfo表得到CourseID、LessonStatus、CourseTitle
		String sql = "SELECT * , COUNT(DISTINCT CourseTitle) FROM(SELECT userscoinfo.LessonStatus , courseinfo.image , courseinfo.CourseTitle ,"+
					"userscoinfo.CourseID FROM userscoinfo ,courseinfo WHERE userscoinfo.UserName = '"+ username +"'"+
					" AND userscoinfo.CourseID = courseinfo.CourseID) AS total GROUP BY CourseTitle";
		ResultSet rs = db.executeQuery(sql);
		try {
			String pic;

			while (rs.next()) {
				
				boolean completed = true;
				String courseID = rs.getString("CourseID");
				String courseTitle = rs.getString("courseTitle");
				int image = rs.getInt("image");

				String sql4 = "SELECT SUM(Score) AS score FROM userscoinfo WHERE CourseID='"+ courseID +"' AND UserName='"+username+"'";
				ResultSet rs4 = db4.executeQuery(sql4);
				if(rs4.next()){
					score = rs4.getString("score");
				}
				// 查出该用户的一个课程 检查完成状态
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
				String sqlSelectNoteinfo = "SELECT * FROM noteinfo WHERE UserName = '"+ username+ "' AND courseTitle = '"+ courseTitle+"'";
				ResultSet noteNoRS = db2.executeQuery(sqlSelectNoteinfo);
				noteNoRS.last();
				int noteNo = noteNoRS.getRow();

				// 查询课程播放的时间，确定totalTime.
				String sqlSelectCourseTotalTime = "SELECT SUM(TotalTime) AS time  FROM userscoinfo WHERE UserName = '"
													+ username + "' AND " + "CourseID = '" + courseID + "'";
				ResultSet totalTimeRS = db3.executeQuery(sqlSelectCourseTotalTime);
				if (totalTimeRS.next()) {
					time = totalTimeRS.getString("time");
				}
				if (null == time || time.equals("")) {
					time = "0";
				}
				String formatTotalTime = getFormatTime(time);

				if (score.equals("0") || score.equals(" ") || score == null) {
					score = "未测试/无测试题";
				} else {
					score = score + "分";
				}

				mainuserscoinfo = new UserScoInfo();
				if (true == completed) {
					mainuserscoinfo.setLessonStatus("已通过");
				} else {
					mainuserscoinfo.setLessonStatus("未完成");
				}
				if(0 == image)
					pic = "null";
				else 
					pic = courseID;
				mainuserscoinfo.setImage(pic);
				mainuserscoinfo.setScore(score);
				mainuserscoinfo.setTotalTime(formatTotalTime);
				mainuserscoinfo.setNoteNum(noteNo);
				mainuserscoinfo.setCourseTitle(courseTitle);
				viewMyCourseScoInfoList.add(mainuserscoinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		db1.CloseDataBase();
		db2.CloseDataBase();
		db3.CloseDataBase();
		db4.CloseDataBase();
		request.setAttribute("MyCourseScoInfoList", viewMyCourseScoInfoList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/centercourseinfo.jsp");
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