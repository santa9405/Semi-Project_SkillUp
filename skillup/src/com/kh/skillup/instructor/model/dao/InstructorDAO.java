package com.kh.skillup.instructor.model.dao;

import static com.kh.skillup.common.JDBCTemplate.*;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import com.kh.skillup.instructor.model.vo.Attachment;
import com.kh.skillup.instructor.model.vo.Instructor;
import com.kh.skillup.instructor.model.vo.PageInfo;
import com.kh.skillup.lesson.model.vo.Lesson;

public class InstructorDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;
	
	public InstructorDAO() {
		String fileName = InstructorDAO.class.getResource("/com/kh/skillup/sql/instructor/instructor-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	// 전문가 등록 저장  -------------------------------------------------------------------------
	/** 전문가 정보 저장  DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertSaveInstructor(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;

		String query = prop.getProperty("insertSaveInstructor");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int) map.get("memberNo"));
			pstmt.setString(2, (String) map.get("introduceContent"));
			pstmt.setString(3, (String) map.get("careerContent"));
			pstmt.setInt(4, (int) map.get("categoryCode"));
			pstmt.setString(5, (String) map.get("memberName"));

			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}

		return result;
	}

	/** 전문가 신청 저장 -> 신청 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateEnrollment(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;

		String query = prop.getProperty("updateEnrollment");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, (String) map.get("introduceContent"));
			pstmt.setString(2, (String) map.get("careerContent"));
			pstmt.setInt(3, (int) map.get("categoryCode"));
			pstmt.setInt(4, (int) map.get("memberNo"));

			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}

		return result;
	}


	
	// 전문가 등록 DAO -------------------------------------------------------------------------
	/**
	 * 전문가 정보 삽입 DAO
	 * 
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int insertInstructor(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;

		String query = prop.getProperty("insertInstructor");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, (int) map.get("memberNo"));
			pstmt.setString(2, (String) map.get("introduceContent"));
			pstmt.setString(3, (String) map.get("careerContent"));
			pstmt.setInt(4, (int) map.get("categoryCode"));
			pstmt.setString(5, (String) map.get("memberName"));

			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);
		}

		return result;
	}

	/**
	 * 파일 정보 삽입 DAO
	 * 
	 * @param conn
	 * @param at
	 * @return result
	 * @throws Exception
	 */
	public int insertAttachment(Connection conn, Attachment at) throws Exception {
		int result = 0;

		String query = prop.getProperty("insertAttachment");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setNString(1, at.getFilePath());
			pstmt.setNString(2, at.getFileName());
			pstmt.setInt(3, at.getFileLevel());
			pstmt.setInt(4, at.getParentInstrNo());

			result = pstmt.executeUpdate();
		} finally {
			close(pstmt);

		}
		return result;
	}
	
	
	// 전문가 승인 -------------------------------------------------------------------------
	/**
	 * 전체 전문가 신청 수 반환 DAO
	 * 
	 * @param conn
	 * @return listCount
	 * @throws Exception
	 */
	public int getListCount(Connection conn) throws Exception {
		int listCount = 0;

		String query = prop.getProperty("getListCount");

		try {
			stmt = conn.createStatement();

			rset = stmt.executeQuery(query);

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		} finally {
			close(stmt);
			close(rset);
		}
		return listCount;
	}

	// 전문가 마이페이지 ------------------------------------------------------------------------
	/**
	 * 전문가 프로필 이미지 조회 DAO
	 * 
	 * @param conn
	 * @param instrNo
	 * @return fList
	 * @throws Exception
	 */
	public List<Attachment> selectInstrFiles(Connection conn, int instrNo) throws Exception {
		List<Attachment> fList = null;

		String query = prop.getProperty("selectInstrFiles");

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, instrNo);

			rset = pstmt.executeQuery();

			fList = new ArrayList<Attachment>();

			while(rset.next()) {
				Attachment at = new Attachment(rset.getInt("FILE_NO"), rset.getNString("FILE_NAME"),
						rset.getInt("FILE_LEVEL"));

				at.setFilePath(rset.getNString("FILE_PATH"));

				fList.add(at);
			}

		} finally {
			close(rset);
			close(pstmt);
		}

		return fList;
	}
	
	/**
	 * 파일 정보 수정 DAO
	 * 
	 * @param conn
	 * @param newFile
	 * @return result
	 * @throws Exception
	 */
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
	
	/** 전문가 마이페이지 수정 DAO
	 * @param conn
	 * @param map
	 * @return result
	 * @throws Exception
	 */
	public int updateInstructor(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateInstructor");
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, (String) map.get("introduceContent"));
			pstmt.setString(2, (String) map.get("careerContent"));
			pstmt.setInt(3, (int) map.get("categoryCode"));
			pstmt.setInt(4, (int) map.get("memberNo"));

			result = pstmt.executeUpdate();
			
		}finally{
			close(pstmt);
		}
		return result;
	}
	
	// 전문가 상세 조회 -------------------------------------------------------------------------
	/**
	 * 전문가 상세 조회 DAO
	 * 
	 * @param conn
	 * @param memberNo
	 * @return instr
	 * @throws Exception
	 */
	public Instructor selectInstructor(Connection conn, int instrNo) throws Exception {
		Instructor instr = null;

		String query = prop.getProperty("selectInstructor");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			rset = pstmt.executeQuery();

			if (rset.next()) {
				instr = new Instructor();
				instr.setInstr(rset.getInt("INSTR"));
				instr.setIntroduction(rset.getNString("INTRODUCTION"));
				instr.setCareer(rset.getNString("CAREER"));
				instr.setCtgrCd(rset.getInt("CTGR_CD"));
				instr.setInstrName(rset.getNString("INSTR_NAME"));
				instr.setPermitFl(rset.getString("PERMIT_FL"));
			}

		} finally {
			close(rset);
			close(pstmt);
		}
		return instr;
	}

	/** 전문가 신청 목록 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @return iList
	 * @throws Exception
	 */
	public List<Instructor> selectInstrList(Connection conn, PageInfo pInfo) throws Exception {
		List<Instructor> iList = null;

		String query = prop.getProperty("selectInstrList");

		try {
			// SQL 구문 조건절에 대입할 변수 생성
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;

			pstmt = conn.prepareStatement(query);

			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);

			rset = pstmt.executeQuery();

			iList = new ArrayList<Instructor>();

			while (rset.next()) {
				Instructor instr = new Instructor(rset.getInt("INSTR"), rset.getInt("CTGR_CD"), rset.getNString("INSTR_NAME"), rset.getNString("PERMIT_FL"), rset.getInt("ENROLLMENT_NO"));
				
				iList.add(instr);
			}

		} finally {
			close(rset);
			close(pstmt);
		}
		return iList;
	}

	/** 전문가 승인에 의한 회원 등급 변경 DAO
	 * @param conn
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int updateMemberGrade(Connection conn, int instrNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateMemberGrade");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/** 전문가 승인에 의한 승인 여부 변경 DAO
	 * @param conn
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int updatePermitFl(Connection conn, int instrNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updatePermitFl");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/** 진행 중인 수업 개수 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param instrNo
	 * @return lessonList
	 * @throws Exception
	 */
	public List<Lesson> selectLessonList(Connection conn, PageInfo pInfo, int instrNo) throws Exception {
		List<Lesson> lessonList = null;
		String query = prop.getProperty("selectLessonList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
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

	/** 진행 중인 수업 썸네일 조회 DAO
	 * @param conn
	 * @param pInfo
	 * @param instrNo
	 * @return lList
	 * @throws Exception
	 */
	public List<Attachment> selectThumbnailList(Connection conn, PageInfo pInfo, int instrNo) throws Exception {
		List<Attachment> lList = null;
		String query = prop.getProperty("selectThumbnailList");
		
		try {
			int startRow = (pInfo.getCurrentPage() - 1) * pInfo.getLimit() + 1;
			int endRow = startRow + pInfo.getLimit() - 1;
			
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			pstmt.setInt(2, startRow);
			pstmt.setInt(3, endRow);
			rset = pstmt.executeQuery();
			
			lList = new ArrayList<Attachment>();
			while(rset.next()) {
				Attachment at = new Attachment();
				at.setFileName(rset.getString("FILE_NAME"));
				at.setParentInstrNo(rset.getInt("LESSON_NO"));
				
				lList.add(at);
			}
		} finally {
			close(rset);
			close(pstmt);
		}
		return lList;
	}



	/** 전문가 신청 저장 여부 확인 DAO
	 * @param conn
	 * @param memberNo
	 * @return checkInstr
	 * @throws Exception
	 */
	public int checkInstr(Connection conn, int memberNo) throws Exception {
		int checkInstr = 0;
		
		String query = prop.getProperty("checkInstr");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, memberNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				checkInstr = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return checkInstr;
	}

	/** 진행 중인 수업 페이징 처리를 위한 값 계산 DAO
	 * @param conn
	 * @param instrNo 
	 * @return PageInfo
	 * @throws Exception
	 */
	public int getLessonListCount(Connection conn, int instrNo) throws Exception {
		int listCount = 0;

		String query = prop.getProperty("getLessonListCount");

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				listCount = rset.getInt(1);
			}

		} finally {
			close(stmt);
			close(rset);
		}
		return listCount;
	}

	/** 전문가 신청 저장 여부 확인 DAO
	 * @param conn
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int selectSaveFl(Connection conn, int instrNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("selectSaveFl");
		
		return 0;
	}


}











