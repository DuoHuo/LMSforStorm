package com.tank.model;

import java.sql.Date;

public class Discuss {
	int id;
	String courseTitle;
	String sendName;
	String content;
	String time;
	String imgPath;
	int recordNum;
	int anNum;
	int queNum;
	int quesionId;
	int flag;
	String latestAContent;
	String latestASendName;
	String latestASendTime;
	
	
	public Discuss(){
		this.anNum = 0;
		this.queNum = 0;
		this.recordNum = 0;
		this.flag = 0;
	}
	
	public String getImgPath() {
		return imgPath;
	}
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	public int getFlag() {
		return flag;
	}
	public void setFlag(int flag) {
		this.flag = flag;
	}
	public String getLatestAContent() {
		return latestAContent;
	}
	public void setLatestAContent(String latestAContent) {
		this.latestAContent = latestAContent;
	}
	public String getLatestASendName() {
		return latestASendName;
	}
	public void setLatestASendName(String latestASendName) {
		this.latestASendName = latestASendName;
	}
	public String getLatestASendTime() {
		return latestASendTime;
	}
	public void setLatestASendTime(String latestASendTime) {
		this.latestASendTime = latestASendTime;
	}
	public int getQuesionId() {
		return quesionId;
	}
	public void setQuesionId(int quesionId) {
		this.quesionId = quesionId;
	}
	public int getAnNum() {
		return anNum;
	}
	public void setAnNum(int anNum) {
		this.anNum = anNum;
	}
	public int getQueNum() {
		return queNum;
	}
	public void setQueNum(int queNum) {
		this.queNum = queNum;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCourseTitle() {
		return courseTitle;
	}
	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getSendName() {
		return sendName;
	}
	public void setSendName(String sendName) {
		this.sendName = sendName;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getRecordNum() {
		return recordNum;
	}
	public void setRecordNum(int recordNum) {
		this.recordNum = recordNum;
	}
}
