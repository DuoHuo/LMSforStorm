package com.tank.model;

public class Course {

	String courseID;
	String courseTitle;
	String checked="";
	String title;
	String regnum;
	String image;
	String evaluate[] = new String[5];
	String lessonStatus;
	String author;
	int evanum;

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRegnum() {
		return regnum;
	}

	public void setRegnum(String regnum) {
		this.regnum = regnum;
	}

	public String[] getEvaluate() {
		return evaluate;
	}

	public void setEvaluate(String[] evaluate) {
		this.evaluate = evaluate;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getLessonStatus() {
		return lessonStatus;
	}

	public void setLessonStatus(String lessonStatus) {
		this.lessonStatus = lessonStatus;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getEvanum() {
		return evanum;
	}

	public void setEvanum(int evanum) {
		this.evanum = evanum;
	}

}
