package com.kh.skillup.pay.model.vo;

import java.sql.Date;

public class Pay {

	private int paymentNo;
	private Date paymentDt;
	private int lessonNo;
	private int ctgrCd;
	private int memberNo;
	private String lessonTitle;
	private String memberEmail;
	private int pay;
	private Date lessonDt;
	private String paymentCk;
	
public Pay() {
	// TODO Auto-generated constructor stub
}

public Pay(int paymentNo, Date paymentDt, int lessonNo, int ctgrCd, int memberNo, String lessonTitle,
		String memberEmail, int pay, Date lessonDt, String paymentCk) {
	super();
	this.paymentNo = paymentNo;
	this.paymentDt = paymentDt;
	this.lessonNo = lessonNo;
	this.ctgrCd = ctgrCd;
	this.memberNo = memberNo;
	this.lessonTitle = lessonTitle;
	this.memberEmail = memberEmail;
	this.pay = pay;
	this.lessonDt = lessonDt;
	this.paymentCk = paymentCk;
}

public int getPaymentNo() {
	return paymentNo;
}

public void setPaymentNo(int paymentNo) {
	this.paymentNo = paymentNo;
}

public Date getPaymentDt() {
	return paymentDt;
}

public void setPaymentDt(Date paymentDt) {
	this.paymentDt = paymentDt;
}

public int getLessonNo() {
	return lessonNo;
}

public void setLessonNo(int lessonNo) {
	this.lessonNo = lessonNo;
}

public int getCtgrCd() {
	return ctgrCd;
}

public void setCtgrCd(int ctgrCd) {
	this.ctgrCd = ctgrCd;
}

public int getMemberNo() {
	return memberNo;
}

public void setMemberNo(int memberNo) {
	this.memberNo = memberNo;
}

public String getLessonTitle() {
	return lessonTitle;
}

public void setLessonTitle(String lessonTitle) {
	this.lessonTitle = lessonTitle;
}

public String getMemberEmail() {
	return memberEmail;
}

public void setMemberEmail(String memberEmail) {
	this.memberEmail = memberEmail;
}

public int getPay() {
	return pay;
}

public void setPay(int pay) {
	this.pay = pay;
}

public Date getLessonDt() {
	return lessonDt;
}

public void setLessonDt(Date lessonDt) {
	this.lessonDt = lessonDt;
}

public String getPaymentCk() {
	return paymentCk;
}

public void setPaymentCk(String paymentCk) {
	this.paymentCk = paymentCk;
}

@Override
public String toString() {
	return "Pay [paymentNo=" + paymentNo + ", paymentDt=" + paymentDt + ", lessonNo=" + lessonNo + ", ctgrCd=" + ctgrCd
			+ ", memberNo=" + memberNo + ", lessonTitle=" + lessonTitle + ", memberEmail=" + memberEmail + ", pay="
			+ pay + ", lessonDt=" + lessonDt + ", paymentCk=" + paymentCk + "]";
}


	
	
	
	
}
