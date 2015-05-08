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

/**
 * @Method PlayEngineServlet()
 * @Description 播放课程----断点续播功能---章节选择功能---翻页功能
 * @author liubin 2014.05.02
 * @return
 */
public class PlayEngineServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 记录课程相关和请求类型
		boolean courseComplete = true; // 课程是否完成
		boolean wasAMenuRequest = false;// 一个菜单请求
		boolean wasANextRequest = false;// 下一页请求
		boolean wasAPrevRequest = false;// 上一页请求
		boolean wasFirstSession = false;// 第一次请求
		boolean empty_block = false;// 没有内容的空块
		// 下一个将被打开的课程
		String nextItemToLaunch = new String();
		// 请求类型
		String buttonType = new String();
		// 打开的单元是否是 sco 或 asset
		String type = new String();
		// is the item a block with no content
		String item_type = new String();
		// 章节号
		String identifier = new String();

		HttpSession session = request.getSession(false);// 若存在会话则返回该会话，否则新建一个会话。

		String username = (String) session.getAttribute("username");
		String courseID = (String) request.getParameter("courseID");
		String requestedSCO = (String) request.getParameter("scoID");// 拿到请求的sco章节ID
																		// 即Identifier
																		// 属于菜单请求
		buttonType = (String) request.getParameter("button");// 拿到请求的按键类型 属于翻页请求

		if ((!(requestedSCO == null)) && (!requestedSCO.equals(""))) {
			wasAMenuRequest = true;
		} else if ((!(buttonType == null)) && (buttonType.equals("next"))) {
			wasANextRequest = true;
		} else if ((!(buttonType == null)) && (buttonType.equals("prev"))) {
			wasAPrevRequest = true;
		} else {
			wasFirstSession = true;// 是否是第一次播放本课程
		}
		// 是否本课程之前没有打开 是否是播放课程后的请求 刷新
		if (courseID != null) {
			//更新最近学习课程
			SqlDao db = new SqlDao();
			String sqlUpdateLast = "Update userinfo set LastCourse = '" + courseID + "' where username = '" + username + "'";
			db.Update(sqlUpdateLast);
			db.CloseDataBase();
			// 把courseID写入session
			session.setAttribute("COURSEID", courseID);
		} else // 不是首次请求 因此从session中读取courseID
		{
			courseID = (String) session.getAttribute("COURSEID");
		}

		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();
		ResultSet courseInfo = null;

		// 查询出该courseID的所有信息
		String sqlSelectCourse = "SELECT * FROM courseinfo WHERE CourseID = '"
				+ courseID + "'";
		courseInfo = db.executeQuery(sqlSelectCourse);


		ResultSet userSCORS = null;
		// 查询出用户的个人课程信息 包括 进度 状态 等 交互信息
		String sqlSelectUserSCO = "SELECT * FROM userscoinfo WHERE UserName = '"
				+ username
				+ "' AND CourseID = '"
				+ courseID
				+ "' ORDER BY Sequence";
		userSCORS = db1.executeQuery(sqlSelectUserSCO);

		// 初始化变量来定位顺序
		String scoID = new String();
		String lessonStatus = new String();
		String launch = new String();

		// 如果选择的是菜单请求，立即处理
		if (wasAMenuRequest) {
			ResultSet MenuInfo = null;
			// 取出课程相关信息
			String sqlSelectItemInfo = "SELECT * FROM iteminfo WHERE CourseID = '"
					+ courseID + "'";
			MenuInfo = db.executeQuery(sqlSelectItemInfo);
			// 在记录表里存入第一纪录
			try {
				while (MenuInfo.next()) {
					item_type = MenuInfo.getString("Type");// 课件类型 sco asset
					identifier = MenuInfo.getString("Identifier");// 不同章节的标识符

					// 这个项目不是asset类型的话 就执行该语句块
					if ((item_type.equals(""))
							&& (identifier.equals(requestedSCO))) {
						MenuInfo.next();
						requestedSCO = MenuInfo.getString("Identifier");// 拿到下一个sco的章节号
						empty_block = true;
					}
					if (empty_block)// 判断是否拿到下一个sco的章节号
						break;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

			String lastScoID = (String) session.getAttribute("SCOID");// 拿到前一个scoID
			// 循环打开下一个
			try {
				while (userSCORS.next()) {
					scoID = userSCORS.getString("SCOID");// 拿到用户课程的scoID
					lessonStatus = userSCORS.getString("LessonStatus");// 拿到用户课程的课程状态
					launch = userSCORS.getString("Launch");// 拿到课程的地址
					type = userSCORS.getString("Type");// 拿到课程的类型

					if (requestedSCO.equals(scoID)) {
						nextItemToLaunch = launch;
						courseComplete = false;// 读取到课程路径 就把 课程完成标识设为否
						session.setAttribute("SCOID", scoID); // scoID加入session中
						break;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			// 如果是asset类型的课件就标记为完成状态
			String sqlUpdateUserSCO = "UPDATE userscoinfo SET LessonStatus = 'completed' WHERE SCOID = '"
					+ scoID + "' AND CourseID = '" + courseID + "'";
			if ((!(type == null)) && type.equals("asset")) {
				db.Update(sqlUpdateUserSCO);
			}
		}

		else // 如果是翻页请求、首次请求、auto类型课件执行以下语句块
		{
			// 首次请求、auto控制类型课件 执行以下语句块
			if (wasFirstSession) {
				// 打开第一个不是完成状态的章节
				try {
					while (userSCORS.next()) {
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");

						if (!(lessonStatus.equalsIgnoreCase("completed"))
								&& !(lessonStatus.equalsIgnoreCase("passed"))
								&& !(lessonStatus.equalsIgnoreCase("failed"))) {
							nextItemToLaunch = launch;
							courseComplete = false;
							session.setAttribute("SCOID", scoID); // 将获取的是从ID送入session
							break;
						}
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			else if (wasANextRequest)// 翻页--下一页请求执行以下语句块
			{
				// 首先获得前一个正在播放的scoID
				String lastScoID = (String) session.getAttribute("SCOID");
				// 是否找到下一个sco 的标识符
				boolean timeToLaunch = false;
				// 循环 找到scoID的下一个sco
				try {
					while (userSCORS.next()) {
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");
						if (timeToLaunch) {
							nextItemToLaunch = launch;
							courseComplete = false;
							session.setAttribute("SCOID", scoID);
							break;
						}
						if (lastScoID.equals(scoID)) {
							timeToLaunch = true;
						}
					}

					// 如果课程类型是asset的话 直接更新状态为 完成状态
					String sqlUpdateUserSCO = "UPDATE userscoinfo SET LessonStatus = 'completed' WHERE SCOID = '"
							+ scoID + "' AND CourseID = '" + courseID + "'";

					if ((!(type == null)) && type.equals("asset")) {
						db.Update(sqlUpdateUserSCO);
					}

				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

			else if (wasAPrevRequest)// 翻页---前一页请求执行以下语句块
			{
				String lastScoID = (String) session.getAttribute("SCOID");
				String prevScoID = new String();
				String prevScoLaunch = new String();
				boolean timeToLaunch = false;
				int count = 0;

				try {
					while (userSCORS.next()) {
						if (timeToLaunch)// 循环到当前scoID时执行,可以获取到前一个sco
						{
							nextItemToLaunch = prevScoLaunch;
							courseComplete = false;
							session.setAttribute("SCOID", prevScoID);
							break;
						}
						// 赋值给前一个要获取的scoID与 课件路径
						prevScoID = scoID;
						prevScoLaunch = launch;

						// 从数据库中获取课程信息
						scoID = userSCORS.getString("SCOID");
						lessonStatus = userSCORS.getString("LessonStatus");
						launch = userSCORS.getString("Launch");
						type = userSCORS.getString("Type");

						count++;
						// 循环到的scoID与当前的scoID一样
						if (lastScoID.equals(scoID)) {
							if (count == 1)// 用来判断是否当前请求的sco就是第一个sco
											// 如果是第一个则前一页仍是第一个
							{
								prevScoID = scoID;
								prevScoLaunch = launch;
							}
							timeToLaunch = true;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				// 如果循环完毕，执行语句块
				try {
					if (!userSCORS.next()) {
						// 将找到的前一个sco路径传递给nextItemToLaunch
						nextItemToLaunch = prevScoLaunch;
						courseComplete = false;
						session.setAttribute("SCOID", prevScoID);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		request.setAttribute("nextItemToLaunch", nextItemToLaunch);// 将下一个课件的播放路径给request
		db.CloseDataBase();
		db1.CloseDataBase();
		// 课程完成时执行该语句块
		if (courseComplete) {
			session.removeAttribute("COURSEID");
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					
					"/play/filterpage.jsp?courseID="+courseID);
			rd.forward(request, response);
		} else// 课程没有完成时执行
		{
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/play/playscorm.jsp");
			rd.forward(request, response);
		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
