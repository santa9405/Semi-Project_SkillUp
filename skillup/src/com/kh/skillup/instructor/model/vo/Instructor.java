package com.kh.skillup.instructor.model.vo;

public class Instructor {
	private int instr; 			  // 전문가 번호
	private String introduction;  // 소개
	private String career;		  // 경력
	private int ctgrCd;			  // 분야 카테고리
	private String instrName;	  // 이름
	private String permitFl;	  // 승인 여부
	private String saveFl;		  // 저장 여부
	private int enrollmentNo;	  // 신청 번호
	
	public Instructor() { }

	
	
	public Instructor(int instr, int ctgrCd, String instrName, String permitFl, int enrollmentNo) {
		super();
		this.instr = instr;
		this.ctgrCd = ctgrCd;
		this.instrName = instrName;
		this.permitFl = permitFl;
		this.enrollmentNo = enrollmentNo;
	}

	public Instructor(int instr, String introduction, String career, int ctgrCd, String instrName, String permitFl,
			String saveFl) {
		super();
		this.instr = instr;
		this.introduction = introduction;
		this.career = career;
		this.ctgrCd = ctgrCd;
		this.instrName = instrName;
		this.permitFl = permitFl;
		this.saveFl = saveFl;
	}

	public int getEnrollmentNo() {
		return enrollmentNo;
	}



	public void setEnrollmentNo(int enrollmentNo) {
		this.enrollmentNo = enrollmentNo;
	}



	public int getInstr() {
		return instr;
	}

	public void setInstr(int instr) {
		this.instr = instr;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public int getCtgrCd() {
		return ctgrCd;
	}

	public void setCtgrCd(int ctgrCd) {
		this.ctgrCd = ctgrCd;
	}

	public String getInstrName() {
		return instrName;
	}

	public void setInstrName(String instrName) {
		this.instrName = instrName;
	}

	public String getPermitFl() {
		return permitFl;
	}

	public void setPermitFl(String permitFl) {
		this.permitFl = permitFl;
	}

	public String getSaveFl() {
		return saveFl;
	}

	public void setSaveFl(String saveFl) {
		this.saveFl = saveFl;
	}

	@Override
	public String toString() {
		return "Instructor [instr=" + instr + ", introduction=" + introduction + ", career=" + career + ", ctgrCd="
				+ ctgrCd + ", instrName=" + instrName + ", permitFl=" + permitFl + ", saveFl=" + saveFl
				+ ", enrollmentNo=" + enrollmentNo + "]";
	}
	
}
