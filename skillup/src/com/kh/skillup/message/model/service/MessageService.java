package com.kh.skillup.message.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.naming.NamingException;

import com.kh.skillup.board.model.vo.Board;
import com.kh.skillup.board.model.vo.PageInfo;
import com.kh.skillup.member.model.vo.Member;
import com.kh.skillup.message.model.dao.MessageDAO;
import com.kh.skillup.message.model.vo.Message;
import com.kh.skillup.message.model.vo.ReceiveMessage;
import com.kh.skillup.message.model.vo.SendMessage;

public class MessageService {

	MessageDAO dao = new MessageDAO();
	
	private String replaceParameter(String param) {

		String result = param;
		if(result != null) {
			result = result.replaceAll("&", "&amp;");
			result = result.replaceAll("<", "&lt;");
			result = result.replaceAll(">", "&gt;");
			result = result	.replaceAll("\"", "&quot;");
		}
		return result;
	}
	
	public PageInfo getPageInfo(String page, int loginMember, int index) throws Exception {
		Connection conn = getConnetion();
		int currentPage = (page == null? 1: Integer.parseInt(page));
		int listCount = dao.getListCount(conn, loginMember, index);
		close(conn);
		return new PageInfo(currentPage, listCount);
	}
	
	public List<Message> rmessageList(PageInfo pInfo, int loginMember) throws Exception {
		Connection conn = getConnetion();
		List<Message> list = dao.receiveMessageList(conn, pInfo, loginMember);
		close(conn);
		return list;
	}
	
	public List<Message> smessageList(PageInfo pInfo, int loginMember) throws Exception {
		Connection conn = getConnetion();
		List<Message> list = dao.sendMessageList(conn, pInfo, loginMember);
		close(conn);
		return list;
	}

	public Message selectMessage(int messageNo) throws Exception {
		Connection conn = getConnetion();
		Message message = dao.selectMessage(conn, messageNo);
		close(conn);
		return message;
	}

	public int selectMember(String email) throws Exception {
		Connection conn = getConnetion();
		int memberNo = dao.selectMember(conn, email);
		close(conn);
		return memberNo;
	}

	public int insertMessage(Message message) throws Exception {
		Connection conn = getConnetion();
		String content = message.getContent();
		content = replaceParameter(content);
		content = content.replaceAll("\r\n", "<br>");
		message.setContent(content);
		int result = dao.insertMessage(conn, message);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int readStatus(int messageNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.insertMessage(conn, messageNo);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public int deleteRMessage(String[] deleteMsg) throws Exception{
		Connection conn = getConnetion();
		int result = dao.deleteRMessage(conn, deleteMsg);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}
	public int deleteSMessage(String[] deleteMsg) throws Exception{
		Connection conn = getConnetion();
		int result = dao.deleteSMessage(conn, deleteMsg);
		if(result > 0) {
			commit(conn);
		}
		else {
			rollback(conn);
		}
		close(conn);
		return result;
	}

	public Member selectInfo(String memberNo) throws Exception {
		Connection conn = getConnetion();
		Member memberInfo = dao.selectEmail(conn, memberNo);
		close(conn);
		return memberInfo;
	}
}
