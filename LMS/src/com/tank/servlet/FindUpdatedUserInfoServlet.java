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
import com.tank.model.Users;

/**
 * @Method FindUpdatedUserInfoServlet()
 * @Description 管理员更新用户信息--查询用户信息
 * @author lwq 2014.04.16
 * @return
 */
public class FindUpdatedUserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	Users user;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		request.setCharacterEncoding("utf-8");
		String userID = request.getParameter("userID");
		user = new Users();
		user.setUserID(Integer.parseInt(userID));
		SqlDao db = new SqlDao();
		String sqlSelect = "SELECT * FROM userinfo WHERE UserID = '" + userID
				+ "'";
		ResultSet rs = db.executeQuery(sqlSelect);
		try {
			if (rs.next()) {

				user.setUserName(rs.getString("userName"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		session.setAttribute("findeduserinfo", user);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin/updateuserinfo.jsp");
		rd.forward(request, response);
	}

	public void init() throws ServletException {
	}

}
