package com.tank.client;

public class LMSErrorManager {

	private static String currentErrorCode;

	private static String[][] errors = {
			{ "0", "no error",
					"The previous LMS API Function call completed successfully." },
			{ "101", "General Exception",
					"An unspecified, unexpected exception has occured" },
			{ "201", "Invalid argument error", "" },
			{ "202", "Element cannot have children", "" },
			{ "203", "Element not an array - cannot have count", "" },
			{ "301", "Not initialized", "The LMS is not initialized." },
			{ "401", "Not implemented error",
					"The data model element in question was not implemented" },
			{
					"402",
					"Invalid set value, element is a keyword",
					"Trying to set a reserved keyword in the data model"
							+ "Trying to set a keyword (_count, _children, or _version) "
							+ "This is prohibited" },
			{
					"403",
					"Element is read only",
					"Data Element is Read Only (Not Writeable)"
							+ "Cannot call LMSSetValue() for the element in question" },
			{
					"404",
					"Element is write only",
					"Data Element is Write Only (Not Readable)"
							+ "Cannot call LMSGetValue() for the element in question" },
			{
					"405",
					"Incorrect Data Type",
					"Invalid Type being used for setting element"
							+ "The type being used as the set value argument does not match"
							+ " that of the element being set" } };

	/**
	 * @Method LMSErrorManager()
	 * @Description 构造函数,设置默认值为0
	 * @author liubin 2014.04.18
	 * @return
	 */
	public LMSErrorManager() {
		currentErrorCode = "0";
	}

	/**
	 * @Method GetCurrentErrorCode()
	 * @Description 获取当前错误号
	 * @author liubin 2014.04.18
	 * @return
	 */
	public String GetCurrentErrorCode() {
		return currentErrorCode;
	}

	/**
	 * @Method GetCurrentErrorCode()
	 * @Description 设置当前错误号
	 * @author liubin 2014.04.18
	 * @return
	 */
	public void SetCurrentErrorCode(String code) {

		if ((code != null) && (code != "")) {
			this.currentErrorCode = code;
		} else {
			this.currentErrorCode = "0";
		}

	}

	/**
	 * @Method ClearCurrentErrorCode()
	 * @Description 清空已经产生的错误码，设置为默认值
	 * @author liubin 2014.04.18
	 * @return
	 */
	public void ClearCurrentErrorCode() {
		this.currentErrorCode = errors[0][0];
	}

	/**
	 * @Method GetCurrentErrorCode()
	 * @Description 获取当前错误具体内容
	 * @author liubin 2014.04.18
	 * @return
	 */
	public String GetErrorDescription(String code) {
		if ((code != null) && (code != "")) {
			return GetErrorElement(code)[1];
		} else {
			return "";
		}
	}

	/**
	 * @Method GetCurrentErrorCode()
	 * @Description 获取当前错误具体内容
	 * @author liubin 2014.04.18
	 * @return
	 */
	public String GetErrorDiagnostic(String code) {
		if ((code != null) && (code != "")) {
			return GetErrorElement(code)[2];
		} else {
			return GetErrorElement(currentErrorCode)[2];
		}
	}

	/**
	 * @Method GetCurrentErrorCode()
	 * @Description 获取当前错误元素
	 * @author liubin 2014.04.18
	 * @return
	 */
	private String[] GetErrorElement(String code) {
		for (int i = 0; i < this.errors.length; i++) {
			if (this.errors[i][0].equalsIgnoreCase(code) == true)
				return this.errors[i];
		}

		String[] tmp = { "", "", "" };
		return tmp;

	}
}
