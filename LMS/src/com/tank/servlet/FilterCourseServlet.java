package com.tank.servlet;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.adl.parsers.dom.ADLOrganizations;

import com.tank.dao.SqlDao;
import com.tank.server.RTEHandler;

/**
 *@function 过滤完成和未完成的课程 
 *@author lwq 2014.8.14
 *@description 判断将要播放的课程是否已经完成，未完成就跳到播放界面
 *				完成跳到用户选择界面
 */
public class FilterCourseServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		String control = request.getParameter("control");
		String courseId  = request.getParameter("courseID");
		
		//判断课程是否通过或者完成，跳到不同页面
		if(control.equals("base")){
			RequestDispatcher rd = null;
			int flag = isCoursePass(username, courseId);
			session.setAttribute("coutseID", courseId);
			if(flag == 1){
				//完成，跳到选择界面
				rd = getServletContext().getRequestDispatcher("/play/filterpage.jsp");
			}else{
				//未完成，跳到播放界面
				rd = getServletContext().getRequestDispatcher("/play/coursePlay.jsp");
			}
			rd.forward(request, response);
		}
		if(control.equals("restudy")){
			resetCourseStatus(username, courseId);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/play/coursePlay.jsp?courseID="+courseId);
			rd.forward(request, response);
		}
		if(control.equals("passed")){
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/play/filterpage.jsp");
			rd.forward(request, response);
		}
	}
	
	public int isCoursePass(String username, String courseID){
		boolean completed = true;
		SqlDao db = new SqlDao();
		ResultSet rs = null;
		String sql = "SELECT LessonStatus FROM userscoinfo WHERE UserName='"+username+"' AND CourseID ='"+courseID+"'";
		
		rs = db.executeQuery(sql);
		try {
			while (rs.next()) {
				String lessonStatus = rs.getString("LessonStatus");
				if (!(lessonStatus.equalsIgnoreCase("Completed"))
						&& !(lessonStatus.equalsIgnoreCase("Passed"))) {
					completed = false;
					break;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.CloseDataBase();
		if(completed == true){
			return 1;   //completed == true表示课程已经完成
		}else{
			return 0;	//completed == false表示课程没有完成
		}
	}
	
	public void resetCourseStatus(String username, String courseID){
		System.out.println(username + courseID);
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
//		String sql = "UPDATE userscoinfo SET LessonStatus='not attempted' WHERE UserName='"+username
//					+"' AND CourseID='"+courseID+"'";
//		db.Update(sql);
//		db.CloseDataBase();
		ResultSet userCourseRS = null;

		// 删除RTE环境
		String sqlSelectUserCourse = "SELECT * FROM usercourseinfo WHERE UserName = '"
				+ username + "' AND CourseID = '" + courseID + "'";
		userCourseRS = db.executeQuery(sqlSelectUserCourse);
		
		// 删除RTE环境中的Course
		RTEHandler fileHandler = new RTEHandler();
		
		try {
			if (userCourseRS.next() == true) {

				
				String sqlDeleteUserCourse = "DELETE FROM usercourseinfo WHERE UserName = '"
						+ username
						+ "' AND CourseID = '"
						+ courseID
						+ "'";
				db1.executeDelete(sqlDeleteUserCourse);

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
				db1.executeDelete(sqlDeleteUserSCO);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//重新添加RTE环境
		try {
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
				FileInputStream istream = null;
					istream = new FileInputStream(
							courseSeqFile);
				ObjectInputStream ois = null;
					ois = new ObjectInputStream(istream);

				ADLOrganizations sequenceObj = null;
				try {
					sequenceObj = (ADLOrganizations) ois.readObject();
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				istream.close();

				String sequencingFileName = theWebPath
						+ "CourseImports\\" + courseID + "\\sequence."
						+ username;
				java.io.File userSequence = new java.io.File(sequencingFileName);
				FileOutputStream ostream = null;
				ostream = new FileOutputStream(userSequence);
				ObjectOutputStream oos = null;
				oos = new ObjectOutputStream(ostream);
				oos.writeObject(sequenceObj);
				oos.flush();
				oos.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		db.CloseDataBase();
		db1.CloseDataBase();
	}
}