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
import com.tank.model.Course;

/**
 * @Method EvaluateCourseServlet()
 * @Description 评价课程模块--读取已完成课程的页面
 * @author liubin 2014.05.11
 * @return
 */
public class EvaluateCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public List<Course> comList = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		comList = new ArrayList<Course>();
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
		// 查询用户所有的课程的courseID
		String sqlSelectUserCourse = "SELECT * FROM usercourseinfo WHERE UserName = '"
				+ username + "'";
		ResultSet usercourseRS = db.executeQuery(sqlSelectUserCourse);
		Course course;
		try {
			while (usercourseRS.next()) {
				boolean completed = true;
				String courseID = usercourseRS.getString("CourseID");
				int score = usercourseRS.getInt("Evaluate");//取出以往评价的分数，若为评价分数为0

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
				// 检查后确定是完成的课程则放入容器
				if (true == completed) {

					String sqlSelectCourse = "SELECT  CourseTitle,image  FROM courseinfo WHERE CourseID = '"
							+ courseID + "'";
					ResultSet courseRS = db1.executeQuery(sqlSelectCourse);
					
					String pic = "";
					
					if (courseRS.next()) {
						course = new Course();
						String courseTitle = courseRS.getString("CourseTitle");
						int image = courseRS.getInt("image");
						course.setCourseTitle(courseTitle);
						course.setCourseID(courseID);
						//存入分数信息
						if( score != 0 ){
							String evaluate[] = new String[5];
							score--;
							evaluate[score] = "checked";
							course.setEvaluate(evaluate);
						}
						//存入图片信息
						if(0 == image)
							pic = "null";
						else 
							pic = courseID;
						course.setImage(pic);
						comList.add(course);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		db.CloseDataBase();
		db1.CloseDataBase();
		request.setAttribute("comList", comList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/centerevaluate.jsp");
		rd.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
