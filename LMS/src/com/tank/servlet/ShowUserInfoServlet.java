package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tank.dao.SqlDao;
import com.tank.model.Users;

/**
 * @Method ShowUserInfoServlet()
 * @Description 管理员模块--读取所有用户信息
 * @author lwq 2014.04.22
 * @return
 */
public class ShowUserInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private ArrayList<Users> showUserInfoList;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// 处理业务逻辑
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		showUserInfoList = new ArrayList<Users>();
		SqlDao db = new SqlDao();
		String sqlSelect = "SELECT * FROM userinfo ";
		ResultSet rs = db.executeQuery(sqlSelect);
		try {
			while (rs.next()) {
				Users user = new Users();
				user.setUserID(rs.getInt("userID"));
				user.setUserName(rs.getString("userName"));
//				user.setPassword(rs.getString("password"));
				user.setIsAdmin(rs.getInt("Admin"));
				showUserInfoList.add(user);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		request.setAttribute("showUserInfoList", showUserInfoList);

		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/admin/manageusers.jsp");
		rd.forward(request, response);
	}
}