package com.kh.skillup.review.model.vo;

import java.sql.Timestamp;

public class Report {
	private int reportNo; // 신고 번호
	private String title; // 신고 제목
	private String content; // 신고 내용
	private Timestamp reportDt; // 신고 날짜
	private int reviewNo; // 리뷰 번호
	private int memberNo; // 신고 회원 번호
	private int ctgrCd; // 카테고리 코드
	
	public Report(int reportNo, String title, String content, Timestamp reportDt, int reviewNo, int memberNo,
			int ctgrCd) {
		super();
		this.reportNo = reportNo;
		this.title = title;
		this.content = content;
		this.reportDt = reportDt;
		this.reviewNo = reviewNo;
		this.memberNo = memberNo;
		this.ctgrCd = ctgrCd;
	}

	public int getReportNo() {
		return reportNo;
	}

	public void setReportNo(int reportNo) {
		this.reportNo = reportNo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getReportDt() {
		return reportDt;
	}

	public void setReportDt(Timestamp reportDt) {
		this.reportDt = reportDt;
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public int getCtgrCd() {
		return ctgrCd;
	}

	public void setCtgrCd(int ctgrCd) {
		this.ctgrCd = ctgrCd;
	}

	@Override
	public String toString() {
		return "Report [reportNo=" + reportNo + ", title=" + title + ", content=" + content + ", reportDt=" + reportDt
				+ ", reviewNo=" + reviewNo + ", memberNo=" + memberNo + ", ctgrCd=" + ctgrCd + "]";
	}
	
}