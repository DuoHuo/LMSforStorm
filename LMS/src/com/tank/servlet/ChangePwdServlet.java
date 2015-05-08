/*******************************************************************************
 **
 ** Filename:  ChangePwdServlet
 **
 ** File Description:  实现密码修改
 **
 ** Auther:            lwq
 **
 ** Added date:        2014.5.1
 **
 *******************************************************************************/
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
 * @Method ChangePwdServlet()
 * @Description 个人中心--修改密码
 * @author lwq 2014.04.15
 * @return
 */
public class ChangePwdServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	// 处理业务逻辑
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html;charset=utf-8");
		String oldpwd = null;
		String newpwd = null;
		String newpwdconfirm = null;

		oldpwd = request.getParameter("oldPwd");
		newpwd = request.getParameter("newPwd");
		newpwdconfirm = request.getParameter("newPwdConfirm");

		{
			SqlDao db = new SqlDao();
			// 判断用户名是否存在
			String sqlSelect = "SELECT userName FROM userinfo WHERE Password = '"
					+ oldpwd + "'";
			ResultSet rs = db.executeQuery(sqlSelect);
			try {
				if (!rs.next()) {
					request.setAttribute("err",
							"<font color=\"red\">不存在您要修改的密码，请确认您的密码</font>");
					RequestDispatcher rd = getServletContext()
							.getRequestDispatcher(
									"/changePwd.jsp");
					rd.forward(request, response);
				} else {
					String username = rs.getString(1);
					String sqlUpdate = "UPDATE userinfo SET Password = '"
							+ newpwd + "' WHERE userName = '" + username + "'";
					int flag = 0;
					flag = db.Update(sqlUpdate);
					if (flag == 1) {
						request.setAttribute("err",
								"<font color=\"black\">修改密码成功！</font>");
						RequestDispatcher rd = getServletContext()
								.getRequestDispatcher(
										"/changePwd.jsp");
						rd.forward(request, response);
					} else {
						request.setAttribute("err",
								"<font color=\"red\">修改密码失败。</font>");
						RequestDispatcher rd = getServletContext()
								.getRequestDispatcher(
										"/changePwd.jsp");
						rd.forward(request, response);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			db.CloseDataBase();
		}

	}
}