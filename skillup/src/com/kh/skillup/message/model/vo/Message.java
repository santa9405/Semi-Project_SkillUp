package com.kh.skillup.message.model.vo;

import java.sql.Timestamp;

public class Message {
	
	private int messageNo;
	private String receiveMemberName;
	private int receiveMemberNo;
	private String sendMemberName;
	private int sendMemberNo;
	private String content;
	private Timestamp recDate;
	private String readStatus;
	
	public Message() {
		super();
	}

	public Message(int messageNo, String receiveMemberName, int receiveMemberNo, String sendMemberName,
			int sendMemberNo, String content, Timestamp recDate, String readStatus) {
		super();
		this.messageNo = messageNo;
		this.receiveMemberName = receiveMemberName;
		this.receiveMemberNo = receiveMemberNo;
		this.sendMemberName = sendMemberName;
		this.sendMemberNo = sendMemberNo;
		this.content = content;
		this.recDate = recDate;
		this.readStatus = readStatus;
	}

	public int getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}

	public String getReceiveMemberName() {
		return receiveMemberName;
	}

	public void setReceiveMemberName(String receiveMemberName) {
		this.receiveMemberName = receiveMemberName;
	}

	public int getReceiveMemberNo() {
		return receiveMemberNo;
	}

	public void setReceiveMemberNo(int receiveMemberNo) {
		this.receiveMemberNo = receiveMemberNo;
	}

	public String getSendMemberName() {
		return sendMemberName;
	}

	public void setSendMemberName(String sendMemberName) {
		this.sendMemberName = sendMemberName;
	}

	public int getSendMemberNo() {
		return sendMemberNo;
	}

	public void setSendMemberNo(int sendMemberNo) {
		this.sendMemberNo = sendMemberNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Timestamp getRecDate() {
		return recDate;
	}

	public void setRecDate(Timestamp recDate) {
		this.recDate = recDate;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	public String toString() {
		return "Message [messageNo=" + messageNo + ", receiveMemberName=" + receiveMemberName + ", receiveMemberNo="
				+ receiveMemberNo + ", sendMemberName=" + sendMemberName + ", sendMemberNo=" + sendMemberNo
				+ ", content=" + content + ", recDate=" + recDate + ", readStatus=" + readStatus + "]";
	}
}
