package com.kh.skillup.member.model.vo;

public class Member {
	private int memberNo;
	private String memberEmail;
	private String password;
	private String memberName;
	private String phone;
	private String interest;
	private String memberStatus;
	private String memberGrade;
	
	public Member() {
	}

	// 회원가입
	public Member(String memberEmail, String password, String memberName, String phone, String interest) {
		super();
		this.memberEmail = memberEmail;
		this.password = password;
		this.memberName = memberName;
		this.phone = phone;
		this.interest = interest;
	}

	// 로그인
	public Member(int memberNo, String memberEmail, String memberName, String phone, String interest, String memberGrade) {
		super();
		this.memberNo = memberNo;
		this.memberEmail = memberEmail;
		this.memberName = memberName;
		this.phone = phone;
		this.interest = interest;
		this.memberGrade = memberGrade;
	}

	public Member(int memberNo, String memberEmail, String password, String memberName, String phone, String interest, String memberStatus, String memberGrade) {
		super();
		this.memberNo = memberNo;
		this.memberEmail = memberEmail;
		this.password = password;
		this.memberName = memberName;
		this.phone = phone;
		this.interest = interest;
		this.memberStatus = memberStatus;
		this.memberGrade = memberGrade;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberEmail() {
		return memberEmail;
	}

	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getInterest() {
		return interest;
	}

	public void setInterest(String interest) {
		this.interest = interest;
	}

	public String getMemberStatus() {
		return memberStatus;
	}

	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}

	public String getMemberGrade() {
		return memberGrade;
	}

	public void setMemberGrade(String memberGrade) {
		this.memberGrade = memberGrade;
	}

	@Override
	public String toString() {
		return "Member [memberNo=" + memberNo + ", memberEmail=" + memberEmail + ", password=" + password + ", memberName=" + memberName + ", phone=" + phone + ", interest=" + interest + ", memberStatus=" + memberStatus + ", memberGrade=" + memberGrade + "]";
	}
}
