package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
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
 * @Method ViewCourseServlet()
 * @Description 播放课程模块--数据库查询已经注册的课程
 * @author liubin 2014.04.18
 * @return
 */
public class ViewCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public List<Course> viewList = null;

	// doGet方法用doPost（）方法来处理
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	// 处理业务逻辑
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		viewList = new ArrayList<Course>();
		String username = (String) session.getAttribute("username");
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();

		String sqlSelectUserCourse = "SELECT CourseInfo.CourseID, "
				+ "CourseInfo.CourseTitle, CourseInfo.Image FROM CourseInfo, UserCourseInfo "
				+ "WHERE UserCourseInfo.username = "
				+ "'"
				+ username
				+ "'"
				+ " AND "
				+ "CourseInfo.CourseID = UserCourseInfo.CourseID AND CourseInfo.Active = 1 ORDER BY CourseInfo.CourseTitle";

		ResultSet userCourseRS = db.executeQuery(sqlSelectUserCourse);

		try {
			
			Course course;
			String pic;
			while (userCourseRS.next()) {
				
				boolean completed = true;
				boolean started = false;
				
				course = new Course();
				
				String courseID = userCourseRS.getString("CourseID");
				String courseTitle = userCourseRS.getString("CourseTitle");
				
				course.setCourseID(courseID);
				course.setCourseTitle(courseTitle);
				int image = userCourseRS.getInt("image");
				
				// 查出该用户的一个课程 检查完成状态
				String sqlSelectComplete = "SELECT LessonStatus FROM userscoinfo WHERE UserName = '"
						+ username + "' AND CourseID = '" + courseID + "'";
				ResultSet userScoRS = db1.executeQuery(sqlSelectComplete);
				while (userScoRS.next()) {
					String lessonStatus = userScoRS.getString("LessonStatus");
					if (!(lessonStatus.equalsIgnoreCase("Completed"))
							&& !(lessonStatus.equalsIgnoreCase("Passed"))) {
						completed = false;
					}
					if (!(lessonStatus.equalsIgnoreCase("not attempted"))){
						started = true;
					}
				}
				
				if (true == completed) {
					course.setLessonStatus("已通过");
				} else if(false == started){
					course.setLessonStatus("未开启");
				}
				else{
					course.setLessonStatus("未完成");
				}
				
				if(0 == image)
					pic = "null";
				else 
					pic = userCourseRS.getString("CourseID");
				course.setImage(pic);
				viewList.add(course);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		db.CloseDataBase();
		request.setAttribute("viewList", viewList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/study.jsp");
		rd.forward(request, response);
	}
}
