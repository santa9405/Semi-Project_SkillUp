package com.kh.skillup.likes.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class LikesDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	public int insertLikes(Connection conn, int lessonNo, int memberNo) throws Exception {
		int result = 0;
		String query = "INSERT INTO LIKES VALUES(?, ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteLikes(Connection conn, int lessonNo, int memberNo) throws Exception {
		int result = 0;
		String query = "DELETE FROM LIKES WHERE LESSON_NO = ? AND MEMBER_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			pstmt.setInt(2, memberNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public int selectLikes(Connection conn, int lessonNo, int memberNo) throws Exception {
		int result = 0;
		String query = "SELECT CCOUNT(*) FROM LIKES WHERE LESSON_NO = ? AND MEMBER_NO = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			pstmt.setInt(2, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next())
				result = rset.getInt(1);
		} finally {
			close(pstmt);
		}
		return result;
	}
}
