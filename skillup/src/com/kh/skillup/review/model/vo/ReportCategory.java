package com.kh.skillup.review.model.vo;

public class ReportCategory {
	private int ctgrCd; // 카테고리 코드
	private int ctgrNm; // 카테고리 이름
	
	public ReportCategory() { }

	public ReportCategory(int ctgrCd, int ctgrNm) {
		super();
		this.ctgrCd = ctgrCd;
		this.ctgrNm = ctgrNm;
	}

	public int getCtgrCd() {
		return ctgrCd;
	}

	public void setCtgrCd(int ctgrCd) {
		this.ctgrCd = ctgrCd;
	}

	public int getCtgrNm() {
		return ctgrNm;
	}

	public void setCtgrNm(int ctgrNm) {
		this.ctgrNm = ctgrNm;
	}

	@Override
	public String toString() {
		return "ReportCategory [ctgrCd=" + ctgrCd + ", ctgrNm=" + ctgrNm + "]";
	}

}