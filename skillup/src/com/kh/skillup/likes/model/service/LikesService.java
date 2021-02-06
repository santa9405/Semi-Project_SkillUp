package com.kh.skillup.likes.model.service;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.sql.Connection;
import com.kh.skillup.likes.model.dao.LikesDAO;

public class LikesService {
	private LikesDAO dao = new LikesDAO();

	public int insertLikes(int lessonNo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.insertLikes(conn, lessonNo, memberNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}

	public int deleteLikes(int lessonNo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.deleteLikes(conn, lessonNo, memberNo);
		
		if(result > 0) commit(conn);
		else rollback(conn);
		
		close(conn);
		return result;
	}
	
	public int selectLikes(int lessonNo, int memberNo) throws Exception {
		Connection conn = getConnetion();
		int result = dao.deleteLikes(conn, lessonNo, memberNo);
		close(conn);
		return result;
	}
}
