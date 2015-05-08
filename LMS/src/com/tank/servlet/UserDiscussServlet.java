package com.tank.servlet;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;
import com.tank.model.Discuss;

/*
 * @auther:lwq 2014.8.8
 * @Description:问答模块
 * 
 */
public class UserDiscussServlet extends HttpServlet {
	
	public List<Discuss> QuestionList;
	public List<Discuss> UserQuestionList;
	public List<Discuss> QuestionDetailList;
	public List<String> UserCourseList;
	public List<Discuss> SpeAnDet;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		HttpSession session = request.getSession();
		String userName =(String) session.getAttribute("username");
		String control = request.getParameter("control");
		
		//得到用户提问数、所有课程提问数
		if(control.equals("base")){
			QuestionList = new ArrayList<Discuss>();
			UserQuestionList = new ArrayList<Discuss>();
			//得到用户提出的问题对应的课程标题、提问数、回答数
			UserQuestionList = getUserQuestionList(userName);
			//得到先查询所有的课程以及相关的问题数量
			QuestionList = getQuestionList();
			request.setAttribute("UserQuestionList", UserQuestionList);
			request.setAttribute("QuestionList", QuestionList);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/discuss/discuss.jsp");
			rd.forward(request, response);
		}
		//进入提问模块
		if(control.equals("proAskQue")){
			UserCourseList = new ArrayList<String>();
			UserCourseList = getUserCourse(userName);
			session.setAttribute("UserCourseList", UserCourseList);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/discuss/askquestion.jsp");
			rd.forward(request, response);
		}
		//进入保存问题模块
		if(control.equals("saveQue")){
			String AskResult = saveUserQue(request, response ,userName);
			request.setAttribute("result", AskResult);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/discuss/askquestion.jsp");
			rd.forward(request, response);
		}
		//显示课程信息
		if(control.equals("showcoursedis")){
			QuestionDetailList = new ArrayList<Discuss>();
			String courseTitle = request.getParameter("coursetitle");
			session.setAttribute("precourtitle", courseTitle);
			QuestionDetailList = getQuestionDetailList(courseTitle);
			request.setAttribute("QuestionDetailList", QuestionDetailList);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/discuss/precoursedis.jsp");
			rd.forward(request, response);
		}
		if(control.equals("specialcour")){
			Discuss SpeQueDet = new Discuss();
			SpeAnDet = new ArrayList<Discuss>();
			int spequeid = Integer.parseInt(request.getParameter("spequeid"));
			SpeQueDet = getSpeQueDet(spequeid);
			SpeAnDet = getSpeAnDet(spequeid);
			request.setAttribute("SpeQueDet", SpeQueDet);
			request.setAttribute("SpeAnDet", SpeAnDet);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/discuss/specoudet.jsp");
			rd.forward(request, response);
		}
		//保存回答内容
		if(control.equals("addAn")){
			int questionId = Integer.parseInt(request.getParameter("questionid"));
			String result = saveAnser(request, response, userName);
			Discuss SpeQueDet = new Discuss();
			SpeAnDet = new ArrayList<Discuss>();
			SpeQueDet = getSpeQueDet(questionId);
			SpeAnDet = getSpeAnDet(questionId);
			request.setAttribute("result", result);
			request.setAttribute("SpeQueDet", SpeQueDet);
			request.setAttribute("SpeAnDet", SpeAnDet);
			RequestDispatcher rd = getServletContext().getRequestDispatcher("/discuss/specoudet.jsp");
			rd.forward(request, response);
		}
	}
	
	/**
	 * @Method getQuestionList()
	 * @Description 得到所有的课程以及相关的问题数量
	 * @author lwq 2014.08.09
	 * @return ArrayList<Discuss>
	 */
	public ArrayList<Discuss> getQuestionList(){
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		ResultSet rs1 = null;
		int recordNum = 0;
		Discuss question;
		String sql1 = "SELECT DISTINCT QCourseTitle FROM questioninfo";
		rs1 = db1.executeQuery(sql1);
		ArrayList<Discuss> List = new ArrayList<Discuss>();
		try {
			while(rs1.next()){
				question = new Discuss();
				String courseTitle = rs1.getString("QCourseTitle");
				//对应课题的提问数
				String sql2 = "SELECT COUNT(QCourseTitle) AS num FROM questioninfo WHERE QCourseTitle='"+ courseTitle +"'";
				ResultSet rs2 = db2.executeQuery(sql2);
				//查询每个课程有多少条提问
				while(rs2.next()){
					recordNum = rs2.getInt("num");
				}
				question.setCourseTitle(courseTitle);
				question.setRecordNum(recordNum);
				List.add(question);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.CloseDataBase();
		db2.CloseDataBase();
		return List;
	}
	
	/**
	 * @Method getUserQuestionList(String userName)
	 * @Description 得到得到用户提出的问题:课程标题、提问数、回答数
	 * @author lwq 2014.08.09
	 * @return ArrayList<Discuss>
	 */
	public ArrayList<Discuss> getUserQuestionList(String userName){
		ArrayList<Discuss> List = new ArrayList<Discuss>();
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		SqlDao db3 = new SqlDao();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		Discuss UserQuestionList;
		//得到用户注册学习的课程
		String sql1 = "SELECT courseinfo.CourseTitle FROM usercourseinfo,courseinfo WHERE usercourseinfo.UserName = '"
				+ userName +"' AND courseinfo.CourseID = usercourseinfo.CourseID";
//		String sql1 = "SELECT DISTINCT QCourseTitle FROM questioninfo WHERE QSendName = '"+ userName +"'";
		rs1 = db1.executeQuery(sql1);
		try {
			while(rs1.next()){
				String courseTitle = rs1.getString("CourseTitle");
				//对应问题用户的提问数
				String sql2 = "SELECT COUNT(QCourseTitle) AS num FROM questioninfo WHERE QSendName='"+ userName +
								"' AND QCourseTitle='"+ courseTitle +"'";
				//对应问题的回答数
				String sql3 = "SELECT COUNT(ACourseTitle) AS num FROM answerinfo WHERE ASendName!='"+ userName +
								"' AND ACourseTitle='"+ courseTitle +"'";
				rs2 = db2.executeQuery(sql2);
				rs3 = db3.executeQuery(sql3);
				//提问数
				if(rs2.next()){
					if(rs3.next()){
						if(rs2.getInt("num") != 0){
							UserQuestionList = new Discuss();
							UserQuestionList.setQueNum(rs2.getInt("num"));
							UserQuestionList.setAnNum(rs3.getInt("num"));
							UserQuestionList.setCourseTitle(courseTitle);
							List.add(UserQuestionList);
						}
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.CloseDataBase();
		db2.CloseDataBase();
		db3.CloseDataBase();
		return List;
	}
	
	/**
	 * @Method getUserCourse(String userName)
	 * @Description 得到用户的课程名称，进入提问模块
	 * @author lwq 2014.08.10
	 * @return ArrayList<String>
	 */
	public ArrayList<String> getUserCourse(String userName){
		SqlDao db = new SqlDao();
		String courseTitle;
		ArrayList<String> List = new ArrayList<String>();
		String sql = "SELECT courseinfo.CourseTitle FROM courseinfo,usercourseinfo WHERE usercourseinfo.UserName='"+
				userName+"' AND usercourseinfo.CourseID = courseinfo.CourseID";
		ResultSet rs = db.executeQuery(sql);
		try {
			while(rs.next()){
				courseTitle = new String(rs.getString("CourseTitle"));
				List.add(courseTitle);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db.CloseDataBase();
		return List;
	}
	
	/**
	 * @Method saveUserQue(HttpServletRequest request, HttpServletResponse response, String userName)
	 * @Description 保存用户提出的问题
	 * @author lwq 2014.08.11
	 * @return String
	 */
	public String saveUserQue(HttpServletRequest request, HttpServletResponse response, String userName){
		SqlDao db = new SqlDao(); 
		String result = null;
		String QCourseTitle = request.getParameter("selecttitle");
		String QueContent = request.getParameter("askcontent");
		
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String currenttime = sdf.format( now );//得到当前时间
		String sql = "INSERT INTO questioninfo(QCourseTitle,QSendName,QuestionContent,QSendTime) VALUES ('"+
				QCourseTitle+"','"+ userName +"','"+ QueContent +"','"+ currenttime +"')";
		int rs = db.executeInsert(sql);
		if(rs == 1){
			result = "提问成功！";
		}else{
			result = "提问失败";
		}
		db.CloseDataBase();
		return result;
	}
	
	/**
	 * @Method getQuestionDetailList(String coursetitle)
	 * @Description 查询指定课程标题对应的提问和回答
	 * @author lwq 2014.08.12
	 * @return ArrayList<Discuss>
	 */
	public ArrayList<Discuss> getQuestionDetailList(String coursetitle){
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		Discuss QuestionDetail = new Discuss();
		ArrayList<Discuss> List = new ArrayList<Discuss>();
		//查询给定课程标题的提问
		String sql1 = "SELECT questioninfo.*, userinfo.UserFace FROM questioninfo, userinfo WHERE QCourseTitle ='"+ 
					coursetitle +"' AND userinfo.UserName=questioninfo.QSendName";
		rs1 = db1.executeQuery(sql1);
		try {
			while(rs1.next()){
				int id = rs1.getInt("QuestionId");
				QuestionDetail = new Discuss();
				QuestionDetail.setQuesionId(rs1.getInt("QuestionId"));
				//问题提问者
				QuestionDetail.setSendName(rs1.getString("QSendName"));
				QuestionDetail.setContent(rs1.getString("QuestionContent"));
				//提问时间
				QuestionDetail.setTime(rs1.getString("QSendTime"));
				QuestionDetail.setImgPath(rs1.getString("UserFace"));
				
				//查询answerinfo表里的最新回复
				String sql2 = "SELECT * FROM answerinfo WHERE ACourseTitle='"+ coursetitle +"' AND QuestionId='"+ id
				+"' ORDER BY ASendTime DESC LIMIT 1";
				rs2 = db2.executeQuery(sql2);
				if(rs2.next()){
					QuestionDetail.setLatestAContent(rs2.getString("AnswerContent"));
					QuestionDetail.setLatestASendName(rs2.getString("ASendName"));
					QuestionDetail.setLatestASendTime(rs2.getString("ASendTime"));
				}else{
					QuestionDetail.setFlag(1);
				}
				List.add(QuestionDetail);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.CloseDataBase();
		db2.CloseDataBase();
		return List;
	}
	
	/**
	 * @Method getSpeQueDet(String specour)
	 * @Description 查询指定课程标题对应的提问和回答
	 * @author lwq 2014.08.12
	 * @return ArrayList<Discuss>
	 */
	public Discuss getSpeQueDet(int spequeid){
		SqlDao db1 = new SqlDao();
		Discuss speCour = null;
		ResultSet rs1 = null;
		String userindex = null;
//		ArrayList<Discuss> List = new ArrayList<Discuss>();
		String sql1 = "SELECT questioninfo.*, userinfo.UserFace FROM questioninfo, userinfo WHERE QuestionId='"+
				spequeid +"'  AND userinfo.UserName=questioninfo.QSendName";
		rs1 = db1.executeQuery(sql1);
		try {
			if(rs1.next()){
				speCour = new Discuss();
				speCour.setQuesionId(rs1.getInt("QuestionId"));
				speCour.setCourseTitle(rs1.getString("QCourseTitle"));
				speCour.setSendName(rs1.getString("QSendName"));
				speCour.setContent(rs1.getString("QuestionContent"));
				speCour.setTime(rs1.getString("QSendTime"));
				speCour.setImgPath(rs1.getString("UserFace"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.CloseDataBase();
		return speCour;
	}
	
	/**
	 * @Method getSpeAnDet(int spequeid)
	 * @Description 查询指定课程标题对应回答
	 * @author lwq 2014.08.12
	 * @return ArrayList<Discuss>
	 */
	public ArrayList<Discuss> getSpeAnDet(int spequeid){
		SqlDao db1 = new SqlDao();
		SqlDao db2 = new SqlDao();
		String uesrindex = null;
		Discuss speAnDel;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ArrayList<Discuss> List = new ArrayList<Discuss>();
		String sql1 = "SELECT ASendName,AnswerContent,ASendTime FROM answerinfo WHERE QuestionId ='"+ spequeid +
				"' ORDER BY ASendTime ASC";

		rs1 = db1.executeQuery(sql1);
		try {
			while(rs1.next()){
				speAnDel = new Discuss();
				uesrindex = rs1.getString("ASendName");
				String sql2 = "select UserFace from userinfo where Username='"+uesrindex+"'";
				rs2 = db2.executeQuery(sql2);
				if(rs2.next()){
					speAnDel.setImgPath(rs2.getString("UserFace"));
				}
				speAnDel.setSendName(rs1.getString("ASendName"));
				speAnDel.setContent(rs1.getString("AnswerContent"));
				speAnDel.setTime(rs1.getString("ASendTime"));
				List.add(speAnDel);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		db1.CloseDataBase();
		db2.CloseDataBase();
		return List;
	}
	
	/**
	 * @Method saveAnser
	 * @Description 保存回答信息到answerinfo
	 * @author lwq 2014.08.13
	 * @return String
	 */
	public String saveAnser(HttpServletRequest request, HttpServletResponse response, String username){
		String courseTitle = request.getParameter("coutitle");
		int questionId = Integer.parseInt(request.getParameter("questionid"));
		String content = request.getParameter("addancont");
		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
		String currenttime = sdf.format( now );//得到当前时间
		SqlDao db = new SqlDao();
		String sql = "INSERT INTO answerinfo(ACourseTitle,ASendName,AnswerContent,QuestionId,ASendTime) VALUES ('"+
				courseTitle+"','"+ username +"','"+ content +"','"+ questionId +"','"+ currenttime +"')";
		int rs = db.executeInsert(sql);
		String result = null;
		if(rs == 1){
			result = "提问成功！";
		}else{
			result = "提问失败";
		}
		db.CloseDataBase();
		return result;
	}
}