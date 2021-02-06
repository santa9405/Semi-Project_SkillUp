package com.kh.skillup.instructor.model.vo;

public class Attachment {
	private int fileNo;
	private String filePath;
	private String fileName;
	private int fileLevel;
	private int parentInstrNo;
	
	public Attachment() {}
	
	public Attachment(int fileNo, String fileName, int fileLevel) {
		super();
		this.fileNo = fileNo;
		this.fileName = fileName;
		this.fileLevel = fileLevel;
	}

	public Attachment(int fileNo, String filePath, String fileName, int fileLevel, int parantBoardNo) {
		super();
		this.fileNo = fileNo;
		this.filePath = filePath;
		this.fileName = fileName;
		this.fileLevel = fileLevel;
		this.parentInstrNo = parantBoardNo;
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

	public int getParentInstrNo() {
		return parentInstrNo;
	}

	public void setParentInstrNo(int parentInstrNo) {
		this.parentInstrNo = parentInstrNo;
	}

	@Override
	public String toString() {
		return "Attachment [fileNo=" + fileNo + ", filePath=" + filePath + ", fileName=" + fileName + ", fileLevel="
				+ fileLevel + ", parentInstrNo=" + parentInstrNo + "]";
	}
	
}
