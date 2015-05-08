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
 * @Method DeleteNoteServlet()
 * @Description 笔记模块--删除笔记功能
 * @author liubin 2014.04.24
 * @return
 */
public class DeleteNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

//		HttpSession session = request.getSession();
//		String recourseTitle = (String) session.getAttribute("recourseTitle");
		request.setCharacterEncoding("UTF-8"); // 设置编码

		String noteID = request.getParameter("noteID");
		SqlDao db = new SqlDao();
		String sql = "DELETE FROM noteinfo WHERE NoteID = " + noteID;
		db.executeDelete(sql);
		db.CloseDataBase();

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/servlet/LoadGroupNoteServlet");
		rd.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
