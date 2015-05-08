package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tank.dao.SqlDao;

/**
 * @Method AddNewUserServlet()
 * @Description 系统管理--用户管理--添加新用户
 * @author lwq 2014.04.15
 * @return
 */
public class AddNewUserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");

		String userName = null;
		String pwd = null;
		String pwdConfirm = null;
		String isAdmin = null;

		userName = request.getParameter("userName");
		pwd = request.getParameter("password");
		pwdConfirm = request.getParameter("pwdConfirm");
		isAdmin = request.getParameter("isAdmin");
		int Admin;
		if (isAdmin.equals("No")) {
			Admin = 0;
		} else {
			Admin = 1;
		}

		SqlDao db = new SqlDao();
		String sqlSelect = "SELECT * FROM userinfo WHERE userName = '"+ userName + "'";
		ResultSet rs = db.executeQuery(sqlSelect);
		try {
			if (rs.next()) {
				request.setAttribute("err",
						"<font color=\"red\">此用户名已经存在，请重新输入！</font>");
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/admin/addnewuser.jsp");
				rd.forward(request, response);
			} else {
				String sqlInsert = "INSERT INTO userinfo(userName,Password,Admin,UserFace) "+ "VALUES ('"
						+ userName+ "', '"+ pwd+ "','"+ Admin+ "','user7')";
				int flag = db.executeInsert(sqlInsert);
				db.CloseDataBase();

				if (flag == 1) {
					request.setAttribute("err",
							"<font color=\"black\">添加用户成功！</font>");
					RequestDispatcher rd = getServletContext()
							.getRequestDispatcher(
									"/admin/addnewuser.jsp");
					rd.forward(request, response);
				} else {
					request.setAttribute("err",
							"<font color=\"red\">添加用户失败,请重新添加。</font>");
					RequestDispatcher rd = getServletContext()
							.getRequestDispatcher("/admin/addnewuser.jsp");
					rd.forward(request, response);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
	}

}