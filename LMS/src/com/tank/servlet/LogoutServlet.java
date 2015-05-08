package com.tank.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @Method LogoutServlet()
 * @Description 注销信息处理
 * @author liubin 2014.03.27
 * @return
 */
public class LogoutServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);

		// 销毁session
		session.invalidate();

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/login.jsp");
		rd.forward(request, response);
	}

}
