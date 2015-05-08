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
import com.tank.model.NoteBook;

/**
 * @Method LoadNoteServlet()
 * @Description 笔记模块--从数据库读取我的笔记
 * @author liubin 2014.04.24
 * @return
 * 
 * @changed by lwq 2014.8.4
 * @Description 将笔记模块细化，根据传过来的课程名和用户名查找特定的笔记
 * @return
 */
public class LoadNoteServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	public List<NoteBook> noteDetailList = null;
	public List<NoteBook> noteOtherlList = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String subrecourseTitle = null;
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		request.setCharacterEncoding("utf-8"); // 设置编码
		String recourseTitle = request.getParameter("courseTitle");
		if(recourseTitle.equals("无课程标题笔记")){
			subrecourseTitle = "null";
		}else{
			subrecourseTitle = recourseTitle;
		}
			
		
		noteDetailList = new ArrayList<NoteBook>();
		SqlDao db1 = new SqlDao();
		String sql = "SELECT * FROM noteinfo WHERE UserName ='" + username +
				"' AND courseTitle='"+ subrecourseTitle +"'";
		ResultSet userNoteRs = null;
		userNoteRs = db1.executeQuery(sql);
		NoteBook notebook;
		String noteStatus;
		try {
			while (userNoteRs.next()) {
				notebook = new NoteBook();
				int noteID = Integer.parseInt(userNoteRs.getString("NoteID"));
				String courseTitle = userNoteRs.getString("courseTitle");
				int flag = userNoteRs.getInt("NoteStatus");
				String content = userNoteRs.getString("Content");
				String author = userNoteRs.getString("UserName");
				if (courseTitle.equals("null")) {
					courseTitle = "非课堂笔记";
				}
				if(flag == 0){
					noteStatus = "私有笔记";
				}else{
					noteStatus = "公开笔记";
				}
				notebook.setNoteID(noteID);
				notebook.setUsername(username);
				notebook.setCourseTitle(courseTitle);
				notebook.setNoteStatus(noteStatus);
				notebook.setContent(content);
				notebook.setAuthor(author);
				noteDetailList.add(notebook);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		db1.CloseDataBase();
		
		
		//查询其他用户的笔记
		noteOtherlList = new ArrayList<NoteBook>();
		ResultSet otherNoteRs = null;
		SqlDao db2 = new SqlDao();
		String sqlOtherNotes = "SELECT * FROM noteinfo WHERE NoteStatus='1' AND UserName!='"+username+
				"' AND courseTitle='"+subrecourseTitle+"'";
		otherNoteRs = db2.executeQuery(sqlOtherNotes);
		try {
			while(otherNoteRs.next()){
				notebook = new NoteBook();
				int noteID = Integer.parseInt(otherNoteRs.getString("NoteID"));
				String courseTitle = otherNoteRs.getString("courseTitle");
//				String noteStatus = otherNoteRs.getString("NoteStatus");
				String content = otherNoteRs.getString("Content");
				String author = otherNoteRs.getString("UserName");
				if (courseTitle.equals("null")) {
					courseTitle = "非课堂笔记";
				}
				notebook.setNoteID(noteID);
				notebook.setUsername(username);
				notebook.setCourseTitle(courseTitle);
//				notebook.setNoteStatus(noteStatus);
				notebook.setContent(content);
				notebook.setAuthor(author);
				noteOtherlList.add(notebook);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db2.CloseDataBase();
		
		session.setAttribute("recourseTitle", recourseTitle);
		request.setAttribute("noteDetailList", noteDetailList);
		request.setAttribute("noteOtherList", noteOtherlList);
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/mydetailnote.jsp");
		rd.forward(request, response);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}
}
