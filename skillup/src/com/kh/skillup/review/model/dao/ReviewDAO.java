package com.kh.skillup.review.model.dao;

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

import com.kh.skillup.instructor.model.dao.InstructorDAO;
import com.kh.skillup.lesson.model.vo.Lesson;
import com.kh.skillup.review.model.vo.Review;

public class ReviewDAO {
	private Statement stmt = null;
	private PreparedStatement pstmt = null;
	private ResultSet rset = null;

	private Properties prop = null;
	
	public ReviewDAO() {
		String fileName = InstructorDAO.class.getResource("/com/kh/skillup/sql/review/review-query.xml").getPath();
		try {
			prop = new Properties();
			prop.loadFromXML(new FileInputStream(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	/** 리뷰 목록 조회 DAO
	    * @param conn
	    * @param parentBoardNo
	    * @return rList
	    * @throws Exception
	    */
   public List<Review> selectList(Connection conn, int instrNo) throws Exception{
      List<Review> rList = null;
      
      String query = prop.getProperty("selectList");
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setInt(1,  instrNo);
         
         rset = pstmt.executeQuery();
         
         rList = new ArrayList<Review>();
         
         while(rset.next()) {
            Review review = new Review();
            //REVIEW_NO, REVIEW_CONTENT, REVIEW_DT, SCORE, REVIEW_WRITER, LESSON_TITLE, MEMBER_NAME
            
            review.setReviewNo(rset.getInt("REVIEW_NO"));
            review.setReviewContent(rset.getNString("REVIEW_CONTENT"));
            review.setReviewDt(rset.getTimestamp("REVIEW_DT"));
            review.setScore(rset.getInt("SCORE"));
            review.setReviewWriter(rset.getInt("REVIEW_WRITER"));
            review.setStatus(rset.getString("LESSON_TITLE"));
            review.setWriterName(rset.getNString("MEMBER_NAME"));
            
            rList.add(review);
         }
      } finally {
         close(rset);
         close(pstmt);
      }
      return rList;
   }

   /** 작성 가능 리뷰 확인 DAO
 * @param conn
 * @param instrNo
 * @param memNo
 * @return
 * @throws Exception
 */
public int confirm(Connection conn, int instrNo, int memNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("confirm");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			pstmt.setInt(2, memNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

   /** 리뷰 삽입 DAO
    * @param conn
    * @param reply
    * @return result
    * @throws Exception
    */
   public int insertReview(Connection conn, Review review) throws Exception {
      int result = 0;
      
      String query = prop.getProperty("insertReview");
      
      try {
         pstmt = conn.prepareStatement(query);
         pstmt.setString(1, review.getReviewContent());
         pstmt.setInt(2, review.getScore());
         pstmt.setInt(3, review.getReviewWriter());
         pstmt.setInt(4, review.getInstructorNo());
         pstmt.setInt(5, review.getSubjectNo());
         
         result = pstmt.executeUpdate();
               
      } finally {
         close(pstmt);
      }
      return result;
   }



	/** 리뷰 수정 DAO
	 * @param conn
	 * @param reply
	 * @return result
	 * @throws Exception
	 */
	public int updateReview(Connection conn, Review review) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateReview");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setNString(1, review.getReviewContent());
			pstmt.setInt(2, review.getReviewNo());
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
			
		}
		return result;
	}

	/** 리뷰 상태 변경 DAO
	 * @param conn
	 * @param replyNo
	 * @return result
	 * @throws Exception
	 */
	public int updateReviewStatus(Connection conn, int reviewNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("updateReviewStatus");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reviewNo);
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}

	/** 수업 번호, 수업명 조회 DAO
	 * @param conn
	 * @param instrNo
	 * @param memNo
	 * @return lTitle
	 * @throws Exception
	 */
	public List<Lesson> selectLessonTitle(Connection conn, int instrNo, int memNo) throws Exception {
		List<Lesson> lTitle = null;
		
		String query = prop.getProperty("selectLessonTitle");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			pstmt.setInt(2, memNo);
			
			rset = pstmt.executeQuery();
			
			lTitle = new ArrayList<Lesson>();
			
			while(rset.next()) {
				Lesson lesson = new Lesson();
				lesson.setLessonNo(rset.getInt("SUBJECT_NO"));
				lesson.setLessonTitle(rset.getNString("LESSON_TITLE"));
				
				lTitle.add(lesson);
				}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		return lTitle;
	}

	/** 리뷰 중복 확인 DAO
	 * @param conn
	 * @param subjectNo
	 * @return overlap
	 * @throws Exception
	 */
	public int overlap(Connection conn, int subjectNo) throws Exception {
		int overlap = 0;
		
		String query = prop.getProperty("overlap");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, subjectNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				overlap = rset.getInt(1);
			}
			
		}finally {
			close(rset);
			close(pstmt);
		}
		
		return overlap;
	}


	/** 리뷰 신고 DAO
	 * @param conn
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public int insertReport(Connection conn, Map<String, Object> map) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("insertReport");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setNString(1, (String)map.get("reportTitle"));
			pstmt.setNString(2, (String)map.get("reportContent"));
			pstmt.setInt(3, (int)map.get("reviewNo"));
			pstmt.setInt(4, (int)map.get("memberNo"));
			pstmt.setInt(5, (int)map.get("ctgrCd"));
			
			result = pstmt.executeUpdate();
		}finally {
			close(pstmt);
		}
		return result;
	}


	/** 리뷰 개수 count DAO
	 * @param conn
	 * @param instrNo
	 * @return result
	 * @throws Exception
	 */
	public int selectReviewCount(Connection conn, int instrNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("selectReviewCount");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}


	/** 수강 평균 만족도 계산 DAO
	 * @param conn
	 * @param instrNo
	 * @return
	 * @throws Exception
	 */
	public int selectReviewScore(Connection conn, int instrNo) throws Exception {
		int result = 0;
		
		String query = prop.getProperty("selectReviewScore");
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, instrNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				result = rset.getInt(1);
			}
		}finally {
			close(rset);
			close(pstmt);
		}
		return result;
	}

}
