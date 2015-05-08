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

import com.tank.dao.SqlDao;
import com.tank.model.UserScoInfo;

/**
 * @Method ShowCourseInfoServlet()
 * @Description 管理员模块--从数据库读取课程相关信息
 * @author lwq 2014.04.21
 * @return
 */
public class ShowCourseInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public List<UserScoInfo> adminmanagecourseList = null;
	UserScoInfo adminmanagecourse;

	public ShowCourseInfoServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	// 处理业务逻辑
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		adminmanagecourseList = new ArrayList<UserScoInfo>();
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		String sumtime = null;
		String avgGrade = null;

		String sqlSelectCourseinfo = "SELECT CourseID , CourseTitle , RegNum FROM courseinfo";
		ResultSet courseInfoRS = db.executeQuery(sqlSelectCourseinfo);
		try {
			while (courseInfoRS.next()) {
				String courseID = courseInfoRS.getString("CourseID");

				// 根据courseID查询userscoinfo获得播放总时间
				String sqlSelectSumTime = "SELECT SUM(TotalTime) AS sumtime FROM userscoinfo WHERE CourseID = '"
						+ courseID + "'";
				ResultSet SumTimeRS = db1.executeQuery(sqlSelectSumTime);
				if (SumTimeRS.next()) {
					sumtime = SumTimeRS.getString("sumtime");
				}
				if (null == sumtime || sumtime.equals("")) {
					sumtime = "0";
				}
				// 得到格式化的总时间
				String formatSumTime = getFormatTime(sumtime);

				// 根据courseID查询usercourseinfo获得平均成绩
				// 注：mysql的AVG函数会自动去掉空值，不用担心未评分的课件会被计算进去
				String sqlSelectAvgEva = "SELECT AVG(Evaluate) AS avgPrice FROM usercourseinfo WHERE CourseID = '"
						+ courseID + "'";
				ResultSet AvgEvaRS = db2.executeQuery(sqlSelectAvgEva);
				if (AvgEvaRS.next()) {
					avgGrade = AvgEvaRS.getString("avgPrice");
					if (null == avgGrade || avgGrade.equals("")) {
						avgGrade = "暂无评分";
					}
				}
				// 所需数据均查找完毕，开始赋值
				adminmanagecourse = new UserScoInfo();
				adminmanagecourse.setCourseID(courseID);
				adminmanagecourse.setTotalTime(formatSumTime);
				adminmanagecourse.setEvaluate(avgGrade);
				adminmanagecourse.setCourseTitle(courseInfoRS.getString("CourseTitle"));
				adminmanagecourse.setRegNum(courseInfoRS.getInt("RegNum"));
				adminmanagecourseList.add(adminmanagecourse);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		db1.CloseDataBase();
		db2.CloseDataBase();

		request.setAttribute("adminmanagecourse", adminmanagecourseList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin/admanacour.jsp");
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
