package com.tank.server;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import org.adl.parsers.dom.ADLItem;
import org.adl.parsers.dom.ADLOrganizations;
import org.adl.parsers.dom.CPDOMParser;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMException;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.tank.dao.SqlDao;

public class ManifestHandler extends CPDOMParser implements Serializable {
	private static final long serialVersionUID = 1L;

	protected InputSource sourceToParse;
	protected String courseTitle;
	protected String courseDir;
	protected String courseID;
	protected String control;
	protected String title;
	protected String author;
	
	protected Vector<ADLItem> items;

	/**
	 * @Method ManifestHandler()
	 * @Description 构造函数
	 * @author liubin 2014.04.02
	 * @return
	 */
	public ManifestHandler() {
		super();
		items = new Vector<ADLItem>();
		courseTitle = "";
		courseDir = "";
		courseID = "";
		control = "";
		title = "";
	}

	/**
	 * @Method processManifest()
	 * @Description 课件解析主方法
	 * @author liubin 2014.04.02
	 * @return sucess/fail
	 */
	public boolean processManifest() {
		boolean valid = false;

		try {
			parse(sourceToParse); // 解析该文件，调用parse()函数

			document = getDocument(); // 解析文件完成,调用getDocument()函数
			valid = true;

			processContent(); // 对解析内容进行处理,调用processContent()函数
		} catch (SAXException se) {
			String message = "Parser threw a SAXException.";
			System.out.println(message);
		} catch (IOException ioe) {
			String message = "Parser threw a IOException.";
			System.out.println(message);
		}

		updateDB(); // 更新数据库信息,调用updateDB()函数
		return valid;
	}

	/**
	 * @Method processContent()
	 * @Description 对解析的内容进行处理
	 * @author liubin 2014.04.02
	 * @return sucess/fail
	 */
	public boolean processContent() {
		boolean result = true;

		if (document == null) {
			System.out.println("the document is null");
		}

		Node contentNode = document.getDocumentElement();// 获得xml的根节点
		NodeList contentChildren = contentNode.getChildNodes();// 获得根节点的孩子节点

		this.manifest.fillManifest(contentNode);// 调用fillManifest()方法.填充解析器结构

		// this.setSequence();// 设置导航控制属性

		this.items = this.getItemList();// 将文件中的item内容移植到items向量中

		// 通过循环解析出xml中的items内容
		for (int i = 0; i < contentChildren.getLength(); i++) {
			Node currentNode = contentChildren.item(i);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE)// 确定该节点为元素ELEMENT_NODE
			{

				if (currentNode.getNodeName().equalsIgnoreCase("organizations")) {
					String value = getSubElement(currentNode, "organization");// 处理organization结点
					if (value == null) {
						break;
					}
					this.courseTitle = value;
				}
				if (currentNode.getNodeName().equalsIgnoreCase("resources"))// 找到resources关节点处
				{
					result = processResources(currentNode);// 处理resources结点
					if (result == false) {
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * @Method processResources()
	 * @Description 对resources节点进行处理
	 * @author liubin 2014.04.02
	 * @return sucess/fail
	 */
	public boolean processResources(Node resourcesNode) {
		boolean result = true;

		if (document == null) {
			System.out.println("the document is null");
		}

		NodeList resourcesChildren = resourcesNode.getChildNodes();// 获取该节点的孩子结点
		// 循环找到 resource 节点
		for (int i = 0; i < resourcesChildren.getLength(); i++) {
			Node currentNode = resourcesChildren.item(i);

			if (currentNode.getNodeType() == Node.ELEMENT_NODE)// 确定该元素为ELEMENT_NODE
			{
				if (currentNode.getNodeName().equalsIgnoreCase("resource")) {
					result = processResource(currentNode);// 对resource结点进行处理
					if (result == false) {
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * @Method processResource()
	 * @Description 对resource节点进行解析
	 * @author liubin 2014.04.02
	 * @return sucess/fail
	 */
	public boolean processResource(Node resourceNode) {
		boolean result = true;

		// 获取resource结点下的相关内容
		String id = getAttribute(resourceNode, "identifier");
		String scormType = getAttribute(resourceNode, "adlcp:scormtype");
		String type = getAttribute(resourceNode, "type");
		String href = getAttribute(resourceNode, "href");

		updateItemList(id, scormType, type, href); // 更新ItemList内容

		return result;
	}

	/**
	 * @Method processResource()
	 * @Description 对resource节点进行处理
	 * @author liubin 2014.04.02
	 * @return sucess/fail
	 */
	public void updateItemList(String id, String scormType, String type,
			String href) {
		ADLItem tempItem = new ADLItem();
	
		// 循环查询item相关的属性，若查询到，则加入该元素并整体替换原来的item
		for (int i = 0; i < items.size(); i++) {
			tempItem = (ADLItem) this.items.elementAt(i);
			String idref = tempItem.getIdentifierref();

			if (idref.equals(id)) {
				if (type != null && !type.equals("")) {
					tempItem.setType(type);
				}
				if (scormType != null && !type.equals("")) {
					tempItem.setScormType(scormType);
				}
				if (href != null && !href.equals("")) {
					tempItem.setLaunchLocation(href);
				}
				// 替换向量中的第i个元素
				items.removeElementAt(i);
				items.insertElementAt(tempItem, i);
			}
		}
	}

	/**
	 * @Method getCourseID()
	 * @Description 返回CourseID
	 * @author liubin 2014.04.03
	 * @return String
	 */
	public String getCourseID() {
		return this.courseID;
	}

	/**
	 * @Method getOrgsCopy()
	 * @Description 返回ADLOrganizations对象
	 * @author liubin 2014.03.28
	 * @return ADLOrganizations
	 */
	public ADLOrganizations getOrgsCopy() {
		return this.manifest.getOrganizations();
	}

	/**
	 * @Method setSequence()
	 * @Description 解析出导航类型
	 * @author liubin 2014.04.03
	 * @return
	 */
	public void setSequence() {
		this.control = this.manifest.getOrganizations().getFirstOrg()
				.getSequence().getControl();
	}

	// Class的set方法
	public void setCourseName(String courseTitle) {
		this.courseTitle = courseTitle;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
	
	public void setFileToParse(InputSource inputStream) {
		this.sourceToParse = inputStream;
	}

	/**
	 * @Method updateDB()
	 * @Description 更新解析出的课程信息到数据库的相关表中
	 * @author liubin 2014.04.03
	 * @return sucess/fail
	 */
	protected void updateDB() {
		try {
			SqlDao db = new SqlDao();

			// 从ApplicationData表中拿出下一个courseID
			String sqlSelectCourseID = "SELECT * FROM ApplicationData WHERE dataName = 'nextCourseID'";
			ResultSet selectCourseIDRS = db.executeQuery(sqlSelectCourseID);

			if (selectCourseIDRS.next()) {
				int idvalue = selectCourseIDRS.getInt("numberValue");
				courseID = "Course-" + idvalue;
				idvalue++;

				// 将courseID加1存入ApplicationData表中
				String sqlUpdateCourseID = "UPDATE ApplicationData SET numberValue = "
						+ idvalue + " WHERE dataName = 'nextCourseID'";
				db.Update(sqlUpdateCourseID);
				
				// 插入课程信息到CourseInfo表中
				String sqlInsertCourse = "INSERT INTO CourseInfo (CourseID, CourseTitle, Active, Author, EvaNum) "
						+ "VALUES('"
						+ courseID
						+ "', '"
						+ courseTitle
						+ "', "
						+ true 
						+ ", '"
						+ author 
						+ "', "
						+ 0 + ")";
				db.executeInsert(sqlInsertCourse);

				// 创建ADLItem对象
				ADLItem tempItem = new ADLItem();
				// 循环每一个item将信息存入数据库
				for (int i = 0; i < items.size(); i++) {
					tempItem = (ADLItem) items.elementAt(i);// 从items中拿出第i个元素

					// 插入数据库前先对URL进行解码
					URLDecoder urlDecoder = new URLDecoder();
					String alteredLocation = new String();

					if ((tempItem.getLaunchLocation().startsWith("http://"))
							|| (tempItem.getLaunchLocation()
									.startsWith("https://"))) {
						alteredLocation = urlDecoder.decode((String) tempItem
								.getLaunchLocation());
					} else {
						alteredLocation = "/LMS/CourseImports/"
								+ courseID
								+ "/"
								+ urlDecoder.decode((String) tempItem
										.getLaunchLocation());
					}

//					String title = new String(tempItem.getTitle().getBytes(),
//							"ISO-8859-1");// 拿出章节标题
//
//					String iso = new String(title.getBytes(),"ISO-8859-1");  
//					
//					for (byte b : title.getBytes("ISO-8859-1")) {
//				        System.out.print(b + " ");  
//				    }  
//					
//					System.out.println(title);//乱码
					String sqlInsertItem = "INSERT INTO ItemInfo (CourseID, Identifier, Type, Title, Launch, "
							+ "ParameterString, DataFromLMS, Prerequisites, MasteryScore, "
							+ "MaxTimeAllowed, TimeLimitAction, Sequence, TheLevel) "
							+ "VALUES('"
							+ courseID
							+ "','"
							+ tempItem.getIdentifier()
							+ "','"
							+ tempItem.getScormType()
							+ "',\""
							+ tempItem.getTitle()
							+ "\",'"
							+ alteredLocation
							+ "','"
							+ tempItem.getParameterString()
							+ "','"
							+ tempItem.getDataFromLMS()
							+ "','"
							+ tempItem.getPrerequisites()
							+ "','"
							+ tempItem.getMasteryScore()
							+ "','"
							+ tempItem.getMaxTimeAllowed()
							+ "','"
							+ tempItem.getTimeLimitAction()
							+ "',"
							+ i
							+ ","
							+ tempItem.getLevel() + ")";

					db.executeInsert(sqlInsertItem);
				}
			}

			db.CloseDataBase();
		} catch (SQLException se) {
			System.out.println(se.getSQLState());
			System.out.println("error code: " + se.getErrorCode());
			se.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method getAttribute()
	 * @Description 获取某结点下的相关属性方法
	 * @author liubin 2014.04.04
	 * @return sucess/fail
	 */
	protected String getAttribute(Node theNode, String theAttribute) {
		String returnValue = new String();// 初始化返回值

		Attr attrs[] = sortAttributes(theNode.getAttributes());// 对节点的属性进行排序

		// 检查所请求的属性是否存在，若存在则返回值
		Attr attribute;// 属性结点
		for (int i = 0; i < attrs.length; i++) {
			attribute = attrs[i];

			if (attribute.getName().equals(theAttribute)) {
				returnValue = attribute.getValue();
				break;
			}
		}
		return returnValue;
	}

	/**
	 * @Method sortAttributes()
	 * @Description 排序所有属性结点
	 * @author liubin 2014.04.04
	 * @return sucess/fail
	 */
	protected Attr[] sortAttributes(NamedNodeMap attrs) {
		int len = (attrs != null) ? attrs.getLength() : 0;
		Attr array[] = new Attr[len];
		for (int i = 0; i < len; i++) {
			array[i] = (Attr) attrs.item(i);
		}
		for (int i = 0; i < len - 1; i++) {
			String name = array[i].getNodeName();
			int index = i;
			for (int j = i + 1; j < len; j++) {
				String curName = array[j].getNodeName();
				if (curName.compareTo(name) < 0) {
					name = curName;
					index = j;
				}
			}
			if (index != i) {
				Attr temp = array[i];
				array[i] = array[index];
				array[index] = temp;
			}
		}
		return (array);
	}

	/**
	 * @Method getSubElement()
	 * @Description 获得结点的文本内容
	 * @author liubin 2014.04.04
	 * @return sucess/fail
	 */
	public String getSubElement(Node node, String element) {
		String value = new String();
		NodeList kids = node.getChildNodes();

		if (kids != null) {
			for (int i = 0; i < kids.getLength(); i++) {
				if (kids.item(i).getNodeType() == Node.ELEMENT_NODE) {
					// 查找请求的element
					if (kids.item(i).getNodeName().equalsIgnoreCase(element)) {
						value = getText(kids.item(i), "title");
						return value;
					}
				}
			}
		} else {
			System.out.println("node has no kids");
		}
		return value;
	}

	/**
	 * @Method getText()
	 * @Description 获得孩子结点的文本内容
	 * @author liubin 2014.04.04
	 * @return sucess/fail
	 * @throws UnsupportedEncodingException
	 */
	public String getText(Node node, String element) {
		String value = null;
		NodeList kids = node.getChildNodes();
		// 循环所有节点获取文本内容
		if (kids != null) {
			for (int i = 0; i < kids.getLength(); i++) {
				if ((kids.item(i).getNodeType() == Node.ELEMENT_NODE)) {
					if (kids.item(i).getNodeName().equalsIgnoreCase(element)) {
						try {
								try {
									value =  new String(kids.item(i).getFirstChild()
											.getNodeValue().trim().getBytes("UTF-8"),"ISO-8859-1");
									String str = new String("通通通通".getBytes("UTF-8"),"ISO-8859-1");
									for (byte a : str.getBytes("ISO-8859-1")) {  
								        System.out.print(a + " ");  
								    } 
									System.out.println();
									for (byte b : value.getBytes("ISO-8859-1")) {  
								        System.out.print(b + " ");  
								    }  
									value = new String(value.getBytes("ISO-8859-1"),"UTF-8");
									System.out.println(value);
								} catch (UnsupportedEncodingException e) {
									e.printStackTrace();
								}
						} catch (DOMException e) {
							e.printStackTrace();
						}
						break;
					}
				}
			}
		} else {
			System.out.println("no kids in node");
		}
		return value;
	}

}
