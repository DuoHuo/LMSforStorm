package com.tank.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import uk.ltd.getahead.dwr.WebContextFactory;

import com.tank.dao.SqlDao;
import com.tank.datamodels.DataModelInterface;
import com.tank.datamodels.SCODataManager;
import com.tank.datamodels.cmi.CMICore;
import com.tank.datamodels.cmi.CMIRequest;
import com.tank.datamodels.cmi.CMIScore;
import com.tank.datamodels.cmi.CMITime;
import com.tank.datamodels.cmi.DMErrorManager;
import com.tank.util.debug.DebugIndicator;

public class APIAdapter {

	private static String cmiFalse;
	private static String cmiTrue;

	private static LMSErrorManager lmsErrorManager = new LMSErrorManager();
	private static SCODataManager theSCOData;
	private static boolean isLMSInitialized = false;
	private DMErrorManager dmErrorManager = new DMErrorManager();
	private URL servletURL;

	/**
	 * @Method init()
	 * @Description 负责建立LMS与SCO联系并初始化APIAdapter相关参数。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public void init() {

		// 将boolean设置为String类型传递参数
		cmiFalse = new String("false");
		cmiTrue = new String("true");

		lmsErrorManager = new LMSErrorManager();
		dmErrorManager = new DMErrorManager();

		APIAdapter.isLMSInitialized = false;

		HttpServletRequest request = WebContextFactory.get()
				.getHttpServletRequest();
		HttpSession session = WebContextFactory.get().getSession();

		String path = request.getRequestURL().toString();
		String str = path.substring(0, path.indexOf("dwr"));
		try {
			String username = (String) session.getAttribute("username");

			this.servletURL = new URL(str + "servlet/LMScmi?username="
					+ username);

			session.setAttribute("servletURL", servletURL);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Method LMSInitialize()
	 * @Description 负责建立学习对象与平台之间的数据传输管道。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSInitialize(String param) {

		String result = cmiFalse;// 默认设置为false

		// 检查参数
		String tempParm = String.valueOf(param);

		if ((tempParm.equals("null") || tempParm.equals("")) != true) {

			APIAdapter.lmsErrorManager.SetCurrentErrorCode("201");
			return result;
		}

		// 检查是否初始化
		if (APIAdapter.isLMSInitialized == true) {
			APIAdapter.lmsErrorManager.SetCurrentErrorCode("101");
		} else {
			// 获取用户课程相关数据以完成初始化
			HttpServletRequest request = WebContextFactory.get()
					.getHttpServletRequest();
			// String sessionid = request.getRequestedSessionId();

			HttpSession session = WebContextFactory.get().getSession();
			servletURL = (URL) session.getAttribute("servletURL");
			String sessionid = session.getId();// 获取用户sessionid

			try {
				String courseID = (String) session.getAttribute("COURSEID");
				String scoID = (String) session.getAttribute("SCOID");

				this.servletURL = new URL(servletURL + "&courseID=" + courseID
						+ "&scoID=" + scoID);

			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			ServletAgent servletProxy = new ServletAgent(this.servletURL,
					sessionid);

			// //返回原始ServletURL路径
			// try {
			// String tservletURL = this.servletURL.toString();
			// this.servletURL = new URL(
			// tservletURL.substring(0,tservletURL.indexOf("&courseID")) );
			// } catch (MalformedURLException e) {
			// e.printStackTrace();
			// }

			// 获取用户数据
			APIAdapter.theSCOData = (SCODataManager) servletProxy.GetSCOData();

			// 初始化成功
			APIAdapter.isLMSInitialized = true;

			// 返回错误码
			APIAdapter.lmsErrorManager.ClearCurrentErrorCode();

			// 返回正确结果
			result = cmiTrue;
		}

		return result;
	}

	/**
	 * @Method LMSFinish()
	 * @Description 负责结束学习对象与平台之间的数据传输管道。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSFinish(String param) {
		String result = cmiFalse;

		// 检查参数
		String tempParm = String.valueOf(param);
		if ((tempParm.equals("null")) || (tempParm.equals(""))) {
			// 检查是否初始化
			if (CheckInitialization() == true) {
				CMICore lmsCore = APIAdapter.theSCOData.getCore();

				// 取出TotalTime参数
				CMITime totalTime = new CMITime(lmsCore.getTotalTime()
						.getValue());

//				System.out.println("totalTime="+totalTime);
				// 记录totalTime到RTE环境
				CMITime sessionTime = new CMITime(lmsCore.getSessionTime()
						.getValue());
//				System.out.println("sessionTime="+sessionTime);
				totalTime.add(sessionTime);

				// 取出Score参数
				CMIScore cmiScore = lmsCore.getScore();
				int score = Integer.parseInt(cmiScore.getRaw().getValue());

				// 将totalTime存入数据库
				HttpSession session = WebContextFactory.get().getSession();
				String username = (String) session.getAttribute("username");
				String courseID = (String) session.getAttribute("COURSEID");
				String scoID = (String) session.getAttribute("SCOID");

				SqlDao db = new SqlDao();
				SqlDao db1 = new SqlDao();

				String sqlUpdateTime = "update userscoinfo set TotalTime ='"
						+ totalTime + "' where UserName ='" + username
						+ "' and CourseID ='" + courseID + "' and SCOID = '"
						+ scoID + "'";

				db.Update(sqlUpdateTime);

				// 更新带有测试题的sco的测试成绩并判断是否通过
				if (score != 0) {
					String sqlUpdateScore = "update userscoinfo set Score ="
							+ score + " where UserName ='" + username
							+ "' and CourseID ='" + courseID
							+ "' and SCOID = '" + scoID + "'";

					db.Update(sqlUpdateScore);

					// 判断是否通过
					ResultSet rs = null;
					String sqlSelectPass = "select MasteryScore from userscoinfo where UserName ='"
							+ username
							+ "' and CourseID ='"
							+ courseID
							+ "' and SCOID = '" + scoID + "'";

					rs = db.executeQuery(sqlSelectPass);

					try {
						while (rs.next()) {
							int masteryScore = Integer.parseInt(rs
									.getString("MasteryScore"));

							if (score >= masteryScore) {
								String sqlUpdateSatus = "update userscoinfo set LessonStatus = 'passed' where UserName ='"
										+ username
										+ "' and CourseID ='"
										+ courseID
										+ "' and SCOID = '"
										+ scoID
										+ "'";
								db1.Update(sqlUpdateSatus);
							} else {
								String sqlUpdateSatus = "update userscoinfo set LessonStatus = 'failed' where UserName ='"
										+ username
										+ "' and CourseID ='"
										+ courseID
										+ "' and SCOID = '"
										+ scoID
										+ "'";
								db1.Update(sqlUpdateSatus);
							}
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
				// 加入到环境中
				lmsCore.setTotalTime(totalTime.toString());

				// 设置挂起状态
				if (lmsCore.getExit().getValue().equalsIgnoreCase("suspend"))// 挂起状态
				{
					lmsCore.setEntry("resume");
				} else {
					lmsCore.setEntry("");
				}

				// 改变课程状态从 未开启 变为 未完成
				if (lmsCore.getLessonStatus().getValue()
						.equalsIgnoreCase("not attempted")) {
					lmsCore.setLessonStatus("incomplete");
				}

				APIAdapter.theSCOData.setCore(lmsCore);

				// 提交数据
				result = LMSCommit("");

				if (result != cmiTrue) {
					System.out.println("LMSCommit失败.");
				} else {
					isLMSInitialized = false;

					result = cmiTrue;
				}
			}
		} else {
			APIAdapter.lmsErrorManager.SetCurrentErrorCode("201");
		}

		return result;
	}

	/**
	 * @Method LMSGetValue()
	 * @Description 负责将学员的学习数据由LMS取出。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSGetValue(String element) {
		if (CheckInitialization() != true) {
			// LMS 未初始化，不再继续执行
			String empty = new String("");
			return empty;
		}

		APIAdapter.lmsErrorManager.ClearCurrentErrorCode();
		this.dmErrorManager.ClearCurrentErrorCode();

		String rtnVal = null;

		DataModelInterface dmInterface = new DataModelInterface();

		// 获取value
		rtnVal = dmInterface.processGet(element, theSCOData, dmErrorManager);

		// 查看错误码
		lmsErrorManager.SetCurrentErrorCode(dmErrorManager
				.GetCurrentErrorCode());

		return rtnVal;
	}

	/**
	 * @Method LMSSetValue()
	 * @Description 负责储存学员之学习信息。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSSetValue(String element, String value) {
		String result = cmiFalse;

		APIAdapter.lmsErrorManager.ClearCurrentErrorCode();
		this.dmErrorManager.ClearCurrentErrorCode();

		if (CheckInitialization() != true) {
			// LMS未初始化，不再继续执行
			return result;
		}

		String setValue;

		// 检查参数
		String tempValue = String.valueOf(value);
		if (tempValue.equals("null")) {
			setValue = new String("");
		} else {
			setValue = tempValue;
		}

		String theRequest = element + "," + setValue;

		DataModelInterface dmInterface = new DataModelInterface();

		// 设置value
		dmInterface.processSet(theRequest, theSCOData, dmErrorManager);

		// 获取LMS错误码
		lmsErrorManager.SetCurrentErrorCode(dmErrorManager
				.GetCurrentErrorCode());
		if (lmsErrorManager.GetCurrentErrorCode() == "0") {
			// 没有发生错误，执行成功
			result = cmiTrue;
		}

		return result;
	}

	/**
	 * @Method LMSCommit()
	 * @Description 负责将章节的所有学习信息数据写入到学习文件中。
	 * @author liubin 2014.04.15
	 * @return
	 */

	public String LMSCommit(String param) {
		String result = cmiFalse;

		// 检查参数
		String tempParm = String.valueOf(param);
		if ((tempParm.equals("null") || tempParm.equals("")) == true) {
			if (CheckInitialization() != true) {
				// LMS没有初始化不继续执行
				return result;
			}

			// 获取用户sessionid等相关信息
			HttpServletRequest request = WebContextFactory.get()
					.getHttpServletRequest();
			HttpSession session = WebContextFactory.get().getSession();
			servletURL = (URL) session.getAttribute("servletURL");
			String sessionid = session.getId();// 获取用户session

			// 设置ServletURL路径
			try {
				String courseID = (String) session.getAttribute("COURSEID");
				String scoID = (String) session.getAttribute("SCOID");

				this.servletURL = new URL(servletURL + "&courseID=" + courseID
						+ "&scoID=" + scoID);
				
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

			ServletAgent servletProxy = new ServletAgent(this.servletURL,
					sessionid);

			String servletResult = servletProxy
					.PutSCOData(APIAdapter.theSCOData);

			if (servletResult.equals("OK") != true) {
				// 提交数据失败
				APIAdapter.lmsErrorManager.SetCurrentErrorCode("101");
			} else {
				// 提交数据成功
				APIAdapter.lmsErrorManager.ClearCurrentErrorCode();
				result = cmiTrue;
			}
		} else {
			// 参数不为空，产生错误
			APIAdapter.lmsErrorManager.SetCurrentErrorCode("201");
		}

		return result;
	}

	/**
	 * @Method LMSGetLastError()
	 * @Description 负责获取学习时产生的错误代码。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSGetLastError() {
		return APIAdapter.lmsErrorManager.GetCurrentErrorCode();
	}

	/**
	 * @Method LMSGetErrorString()
	 * @Description 负责将错误代码转化为错误解释。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSGetErrorString(String errorCode) {
		return APIAdapter.lmsErrorManager.GetErrorDescription(errorCode);
	}

	/**
	 * @Method CheckInitialization()
	 * @Description 检查是否已经初始化
	 * @author liubin 2014.04.15
	 * @return
	 */
	private boolean CheckInitialization() {
		if (isLMSInitialized != true) {
			APIAdapter.lmsErrorManager.SetCurrentErrorCode("301");
		}
		return isLMSInitialized;
	}

	/**
	 * @Method LMSGetDiagnostic()
	 * @Description 负责获得针对该错误而进行的处理方式。
	 * @author liubin 2014.04.15
	 * @return
	 */
	public String LMSGetDiagnostic(String errorCode) {
		return APIAdapter.lmsErrorManager.GetErrorDiagnostic(errorCode);
	}
}
