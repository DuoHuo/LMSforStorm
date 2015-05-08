package com.tank.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tank.dao.SqlDao;

/**
 * @Method DelUserServlet()
 * @Description 系统管理模块--删除用户功能
 * @author lwq 2014.04.15
 * @return
 */
public class DelUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String userID = request.getParameter("userID");
		SqlDao db = new SqlDao();
		String sqlDelUser = "DELETE FROM userinfo WHERE userID = '" + userID
				+ "'";
		db.executeDelete(sqlDelUser);
		db.CloseDataBase();
		request.getRequestDispatcher(
				"/servlet/ShowUserInfoServlet?action=showAllUser").forward(
				request, response);
	}

}
