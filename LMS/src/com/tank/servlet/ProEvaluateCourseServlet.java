package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;

/**
 * @Method ProEvaluateCourseServlet()
 * @Description 评价课程模块--处理评价课程保存分数到数据库
 * @author liubin 2014.05.11
 * @return
 */
public class ProEvaluateCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);// 若存在会话则返回该会话，否则新建一个会话。
		String username = (String) session.getAttribute("username");

		String courseID = request.getParameter("courseID");
		String score = request.getParameter("score");

		SqlDao db = new SqlDao();
		String sqlUpdateEva = "UPDATE usercourseinfo SET Evaluate =" + score
				+ " WHERE UserName='" + username + "' AND CourseID='"
				+ courseID + "'";

		db.Update(sqlUpdateEva);

		//更新课程平均分
		SqlDao db1 = new SqlDao();
		String avgGrade = null;

		String sqlSelectCourseinfo = "SELECT CourseID , CourseTitle  FROM courseinfo";
		ResultSet courseInfoRS = db.executeQuery(sqlSelectCourseinfo);
		try {
		
			while (courseInfoRS.next()) {
				
				// 根据courseID查询usercourseinfo获得平均成绩
				// 注：mysql的AVG函数会自动去掉空值，不用担心未评分的课件会被计算进去
				String sqlSelectAvgEva = "SELECT AVG(Evaluate) AS avgPrice FROM usercourseinfo WHERE CourseID = '"
						+ courseID + "'";
				ResultSet AvgEvaRS = db1.executeQuery(sqlSelectAvgEva);
				if (AvgEvaRS.next()) {
					avgGrade = AvgEvaRS.getString("avgPrice");

					if (null == avgGrade || avgGrade.equals("")) {
						continue;
					}
					else{
						String sqlUpdateAvgEva = "update courseinfo set AvgEva=" + avgGrade + "where CourseID = '" + courseID + "'";
						db1.Update(sqlUpdateAvgEva);
					}
				}
			}
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		//更新评价人数
		String sqlUpdateEvaNum = "UPDATE courseinfo set EvaNum = EvaNum + 1 WHERE CourseID = '" + courseID + "'";
		db1.Update(sqlUpdateEvaNum);
		
		db.CloseDataBase();
		db1.CloseDataBase();
		
		
		request.getRequestDispatcher("/servlet/EvaluateCourseServlet").forward(
				request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
