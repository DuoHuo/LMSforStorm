package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;

/**
 * @Method SaveCourseNoteServlet()
 * @Description 笔记模块--保存学习课件时产生的笔记
 * @author liubin 2014.04.26
 * @return
 */
public class SaveCourseNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		request.setCharacterEncoding("UTF-8"); // 设置编码

		String username = (String) session.getAttribute("username");
		String courseID = (String) session.getAttribute("COURSEID");
		String content = request.getParameter("notebody");
		String noteStatus = request.getParameter("noteStatus");
		String courseTitle = null;

		SqlDao db = new SqlDao();

		// 查询课程标题
		ResultSet rs;
		String getTitleSql = "SELECT * FROM courseinfo WHERE CourseID = '"
				+ courseID + "'";
		rs = db.executeQuery(getTitleSql);
		try {
			if (rs.next()) {
				courseTitle = rs.getString("CourseTitle");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		// 添加笔记
		String sql = "INSERT INTO noteinfo (UserName, CourseTitle, NoteStatus, Content) VALUES('"
				+ username
				+ "','"
				+ courseTitle
				+ "','"
				+ noteStatus
				+ "','"
				+ content + "')";
		db.executeInsert(sql);

		db.CloseDataBase();

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/servlet/MenuServlet?courseID=" + courseID);
		rd.forward(request, response);
	}
}
