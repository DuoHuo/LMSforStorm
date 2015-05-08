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
 * @Method RegCourseServlet()
 * @Description 注册课程模块--数据库查询出平台可以注册的课程到浏览器
 * @author liubin 2014.04.10
 * @return
 */
public class RegCourseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public List<Course> courseList = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);// 若存在会话则返回该会话，否则返回登录页面
		
		String username = (String) session.getAttribute("username");
		
		courseList = new ArrayList<Course>();

		ResultSet userCourseRS = null;
		ResultSet courseRS = null;
		SqlDao rs = new SqlDao();
		String sqlSelectUserCourse = "SELECT * FROM usercourseinfo WHERE UserName = "
				+ "'" + username + "'";
		String sqlSelectCourse = "SELECT * FROM courseinfo WHERE Active = 1";

		// 查询用户已注册课程
		userCourseRS = rs.executeQuery(sqlSelectUserCourse);

		String userCourses = "|";
		try {
			while (userCourseRS.next()) {
				userCourses += userCourseRS.getString("CourseID") + "|";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 查询所有课程
		courseRS = rs.executeQuery(sqlSelectCourse);

		try {
			Course course;
			while (courseRS.next()) {
				String pic = "";
				String courseID = courseRS.getString("CourseID");
				String courseTitle = courseRS.getString("CourseTitle");
				String author = courseRS.getString("Author");
				String regnum = courseRS.getString("RegNum");
				int evanum = courseRS.getInt("EvaNum");
				int avgGrade = (int)(courseRS.getFloat("AvgEva") + 0.5);//四舍五入取整
				
				int image = courseRS.getInt("image");
				if(0 == image)
					pic = "null";
				else 
					pic = courseID;
				course = new Course();
				if (userCourses.indexOf("|" + courseID + "|") != -1) {
					course.setChecked("checked");
				}
				else course.setChecked("unchecked");
				
				if( avgGrade != 0 ){
					String evaluate[] = new String[5];
					avgGrade--;
					evaluate[avgGrade] = "checked";
					course.setEvaluate(evaluate);
				}
				
				course.setCourseID(courseID);
				course.setCourseTitle(courseTitle);
				course.setImage(pic);
				course.setRegnum(regnum);
				course.setEvanum(evanum);
				course.setAuthor(author);
				courseList.add(course);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("courseList", courseList);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/share.jsp");
		rd.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}
