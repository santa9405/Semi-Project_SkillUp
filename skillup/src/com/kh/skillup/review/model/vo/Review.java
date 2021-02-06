package com.kh.skillup.review.model.vo;

import java.sql.Timestamp;

public class Review {
	private int reviewNo;         // 리뷰 번호
	private String reviewContent; // 리뷰 내용
	private Timestamp reviewDt;   // 작성 날짜
	private String status;          // 리뷰 상태
	private int score;            // 만족도
	private int reviewWriter;     // 작성 회원 번호
	private int instructorNo;     // 전문가 번호
	private int subjectNo;        // 수강 번호
	private String writerName;    // 작성자명
	
	public Review() { }
	
	public Review(int reviewNo, String reviewContent, Timestamp reviewDt, String status, int score, int reviewWriter,
			int instructorNo, int subjectNo, String writerName) {
		super();
		this.reviewNo = reviewNo;
		this.reviewContent = reviewContent;
		this.reviewDt = reviewDt;
		this.status = status;
		this.score = score;
		this.reviewWriter = reviewWriter;
		this.instructorNo = instructorNo;
		this.subjectNo = subjectNo;
		this.writerName = writerName;
	}

	public Review(String writerName) {
		super();
		this.writerName = writerName;
	}

	public String getWriterName() {
		return writerName;
	}

	public void setWriterName(String writerName) {
		this.writerName = writerName;
	}

	public int getReviewNo() {
		return reviewNo;
	}

	public void setReviewNo(int reviewNo) {
		this.reviewNo = reviewNo;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public Timestamp getReviewDt() {
		return reviewDt;
	}

	public void setReviewDt(Timestamp reviewDt) {
		this.reviewDt = reviewDt;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getReviewWriter() {
		return reviewWriter;
	}

	public void setReviewWriter(int reviewWriter) {
		this.reviewWriter = reviewWriter;
	}

	public int getInstructorNo() {
		return instructorNo;
	}

	public void setInstructorNo(int instructorNo) {
		this.instructorNo = instructorNo;
	}

	public int getSubjectNo() {
		return subjectNo;
	}

	public void setSubjectNo(int subjectNo) {
		this.subjectNo = subjectNo;
	}

	@Override
	public String toString() {
		return "Review [reviewNo=" + reviewNo + ", reviewContent=" + reviewContent + ", reviewDt=" + reviewDt
				+ ", status=" + status + ", score=" + score + ", reviewWriter=" + reviewWriter + ", instructorNo="
				+ instructorNo + ", subjectNo=" + subjectNo + ", writerName=" + writerName + "]";
	}

}