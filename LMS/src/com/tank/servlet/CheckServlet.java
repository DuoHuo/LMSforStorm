package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;

/**
 * @Method CheckServlet()
 * @Description 登录信息校验
 * @author liubin 2014.03.27
 * @return
 */
public class CheckServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	// doPost方法用doGet（）方法来处理
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=utf-8");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String kind = request.getParameter("kind");
		SqlDao db = new SqlDao();
		String sql = "SELECT * FROM userinfo WHERE UserName = '" + username
				+ "' AND Password ='" + password + "'";
		ResultSet rs = db.executeQuery(sql);
		try {
			if (rs.next()) {
				String admin = rs.getString("Admin");
				if (kind.equals("user") && (admin.equals("0"))
						|| (kind.equals("manager") && (admin.equals("1")))) {
					goo(request, response, kind);
				} else {
					request.setAttribute("err",
							"<font color=\"red\">用户名或密码错误</font>");
					RequestDispatcher rd = getServletContext()
							.getRequestDispatcher("/login.jsp");
					rd.forward(request, response);
					System.err.println("用户名或密码错误！");
					doError(request, response, "用户名或密码错误！");
				}

			} else {
				request.setAttribute("err",
						"<font color=\"red\">用户名或密码错误</font>");
				RequestDispatcher rd = getServletContext()
						.getRequestDispatcher("/login.jsp");
				rd.forward(request, response);
				System.err.println("用户名或密码错误！");
				doError(request, response, "用户名或密码错误！");
			}

		} catch (Exception e) {
			System.err.println("用户名或密码错误！" + e.getMessage());
			System.out.print("用户名或密码错误！" + e.getMessage());
		}
		db.CloseDataBase();
	}

	// 处理业务逻辑
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// 不同权限跳转至不同的页面
	private void goo(HttpServletRequest request, HttpServletResponse response,
			String kind) throws ServletException, IOException {

		if (kind.equals("user")) {
			String username = request.getParameter("username");
			HttpSession session = request.getSession(true);// 若存在会话则返回该会话，否则新建一个会话。
			session.setAttribute("username", username);// 把用户名写入session中
			session.setAttribute("admin", false);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/index.jsp");
			rd.forward(request, response);
		}
		if (kind.equals("manager")) {
			String username = request.getParameter("username");
			HttpSession session = request.getSession(true);// 若存在session则返回该session，否则新建一个session。
			session.setAttribute("username", username);// 把用户名写入session中
			session.setAttribute("admin", true);
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/index.jsp");
			rd.forward(request, response);
		}
	}

	private void doError(HttpServletRequest request,
			HttpServletResponse response, String string) {
		request.setAttribute("problem", string);
		getServletConfig().getServletContext().getRequestDispatcher(
				"/errorpage.jsp");
	}

	// 初始化方法
	public void init() throws ServletException {
	}
}
