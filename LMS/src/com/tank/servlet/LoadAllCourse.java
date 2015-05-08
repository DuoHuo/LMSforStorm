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
 * @Method LoadAllCourse()
 * @Description 读取所有课程  下载
 * @author liubin 2014.08.2
 * @return
 */
public class LoadAllCourse extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public List<Course> courseList = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);// 若存在会话则返回该会话，否则新建一个会话。
		String username = (String) session.getAttribute("username");
		String screenshot = (String) request.getParameter("screenshot");
		courseList = new ArrayList<Course>();

		ResultSet courseRS = null;
		
		SqlDao rs = new SqlDao();
		
		String sqlSelectCourse = "SELECT * FROM courseinfo WHERE Active = 1";
		// 查询所有课程
		courseRS = rs.executeQuery(sqlSelectCourse);

		try {
			Course course;
			while (courseRS.next()) {
				String pic = "";
				String courseID = courseRS.getString("CourseID");
				String courseTitle = courseRS.getString("CourseTitle");
				int image = courseRS.getInt("image");
				if(0 == image)
					pic = "null";
				else 
					pic = courseID;
				course = new Course();
				course.setCourseID(courseID);
				course.setCourseTitle(courseTitle);
				course.setImage(pic);
				courseList.add(course);
			}

		} catch (SQLException e) {

			e.printStackTrace();
		}
		request.setAttribute("courseList", courseList);

		if(screenshot.equals("b")){
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/centerscreenshot.jsp");
			rd.forward(request, response);
		}
		else{
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/centerdownload.jsp");
			rd.forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}
