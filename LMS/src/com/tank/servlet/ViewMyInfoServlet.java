package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;
import com.tank.model.Users;

/**
 * @Method ViewMyInfoServlet()
 * @Description 个人中心模块--查看我的个人信息
 * @author lwq 2014.04.23
 * @return
 */
public class ViewMyInfoServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public List<Users> viewMyInfoList = null;

	public ViewMyInfoServlet() {
		super();
	}

	public void destroy() {
		super.destroy();
	}

	// doGet方法用doPost（）方法来处理
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// doPost方法处理业务逻辑
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(true);
		viewMyInfoList = new ArrayList<Users>();
		String username = (String) session.getAttribute("username");
		SqlDao db = new SqlDao();
		String sqlSlectUserInfo = "SELECT * FROM userinfo WHERE UserName = '"
				+ username + "'";
		ResultSet userInfoRS = db.executeQuery(sqlSlectUserInfo);
		try {
			Users myinfo;
			while (userInfoRS.next()) {
				myinfo = new Users();
				myinfo.setIsAdmin(userInfoRS.getInt("Admin"));
				myinfo.setPassword(userInfoRS.getString("Password"));
				myinfo.setUserID(userInfoRS.getInt("UserID"));
				myinfo.setUserName(userInfoRS.getString("UserName"));
				viewMyInfoList.add(myinfo);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db.CloseDataBase();
		request.setAttribute("viewMyInfoList", viewMyInfoList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/mhome/mycenter/myinfo.jsp");
		rd.forward(request, response);
	}
}