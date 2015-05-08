package com.tank.servlet;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.tank.dao.SqlDao;

public class ScreenShotServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		String courseID = (String) session.getAttribute("COURSEID");
		
		String realPath = request.getSession().getServletContext().getRealPath(""); // 获得项目下绝对路径
		String FilePath = realPath + "\\images\\course\\";
		String pngPath = FilePath + courseID + ".png";
		String jpgPath = FilePath + courseID + ".jpg";
		//截取屏幕
		try {  
            int width = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth();  //要截取的宽度
            int height = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight();  //要截取的高度
            Robot robot = new Robot();  
            BufferedImage image1 = robot.createScreenCapture(new Rectangle(width,height));  
            image1 = image1.getSubimage(470, 190, 700, 500);
            ImageIO.write (image1, "png" , new File(pngPath)); 
            
            BufferedImage image2 = robot.createScreenCapture(new Rectangle(width,height));  
            image2 = image2.getSubimage(550, 170, 550, 550);
            ImageIO.write (image2, "jpg" , new File(jpgPath)); 
               
        } catch (AWTException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
		//修改数据库图片信息
		SqlDao db = new SqlDao();
		String sqlUpdateCourse = "Update courseinfo set image = '1' where courseID = '" + courseID + "'";
		db.Update(sqlUpdateCourse);
		db.CloseDataBase();
		
		RequestDispatcher rd = getServletContext().getRequestDispatcher(
				"/servlet/LoadAllCourse?screenshot=b");
		rd.forward(request, response);
	}
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doPost(request, response);
	}
}
