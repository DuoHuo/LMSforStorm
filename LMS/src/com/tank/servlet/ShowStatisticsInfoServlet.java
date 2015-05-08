package com.tank.servlet;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.tank.dao.SqlDao;

public class ShowStatisticsInfoServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	    this.doPost(request, response);
	 }
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {

		SqlDao db = new SqlDao();
		/*chart1 注册人数统计*/
			String sqlSelectRegNum = "Select * from courseinfo where CourseInfo.Active = 1 ORDER BY CourseInfo.RegNum desc limit 0,10";
			ResultSet rs = db.executeQuery(sqlSelectRegNum);
			try {
				JSONObject jsonObj;//创建json格式的数据
				
				JSONArray jsonArr1 = new JSONArray();//创建json格式的数组  
				
				while(rs.next()){
					jsonObj = new JSONObject();
					String courseTitle = rs.getString("CourseTitle");
					String courseID = rs.getString("CourseID").substring(7);
					String regNum = rs.getString("RegNum");
					String color = getRandColor();
					jsonObj.put("name", courseID);
					jsonObj.put("title", courseTitle);
					jsonObj.put("value", regNum);
					jsonObj.put("color", color);
					jsonArr1.add(jsonObj);//将对象放入数组
				}
				request.setAttribute("jsonArr1",jsonArr1);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			/*chart2  评课平均得分统计*/
			SqlDao db1 = new SqlDao();
			
			String avgGrade = null;

			String sqlSelectCourseinfo = "SELECT CourseID , CourseTitle , RegNum FROM courseinfo";
			ResultSet courseInfoRS = db.executeQuery(sqlSelectCourseinfo);
			try {
				JSONObject jsonObj;//创建json格式的数据
				
				JSONArray jsonArr2 = new JSONArray();//创建json格式的数组  
			
				while (courseInfoRS.next()) {
					jsonObj = new JSONObject();
					
					String courseID = courseInfoRS.getString("CourseID");
					String courseTitle = courseInfoRS.getString("CourseTitle");
					// 根据courseID查询usercourseinfo获得平均成绩
					// 注：mysql的AVG函数会自动去掉空值，不用担心未评分的课件会被计算进去
					String sqlSelectAvgEva = "SELECT AVG(Evaluate) AS avgPrice FROM usercourseinfo WHERE CourseID = '"
							+ courseID + "'";
					ResultSet AvgEvaRS = db1.executeQuery(sqlSelectAvgEva);
					if (AvgEvaRS.next()) {
						avgGrade = AvgEvaRS.getString("avgPrice");

						if (null == avgGrade || avgGrade.equals("")) {
							continue;
						}
						String color = getRandColor();
						courseID = courseInfoRS.getString("CourseID").substring(7);
						jsonObj.put("name", courseID);
						jsonObj.put("title", courseTitle);
						jsonObj.put("value", avgGrade);
						jsonObj.put("color", color);
						jsonArr2.add(jsonObj);//将对象放入数组
					}
				}
				request.setAttribute("jsonArr2",jsonArr2);
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
			
			/*chart3 分享达人*/
			
			String sqlSelectAuthor = "Select * from userinfo ORDER BY UserInfo.ShareNum desc limit 0,10";
			ResultSet userInfoRS = db.executeQuery(sqlSelectAuthor);
			
			try {
				JSONObject jsonObj;//创建json格式的数据
				
				JSONArray jsonArr3 = new JSONArray();//创建json格式的数组  
				
				while(userInfoRS.next()){
					jsonObj = new JSONObject();
					String username = userInfoRS.getString("username");
					String shareNum = userInfoRS.getString("ShareNum");
					String color = getRandColor();
					jsonObj.put("name", username);
					jsonObj.put("value", shareNum);
					jsonObj.put("color", color);
					jsonArr3.add(jsonObj);//将对象放入数组
				}
				request.setAttribute("jsonArr3",jsonArr3);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			/*chart4 学习标兵*/
			String sqlSelectUserRegNum = "Select * from userinfo ORDER BY UserInfo.RegNum desc limit 0,10";
			ResultSet userInfoRS1 = db.executeQuery(sqlSelectUserRegNum);
			
			try {
				JSONObject jsonObj;//创建json格式的数据
				
				JSONArray jsonArr4 = new JSONArray();//创建json格式的数组  
				
				while(userInfoRS1.next()){
					jsonObj = new JSONObject();
					String username = userInfoRS1.getString("username");
					String RegNum = userInfoRS1.getString("RegNum");
					String color = getRandColor();
					jsonObj.put("name", username);
					jsonObj.put("value", RegNum);
					jsonObj.put("color", color);
					jsonArr4.add(jsonObj);//将对象放入数组
				}
				request.setAttribute("jsonArr4",jsonArr4);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			
			
			/*chart5 */
			db.CloseDataBase();
			db1.CloseDataBase();
			
			
			RequestDispatcher rd = getServletContext().getRequestDispatcher(
					"/centerstatistic.jsp");
			rd.forward(request, response);
		}
	
	//获取随机颜色
	public String getRandColor() {  
        
        String r,g,b;    
        Random random = new Random();    
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();    
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();    
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();    
            
        r = r.length()==1 ? "0" + r : r ;    
        g = g.length()==1 ? "0" + g : g ;    
        b = b.length()==1 ? "0" + b : b ;    
            
        return r+g+b;
	}  
}
