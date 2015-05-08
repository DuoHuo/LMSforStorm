package com.tank.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspFactory;
import javax.servlet.jsp.PageContext;

import org.xml.sax.InputSource;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.tank.dao.SqlDao;
import com.tank.server.ManifestHandler;
import com.tank.server.PackageHandler;
import com.tank.util.ImportUtil;

/**
 * @Method UploadServlet()
 * @Description 上传课程模块--处理上传的课程(包括xml解析与解压缩zip文件)
 * @author liubin 2014.04.08
 * @return
 */
public class UploadServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	String sessionID = new String();
	String uploadDir = new String();
	String userDir = new String();
	String libPath = new String();
	String error = new String();
	ManifestHandler myManifestHandler;
	PackageHandler myPackageHandler;

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8"); // 设置编码
		HttpSession session = request.getSession(false);

		String username = (String) session.getAttribute("username");
		sessionID = session.getId();// 获取用户session
		String theWebPath = getServletConfig().getServletContext().getRealPath("/");

		// response.setContentType("text/html;charset=UTF-8");
		// PageContext是jsp的内置对象，在servlet不能直接使用，需要做一些处理
		JspFactory _jspxFactory = null;
		PageContext pageContext = null;
		_jspxFactory = JspFactory.getDefaultFactory();
		pageContext = _jspxFactory.getPageContext(this, request, response, "",true, 8192, true);

		SmartUpload myUpload = new SmartUpload();
		myUpload.initialize(pageContext);
		try {
			myUpload.upload();
		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}

		uploadDir = request.getSession().getServletContext().getRealPath("/uploadData/"+sessionID);//上传路径

		java.io.File theRTEUploadDir = new java.io.File(uploadDir);
		
		if (!theRTEUploadDir.isDirectory()) {
			theRTEUploadDir.mkdirs();
		}
		try {

			myUpload.save(uploadDir, SmartUpload.SAVE_PHYSICAL);

		} catch (SmartUploadException e1) {
			e1.printStackTrace();
		}
		
		
		String zipFile = new String(myUpload.getRequest().getParameter("theZipFile").getBytes("UTF-8"),"UTF-8");
		String newZip = zipFile.substring(zipFile.lastIndexOf("\\") + 1);// 上传文件名
		String uploadPath = uploadDir + "\\" + newZip;

		// 从压缩包中解压出xml文件
		myPackageHandler = new PackageHandler();
		
		String packagename = myPackageHandler.extract(uploadPath,"imsmanifest.xml", uploadDir); // 从zip文件中解压出xml文件到当前目录

		String manifestFile = uploadDir + "\\" + "imsmanifest.xml";

		zipFile = uploadDir + "\\" + newZip;

		// 创建一个manifest控制器，解析XML 
		myManifestHandler = new ManifestHandler();
	
		InputSource fileToParse = new ImportUtil().setUpInputSource(manifestFile);
		myManifestHandler.setFileToParse(fileToParse);
		myManifestHandler.setAuthor(username);
		myManifestHandler.processManifest();

		// 获得courseID，将课程解压到指定目录
		String courseID = myManifestHandler.getCourseID();
		
		ZipFile archive = new ZipFile(zipFile);

		byte[] buffer = new byte[16384];

		for (Enumeration e = archive.entries(); e.hasMoreElements();) {
			ZipEntry entry = (ZipEntry) e.nextElement();

			if (!entry.isDirectory()) {
				String filename = entry.getName();
				filename = filename.replace('/', java.io.File.separatorChar);
				filename = theWebPath + "CourseImports\\" + courseID + "\\"
						+ filename;
				java.io.File destFile = new java.io.File(filename);

				String parent = destFile.getParent();
				if (parent != null) {
					java.io.File parentFile = new java.io.File(parent);
					if (!parentFile.exists()) {
						parentFile.mkdirs();
					}
				}

				InputStream in = archive.getInputStream(entry);

				OutputStream outStream = new FileOutputStream(filename);

				int count;
				while ((count = in.read(buffer)) != -1)
					outStream.write(buffer, 0, count);

				in.close();
				outStream.close();
			}
		}
		
		// 在对应文件夹中写入 Sequencing对象
		String sequencingFileName = theWebPath + "CourseImports\\" + courseID + "\\sequence.obj";
		java.io.File sequencingFile = new java.io.File(sequencingFileName);
		FileOutputStream ostream = new FileOutputStream(sequencingFile);
		ObjectOutputStream oos = new ObjectOutputStream(ostream);
		oos.writeObject(myManifestHandler.getOrgsCopy());
		oos.flush();
		oos.close();
		ostream.close();
		
		// 将分享数目加1存入userinfo表中
		SqlDao db = new SqlDao();
		String sqlUpdateShareNum = "UPDATE userinfo SET ShareNum = ShareNum + 1 WHERE UserName = '" + username + "'";
		db.Update(sqlUpdateShareNum);
		
		//复制文件
		libPath = theWebPath+"\\SCORM_lib\\";
		copyFile(zipFile,libPath+newZip);
		//给课件压缩包重命名方便下载
		File f1 = new File(libPath+newZip);
	    File f2 = new File(libPath+"\\"+courseID+".zip");
	    if(f2.exists()){
	        f2.delete();
	    }
	    boolean b = f1.renameTo(f2);
		        
		// 删除已上传的文件
//		java.io.File uploadFiles[] = theRTEUploadDir.listFiles();
//		for (int i = 0; i < uploadFiles.length; i++) {
//			uploadFiles[i].deleteOnExit();
//		}
//		theRTEUploadDir.deleteOnExit();
		request.getRequestDispatcher("/servlet/RegCourse").forward(
				request, response);
	}
	/** 
     * 复制文件 
     * @param oldPath String 原文件路径 
     * @param newPath String 复制后路径 
     * @return boolean 
     */ 
	public void copyFile(String oldPath, String newPath) { 
	       try { 
	           int bytesum = 0; 
	           int byteread = 0; 
	           File oldfile = new File(oldPath); 
	           if (oldfile.exists()) { //文件存在时 
	               InputStream inStream = new FileInputStream(oldPath); //读入原文件 
	               FileOutputStream fs = new FileOutputStream(newPath); 
	               byte[] buffer = new byte[1444]; 
	               while ( (byteread = inStream.read(buffer)) != -1) { 
	                   bytesum += byteread; //字节数 文件大小 
	                   fs.write(buffer, 0, byteread); 
	               } 
	               inStream.close(); 
	               fs.close();
	           } 
	       } 
	       catch (Exception e) { 
	           System.out.println("复制文件操作出错"); 
	           e.printStackTrace(); 

	       } 

	   } 
}