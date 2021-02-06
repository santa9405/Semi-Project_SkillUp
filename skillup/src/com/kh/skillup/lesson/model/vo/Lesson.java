package com.kh.skillup.lesson.model.vo;

import java.sql.Date;

public class Lesson {
	private int lessonNo;
	private String lessonTitle;
	private String lessonContent;
	private String place;
	private int price;
	private int maxNum;
	private Date lessonDate;
	private String memberName;
	private int instr;
	private int lessonCnt;
	private int lessonTime;
	private char lessonAva;
	private int readCnt;
	private char lessonFl;
	private char lessonType;
	private String categoryName;
	private int likesCnt;

	public Lesson() {}

	// 수업 등록
	public Lesson(String lessonTitle, String lessonContent, String place, int price, int maxNum, Date lessonDate, int lessonCnt, int lessonTime, char lessonType) {
		super();
		this.lessonTitle = lessonTitle;
		this.lessonContent = lessonContent;
		this.place = place;
		this.price = price;
		this.maxNum = maxNum;
		this.lessonDate = lessonDate;
		this.lessonCnt = lessonCnt;
		this.lessonTime = lessonTime;
		this.lessonType = lessonType;
	}

	public Lesson(int lessonNo, String lessonTitle, String lessonContent, String place, int price, int maxNum, Date lessonDate, String memberName, int instr, int lessonCnt, int lessonTime, char lessonAva, int readCnt, char lessonFl, char lessonType, String categoryName, int likesCnt) {
		super();
		this.lessonNo = lessonNo;
		this.lessonTitle = lessonTitle;
		this.lessonContent = lessonContent;
		this.place = place;
		this.price = price;
		this.maxNum = maxNum;
		this.lessonDate = lessonDate;
		this.memberName = memberName;
		this.instr = instr;
		this.lessonCnt = lessonCnt;
		this.lessonTime = lessonTime;
		this.lessonAva = lessonAva;
		this.readCnt = readCnt;
		this.lessonFl = lessonFl;
		this.lessonType = lessonType;
		this.categoryName = categoryName;
		this.likesCnt = likesCnt;
	}

	public int getLessonNo() {
		return lessonNo;
	}

	public void setLessonNo(int lessonNo) {
		this.lessonNo = lessonNo;
	}

	public String getLessonTitle() {
		return lessonTitle;
	}

	public void setLessonTitle(String lessonTitle) {
		this.lessonTitle = lessonTitle;
	}

	public String getLessonContent() {
		return lessonContent;
	}

	public void setLessonContent(String lessonContent) {
		this.lessonContent = lessonContent;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getMaxNum() {
		return maxNum;
	}

	public void setMaxNum(int maxNum) {
		this.maxNum = maxNum;
	}

	public Date getLessonDate() {
		return lessonDate;
	}

	public void setLessonDate(Date lessonDate) {
		this.lessonDate = lessonDate;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getInstr() {
		return instr;
	}

	public void setInstr(int instr) {
		this.instr = instr;
	}

	public int getLessonCnt() {
		return lessonCnt;
	}

	public void setLessonCnt(int lessonCnt) {
		this.lessonCnt = lessonCnt;
	}

	public int getLessonTime() {
		return lessonTime;
	}

	public void setLessonTime(int lessonTime) {
		this.lessonTime = lessonTime;
	}

	public char getLessonAva() {
		return lessonAva;
	}

	public void setLessonAva(char lessonAva) {
		this.lessonAva = lessonAva;
	}

	public int getReadCnt() {
		return readCnt;
	}

	public void setReadCnt(int readCnt) {
		this.readCnt = readCnt;
	}

	public char getLessonFl() {
		return lessonFl;
	}

	public void setLessonFl(char lessonFl) {
		this.lessonFl = lessonFl;
	}

	public char getLessonType() {
		return lessonType;
	}

	public void setLessonType(char lessonType) {
		this.lessonType = lessonType;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	
	public int getLikesCnt() {
		return likesCnt;
	}

	public void setLikesCnt(int likesCnt) {
		this.likesCnt = likesCnt;
	}

	@Override
	public String toString() {
		return "Lesson [lessonNo=" + lessonNo + ", lessonTitle=" + lessonTitle + ", lessonContent=" + lessonContent + ", place=" + place + ", price=" + price + ", maxNum=" + maxNum + ", lessonDate=" + lessonDate + ", memberName=" + memberName + ", instr=" + instr + ", lessonCnt=" + lessonCnt
				+ ", lessonTime=" + lessonTime + ", lessonAva=" + lessonAva + ", readCnt=" + readCnt + ", lessonFl=" + lessonFl + ", lessonType=" + lessonType + ", categoryName=" + categoryName + ", likesCnt=" + likesCnt + "]";
	}
}
