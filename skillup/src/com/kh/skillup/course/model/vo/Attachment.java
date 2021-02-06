package com.kh.skillup.course.model.vo;

public class Attachment {

	private int fileNo;
	private String filePath;
	private String fileName;
	private int fileLevel;
	private int lessonNo;

	
	public Attachment() {
		// TODO Auto-generated constructor stub
	}


	public Attachment(int fileNo, String filePath, String fileName, int fileLevel, int lessonNo) {
		super();
		this.fileNo = fileNo;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileLevel = fileLevel;
		this.lessonNo = lessonNo;
	}


	public int getFileNo() {
		return fileNo;
	}


	public void setFileNo(int fileNo) {
		this.fileNo = fileNo;
	}


	public String getFilePath() {
		return filePath;
	}


	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}


	public String getFileName() {
		return fileName;
	}


	public void setFileName(String fileName) {
		this.fileName = fileName;
	}


	public int getFileLevel() {
		return fileLevel;
	}


	public void setFileLevel(int fileLevel) {
		this.fileLevel = fileLevel;
	}


	public int getLessonNo() {
		return lessonNo;
	}


	public void setLessonNo(int lessonNo) {
		this.lessonNo = lessonNo;
	}


	@Override
	public String toString() {
		return "Attachment [fileNo=" + fileNo + ", filePath=" + filePath + ", fileName=" + fileName + ", fileLevel="
				+ fileLevel + ", lessonNo=" + lessonNo + "]";
	}
	
	
	
}
