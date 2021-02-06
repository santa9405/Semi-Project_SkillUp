package com.kh.skillup.lesson.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.skillup.lesson.model.vo.Attachment;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.lesson.model.vo.PageInfo;

public class LessonDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;
	
	private Properties prop = null;
	
	public LessonDAO(){
		String fileName = LessonDAO.class.getResource("/com/kh/skillup/sql/lesson/lesson-query.xml").getPath();
		
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName)); 
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int getListCount(Connection conn) throws Exception {
		
		int listCount = 0;
		String query = prop.getProperty("getListCount");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next())
				listCount = rset.getInt(1);
		} finally {
			close(stmt);
			close(rset);
		}
		return listCount;
	}

	public List<Lesson> selectLessonList(Connection conn, PageInfo pInfo) throws Exception {
		
		List<Lesson> lessonList = null;
		String query = prop.getProperty("selectLessonList");
		
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

	public List<Lesson> selectMyLessonList(Connection conn, PageInfo pInfo, int memberNo) throws Exception {
		
		List<Lesson> lessonList = null;
		String query = prop.getProperty("selectMyLessonList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
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
	
	public Lesson selectLesson(Connection conn, int lessonNo) throws Exception {
		
		Lesson lesson = null;
		String query = prop.getProperty("selectLesson");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				lesson = new Lesson();
				lesson.setLessonNo(rset.getInt("LESSON_NO"));
				lesson.setLessonTitle(rset.getString("LESSON_TITLE"));
				lesson.setLessonContent(rset.getString("LESSON_CONTENT"));
				lesson.setPlace(rset.getString("PLACE"));
				lesson.setPrice(rset.getInt("PRICE"));
				lesson.setMaxNum(rset.getInt("MAX_NUM"));
				lesson.setLessonDate(rset.getDate("LESSON_DT"));
				lesson.setLessonCnt(rset.getInt("LESSON_CNT"));
				lesson.setLessonTime(rset.getInt("LESSON_TIME"));
				lesson.setLessonType(rset.getString("LESSON_TYPE").charAt(0));
				lesson.setMemberName(rset.getString("MEMBER_NAME"));
				lesson.setInstr(rset.getInt("INSTR"));
				lesson.setCategoryName(rset.getString("CTGR_NM"));
				lesson.setLikesCnt(rset.getInt("LIKES_CNT"));
				lesson.setLessonAva(rset.getString("LESSON_AVA").charAt(0));
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return lesson;
	}
	
	public int increseReadCnt(Connection conn, int lessonNo) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("increseReadCnt");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int selectNextNo(Connection conn) throws Exception {
		
		int lessonNo = 0;
		String query = prop.getProperty("selectNextNo");
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			if(rset.next())
				lessonNo = rset.getInt(1);
		} finally {
			close(rset);
			close(stmt);
		}
		return lessonNo;
	}

	public int insertLesson(Connection conn, Map<String, Object> map) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("insertLesson");
		Lesson lesson = (Lesson) map.get("lesson");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, (int) map.get("lessonNo"));
			pstmt.setString(2, lesson.getLessonTitle());
			pstmt.setString(3, lesson.getLessonContent());
			pstmt.setString(4, lesson.getPlace());
			pstmt.setInt(5, lesson.getPrice());
			pstmt.setInt(6, lesson.getMaxNum());
			pstmt.setDate(7, lesson.getLessonDate());
			pstmt.setInt(8, lesson.getLessonCnt());
			pstmt.setInt(9, lesson.getLessonTime());
			pstmt.setString(10, lesson.getLessonType()+"");
			pstmt.setInt(11, (int) map.get("instr"));
			pstmt.setInt(12, (int) map.get("categoryCode"));
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int insertAttachment(Connection conn, Attachment at) throws Exception {
		
		int result = 0;
		String query = prop.getProperty("insertAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, at.getFilePath());
			pstmt.setString(2, at.getFileName());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getParentLessonNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<Attachment> selectLessonFiles(Connection conn, int lessonNo) throws Exception {
		
		List<Attachment> fList = null;
		String query = prop.getProperty("selectLessonFiles");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			rset = pstmt.executeQuery();

			fList = new ArrayList<Attachment>();
			while(rset.next()) {
				Attachment at = new Attachment(rset.getInt("FILE_NO"), rset.getString("FILE_NAME"), rset.getInt("FILE_LEVEL"));
				at.setFilePath(rset.getString("FILE_PATH"));
				fList.add(at);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return fList;
	}

	public List<Attachment> selectThumbnailList(Connection conn, PageInfo pInfo) throws Exception {
		
		List<Attachment> fList = null;
		String query = prop.getProperty("selectThumbnailList");
		
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

	public int updateLesson(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateLesson");
		Lesson lesson = (Lesson) map.get("lesson");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, lesson.getLessonTitle());
			pstmt.setString(2, lesson.getLessonContent());
			pstmt.setString(3, lesson.getPlace());
			pstmt.setInt(4, lesson.getPrice());
			pstmt.setInt(5, lesson.getMaxNum());
			pstmt.setDate(6, lesson.getLessonDate());
			pstmt.setInt(7, lesson.getLessonCnt());
			pstmt.setInt(8, lesson.getLessonTime());
			pstmt.setString(9, lesson.getLessonType()+"");
			pstmt.setInt(10, (int) map.get("categoryCode"));
			pstmt.setInt(11, lesson.getLessonNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateAttachment(Connection conn, Attachment newFile) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, newFile.getFilePath());
			pstmt.setString(2, newFile.getFileName());
			pstmt.setInt(3, newFile.getFileNo());
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}
	
	public int updateLessonFL(Connection conn, int lessonNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateLessonFL");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int updateStatus(Connection conn, int lessonNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("updateStatus");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public int deleteAttachment(Connection conn, int fileNo) throws SQLException {
		int result = 0;
		String query = prop.getProperty("deleteAttachment");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, fileNo);
			
			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}
		return result;
	}

	public List<Lesson> selectLikesLesson(Connection conn, PageInfo pInfo, int memberNo) throws Exception {
		List<Lesson> lessonList = null;
		String query = prop.getProperty("selectLikesLesson");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
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

	public List<Attachment> selectLikesThumbnailList(Connection conn, PageInfo pInfo, int memberNo) throws Exception {
		List<Attachment> fList = null;
		String query = prop.getProperty("selectLikesThumbnailList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
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

	public List<Attachment> selectMyThumbnailList(Connection conn, PageInfo pInfo, int memberNo) throws Exception {
		List<Attachment> fList = null;
		String query = prop.getProperty("selectMyThumbnailList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
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

	public int selectSubjectCnt(Connection conn, int lessonNo) throws Exception {
		int result = 0;
		String query = prop.getProperty("selectSubjectCnt");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, lessonNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

	public int getMyListCount(Connection conn, String cp, int memberNo) throws Exception {

		int listCount = 0;
		String query = prop.getProperty("getMyListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			if(rset.next())
				listCount = rset.getInt(1);
		} finally {
			close(stmt);
			close(rset);
		}
		return listCount;
	}

	public int getLikesListCount(Connection conn, String cp, int memberNo) throws Exception {

		int listCount = 0;
		String query = prop.getProperty("getLikesListCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			rset = pstmt.executeQuery();
			
			if(rset.next())
				listCount = rset.getInt(1);
		} finally {
			close(stmt);
			close(rset);
		}
		return listCount;
	}
}
