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
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;
import com.tank.model.Scorm;

/**
 * @Method MenuServlet()
 * @Description 播放课程--课程导航模块--列出课程章节并实现交互
 * @author liubin 2014.05.08
 * @return
 */
public class MenuServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		SqlDao db = new SqlDao();
		SqlDao db1 = new SqlDao();

		ArrayList<Scorm> scoList = new ArrayList<Scorm>();

		String username = (String) session.getAttribute("username");
		String courseID = (String) request.getParameter("courseID");

		// 得到courseTitle
		// if (courseID != "") {
		// ResultSet courseInfo = null;
		// String sqlSelectCourse =
		// "SELECT CourseTitle FROM CourseInfo WHERE CourseID = '" + courseID +
		// "'";
		//
		// courseInfo = db.executeQuery(sqlSelectCourse);
		// try {
		// if ( courseInfo.next() ){
		// String courseTitle = courseInfo.getString("CourseTitle");
		// map.put("courseTitle", courseTitle);
		// }
		// } catch (SQLException e) {
		// e.printStackTrace();
		// }
		// }

		ResultSet itemInfo = null;

		if (courseID != "") {
			String sqlSelectUserSCO = "SELECT * FROM iteminfo WHERE CourseID = '"
					+ courseID + "' ORDER BY Sequence";

			itemInfo = db.executeQuery(sqlSelectUserSCO);

			Scorm sco;
			try {
				while (itemInfo.next()) {

					sco = new Scorm();
					String type = itemInfo.getString("Type");
					if (!type.equals("")) {
						String title = new String(itemInfo.getString("Title")
								.getBytes(), "UTF-8");
						String scoID = itemInfo.getString("Identifier");
						String level = itemInfo.getString("TheLevel");

						ResultSet userscoInfo = null;
						String sqlSelectUserSCOInfo = "SELECT * FROM userscoinfo WHERE CourseID = '"
								+ courseID
								+ "' and UserName ='"
								+ username
								+ "' and SCOID = '" + scoID + "'";
						userscoInfo = db1.executeQuery(sqlSelectUserSCOInfo);

						if (userscoInfo.next()) {
							String lessonStatus = userscoInfo
									.getString("LessonStatus");
							sco.setScoID(scoID);
							sco.setTitle(title);
							sco.setLevel(level);
							sco.setLessonStatus(lessonStatus);
						}
						scoList.add(sco);
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		db.CloseDataBase();
		db1.CloseDataBase();
		request.setAttribute("scoList", scoList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/play/playleft.jsp");
		rd.forward(request, response);
	}

}
