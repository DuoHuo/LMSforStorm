package com.tank.servlet;
import javax.servlet.*;
import javax.servlet.http.*;

import com.tank.dao.SqlDao;
import com.tank.model.Course;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
public class SearchServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	public List<Course> courseList = null;
	
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);// 若存在会话则返回该会话，否则新建一个会话。
    	
		String username = (String) session.getAttribute("username");
		String search = request.getParameter("search");//拿到关键词
		
		courseList = new ArrayList<Course>();

		ResultSet userCourseRS = null;
		ResultSet courseRS = null;
		SqlDao rs = new SqlDao();
		String sqlSelectUserCourse = "SELECT * FROM usercourseinfo WHERE UserName = "
				+ "'" + username + "'";
		String sqlSelectCourse = "SELECT * FROM courseinfo WHERE CourseTitle like '%" + search + "%' And Active = 1";

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
				int avgGrade = (int)(courseRS.getFloat("AvgEva") + 0.5);//四舍五入取整
				
				int image = courseRS.getInt("image");
				if(0 == image)
					pic = "null";
				else 
					pic = courseID;
				course = new Course();
				//设置是否已注册标示
				if (userCourses.indexOf("|" + courseID + "|") != -1) {
					course.setChecked("checked");
				}
				else course.setChecked("unchecked");
				//设置评价星星的分数
				if( avgGrade != 0 ){
					String evaluate[] = new String[5];
					avgGrade--;
					evaluate[avgGrade] = "checked";
					course.setEvaluate(evaluate);
				}
				
				course.setCourseID(courseID);
				course.setCourseTitle(courseTitle);
				course.setRegnum(regnum);
				course.setAuthor(author);
				course.setImage(pic);
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
