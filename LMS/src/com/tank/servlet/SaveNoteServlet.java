package com.tank.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;

/**
 * @Method SaveNoteServlet()
 * @Description 笔记模块--保存非课堂产生的笔记信息
 * @author liubin 2014.04.24
 * @return
 */
public class SaveNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);

		request.setCharacterEncoding("UTF-8"); // 设置编码

		String username = (String) session.getAttribute("username");
		String courseTitle = request.getParameter("title");
		String noteID = request.getParameter("noteID");
		String content = request.getParameter("notebody");
		String noteStatus = request.getParameter("noteStatus");

		SqlDao db = new SqlDao();
		// 添加笔记
		if ("" == noteID || null == noteID) {
			String sql = "INSERT INTO noteinfo (UserName, CourseTitle, NoteStatus, Content) VALUES('"
					+ username
					+ "','"
					+ courseTitle
					+ "','"
					+ noteStatus
					+ "','" + content + "')";
			db.executeInsert(sql);
		}
		// 更新笔记
		else {
			String sql = "UPDATE noteinfo SET NoteStatus = '" + noteStatus
					+ "' , Content = '" + content + "' where NoteID = "
					+ noteID;
			db.Update(sql);
		}
		db.CloseDataBase();

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/servlet/LoadGroupNoteServlet");
		rd.forward(request, response);
	}
}