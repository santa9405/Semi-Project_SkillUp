package com.kh.skillup.message.model.vo;

public class ReceiveMessage {
	
	private int messageNo;
	private String content;
	private int receiveMember;
	private int sendMember;
	private String sendMemberName;
	private String readStatus;
	
	public ReceiveMessage() {
		super();
	}

	public ReceiveMessage(int messageNo, String content, int receiveMember, int sendMember, String sendMemberName,
			String readStatus) {
		super();
		this.messageNo = messageNo;
		this.content = content;
		this.receiveMember = receiveMember;
		this.sendMember = sendMember;
		this.sendMemberName = sendMemberName;
		this.readStatus = readStatus;
	}

	public int getMessageNo() {
		return messageNo;
	}

	public void setMessageNo(int messageNo) {
		this.messageNo = messageNo;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getReceiveMember() {
		return receiveMember;
	}

	public void setReceiveMember(int receiveMember) {
		this.receiveMember = receiveMember;
	}

	public int getSendMember() {
		return sendMember;
	}

	public void setSendMember(int sendMember) {
		this.sendMember = sendMember;
	}

	public String getSendMemberName() {
		return sendMemberName;
	}

	public void setSendMemberName(String sendMemberName) {
		this.sendMemberName = sendMemberName;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	public String toString() {
		return "ReceiveMessage [messageNo=" + messageNo + ", content=" + content + ", receiveMember=" + receiveMember
				+ ", sendMember=" + sendMember + ", sendMemberName=" + sendMemberName + ", readStatus=" + readStatus
				+ "]";
	}
}
