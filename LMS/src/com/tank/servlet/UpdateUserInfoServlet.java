package com.tank.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tank.dao.SqlDao;

/**
 * @Method UpdateUserInfoServlet()
 * @Description 管理员模块--更改用户信息
 * @author lwq 2014.04.22
 * @return
 */
public class UpdateUserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public UpdateUserInfoServlet() {
		super();
	}

	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String userID = request.getParameter("userID");
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		SqlDao db = new SqlDao();
		String sqlUpdate = "UPDATE userinfo SET UserName='" + userName
				+ "' , password='" + password + "' WHERE userID='" + userID
				+ "'";
		int rs = db.Update(sqlUpdate);
		if (rs == 1) {
			request.setAttribute("err", "<font color=\"blue\">用户更新成功！</font>");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/servlet/FindUpdatedUserInfoServlet?userID="+userID);
			rd.forward(request, response);
		} else {
			request.setAttribute("err", "<font color=\"red\">用户更新失败！</font>");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/servlet/FindUpdatedUserInfoServlet?userID="+userID);
			rd.forward(request, response);
		}
		db.CloseDataBase();
	}
}