package com.kh.skillup.member.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import com.kh.skillup.member.model.vo.Member;

import oracle.net.aso.p;

public class MemberDAO {

	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	private Properties prop = null;
	
	public MemberDAO() {
		try {
			String filePath = MemberDAO.class.getResource("/com/kh/skillup/sql/member/member-query.xml").getPath();
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public int emailDupCheck(Connection conn, String email) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("emailDupCheck");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, email);
			rset = pstmt.executeQuery();
			
			if(rset.next())
				result = rset.getInt(1);
			
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}
	
	public int signUp(Connection conn, Member member) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("signUp");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getPhone());
			pstmt.setString(5, member.getInterest());
			
			result = pstmt.executeUpdate();
			
		}finally {
			close(pstmt);
		}
		return result;
	}
	
	public Member login(Connection conn, Member member) throws Exception {
		
		Member loginMember = null;
		String query = prop.getProperty("login");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getMemberEmail());
			pstmt.setString(2, member.getPassword());
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				loginMember = new Member(rset.getInt("MEMBER_NO"), rset.getString("MEMBER_EMAIL"), 
						rset.getString("MEMBER_NAME"), rset.getString("PHONE"), 
						rset.getString("INTEREST"), rset.getString("MEMBER_GRADE"));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return loginMember;
	}

	public int updateMember(Connection conn, Member member) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("updateMember");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getPhone());
			pstmt.setString(2, member.getInterest());
			pstmt.setInt(3, member.getMemberNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	public int checkCurrentPwd(Connection conn, Member loginMember) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("checkCurrentPwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, loginMember.getMemberNo());
			pstmt.setString(2, loginMember.getPassword());
			rset = pstmt.executeQuery();
			
			if(rset.next())
				result = rset.getInt(1);
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int updatePwd(Connection conn, Member loginMember) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("updatePwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, loginMember.getPassword());
			pstmt.setInt(2, loginMember.getMemberNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateStatus(Connection conn, int memberNo) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("updateStatus");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int findPwd(Connection conn, Member member) throws Exception {
		int result = 0;
		String query = prop.getProperty("findPwd");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberEmail());
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
}
