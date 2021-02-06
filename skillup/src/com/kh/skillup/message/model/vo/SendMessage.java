package com.kh.skillup.message.model.vo;

public class SendMessage {
	
	private int messageNo;
	private String content;
	private int receiveMember;
	private int sendMember;
	private String receiveMemberName;
	private String readStatus;
	
	public SendMessage() {
		super();
	}

	public SendMessage(int messageNo, String content, int receiveMember, int sendMember, String receiveMemberName,
			String readStatus) {
		super();
		this.messageNo = messageNo;
		this.content = content;
		this.receiveMember = receiveMember;
		this.sendMember = sendMember;
		this.receiveMemberName = receiveMemberName;
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

	public String getReceiveMemberName() {
		return receiveMemberName;
	}

	public void setReceiveMemberName(String receiveMemberName) {
		this.receiveMemberName = receiveMemberName;
	}

	public String getReadStatus() {
		return readStatus;
	}

	public void setReadStatus(String readStatus) {
		this.readStatus = readStatus;
	}

	@Override
	public String toString() {
		return "SendMessage [messageNo=" + messageNo + ", content=" + content + ", receiveMember=" + receiveMember
				+ ", sendMember=" + sendMember + ", receiveMemberName=" + receiveMemberName + ", readStatus="
				+ readStatus + "]";
	}
}
