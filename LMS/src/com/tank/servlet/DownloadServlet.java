package com.tank.servlet;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
public class DownloadServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
    
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        //得到下载文件的名字
        String filename=request.getParameter("courseID");
        String courseTitle = request.getParameter("courseTitle");
        courseTitle = courseTitle + ".zip";
        filename = filename + ".zip";
       
        String realPath = request.getSession().getServletContext().getRealPath(""); // 获得项目下绝对路径
        
        String FilePath = realPath + "\\SCORM_lib\\"; // 获得服务器绝对路径
        
        //创建file对象
        File file=new File(FilePath + filename);
        
        //设置response的编码方式
        response.setContentType("application/zip");
        
        //写明要下载的文件的大小
        response.setContentLength((int)file.length());
        
        //设置附加文件名
        response.setHeader("Content-Disposition","attachment;filename=" + courseTitle);  
        
        //读出文件到i/o流
        FileInputStream fis = new FileInputStream(file);
        BufferedInputStream  bis = new BufferedInputStream(fis);  
        BufferedOutputStream  bos = new BufferedOutputStream(response.getOutputStream());   
        //开始循环下载
        byte[] buffer = new byte[1024];
        int i = -1;
        while ((i = bis.read(buffer)) != -1) {
        	bos.write(buffer, 0, i);
        }
        bos.flush();
        bos.close();
        bis.close();
        fis.close();
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        doGet(request, response);
    }
}