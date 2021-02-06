package com.kh.skillup.searchlesson.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.kh.skillup.lesson.model.vo.Attachment;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.lesson.model.vo.PageInfo;

public class SearchLessonDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	public int getListCount(Connection conn, String condition) throws Exception {
		int listCount = 0;
		String query = "SELECT COUNT(*) FROM V_LESSON WHERE " + "LESSON_FL = 'N' AND LESSON_AVA = 'Y' " + condition;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next())
				listCount = rset.getInt(1);
		} finally {
			close(rset);
			close(stmt);
		}
		return listCount;
	}

	public List<Lesson> searchLessonList(Connection conn, PageInfo pInfo, Map<String, Object> cMap) throws Exception {
		
		List<Lesson> lessonList = null;
		String query = " SELECT * FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM" + 
				"        (SELECT * " + 
				"        FROM V_LESSON " + 
				"        WHERE LESSON_FL = 'N' AND LESSON_AVA = 'Y' " + cMap.get("condition")
				+ cMap.get("orderby") +") V ) " + 
				"WHERE RNUM BETWEEN ? AND ?";

		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			lessonList = new ArrayList<Lesson>();
			
			while(rset.next()) {
				Lesson lesson = new Lesson(); 
				lesson.setLessonNo(rset.getInt("LESSON_NO"));
				lesson.setLessonTitle(rset.getString("LESSON_TITLE"));
				lesson.setCategoryName(rset.getString("CTGR_NM"));
				lesson.setMemberName(rset.getString("MEMBER_NAME"));
				lesson.setPrice(rset.getInt("PRICE"));
				lessonList.add(lesson);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return lessonList;
	}

	public List<Attachment> searchThumbnailList(Connection conn, PageInfo pInfo, Map<String, Object> cMap) throws Exception {
		
		List<Attachment> fList = null;
		String query = 
				"SELECT * " + 
				"FROM LESSON_FILE " + 
				"WHERE (FILE_LEVEL, LESSON_NO) IN " + 
				"(SELECT MIN(FILE_LEVEL), LESSON_NO " + 
				"FROM LESSON_FILE " + 
				"WHERE LESSON_NO IN ( " + 
				"    SELECT LESSON_NO FROM " + 
				"    (SELECT ROWNUM RNUM , V.* " + 
				"    FROM" + 
				"        (SELECT * " + 
				"        FROM V_LESSON " + 
				"        WHERE LESSON_FL = 'N' AND LESSON_AVA = 'Y' " + cMap.get("condition")
				+ cMap.get("orderby") +") V ) " + 
				"WHERE RNUM BETWEEN ? AND ?) " + "AND FILE_FL = 'N'" +
				"GROUP BY LESSON_NO)";
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			rset = pstmt.executeQuery();
			
			fList = new ArrayList<Attachment>();
			while(rset.next()) {
				Attachment at = new Attachment();
				at.setFileName(rset.getString("FILE_NAME"));
				at.setParentLessonNo(rset.getInt("LESSON_NO"));
				
				fList.add(at);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return fList;
	}
}
