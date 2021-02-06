package com.kh.skillup.message.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.message.model.vo.Message;
import com.kh.skillup.message.model.vo.ReceiveMessage;
import com.kh.skillup.message.model.vo.SendMessage;

public class MessageDAO {
	
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public MessageDAO(){
		String fileName = MessageDAO.class.getResource("/com/kh/skillup/sql/message/message-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int getListCount(Connection conn, int loginMember, int index) throws Exception {
		int listCount = 0;
		String query = "";
		if(index== 1) {
			query += prop.getProperty("sendListCount");
		}
		else {
			query += prop.getProperty("receiveListCount");
		}
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, loginMember);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}
	
	public List<Message> receiveMessageList(Connection conn, PageInfo pInfo, int loginMember) throws Exception {
		String query = prop.getProperty("receiveMessageList");
		
		List<Message> list = new ArrayList<>();
		
		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, loginMember);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Message rMessage = new Message();
				rMessage.setMessageNo(rset.getInt("MESSAGE_NO"));
				rMessage.setSendMemberNo(rset.getInt("SEND_MEMBER"));
				rMessage.setContent(rset.getString("MESSAGE_CONTENT"));
				rMessage.setReadStatus(rset.getString("READ_STATUS"));
				rMessage.setSendMemberName(rset.getString("MEMBER_NAME"));
				list.add(rMessage);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public List<Message> sendMessageList(Connection conn, PageInfo pInfo, int loginMember) throws Exception {
		
		String query = prop.getProperty("sendMessageList");
		List<Message> list = new ArrayList<>();
		try {
			int startRow = (pInfo.getCurrentPage()-1)*pInfo.getLimit()+1;
			int endRow = startRow + pInfo.getLimit()-1;
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, loginMember);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			while(rset.next()) {
				Message sMessage = new Message();
				sMessage.setMessageNo(rset.getInt("MESSAGE_NO"));
				sMessage.setReceiveMemberNo(rset.getInt("REC_MEMBER"));
				sMessage.setContent(rset.getString("MESSAGE_CONTENT"));
				sMessage.setReadStatus(rset.getString("READ_STATUS"));
				sMessage.setReceiveMemberName(rset.getString("MEMBER_NAME"));
				list.add(sMessage);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public Message selectMessage(Connection conn, int messageNo) throws Exception {
		Message message = new Message();
		String query1 = "SELECT * FROM V_RMESSAGE WHERE MESSAGE_NO=" + messageNo;
		String query2 = "SELECT * FROM V_SMESSAGE WHERE MESSAGE_NO=" + messageNo;
		try {
			pstmt = conn.prepareStatement(query1);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				message.setSendMemberName(rset.getString("MEMBER_NAME"));
				message.setSendMemberNo(rset.getInt("SEND_MEMBER"));
				message.setContent(rset.getString("MESSAGE_CONTENT"));
				message.setRecDate(rset.getTimestamp("REC_DT"));
			}
			pstmt = conn.prepareStatement(query2);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				message.setReceiveMemberName(rset.getString("MEMBER_NAME"));
				message.setReceiveMemberNo(rset.getInt("REC_MEMBER"));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return message;
	}

	public int selectMember(Connection conn, String email) throws Exception {
		int memberNo = 0;
		String query = prop.getProperty("selectMember");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				memberNo = rset.getInt("MEMBER_NO");
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return memberNo;
	}

	public int insertMessage(Connection conn, Message message) throws Exception {
		int result = 0;
		String query = prop.getProperty("insertMessage");
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, message.getContent());
			pstmt.setInt(2, message.getReceiveMemberNo());
			pstmt.setInt(3, message.getSendMemberNo());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertMessage(Connection conn, int messageNo) throws Exception {
		int result = 0;
		String query = "UPDATE MESSAGE SET READ_STATUS = 'Y' WHERE MESSAGE_NO = " + messageNo;
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	public int deleteRMessage(Connection conn, String[] deleteMsg) throws Exception {
		
		int result = 0;
		String query = "UPDATE MESSAGE SET RECEIVE_MSG_FL = 'Y' WHERE ";
		for(int i=0; i<deleteMsg.length; i++) {
			query += "MESSAGE_NO = " + deleteMsg[i];
			if(i!=deleteMsg.length-1) {
				query += " OR ";
			}
		}
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}
	
	public int deleteSMessage(Connection conn, String[] deleteMsg) throws Exception {
		
		int result = 0;
		String query = "UPDATE MESSAGE SET SEND_MSG_FL = 'Y' WHERE ";
		for(int i=0; i<deleteMsg.length; i++) {
			query += "MESSAGE_NO = " + deleteMsg[i];
			if(i!=deleteMsg.length-1) {
				query += " OR ";
			}
		}
		try {
			stmt = conn.createStatement();
			result = stmt.executeUpdate(query);
		} finally {
			close(stmt);
		}
		return result;
	}

	public Member selectEmail(Connection conn, String memberNo) throws Exception {
		
		Member memberInfo = new Member();
		String query = "SELECT * FROM MEMBER WHERE MEMBER_NO = ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberNo);
			rset = pstmt.executeQuery();
			if(rset.next()) {
				memberInfo.setMemberEmail(rset.getString("MEMBER_EMAIL"));
				memberInfo.setMemberName(rset.getString("MEMBER_NAME"));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return memberInfo;
	}
}
