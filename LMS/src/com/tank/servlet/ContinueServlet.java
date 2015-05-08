package com.tank.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;

public class ContinueServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession(false);
		String username = (String) session.getAttribute("username");
		
		SqlDao db = new SqlDao();
		String SqlSelectLast = "Select LastCourse from userinfo where username = '" + username + "'";
		
		ResultSet rs = db.executeQuery(SqlSelectLast);
		String courseID = null;
		try {
			if(rs.next()){
				courseID = rs.getString("LastCourse");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if( courseID != null ){
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/play/coursePlay.jsp?courseID=" + courseID);
			rd.forward(request, response);
		}
		else{
			request.setAttribute("flag", "yes");
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher( "/servlet/RegCourse"); 
			rd.forward(request, response);
		}
			
	}
		
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
