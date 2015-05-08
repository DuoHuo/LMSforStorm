package com.tank.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class SqlDao {
	public Connection conn = null;
	public ResultSet rs = null;
	Statement st = null;
	private String driver = null;
	private String url = null;
	private String username = null;
	private String password = null;

	/**
	 * @Method SqlDao()
	 * @Description 构造方法，获取数据库连接
	 * @author lwq 2014.03.27
	 * @return
	 */
	public SqlDao() {
		try {
			//读取配置文件中的信息
			Properties prop = new Properties();
			InputStream in = this.getClass().getResourceAsStream("database.properties");
			prop.load(in);
			
			driver = prop.getProperty("driver");
			url = prop.getProperty("url");
			username = prop.getProperty("username");
			password = prop.getProperty("password");
			
			Class.forName(driver);// 加载驱动
			conn = DriverManager.getConnection(url, username, password);
			st = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		} catch (java.lang.ClassNotFoundException e) {
			System.err.println("加载驱动器有错误:" + e.getMessage());
			System.out.print("执行插入有错误:" + e.getMessage());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @Method executeInsert()
	 * @Description 插入方法
	 * @author lwq 2014.03.27
	 * @return
	 */
	public int executeInsert(String sql) {
		int num = 0;
		try {
			num = st.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("执行插入有错误:" + e.getMessage());
			System.out.println("执行插入有错误:" + e.getMessage());
		}
		return num;
	}

	/**
	 * @Method executeQuery()
	 * @Description 查询方法
	 * @author lwq 2014.03.27
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		rs = null;
		try {
			rs = st.executeQuery(sql);
		} catch (SQLException e) {
			System.err.println("执行查询有错误:" + e.getMessage());
			System.out.print("执行查询有错误:" + e.getMessage());
		}
		return rs;
	}

	/**
	 * @Method Update()
	 * @Description 更新方法
	 * @author lwq 2014.03.27
	 * @return
	 */
	public int Update(String sql) {
		int num = 0;
		try {
			num = st.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println("执行修改有错误：" + ex.getMessage());
			System.out.print("执行修改有错误：" + ex.getMessage());
		}
		return num;
	}

	/**
	 * @Method executeDelete()
	 * @Description 删除方法
	 * @author lwq 2014.03.27
	 * @return
	 */
	public int executeDelete(String sql) {
		int num = 0;
		try {
			num = st.executeUpdate(sql);
		} catch (SQLException e) {
			System.err.println("执行删除有错误:" + e.getMessage());
			System.out.print("执行删除有错误:" + e.getMessage());
		}
		return num;
	}

	/**
	 * @Method CloseDataBase()
	 * @Description 关闭数据库
	 * @author lwq 2014.03.27
	 * @return
	 */
	public void CloseDataBase() {
		try {
			conn.close();
			st.close();
		} catch (Exception ex) {
			System.err.println("执行关闭Connection对象有错误:" + ex.getMessage());
			System.out.print("执行关闭Connection对象有错误:" + ex.getMessage());
		}
	}
}