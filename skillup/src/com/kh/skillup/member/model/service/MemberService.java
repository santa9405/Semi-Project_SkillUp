package com.kh.skillup.member.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.NamingException;

import com.kh.skillup.member.model.dao.MemberDAO;
import com.kh.skillup.member.model.vo.Member;

public class MemberService {

	private MemberDAO dao = new MemberDAO();
	
	public int emailDupCheck(String email) throws Exception {
		Connection conn = getConnetion();
		int result = dao.emailDupCheck(conn, email);
		close(conn);
		return result;
	}
	
	public int signUp(Member member) throws Exception {
		Connection conn = getConnetion();
		int result = dao.signUp(conn, member);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
	
	public Member login(Member member) throws Exception {
		Connection conn = getConnetion();
		Member loginMember = dao.login(conn, member);
		close(conn);
		return loginMember;
	}

	public int updateMember(Member member) throws Exception {
		Connection conn = getConnetion();
		int result = dao.updateMember(conn, member);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}

	public int updatePwd(Member loginMember, String newPwd) throws Exception {
		Connection conn = getConnetion();
		int result = dao.checkCurrentPwd(conn, loginMember);
		
		if(result > 0) {
			loginMember.setPassword(newPwd);
			result = dao.updatePwd(conn, loginMember);
			
			if(result > 0) commit(conn);
			else rollback(conn);
			
		} else
			result = -1;

		close(conn);
		return result;
	}

	public int updateStatus(Member loginMember) throws Exception {
		Connection conn = getConnetion();
		int result = dao.checkCurrentPwd(conn, loginMember);
		
		if(result > 0) {
			result = dao.updateStatus(conn, loginMember.getMemberNo());
			
			if(result > 0) commit(conn);
			else rollback(conn);
		} else 
			result = -1;

		close(conn);
		return result;
	}

	public int findPwd(Member member) throws Exception {
		Connection conn = getConnetion();
		int result = dao.findPwd(conn, member);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
}
