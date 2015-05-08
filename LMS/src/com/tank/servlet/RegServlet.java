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
 * @Method RegServlet()
 * @Description 登录注册模块--处理注册信息
 * @author lwq 2014.03.26
 * @return
 */
public class RegServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// 构造方法
	public RegServlet() {
		super();
	}

	// 销毁方法
	public void destroy() {
		super.destroy();
	}

	// doPost方法用doGet（）方法来处理
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	// 处理业务逻辑
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		String username = null;
		String password = null;
		String pwdConfirm = null;
		String imgPath = null;
		username = request.getParameter("username");
		password = request.getParameter("password");
		pwdConfirm = request.getParameter("pwdConfirm");
		imgPath = request.getParameter("secuserimg");
		{
			SqlDao db = new SqlDao();
			// 判断用户名是否存在
			String sql = "SELECT UserName FROM userinfo WHERE UserName='"+ username + "'";
			ResultSet rs = db.executeQuery(sql);
			try {
				if (rs.next()) {
					request.setAttribute("err",
							"<font color=\"red\">该用户已经存在！</font>");
					RequestDispatcher rd = getServletContext()
							.getRequestDispatcher("/reg.jsp");
					rd.forward(request, response);
				} else {
					sql = "INSERT INTO userinfo(UserName,Password,Admin,UserFace,ShareNum,RegNum) VALUES ('"
							+ username + "', '" + password + "','0','"+ imgPath +"','0','0')";
					int flag = 0;
					flag = db.executeInsert(sql);
					// 注册成功
					if (flag == 1) {
						request.setAttribute("err",
								"<font color=\"red\">注册成功！请登录</font>");
						RequestDispatcher rd = getServletContext()
								.getRequestDispatcher("/login.jsp");
						rd.forward(request, response);
					} else {
						request.setAttribute("err",
								"<font color=\"red\">注册失败！</font>");
						RequestDispatcher rd = getServletContext()
								.getRequestDispatcher("/login.jsp");
						rd.forward(request, response);
					}
				}

			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	// 初始化方法
	public void init() throws ServletException {
	}
}
