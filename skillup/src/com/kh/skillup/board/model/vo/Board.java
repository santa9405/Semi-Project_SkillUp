package com.kh.skillup.board.model.vo;

import java.sql.Timestamp;

public class Board {
	private int documentNo;
	private String boardTitle;
	private String boardContent;
	private Timestamp boardDt;
	private int readCount;
	private String boardDeleteFl;
	private int memberNo;
	private String memberName;
	private String categoryName;
	private int boardNo;
	private int rowNum;
	
	public Board() {
		super();
	}

	public Board(int documentNo, String boardTitle, String boardContent, Timestamp boardDt, int readCount,
			String boardDeleteFl, int memberNo, String memberName, String categoryName, int boardNo, int rowNum) {
		super();
		this.documentNo = documentNo;
		this.boardTitle = boardTitle;
		this.boardContent = boardContent;
		this.boardDt = boardDt;
		this.readCount = readCount;
		this.boardDeleteFl = boardDeleteFl;
		this.memberNo = memberNo;
		this.memberName = memberName;
		this.categoryName = categoryName;
		this.boardNo = boardNo;
		this.rowNum = rowNum;
	}

	public int getDocumentNo() {
		return documentNo;
	}

	public void setDocumentNo(int documentNo) {
		this.documentNo = documentNo;
	}

	public String getBoardTitle() {
		return boardTitle;
	}

	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}

	public String getBoardContent() {
		return boardContent;
	}

	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}

	public Timestamp getBoardDt() {
		return boardDt;
	}

	public void setBoardDt(Timestamp boardDt) {
		this.boardDt = boardDt;
	}

	public int getReadCount() {
		return readCount;
	}

	public void setReadCount(int readCount) {
		this.readCount = readCount;
	}

	public String getBoardDeleteFl() {
		return boardDeleteFl;
	}

	public void setBoardDeleteFl(String boardDeleteFl) {
		this.boardDeleteFl = boardDeleteFl;
	}

	public int getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(int memberNo) {
		this.memberNo = memberNo;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public int getBoardNo() {
		return boardNo;
	}

	public void setBoardNo(int boardNo) {
		this.boardNo = boardNo;
	}

	public int getRowNum() {
		return rowNum;
	}

	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}

	@Override
	public String toString() {
		return "Board [documentNo=" + documentNo + ", boardTitle=" + boardTitle + ", boardContent=" + boardContent
				+ ", boardDt=" + boardDt + ", readCount=" + readCount + ", boardDeleteFl=" + boardDeleteFl
				+ ", memberNo=" + memberNo + ", memberName=" + memberName + ", categoryName=" + categoryName
				+ ", boardNo=" + boardNo + ", rowNum=" + rowNum + "]";
	}
}
