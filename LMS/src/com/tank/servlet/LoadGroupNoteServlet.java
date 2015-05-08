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
import com.tank.model.GroupNoteBook;

public class LoadGroupNoteServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
	public List<GroupNoteBook> groupNoteList = null;
	GroupNoteBook groupNote = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 *@function 按课程标题将笔记分组
	 *@description 查询用户注册课程的个人笔记以及其他人共享的笔记
	 *@author  lwq 2014.8.5 changed by lwq 2014.8.13
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String username = (String) session.getAttribute("username");
		request.setCharacterEncoding("UTF-8"); // 设置编码
		
		groupNoteList = new ArrayList<GroupNoteBook>();
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		SqlDao db3 = new SqlDao();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		String tip = null;
		String sql1 = "SELECT courseinfo.CourseTitle FROM courseinfo,usercourseinfo WHERE usercourseinfo.UserName='"+username+
						"' AND courseinfo.CourseID=usercourseinfo.CourseID ";
		rs1 = db1.executeQuery(sql1);
		try {
			if(rs1.next()){
				rs1.beforeFirst();
				while(rs1.next()){
					groupNote = new GroupNoteBook();
					String courseTitle = rs1.getString("CourseTitle");
					//查询指定课程标题的我的笔记数量
					String sql2 = "SELECT COUNT(courseTitle) AS mynum FROM noteinfo WHERE UserName='"+username+"'  AND courseTitle='"+courseTitle+"'";
					rs2 = db2.executeQuery(sql2);
					if(rs2.next()){
						groupNote.setMyNoteNum(rs2.getInt("mynum"));
					}
					
					//查询他人的共享笔记数量
					String sql3 = "SELECT COUNT(courseTitle) AS num FROM noteinfo WHERE UserName!='"+username+
							"' AND NoteStatus='1' AND courseTitle='"+ courseTitle +"'";
					rs3 = db3.executeQuery(sql3);
					if(rs3.next()){
						groupNote.setOtherNoteNum(rs3.getInt("num"));
					}
					if(courseTitle.equals("null")){
						groupNote.setCourseTitle("无课程标题笔记");
					}else{
						groupNote.setCourseTitle(courseTitle);
					}
					groupNoteList.add(groupNote);
				}
				request.setAttribute("noteGroupList", groupNoteList);
			}else{
				tip = "您还没有注册课程，所以没法查看和记录自己的笔记，请您先注册课程~~~";
				request.setAttribute("tip", tip);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.CloseDataBase();
		db2.CloseDataBase();
		db3.CloseDataBase();
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/mygroupnote.jsp");
		rd.forward(request, response);
	}

}